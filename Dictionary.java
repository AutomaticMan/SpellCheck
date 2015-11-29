/**
 * @author Jiongming Fan, John Gardiner, Kendall Hickie and Blake Thomson
 * 
 * A Dictionary object maintains a list of words, and supports search operations to on the 
 * list as well as the option to add words to the dictions. 
 */
public class Dictionary extends Document {
	
    /** 
     * Searches the dictionary to determine if it contains the input String as an entry. 
     * Returns true if a match is found, otherwise returns false.  Running time: O(lgn)
     * @param key- String object to be searched for in the dictionary
     * 
     * @return boolean- true if result found, otherwise false
     */
	public boolean contains(String key) {
		//implement binary search of arraylist
		return super.contains(key);
	}
	
    /**
     * Add a word into the current dictionary
     * @param newEntry- a new word entry into the dictionary
     */	
	@Override
	public boolean add(String newEntry) {
		//add word in while maintaining sorted order
		return super.add(newEntry);
	}
}