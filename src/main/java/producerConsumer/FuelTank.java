package producerConsumer;

public class FuelTank {

    private int capacity;
    private int quantity;

    public FuelTank(int capacity, int quantity) {
        this.capacity = capacity;
        this.quantity = quantity;
    }

    synchronized public void fill(int gaz) {
        this.quantity += gaz;

        if (this.quantity > capacity) {
            throw new RuntimeException("Tank is overfilled!");
        }
    }

    synchronized public int pump(int requestedAmount) {
        if (requestedAmount<1) return 0;
        if (requestedAmount > quantity) {
            if (quantity == 0) {
                throw new RuntimeException("Tank is empty!");
            }
            var pumped = quantity;
            quantity = 0;
            return pumped;
        }
        quantity -= requestedAmount;
        return requestedAmount;
    }

    @Override
    public String toString() {
        return "FuelTank{" +
                "quantity=" + quantity +
                '}';
    }
}
