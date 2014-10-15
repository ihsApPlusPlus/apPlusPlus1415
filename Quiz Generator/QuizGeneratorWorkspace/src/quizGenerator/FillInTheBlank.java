package quizGenerator;

public class FillInTheBlank extends Question {
	private String answer;
	   
    public FillInTheBlank(String content) {
	   super(content);
	}
	   
	public void setAnswer(String answer) {
	   this.answer = answer;
	}
	
	public String getAnswer() {
	   return this.answer;
	}
	
	public String toString() {
	   String question = "Fill in the Blank: " + this.getContent();
	   question += " | Answer: " + this.getAnswer();
	   question += " | Difficulty: " + this.getDifficulty() + "\r\n";
	   return question;
	}
}