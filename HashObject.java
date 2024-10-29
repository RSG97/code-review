/**
 * A class representing objects stored in the hash table.
 * Each HashObject contains a key, a frequency count for duplicate keys,
 * and a probe count tracking the number of probes required for insertion.
 *
 * @author Ryan Graham
 */
public class HashObject {
    private Object key;
    private int freqCount;
    private int probeCount;

    /**
     * Constructs a new HashObject with the given key.
     * Initializes frequency count to 1 for new insertions.
     *
     * @param key The key object to be stored
     */
    public HashObject(Object key) {
        this.key = key;
        this.freqCount = 1;
    }

    /**
     * Returns the key stored in this HashObject.
     *
     * @return The stored key object
     */
    public Object getKey() {
        return key;
    }

    /**
     * Returns the frequency count for this key.
     *
     * @return Number of times this key appears in the data
     */
    public int getFreqCount() {
        return freqCount;
    }

    /**
     * Increments the frequency count when a duplicate key is encountered.
     */
    public void incrementFreqCount() {
        freqCount++;
    }

    /**
     * Returns the number of probes required for the initial insertion.
     *
     * @return The probe count for this object
     */
    public int getProbeCount() {
        return probeCount;
    }

    /**
     * Sets the number of probes required for insertion.
     *
     * @param count The number of probes needed to insert this object
     */
    public void setProbeCount(int count) {
        this.probeCount = count;
    }

    @Override
    public int hashCode() {
        if (key != null) {
            return key.hashCode();
        }
        else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HashObject otherObj = (HashObject) obj;
        if (key != null) {
            return key.equals(otherObj.key);
        }
        else {
            return otherObj.key == null;
        }
    }



    @Override
    public String toString() {
        String str = key.toString();
        return str;
    }
}
