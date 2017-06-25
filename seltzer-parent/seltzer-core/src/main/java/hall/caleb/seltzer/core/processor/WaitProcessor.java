package hall.caleb.seltzer.core.processor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hall.caleb.seltzer.objects.command.wait.OldVisibilityWaitCommand;
import hall.caleb.seltzer.objects.command.wait.WaitCommand;
import hall.caleb.seltzer.objects.response.Response;

public class WaitProcessor {
	static Response processCommand(WebDriver driver, WaitCommand command) {
		return null;
	}
	
	static Response processCommand(WebDriver driver, OldVisibilityWaitCommand command) {
		return wait(driver, command);
	}
	
	private static Response wait(WebDriver driver, OldVisibilityWaitCommand command) {
		Response response = new Response(command.getId());
		
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds()); 
		WebElement e = wait.until(ExpectedConditions.visibilityOf(driver.findElement(BaseProcessor.getSelector(command))));
		response.setSuccess(e != null);
		
		return response;
	}

}
