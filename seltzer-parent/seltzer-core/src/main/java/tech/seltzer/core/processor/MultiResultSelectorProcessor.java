package tech.seltzer.core.processor;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import tech.seltzer.core.Messages;
import tech.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommandData;
import tech.seltzer.objects.command.selector.multiresult.ReadAttributeCommandData;
import tech.seltzer.objects.response.ExceptionResponse;
import tech.seltzer.objects.response.MultiResultResponse;
import tech.seltzer.objects.response.Response;
import tech.seltzer.objects.response.SingleResultResponse;

/**
 * Processor for <code>MultiResultSelectorCommandData</code> and all subclasses. 
 * Does not delegate to other processors.
 */
public class MultiResultSelectorProcessor {
	static Logger logger = LogManager.getLogger(MultiResultSelectorProcessor.class);

	static Response processCommand(WebDriver driver, MultiResultSelectorCommandData command) {
		Response response = new Response(command.getId(), false);

		int tryNumber = 0;
		while (tryNumber < BaseProcessor.RETRIES) {
			try {
				switch (command.getType()) {
				case READ_ATTRIBUTE:
					response = readAttribute(driver, (ReadAttributeCommandData) command);
					break;
				case READ_TEXT:
					response = readText(driver, command);
					break;
				default:
					response = new MultiResultResponse(command.getId(), false);
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
		
		return response;
	}

	private static Response readAttribute(WebDriver driver, ReadAttributeCommandData command)
			throws WebDriverException, Exception {
		List<String> attributes = Arrays.asList(command.getAttribute().split("/"));

		MultiResultResponse mrResponse = new MultiResultResponse(command.getId(), true);
		SingleResultResponse srResponse = new SingleResultResponse(command.getId(), true);

		By selector = BaseProcessor.getBy(command.getSelector());
		List<WebElement> elements = null;
		int maxResults = -1;

		elements = driver.findElements(selector);

		if (elements == null) {
			throw new WebDriverException(Messages.getString("MultiResultSelectorProcessor.nullList"));
		} else {
			maxResults = elements.size();
		}

		maxResults = (command.getMaxResults() <= 0 ? maxResults : Math.min(maxResults, command.getMaxResults()));
		if (maxResults < 0) {
			return new MultiResultResponse(command.getId(), false);
		}

		String tmpResult = null;
		if (command.getMaxResults() == 1) {
			tmpResult = elements.get(0).getText();

			if (tmpResult == null) {
				srResponse.setSuccess(false);
			} else {
				srResponse.setResult(tmpResult);
			}

			return srResponse;
		} else {
			for (int i = 0; i < maxResults; i++) {
				for (String attribute : attributes) {
					tmpResult = elements.get(i).getAttribute(attribute.trim());

					if (tmpResult != null && !tmpResult.isEmpty()) {
						break;
					}
				}

				if (tmpResult != null && !tmpResult.isEmpty()) {
					mrResponse.getResults().add(tmpResult);
					tmpResult = null;
				}
			}

			return mrResponse;
		}
	}

	private static Response readText(WebDriver driver, MultiResultSelectorCommandData command)
			throws WebDriverException, Exception {
		MultiResultResponse mrResponse = new MultiResultResponse(command.getId(), true);
		SingleResultResponse srResponse = new SingleResultResponse(command.getId(), true);

		By selector = BaseProcessor.getBy(command.getSelector());
		List<WebElement> elements = null;
		int maxResults = -1;

		elements = driver.findElements(selector);

		if (elements == null) {
			throw new WebDriverException(Messages.getString("MultiResultSelectorProcessor.nullList"));
		} else {
			maxResults = elements.size();
		}

		maxResults = (command.getMaxResults() <= 0 ? maxResults : Math.min(maxResults, command.getMaxResults()));
		if (maxResults < 0) {
			return new MultiResultResponse(command.getId(), false);
		}

		String tmpResult = null;
		if (command.getMaxResults() == 1) {
			tmpResult = elements.get(0).getText();

			if (tmpResult == null) {
				srResponse.setSuccess(false);
			} else {
				srResponse.setResult(tmpResult);
			}

			return srResponse;
		} else {
			for (int i = 0; i < maxResults; i++) {
				tmpResult = elements.get(i).getText();

				if (tmpResult == null) {
					mrResponse.setSuccess(false);
					break;
				} else {
					mrResponse.getResults().add(tmpResult);
					tmpResult = null;
				}
			}

			return mrResponse;
		}
	}
}
