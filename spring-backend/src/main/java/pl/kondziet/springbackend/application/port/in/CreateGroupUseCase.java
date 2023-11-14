package pl.kondziet.springbackend.application.port.in;

import pl.kondziet.springbackend.application.port.in.command.CreateGroupCommand;

public interface CreateGroupUseCase {

    void createGroup(CreateGroupCommand command);
}
