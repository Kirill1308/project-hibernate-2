package org.javarush.command;

public interface Command {
    void execute();

    String getTarget();

    String getDescription();
}
