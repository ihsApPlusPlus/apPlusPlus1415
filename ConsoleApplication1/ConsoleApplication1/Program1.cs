using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Office.Tools.Word;
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
            //System.IO.File.WriteAllLines(txtFile, CreateTextFile(fileInput, txtFile));
            CreateTextFile(fileInput, saveFileAs);
            

        }
        public static void CreateTextFile(object fileName, object saveAsAFile) 
        {

            Console.WriteLine("Here1?"); 
            Word.Document doc1 = null;
            object missing = Type.Missing;
            object readOnly = true;
            object isVisual = false;
            string text;
            //string hiddenFile = fileName.ToString();
            Console.WriteLine("Here1.5");



            if(File.Exists((string)fileName)){               //CODE IS NOT REACHING THIS POINT, FILE DOES NOT EXIST ACCORDING TO CODE
                Console.WriteLine("Here2");
            try
            {
                //creates the document (LOOK AT WHAT IT MEANS)
                Word.Application wordApp = new Word.Application();

                doc1 = wordApp.Documents.Open(ref fileName, ref missing, ref readOnly, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref isVisual, ref missing, ref missing, ref missing, ref missing);
                doc1.Activate();
                text = doc1.Content.Text;
                //File.WriteAllText(hiddenFile, text);
                Console.WriteLine("Here3");

                InsertLineBreaks(doc1);
                //this.FindAndReplace();
                
                wordApp.Visible = false;

                doc1.Content.InsertBefore("This is the beginning of the Document \r\n\r\n");
                Console.WriteLine("Here4");
            }

            catch
            {
                
                Console.WriteLine("An error occured. Please check the file name of your word document, and whether the word document is valid.");


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



