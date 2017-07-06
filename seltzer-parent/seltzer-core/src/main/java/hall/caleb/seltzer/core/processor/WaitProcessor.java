package hall.caleb.seltzer.core.processor;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hall.caleb.seltzer.objects.command.wait.CountWaitCommand;
import hall.caleb.seltzer.objects.command.wait.JavaScriptWaitCommand;
import hall.caleb.seltzer.objects.command.wait.SelectionStateWaitCommand;
import hall.caleb.seltzer.objects.command.wait.WaitCommand;
import hall.caleb.seltzer.objects.command.wait.existence.ExistenceWaitCommand;
import hall.caleb.seltzer.objects.command.wait.existence.NestedExistenceWaitCommand;
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
		Response response = new Response(command.getId(), false);

		try {
			Class<? extends WaitCommand> waitClass = command.getWaitType().getWaitClass();
			if (waitClass.equals(CountWaitCommand.class)) {
				response = processCountWaitCommand(driver, (CountWaitCommand) command);
			} else if (waitClass.equals(ExistenceWaitCommand.class)) {
				response = processExistenceWaitCommand(driver, (ExistenceWaitCommand) command);
			} else if (waitClass.equals(InvisibilityWaitCommand.class)) {
				response = processInvisibilityWaitCommand(driver, (InvisibilityWaitCommand) command);
			} else if (waitClass.equals(JavaScriptWaitCommand.class)) {
				response = processJavaScriptWaitCommand(driver, (JavaScriptWaitCommand) command);
			} else if (waitClass.equals(SelectionStateWaitCommand.class)) {
				response = processSelectionStateWaitCommand(driver, (SelectionStateWaitCommand) command);
			} else if (waitClass.equals(TextMatchWaitCommand.class)) {
				response = processTextMatchWaitCommand(driver, (TextMatchWaitCommand) command);
			} else if (waitClass.equals(VisibilityWaitCommand.class)) {
				response = processVisibilityWaitCommand(driver, (VisibilityWaitCommand) command);
			} else {
				response = processWaitCommand(driver, command);
			}
		} catch (WebDriverException e) {
			response.setSuccess(false);
		} catch (Exception e) {
			logger.error(e);
			ExceptionResponse eResponse = new ExceptionResponse(command.getId(), false);
			eResponse.setMessage(
					"A system error unrelated to Selenium has happened. No stack trace information is attached. Please try again.");
			eResponse.setStackTrace(new StackTraceElement[0]);
			response = eResponse;
		}

		return response;
	}

	private static Response processCountWaitCommand(WebDriver driver, CountWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), false);

		switch (command.getWaitType()) {
		case NumberOfElementsToBe:
			response = elementCount(driver, command);
			break;
		case NumberOfElementsToBeLessThan:
			response = elementCountLessThan(driver, command);
			break;
		case NumberOfElementsToBeMoreThan:
			response = elementCountMoreThan(driver, command);
			break;
		case NumberOfWindowsToBe:
			response = windowCount(driver, command);
			break;
		default:
			break;
		}

		return response;
	}

	private static Response processExistenceWaitCommand(WebDriver driver, ExistenceWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), false);

		Class<? extends WaitCommand> waitClass = command.getWaitType().getWaitClass();
		if (waitClass.equals(NestedExistenceWaitCommand.class)) {
			response = processNestedExistenceWaitCommand(driver, (NestedExistenceWaitCommand) command);
		}

		switch (command.getWaitType()) {
		case ElementToBeClickable:
			response = elementClickable(driver, command);
			break;
		case ElementToBeSelected:
			response = elementSelected(driver, command);
			break;
		case FrameToBeAvailableAndSwitchToIt:
			response = frameToBeAvailableSwitch(driver, command);
			break;
		case PresenceOfAllElementsLocatedBy:
			response = presenceOfAllElements(driver, command);
			break;
		case PresenceOfElementLocated:
			response = presenceOfElement(driver, command);
			break;
		case StalenessOf:
			response = staleness(driver, command);
			break;
		default:
			break;
		}

		return response;
	}

	private static Response processNestedExistenceWaitCommand(WebDriver driver, NestedExistenceWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), false);

		switch (command.getWaitType()) {
		case PresenceOfNestedElementLocatedBy:
			response = presenceOfNestedElement(driver, command);
			break;
		case PresenceOfNestedElementsLocatedBy:
			response = presenceOfAllNestedElements(driver, command);
			break;
		default:
			break;
		}

		return response;
	}

	private static Response processInvisibilityWaitCommand(WebDriver driver, InvisibilityWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), false);

		switch (command.getWaitType()) {
		case InvisibilityOf:
			response = invisibilityOf(driver, command);
			break;
		case InvisibilityOfAllElements:
			response = invisibilityOfAll(driver, command);
			break;
		case InvisibilityOfElementWithText:
			response = invisibilityOfElementWithText(driver, command);
			break;
		default:
			break;
		}

		return response;
	}

	private static Response processJavaScriptWaitCommand(WebDriver driver, JavaScriptWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), false);

		switch (command.getWaitType()) {
		case JavascriptReturnsValue:
			response = javascriptReturns(driver, command);
			break;
		case JavascriptThrowsNoExceptions:
			response = javascriptCompletes(driver, command);
			break;
		default:
			break;
		}

		return response;
	}

	private static Response processSelectionStateWaitCommand(WebDriver driver, SelectionStateWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), false);

		switch (command.getWaitType()) {
		case ElementSelectionStateToBe:
			response = elementSelectionState(driver, command);
			break;
		default:
			break;
		}

		return response;
	}

	private static Response processTextMatchWaitCommand(WebDriver driver, TextMatchWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), false);

		Class<? extends WaitCommand> waitClass = command.getWaitType().getWaitClass();
		if (waitClass.equals(TextMatchAttributeSelectorWaitCommand.class)) {
			response = processTextMatchAttributeSelectorWaitCommand(driver,
					(TextMatchAttributeSelectorWaitCommand) command);
		} else if (waitClass.equals(TextMatchSelectorWaitCommand.class)) {
			response = processTextMatchSelectorWaitCommand(driver, (TextMatchSelectorWaitCommand) command);
		}

		switch (command.getWaitType()) {
		case TitleContains:
			response = titleContains(driver, command);
			break;
		case TitleIs:
			response = titleIs(driver, command);
			break;
		case UrlContains:
			response = urlContains(driver, command);
			break;
		case UrlMatches:
			response = urlMatches(driver, command);
			break;
		case UrlToBe:
			response = urlToBe(driver, command);
			break;
		default:
			break;
		}

		return response;
	}

	private static Response processTextMatchSelectorWaitCommand(WebDriver driver, TextMatchSelectorWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), false);

		switch (command.getWaitType()) {
		case TextMatches:
			response = textMatches(driver, command);
			break;
		case TextToBe:
			response = textIs(driver, command);
			break;
		case TextToBePresentInElementLocated:
			response = textPresent(driver, command);
			break;
		case TextToBePresentInElementValue:
			response = textPresentInValue(driver, command);
			break;
		default:
			break;
		}

		return response;
	}

	private static Response processTextMatchAttributeSelectorWaitCommand(WebDriver driver,
			TextMatchAttributeSelectorWaitCommand command) throws WebDriverException, Exception {
		Response response = new Response(command.getId(), false);

		switch (command.getWaitType()) {
		case AttributeContains:
			response = attributeContains(driver, command);
			break;
		case AttributeToBe:
			response = attributeIs(driver, command);
			break;
		case AttributeToBeNotEmpty:
			response = attributeIsNotEmpty(driver, command);
			break;
		default:
			break;
		}

		return response;
	}

	private static Response processVisibilityWaitCommand(WebDriver driver, VisibilityWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), false);

		Class<? extends WaitCommand> waitClass = command.getWaitType().getWaitClass();
		if (waitClass.equals(NestedVisibilityWaitCommand.class)) {
			response = processNestedVisibilityWaitCommand(driver, (NestedVisibilityWaitCommand) command);
		}

		switch (command.getWaitType()) {
		case VisibilityOf:
			response = visibilityOf(driver, command);
			break;
		case VisibilityOfAllElements:
			response = visibilityOfAll(driver, command);
			break;
		default:
			break;
		}

		return response;
	}

	private static Response processNestedVisibilityWaitCommand(WebDriver driver, NestedVisibilityWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), false);

		Class<? extends WaitCommand> waitClass = command.getWaitType().getWaitClass();
		if (waitClass.equals(NestedVisibilityWaitCommand.class)) {
			response = processNestedVisibilityWaitCommand(driver, (NestedVisibilityWaitCommand) command);
		}

		switch (command.getWaitType()) {
		case VisibilityOfNestedElementsLocatedBy:
			response = visibilityOfAllNested(driver, command);
			break;
		default:
			break;
		}

		return response;
	}

	private static Response processWaitCommand(WebDriver driver, WaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), false);

		switch (command.getWaitType()) {
		case AlertIsPresent:
			response = alertIsPresent(driver, command);
			break;
		default:
			break;
		}

		return response;
	}

	private static Response invisibilityOfElementWithText(WebDriver driver, InvisibilityWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.invisibilityOfElementWithText(by, command.getText()));

		return response;
	}

	private static Response invisibilityOfAll(WebDriver driver, InvisibilityWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(by)));

		return response;
	}

	private static Response invisibilityOf(WebDriver driver, InvisibilityWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));

		return response;
	}

	private static Response frameToBeAvailableSwitch(WebDriver driver, ExistenceWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));

		return response;
	}

	private static Response elementClickable(WebDriver driver, ExistenceWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.elementToBeClickable(by));

		return response;
	}

	private static Response elementSelected(WebDriver driver, ExistenceWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.elementToBeSelected(by));

		return response;
	}

	private static Response elementSelectionState(WebDriver driver, SelectionStateWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.elementSelectionStateToBe(by, command.getSelected()));

		return response;
	}

	private static Response presenceOfAllNestedElements(WebDriver driver, NestedExistenceWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By parentBy = BaseProcessor.getBy(command.getSelector());
		By childBy = BaseProcessor.getBy(command.getChildSelector());

		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(parentBy, childBy));

		return response;
	}

	private static Response presenceOfNestedElement(WebDriver driver, NestedExistenceWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By parentBy = BaseProcessor.getBy(command.getSelector());
		By childBy = BaseProcessor.getBy(command.getChildSelector());

		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parentBy, childBy));

		return response;
	}

	private static Response presenceOfElement(WebDriver driver, ExistenceWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.presenceOfElementLocated(by));

		return response;
	}

	private static Response presenceOfAllElements(WebDriver driver, ExistenceWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));

		return response;
	}

	private static Response attributeContains(WebDriver driver, TextMatchAttributeSelectorWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.attributeContains(by, command.getAttribute(), command.getText()));

		return response;
	}

	private static Response attributeIs(WebDriver driver, TextMatchAttributeSelectorWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.attributeToBe(by, command.getAttribute(), command.getText()));

		return response;
	}

	private static Response attributeIsNotEmpty(WebDriver driver, TextMatchAttributeSelectorWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(by), command.getAttribute()));

		return response;
	}

	private static Response javascriptReturns(WebDriver driver, JavaScriptWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.javaScriptThrowsNoExceptions(command.getJavaScript()));

		return response;
	}

	private static Response javascriptCompletes(WebDriver driver, JavaScriptWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.javaScriptThrowsNoExceptions(command.getJavaScript()));

		return response;
	}

	private static Response elementCount(WebDriver driver, CountWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.numberOfElementsToBe(by, command.getBound()));

		return response;
	}

	private static Response elementCountLessThan(WebDriver driver, CountWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.numberOfElementsToBeLessThan(by, command.getBound()));

		return response;
	}

	private static Response elementCountMoreThan(WebDriver driver, CountWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by, command.getBound()));

		return response;
	}

	private static Response windowCount(WebDriver driver, CountWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.numberOfWindowsToBe(command.getBound()));

		return response;
	}

	private static Response staleness(WebDriver driver, ExistenceWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.stalenessOf(driver.findElement(by)));

		return response;
	}

	private static Response textMatches(WebDriver driver, TextMatchSelectorWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		Pattern pattern = Pattern.compile(command.getText());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.textMatches(by, pattern));

		return response;
	}

	private static Response textIs(WebDriver driver, TextMatchSelectorWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.textToBe(by, command.getText()));

		return response;
	}

	private static Response textPresentInValue(WebDriver driver, TextMatchSelectorWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.textToBePresentInElementValue(by, command.getText()));

		return response;
	}

	private static Response textPresent(WebDriver driver, TextMatchSelectorWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.textToBePresentInElementLocated(by, command.getText()));

		return response;
	}

	private static Response visibilityOfAllNested(WebDriver driver, NestedVisibilityWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By parentBy = BaseProcessor.getBy(command.getSelector());
		By childBy = BaseProcessor.getBy(command.getChildSelector());

		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(parentBy, childBy));

		return response;
	}

	private static Response visibilityOfAll(WebDriver driver, VisibilityWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());

		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));

		return response;
	}

	private static Response visibilityOf(WebDriver driver, VisibilityWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());

		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));

		return response;
	}

	private static Response titleIs(WebDriver driver, TextMatchWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.titleIs(command.getText()));

		return response;
	}

	private static Response titleContains(WebDriver driver, TextMatchWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.titleContains(command.getText()));

		return response;
	}

	private static Response urlToBe(WebDriver driver, TextMatchWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.urlToBe(command.getText()));

		return response;
	}

	private static Response urlMatches(WebDriver driver, TextMatchWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.urlMatches(command.getText()));

		return response;
	}

	private static Response urlContains(WebDriver driver, TextMatchWaitCommand command)
			throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.urlContains(command.getText()));

		return response;
	}

	private static Response alertIsPresent(WebDriver driver, WaitCommand command) throws WebDriverException, Exception {
		Response response = new Response(command.getId(), true);

		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
		wait.until(ExpectedConditions.alertIsPresent());

		return response;
	}
}
