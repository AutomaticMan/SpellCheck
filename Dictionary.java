import java.util.Collections;

/**
 * @author Jiongming Fan, John Gardiner, Kendall Hickie and Blake Thomson
 * 
 * A Dictionary object maintains a list of words, and supports search operations to on the 
 * list as well as the option to add words to the dictions. 
 */
public class Dictionary extends Document {
	
    /**
     * Add a word into the current dictionary
     * @param newEntry- a new word entry into the dictionary
     */	
	@Override
	public boolean add(String newEntry) {
		int index = 0;
		
		while(index < size() && newEntry.compareTo(get(index)) > 0) {
			index++;
		}
		
		super.add(index, newEntry);
		return true;
	}
	
	/** 
     * Searches the dictionary to determine if it contains the input String as an entry. 
     * Returns true if a match is found, otherwise returns false.  Running time: O(lgn)
     * @param key- String object to be searched for in the dictionary
     * 
     * @return boolean- true if result found, otherwise false
     */
	@Override  
	public boolean contains(Object key) {
		boolean result = false;
		
		result = binarySearch((String)key, 0, size() - 1) >= 0;
		
		return result;
	}
	
	public int binarySearch(String key, int start, int end) {
		if(end < start) {
			return -1;
		}

		int mid = start + (end - start) / 2;
		String word = get(mid);
		
		if(key.compareTo(word) < 0) {
			return binarySearch(key, start, mid - 1);
		}
		else if(key.compareTo(word) > 0) {
			return binarySearch(key, mid + 1, end);
		}
		else {
			return mid;
		}
	}
}
