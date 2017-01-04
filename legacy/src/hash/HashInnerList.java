package hash;

import occurrences.OccurrencesList;

/**
 * A class of inner lists in hash map
 * 
 * @author <b>dardam</b>, michaldarda1@gmail.com
 * <br><b>starczyr</b>, radek.starczynowski@gmail.com
 */
public class HashInnerList {
    private Node first;
    
    private class Node
    {
        String key;
        OccurrencesList value;
        Node next;
        
        public Node(String key, OccurrencesList value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    
    /**
     * Get a value (OccurrencessList) of token given as 'key'
     * @see OccurrencesList
     * 
     * @param key word from index
     * @return instance of OccurrencesList if found, or null if not
     */
    public OccurrencesList get(String key) {
        for(Node x = first; x != null; x = x.next) {
            if(key.equals(x.key)) {
                return x.value;
            }
        }
        return null;
    }
    
    /**
     * Put a single word with it's occurrences in text
     * @see OccurrencesList
     * 
     * @param key word to put into index
     * @param value instance of OccurrencesList class of a single word
     */
    public void put(String key, OccurrencesList value) {
        for(Node x = first; x != null; x = x.next) {
            if(key.equals(x.key)) {
                x.value = value;
                return;
            }
        }
        first = new Node(key, value, first);
    }
    
    /**
     * Print single list
     */
    public void printInnerList() {
        for(Node x = first; x != null; x = x.next) {
            System.out.print(x.key + " -> " + x.value);
        }
    }
}
