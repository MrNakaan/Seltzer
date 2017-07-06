package hall.caleb.seltzer.core.processor;

import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
import hall.caleb.seltzer.objects.response.Response;

public class WaitProcessor {
	static Response processCommand(WebDriver driver, WaitCommand command) {
		Response response = new Response(command.getId(), false);

		switch (command.getWaitType()) {
		case AlertIsPresent:
			response = alertIsPresent(driver, command);
			break;
		// case And:
		// break;
		case AttributeContains:
			response = attributeContains(driver, (TextMatchAttributeSelectorWaitCommand) command);
			break;
		case AttributeToBe:
			response = attributeIs(driver, (TextMatchAttributeSelectorWaitCommand) command);
			break;
		case AttributeToBeNotEmpty:
			response = attributeIsNotEmpty(driver, (TextMatchAttributeSelectorWaitCommand) command);
			break;
		case ElementSelectionStateToBe:
			response = elementSelectionState(driver, (SelectionStateWaitCommand) command);
			break;
		case ElementToBeClickable:
			response = elementClickable(driver, (ExistenceWaitCommand) command);
			break;
		case ElementToBeSelected:
			response = elementSelected(driver, (ExistenceWaitCommand) command);
			break;
		case FrameToBeAvailableAndSwitchToIt:
			response = frameToBeAvailableSwitch(driver, (ExistenceWaitCommand) command);
			break;
		case InvisibilityOf:
			response = invisibilityOf(driver, (InvisibilityWaitCommand) command);
			break;
		case InvisibilityOfAllElements:
			response = invisibilityOfAll(driver, (InvisibilityWaitCommand) command);
			break;
		case InvisibilityOfElementWithText:
			response = invisibilityOfElementWithText(driver, (InvisibilityWaitCommand) command);
			break;
		case JavascriptReturnsValue:
			response = javascriptReturns(driver, (JavaScriptWaitCommand) command);
			break;
		case JavascriptThrowsNoExceptions:
			response = javascriptCompletes(driver, (JavaScriptWaitCommand) command);
			break;
		// case Not:
		// break;
		case NumberOfElementsToBe:
			response = elementCount(driver, (CountWaitCommand) command);
			break;
		case NumberOfElementsToBeLessThan:
			response = elementCountLessThan(driver, (CountWaitCommand) command);
			break;
		case NumberOfElementsToBeMoreThan:
			response = elementCountMoreThan(driver, (CountWaitCommand) command);
			break;
		case NumberOfWindowsToBe:
			response = windowCount(driver, (CountWaitCommand) command);
			break;
		// case Or:
		// break;
		case PresenceOfAllElementsLocatedBy:
			response = presenceOfAllElements(driver, (ExistenceWaitCommand) command);
			break;
		case PresenceOfElementLocated:
			response = presenceOfElement(driver, (ExistenceWaitCommand) command);
			break;
		case PresenceOfNestedElementLocatedBy:
			response = presenceOfNestedElement(driver, (NestedExistenceWaitCommand) command);
			break;
		case PresenceOfNestedElementsLocatedBy:
			response = presenceOfAllNestedElements(driver, (NestedExistenceWaitCommand) command);
			break;
		case StalenessOf:
			response = staleness(driver, (ExistenceWaitCommand) command);
			break;
		case TextMatches:
			response = textMatches(driver, (TextMatchSelectorWaitCommand) command);
			break;
		case TextToBe:
			response = textIs(driver, (TextMatchSelectorWaitCommand) command);
			break;
		case TextToBePresentInElementLocated:
			response = textPresent(driver, (TextMatchSelectorWaitCommand) command);
			break;
		case TextToBePresentInElementValue:
			response = textPresentInValue(driver, (TextMatchSelectorWaitCommand) command);
			break;
		case TitleContains:
			response = titleContains(driver, (TextMatchWaitCommand) command);
			break;
		case TitleIs:
			response = titleIs(driver, (TextMatchWaitCommand) command);
			break;
		case UrlContains:
			response = urlContains(driver, (TextMatchWaitCommand) command);
			break;
		case UrlMatches:
			response = urlMatches(driver, (TextMatchWaitCommand) command);
			break;
		case UrlToBe:
			response = urlToBe(driver, (TextMatchWaitCommand) command);
			break;
		case VisibilityOf:
			response = visibilityOf(driver, (ExistenceWaitCommand) command);
			break;
		case VisibilityOfAllElements:
			response = visibilityOfAll(driver, (ExistenceWaitCommand) command);
			break;
		case VisibilityOfNestedElementsLocatedBy:
			response = visibilityOfAllNested(driver, (NestedExistenceWaitCommand) command);
			break;
		default:
			break;
		}

		return response;
	}

