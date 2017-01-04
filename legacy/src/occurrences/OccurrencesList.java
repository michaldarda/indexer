package occurrences;

/**
 * Class with dynamic array, keeping all occurrences and document numbers
 * as sorted list
 *
 * Odd indexes - documents, even - positions in document
 *
 *  @author <b>dardam</b>, michaldarda1@gmail.com <br>
 *     <b>starczyr</b>, radek.starczynowski@gmail.com
 */
public class OccurrencesList {
    private int[] occurrences;
    private int numberOfElements;
    private int size;

    /**
     *
     * @param document number
     * @param position in document
     */
    public OccurrencesList(int document, int position) {
        size = 32;
        occurrences = new int[size];
        numberOfElements = 0;

        numberOfElements+=2;

        occurrences[numberOfElements-2] = document;
        occurrences[numberOfElements-1] = position;

    }

    /**
     *
     * @param document number
     * @param position in the document
     */
    public void addOccurrence(int document, int position) {
        if(isFull()) {
            doubleSize();
        }

        numberOfElements+=2;

        occurrences[numberOfElements-2] = document;
        occurrences[numberOfElements-1] = position;

    }

    /**
     *
     * @return all occurrences
     */
    public int[] getOccurrences() {
        return occurrences;
    }

    /**
     *
     * @return occurrences tables size
     */
    public int getNumberOfElements() {
        return numberOfElements;
    }

    private boolean isFull() {
        return (occurrences.length == numberOfElements);
    }

    private void doubleSize() {

        size *= 2;
        int[] temp = new int[size];
        for(int i=0; i < numberOfElements; i++) {
            temp[i] = occurrences[i];
        }
        occurrences = temp;

    }

    @Override
    public String toString() {

        String s = "";
        for(int i = 0; i < numberOfElements; i+=2) {
            s += (" -> " + occurrences[i] + ":" + occurrences[i+1] +"\n");
        }

        return s;

    }


}
