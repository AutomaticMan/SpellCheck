import java.util.*;
import java.io.*;

/**
 * @author Jiongming Fan, John Gardiner, Kendall Hickie and Blake Thomson
 * 
 * A Document object extends TreeSet and adds features to specific to 
 * a document.  
 */
public class Document extends TreeSet<String> {
	
	/**
	 * Load words from a file into this list.  Throws I/O exception if the file
	 * cannot be read. 
	 * 
	 * @param file- a file to be read
	 * 
	 * @throws IOException 
	 */
	public void load(File file) throws IOException {
		final String DELIMITER = " *\\s";
		
		try {
			FileReader fileReader = new FileReader(file);
			Scanner scanner = new Scanner(fileReader);
			
			scanner.useDelimiter(DELIMITER);
			scanner.forEachRemaining(word -> add(word.toLowerCase()));
			
			scanner.close();
			fileReader.close();
		}
		catch(IOException e) {
			throw e;
		}	 
	}
	
	/**
	 * Save this list to a file.  Throws I/O exception if the file cannot 
	 * be read.
	 * 
	 * @param file- a file to write to
	 * 
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
	 * Trim a leading or trailing special character from words in this set.
	 * 
	 * @param word- a string to be trimmed
	 * 
	 * @return string- a trimmed string
	 */
	public void trimPunctuation() {
		//Any special characters at beginning or end of word are trimmed.  This
		//technique yielded the best results when testing actual text documents.
		final String TRIM = "(^\\W*)|(\\W*$)";		
		ArrayList<String> temp = new ArrayList<>();
		
		forEach(word -> temp.add(word.replaceAll(TRIM, "")));
		
		clear();
		addAll(temp);
	}
	
	/**
	 * Overrides add method to ensure that empty strings are not added into the
	 * document.
	 * 
	 * @param word- a string to be added to list
	 * 
	 * @return boolean- true if word was added, otherwise false
	 */
	@Override
	public boolean add(String word) {
		boolean result;
		
		if(word.equals("")) {
			result = false;
		}
		else {
			result = super.add(word);
		}
		
		return result;
	}
	
	/**
	 * Returns a string representation of this set.
	 * 
	 * @return string- a list of words in the document
	 */
	@Override
	public String toString() {
		final String DELIMITER = "\n";
		StringBuilder outputString = new StringBuilder();
		
		forEach(word -> outputString.append(word + DELIMITER));
		
		return outputString.toString();
	}
}
