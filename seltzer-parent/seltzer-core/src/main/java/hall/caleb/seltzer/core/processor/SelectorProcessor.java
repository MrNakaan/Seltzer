package hall.caleb.seltzer.core.processor;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.objects.command.selector.FillFieldCommand;
import hall.caleb.seltzer.objects.command.selector.SelectorCommand;
import hall.caleb.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommand;
import hall.caleb.seltzer.objects.response.Response;
import hall.caleb.seltzer.objects.response.SingleResultResponse;

public class SelectorProcessor {
	static Response processCommand(WebDriver driver, SelectorCommand command) {
		Response response;

		if (command instanceof MultiResultSelectorCommand) {
			response = MultiResultSelectorProcessor.processCommand(driver, (MultiResultSelectorCommand) command);
		} else {
			switch (command.getType()) {
			case Click:
				response = click(driver, command);
				break;
			case Count:
				response = count(driver, command);
				break;
			case Delete:
				response = delete(driver, command);
				break;
			case FillField:
				response = fillField(driver, (FillFieldCommand) command);
				break;
			case FormSubmit:
				response = formSubmit(driver, command);
				break;
			default:
				response = new Response(command.getId(), false);
				break;
			}
		}

		return response;
	}

	private static Response click(WebDriver driver, SelectorCommand command) {
		int tryNumber = 0;

		Response response = new Response(command.getId(), false);

		while (tryNumber < BaseProcessor.RETRIES) {
			try {
				driver.findElement(BaseProcessor.getBy(command.getSelector())).click();
				response.setSuccess(true);
				break;
			} catch (NoSuchElementException | StaleElementReferenceException e) {
				tryNumber++;
				BaseProcessor.sleep(e, tryNumber);
			}
		}

		return response;
	}

	private static SingleResultResponse count(WebDriver driver, SelectorCommand command) {
		SingleResultResponse response = new SingleResultResponse(command.getId());

		Integer size = driver.findElements(BaseProcessor.getBy(command.getSelector())).size();
		response.setResult(size.toString());
		response.setSuccess(size.toString().equals(response.getResult()));

		return response;
	}

	private static Response delete(WebDriver driver, SelectorCommand command) {
		Response response = new Response(command.getId(), false);
		if (command.getSelector().getSelectorType() == SelectorType.Xpath && driver instanceof JavascriptExecutor) {
			String selector = command.getSelector().getSelector().replace("\"", "\\\"");

			StringBuilder removeScript = new StringBuilder();
			removeScript.append("while (true) {");
			removeScript.append("e = document.evaluate(\"");
			removeScript.append(selector);
			removeScript.append("\", document.documentElement); ");
			removeScript.append("e = e.iterateNext(); ");
			removeScript.append("if (e == null) {");
			removeScript.append("break;");
			removeScript.append("}");
			removeScript.append("e.parentNode.removeChild(e);");
			removeScript.append("}");

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript(removeScript.toString());

			response.setSuccess(true);
		}

		return response;
	}

	private static Response fillField(WebDriver driver, FillFieldCommand command) {
		int tryNumber = 0;

		Response response = new Response(command.getId(), false);

		while (tryNumber < BaseProcessor.RETRIES) {
			try {
				WebElement field = driver.findElement(BaseProcessor.getBy(command.getSelector()));
				field.click();
				field.sendKeys(command.getText());
				response.setSuccess(true);
				break;
			} catch (NoSuchElementException | StaleElementReferenceException e) {
				tryNumber++;
				BaseProcessor.sleep(e, tryNumber);
			}
		}

		return response;
	}

	private static Response formSubmit(WebDriver driver, SelectorCommand command) {
		int tryNumber = 0;

		Response response = new Response(command.getId(), false);

		while (tryNumber < BaseProcessor.RETRIES) {
			try {
				WebElement form = driver.findElement(BaseProcessor.getBy(command.getSelector()));
				form.submit();
				response.setSuccess(true);
				break;
			} catch (NoSuchElementException e) {
				tryNumber++;
				BaseProcessor.sleep(e, tryNumber);
			}
		}

		return response;
	}
}
