import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.SwingConstants;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Jiongming Fan, John Gardiner, Kendall Hickie and Blake Thomson
 * 
 * SpellCheck.
 */
public class SpellCheck extends JFrame {
	
	/*
	 * Data
	 */
	public final static String DICTIONARY_LOCATION = "dictionary.txt";
	private Document dictionary;
	private Document document;
	private Document wordsAdded;
	private Document wordsNotAdded;
	private Iterator<String> currentWord;
	
	// To maintain a word count for the user
	private int wordIndex;
	private int wordCount;
	
	/*
	 * JPanels Objects
	 */
	private JPanel contentPane;
	private JPanel loadDocument;
	private JPanel saveDocuments;
	private JPanel displayResults;
	
	/*
	 * Objects for LoadDocument
	 */
	private JLabel lblWelcomeTitle;
	private JLabel lblSelect;
	private JButton btnLoadFile;
	private JButton btnClose;
	
	/* 
	 * Objects for DisplayResults
	 */
	private JLabel lblWordTitle;
	private JLabel lblWord;
	private JLabel lblWordCountTitle;
	private JLabel lblWordCount;
	private JButton btnNextWord;
	private JButton btnAddWord;
	private JButton btnDone;
	
	/* 
	 * Objects for SaveDocuments
	 */
	private JLabel lblSaveTitle;
	private JLabel lblSaveAddedWords;
	private JLabel lblSaveWordsNotAdded;
	private JButton btnSaveWordsNotAdded;
	private JButton btnSaveWordsAdded;
	private JButton btnLoadFileSD;
	private JButton btnCloseSD;
	
	/**
	 * Launching point for the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpellCheck frame = new SpellCheck();
					frame.setVisible(true);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	
	/**
	 * Class constructor initializes all objects, and calls methods to Setup 
	 * Spell Check.
	 */
	public SpellCheck() {
		//Initialize Dictionary
		dictionary = new Document();
		
		File file = new File(DICTIONARY_LOCATION);
		
		//Only load dictionary if it exists, otherwise the file will be created
		//when user adds words to the dictionary
		if(file.exists()) {
			loadFile(dictionary, file);
		}
		
		//Initialize Content Panel
		contentPane = new JPanel();
		loadDocument = new JPanel();
		saveDocuments = new JPanel();
		displayResults = new JPanel();
		
		//Initialize Objects for LoadDocument Panel
		lblWelcomeTitle = new JLabel();
		lblSelect = new JLabel();
		btnLoadFile = new JButton();
		btnClose = new JButton();
		
		//Initialize Objects for DisplayResults Panel
		lblWordTitle = new JLabel();
		lblWord = new JLabel();
		lblWordCountTitle = new JLabel();
		lblWordCount = new JLabel();
		btnNextWord = new JButton();
		btnAddWord = new JButton();
		btnDone = new JButton();
		
		//Initialize Objects for SaveDocuments Panel
		lblSaveTitle = new JLabel();
		lblSaveWordsNotAdded = new JLabel();
		lblSaveAddedWords = new JLabel();
		btnSaveWordsNotAdded = new JButton();
		btnSaveWordsAdded = new JButton();
		btnLoadFileSD = new JButton();
		btnCloseSD = new JButton();
		
		//Setup Panels
		setupContentPane();
		setupLoadDocumentPanel();
		setupDisplayResultsPanel();
		setupSaveDocumentsPanel();
		
		setupSpellCheck();
		
		//Show Panel
		loadDocument.setVisible(true);
	}
	
