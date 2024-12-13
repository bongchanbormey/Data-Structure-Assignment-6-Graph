import java.util.Arrays;

public class UnorderedArray<T> {
    private Object[] array;
    private int size;
    private int capacity;


    public UnorderedArray(int capacity) {
        this.capacity = capacity;
        this.array = new Object[capacity];
        this.size = 0;
    }


    // Add key-value pair
    public void add(String key, T value) {
        if (size >= capacity) {
            throw new RuntimeException("Array is full");
        }
        array[size++] = new HashEntry<>(key, value); // Store entries in the array
    }


    // Get the value associated with the key
    public T get(String key) {
        for (int i = 0; i < size; i++) {
            HashEntry<T> entry = (HashEntry<T>) array[i];
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }


    // Remove key-value pair
    public void remove(String key) {
        for (int i = 0; i < size; i++) {
            HashEntry<T> entry = (HashEntry<T>) array[i];
            if (entry.getKey().equals(key)) {
                array[i] = array[size - 1]; // Replace with last element
                array[size - 1] = null; // Remove last element
                size--;
                return;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public HashEntry<T> getEntryAt(int index) {
        if (index >= 0 && index < size) {
            return (HashEntry<T>) array[index];
        }
        return null;
    }


    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(array, size));
    }

    // Internal class to represent key-value pair
    public static class HashEntry<T> {
        private String key;
        private T value;

        public HashEntry(String key, T value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }
    }
}
