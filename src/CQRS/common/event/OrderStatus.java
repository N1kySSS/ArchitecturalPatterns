package common.event;

import common.exception.OrderExceptions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum OrderStatus {
    CREATED,
    IN_PROGRESS,
    READY,
    COMPLETED,
    CANCELLED;

    public void validateOrderStatusTransition(OrderStatus newStatus) {
        Set<OrderStatus> validTransitions = switch (this) {
            case CREATED -> new HashSet<>(Arrays.asList(IN_PROGRESS, CANCELLED));
            case IN_PROGRESS -> new HashSet<>(Arrays.asList(READY, CANCELLED));
            case READY -> new HashSet<>(Arrays.asList(COMPLETED, CANCELLED));
            case COMPLETED, CANCELLED -> new HashSet<>();
        };

        if (!validTransitions.contains(newStatus)) {
            Set<String> validTransitionNames = new HashSet<>();
            for (OrderStatus status : validTransitions) {
                validTransitionNames.add(status.name());
            }
            throw new OrderExceptions.InvalidOrderStatusTransitionException(
                    this.name(),
                    newStatus.name(),
                    validTransitionNames
            );
        }
    }
}
