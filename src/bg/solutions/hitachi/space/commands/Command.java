package bg.solutions.hitachi.space.commands;

@FunctionalInterface
public interface Command {
    String execute();
}