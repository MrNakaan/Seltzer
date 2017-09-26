package tech.seltzer.core.processor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tech.seltzer.core.Messages;
import tech.seltzer.core.SeltzerSession;
import tech.seltzer.enums.CommandType;
import tech.seltzer.objects.command.ChainCommandData;
import tech.seltzer.objects.command.CommandData;
import tech.seltzer.objects.command.GetCookieCommandData;
import tech.seltzer.objects.command.GetCookiesCommandData;
import tech.seltzer.objects.command.GoToCommandData;
import tech.seltzer.objects.command.Selector;
import tech.seltzer.objects.command.selector.SelectorCommandData;
import tech.seltzer.objects.command.wait.WaitCommandData;
import tech.seltzer.objects.exception.SeltzerException;
import tech.seltzer.objects.response.ChainResponse;
import tech.seltzer.objects.response.ExceptionResponse;
import tech.seltzer.objects.response.MultiResultResponse;
import tech.seltzer.objects.response.Response;
import tech.seltzer.objects.response.SingleResultResponse;

/**
 * Processor for <code>CommandData</code> and all subclasses. 
 * Delegates to <code>SelectorProcessor</code> and 
 * <code>WaitProcessor</code> as appropriate.
 */
public class BaseProcessor {
	private static Logger logger = LogManager.getLogger(BaseProcessor.class);
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	static final int RETRIES = 4;
	private static final int RETRY_WAIT = 8;

	public static Response processCommand(WebDriver driver, CommandData command) {
		String screenshotBefore = null;
		if (command.takeScreenshotBefore() && !isScreenshotCommand(command)) {
			screenshotBefore = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		}
		
		if (command.getType() != CommandType.CHAIN) {
			logger.info(Messages.getString("BaseProcessor.command"));
			logger.info(gson.toJson(command));
		}

		Response response = new Response(command.getId(), false);
		response.setScreenshotBefore(screenshotBefore);
		
		if (command instanceof SelectorCommandData) {
			response = SelectorProcessor.processCommand(driver, (SelectorCommandData) command);
		} else if (command instanceof WaitCommandData) {
			response = WaitProcessor.processCommand(driver, (WaitCommandData) command);
		} else {
			int tryNumber = 0;
			while (tryNumber < BaseProcessor.RETRIES) {
				try {
					switch (command.getType()) {
					case START:
						response = start();
						break;
					case EXIT:
						response = exit(driver, command);
						break;
					case BACK:
						response = back(driver, command);
						break;
					case CHAIN:
						response = BaseProcessor.processChain(driver, (ChainCommandData<?>) command);
						break;
					case FORWARD:
						response = forward(driver, command);
						break;
					case GET_COOKIE:
						response = getCookie(driver, (GetCookieCommandData) command);
						break;
					case GET_COOKIE_FILE:
						response = getCookieFile(command);
						break;
					case GET_COOKIES:
						response = getCookies(driver, (GetCookiesCommandData) command);
						break;
					case GET_URL:
						response = getUrl(driver, command);
						break;
					case GO_TO:
						response = goTo(driver, (GoToCommandData) command);
						break;
					case SCREENSHOT_PAGE:
						response = takeScreenshot(driver, command);
						break;
					default:
						response.setSuccess(false);
						break;
					}
					
					if (command.takeScreenshotBefore() && !isScreenshotCommand(command)) {
						String screenshotAfter = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
						response.setScreenshotAfter(screenshotAfter);
					}
					
					break;
				} catch (WebDriverException | IOException e) {
					logger.error(e);
					tryNumber++;
					ExceptionResponse eResponse = new ExceptionResponse(command.getId());
					eResponse.setMessage(e.getMessage());
					eResponse.setStackTrace(e.getStackTrace());
					response = eResponse;
					sleep(e, tryNumber);
				} catch (Exception e) {
					logger.error(e);
					tryNumber++;
					ExceptionResponse eResponse = new ExceptionResponse(command.getId());
					eResponse.setMessage(
							Messages.getString("BaseProcessor.exception"));
					eResponse.setStackTrace(new StackTraceElement[0]);
					response = eResponse;
					sleep(e, tryNumber);
				}
			}
		}

		response.setId(command.getId());
		return response;
	}

	private static boolean isScreenshotCommand(CommandData command) {
		return command.getType() == CommandType.SCREENSHOT_ELEMENT || command.getType() == CommandType.SCREENSHOT_PAGE;
	}
	
