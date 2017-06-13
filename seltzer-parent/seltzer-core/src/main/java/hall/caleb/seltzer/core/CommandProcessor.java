package hall.caleb.seltzer.core;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.objects.command.ChainCommand;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.command.FillFieldCommand;
import hall.caleb.seltzer.objects.command.GoToCommand;
import hall.caleb.seltzer.objects.command.MultiResultSelectorCommand;
import hall.caleb.seltzer.objects.command.ReadAttributeCommand;
import hall.caleb.seltzer.objects.command.SelectorCommand;
import hall.caleb.seltzer.objects.command.WaitCommand;
import hall.caleb.seltzer.objects.response.ChainResponse;
import hall.caleb.seltzer.objects.response.MultiResultResponse;
import hall.caleb.seltzer.objects.response.Response;
import hall.caleb.seltzer.objects.response.SingleResultResponse;

public class CommandProcessor {
	private static Logger logger = LogManager.getLogger(CommandProcessor.class);
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private static final int RETRIES = 4;
	private static final int RETRY_WAIT = 8;

	public static Response processCommand(WebDriver driver, Command command) {
		if (command.getType() != CommandType.Chain) {
			logger.info("Processing command:");
			logger.info(gson.toJson(command));
		}

		Response response = new Response(command.getId(), false);

		switch (command.getType()) {
			case Back:
				response = back(driver, command);
				break;
			case Chain:
				response = processChain(driver, (ChainCommand) command);
				break;
			case Click:
				response = click(driver, (SelectorCommand) command);
				break;
			case Count:
				response = count(driver, (SelectorCommand) command);
				break;
			case Delete:
				response = delete(driver, (SelectorCommand) command);
				break;
			case FillField:
				response = fillField(driver, (FillFieldCommand) command);
				break;
			case FormSubmit:
				response = formSubmit(driver, (SelectorCommand) command);
				break;
			case Forward:
				response = forward(driver, command);
				break;
			case GetUrl:
				response = getUrl(driver, command);
				break;
			case GoTo:
				response = goTo(driver, (GoToCommand) command);
				break;
			case ReadAttribute:
				response = readAttribute(driver, (ReadAttributeCommand) command);
				break;
			case ReadText:
				response = readText(driver, (MultiResultSelectorCommand) command);
				break;
			case Wait:
				response = wait(driver, (WaitCommand) command);
				break;
			default:
				response.setSuccess(false);
				break;
		}

		response.setId(command.getId());
		return response;
	}

	private static By getSelector(SelectorCommand command) {
		By selector;

		switch (command.getSelectorType()) {
			case ClassName:
				selector = By.className(command.getSelector());
				break;
			case CssSelector:
				selector = By.cssSelector(command.getSelector());
				break;
			case Id:
				selector = By.id(command.getSelector());
				break;
			case LinkText:
				selector = By.linkText(command.getSelector());
				break;
			case Name:
				selector = By.name(command.getSelector());
				break;
			case PartialLinkText:
				selector = By.partialLinkText(command.getSelector());
				break;
			case TagName:
				selector = By.tagName(command.getSelector());
				break;
			case Xpath:
				selector = By.xpath(command.getSelector());
				break;
			default:
				selector = null;
		}

		return selector;
	}

	private static Response back(WebDriver driver, Command command) {
		driver.navigate().back();

		return new Response(command.getId(), true);
	}

	private static ChainResponse processChain(WebDriver driver, ChainCommand command) {
		logger.info("Processing chain:");
		logger.info(gson.toJson(command));

		command.deserialize();
		
		ChainResponse response = new ChainResponse();

		Response tempResponse;
		for (Command subCommand : command.getCommands()) {
			if (!subCommand.getId().equals(command.getId())) {
				tempResponse = new Response(command.getId(), false);
			} else {
				tempResponse = processCommand(driver, subCommand);
			}
			response.setSuccess(response.isSuccess() && tempResponse.isSuccess());
			response.getResponses().add(tempResponse);

			if (!response.isSuccess()) {
				break;
			}
		}

		return response;
	}

	private static Response click(WebDriver driver, SelectorCommand command) {
		int tryNumber = 0;

		Response response = new Response(command.getId(), false);

		while (tryNumber < RETRIES) {
			try {
				driver.findElement(getSelector(command)).click();
				response.setSuccess(true);
				break;
			} catch (NoSuchElementException | StaleElementReferenceException e) {
				tryNumber++;
				sleep(e, tryNumber);
			}
		}

		return response;
	}

