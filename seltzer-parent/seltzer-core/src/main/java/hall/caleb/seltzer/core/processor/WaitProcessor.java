package hall.caleb.seltzer.core.processor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import hall.caleb.seltzer.objects.command.WaitCommand;
import hall.caleb.seltzer.objects.response.SingleResultResponse;

public class WaitProcessor {

	static SingleResultResponse wait(WebDriver driver, WaitCommand command) {
		SingleResultResponse response = new SingleResultResponse(command.getId());
		
		WebDriverWait wait = new WebDriverWait(driver, command.getSeconds()); 
		WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(BaseProcessor.getSelector(command)));
		response.setSuccess(e != null);
		
		return response;
	}

}
