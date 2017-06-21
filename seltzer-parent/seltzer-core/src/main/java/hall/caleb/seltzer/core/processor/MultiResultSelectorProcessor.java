package hall.caleb.seltzer.core.processor;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import hall.caleb.seltzer.objects.command.MultiResultSelectorCommand;
import hall.caleb.seltzer.objects.command.ReadAttributeCommand;
import hall.caleb.seltzer.objects.response.MultiResultResponse;
import hall.caleb.seltzer.objects.response.Response;

public class MultiResultSelectorProcessor {
	static Response processCommand(WebDriver driver, MultiResultSelectorCommand command) {
		Response response;

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

		return response;
	}

	private static MultiResultResponse readAttribute(WebDriver driver, ReadAttributeCommand command) {
		List<String> attributes = Arrays.asList(command.getAttribute().split("/"));

		MultiResultResponse response = new MultiResultResponse(command.getId(), true);

		int tryNumber = 0;

		By selector = BaseProcessor.getSelector(command);
		int maxResults = -1;
		while (tryNumber < BaseProcessor.RETRIES) {
			try {
				maxResults = driver.findElements(selector).size();
				break;
			} catch (NoSuchElementException e) {
				tryNumber++;
				BaseProcessor.sleep(e, tryNumber);
			}
		}

		maxResults = (command.getMaxResults() <= 0 ? maxResults : Math.min(maxResults, command.getMaxResults()));
		if (maxResults < 0) {
			return new MultiResultResponse(command.getId(), false);
		}

		tryNumber = 0;

		String tmpResult = null;
		for (int i = 0; i < maxResults; i++) {
			while (tryNumber < BaseProcessor.RETRIES) {
				try {
					for (String attribute : attributes) {
						tmpResult = driver.findElements(BaseProcessor.getSelector(command)).get(i)
								.getAttribute(attribute.trim());

						if (tmpResult != null && !tmpResult.isEmpty()) {
							break;
						}
					}
					break;
				} catch (NoSuchElementException e) {
					tryNumber++;
					BaseProcessor.sleep(e, tryNumber);
				}
			}

			if (tmpResult != null && !tmpResult.isEmpty()) {
				response.getResults().add(tmpResult);
				tmpResult = null;
			}
		}

		return response;
	}

	private static MultiResultResponse readText(WebDriver driver, MultiResultSelectorCommand command) {
		MultiResultResponse response = new MultiResultResponse(command.getId(), true);

		int tryNumber = 0;

		By selector = BaseProcessor.getSelector(command);
		int maxResults = -1;
		while (tryNumber < BaseProcessor.RETRIES) {
			try {
				maxResults = driver.findElements(selector).size();
				break;
			} catch (NoSuchElementException e) {
				tryNumber++;
				BaseProcessor.sleep(e, tryNumber);
			}
		}

		maxResults = (command.getMaxResults() <= 0 ? maxResults : Math.min(maxResults, command.getMaxResults()));
		if (maxResults < 0) {
			return new MultiResultResponse(command.getId(), false);
		}

		tryNumber = 0;

		String tmpResult = null;
		for (int i = 0; i < maxResults; i++) {
			while (tryNumber < BaseProcessor.RETRIES) {
				try {
					tmpResult = driver.findElements(BaseProcessor.getSelector(command)).get(i).getText();
					break;
				} catch (NoSuchElementException e) {
					tryNumber++;
					BaseProcessor.sleep(e, tryNumber);
				}
			}

			if (tmpResult == null) {
				response.setSuccess(false);
				break;
			} else {
				response.getResults().add(tmpResult);
				tmpResult = null;
			}
		}

		return response;
	}
}
