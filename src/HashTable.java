public interface HashTable<T> {
    void insert(String key, T value);
    T search(String key);
    void delete(String key);
    String toString();
}
