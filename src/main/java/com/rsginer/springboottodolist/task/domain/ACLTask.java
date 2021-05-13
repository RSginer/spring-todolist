package com.rsginer.springboottodolist.task.domain;

import com.rsginer.springboottodolist.task.api.TaskNotCreatedByAndNotAssignedToForbiddenException;
import com.rsginer.springboottodolist.user.domain.AppUser;

import java.util.stream.Collectors;

public class ACLTask {
    public static boolean isUserAuthorized(AppUser user, Task task) throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        var assignedToUuids = task
                .getAssignedTo().stream().map(AppUser::getUsername).collect(Collectors.toList());

        if (task.getCreatedBy().getUsername().equals(user.getUsername()) ||
                assignedToUuids.contains(user.getId().toString())) {
            return true;
        }

        throw new TaskNotCreatedByAndNotAssignedToForbiddenException(task.getId());
    }
}
