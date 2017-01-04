package indexer;

import hash.Hash;
import occurrences.OccurrencesIterator;
import occurrences.OccurrencesList;

/**
 * Searches for a word
 *
 * @author <b>dardam</b>, michaldarda1@gmail.com
 * <br><b>starczyr</b>, radek.starczynowski@gmail.com
 */
public class Searcher {

    private Hash hash = Indexer.getHash();
    
    // Set of values of last search
    private OccurrencesIterator occurrences;

    /**
     * Find FIRST occurrence of the word
     *
     * @param word word to search for
     * @return pair: nr of document, word offset in document
     */
    public int[] findFirstOccurrence(String word) {
        OccurrencesList occurs = hash.get(word);

        if(occurs == null) {
            return null;
        }
        occurrences = new OccurrencesIterator(occurs);

        return occurrences.first();
    }

    /**
     * Finds next occurrence of the last searched word
     *
     * @return pair: nr of document, word offset in document
     */
    public int[] findNextOccurrence() {
        return occurrences.next();
    }

    /**
     * Find next occurrence of the word starting at given offset in given
     * document
     *
     * @param fromDocument start in this document
     * @param fromWord start with that offset
     * @return pair: nr of document, word offset in document
     */
    public int[] findNextOccurrence(int fromDocument, int fromWord) {
        return occurrences.greaterThan(fromDocument, fromWord);
    }

    /**
     * Finds previous occurrence of the last searched word
     *
     * @return pair: nr of document, word offset in document
     */
    public int[] findPreviousOccurrence() {
        return occurrences.prev();
    }

    /**
     * Find previous occurrence of the word starting at given offset in given
     * document
     *
     * @param fromDocument start in this document
     * @param fromWord start with that offset
     * @return pair: nr of document, word offset in document
     */
    public int[] findPreviousOccurrence(int fromDocument, int fromWord) {
        return occurrences.lessThan(fromDocument, fromWord);
    }
}