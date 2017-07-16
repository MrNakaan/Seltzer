package hall.caleb.seltzer.core.processor;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.command.wait.CountWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.JavaScriptWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.RefreshedWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.SelectionStateWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.WaitCommandData;
import hall.caleb.seltzer.objects.command.wait.existence.ExistenceWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.existence.NestedExistenceWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.logical.LogicalAndOrWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.logical.LogicalNotWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.logical.LogicalWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchAttributeSelectorWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchSelectorWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.visibility.InvisibilityWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.visibility.NestedVisibilityWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.visibility.VisibilityWaitCommandData;
import hall.caleb.seltzer.objects.response.ExceptionResponse;
import hall.caleb.seltzer.objects.response.Response;

public class WaitProcessor {
	private static Logger logger = LogManager.getLogger(WaitProcessor.class);

	static Response processCommand(WebDriver driver, WaitCommandData command) {
		Response response = new Response(command.getId(), true);

		try {
			ExpectedCondition<?> condition = processWaitCommand(driver, command);

			if (condition != null) {
				WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
				wait.until(condition);
			}
		} catch (WebDriverException e) {
			response.setSuccess(false);
		} catch (Exception e) {
			logger.error(e);
			ExceptionResponse eResponse = new ExceptionResponse(command.getId());
			eResponse.setMessage(Messages.getString("BaseProcessor.exception"));
			eResponse.setStackTrace(new StackTraceElement[0]);
			response = eResponse;
		}

		return response;
	}
	
