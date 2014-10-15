package quizGenerator;

public class FreeResponse extends Question {
	
	public FreeResponse(String content) {
	   super(content);
	}
	
	public String toString() {
       String question = "Free Response Question: " + this.getContent();
       question += " | Difficulty: " + this.getDifficulty() + "\r\n";
       return question;
	}
}
