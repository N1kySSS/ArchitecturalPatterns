package command.command;

import java.util.UUID;

public interface Command {
    default String getCommandId() {
        return UUID.randomUUID().toString();
    }
}
