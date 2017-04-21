package hall.caleb.selenium;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hall.caleb.selenium.enums.SeleniumCommandType;
import hall.caleb.selenium.enums.SeleniumSelectorType;
import hall.caleb.selenium.objects.SeleniumCommand;
import hall.caleb.selenium.objects.SeleniumResponse;

public class CommandProcessor {
	private static Logger logger = LogManager.getLogger(CommandProcessor.class);
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private static final int RETRIES = 4;
	private static final int RETRY_WAIT = 8;
	
	public static SeleniumResponse processCommand(WebDriver driver, SeleniumCommand command) {
		if (command.getCommandType() != SeleniumCommandType.Chain) {
			logger.info("Processing command:");
			logger.info(gson.toJson(command));
		}
		
		SeleniumResponse response = new SeleniumResponse();

		switch (command.getCommandType()) {
		case Back:
			response.setSuccess(back(driver));
			break;
		case Chain:
			response = processChain(driver, command);
			break;
		case Click:
			response.setSuccess(click(driver, command));
			break;
		case Count:
			response = count(driver, command);
			break;
		case Delete:
			response.setSuccess(delete(driver, command));
			break;
		case FillField:
			response.setSuccess(fillField(driver, command));
			break;
		case FormSubmit:
			response.setSuccess(formSubmit(driver, command));
			break;
		case Forward:
			response.setSuccess(forward(driver));
			break;
		case GetUrl:
			response = getUrl(driver, command);
			break;
		case GoTo:
			response.setSuccess(goTo(driver, command));
			break;
		case ReadAttribute:
			response = readAttribute(driver, command);
			break;
		case ReadText:
			response = readText(driver, command);
			break;
		default:
			response.setSuccess(false);
			break;
		}

		response.setId(command.getId());
		return response;
	}

	private static By getSelector(SeleniumCommand command) {
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

	private static boolean back(WebDriver driver) {
		driver.navigate().back();

		return true;
	}

	private static SeleniumResponse processChain(WebDriver driver, SeleniumCommand command) {
		logger.info("Processing chain:");
		logger.info(gson.toJson(command));
		
		SeleniumResponse response = new SeleniumResponse();

		SeleniumResponse tempResponse;
		for (SeleniumCommand subCommand : command.getCommands()) {
			if (!subCommand.getId().equals(command.getId())) {
				tempResponse = new SeleniumResponse();
				tempResponse.setId(subCommand.getId());
				tempResponse.setSuccess(false);
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

	private static boolean click(WebDriver driver, SeleniumCommand command) {
		int tryNumber = 0;

		while (tryNumber < RETRIES) {
			try {
				driver.findElement(getSelector(command)).click();
				return true;
			} catch (NoSuchElementException | StaleElementReferenceException e) {
				tryNumber++;
				sleep(e, tryNumber);
			}
		}
		
		return false;
	}

	private static SeleniumResponse count(WebDriver driver, SeleniumCommand command) {
		SeleniumResponse response = new SeleniumResponse();
		response.setId(command.getId());

		response.setCount(driver.findElements(getSelector(command)).size());

		return response;
	}

	private static boolean delete(WebDriver driver, SeleniumCommand command) {
		if (command.getSelectorType() != SeleniumSelectorType.Xpath) {
			return false;
		} else if (!(driver instanceof JavascriptExecutor)) {
			return false;
		}

		String selector = command.getSelector().replace("\"", "\\\"");

		StringBuilder removeScript = new StringBuilder();
		removeScript.append("var e = document.evaluate(\"");
		removeScript.append(selector);
		removeScript.append("\", document.documentElement); ");
		removeScript.append("e = e.iterateNext(); ");
		removeScript.append("e.parentNode.removeChild(e);");

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript(removeScript.toString());

		return true;
	}

	private static boolean fillField(WebDriver driver, SeleniumCommand command) {
		int tryNumber = 0;

		while (tryNumber < RETRIES) {
			try {
				WebElement field = driver.findElement(getSelector(command));
				field.click();
				field.sendKeys(command.getText());
				return true;
			} catch (NoSuchElementException | StaleElementReferenceException e) {
				tryNumber++;
				sleep(e, tryNumber);
			}
		}
		
		return false;
	}

	private static boolean formSubmit(WebDriver driver, SeleniumCommand command) {
		int tryNumber = 0;

		while (tryNumber < RETRIES) {
			try {
				WebElement form = driver.findElement(getSelector(command));
				form.submit();
				return true;
			} catch (NoSuchElementException e) {
				tryNumber++;
				sleep(e, tryNumber);
			}
		}
		
		return false;
	}

	private static boolean forward(WebDriver driver) {
		driver.navigate().forward();

		return true;
	}

	private static SeleniumResponse getUrl(WebDriver driver, SeleniumCommand command) {
		SeleniumResponse response = new SeleniumResponse();
		response.setId(command.getId());
		
		response.setText(driver.getCurrentUrl());
		
		return response;
	}
	
	private static boolean goTo(WebDriver driver, SeleniumCommand command) {
		driver.get(command.getUrl());

		return true;
	}

	private static SeleniumResponse readAttribute(WebDriver driver, SeleniumCommand command) {
		List<String> attributes = Arrays.asList(command.getAttribute().split("/"));
		String attributeText = null;

		SeleniumResponse response = new SeleniumResponse();
		response.setSuccess(false);
		
		int tryNumber = 0;

		while (tryNumber < RETRIES) {
			try {
				for (String attribute : attributes) {
					attributeText = driver.findElement(getSelector(command)).getAttribute(attribute.trim());
					
					if (attributeText != null) {
						break;
					}
				}
		
				response.setSuccess(true);
				response.setAttribute(attributeText != null ? attributeText : "");
			} catch (NoSuchElementException e) {
				tryNumber++;
				sleep(e, tryNumber);
			}
		}

		return response;
	}

	private static SeleniumResponse readText(WebDriver driver, SeleniumCommand command) {
		SeleniumResponse response = new SeleniumResponse();
		response.setSuccess(false);
		
		int tryNumber = 0;

		while (tryNumber < RETRIES) {
			try {
				String text = driver.findElement(getSelector(command)).getText();

				response.setSuccess(true);
				response.setText(text);
			} catch (NoSuchElementException e) {
				tryNumber++;
				sleep(e, tryNumber);
			}
		}

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
