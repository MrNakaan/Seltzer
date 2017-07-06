package hall.caleb.seltzer.core.processor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hall.caleb.seltzer.core.SeltzerSession;
import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.command.ChainCommand;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.command.GetCookieCommand;
import hall.caleb.seltzer.objects.command.GetCookiesCommand;
import hall.caleb.seltzer.objects.command.GoToCommand;
import hall.caleb.seltzer.objects.command.Selector;
import hall.caleb.seltzer.objects.command.SendKeyCommand;
import hall.caleb.seltzer.objects.command.SendKeysCommand;
import hall.caleb.seltzer.objects.command.SerializableCommand;
import hall.caleb.seltzer.objects.command.selector.SelectorCommand;
import hall.caleb.seltzer.objects.command.wait.WaitCommand;
import hall.caleb.seltzer.objects.response.ChainResponse;
import hall.caleb.seltzer.objects.response.ExceptionResponse;
import hall.caleb.seltzer.objects.response.MultiResultResponse;
import hall.caleb.seltzer.objects.response.Response;
import hall.caleb.seltzer.objects.response.SingleResultResponse;

public class BaseProcessor {
	private static Logger logger = LogManager.getLogger(BaseProcessor.class);
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	static final int RETRIES = 4;
	private static final int RETRY_WAIT = 8;

	public static Response processCommand(WebDriver driver, Command command) {
		if (command.getType() != CommandType.Chain) {
			logger.info("Processing command:");
			logger.info(gson.toJson(command));
		}

		Response response = new Response(command.getId(), false);

		if (command instanceof SelectorCommand) {
			response = SelectorProcessor.processCommand(driver, (SelectorCommand) command);
		} else if (command instanceof WaitCommand) {
			response = WaitProcessor.processCommand(driver, (WaitCommand) command);
		} else {
			try {
				switch (command.getType()) {
				case Start:
					response = start();
					break;
				case Exit:
					response = exit(driver, command);
					break;
				case Back:
					response = back(driver, command);
					break;
				case Chain:
					response = BaseProcessor.processChain(driver, (ChainCommand) command);
					break;
				case Forward:
					response = forward(driver, command);
					break;
				case GetCookie:
					response = getCookie(driver, (GetCookieCommand) command);
					break;
				case GetCookieFile:
					response = getCookieFile(command);
					break;
				case GetCookies:
					response = getCookies(driver, (GetCookiesCommand) command);
					break;
				case GetUrl:
					response = getUrl(driver, command);
					break;
				case GoTo:
					response = goTo(driver, (GoToCommand) command);
					break;
				case SendKey:
					response = sendKey(driver, (SendKeyCommand) command);
					break;
				case SendKeys:
					response = sendKeys(driver, (SendKeysCommand) command);
					break;
				default:
					response.setSuccess(false);
					break;
				}
			} catch (WebDriverException | IOException e) {
				logger.error(e);
				ExceptionResponse eResponse = new ExceptionResponse(command.getId(), false);
				eResponse.setMessage(e.getMessage());
				eResponse.setStackTrace(e.getStackTrace());
				response = eResponse;
			} catch (Exception e) {
				logger.error(e);
				ExceptionResponse eResponse = new ExceptionResponse(command.getId(), false);
				eResponse.setMessage(
						"A system error unrelated to Selenium has happened. No stack trace information is attached. Please try again.");
				eResponse.setStackTrace(new StackTraceElement[0]);
				response = eResponse;
			}
		}

		response.setId(command.getId());
		return response;
	}

	private static Response sendKey(WebDriver driver, SendKeyCommand command) {
		Response response = new Response(command.getId(), true);

		By by = getBy(command.getSelector());
		String keyName = command.getKey().toString().toUpperCase();
		driver.findElement(by).sendKeys(Keys.valueOf(keyName));
		
		return response;
	}

	private static Response sendKeys(WebDriver driver, SendKeysCommand command) {
		return null;
	}

	static By getBy(Selector selector) {
		By by;

		switch (selector.getSelectorType()) {
		case ClassName:
			by = By.className(selector.getSelector());
			break;
		case CssSelector:
			by = By.cssSelector(selector.getSelector());
			break;
		case Id:
			by = By.id(selector.getSelector());
			break;
		case LinkText:
			by = By.linkText(selector.getSelector());
			break;
		case Name:
			by = By.name(selector.getSelector());
			break;
		case PartialLinkText:
			by = By.partialLinkText(selector.getSelector());
			break;
		case TagName:
			by = By.tagName(selector.getSelector());
			break;
		case Xpath:
			by = By.xpath(selector.getSelector());
			break;
		default:
			by = null;
		}

		return by;
	}

	static ChainResponse processChain(WebDriver driver, ChainCommand command) {
		logger.info("Processing chain:");
		logger.info(gson.toJson(command));

		ChainResponse response = new ChainResponse();

		Response tempResponse;
		for (Command subCommand : ((SerializableCommand) command).getCommands().getCommands()) {
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
	private static Response start() throws SessionNotCreatedException {
		Response response = new Response();

		response.setId(new SeltzerSession().getId());
		response.setSuccess(true);

		return response;
	}

	private static Response exit(WebDriver driver, Command command) throws NoSuchSessionException {
		Response response = new Response();

		try {
			SeltzerSession.findSession(command.getId()).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setSuccess(true);

		return response;
	}

	private static Response back(WebDriver driver, Command command) throws WebDriverException {
		driver.navigate().back();

		return new Response(command.getId(), true);
	}

	private static Response forward(WebDriver driver, Command command) throws WebDriverException {
		driver.navigate().forward();

		return new Response(command.getId(), true);
	}

	private static SingleResultResponse getUrl(WebDriver driver, Command command) throws WebDriverException {
		SingleResultResponse response = new SingleResultResponse(command.getId(), true);

		response.setResult(driver.getCurrentUrl());

		return response;
	}

	private static SingleResultResponse getCookie(WebDriver driver, GetCookieCommand command)
			throws WebDriverException {
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

	private static SingleResultResponse getCookieFile(Command command) throws WebDriverException, IOException {
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

	private static MultiResultResponse getCookies(WebDriver driver, GetCookiesCommand command)
			throws WebDriverException {
		MultiResultResponse response = new MultiResultResponse(command.getId(), false);
		String value = null;

		for (String cookieName : command.getCookieNames()) {
			value = driver.manage().getCookieNamed(cookieName).getValue();

			if (StringUtils.isNotEmpty(value)) {
				response = new MultiResultResponse(command.getId(), true);
				response.getResults().add(value);
				response.setSuccess(true);
			}
		}
		return response;
	}

	private static Response goTo(WebDriver driver, GoToCommand command) throws WebDriverException {
		driver.get(command.getUrl());

		return new Response(command.getId(), true);
	}

	static void sleep(Exception e, int tryNumber) {
		logger.error("Error on try " + tryNumber + " of " + RETRIES + ".");
		logger.error(e);

		try {
			Thread.sleep(RETRY_WAIT * 1000);
		} catch (InterruptedException e1) {
			logger.error("Thread interrupted while waiting to retry, retrying immediately.");
		}
	}
}
