package hall.caleb.seltzer.objects.command;

public interface SerializableCommand {
	void serialize();
	
	void deserialize();
	
	CommandList getCommands();
	
	void setCommands(CommandList commands);
}
