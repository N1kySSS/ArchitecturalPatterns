import api.facade.RestaurantFacade;
import api.ui.ConsoleInterface;
import command.command.AddDishToOrderCommand;
import command.command.CreateOrderCommand;
import command.command.RemoveDishFromOrderCommand;
import command.command.UpdateOrderStatusCommand;
import command.handler.*;
import command.repository.InMemoryOrderRepository;
import common.event.EventBus;
import query.dto.OrderDTO;
import query.repository.OrderViewRepository;
import query.service.OrderEventHandler;
import query.service.OrderQueryService;

import java.util.List;

public class RestaurantApplication {
    public static void main(String[] args) {
        InMemoryOrderRepository commandOrderRepository = new InMemoryOrderRepository();
        OrderViewRepository queryOrderRepository = new OrderViewRepository();

        OrderEventHandler eventHandler = new OrderEventHandler(queryOrderRepository);
        EventBus.getInstance().register(eventHandler);

        CommandBus commandBus = new CommandBus();
        commandBus.register(CreateOrderCommand.class, new CreateOrderCommandHandler(commandOrderRepository));
        commandBus.register(AddDishToOrderCommand.class, new AddDishCommandHandler(commandOrderRepository));
        commandBus.register(RemoveDishFromOrderCommand.class, new RemoveDishCommandHandler(commandOrderRepository));
        commandBus.register(UpdateOrderStatusCommand.class, new UpdateOrderStatusCommandHandler(commandOrderRepository));

        OrderQueryService queryService = new OrderQueryService(queryOrderRepository);
        RestaurantFacade restaurantFacade = new RestaurantFacade(commandBus, queryService);

        try {
            System.out.println("Создание тестовых данных...");

            restaurantFacade.createOrder("Клиент1", 1);

            List<OrderDTO> orders = restaurantFacade.getAllOrders();
            String order1Id = orders.getFirst().getId();

            restaurantFacade.addDishToOrder(order1Id, "Бургер", 2, 150.49);
            restaurantFacade.addDishToOrder(order1Id, "Картошка Фри", 1, 69.99);
            restaurantFacade.addDishToOrder(order1Id, "Сырный соус", 1, 50.99);

            System.out.println("Тестовые данные созданы.");
            System.out.println("\nСостояние текущих заказов:");
            for (OrderDTO order : restaurantFacade.getAllOrders()) {
                System.out.println("Заказ " + order.getId() + ":");
                System.out.println("  Заказчик: " + order.getCustomerId());
                System.out.println("  Номер стола: " + order.getTableNumber());
                System.out.println("  Статус: " + order.getStatus());
                System.out.println("  Количество блюд: " + order.getItems().size());
                System.out.println("  Счёт: " + String.format("%.2f", order.getTotalAmount()) + " руб.");
            }

        } catch (Exception e) {
            System.out.println("Ошибка создания тестовых данных: " + e.getMessage());
            return;
        }

        System.out.println("\nЗапуск консольного интерфейса...");
        ConsoleInterface consoleUI = new ConsoleInterface(restaurantFacade);
        consoleUI.start();
    }
}
