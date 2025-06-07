package common.exception;

import java.util.Set;

public class OrderExceptions {
    public static class InvalidQuantityException extends IllegalArgumentException {
        public InvalidQuantityException() {
            super("Количество должно быть положительным.");
        }
    }

    public static class InvalidPriceException extends IllegalArgumentException {
        public InvalidPriceException() {
            super("Цена должна быть положительной.");
        }
    }

    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String orderId) {
            super(String.format("Заказ с ID '%s' не найден.", orderId));
        }
    }

    public static class InvalidOrderStatusTransitionException extends IllegalStateException {
        public InvalidOrderStatusTransitionException(String currentStatus, String newStatus, Set<String> validTransitions) {
            super(String.format("Нельзя изменить статус с %s на %s. Допустимые переходы: %s.",
                currentStatus, newStatus, validTransitions));
        }
    }

    public static class OrderItemNotFoundException extends RuntimeException {
        public OrderItemNotFoundException(String dish) {
            super(String.format("Позиция заказа с названием '%s' не найдена.", dish));
        }
    }

    public static class InvalidTableNumberException extends IllegalArgumentException {
        public InvalidTableNumberException() {
            super("Номер стола должен быть положительным.");
        }
    }

    public static class BlankOrderIdException extends IllegalArgumentException {
        public BlankOrderIdException() {
            super("ID заказа не может отсутствовать.");
        }
    }

    public static class BlankCustomerIdException extends IllegalArgumentException {
        public BlankCustomerIdException() {
            super("ID клиента не может отсутствовать.");
        }
    }

    public static class BlankDishException extends IllegalArgumentException {
        public BlankDishException() {
            super("Название блюда не может отсутствовать.");
        }
    }

    public static class OrderNotModifiableException extends IllegalStateException {
        public OrderNotModifiableException() {
            super("Чтобы изменить заказ, он должен быть в статусе CREATED.");
        }
    }
}
