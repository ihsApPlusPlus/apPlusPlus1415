package quizGenerator;

public class TrueOrFalse extends Question {
	private boolean answer;
	   
	public TrueOrFalse(String content) {
	   super(content);
	}
	
	//User input was already determined to be "true" or "false"
	public void setAnswer(String answer) {
	   if (answer.equalsIgnoreCase("true")) {
          this.answer = true;
       } else {
	      this.answer = false;
	   }
    }
	   
	public boolean getAnswer() {
	   return this.answer;
	} 
	
	public String toString() {
	   String question = "True or False: " + this.getContent();
	   question += " | Answer: " + this.getAnswer();
	   question += " | Difficulty: " + this.getDifficulty() + "\r\n";
	   return question;
	}
}
	         