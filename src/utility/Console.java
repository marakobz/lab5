package utility;

import exceptions.NoSuchCommandException;

import java.util.*;

/**
 * Operates command input.
 */
public class Console {
    private final CommandManager commandManager;
    private final UserIO userIO;

    public Console(CommandManager commandManager, UserIO userIO) {
        this.commandManager = commandManager;
        this.userIO = userIO;
    }

    /**
     * starts read commands and execute it while it is not an exit command
     */
    public void start() {
        boolean executeFlag = true;
        while (executeFlag) {
            if (!userIO.isScriptMode() && !userIO.getScanner().hasNextLine()) {
                userIO.writeln("The work of the program has been completed due to the termination of the input.");
                break;
            }

            String input = userIO.readline();
            String inputCommand = input.split(" ")[0].toLowerCase(Locale.ROOT);
            String argument = "";
            if (input.split(" ").length > 1) {
                argument = input.replaceFirst(inputCommand + " ", "");
            }
            if (commandManager.getCommands().containsKey(inputCommand)) {
                ICommand command = commandManager.getCommands().get(inputCommand);
                try {

                    command.execute(argument);
                    executeFlag = command.getExecuteFlag();
                    if (!userIO.isScriptMode()) {
                        userIO.writeln(">> completed");
                    }
                } catch (NoSuchCommandException e) {
                    userIO.finishReadAllScript();
                    userIO.writeln(e.getMessage());
                } catch (NumberFormatException e) {
                    if (userIO.isScriptMode()) {
                        userIO.finishReadScript();
                    }
                    userIO.writeln("type a number please");
                } catch (NoSuchElementException e) {
                    userIO.writeln("\nThe work of the program has been completed due to the termination of the input.");
                    break;
                }
            } else {
                userIO.writeln("No such command. Type \"help\" to get all commands with their names and descriptions");
            }
        }
    }

    /**
     * Prints toOut.toString() to Console
     * @param toOut Object to print
     */
    public static void print(Object toOut) {
        System.out.print(toOut);
    }

    /**
     * Prints toOut.toString() + \n to Console
     * @param toOut Object to print
     */
    public static void println(Object toOut) {
        System.out.println(toOut);
    }

    /**
     * Prints error: toOut.toString() to Console
     * @param toOut Error to print
     */
    public static void printerror(Object toOut) {
        System.out.println("error: " + toOut);
    }


    @Override
    public String toString() {
        return "Console (класс для обработки ввода команд)";
    }

}
