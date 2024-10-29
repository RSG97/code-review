/**
 * Implementation of a hash table using linear probing for collision resolution.
 * Linear probing uses a single hash function h1(k) = k mod m for the initial probe,
 * then linearly probes subsequent positions using the formula:
 * h(k,i) = (h1(k) + i) mod m
 *
 * @author Ryan Graham
 */
public class LinearProbing extends Hashtable {

    /**
     * Constructs a new hash table using linear probing.
     *
     * @param size The size of the hash table, should be a twin prime number
     */
    public LinearProbing(int size) {
        super(size);
    }

    /**
     * Primary hash function h1(k) that determines the initial probe position.
     * Calculates h1(k) = k mod m where m is the table size.
     *
     * @param key The key to be hashed
     * @return The initial probe position for the key
     */
    private int h1(Object key) {
        return positiveMod(key.hashCode(), tableSize);
    }

    @Override
    protected int h(Object key, int i) {
        return (h1(key) + i) % tableSize;
    }
}
