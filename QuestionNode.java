//Programmer: Ben Rathbone
//CS 145
//Date: 8-8-23
//Assignment: Lab 6 - 20 Questions
//Purpose: A class that represents a question or answer in the 20 questions game

public class QuestionNode
{
   //---fields---
   public String data;
   public QuestionNode yes;
   public QuestionNode no;
   
   //---constructors---
   
   //constructs a leaf node with given data and null yes/no
   //this will serve as an answer in the game
   public QuestionNode(String data)
   {
      this.data = data;
      this.yes = null;
      this.no = null;
   }//end of constructor
                        
   //constructs a branch node with given data, yes subtree, and no subtree
   //this will serve as a question in the game
   public QuestionNode(String data, QuestionNode yes, QuestionNode no)
   {
      this.data = data;
      this.yes = yes;
      this.no = no;
   }//end of constructor
}//end of class