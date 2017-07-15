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

import hall.caleb.seltzer.enums.WaitType;
import hall.caleb.seltzer.objects.command.wait.CountWaitCommand;
import hall.caleb.seltzer.objects.command.wait.JavaScriptWaitCommand;
import hall.caleb.seltzer.objects.command.wait.RefreshedWaitCommand;
import hall.caleb.seltzer.objects.command.wait.SelectionStateWaitCommand;
import hall.caleb.seltzer.objects.command.wait.WaitCommand;
import hall.caleb.seltzer.objects.command.wait.existence.ExistenceWaitCommand;
import hall.caleb.seltzer.objects.command.wait.existence.NestedExistenceWaitCommand;
import hall.caleb.seltzer.objects.command.wait.logical.LogicalAndOrWaitCommand;
import hall.caleb.seltzer.objects.command.wait.logical.LogicalNotWaitCommand;
import hall.caleb.seltzer.objects.command.wait.logical.LogicalWaitCommand;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchAttributeSelectorWaitCommand;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchSelectorWaitCommand;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchWaitCommand;
import hall.caleb.seltzer.objects.command.wait.visibility.InvisibilityWaitCommand;
import hall.caleb.seltzer.objects.command.wait.visibility.NestedVisibilityWaitCommand;
import hall.caleb.seltzer.objects.command.wait.visibility.VisibilityWaitCommand;
import hall.caleb.seltzer.objects.response.ExceptionResponse;
import hall.caleb.seltzer.objects.response.Response;

public class WaitProcessor {
	private static Logger logger = LogManager.getLogger(WaitProcessor.class);

