import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Abstract base class for hash table implementation using open addressing.
 * Provides core functionality for hash table operations while leaving the
 * probing mechanism to be implemented by subclasses.
 *
 * @author Ryan Graham
 */
public abstract class Hashtable {
    protected HashObject[] table;
    protected int tableSize;
    protected int numElements;
    protected int probeCount;

    /**
     * Constructs a hash table with specified size.
     *
     * @param size The size of the hash table, should be a twin prime number
     */
    public Hashtable(int size) {
        this.tableSize = size;
        this.table = new HashObject[this.tableSize];
        this.numElements = 0;
        this.probeCount = 0;
    }

    /**
     * Abstract method to be implemented by subclasses for specific probing sequence.
     *
     * @param key The key to be hashed
     * @param i The probe sequence number
     * @return The next position in the probe sequence
     */
    protected abstract int h(Object key, int i);

    /**
     * Inserts a key into the hash table using open addressing.
     * If the key is a duplicate, increments its frequency count instead.
     *
     * @param key The key to insert
     * @return The index where the key was inserted or found
     * @throws IllegalStateException if the table is full and key cannot be inserted
     */
    public int insert(Object key) {
        int i = 0;
        int initialProbeCount = probeCount;  // Store initial probe count to calculate probes for this insertion
        do {
            int probe = h(key, i);
            probeCount++;

            // insert element into table if position is open. If a duplicate is detected, do not insert.
            if (table[probe] == null) {
                table[probe] = new HashObject(key);
                table[probe].setProbeCount(probeCount - initialProbeCount);
                numElements++;
                return probe;
            }
            else if (table[probe].getKey().equals(key)) {
                // Frequency includes original value in the table and the duplicate(s)
                table[probe].incrementFreqCount();
                probeCount = initialProbeCount; // Reset probe count for duplicates
                return probe;
            }
            i++;
        } while (i != tableSize);
        throw new IllegalStateException("Hash table overflow");
    }

    /**
     * Searches for a key in the hash table.
     *
     * @param key The key to search for
     * @return The index of the key if found, -1 otherwise
     */
    public int Search(Object key) {
        int i = 0;
        do {
            int probe = h(key, i);
            if (table[probe].getKey().equals(key)) {
                return probe;
            }
            i++;
        } while ( i != tableSize);
        return -1; //key not found
    }

    /**
     * Calculates the average number of probes required for successful insertions.
     *
     * @return Average number of probes per successful insertion
     */
    public double getAverageProbes() {
        long totalProbes = 0;
        for (HashObject obj : table) {
            if (obj != null) {
                totalProbes += obj.getProbeCount();
            }
        }
        return (double) totalProbes / numElements;
    }

    /**
     * Handles the modulo operation ensuring the result is positive.
     * Required because Java's % operator returns negative results for negative dividends.
     *
     * @param dividend The number being divided
     * @param divisor The number dividing
     * @return Positive modulo result
     */
    protected int positiveMod (int dividend, int divisor) {
        int quotient = dividend % divisor;
        if (quotient < 0)
            quotient += divisor;
        return quotient;
    }

    /**
     * Dumps the contents of the hash table to a file.
     * Only non-null entries are written to the file.
     * Format: table[index]: key frequency probeCount
     *
     * @param filename The name of the file to write to
     */
    public void dumpToFile(String filename) {
        try (PrintWriter pr = new PrintWriter(filename)) {
            for (int i = 0; i < tableSize; i++) {
                if (table[i] != null) {
                    pr.println("table[" + i + "]: " + table[i].toString() + " " + table[i].getFreqCount() + " " + table[i].getProbeCount());
                }
            }
        }
        catch (FileNotFoundException fnfe) {
            System.err.println("File not found: " + fnfe.getMessage());
        }
    }
}
