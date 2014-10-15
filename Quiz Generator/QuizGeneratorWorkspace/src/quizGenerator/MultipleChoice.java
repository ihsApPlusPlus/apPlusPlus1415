package quizGenerator;

public class MultipleChoice extends Question {
	private String[] answerChoices;
	private char answer;
	   
	public MultipleChoice(String content) {
	   super(content);
    }
	
	public void setAnswer(char answer) {
		this.answer = answer;
	}
	
    public char getAnswer() {
	   return this.answer;
    }
	   
	public void setAnswerChoices(String[] choices) {
	   this.answerChoices = choices;
	}
	
	//For library file:
	public String toString() {
		String question = "Multiple Choice: " + this.getContent() + " | ";
		for (int i = 0; i < this.answerChoices.length; i++) {
	       question += (char)(i + 65) + ": " + this.answerChoices[i] + " ";
		}
		question += " | Answer: " + this.getAnswer();
		question += " | Difficulty: " + this.getDifficulty() + "\r\n";
		return question;
	}
	
	//For new quiz
	public String toString2() {
	   String mc = this.getContent() + "\r\n";
	   for (int i = 0; i < this.answerChoices.length; i++) {
	      mc += " " + (char)(i + 65) + ": " + this.answerChoices[i] + "\r\n";
	   }
	   return mc;
	}
}