/**
 * Implementation of a hash table using double hashing for collision resolution.
 * Double hashing uses two hash functions:
 * h1(k) = k mod m for the initial probe
 * h2(k) = 1 + (k mod (m-2)) for the step size
 * The combined hash function is h(k,i) = (h1(k) + i*h2(k)) mod m
 *
 * @author Ryan Graham
 */
public class DoubleHashing extends Hashtable {

    /**
     * Constructs a new hash table using double hashing.
     *
     * @param size The size of the hash table, should be a twin prime number
     */
    public DoubleHashing(int size) {
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

    /**
     * Secondary hash function h2(k) that determines the step size.
     * Calculates h2(k) = 1 + (k mod (m-2)) where m is the table size.
     * The adding of 1 ensures the step size is never zero.
     *
     * @param key The key to be hashed
     * @return The step size for probing
     */
    private int h2(Object key) {
        return 1 + positiveMod(key.hashCode(), tableSize - 2);
    }

    @Override
    protected int h(Object key, int i) {
        return (h1(key) + (i * h2(key))) % tableSize;
    }
}