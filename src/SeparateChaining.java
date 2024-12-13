public class SeparateChaining<T> implements HashTable<T> {
    // Each index in the array will hold an UnorderedArray of key-value pairs
    UnorderedArray<T>[] chains;
    int size;


    // Constructor
    public SeparateChaining(int capacity, int chainCapacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.size = capacity;
        chains = (UnorderedArray<T>[]) new UnorderedArray[capacity];
        for (int i = 0; i < capacity; i++) {
            chains[i] = new UnorderedArray<>(chainCapacity); // Each chain has a limited capacity
        }
    }



    // Hash function
    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % size;
    }


    // Insert key-value pair
    @Override
    public void insert(String key, T value) {
        int hash = hash(key);
        UnorderedArray<T> chain = chains[hash];

        chain.remove(key);              // Remove the key if it already exists, so we can replace it
        chain.add(key, value);          // Add new entry to the chain
    }


    // Search for a value by key
    @Override
    public T search(String key) {
        int hash = hash(key);
        UnorderedArray<T> chain = chains[hash];
        return chain.get(key); // Use UnorderedArray's get method
    }


    // Delete a key-value pair
    @Override
    public void delete(String key) {
        int hash = hash(key);
        UnorderedArray<T> chain = chains[hash];
        chain.remove(key); // Use UnorderedArray's remove method
    }


    // Display the hash table contents
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append("Index ").append(i).append(": ");
            sb.append(chains[i].toString()).append("\n");
        }
        return sb.toString();
    }


    public int size() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            count += chains[i].getSize();
        }
        return count;
    }
}
