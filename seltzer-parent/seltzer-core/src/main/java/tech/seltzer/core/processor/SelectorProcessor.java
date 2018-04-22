package tech.seltzer.core.processor;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import tech.seltzer.core.Messages;
import tech.seltzer.core.SeltzerSession;
import tech.seltzer.enums.SelectorType;
import tech.seltzer.objects.command.Selector;
import tech.seltzer.objects.command.selector.FillFieldCommandData;
import tech.seltzer.objects.command.selector.SelectorCommandData;
import tech.seltzer.objects.command.selector.SendKeyCommandData;
import tech.seltzer.objects.command.selector.SendKeysCommandData;
import tech.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommandData;
import tech.seltzer.objects.response.ExceptionResponse;
import tech.seltzer.objects.response.Response;
import tech.seltzer.objects.response.SingleResultResponse;

/**
 * Processor for <code>SelectorCommandData</code> and all subclasses. Delegates
 * to <code>MultiResultSelectorProcessor</code> as appropriate.
 */
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
					case SEND_KEY:
						response = sendKey(driver, (SendKeyCommandData) command);
						break;
					case SEND_KEYS:
						response = sendKeys(driver, (SendKeysCommandData) command);
						break;
					case CACHE:
						response = cache(driver, command);
						break;
					// Removed for now since only Edge supports it
					// case SCREENSHOT_ELEMENT:
					// response = takeScreenshot(driver, command);
					// break;
					default:
						response = new Response(command.getId(), false);
						break;
					}
					break;
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

		BaseProcessor.getElements(command, command.getSelector()).get(0).click();
		response.setSuccess(true);

		return response;
	}

	private static SingleResultResponse count(WebDriver driver, SelectorCommandData command)
			throws NoSuchElementException {
		SingleResultResponse response = new SingleResultResponse(command.getId());

		Integer size = BaseProcessor.getElements(command, command.getSelector()).size();
		response.setResult(size.toString());
		response.setSuccess(size.toString().equals(response.getResult()));

		return response;
	}

	private static Response delete(WebDriver driver, SelectorCommandData command) {
		Response response = new Response(command.getId(), false);
		if (driver instanceof JavascriptExecutor) {
			Selector convertedSelector = convert(command.getId(), command.getSelector());

			if (convertedSelector.getType() == SelectorType.XPATH) {
				String selector = convertedSelector.getPath().replace("\"", "\\\"");

				StringBuilder removeScript = new StringBuilder();
				removeScript.append(Messages.getString("SelectorProcessor.js.xpath1"));
				removeScript.append(Messages.getString("SelectorProcessor.js.xpath2"));
				removeScript.append(selector);
				removeScript.append(Messages.getString("SelectorProcessor.js.xpath3"));
				removeScript.append(Messages.getString("SelectorProcessor.js.xpath4"));
				removeScript.append(Messages.getString("SelectorProcessor.js.xpath5"));
				removeScript.append(Messages.getString("SelectorProcessor.js.xpath6"));
				removeScript.append(Messages.getString("SelectorProcessor.js.xpath7"));
				removeScript.append(Messages.getString("SelectorProcessor.js.xpath8"));
				removeScript.append(Messages.getString("SelectorProcessor.js.xpath7"));

				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript(removeScript.toString());

				response.setSuccess(true);
			} else if (convertedSelector.getType() == SelectorType.CSS_SELECTOR) {
				StringBuilder removeScript = new StringBuilder();
				removeScript.append(Messages.getString("SelectorProcessor.js.css1"));
				removeScript.append(convertedSelector.getPath());
				removeScript.append(Messages.getString("SelectorProcessor.js.css2"));
				removeScript.append(Messages.getString("SelectorProcessor.js.css3"));
				removeScript.append(Messages.getString("SelectorProcessor.js.css4"));
				removeScript.append(Messages.getString("SelectorProcessor.js.css5"));

				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript(removeScript.toString());

				response.setSuccess(true);
			}
		}

		return response;
	}

	private static Response fillField(WebDriver driver, FillFieldCommandData command) throws WebDriverException {
		Response response = new Response(command.getId(), false);

		WebElement field = BaseProcessor.getElements(command, command.getSelector()).get(0);
		field.click();
		field.sendKeys(command.getText());
		response.setSuccess(true);

		return response;
	}

	private static Response formSubmit(WebDriver driver, SelectorCommandData command) throws WebDriverException {
		Response response = new Response(command.getId(), false);

		WebElement form = BaseProcessor.getElements(command, command.getSelector()).get(0);
		form.submit();
		response.setSuccess(true);

		return response;
	}

	private static Response sendKey(WebDriver driver, SendKeyCommandData command) {
		Response response = new Response(command.getId(), true);

		WebElement input = BaseProcessor.getElements(command, command.getSelector()).get(0);
		String keyName = command.getKey().toString().toUpperCase();
		input.sendKeys(Keys.valueOf(keyName));

		return response;
	}

	private static Response sendKeys(WebDriver driver, SendKeysCommandData command) {
		Response response = new Response(command.getId(), true);

		WebElement input = BaseProcessor.getElements(command, command.getSelector()).get(0);
		input.sendKeys(command.getKeys());

		return response;
	}
	
	private static Response cache(WebDriver driver, SelectorCommandData command) {
		SingleResultResponse response = new SingleResultResponse(command.getId(), true);

		By by = BaseProcessor.getBy(command.getSelector());
		WebElement element = driver.findElement(by);
		
		SeltzerSession session = SeltzerSession.findSession(command.getId());
		Integer index = session.cacheWebElement(command.getSelector(), element);
		response.setResult(index.toString());
		
		return response;
	}

	@SuppressWarnings("unused")
	private static Response takeScreenshot(WebDriver driver, SelectorCommandData command)
			throws WebDriverException, Exception {
		WebElement element = BaseProcessor.getElements(command, command.getSelector()).get(0);

		String screenshot = ((TakesScreenshot) element).getScreenshotAs(OutputType.BASE64);

		SingleResultResponse response = new SingleResultResponse(command.getId(), true);
		response.setResult(screenshot);

		return response;
	}

	private static Selector convert(UUID id, Selector selector) {
		if (selector.getType() == SelectorType.INDEX) {
			selector = SeltzerSession.findSession(id).getCachedSelectionSelector(Integer.valueOf(selector.getPath()));
		}

		switch (selector.getType()) {
		case XPATH:
		case CSS_SELECTOR:
			return selector;
		case TAG_NAME:
		case ID:
		case LINK_TEXT:
		case PARTIAL_LINK_TEXT:
		case NAME:
			return convertToXpath(selector);
		case CLASS_NAME:
			return convertToCssSelector(selector);
		default:
			return new Selector();
		}
	}

	private static Selector convertToXpath(Selector selector) {
		Selector convertedSelector = new Selector(SelectorType.XPATH, "");

		switch (selector.getType()) {
		case TAG_NAME:
			convertedSelector.setPath("//" + selector.getPath());
			break;
		case ID:
			convertedSelector.setPath("//*[@id='" + selector.getPath() + "']");
			break;
		case LINK_TEXT:
			convertedSelector.setPath("//a[text()='" + selector.getPath() + "']");
			break;
		case PARTIAL_LINK_TEXT:
			convertedSelector.setPath("//a[contains(text(),'" + selector.getPath() + "')]");
			break;
		case NAME:
			convertedSelector.setPath("//*[@name='" + selector.getPath() + "']");
			break;
		default:
			convertedSelector.setType(SelectorType.NONE);
			break;
		}

		return convertedSelector;
	}

	private static Selector convertToCssSelector(Selector selector) {
		Selector convertedSelector = new Selector(SelectorType.CSS_SELECTOR, "");

		switch (selector.getType()) {
		case CLASS_NAME:
			convertedSelector.setPath("." + selector.getPath());
			break;
		default:
			convertedSelector.setType(SelectorType.NONE);
			break;
		}

		return convertedSelector;
	}
}
