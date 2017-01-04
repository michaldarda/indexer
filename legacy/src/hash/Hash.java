package hash;

import occurrences.OccurrencesList;
import java.io.File;

/**
 * Hash map of Indexer
 * 
 * @author <b>dardam</b>, michaldarda1@gmail.com
 * <br><b>starczyr</b>, radek.starczynowski@gmail.com
 */
public class Hash {

    private static int hashSize = 31;
    
    private HashInnerList[] hashTable;
    
    /**
     * Create hash map of specific size
     */
    public Hash() {
        hashTable = new HashInnerList[hashSize];
        for(int i = 0; i < hashSize; i++) {
            hashTable[i] = new HashInnerList();
        }
        
    }
    
    private int hash(String string) {
       return (string.hashCode() & 0x7fffffff) % hashSize;
    }
    
    /**
     * Add word with it's occurrences in text to hash map
     * @param key word to insert
     * @param value list of occurrences of word
     */
    public void put(String key, OccurrencesList value) {
        hashTable[hash(key)].put(key, value);
    }
    
    /**
     * Get word from hash map
     * @see OccurrencesList
     * 
     * @param key a word
     * @return list of occurrences
     */
    public OccurrencesList get(String key) {
        return hashTable[hash(key)].get(key);
    }
    
    /**
     * Print whole hash map
     */
    public void printHash() {
        for(int i = 0; i < hashSize; i++) {
            hashTable[i].printInnerList();
        }
    }
    
    /**
     * Estimates size of hash table based on size of files
     * 
     * @param files text files
     */
    public static void estimateHashSize(String[] files) {
        int acc = 0;
        for(int i = 0; i < files.length; i++) {
            File file = new File(files[i]);
            acc += file.length();
        }

        double y =  2200000;
        double x = y / (double) 298844160;
        hashSize = (int) (x * acc);
    }
}