	private static SingleResultResponse count(WebDriver driver, SelectorCommand command) {
		SingleResultResponse response = new SingleResultResponse(command.getId());

		Integer size = driver.findElements(getSelector(command)).size();
		response.setResult(size.toString());
		response.setSuccess(size.toString().equals(response.getResult()));

		return response;
	}

	private static Response delete(WebDriver driver, SelectorCommand command) {
		Response response = new Response(command.getId(), false);
		if (command.getSelectorType() == SelectorType.Xpath && driver instanceof JavascriptExecutor) {
			String selector = command.getSelector().replace("\"", "\\\"");

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

		while (tryNumber < RETRIES) {
			try {
				WebElement field = driver.findElement(getSelector(command));
				field.click();
				field.sendKeys(command.getText());
				response.setSuccess(true);
				break;
			} catch (NoSuchElementException | StaleElementReferenceException e) {
				tryNumber++;
				sleep(e, tryNumber);
			}
		}

		return response;
	}

	private static Response formSubmit(WebDriver driver, SelectorCommand command) {
		int tryNumber = 0;

		Response response = new Response(command.getId(), false);

		while (tryNumber < RETRIES) {
			try {
				WebElement form = driver.findElement(getSelector(command));
				form.submit();
				response.setSuccess(true);
				break;
			} catch (NoSuchElementException e) {
				tryNumber++;
				sleep(e, tryNumber);
			}
		}

		return response;
	}

	private static Response forward(WebDriver driver, Command command) {
		driver.navigate().forward();

		return new Response(command.getId(), true);
	}

	private static SingleResultResponse getUrl(WebDriver driver, Command command) {
		SingleResultResponse response = new SingleResultResponse(command.getId(), true);

		response.setResult(driver.getCurrentUrl());

		return response;
	}

	private static Response goTo(WebDriver driver, GoToCommand command) {
		driver.get(command.getUrl());

		return new Response(command.getId(), true);
	}

	private static MultiResultResponse readAttribute(WebDriver driver, ReadAttributeCommand command) {
		List<String> attributes = Arrays.asList(command.getAttribute().split("/"));

		MultiResultResponse response = new MultiResultResponse(command.getId(), true);

		int tryNumber = 0;

		By selector = getSelector(command);
		int maxResults = -1;
		while (tryNumber < RETRIES) {
			try {
				maxResults = driver.findElements(selector).size();
				break;
			} catch (NoSuchElementException e) {
				tryNumber++;
				sleep(e, tryNumber);
			}
		}

		maxResults = (command.getMaxResults() <= 0 ? maxResults : Math.min(maxResults, command.getMaxResults()));
		if (maxResults < 0) {
			return new MultiResultResponse(command.getId(), false);
		}

		tryNumber = 0;

		String tmpResult = null;
		for (int i = 0; i < maxResults; i++) {
			while (tryNumber < RETRIES) {
				try {
					for (String attribute : attributes) {
						tmpResult = driver.findElements(getSelector(command)).get(i).getAttribute(attribute.trim());

						if (tmpResult != null && !tmpResult.isEmpty()) {
							break;
						}
					}
					break;
				} catch (NoSuchElementException e) {
					tryNumber++;
					sleep(e, tryNumber);
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

		By selector = getSelector(command);
		int maxResults = -1;
		while (tryNumber < RETRIES) {
			try {
				maxResults = driver.findElements(selector).size();
				break;
			} catch (NoSuchElementException e) {
				tryNumber++;
				sleep(e, tryNumber);
			}
		}

		maxResults = (command.getMaxResults() <= 0 ? maxResults : Math.min(maxResults, command.getMaxResults()));
		if (maxResults < 0) {
			return new MultiResultResponse(command.getId(), false);
		}

		tryNumber = 0;

		String tmpResult = null;
		for (int i = 0; i < maxResults; i++) {
			while (tryNumber < RETRIES) {
				try {
					tmpResult = driver.findElements(getSelector(command)).get(i).getText();
					break;
				} catch (NoSuchElementException e) {
					tryNumber++;
					sleep(e, tryNumber);
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
	
	private static SingleResultResponse wait(WebDriver driver, WaitCommand command) {
		SingleResultResponse response = new SingleResultResponse(command.getId());
		
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds()); 
		WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(getSelector(command)));
		response.setSuccess(e != null);
		
		return response;
	}

	private static void sleep(Exception e, int tryNumber) {
		logger.error("Error on try " + tryNumber + " of " + RETRIES + ".");
		logger.error(e);

		try {
			Thread.sleep(RETRY_WAIT * 1000);
		} catch (InterruptedException e1) {
			logger.error("Thread interrupted while waiting to retry, retrying immediately.");
		}
	}
}
