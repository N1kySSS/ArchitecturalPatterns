package query.dto;

public class OrderStatisticsDTO {
    private final int totalOrders;
    private final int completedOrders;
    private final double totalRevenue;
    private final double averageOrderValue;

    public OrderStatisticsDTO(int totalOrders, int completedOrders, double totalRevenue, double averageOrderValue) {
        this.totalOrders = totalOrders;
        this.completedOrders = completedOrders;
        this.totalRevenue = totalRevenue;
        this.averageOrderValue = averageOrderValue;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public int getCompletedOrders() {
        return completedOrders;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double getAverageOrderValue() {
        return averageOrderValue;
    }
}
