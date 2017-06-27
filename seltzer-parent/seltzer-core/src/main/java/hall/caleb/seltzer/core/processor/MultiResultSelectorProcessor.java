package hall.caleb.seltzer.core.processor;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import hall.caleb.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommand;
import hall.caleb.seltzer.objects.command.selector.multiresult.ReadAttributeCommand;
import hall.caleb.seltzer.objects.response.ExceptionResponse;
import hall.caleb.seltzer.objects.response.MultiResultResponse;
import hall.caleb.seltzer.objects.response.Response;
import hall.caleb.seltzer.objects.response.SingleResultResponse;

public class MultiResultSelectorProcessor {
	static Response processCommand(WebDriver driver, MultiResultSelectorCommand command) {
		Response response;

		try {
			switch (command.getType()) {
			case ReadAttribute:
				response = readAttribute(driver, (ReadAttributeCommand) command);
				break;
			case ReadText:
				response = readText(driver, command);
				break;
			default:
				response = new MultiResultResponse(command.getId(), false);
				break;
			}
		} catch (WebDriverException e) {
			ExceptionResponse eResponse = new ExceptionResponse(command.getId(), false);
			eResponse.setMessage(e.getMessage());
			eResponse.setStackTrace(e.getStackTrace());
			response = eResponse;
		}

		return response;
	}

	private static Response readAttribute(WebDriver driver, ReadAttributeCommand command)
			throws WebDriverException {
		List<String> attributes = Arrays.asList(command.getAttribute().split("/"));

		MultiResultResponse mrResponse = new MultiResultResponse(command.getId(), true);
		SingleResultResponse srResponse = new SingleResultResponse(command.getId(), true);

		int tryNumber = 0;

		By selector = BaseProcessor.getBy(command.getSelector());
		List<WebElement> elements = null;
		NoSuchElementException lastException = null;
		int maxResults = -1;
		while (tryNumber < BaseProcessor.RETRIES) {
			try {
				elements = driver.findElements(selector);
				break;
			} catch (NoSuchElementException e) {
				tryNumber++;
				lastException = e;
				BaseProcessor.sleep(e, tryNumber);
			}
		}

		if (elements == null) {
			throw lastException;
		} else {
			maxResults = elements.size();
		}

		maxResults = (command.getMaxResults() <= 0 ? maxResults : Math.min(maxResults, command.getMaxResults()));
		if (maxResults < 0) {
			return new MultiResultResponse(command.getId(), false);
		}

		tryNumber = 0;

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

	private static Response readText(WebDriver driver, MultiResultSelectorCommand command)
			throws WebDriverException {
		MultiResultResponse mrResponse = new MultiResultResponse(command.getId(), true);
		SingleResultResponse srResponse = new SingleResultResponse(command.getId(), true);

		int tryNumber = 0;

		By selector = BaseProcessor.getBy(command.getSelector());
		List<WebElement> elements = null;
		NoSuchElementException lastException = null;
		int maxResults = -1;
		while (tryNumber < BaseProcessor.RETRIES) {
			try {
				elements = driver.findElements(selector);
				break;
			} catch (NoSuchElementException e) {
				tryNumber++;
				lastException = e;
				BaseProcessor.sleep(e, tryNumber);
			}
		}

		if (elements == null) {
			throw lastException;
		} else {
			maxResults = elements.size();
		}

		maxResults = (command.getMaxResults() <= 0 ? maxResults : Math.min(maxResults, command.getMaxResults()));
		if (maxResults < 0) {
			return new MultiResultResponse(command.getId(), false);
		}

		tryNumber = 0;

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
