using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Office.Tools.Word;
using thisDocument = Microsoft.Office.Tools.Word.DocumentBase;
using Word = Microsoft.Office.Interop.Word;








namespace Project1
{
    class Project1
    {
        static void Main(string[] args)
        {
            
            Console.WriteLine("Please enter the full file path name WITHOUT QUOTES:");
            object fileInput = Console.ReadLine(); //reads in afile name (word docuemnt)
            Console.WriteLine("Please enter a new file name:");
            object saveFileAs = Console.ReadLine();
            CreateTextFile(fileInput, saveFileAs);
            

        }
        public static void CreateTextFile(object fileName, object saveAsAFile) 
        {

            
            Word.Document doc1 = null;
            object missing = Type.Missing;
            object readOnly = true;
            object isVisual = false;
            string text;
            

            if(File.Exists((string)fileName)){   
                
            try
            {
                
                Word.Application wordApp = new Word.Application(); //creates the word application which makes it able to be altered

                doc1 = wordApp.Documents.Open(ref fileName, ref missing, ref readOnly, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref isVisual, ref missing, ref missing, ref missing, ref missing);
                    //makes a new document that is the same as the one submitted

                doc1.Activate(); //activates the new document that is created
                text = doc1.Content.Text;
                doc1.Application.ActiveDocument.Range(doc1.Application.ActiveDocument.Content.Start, doc1.Application.ActiveDocument.Content.End).Select();
                
                wordApp.Visible = false;

                doc1.Application.ActiveDocument.Content.ParagraphFormat.Space2();

                //USE THIS FOR THE INDENTING LARGE QOUTES: doc1.Application.ActiveDocument.Content.ParagraphFormat.TabIndent(1);
                doc1.Paragraphs.IndentFirstLineCharWidth(4);






                wordApp.Visible = false;
            }

            catch
            {
                
                Console.WriteLine("An error occured. Please check the file name of your word document, and whether the word document is valid.");
                //if the file does not exist then an error appears

            }

            finally 
            {

                Console.WriteLine("Here5");
                Object fileFormat = Word.WdSaveFormat.wdFormatDocument;
                Object lockComments = true;
                Object password = false;
                Object addToRecentFiles = true;
                Object writePassword = false;
                Object readOnlyRecommended = false;
                Object embedTrueTypeFonts = true;
                Object saveNativePictureFormat = false;
                Object saveFormsData = true;
                Object saveAsAOCELetter = true;
                Object encoding = true;
                Object insertLineBreaks = true;
                Object allowSubstitutions = true;
                Object lineEnding = Word.WdLineEndingType.wdCRLF;
                Object addBiDiMarks = true;

                doc1.SaveAs(ref saveAsAFile, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
                Object saveChanges = Word.WdSaveOptions.wdSaveChanges;
                Object ogFormat = Word.WdOriginalFormat.wdOriginalDocumentFormat;
                Object routeDoc = true;
                Console.WriteLine("Here6");
               // doc1.Close(saveChanges, ogFormat, routeDoc);

                
                

            }
            
           
        }

            else {
                
                Console.WriteLine("an Error has occured, please check the file name");
            }
        }

       private static void InsertLineBreaks(Word.Document doc1)
        {
            throw new NotImplementedException();
        }

         }

    }



