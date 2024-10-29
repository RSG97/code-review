/**
 * Utility class for generating twin prime numbers.
 * Twin primes are pairs of prime numbers that differ by 2.
 * This generator is used to find appropriate hash table sizes,
 * as twin primes help optimize the double hashing probe sequence.
 *
 * @author Ryan Graham
 */
public class TwinPrimeGenerator {

    /**
     * Generates the larger number of the first twin prime pair found within the given range.
     * A twin prime pair consists of two prime numbers (p, p+2).
     * For hash table implementation, we return p+2 (the larger prime)
     * to ensure the table size and (table size - 2) are both prime numbers.
     *
     * @param min The lower bound of the range to search (inclusive)
     * @param max The upper bound of the range to search (inclusive)
     * @return The larger prime number (p+2) of the first twin prime pair found
     * @throws IllegalArgumentException if no twin primes exist in the given range
     */
    public static int generateTwinPrime(int min, int max) {
        for (int i = min; i <= max; i++) {
            if (isPrime(i) == true && isPrime(i + 2) == true) {
                return i + 2;
            }
        }
        throw new IllegalArgumentException("No twin primes found within given range.");
    }

    /**
     * Tests whether a given number is prime.
     * A number is prime if it's greater than 1 and only divisible by 1 and itself.
     * Uses trial division up to the square root of the number for efficiency.
     *
     * @param number The number to test for primality
     * @return true if the number is prime, false otherwise
     */
    private static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        // Only need to check up to square root of number
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
