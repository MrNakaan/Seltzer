package hall.caleb.seltzer.core.processor;

import org.openqa.selenium.WebDriver;

import hall.caleb.seltzer.objects.command.ChainCommand;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.response.ChainResponse;
import hall.caleb.seltzer.objects.response.Response;

public class ChainProcessor {

	static ChainResponse processChain(WebDriver driver, ChainCommand command) {
		BaseProcessor.logger.info("Processing chain:");
		BaseProcessor.logger.info(BaseProcessor.gson.toJson(command));
	
		command.deserialize();
		
		ChainResponse response = new ChainResponse();
	
		Response tempResponse;
		for (Command subCommand : command.getCommands()) {
			if (!subCommand.getId().equals(command.getId())) {
				tempResponse = new Response(command.getId(), false);
			} else {
				tempResponse = BaseProcessor.processCommand(driver, subCommand);
			}
			response.setSuccess(response.isSuccess() && tempResponse.isSuccess());
			response.getResponses().add(tempResponse);
	
			if (!response.isSuccess()) {
				break;
			}
		}
	
		return response;
	}

}
