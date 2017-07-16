package hall.caleb.seltzer.core.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.objects.command.selector.FillFieldCommandData;
import hall.caleb.seltzer.objects.command.selector.SelectorCommandData;
import hall.caleb.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommandData;
import hall.caleb.seltzer.objects.response.ExceptionResponse;
import hall.caleb.seltzer.objects.response.Response;
import hall.caleb.seltzer.objects.response.SingleResultResponse;

public class SelectorProcessor {
	static Logger logger = LogManager.getLogger(SelectorProcessor.class);

	static Response processCommand(WebDriver driver, SelectorCommandData command) {
		Response response = new Response(command.getId(), false);

		if (command instanceof MultiResultSelectorCommandData) {
			response = MultiResultSelectorProcessor.processCommand(driver, (MultiResultSelectorCommandData) command);
		} else {
			int tryNumber = 0;
			while (tryNumber < BaseProcessor.RETRIES) {
				try {
					switch (command.getType()) {
					case CLICK:
						response = click(driver, command);
						break;
					case COUNT:
						response = count(driver, command);
						break;
					case DELETE:
						response = delete(driver, command);
						break;
					case FILL_FIELD:
						response = fillField(driver, (FillFieldCommandData) command);
						break;
					case FORM_SUBMIT:
						response = formSubmit(driver, command);
						break;
					default:
						response = new Response(command.getId(), false);
						break;
					}
				} catch (WebDriverException e) {
					logger.error(e);
					tryNumber++;
					ExceptionResponse eResponse = new ExceptionResponse(command.getId());
					eResponse.setMessage(e.getMessage());
					eResponse.setStackTrace(e.getStackTrace());
					response = eResponse;
					BaseProcessor.sleep(e, tryNumber);
				} catch (Exception e) {
					logger.error(e);
					tryNumber++;
					ExceptionResponse eResponse = new ExceptionResponse(command.getId());
					eResponse.setMessage(Messages.getString("BaseProcessor.exception"));
					eResponse.setStackTrace(new StackTraceElement[0]);
					response = eResponse;
					BaseProcessor.sleep(e, tryNumber);
				}
			}
		}

		return response;
	}

	private static Response click(WebDriver driver, SelectorCommandData command) throws WebDriverException {
		Response response = new Response(command.getId(), false);

		driver.findElement(BaseProcessor.getBy(command.getSelector())).click();
		response.setSuccess(true);

		return response;
	}

	private static SingleResultResponse count(WebDriver driver, SelectorCommandData command) throws NoSuchElementException {
		SingleResultResponse response = new SingleResultResponse(command.getId());

		Integer size = driver.findElements(BaseProcessor.getBy(command.getSelector())).size();
		response.setResult(size.toString());
		response.setSuccess(size.toString().equals(response.getResult()));

		return response;
	}

	private static Response delete(WebDriver driver, SelectorCommandData command) {
		Response response = new Response(command.getId(), false);
		if (command.getSelector().getType() == SelectorType.XPATH && driver instanceof JavascriptExecutor) {
			String selector = command.getSelector().getPath().replace("\"", "\\\"");

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

	private static Response fillField(WebDriver driver, FillFieldCommandData command) throws WebDriverException {
		Response response = new Response(command.getId(), false);

		WebElement field = driver.findElement(BaseProcessor.getBy(command.getSelector()));
		field.click();
		field.sendKeys(command.getText());
		response.setSuccess(true);

		return response;
	}

	private static Response formSubmit(WebDriver driver, SelectorCommandData command) throws WebDriverException {
		Response response = new Response(command.getId(), false);

		WebElement form = driver.findElement(BaseProcessor.getBy(command.getSelector()));
		form.submit();
		response.setSuccess(true);

		return response;
	}
}
