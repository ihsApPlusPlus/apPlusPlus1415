This project's functionality is twofold:
1. It takes quiz questions as input from the user and places them into a library file. 
2. It can make a new quiz from a given library file of questions based on user preference. 
	-User preference includes: Number of each type of question and a difficulty range.

The main convenience of this program is that it can reduce the process of making a new quiz into a mere specification 
of how many of each type of question, either multiple choice, free response, true or false, or fill in the blank, 
and the difficulty range on a scale of 1-10. Teacher's jobs might now be a little easier. 

As an example, a teacher could enter a large number of questions into library files at the beginning of a school year, 
separate each unit of study into different library files, and when the teacher wishes to issue a quiz to students, 
it would be as simple as specifying the number of each type of questions and the difficulty.

Notes about program usage (mostly explained upon running the program itself):
	-The number of questions for any type can be specified as 0.
	-The difficulty MUST be on a scale from 1-10.
	-User may add questions to a library even if the library already exists.
	-User CANNOT add questions to a pre-existing quiz.
	
	-Invalid input is always taken care of.
	-There is no need to enter "multiple choice", "true or false", "fill in the blank", or "free response"
         before entering the content of a question, the program will take care of this.

Within the workspace folder, all classes can be found in the 'src' folder.