	static Response processCommand(WebDriver driver, WaitCommand command) {
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
	
	private static ExpectedCondition<?> processWaitCommand(WebDriver driver, WaitCommand command) {
		ExpectedCondition<?> condition = null;

		if (command instanceof LogicalWaitCommand) {
			condition = processLogicalWaitCommand(driver, (LogicalWaitCommand) command);
		} else if (command instanceof CountWaitCommand) {
			condition = processCountWaitCommand(driver, (CountWaitCommand) command);
		} else if (command instanceof ExistenceWaitCommand) {
			condition = processExistenceWaitCommand(driver, (ExistenceWaitCommand) command);
		} else if (command instanceof InvisibilityWaitCommand) {
			condition = processInvisibilityWaitCommand(driver, (InvisibilityWaitCommand) command);
		} else if (command instanceof JavaScriptWaitCommand) {
			condition = processJavaScriptWaitCommand(driver, (JavaScriptWaitCommand) command);
		} else if (command instanceof SelectionStateWaitCommand) {
			condition = processSelectionStateWaitCommand(driver, (SelectionStateWaitCommand) command);
		} else if (command instanceof TextMatchWaitCommand) {
			condition = processTextMatchWaitCommand(driver, (TextMatchWaitCommand) command);
		} else if (command instanceof VisibilityWaitCommand) {
			condition = processVisibilityWaitCommand(driver, (VisibilityWaitCommand) command);
		} else if (command instanceof RefreshedWaitCommand) {
			condition = processRefreshedWaitCommand(driver, (RefreshedWaitCommand) command);
		}
		
		switch (command.getWaitType()) {
		case ALERT_PRESENT:
			condition = alertIsPresent(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processRefreshedWaitCommand(WebDriver driver, RefreshedWaitCommand command) {
		ExpectedCondition<?> condition = null;

		switch (command.getWaitType()) {
		case REFRESHED:
			condition = refreshed(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> refreshed(WebDriver driver, RefreshedWaitCommand command) {
		ExpectedCondition<?> condition = null;

		condition = ExpectedConditions.refreshed(processWaitCommand(driver, command.getWaitCommand()));
		
		return condition;
	}

	private static ExpectedCondition<?> processLogicalWaitCommand(WebDriver driver, LogicalWaitCommand command) {
		ExpectedCondition<?> condition = null;

		switch (command.getWaitType()) {
		case AND:
			condition = andOr(driver, (LogicalAndOrWaitCommand) command);
			break;
		case OR:
			condition = andOr(driver, (LogicalAndOrWaitCommand) command);
			break;
		case NOT:
			condition = not(driver, (LogicalNotWaitCommand) command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> not(WebDriver driver, LogicalNotWaitCommand command) {
		ExpectedCondition<?> condition = null;

		condition = ExpectedConditions.not(processWaitCommand(driver, command.getWaitCommand()));
		
		return condition;
	}

	private static ExpectedCondition<?> andOr(WebDriver driver, LogicalAndOrWaitCommand command) {
		ExpectedCondition<?> condition = null;
		ExpectedCondition<?>[] conditions = new ExpectedCondition<?>[command.getCommands().getCommands().size()];

		int badCommands = 0; 
		for (int i = 0; i < command.getCommands().getCommands().size(); i++) {
			if (command.getCommands().getCommands().get(i) instanceof WaitCommand) {
				conditions[i] = processWaitCommand(driver, (WaitCommand) command.getCommands().getCommands().get(i));
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
		
		
		if (command.getWaitType() == WaitType.AND) { 
			condition = ExpectedConditions.and(conditions);
		} else if (command.getWaitType() == WaitType.OR) {
			condition = ExpectedConditions.or(conditions);
		}
		
		return condition;
	}
	
	private static ExpectedCondition<?> processCountWaitCommand(WebDriver driver, CountWaitCommand command) {
		ExpectedCondition<?> condition = null;

		switch (command.getWaitType()) {
		case ELEMENT_COUNT_IS:
			condition = elementCount(driver, command);
			break;
		case ELEMENT_COUNT_LESS_THAN:
			condition = elementCountLessThan(driver, command);
			break;
		case ELEMENT_COUNT_GREATER_THAN:
			condition = elementCountMoreThan(driver, command);
			break;
		case WINDOW_COUNT_IS:
			condition = windowCount(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processExistenceWaitCommand(WebDriver driver, ExistenceWaitCommand command) {
		ExpectedCondition<?> condition = null;

		if (command instanceof NestedExistenceWaitCommand) {
			condition = processNestedExistenceWaitCommand(driver, (NestedExistenceWaitCommand) command);
		}

		switch (command.getWaitType()) {
		case ELEMENT_CLICKABLE:
			condition = elementClickable(driver, command);
			break;
		case SWITCH_TO_FRAME_WHEN_AVAILABLE:
			condition = frameToBeAvailableSwitch(driver, command);
			break;
		case ALL_ELEMENTS_PRESENT:
			condition = presenceOfAllElements(driver, command);
			break;
		case ELEMENT_PRESENT:
			condition = presenceOfElement(driver, command);
			break;
		case IS_STALE:
			condition = staleness(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processNestedExistenceWaitCommand(WebDriver driver,
			NestedExistenceWaitCommand command) {
		ExpectedCondition<?> condition = null;

		switch (command.getWaitType()) {
		case NESTED_ELEMENT_PRESENT:
			condition = presenceOfNestedElement(driver, command);
			break;
		case NESTED_ELEMENTS_PRESENT:
			condition = presenceOfAllNestedElements(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processInvisibilityWaitCommand(WebDriver driver,
			InvisibilityWaitCommand command) {
		ExpectedCondition<?> condition = null;

		switch (command.getWaitType()) {
		case ELEMENT_INVISIBLE:
			condition = invisibilityOf(driver, command);
			break;
		case ALL_ELEMENTS_INVISBLE:
			condition = invisibilityOfAll(driver, command);
			break;
		case ELEMENT_WITH_TEXT_INVISIBLE:
			condition = invisibilityOfElementWithText(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processJavaScriptWaitCommand(WebDriver driver, JavaScriptWaitCommand command) {
		ExpectedCondition<?> condition = null;

		switch (command.getWaitType()) {
		case JAVASCRIPT_RETURNS_STRING:
			condition = javascriptReturns(driver, command);
			break;
		case JAVASCRIPT_THROWS_NO_EXCEPTIONS:
			condition = javascriptCompletes(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processSelectionStateWaitCommand(WebDriver driver,
			SelectionStateWaitCommand command) {
		ExpectedCondition<?> condition = null;

		switch (command.getWaitType()) {
		case ELEMENT_SELECTION_STATE_IS:
			condition = elementSelectionState(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processTextMatchWaitCommand(WebDriver driver, TextMatchWaitCommand command) {
		ExpectedCondition<?> condition = null;

		if (command instanceof TextMatchAttributeSelectorWaitCommand) {
			condition = processTextMatchAttributeSelectorWaitCommand(driver,
					(TextMatchAttributeSelectorWaitCommand) command);
		} else if (command instanceof TextMatchSelectorWaitCommand) {
			condition = processTextMatchSelectorWaitCommand(driver, (TextMatchSelectorWaitCommand) command);
		}

		switch (command.getWaitType()) {
		case TITLE_CONTAINS:
			condition = titleContains(driver, command);
			break;
		case TITLE_IS:
			condition = titleIs(driver, command);
			break;
		case URL_CONTAINS:
			condition = urlContains(driver, command);
			break;
		case URL_MATCHES:
			condition = urlMatches(driver, command);
			break;
		case URL_IS:
			condition = urlToBe(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processTextMatchSelectorWaitCommand(WebDriver driver,
			TextMatchSelectorWaitCommand command) {
		ExpectedCondition<?> condition = null;

		switch (command.getWaitType()) {
		case TEXT_MATCHES:
			condition = textMatches(driver, command);
			break;
		case TEXT_IS:
			condition = textIs(driver, command);
			break;
		case TEXT_PRESENT_IN_ELEMENT:
			condition = textPresent(driver, command);
			break;
		case TEXT_IN_ELEMENT_VALUE:
			condition = textPresentInValue(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processTextMatchAttributeSelectorWaitCommand(WebDriver driver,
			TextMatchAttributeSelectorWaitCommand command) {
		ExpectedCondition<?> condition = null;

		switch (command.getWaitType()) {
		case ATTRIBUTE_CONTAINS:
			condition = attributeContains(driver, command);
			break;
		case ATTRIBUTE_IS:
			condition = attributeIs(driver, command);
			break;
		case ATTRIBUTE_IS_NOT_EMPTY:
			condition = attributeIsNotEmpty(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processVisibilityWaitCommand(WebDriver driver, VisibilityWaitCommand command) {
		ExpectedCondition<?> condition = null;

		if (command instanceof NestedVisibilityWaitCommand) {
			condition = processNestedVisibilityWaitCommand(driver, (NestedVisibilityWaitCommand) command);
		}

		switch (command.getWaitType()) {
		case ELEMENT_VISIBLE:
			condition = visibilityOf(driver, command);
			break;
		case ALL_ELEMENTS_VISIBLE:
			condition = visibilityOfAll(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> processNestedVisibilityWaitCommand(WebDriver driver,
			NestedVisibilityWaitCommand command) {
		ExpectedCondition<?> condition = null;

		switch (command.getWaitType()) {
		case NESTED_ELEMENTS_VISIBLE:
			condition = visibilityOfAllNested(driver, command);
			break;
		default:
			break;
		}

		return condition;
	}

	private static ExpectedCondition<?> invisibilityOfElementWithText(WebDriver driver,
			InvisibilityWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.invisibilityOfElementWithText(by, command.getText());
	}

	private static ExpectedCondition<?> invisibilityOfAll(WebDriver driver, InvisibilityWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.invisibilityOfAllElements(driver.findElements(by));
	}

	private static ExpectedCondition<?> invisibilityOf(WebDriver driver, InvisibilityWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.invisibilityOfElementLocated(by);
	}

	private static ExpectedCondition<?> frameToBeAvailableSwitch(WebDriver driver, ExistenceWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.frameToBeAvailableAndSwitchToIt(by);
	}

	private static ExpectedCondition<?> elementClickable(WebDriver driver, ExistenceWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.elementToBeClickable(by);
	}

	private static ExpectedCondition<?> elementSelectionState(WebDriver driver, SelectionStateWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.elementSelectionStateToBe(by, command.getSelected());
	}

	private static ExpectedCondition<?> presenceOfAllNestedElements(WebDriver driver,
			NestedExistenceWaitCommand command) {
		By parentBy = BaseProcessor.getBy(command.getSelector());
		By childBy = BaseProcessor.getBy(command.getChildSelector());

		return ExpectedConditions.presenceOfNestedElementsLocatedBy(parentBy, childBy);
	}

	private static ExpectedCondition<?> presenceOfNestedElement(WebDriver driver, NestedExistenceWaitCommand command) {
		By parentBy = BaseProcessor.getBy(command.getSelector());
		By childBy = BaseProcessor.getBy(command.getChildSelector());

		return ExpectedConditions.presenceOfNestedElementLocatedBy(parentBy, childBy);
	}

	private static ExpectedCondition<?> presenceOfElement(WebDriver driver, ExistenceWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.presenceOfElementLocated(by);
	}

	private static ExpectedCondition<?> presenceOfAllElements(WebDriver driver, ExistenceWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.presenceOfAllElementsLocatedBy(by);
	}

	private static ExpectedCondition<?> attributeContains(WebDriver driver,
			TextMatchAttributeSelectorWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.attributeContains(by, command.getAttribute(), command.getText());
	}

	private static ExpectedCondition<?> attributeIs(WebDriver driver, TextMatchAttributeSelectorWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.attributeToBe(by, command.getAttribute(), command.getText());
	}

	private static ExpectedCondition<?> attributeIsNotEmpty(WebDriver driver,
			TextMatchAttributeSelectorWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.attributeToBeNotEmpty(driver.findElement(by), command.getAttribute());
	}

	private static ExpectedCondition<?> javascriptReturns(WebDriver driver, JavaScriptWaitCommand command) {
		return ExpectedConditions.javaScriptThrowsNoExceptions(command.getJavaScript());
	}

	private static ExpectedCondition<?> javascriptCompletes(WebDriver driver, JavaScriptWaitCommand command) {
		return ExpectedConditions.javaScriptThrowsNoExceptions(command.getJavaScript());
	}

	private static ExpectedCondition<?> elementCount(WebDriver driver, CountWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.numberOfElementsToBe(by, command.getBound());
	}

	private static ExpectedCondition<?> elementCountLessThan(WebDriver driver, CountWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.numberOfElementsToBeLessThan(by, command.getBound());
	}

	private static ExpectedCondition<?> elementCountMoreThan(WebDriver driver, CountWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.numberOfElementsToBeMoreThan(by, command.getBound());
	}

	private static ExpectedCondition<?> windowCount(WebDriver driver, CountWaitCommand command) {
		return ExpectedConditions.numberOfWindowsToBe(command.getBound());
	}

	private static ExpectedCondition<?> staleness(WebDriver driver, ExistenceWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.stalenessOf(driver.findElement(by));
	}

	private static ExpectedCondition<?> textMatches(WebDriver driver, TextMatchSelectorWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());
		Pattern pattern = Pattern.compile(command.getText());

		return ExpectedConditions.textMatches(by, pattern);
	}

	private static ExpectedCondition<?> textIs(WebDriver driver, TextMatchSelectorWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.textToBe(by, command.getText());
	}

	private static ExpectedCondition<?> textPresentInValue(WebDriver driver, TextMatchSelectorWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.textToBePresentInElementValue(by, command.getText());
	}

	private static ExpectedCondition<?> textPresent(WebDriver driver, TextMatchSelectorWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.textToBePresentInElementLocated(by, command.getText());
	}

	private static ExpectedCondition<?> visibilityOfAllNested(WebDriver driver, NestedVisibilityWaitCommand command) {
		By parentBy = BaseProcessor.getBy(command.getSelector());
		By childBy = BaseProcessor.getBy(command.getChildSelector());

		return ExpectedConditions.visibilityOfNestedElementsLocatedBy(parentBy, childBy);
	}

	private static ExpectedCondition<?> visibilityOfAll(WebDriver driver, VisibilityWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.visibilityOfAllElementsLocatedBy(by);
	}

	private static ExpectedCondition<?> visibilityOf(WebDriver driver, VisibilityWaitCommand command) {
		By by = BaseProcessor.getBy(command.getSelector());

		return ExpectedConditions.visibilityOf(driver.findElement(by));
	}

	private static ExpectedCondition<?> titleIs(WebDriver driver, TextMatchWaitCommand command) {
		return ExpectedConditions.titleIs(command.getText());
	}

	private static ExpectedCondition<?> titleContains(WebDriver driver, TextMatchWaitCommand command) {
		return ExpectedConditions.titleContains(command.getText());
	}

	private static ExpectedCondition<?> urlToBe(WebDriver driver, TextMatchWaitCommand command) {
		return ExpectedConditions.urlToBe(command.getText());
	}

	private static ExpectedCondition<?> urlMatches(WebDriver driver, TextMatchWaitCommand command) {
		return ExpectedConditions.urlMatches(command.getText());
	}

	private static ExpectedCondition<?> urlContains(WebDriver driver, TextMatchWaitCommand command) {
		return ExpectedConditions.urlContains(command.getText());
	}

	private static ExpectedCondition<?> alertIsPresent(WebDriver driver, WaitCommand command) {
		return ExpectedConditions.alertIsPresent();
	}
}