	private static Response invisibilityOfElementWithText(WebDriver driver, InvisibilityWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.invisibilityOfElementWithText(by, command.getText()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response invisibilityOfAll(WebDriver driver, InvisibilityWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(by)));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response invisibilityOf(WebDriver driver, InvisibilityWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response frameToBeAvailableSwitch(WebDriver driver, ExistenceWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response elementClickable(WebDriver driver, ExistenceWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response elementSelected(WebDriver driver, ExistenceWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.elementToBeSelected(by));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response elementSelectionState(WebDriver driver, SelectionStateWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.elementSelectionStateToBe(by, command.getSelected()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response presenceOfAllNestedElements(WebDriver driver, NestedExistenceWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By parentBy = BaseProcessor.getBy(command.getSelector());
			By childBy = BaseProcessor.getBy(command.getChildSelector());

			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(parentBy, childBy));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response presenceOfNestedElement(WebDriver driver, NestedExistenceWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By parentBy = BaseProcessor.getBy(command.getSelector());
			By childBy = BaseProcessor.getBy(command.getChildSelector());

			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parentBy, childBy));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response presenceOfElement(WebDriver driver, ExistenceWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response presenceOfAllElements(WebDriver driver, ExistenceWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response attributeContains(WebDriver driver, TextMatchAttributeSelectorWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.attributeContains(by, command.getAttribute(), command.getText()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response attributeIs(WebDriver driver, TextMatchAttributeSelectorWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.attributeToBe(by, command.getAttribute(), command.getText()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response attributeIsNotEmpty(WebDriver driver, TextMatchAttributeSelectorWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(by), command.getAttribute()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response javascriptReturns(WebDriver driver, JavaScriptWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.javaScriptThrowsNoExceptions(command.getJavaScript()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response javascriptCompletes(WebDriver driver, JavaScriptWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.javaScriptThrowsNoExceptions(command.getJavaScript()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response elementCount(WebDriver driver, CountWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.numberOfElementsToBe(by, command.getBound()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response elementCountLessThan(WebDriver driver, CountWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.numberOfElementsToBeLessThan(by, command.getBound()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response elementCountMoreThan(WebDriver driver, CountWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by, command.getBound()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response windowCount(WebDriver driver, CountWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.numberOfWindowsToBe(command.getBound()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response staleness(WebDriver driver, ExistenceWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.stalenessOf(driver.findElement(by)));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response textMatches(WebDriver driver, TextMatchSelectorWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			Pattern pattern = Pattern.compile(command.getText());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.textMatches(by, pattern));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response textIs(WebDriver driver, TextMatchSelectorWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.textToBe(by, command.getText()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response textPresentInValue(WebDriver driver, TextMatchSelectorWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.textToBePresentInElementValue(by, command.getText()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response textPresent(WebDriver driver, TextMatchSelectorWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.textToBePresentInElementLocated(by, command.getText()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response visibilityOfAllNested(WebDriver driver, NestedExistenceWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By parentBy = BaseProcessor.getBy(command.getSelector());
			By childBy = BaseProcessor.getBy(command.getChildSelector());

			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(parentBy, childBy));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response visibilityOfAll(WebDriver driver, ExistenceWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());

			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response visibilityOf(WebDriver driver, ExistenceWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			By by = BaseProcessor.getBy(command.getSelector());

			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response titleIs(WebDriver driver, TextMatchWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.titleIs(command.getText()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response titleContains(WebDriver driver, TextMatchWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.titleContains(command.getText()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response urlToBe(WebDriver driver, TextMatchWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.urlToBe(command.getText()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response urlMatches(WebDriver driver, TextMatchWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.urlMatches(command.getText()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response urlContains(WebDriver driver, TextMatchWaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.urlContains(command.getText()));
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}

	private static Response alertIsPresent(WebDriver driver, WaitCommand command) {
		Response response = new Response(command.getId(), true);

		try {
			WebDriverWait wait = new WebDriverWait(driver, command.getSeconds());
			wait.until(ExpectedConditions.alertIsPresent());
		} catch (Exception e) {
			response.setSuccess(false);
		}

		return response;
	}
}
