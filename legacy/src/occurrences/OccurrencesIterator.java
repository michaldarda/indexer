package occurrences;

/**
 * Class which helps iterating over occurrences
 * returning them as a pairs (doc, pos)
 *
 * @author <b>dardam</b>, michaldarda1@gmail.com <br>
 *     <b>starczyr</b>, radek.starczynowski@gmail.com
 */
public class OccurrencesIterator {

    private int[] occurs;
    private int numberOfElements;

    private int curDoc;
    private int curPos;

    /**
     *
     * @param occurs occurrances to iterate
     */
    public OccurrencesIterator(OccurrencesList occurs) {
        this.occurs = occurs.getOccurrences();
        this.numberOfElements = occurs.getNumberOfElements();

        curDoc = 0;
        curPos = 1;
    }

    /**
     *
     * @return first occurrence
     */
    public int[] first() {
        int[] results = new int[2];
        results[0] = occurs[0];
        results[1] = occurs[1];

        return results;
    }

    /**
     *
     * @return next occurrence or null if haven't found
     */
    public int[] next() {
        int[] results = new int[2];

        if(!hasNext()) {
            return null;
        }

        curDoc+=2;
        curPos+=2;

        results[0] = occurs[curDoc];
        results[1] = occurs[curPos];

        return results;
    }

    /*
    * returns previous occurrence from curDoc, curPos
    * or null if there isn't
     */

    /**
     *
     * @return previous occurrence or null if haven't found
     */
    public int[] prev() {
        int[] results = new int[2];

        if(!hasPrev()) {
            return null;
        }

        curDoc-=2;
        curPos-=2;

        results[0] = occurs[curDoc];
        results[1] = occurs[curPos];

        return results;
    }

    /**
     *
     * @param doc document number to search from
     * @param pos position to search from
     * @return next occurrence greater than doc, pos or null if haven't found
     */
    public int[] greaterThan(int doc, int pos) {
        int[] results = new int[2];

        for(int i = 0; i < numberOfElements - 1; i+=2) {
            if(occurs[i] >= doc && occurs[i+1] >= pos ||
                    occurs[i] > doc) {
                curDoc = i;
                curPos = i+1;
                results[0] = occurs[curDoc];
                results[1] = occurs[curPos];
                return results;
            }
        }

        return null;
    }


    /**
     *
     * @param doc document number to search from
     * @param pos position to search from
     * @return previous occurrence less than doc, pos or null if haven't found
     */
    public int[] lessThan(int doc, int pos) {
        int[] results = new int[2];

        for(int i = numberOfElements - 2; i >= 0; i-=2) {
            if(occurs[i] <= doc && occurs[i+1] <= pos ||
                    occurs[i] < doc) {
                curDoc = i;
                curPos = i+1;
                results[0] = occurs[curDoc];
                results[1] = occurs[curPos];
                return results;
            }
        }

        return null;
    }

    private boolean hasNext() {
        return !(curPos+1 >= numberOfElements);
    }

    private boolean hasPrev() {
        return !(curDoc-1 < 0);
    }
}