package hall.caleb.seltzer.core.processor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hall.caleb.seltzer.objects.command.wait.WaitCommand;
import hall.caleb.seltzer.objects.command.wait.existence.ExistenceWaitCommand;
import hall.caleb.seltzer.objects.command.wait.existence.NestedExistenceWaitCommand;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchWaitCommand;
import hall.caleb.seltzer.objects.response.Response;

public class WaitProcessor {
	static Response processCommand(WebDriver driver, WaitCommand command) {
		Response response = new Response(command.getId(), false);

		switch (command.getWaitType()) {
		case AlertIsPresent:
			response = alertIsPresent(driver, command);
			break;
		case And:
			break;
		case AttributeContains:
			break;
		case AttributeToBe:
			break;
		case AttributeToBeNotEmpty:
			break;
		case ElementSelectionStateToBe:
			break;
		case ElementToBeClickable:
			break;
		case ElementToBeSelected:
			break;
		case FrameToBeAvailableAndSwitchToIt:
			break;
		case InvisibilityOf:
			break;
		case InvisibilityOfAllElements:
			break;
		case InvisibilityOfElementLocated:
			break;
		case InvisibilityOfElementWithText:
			break;
		case JavascriptReturnsValue:
			break;
		case JavascriptThrowsNoExceptions:
			break;
		case Not:
			break;
		case NumberOfElementsToBe:
			break;
		case NumberOfElementsToBeLessThan:
			break;
		case NumberOfElementsToBeMoreThan:
			break;
		case NumberOfWindowsToBe:
			break;
		case Or:
			break;
		case PresenceOfAllElementsLocatedBy:
			break;
		case PresenceOfElementLocated:
			break;
		case PresenceOfNestedElementLocatedBy:
			break;
		case PresenceOfNestedElementsLocatedBy:
			break;
		case StalenessOf:
			break;
		case TextMatches:
			break;
		case TextToBe:
			break;
		case TextToBePresentInElement:
			break;
		case TextToBePresentInElementLocated:
			break;
		case TextToBePresentInElementValue:
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
