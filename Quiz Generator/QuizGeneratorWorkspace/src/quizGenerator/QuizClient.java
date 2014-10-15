package quizGenerator;
import java.util.*;
import java.io.*;

public class QuizClient {
   public static void main(String[] args) throws FileNotFoundException {
	  //Variables to control various while loops in program:
      boolean isInUse = true; //Controls main loop
      boolean fileProcess1 = false; 
      boolean fileProcess2 = false;
      boolean fileProcess3 = false;
      Scanner reader = new Scanner(System.in);
      explainFunctionality(); 
      
      //Main loop:
      while (isInUse) {
         System.out.print("Enter a command, or type \"quit\" to exit quiz generator: ");
         String option = reader.nextLine();
         //Adding questions to library:
         if (option.equalsIgnoreCase("Add more questions")) {
        	int numNewQs;
            String questions = "";
            System.out.print("Number of questions to add: ");
            //Ensure that user enters a number:
            if (reader.hasNextInt()) {
               numNewQs = reader.nextInt();
               reader.nextLine();   
            } else { 
               System.out.println("Please enter a NUMBER to specify how many new questions you wish to add. ");
               System.out.println(); //For clarity
               reader.nextLine();
               continue; 
            }
            
            //Loop numNewQs times and ask for respective question types:
            for (int i = 0; i < numNewQs; i++) { 
               System.out.println("Valid question types include: multiple choice, free response, true or false, or fill in the blank.");
               System.out.print("Enter the type of question " + (i + 1) + ": "); 
               String q = reader.nextLine();  
               //Ensure that user enters valid question type:
               if (isAQuestion(q)) {
            	   
            	   //Multiple choice mechanics...
                   if (q.equalsIgnoreCase("multiple choice")) {
                	 System.out.println("*You may enter \"back\" to revert to the previous step*");
                     System.out.print("Please enter the question: ");
                     //Make sure user doesn't want to go back:
                     String nextCommand = reader.nextLine();
                     if (nextCommand.equalsIgnoreCase("back")) { 
                    	System.out.println(); //For clarity
                        i--;
                        continue;
                     }
                     MultipleChoice m = new MultipleChoice(nextCommand);
                     System.out.print("Enter the number of choices: ");
                     if (reader.hasNextInt()) {
                    	int numChoices = reader.nextInt();
                        //Ensure number is valid:
                        if (numChoices > 26 || numChoices <= 0) { 
                    	    System.out.println("Invalid number of choices.");
                    	    System.out.println(); //For clarity
                    	    i--;
                    	    reader.nextLine();
                    	    continue;
                        }
                        String[] answerChoices = new String[numChoices];
                        reader.nextLine();
                        //Populate answerChoices array
                        for (int j = 0; j < numChoices; j++) {
                           System.out.print("Enter answer choice " + ((char)(j + 65)) + ": ");
                           String choice = reader.nextLine();
                           answerChoices[j] = choice;
                        } 
                        m.setAnswerChoices(answerChoices);
                        System.out.print("Please enter the character of the correct answer to the above question: ");
                        String answer = reader.nextLine();
                        //Ensure that user entered a valid character that is in the alphabet, either upper or lower case, and is one of the answer choices:
                        if ((answer.length() != 1) || (((byte)answer.charAt(0) > (65 + numChoices - 1)) && ((byte)answer.charAt(0) > (97 + numChoices - 1)))
                           || ((byte)answer.charAt(0) > (65 + numChoices - 1) && (byte)answer.charAt(0) < 97) || (byte)answer.charAt(0) < 65 ||
                           (byte)answer.charAt(0) > 122) {
                           System.out.println("Invalid answer. Must be a single character, upper or lower case, that is also one of the answer choices.");
                           System.out.println(); //For clarity
                           i--;
                           continue;
                        } else {
                            m.setAnswer(answer.charAt(0));   
                        }
                        askForDifficulty(reader, m);
                        questions += m.toString();
                     } else {
                        System.out.println("Please enter a NUMBER to specify how many choices you want this multiple choice question to contain.");
                        System.out.println(); //For clarity
                        i--;
                        reader.nextLine();
                        continue;
                     }
                     
                  //Free response mechanics...
                  } else if (q.equalsIgnoreCase("free response")) { 
                	 System.out.println("*You may enter \"back\" to revert to the previous step*");
                     System.out.print("Please enter the question: ");
                     //Ensure user doesn't want to go back:
                     String nextCommand = reader.nextLine();
                     if (nextCommand.equalsIgnoreCase("back")) {
                    	   System.out.println(); //For clarity
                    	   i--;
                    	   continue;
                     }
                     FreeResponse fr = new FreeResponse(nextCommand);
                     askForDifficulty(reader, fr);
                     questions += fr.toString();
                     
                  //Fill in the blank mechanics...
                  } else if (q.equalsIgnoreCase("fill in the blank")) { 
                	 System.out.println("*You may enter \"back\" to revert to the previous step*");
                     System.out.println("Please enter the question, using \"____\" (underscore)");
                     System.out.print("to indicate the missing part: ");
                     String nextCommand = reader.nextLine();
                     //Ensure user doesn't want to go back:
                     if (nextCommand.equalsIgnoreCase("back")) {
                        System.out.println(); //For clarity
                    	i--;
                        continue;
                     }
                     FillInTheBlank fb = new FillInTheBlank(nextCommand);
                     System.out.print("Please enter the word that belongs in the blank (the answer): ");
                     fb.setAnswer(reader.nextLine()); 
                     askForDifficulty(reader, fb);
                     questions += fb.toString();
                     
                  //True or false mechanics...
                  } else if (q.equalsIgnoreCase("true or false")) {
                	 System.out.println("*You may enter \"back\" to revert to the previous step*"); 
                     System.out.print("Please enter the question: ");
                     String nextCommand = reader.nextLine();
                     //Ensure user doesn't want to go back:
                     if (nextCommand.equalsIgnoreCase("back")) {
                        System.out.println(); //For clarity
                    	i--;
                    	continue;
                     }
                     TrueOrFalse t = new TrueOrFalse(nextCommand);
                     System.out.println("Is the answer to this question \"true\"");
                     System.out.print("or \"false\"? ");
                     String answer = reader.nextLine();
                     if (answer.equalsIgnoreCase("false")) {
                       t.setAnswer("false");
                     } else if (answer.equalsIgnoreCase("true")) {
                       t.setAnswer("true");
                     } else {
                       System.out.println("Invalid answer.");
                       System.out.println(); //For clarity
                       i--;
                       continue;
                     }
                     askForDifficulty(reader, t);
                     questions += t.toString();
                  }
               } else {
                  i--;
                  System.out.println("Invalid question type.");
               }
               System.out.println(); //For clarity
            }
            if (questions.length() > 0) {
               fileProcess1 = true;
            }
            //Obtain name of library file:
            while (fileProcess1) {   
               System.out.println("Please enter the name of the .txt library file");
               System.out.print("on which you would like these questions to be placed: ");
               String fileName = reader.nextLine();
               File libraryFile = new File(fileName);
               if (libraryFile.exists()) {
            	  try {
            		 //Print questions to existing file:
            	     PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            	     out.print(questions);
            	     System.out.println("The new questions have been added to your file!");
            	     System.out.println(); //For clarity
            	     out.close();
            	     fileProcess1 = false;
            	  } catch (IOException exception) {
            	     System.out.println("An error occured. Please try entering your file name again.");
            	     continue;
            	  }   
               } else if (fileName.contains(".") && fileName.substring(fileName.indexOf("."), fileName.length()).equalsIgnoreCase(".txt")) {
            	  //Print questions to new file:
                  File newLibraryFile = new File(fileName);
                  PrintStream output = new PrintStream(newLibraryFile);
                  output.print(questions);
                  System.out.println("Your new library file is complete!");
                  System.out.println(); //For clarity
                  output.close();
                  fileProcess1 = false;
               } else {
            	  System.out.println("Invalid file name. You must include .txt at the end of the file name.");
            	  continue;
               }
            }   
            
         //Creating a new quiz:
         } else if (option.equalsIgnoreCase("new quiz")) {
            int numMC, numFR, numToF, numFitB; 
            
            //Ask user how many of each question, then assign variables:
            numMC = askForNumQs(reader, "multiple choice");
            numFR = askForNumQs(reader, "free response");
            numToF = askForNumQs(reader, "true or false");
            numFitB = askForNumQs(reader, "fill in the blank");
            
            //Obtain minimum difficulty:
            boolean askingForMin = true;
            int min = 0;
            while (askingForMin) {
               System.out.print("Please enter the minumum difficulty of the questions on your quiz: ");
               if (reader.hasNextInt()) {
                  min = reader.nextInt();
                  if (min <= 0 || min > 10) {
                     System.out.println("The mininum diificulty must be greater than 0 and less than or equal to 10.");
                     reader.nextLine();
                     continue;
                  }
                  reader.nextLine();
                  askingForMin = false;
               } else {
                  System.out.println("Please enter a NUMBER to specify the minimum difficulty.");
                  reader.nextLine();
                  continue;
               }
            }  
            //Obtain maximum difficulty:
            boolean askingForMax = true;
            int max = 0;
            while (askingForMax) {
               System.out.print("Please enter the maximum difficulty of the questions on your quiz: ");
               if (reader.hasNextInt()) {
                  max = reader.nextInt();
                  if (max <= 0 || max > 10 || max < min) {
                     System.out.println("The mininum difficulty must be greater than 0 and less than or equal to 10.");
                     System.out.println("It must also be greater than or equal to the minumum difficulty.");
                     reader.nextLine();
                     continue;
                  }
                  reader.nextLine();
                  askingForMax = false;
               } else {
                  System.out.println("Please enter a NUMBER to specify the maximum difficulty.");
                  reader.nextLine();
                  continue;
               }
            }   
            
            //Obtain name of library file:
            fileProcess2 = true;
            String newQuiz = "";
            while (fileProcess2) {
               System.out.print("Please enter the name of the library file from which you will be drawing questions: ");
               String fileName = reader.nextLine();
               File libraryFile = new File(fileName);
               if (libraryFile.exists()) {
                  ArrayList<Question> inputQuestions = Question.parse(libraryFile);
                  newQuiz = Question.filterQuestions(inputQuestions, max, min, numMC, numFR, numToF, numFitB);
                  fileProcess2 = false;
               } else {
                  System.out.println("Sorry, that file does not exist. Try again.");
                  continue;
               }
            }
            //Obtain name of new quiz file:
            fileProcess3 = true;
            while (fileProcess3) {
               System.out.print("Please enter the name of the new .txt file on which you would like this new quiz to be placed: ");
               String fileName = reader.nextLine();
               if (fileName.contains(".") && fileName.substring(fileName.indexOf("."), fileName.length()).equalsIgnoreCase(".txt")) {
            	  try {
                     File newQuizFile = new File(fileName);
                     PrintStream output = new PrintStream(newQuizFile);
                     output.print(newQuiz);
                     output.close();
                     System.out.println("Your new quiz has been generated!");
                     System.out.println(); //For clarity
                     fileProcess3 = false;
            	  } catch (FileNotFoundException exception) {
            		  System.out.println("An error occured: " + exception.getMessage() +  "Please try entering your file name again.");
            		  continue;
            	  }
               } else {
                  System.out.println("Invalid file name. Use .txt to indicate that is it a text file.");
                  continue;
               }
            }
            
         } else if (option.equalsIgnoreCase("quit")) {
            System.out.print("Thanks for using the Quiz Generator!");
            reader.close();
            isInUse = false;
            
         } else {
            System.out.println("Invalid command.");      
            System.out.println(); //For clarity
         }
      }  
   }
   
