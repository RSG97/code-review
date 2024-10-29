import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.List;

/**
 * Driver class for running experiments comparing Linear Probing and Double Hashing.
 * Tests different load factors with various data sources to analyze performance.
 *
 * @author Ryan Graham
 */
public class HashtableExperiment {
    private static final int TABLE_SIZE = TwinPrimeGenerator.generateTwinPrime(95500, 96000);
    private static final String WORD_LIST = "word-list.txt";
    private static List<String> wordList;

    /**
     * Main method for the program.
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.out.println("Usage: java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]\n" +
                    "       <dataSource>: 1 ==> random numbers\n" +
                    "                     2 ==> date value as a long\n" +
                    "                     3 ==> word list\n" +
                    "       <loadFactor>: The ratio of objects to table size, \n" +
                    "                       denoted by alpha = n/m\n" +
                    "       <debugLevel>: 0 ==> print summary of experiment\n" +
                    "                     1 ==> save the two hash tables to a file at the end\n" +
                    "                     2 ==> print debugging output for each insert\n");
            System.exit(1);
        }

        int dataSource = Integer.parseInt(args[0]);
        double loadFactor = Double.parseDouble(args[1]);
        if (loadFactor > 1.00 || loadFactor < 0.00) {
            System.out.println("Invalid load factor: " + loadFactor);
            System.exit(1);
        }

        int debugLevel = 0; // Default debug mode
        if (args.length == 3) {
            debugLevel = Integer.parseInt(args[2]);
        }
        if (debugLevel > 2) {
            System.out.println("Invalid debug level: " + debugLevel);
            System.exit(1);
        }

        // Create instances of both hash table implementations
        LinearProbing linearProbing = new LinearProbing(TABLE_SIZE);
        DoubleHashing doubleHashing = new DoubleHashing(TABLE_SIZE);

        System.out.println("HashtableExperiment: Found a twin prime table capacity: " + TABLE_SIZE);
        System.out.println("HashtableExperiment: Input: " + getSource(dataSource) + "    Loadfactor: " + loadFactor + "\n");
        runExperiment(linearProbing, "Linear Probing", dataSource, loadFactor, debugLevel);
        runExperiment(doubleHashing, "Double Hashing", dataSource, loadFactor, debugLevel);
    }

    /**
     * Runs a single experiment with the specified hash table implementation and parameters.
     *
     * @param table The hash table implementation to test
     * @param method The name of the probing method being tested
     * @param dataSource Type of data to use (1=random, 2=dates, 3=words)
     * @param loadFactor Target load factor for the experiment
     * @param debugLevel Level of debugging output
     */
    private static void runExperiment(Hashtable table, String method, int dataSource, Double loadFactor, int debugLevel) {
        int numElementsToInsert = (int)(table.tableSize * loadFactor);
        int uniqueInserted = 0;
        int freqSum = 0;

        // Load word list if using string data source
        if (dataSource == 3) {
            try {
                loadWordList();
            } catch (IOException e) {
                System.err.println("Error loading word list: " + e.getMessage());
                System.exit(1);
            }
        }

        // Insert elements until reaching desired load factor
        for (int i = 0; uniqueInserted <= numElementsToInsert; i++) {
            Object key = new HashObject(generateData(dataSource, i));
            int result = table.insert(key);


            if (table.table[result].getFreqCount() == 1) {
                uniqueInserted++;
            }

            // Debug level 2: Print details of each insertion
            if (debugLevel == 2) {
                System.out.print("Element " + key + " inserted at " + result);
                if (table.table[result].getFreqCount() > 1) {
                    System.out.println(" (duplicate)");
                }
                System.out.println();
            }
        }

        // Calculate total frequency sum
        for (int i = 0; i < table.tableSize; i++) {
            if (table.table[i] != null) {
                freqSum += table.table[i].getFreqCount();
            }
        }

        // Output results based on debug level
        switch (debugLevel) {
            case 0: // Basic summary
                printSummary(table, method, freqSum);
                break;
            case 1: // Summary and dump to file
                printSummary(table, method, freqSum);
                System.out.println("HashtableExperiment: Saved dump of hash table\n");
                dumpTable(table, method);
                break;
            default:
                break;
        }
    }

    /**
     * Loads words from the word list file into memory.
     *
     * @throws IOException if there's an error reading the word list file
     */
    private static void loadWordList() throws IOException {
        wordList = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(WORD_LIST))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordList.add(line.trim());
            }
        }
    }

    /** Tracks current date for date-based data generation */
    private static long currentDate = new Date().getTime();

    /**
     * Generates data based on the specified data source type.
     *
     * @param dataSource Type of data to generate (1=random, 2=dates, 3=words)
     * @param index Index for word list access when using data source 3
     * @return Generated data object
     * @throws IllegalStateException if word list runs out of words
     * @throws IllegalArgumentException if invalid data source specified
     */
    private static Object generateData(int dataSource, int index) {
        switch (dataSource) {
            case 1:
                return new Random().nextInt();
            case 2:
                currentDate += 1000;
                Date date = new Date(currentDate);
                return date;
            case 3:
                if (index < wordList.size()) {
                    return wordList.get(index);
                }
                else {
                    throw new IllegalStateException("Not enough words in file to reach desired load factor ");
                }
            default:
                throw new IllegalArgumentException("Invalid data source: " + dataSource);
        }
    }

    /**
     * Returns a string description of the data source type.
     *
     * @param dataSource The data source identifier
     * @return String description of the data source
     */
    private static String getSource(int dataSource) {
        switch (dataSource) {
            case 1:
                return "Random numbers";
            case 2:
                return "Date";
            case 3:
                return "Word list";
            default:
                return "Unknown";
        }
    }

    /**
     * Prints summary statistics for an experiment.
     *
     * @param table The hash table that was tested
     * @param method The probing method used
     * @param freqSum Total frequency sum including duplicates
     */
    private static void printSummary(Hashtable table, String method, int freqSum) {
        System.out.println("\tUsing " + method);
        System.out.println("HashtableExperiment: size of hash table is " + table.numElements);
        System.out.println("\tInserted " + freqSum + " elements, of which " +
                (freqSum - table.numElements) + " were duplicates");
        System.out.println("\tAvg. no. of probes = " +
                String.format("%.2f", table.getAverageProbes()) + "\n");
    }

    /**
     * Dumps hash table contents to appropriate file based on method.
     *
     * @param table The hash table to dump
     * @param method The probing method used
     */
    private static void dumpTable(Hashtable table, String method) {
        if (method.equals("Linear Probing")) {
            table.dumpToFile("linear-dump.txt");
        }
        if (method.equals("Double Hashing")) {
            table.dumpToFile("double-dump.txt");
        }
    }
}
