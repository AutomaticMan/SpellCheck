import java.util.ArrayList;
import java.io.*;

/**
 * @author Jiongming Fan, John Gardiner, Kendall Hickie and Blake Thomson
 * 
 * A Document object extends ArrayList and maintains a list of unique words.  Contains 
 * a method trimming trailing special characters from a word.
 */
public class Document extends ArrayList<String> {
	
	/**
	 * Add a unique string to this list.
	 * @param newEntry- a string to be added to this list
	 */
	@Override
	public boolean add(String newEntry) {
		boolean result;
		
		if(contains(newEntry)) {
			result = false;
		}
		else {
			result = super.add(newEntry);
		}
		
		return result;
	}
	
	/**
	 * Load words from a file into this list.  Throws I/O exception if the file cannot 
	 * be read. 
	 * @param file- a file to be read.
	 * @throws IOException 
	 */
	public void load(File file) throws IOException {
		final String DELIMITER = "\\s | *\\s";
		
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = bufferedReader.readLine();
			
			//For each line in file
			while(line != null ) {
				String[] words = line.split(DELIMITER);	
				
				//For each word on line
				for(String word : words) {
					word = trimWord(word);
					if(!word.equals("")) {
						add(word);
					}
				}
				
				line = bufferedReader.readLine();
			}
			
			bufferedReader.close();
			fileReader.close();
		}
		catch(IOException e) {
			throw e;
		}
		 
	}
	
	/**
	 * Save this list to a file.  Throws I/O exception if the file cannot be read.
	 * @param file- a file to write to.
	 * @throws IOException 
	 */
	public void save(File file) throws IOException {
		
		//save words
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		}
		catch (Exception IOException) {
			throw IOException;
		}
	}
	
	/**
	 * Trim any trailing special character from an input string.
	 * @param word- a string to be trimmed
	 * @return string- a trimmed string.
	 */
	public static String trimWord(String word) {
		
		//Remove an instance of a special char (separated by |) from end of word
		final String TRIM = "(!|\\?|:|;|,|\\.)$";
		
		if(word != null) {
			word = word.replaceAll(TRIM, "");
		}
		
		return word;
	}
	
	@Override
	public String toString() {
		String result = "";
		
		for(String word : this) {
			result += word + '\n';
		}
		
		return result;
	}
}