	private static ExpectedCondition<?> processWaitCommand(WebDriver driver, WaitCommandData command) {
		ExpectedCondition<?> condition = null;

		if (command instanceof LogicalWaitCommandData) {
			condition = processLogicalWaitCommand(driver, (LogicalWaitCommandData) command);
		} else if (command instanceof CountWaitCommandData) {
			condition = processCountWaitCommand(driver, (CountWaitCommandData) command);
		} else if (command instanceof ExistenceWaitCommandData) {
			condition = processExistenceWaitCommand(driver, (ExistenceWaitCommandData) command);
		} else if (command instanceof InvisibilityWaitCommandData) {
			condition = processInvisibilityWaitCommand(driver, (InvisibilityWaitCommandData) command);
		} else if (command instanceof JavaScriptWaitCommandData) {
			condition = processJavaScriptWaitCommand(driver, (JavaScriptWaitCommandData) command);
		} else if (command instanceof SelectionStateWaitCommandData) {
			condition = processSelectionStateWaitCommand(driver, (SelectionStateWaitCommandData) command);
		} else if (command instanceof TextMatchWaitCommandData) {
			condition = processTextMatchWaitCommand(driver, (TextMatchWaitCommandData) command);
		} else if (command instanceof VisibilityWaitCommandData) {
			condition = processVisibilityWaitCommand(driver, (VisibilityWaitCommandData) command);
		} else if (command instanceof RefreshedWaitCommandData) {
			condition = processRefreshedWaitCommand(driver, (RefreshedWaitCommandData) command);
		}
		
		switch (command.getType()) {
		case ALERT_PRESENT_WAIT:
			condition = alertIsPresent(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processRefreshedWaitCommand(WebDriver driver, RefreshedWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		switch (command.getType()) {
		case REFRESHED_WAIT:
			condition = refreshed(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> refreshed(WebDriver driver, RefreshedWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		condition = ExpectedConditions.refreshed(processWaitCommand(driver, command.getWaitCommand()));
		
		return condition;
	}

	private static ExpectedCondition<?> processLogicalWaitCommand(WebDriver driver, LogicalWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		switch (command.getType()) {
		case AND_WAIT:
			condition = andOr(driver, (LogicalAndOrWaitCommandData) command);
			break;
		case OR_WAIT:
			condition = andOr(driver, (LogicalAndOrWaitCommandData) command);
			break;
		case NOT_WAIT:
			condition = not(driver, (LogicalNotWaitCommandData) command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> not(WebDriver driver, LogicalNotWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		condition = ExpectedConditions.not(processWaitCommand(driver, command.getWaitCommand()));
		
		return condition;
	}

	private static ExpectedCondition<?> andOr(WebDriver driver, LogicalAndOrWaitCommandData command) {
		ExpectedCondition<?> condition = null;
		ExpectedCondition<?>[] conditions = new ExpectedCondition<?>[command.getCommands().size()];

		int badCommands = 0; 
		for (int i = 0; i < command.getCommands().size(); i++) {
			if (command.getCommands().get(i) instanceof WaitCommandData) {
				conditions[i] = processWaitCommand(driver, (WaitCommandData) command.getCommands().get(i));
			} else {
				conditions[i] = null;
				badCommands++;
			}
		}
		
		if (badCommands > 0) {
			ExpectedCondition<?>[] goodConditions = new ExpectedCondition<?>[conditions.length - badCommands];
			
			int i = 0;
			for (ExpectedCondition<?> c : conditions) {
				if (c != null) {
					goodConditions[i] = c;
					i++;
				}
			}
			
			conditions = goodConditions;
		}
		
		
		if (command.getType() == CommandType.AND_WAIT) { 
			condition = ExpectedConditions.and(conditions);
		} else if (command.getType() == CommandType.OR_WAIT) {
			condition = ExpectedConditions.or(conditions);
		}
		
		return condition;
	}
	
	private static ExpectedCondition<?> processCountWaitCommand(WebDriver driver, CountWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		switch (command.getType()) {
		case ELEMENT_COUNT_IS_WAIT:
			condition = elementCount(driver, command);
			break;
		case ELEMENT_COUNT_LESS_THAN_WAIT:
			condition = elementCountLessThan(driver, command);
			break;
		case ELEMENT_COUNT_GREATER_THAN_WAIT:
			condition = elementCountMoreThan(driver, command);
			break;
		case WINDOW_COUNT_IS_WAIT:
			condition = windowCount(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processExistenceWaitCommand(WebDriver driver, ExistenceWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		if (command instanceof NestedExistenceWaitCommandData) {
			condition = processNestedExistenceWaitCommand(driver, (NestedExistenceWaitCommandData) command);
		}

		switch (command.getType()) {
		case ELEMENT_CLICKABLE_WAIT:
			condition = elementClickable(driver, command);
			break;
		case SWITCH_TO_FRAME_WHEN_AVAILABLE_WAIT:
			condition = frameToBeAvailableSwitch(driver, command);
			break;
		case ALL_ELEMENTS_PRESENT_WAIT:
			condition = presenceOfAllElements(driver, command);
			break;
		case ELEMENT_PRESENT_WAIT:
			condition = presenceOfElement(driver, command);
			break;
		case IS_STALE_WAIT:
			condition = staleness(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processNestedExistenceWaitCommand(WebDriver driver,
			NestedExistenceWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		switch (command.getType()) {
		case NESTED_ELEMENT_PRESENT_WAIT:
			condition = presenceOfNestedElement(driver, command);
			break;
		case NESTED_ELEMENTS_PRESENT_WAIT:
			condition = presenceOfAllNestedElements(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processInvisibilityWaitCommand(WebDriver driver,
			InvisibilityWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		switch (command.getType()) {
		case ELEMENT_INVISIBLE_WAIT:
			condition = invisibilityOf(driver, command);
			break;
		case ALL_ELEMENTS_INVISBLE_WAIT:
			condition = invisibilityOfAll(driver, command);
			break;
		case ELEMENT_WITH_TEXT_INVISIBLE_WAIT:
			condition = invisibilityOfElementWithText(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processJavaScriptWaitCommand(WebDriver driver, JavaScriptWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		switch (command.getType()) {
		case JAVASCRIPT_RETURNS_STRING_WAIT:
			condition = javascriptReturns(driver, command);
			break;
		case JAVASCRIPT_THROWS_NO_EXCEPTIONS_WAIT:
			condition = javascriptCompletes(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processSelectionStateWaitCommand(WebDriver driver,
			SelectionStateWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		switch (command.getType()) {
		case ELEMENT_SELECTION_STATE_IS_WAIT:
			condition = elementSelectionState(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processTextMatchWaitCommand(WebDriver driver, TextMatchWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		if (command instanceof TextMatchAttributeSelectorWaitCommandData) {
			condition = processTextMatchAttributeSelectorWaitCommand(driver,
					(TextMatchAttributeSelectorWaitCommandData) command);
		} else if (command instanceof TextMatchSelectorWaitCommandData) {
			condition = processTextMatchSelectorWaitCommand(driver, (TextMatchSelectorWaitCommandData) command);
		}

		switch (command.getType()) {
		case TITLE_CONTAINS_WAIT:
			condition = titleContains(driver, command);
			break;
		case TITLE_IS_WAIT:
			condition = titleIs(driver, command);
			break;
		case URL_CONTAINS_WAIT:
			condition = urlContains(driver, command);
			break;
		case URL_MATCHES_WAIT:
			condition = urlMatches(driver, command);
			break;
		case URL_IS_WAIT:
			condition = urlToBe(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processTextMatchSelectorWaitCommand(WebDriver driver,
			TextMatchSelectorWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		switch (command.getType()) {
		case TEXT_MATCHES_WAIT:
			condition = textMatches(driver, command);
			break;
		case TEXT_IS_WAIT:
			condition = textIs(driver, command);
			break;
		case TEXT_PRESENT_IN_ELEMENT_WAIT:
			condition = textPresent(driver, command);
			break;
		case TEXT_IN_ELEMENT_VALUE_WAIT:
			condition = textPresentInValue(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processTextMatchAttributeSelectorWaitCommand(WebDriver driver,
			TextMatchAttributeSelectorWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		switch (command.getType()) {
		case ATTRIBUTE_CONTAINS_WAIT:
			condition = attributeContains(driver, command);
			break;
		case ATTRIBUTE_IS_WAIT:
			condition = attributeIs(driver, command);
			break;
		case ATTRIBUTE_IS_NOT_EMPTY_WAIT:
			condition = attributeIsNotEmpty(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processVisibilityWaitCommand(WebDriver driver, VisibilityWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		if (command instanceof NestedVisibilityWaitCommandData) {
			condition = processNestedVisibilityWaitCommand(driver, (NestedVisibilityWaitCommandData) command);
		}

		switch (command.getType()) {
		case ELEMENT_VISIBLE_WAIT:
			condition = visibilityOf(driver, command);
			break;
		case ALL_ELEMENTS_VISIBLE_WAIT:
			condition = visibilityOfAll(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processNestedVisibilityWaitCommand(WebDriver driver,
			NestedVisibilityWaitCommandData command) {
		ExpectedCondition<?> condition = null;

		switch (command.getType()) {
		case NESTED_ELEMENTS_VISIBLE_WAIT:
			condition = visibilityOfAllNested(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> invisibilityOfElementWithText(WebDriver driver,
			InvisibilityWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.invisibilityOfElementWithText(by, command.getText());
	}

	private static ExpectedCondition<?> invisibilityOfAll(WebDriver driver, InvisibilityWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.invisibilityOfAllElements(driver.findElements(by));
	}

	private static ExpectedCondition<?> invisibilityOf(WebDriver driver, InvisibilityWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.invisibilityOfElementLocated(by);
	}

	private static ExpectedCondition<?> frameToBeAvailableSwitch(WebDriver driver, ExistenceWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.frameToBeAvailableAndSwitchToIt(by);
	}

	private static ExpectedCondition<?> elementClickable(WebDriver driver, ExistenceWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.elementToBeClickable(by);
	}

	private static ExpectedCondition<?> elementSelectionState(WebDriver driver, SelectionStateWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.elementSelectionStateToBe(by, command.getSelected());
	}

	private static ExpectedCondition<?> presenceOfAllNestedElements(WebDriver driver,
			NestedExistenceWaitCommandData command) {
		By parentBy = BaseProcessor.getBy(command.getSelector());
		By childBy = BaseProcessor.getBy(command.getChildSelector());

		return ExpectedConditions.presenceOfNestedElementsLocatedBy(parentBy, childBy);
	}

	private static ExpectedCondition<?> presenceOfNestedElement(WebDriver driver, NestedExistenceWaitCommandData command) {
		By parentBy = BaseProcessor.getBy(command.getSelector());
		By childBy = BaseProcessor.getBy(command.getChildSelector());

		return ExpectedConditions.presenceOfNestedElementLocatedBy(parentBy, childBy);
	}

	private static ExpectedCondition<?> presenceOfElement(WebDriver driver, ExistenceWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.presenceOfElementLocated(by);
	}

	private static ExpectedCondition<?> presenceOfAllElements(WebDriver driver, ExistenceWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.presenceOfAllElementsLocatedBy(by);
	}

	private static ExpectedCondition<?> attributeContains(WebDriver driver,
			TextMatchAttributeSelectorWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.attributeContains(by, command.getAttribute(), command.getText());
	}

	private static ExpectedCondition<?> attributeIs(WebDriver driver, TextMatchAttributeSelectorWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.attributeToBe(by, command.getAttribute(), command.getText());
	}

	private static ExpectedCondition<?> attributeIsNotEmpty(WebDriver driver,
			TextMatchAttributeSelectorWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.attributeToBeNotEmpty(driver.findElement(by), command.getAttribute());
	}

	private static ExpectedCondition<?> javascriptReturns(WebDriver driver, JavaScriptWaitCommandData command) {
		return ExpectedConditions.javaScriptThrowsNoExceptions(command.getJavaScript());
	}

	private static ExpectedCondition<?> javascriptCompletes(WebDriver driver, JavaScriptWaitCommandData command) {
		return ExpectedConditions.javaScriptThrowsNoExceptions(command.getJavaScript());
	}

	private static ExpectedCondition<?> elementCount(WebDriver driver, CountWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.numberOfElementsToBe(by, command.getBound());
	}

	private static ExpectedCondition<?> elementCountLessThan(WebDriver driver, CountWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.numberOfElementsToBeLessThan(by, command.getBound());
	}

	private static ExpectedCondition<?> elementCountMoreThan(WebDriver driver, CountWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.numberOfElementsToBeMoreThan(by, command.getBound());
	}

	private static ExpectedCondition<?> windowCount(WebDriver driver, CountWaitCommandData command) {
		return ExpectedConditions.numberOfWindowsToBe(command.getBound());
	}

	private static ExpectedCondition<?> staleness(WebDriver driver, ExistenceWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.stalenessOf(driver.findElement(by));
	}

	private static ExpectedCondition<?> textMatches(WebDriver driver, TextMatchSelectorWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());
		Pattern pattern = Pattern.compile(command.getText());

		return ExpectedConditions.textMatches(by, pattern);
	}

	private static ExpectedCondition<?> textIs(WebDriver driver, TextMatchSelectorWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.textToBe(by, command.getText());
	}

	private static ExpectedCondition<?> textPresentInValue(WebDriver driver, TextMatchSelectorWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.textToBePresentInElementValue(by, command.getText());
	}

	private static ExpectedCondition<?> textPresent(WebDriver driver, TextMatchSelectorWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.textToBePresentInElementLocated(by, command.getText());
	}

	private static ExpectedCondition<?> visibilityOfAllNested(WebDriver driver, NestedVisibilityWaitCommandData command) {
		By parentBy = BaseProcessor.getBy(command.getSelector());
		By childBy = BaseProcessor.getBy(command.getChildSelector());

		return ExpectedConditions.visibilityOfNestedElementsLocatedBy(parentBy, childBy);
	}

	private static ExpectedCondition<?> visibilityOfAll(WebDriver driver, VisibilityWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.visibilityOfAllElementsLocatedBy(by);
	}

	private static ExpectedCondition<?> visibilityOf(WebDriver driver, VisibilityWaitCommandData command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.visibilityOf(driver.findElement(by));
	}

	private static ExpectedCondition<?> titleIs(WebDriver driver, TextMatchWaitCommandData command) {
		return ExpectedConditions.titleIs(command.getText());
	}

	private static ExpectedCondition<?> titleContains(WebDriver driver, TextMatchWaitCommandData command) {
		return ExpectedConditions.titleContains(command.getText());
	}

	private static ExpectedCondition<?> urlToBe(WebDriver driver, TextMatchWaitCommandData command) {
		return ExpectedConditions.urlToBe(command.getText());
	}

	private static ExpectedCondition<?> urlMatches(WebDriver driver, TextMatchWaitCommandData command) {
		return ExpectedConditions.urlMatches(command.getText());
	}

	private static ExpectedCondition<?> urlContains(WebDriver driver, TextMatchWaitCommandData command) {
		return ExpectedConditions.urlContains(command.getText());
	}

	private static ExpectedCondition<?> alertIsPresent(WebDriver driver, WaitCommandData command) {
		return ExpectedConditions.alertIsPresent();
	}
}