   //Ask user for a number until he/she enters a number:
   public static int askForNumQs(Scanner reader, String type) {
	  boolean isAsking = true;
	  int numQs;
	  while (isAsking) {
		 System.out.print("Number of " + type + " questions: ");
         if (reader.hasNextInt()) {
            numQs = reader.nextInt();
            if (numQs < 0) {
               System.out.println("There cannot be a negative number of " + type + " questions.");
               reader.nextLine();
               continue;
            }
            reader.nextLine();
            isAsking = false;
            return numQs;
         } else {
            System.out.println("Please enter a NUMBER to specify the number of " + type + " questions.");
            reader.nextLine();
            continue;
         }
	  }
	  return 0; //Because java doesn't understand that since there is a 'continue', numQs will always be initialized
   }
   
   //Asks user for a difficulty until he/she enters a valid difficulty:
   public static void askForDifficulty(Scanner reader, Question q) {
      boolean isAskingDifficulty = true;
      while (isAskingDifficulty) {
         System.out.print("On a scale of 1-10, how difficult is this question? ");
         if (reader.hasNextInt()) {
            int difficulty = reader.nextInt();
            if (difficulty <= 0 || difficulty > 10) {
               System.out.println("The difficulty must be less than or equal to 10 and greater than 0.");
               reader.nextLine();
               continue;
            } else {
               q.setDifficulty(difficulty);
               reader.nextLine();
               isAskingDifficulty = false;
            }
         } else {
            System.out.println("Please enter a NUMBER on a scale from 1-10 to specify difficulty.");
            reader.nextLine();
            continue;
         }
      }
   }
   
   //Checks to ensure user input is a valid question:
   public static boolean isAQuestion(String question) { 
      if (question.equalsIgnoreCase("true or false") ||
      question.equalsIgnoreCase("free response") || 
      question.equalsIgnoreCase("fill in the blank") || 
      question.equalsIgnoreCase("multiple choice")) {
         return true;
      } else { 
         return false;
      }
   }
   
   //Prompts user:
   public static void explainFunctionality() { 
      System.out.println("Welcome to the Quiz Generator, by Liam Bruno.");
      System.out.println("This program allows you to either (1) add quiz questions");
      System.out.println("to a library of questions, or (2) create a new quiz from");
      System.out.println("an existing library.");
      System.out.println();
      System.out.println("Please enter \"add more questions\" if you would like to add");
      System.out.println("questions to an existing library or make a new library.");
      System.out.println("Please enter \"new quiz\" if you would like to make a new quiz");
      System.out.println("from an existing library of questions.");
      System.out.println();
   }
}      
