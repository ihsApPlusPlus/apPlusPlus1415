package quizGenerator;
import java.util.*;
import java.io.*;

public abstract class Question {
   private int difficulty;
   private String content;
   
   public Question(String content) {
      this.content = content;
   }
   
   public String getContent() {
      return this.content;
   }
   
   public void setDifficulty(int difficulty) {
	   this.difficulty = difficulty;
   }  
   
   public int getDifficulty() {
      return this.difficulty;
   }
   
   public abstract String toString();
   
   //Turns library file into an array list of questions:
   public static ArrayList<Question> parse(File inFile) throws FileNotFoundException {
      ArrayList<Question> inputQuestions = new ArrayList<Question>();
      Scanner fileReader = new Scanner(inFile);
      //Loops through each line of file and creates new instances of respective question types, then
      //adds them to inputQuestions:
      while (fileReader.hasNextLine()) {
         String currentQ = fileReader.nextLine();
         if (currentQ.contains("Fill in the Blank")) {
            int pos1 = currentQ.indexOf(":") + 1;
            int pos2 = currentQ.indexOf("|");
            String content = currentQ.substring(pos1, pos2);
            FillInTheBlank f = new FillInTheBlank(content);
            String last = currentQ.substring(currentQ.length() - 1);
            int difficulty = Integer.parseInt(last);
            int realDifficulty;
            //If user entered 10 as difficulty, then difficulty was assigned 0, so:
            if (difficulty == 0) {
               realDifficulty = Integer.parseInt(currentQ.substring(currentQ.length() - 2, currentQ.length()));
               f.setDifficulty(realDifficulty);
            } else {
               f.setDifficulty(difficulty);
            }
            inputQuestions.add(f);
            
         } else if (currentQ.contains("Multiple Choice")) {
            int pos1 = currentQ.indexOf(":") + 1;
            int pos2 = currentQ.indexOf("|");
            String content = currentQ.substring(pos1, pos2);
            MultipleChoice m = new MultipleChoice(content);
            String last = currentQ.substring(currentQ.length() - 1);
            int difficulty = Integer.parseInt(last);
            int realDifficulty;
            //If user entered 10 as difficulty, then difficulty was assigned 0, so:
            if (difficulty == 0) {
               realDifficulty = Integer.parseInt(currentQ.substring(currentQ.length() - 2, currentQ.length()));
               m.setDifficulty(realDifficulty);
            } else {
               m.setDifficulty(difficulty);
            }
            int pos3 = currentQ.indexOf("|") + 1;
            int pos4 = currentQ.indexOf("|", pos3);
            String choices = currentQ.substring(pos3, pos4);
            String[] toBecomeAnswerChoices = choices.split(":");
            String[] realAnswerChoices = new String[toBecomeAnswerChoices.length - 1];
            for (int i = 0; i < realAnswerChoices.length; i++) {
               realAnswerChoices[i] = toBecomeAnswerChoices[i + 1].substring(0, toBecomeAnswerChoices[i + 1].length() - 1);
            }
            m.setAnswerChoices(realAnswerChoices);
            inputQuestions.add(m);
           
         } else if (currentQ.contains("Free Response")) {
            int pos1 = currentQ.indexOf(":") + 1;
            int pos2 = currentQ.indexOf("|");
            String content = currentQ.substring(pos1, pos2);
            FreeResponse fr = new FreeResponse(content);
            String last = currentQ.substring(currentQ.length() - 1);
            int difficulty = Integer.parseInt(last);
            int realDifficulty;
            //If user entered 10 as difficulty, then difficulty was assigned 0, so:
            if (difficulty == 0) {
               realDifficulty = Integer.parseInt(currentQ.substring(currentQ.length() - 2, currentQ.length()));
               fr.setDifficulty(realDifficulty);
            } else {
               fr.setDifficulty(difficulty);
            }
            inputQuestions.add(fr);
     
         } else if (currentQ.contains("True or False")) {
            int pos1 = currentQ.indexOf(":") + 1;
            int pos2 = currentQ.indexOf("|");
            String content = currentQ.substring(pos1, pos2);
            TrueOrFalse t = new TrueOrFalse(content);
            String last = currentQ.substring(currentQ.length() - 1);
            int difficulty = Integer.parseInt(last);
            int realDifficulty;
            //If user entered 10 as difficulty, then difficulty was assigned 0, so:
            if (difficulty == 0) {
               realDifficulty = Integer.parseInt(currentQ.substring(currentQ.length() - 2, currentQ.length()));
               t.setDifficulty(realDifficulty);
            } else {
               t.setDifficulty(difficulty);
            }
            inputQuestions.add(t);
         }
      }
      fileReader.close();
      shuffle(inputQuestions); //Must be randomized list
      return inputQuestions;
   }
   
   //Randomizes order of questions:
   public static void shuffle(ArrayList<Question> questions) {
      for (int i = 0; i < questions.size(); i++) {
         int rand = (int)(Math.random() * (questions.size() - i) + i);
         Question q = questions.get(i);
         questions.set(i, questions.get(rand));
         questions.set(rand, q);
      }
   }
   
   //Filters array list, then converts it into a string (new quiz):
   public static String filterQuestions(ArrayList<Question> questions, int max, int min, int numMC, int numFR, int numToF, int numFitB) {
      ArrayList<Question> filteredQuestions = new ArrayList<Question>();
      //Remove all questions that aren't in difficulty range:
      for (int i = 0; i < questions.size(); i++) {
         if (questions.get(i).getDifficulty() < min || questions.get(i).getDifficulty() > max) {
            questions.remove(questions.get(i));
            i--;
         }
      }
      //For each question, if we need more of that type, add it:
      for (Question q : questions) {
         if (q instanceof MultipleChoice && numMC > 0) {
            filteredQuestions.add(q);
            numMC--;
         } else if (q instanceof FreeResponse && numFR > 0) {
            filteredQuestions.add(q);
            numFR--;
         } else if (q instanceof TrueOrFalse && numToF > 0) {
            filteredQuestions.add(q);
            numToF--;
         } else if (q instanceof FillInTheBlank && numFitB > 0) {
        	filteredQuestions.add(q);
        	numFitB--;
         }
      }
      //Turn filteredQuestions into a string and return it:
      String newQuiz = "";
      for (Question q : filteredQuestions) {
    	 if (q instanceof MultipleChoice) {
            newQuiz += " Multiple choice: " + ((MultipleChoice)q).toString2() + "\r\n";
    	 } else if (q instanceof FreeResponse) {
    	    newQuiz += " Free response: " + q.getContent() + "\r\n" + "\r\n";
    	 } else if (q instanceof TrueOrFalse) {
    	    newQuiz += " True or false: " + q.getContent() + "\r\n" + "\r\n";
    	 } else if (q instanceof FillInTheBlank) {
    		newQuiz += " Fill in the blank: " + q.getContent() + "\r\n" + "\r\n";
    	 }
      }
      return newQuiz;
   }
}
