package query.dto;

public class OrderItemDTO {
    private final String id;
    private final String dish;
    private final int quantity;
    private final double price;
    private final double subtotal;

    public OrderItemDTO(String id, String dish, int quantity, double price, double subtotal) {
        this.id = id;
        this.dish = dish;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    public String getId() {
        return id;
    }

    public String getDish() {
        return dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getSubtotal() {
        return subtotal;
    }
}
