package utility;

/**
 * Interface for all commands.
 */
public interface ICommand {

    String getName();

    boolean execute(String argument);

    boolean getExecuteFlag();
}