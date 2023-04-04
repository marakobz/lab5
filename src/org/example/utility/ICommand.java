package org.example.utility;

/**
 * Interface for all org.example.commands.
 */
public interface ICommand {

    String getName();

    boolean execute(String argument);

    boolean getExecuteFlag();
}