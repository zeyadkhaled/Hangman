import java.util.Scanner;

public class Hangman 
{
   // properties
   private StringBuffer secretWord;
   private StringBuffer allLetters;
   private StringBuffer usedLetters;
   private int numberOfIncorrectTries;
   private int maxAllowedIncorrectTries;
   private StringBuffer knownSoFar;
   
   // constructors
   public Hangman() {
		String stars; 

		allLetters = new StringBuffer( "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		maxAllowedIncorrectTries = 6;
		numberOfIncorrectTries = 0;
		usedLetters = new StringBuffer( ""); // not camelCase because it is given this way in the assignment
		secretWord = new StringBuffer( chooseSecretWord()); // chooseSecretWord() return type is not specified, assume it returns a String rather than a StringBuffer
		stars = "";
		for ( int i = 0; i < secretWord.length(); i++ ) {
			stars = stars + "*";
		}
		knownSoFar = new StringBuffer( stars);
	}
   
   
   public static void main( String[] args)
   {
      Scanner scan = new Scanner( System.in);
      
      // constants
      final int INVALID_INPUT = -1;
      final int USED_LETTER = -2;
      final int GAME_OVER = -3;
      
      // variables
      Hangman hangman;
      char triedLetter;
      int tryThisResult;
      
      // program code
      hangman = new Hangman();
      
      // print all letters
      System.out.println( hangman.getAllLetters()); 
      
      
      tryThisResult = 0;
      do
      {
         // interface update
         System.out.println( "Secret Word: " + hangman.getKnownSoFar());
         System.out.println( "Used Letters: " + hangman.getUsedLetters());
         System.out.println( "Remaining tries: " + (hangman.getMaxAllowedIncorrectTries() - hangman.getNumOfIncorrectTries()));
         
         // takes and tries a given letter
         System.out.print( "Please enter a letter: ");
         triedLetter = scan.next().charAt(0);
         tryThisResult = hangman.tryThis( triedLetter); // stores error code
         
         if ( tryThisResult == INVALID_INPUT)
         {
            System.out.println( "Invalid letter.");
         }
         else if ( tryThisResult == USED_LETTER)
         {
            System.out.println( "Letter is already used.");
         }
         else if ( tryThisResult == GAME_OVER)
         {
            // we would need to print the secret word but there is no method to get the secret word
            System.out.println( "Game over!");
         }
      } while ( !hangman.isGameOver());
      
      if ( hangman.hasLost())
      {
         System.out.println( "SORRY YOU LOST! :(");
      }
      else
      {
         System.out.println( "CONGRATS! YOU WON!");
      }
   }
   
   /**
    * Gets all letters
    * @return allLetters
    */
   public String getAllLetters() {
      return allLetters.toString();
   }
   
   /**
    * Gets used letters
    * @return usedLetters
    */
   public String getUsedLetters() {
      return usedLetters.toString();
   }
   
   /**
    * Gets number of incorrect tries
    * @return numberOfIncorrectTries
    */ 
   public int getNumOfIncorrectTries() {
      return numberOfIncorrectTries;
   }
   
   /**
    * Gets number of maximum allowed tries
    * @return getMaxAllowedIncorrectTries
    */  
   public int getMaxAllowedIncorrectTries() {
      return maxAllowedIncorrectTries;
   }
   
   /**
    * Gets the letters known so far
    * @return knownSoFar
    */ 
   public String getKnownSoFar() {
      return knownSoFar.toString();
   }
   
   public int tryThis(char letter) {
	   
	   int count;
	   boolean isCorrect;
	   
	   letter = Character.toLowerCase(letter);
	   // Check if it is valid char.
	   if ( ( letter < 'a' || letter > 'z')) {
	      return -1;
	   }

	   else if ( usedLetters.indexOf( Character.toString(letter)) != -1) {
	      return -2;
	   }

	   else {
	      count = 0;
	      usedLetters.append( Character.toString(letter));
	      isCorrect = false;
	      for ( int i = 0; i < secretWord.length(); i++) {
	         if ( secretWord.charAt(i) == letter) {
	            knownSoFar.deleteCharAt(i);
	            knownSoFar.insert(i, letter);
	            isCorrect = true;
	            count++;
	         }
	      }

	      if (!isCorrect) {
	         numberOfIncorrectTries++;
	      }

	      if (isGameOver() || hasLost()){
	         return -3;
	      } else {
	         return count;
	      }
	   }
	}
   
   /**
    * Checks game is over or not.
    * @return true if game is over, false if game is not over
    */
      public boolean isGameOver() {
      if ( hasLost() || secretWord.toString().equals(knownSoFar.toString()))
         return true;
      return false;
         
   }
      
   /**
    * Checks the user has lost or not.
    * @return true if the user has lost, false if the user has not lost
    */
   public boolean hasLost() {
      if ( numberOfIncorrectTries >= maxAllowedIncorrectTries)
         return true;
      return false;
   }
   
   private static String chooseSecretWord(){
		String[] words = { "HIPPOPOTAMUS" , "PLATIPUS" , "HELLOWORLD" , "JACKPOT" , "MICROWAVE" ,
						"SECTION" , "BILKENT" , "ABYSS" , "ALCHEMIST" , "HACKERMAN" , "SYNTAX" ,
						"RETURN" , "BATMAN" , "SMASHING" , "GRAPPLINGHOOK", "SENSITIVE", "UPPERCASE" };
		return words[ ( int )( Math.random()* words.length ) ].toLowerCase();
	}
   
   /**
    * Returns the string of properties
    * @return string of properties
    */
   public String toString() {
      return "Secret Word: " + secretWord.toString() + "\nAll letters: " + 
         allLetters.toString() + "\nUsed Letters: " + usedLetters.toString() + 
         "\nNumber of incorrect tries: " + numberOfIncorrectTries + 
         "\nNumber of maximum allowed tries: " + maxAllowedIncorrectTries + 
         "The letters known so far: "+ knownSoFar.toString();   
   }
   
   /**
    * Checks two Hangman whether they are equal or not
    * @param other Hangman object
    * @return true if objects are equal or false if they are not equal
    */
   public boolean equals( Hangman other) {
      if ( !secretWord.toString().equals( other.secretWord.toString()))
         return false;
      else if ( !allLetters.toString().equals( other.allLetters.toString()))
         return false;
      else if ( !usedLetters.toString().equals( other.usedLetters.toString()))
         return false;
      else if ( numberOfIncorrectTries != other.numberOfIncorrectTries)
         return false;
      else if ( maxAllowedIncorrectTries != other.maxAllowedIncorrectTries)
         return false;
      else if ( !knownSoFar.toString().equals( other.knownSoFar.toString()))
         return false;
      else
         return true;
   }     
}