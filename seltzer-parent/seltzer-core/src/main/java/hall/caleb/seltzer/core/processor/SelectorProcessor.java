package hall.caleb.seltzer.core.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.objects.command.selector.FillFieldCommand;
import hall.caleb.seltzer.objects.command.selector.SelectorCommand;
import hall.caleb.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommand;
import hall.caleb.seltzer.objects.response.ExceptionResponse;
import hall.caleb.seltzer.objects.response.Response;
import hall.caleb.seltzer.objects.response.SingleResultResponse;

public class SelectorProcessor {
	static Logger logger = LogManager.getLogger(SelectorProcessor.class);

	static Response processCommand(WebDriver driver, SelectorCommand command) {
		Response response = new Response(command.getId(), false);

		if (command instanceof MultiResultSelectorCommand) {
			response = MultiResultSelectorProcessor.processCommand(driver, (MultiResultSelectorCommand) command);
		} else {
			int tryNumber = 0;
			while (tryNumber < BaseProcessor.RETRIES) {
				try {
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
				} catch (WebDriverException e) {
					logger.error(e);
					tryNumber++;
					ExceptionResponse eResponse = new ExceptionResponse(command.getId(), false);
					eResponse.setMessage(e.getMessage());
					eResponse.setStackTrace(e.getStackTrace());
					response = eResponse;
					BaseProcessor.sleep(e, tryNumber);
				} catch (Exception e) {
					logger.error(e);
					tryNumber++;
					ExceptionResponse eResponse = new ExceptionResponse(command.getId(), false);
					eResponse.setMessage(Messages.getString("BaseProcessor.exception"));
					eResponse.setStackTrace(new StackTraceElement[0]);
					response = eResponse;
					BaseProcessor.sleep(e, tryNumber);
				}
			}
		}

		return response;
	}

	private static Response click(WebDriver driver, SelectorCommand command) throws WebDriverException {
		Response response = new Response(command.getId(), false);

		driver.findElement(BaseProcessor.getBy(command.getSelector())).click();
		response.setSuccess(true);

		return response;
	}

	private static SingleResultResponse count(WebDriver driver, SelectorCommand command) throws NoSuchElementException {
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
			removeScript.append(Messages.getString("SelectorProcessor.js1"));
			removeScript.append(Messages.getString("SelectorProcessor.js2"));
			removeScript.append(selector);
			removeScript.append(Messages.getString("SelectorProcessor.js3"));
			removeScript.append(Messages.getString("SelectorProcessor.js4"));
			removeScript.append(Messages.getString("SelectorProcessor.js5"));
			removeScript.append(Messages.getString("SelectorProcessor.js6"));
			removeScript.append(Messages.getString("SelectorProcessor.js7"));
			removeScript.append(Messages.getString("SelectorProcessor.js8"));
			removeScript.append(Messages.getString("SelectorProcessor.js7"));

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript(removeScript.toString());

			response.setSuccess(true);
		}

		return response;
	}

	private static Response fillField(WebDriver driver, FillFieldCommand command) throws WebDriverException {
		Response response = new Response(command.getId(), false);

		WebElement field = driver.findElement(BaseProcessor.getBy(command.getSelector()));
		field.click();
		field.sendKeys(command.getText());
		response.setSuccess(true);

		return response;
	}

	private static Response formSubmit(WebDriver driver, SelectorCommand command) throws WebDriverException {
		Response response = new Response(command.getId(), false);

		WebElement form = driver.findElement(BaseProcessor.getBy(command.getSelector()));
		form.submit();
		response.setSuccess(true);

		return response;
	}
}
