import java.util.*;
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
		
		if(size() != 0 && contains(newEntry.toLowerCase())) {
			result = false;
		}
		else {
			result = super.add(newEntry.toLowerCase());
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		for(String word : this) {
			str.append(word + '\n');
		}
		
		return str.toString();
	}
	
	/**
	 * Load words from a file into this list.  Throws I/O exception if the file cannot 
	 * be read. 
	 * @param file- a file to be read.
	 * @throws IOException 
	 */
	public void load(File file) throws IOException {
		final String DELIMITER = " *\\s";
		
		try {
			FileReader fileReader = new FileReader(file);
			Scanner scanner = new Scanner(fileReader);
			scanner.useDelimiter(DELIMITER);
			
			//For each line in file
			while(scanner.hasNext()) {
				//For each word on line
				String word = scanner.next();
				word = trimWord(word);
				add(word);
			}
			
			scanner.close();
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
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			bufferedWriter.write(toString());
			
			bufferedWriter.close();
			fileWriter.close();
		}
		catch (IOException e) {
			throw e;
		}
	}
	
	/**
	 * Trim any trailing special character from an input string.
	 * @param word- a string to be trimmed
	 * @return string- a trimmed string.
	 */
	private static String trimWord(String word) {
		
		//Remove an instance of a special char from end of word
		final String TRIM = "\\W $";
		
		if(word != null) {
			word = word.replaceAll(TRIM, "");
		}
		
		return word;
	}
}
