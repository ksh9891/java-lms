package nextstep.session.domain;

public class Capacity {
    private final Integer capacity;

    private Capacity(final Integer capacity) {
        this.capacity = capacity;
    }

    public static Capacity noLimit() {
        return new Capacity(null);
    }

    public static Capacity of(final int capacity) {
        return new Capacity(capacity);
    }

    public boolean hasLimit() {
        return capacity != null;
    }

    public boolean isFull(final int currentCapacity) {
        if (this.capacity == null) {
            return false;
        }

        return this.capacity == currentCapacity;
    }
}