	static By getBy(Selector selector) {
		By by;

		switch (selector.getType()) {
		case CLASS_NAME:
			by = By.className(selector.getPath());
			break;
		case CSS_SELECTOR:
			by = By.cssSelector(selector.getPath());
			break;
		case ID:
			by = By.id(selector.getPath());
			break;
		case LINK_TEXT:
			by = By.linkText(selector.getPath());
			break;
		case NAME:
			by = By.name(selector.getPath());
			break;
		case PARTIAL_LINK_TEXT:
			by = By.partialLinkText(selector.getPath());
			break;
		case TAG_NAME:
			by = By.tagName(selector.getPath());
			break;
		case XPATH:
			by = By.xpath(selector.getPath());
			break;
		default:
			by = null;
		}

		return by;
	}

	static ChainResponse<?> processChain(WebDriver driver, ChainCommandData<?> command) throws WebDriverException, Exception {
		logger.info(Messages.getString("BaseProcessor.chain"));
		logger.info(gson.toJson(command));

		ChainResponse<Response> response = new ChainResponse<>();

		Response tempResponse;
		for (CommandData subCommand : command.getCommands()) {
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

	@SuppressWarnings("resource")
	private static Response start() throws WebDriverException, Exception {
		Response response = new Response();

		response.setId(new SeltzerSession().getId());
		response.setSuccess(true);

		return response;
	}

	private static Response exit(WebDriver driver, CommandData command) throws WebDriverException, Exception {
		Response response = new Response();

		SeltzerSession.findSession(command.getId()).close();

		response.setSuccess(true);

		return response;
	}

	private static Response back(WebDriver driver, CommandData command) throws WebDriverException, Exception {
		driver.navigate().back();

		return new Response(command.getId(), true);
	}

	private static Response forward(WebDriver driver, CommandData command) throws WebDriverException, Exception {
		driver.navigate().forward();

		return new Response(command.getId(), true);
	}

	private static SingleResultResponse getUrl(WebDriver driver, CommandData command) throws WebDriverException, Exception {
		SingleResultResponse response = new SingleResultResponse(command.getId(), true);

		response.setResult(driver.getCurrentUrl());

		return response;
	}

	private static SingleResultResponse getCookie(WebDriver driver, GetCookieCommandData command)
			throws WebDriverException, Exception {
		String value = driver.manage().getCookieNamed(command.getCookieName()).getValue();
		SingleResultResponse response = null;
		if (StringUtils.isNotEmpty(value)) {
			response = new SingleResultResponse(command.getId(), true);
			response.setResult(value);
		} else {
			response = new SingleResultResponse(command.getId(), false);
		}
		return response;
	}

	private static SingleResultResponse getCookieFile(CommandData command) throws WebDriverException, Exception {
		SingleResultResponse response = new SingleResultResponse(command.getId(), true);

		Path dataDir = SeltzerSession.findSession(command.getId()).getDataDir();
		dataDir = Paths.get(dataDir.toString(), "Default", "Cookies");

		try {
			String encoded = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(dataDir.toFile()));
			response.setResult(encoded);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}

	private static MultiResultResponse getCookies(WebDriver driver, GetCookiesCommandData command)
			throws WebDriverException, Exception {
		MultiResultResponse response = new MultiResultResponse(command.getId(), false);
		String value = null;

		for (String cookieName : command.getCookieNames()) {
			value = driver.manage().getCookieNamed(cookieName).getValue();

			if (StringUtils.isNotEmpty(value)) {
				response.getResults().add(value);
				response.setSuccess(true);
			}
		}
		return response;
	}

	private static Response goTo(WebDriver driver, GoToCommandData command) throws WebDriverException, Exception {
		driver.get(command.getUrl());

		return new Response(command.getId(), true);
	}
	
	private static Response takeScreenshot(WebDriver driver, CommandData command) throws WebDriverException, Exception {
		String screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

		SingleResultResponse response = new SingleResultResponse(command.getId(), true);
		response.setResult(screenshot);
		
		return response;
	}
	
	static void sleep(Exception e, int tryNumber) {
		String message = Messages.getString("BaseProcessor.try");
		message = MessageFormat.format(message, tryNumber, RETRIES);
		logger.error(message);
		logger.error(e);

		try {
			Thread.sleep(RETRY_WAIT * 1000);
		} catch (InterruptedException e1) {
			logger.error(Messages.getString("BaseProcessor.interrupted"));
		}
	}
}