	/**
     * Private method that handles setup of spell check for each document
     * check that is performed.
     */	
	private void setupSpellCheck() {
		//Initialize Document
		document = new Document();
		
		loadDocument.setVisible(false);
		displayResults.setVisible(false);
		saveDocuments.setVisible(false);
		
		btnNextWord.setEnabled(true);
	}
	
	
	/**
     * Private method that handles setup of the content pane.
     */
	private void setupContentPane() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
	}
	
	/**
     * Private method that handles setup of Swing components for the 
     * LoadDocument Panel.
     */
	private void setupLoadDocumentPanel() {
		
		//Add LoadDocument to ContentPane
		contentPane.add(loadDocument, "name_76583272863026");
		loadDocument.setLayout(null);
		
		//Setup Title Label
		lblWelcomeTitle.setText("Welcome to Spell Check");
		lblWelcomeTitle.setBounds(90, 48, 214, 19);
		lblWelcomeTitle.setFont(new Font("Dialog", Font.BOLD, 16));
		loadDocument.add(lblWelcomeTitle);
		
		//Setup Select File Label
		lblSelect.setText("Select a file to load:");
		lblSelect.setBounds(126, 132, 142, 15);
		loadDocument.add(lblSelect);
		
		//Setup Choose File Button
		btnLoadFile.setText("Choose File");
		btnLoadFile.setBounds(139, 152, 116, 25);
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooseFile();
				
				//Display DisplayResults panel
				loadDocument.setVisible(false);
				displayResults.setVisible(true);    
			}
		});
		loadDocument.add(btnLoadFile);
		
		//Setup Close Button
		btnClose.setText("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnClose.setBounds(301, 223, 117, 25);	
		loadDocument.add(btnClose);
	}
	
	/**
     * Private method that handles setup of Swing components for the 
     * DisplayResults Panel.
     */	
	private void setupDisplayResultsPanel() {
		
		//Setup Event Handler for DisplayResultsPanel Shown
		displayResults.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				
				//Run spell check and display results IFF found
				spellCheck();
				
				if(currentWord.hasNext()) {
					nextWord();
				}
				else {
					error("No matches found in document. ");
				}
			}
		});
		
		//Add DisplayResults to ContentPane
		contentPane.add(displayResults, "name_76595257931342");
		displayResults.setLayout(null);
		
		//Setup Word Title Label
		lblWordTitle.setText("Word:");
		lblWordTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblWordTitle.setFont(new Font("Dialog", Font.BOLD, 16));
		lblWordTitle.setBounds(20, 45, 70, 15);
		displayResults.add(lblWordTitle);
		
		//Setup Word Label- stores spell check results
		lblWord.setFont(new Font("Dialog", Font.BOLD, 16));
		lblWord.setBounds(79, 45, 339, 15);
		displayResults.add(lblWord);
		
		//Setup Word Count Title Label
		lblWordCountTitle.setText("Remaining Words:");
		lblWordCountTitle.setBounds(242, 112, 129, 15);
		displayResults.add(lblWordCountTitle);
		
		//Setup Word Count Label
		lblWordCount.setBounds(376, 112, 42, 15);
		displayResults.add(lblWordCount);
		
		//Setup Next Word Button
		btnNextWord.setText("Next Word");
		btnNextWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextWord();
			}
		});
		btnNextWord.setBounds(242, 139, 108, 25);
		displayResults.add(btnNextWord);
		
		//Setup Add Word Button
		btnAddWord.setText("Add Word to Dictionary");
		btnAddWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = lblWord.getText();
				
				currentWord.remove();
				dictionary.add(word);
				wordsAdded.add(word);
				
				btnAddWord.setEnabled(false);
			}
		});
		btnAddWord.setBounds(20, 139, 198, 25);
		displayResults.add(btnAddWord);
		
		//Setup Done Button
		btnDone.setText("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				//If changes are made to the dictionary, 
				//then save them to hard storage
				if(wordsAdded.size() > 0) {
					File file = new File(DICTIONARY_LOCATION);
					saveFile(dictionary, file);
				}
				
				//Display SaveDocuments panel
				displayResults.setVisible(false);
				saveDocuments.setVisible(true);
			}
		});
		btnDone.setBounds(301, 223, 117, 25);
		displayResults.add(btnDone);
	}
	
	/**
     * Private method that handles setup of Swing components for the 
     * SaveDocuments Panel.
     */
	private void setupSaveDocumentsPanel() {
		
		//Add LoadDocument to ContentPane
		contentPane.add(saveDocuments, "name_76599778942302");
		saveDocuments.setLayout(null);
		
		//Setup Title Label
		lblSaveTitle.setText("Would you like to save your results?");
		lblSaveTitle.setFont(new Font("Dialog", Font.BOLD, 16));
		lblSaveTitle.setBounds(12, 12, 406, 15);
		saveDocuments.add(lblSaveTitle);
		
		//Setup Save Added Words Label
		lblSaveAddedWords.setText("Save a list of words added to dictionary:");
		lblSaveAddedWords.setBounds(12, 60, 292, 15);
		saveDocuments.add(lblSaveAddedWords);

		//Setup Save Words Not Added Label
		lblSaveWordsNotAdded.setText("Save a list of words not added "
			+ "to dictionary:");
		lblSaveWordsNotAdded.setBounds(12, 123, 320, 15);
		saveDocuments.add(lblSaveWordsNotAdded);
		
		//Setup Save Words Button
		btnSaveWordsAdded.setText("Create File");
		btnSaveWordsAdded.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createFile(wordsAdded);
			}
		});
		btnSaveWordsAdded.setBounds(22, 86, 117, 25);
		saveDocuments.add(btnSaveWordsAdded);
		
		//Setup Save Words Not Added Button
		btnSaveWordsNotAdded.setText("Create File");
		btnSaveWordsNotAdded.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createFile(wordsNotAdded);
			}
		});
		btnSaveWordsNotAdded.setBounds(22, 150, 117, 25);
		saveDocuments.add(btnSaveWordsNotAdded);
				
		//Setup Close Button
		btnCloseSD.setText("Close");
		btnCloseSD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCloseSD.setBounds(301, 223, 117, 25);  
		saveDocuments.add(btnCloseSD);
		
		btnLoadFileSD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setupSpellCheck(); 
				
				chooseFile();
				
				//Display DisplayResults panel
				loadDocument.setVisible(false);
				displayResults.setVisible(true);    
			}
		});
		btnLoadFileSD.setText("Load New FIle");
		btnLoadFileSD.setBounds(156, 223, 133, 25);
		saveDocuments.add(btnLoadFileSD);
	}
	
	/**
     * Private method chooses the next word from the wordNotAdded list, 
     * then displays it to the user.
     */
	private void nextWord() {
		if(currentWord.hasNext()) {
			String word = currentWord.next();
			lblWord.setText(word);
			
			wordIndex++;
			String count = Integer.toString(wordCount - wordIndex);
			lblWordCount.setText(count);
			
			//Enable add word button for next word
			btnAddWord.setEnabled(true);	
			
			//Disable next word button when no words remain in wordsNotAdded
			if(!currentWord.hasNext()) {
				btnNextWord.setEnabled(false);	
			}
		}
	}
	
	/**
     * Private method that loads document file, otherwise program terminates.
     */
	private void loadFile(Document document, File file) {
		try {
			document.load(file);
		}
		catch(IOException e) {
	    	error("Failed to load " + file.getAbsolutePath() + 
	    		". Check file to see it exists. ");
		}
	}
	
	/**
     * Private method allows the user to save a file to a designated location.  
     * The document is saved to file, otherwise program terminates.
     */	
	private void saveFile(Document document, File file) {
		try {
			document.save(file);
		}
		catch(IOException e) {
			String message = "Failed to save " + file.getAbsolutePath() + 
				". Check directory to see if it can be written to. ";
			JOptionPane.showMessageDialog(null, message);
		}
	}
	
	/**
     * Private method allows the user to create a new file.  The document is 
     * saved to file, otherwise notify user.
     */
	private void createFile(Document document) {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setDialogTitle("Specify a file to save");    

		int selection = fileChooser.showSaveDialog(saveDocuments);
		if(selection == JFileChooser.APPROVE_OPTION) {
		    File file = fileChooser.getSelectedFile();
		    saveFile(document, file);
		}
	}
	
	/**
     * Private method that displays load file option to the user.  The selected 
     * file is loaded into the document list, otherwise program terminates.
     */
	private void chooseFile() {
	    JFileChooser fileChooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text", "txt", "text");
	    
	    fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle("Specify a file to load");    
        fileChooser.setCurrentDirectory(
        	new File(System.getProperty("user.home")));
        
        int selection = fileChooser.showOpenDialog(loadDocument);
        if (selection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            loadFile(document, file);
            document.trimPunctuation();	
        }
        else {
        	
        	//If the user doesn't want to load file, then terminate the program
        	System.exit(0);
        }
	}
	
	/**
     * Private method handles graceful termination of the program if it 
     * encounters an error.
     * 
     * @param message- a string that will be displayed to the user
     */
	private void error(String message) {
		JOptionPane.showMessageDialog(null, message);
		
		System.exit(0);
	}
	
	/**
     * Check the document list against the dictionary to determine if it 
     * contains any unmatched words.  Words fitting this criteria are added 
     * to the wordsNotAdded list.
     */
	private void spellCheck() {
		wordsAdded = new Document();
		wordsNotAdded = new Document();
		
		for(String word : document) {
			if(!dictionary.contains(word)) {
				wordsNotAdded.add(word);   
			}
		}
		
		wordCount = wordsNotAdded.size();
		currentWord = wordsNotAdded.iterator();
		wordIndex = 0;
	}
}
