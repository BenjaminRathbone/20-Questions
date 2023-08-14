//Programmer: Ben Rathbone
//CS 145
//Date: 8-8-23
//Assignment: Lab 6 - 20 Questions
//Purpose: A class that creates a binary tree of questions and answers
//         This tree is used by QuestionMain.java to play a game of 20 questions
//         Each question or answer is represented by a QuestionNode object
//         When you're done playing, you can save your tree to a .txt file
//         Previous trees can be loaded to pick up where you left off.

import java.io.*;
import java.util.*;

public class QuestionTree
{
   //---fields---
   private QuestionNode root;
	private UserInterface ui;
   private int totalGames;
   private int gamesWon;
   
   //---constructor---
   //initializes a new question tree
   //accepts ui, which is a UserInterface object
   public QuestionTree(UserInterface ui)
   {
      if (ui == null)   //if UserInterface is null
      {
         throw new IllegalArgumentException();
      }
      root = new QuestionNode("A:Computer"); //creates root
      this.ui = ui;
      totalGames = 0;
      gamesWon = 0;
   }//end of constructor
   
   //---methods---
   
   //plays one complete guessing game with the user
   //asks yes/no questions until reaching an answer object
   public void play()
   {
		root = play(root);   //calls the recursive overload play method
	}//end of play method
   
   //recursive overload play method
   //accepts QuestionNode current
   //returns an updated version of the current QuestionNode
   private QuestionNode play(QuestionNode current)
   {      
      //if this is an answer node
      if (current.data.startsWith("A:"))
      {
         ui.println("Alright, here's my guess...");
         ui.print("Are you thinking of " + current.data.substring(2) + "?");
         if (ui.nextBoolean())  //if yes
         {
            ui.println("Sweet! I got it right!");
            gamesWon++; //increases gamesWon
         }
         else  //if no
         {
            //prompts the user for their object and a question
            ui.println("Dang, I got it wrong!");
            ui.print("What object were you thinking of?");
            String object = "A:" + ui.nextLine();  //saves answer
            ui.print("Type a yes/no question to distinguish your object from " +
                              current.data.substring(2) + ":");
            String question = "Q:" + ui.nextLine(); //saves question
            ui.print("And what is the answer to this question?");
            if(ui.nextBoolean()) //if yes
            {
               //adds object to the yes subtree
               current = new QuestionNode(question, new QuestionNode(object), current);
            }
            else  //if no
            {
               //adds object to the no subtree
               current = new QuestionNode(question, current, new QuestionNode(object));
            }                  
         }
         totalGames++; //increases totalGames
      }//end of answer if
      
      //if this is a questionNode
      else
      {
         ui.print(current.data.substring(2)); //print the question
			if(ui.nextBoolean()) //if the user answers yes
         {
		      current.yes = play(current.yes); //recursive call, yes subtree
         }
			else  //if the user answers no
         {
			   current.no = play(current.no);   //recursive call, no subtree
         }
      }//end of question else
      return current;
   }//end of play method
   
   //stores the current tree state to an output file
   //accepts PrintStream output
	public void save(PrintStream output)
   {
		if(output == null)   //if PrintStream is null
      {
			throw new IllegalArgumentException();
      }
		save(output, root);  //calls the recursive overload play method
	}//end of save method
   
   //recursive overload save method
   //perfroms a preorder traversal of the tree to save it to the file
	private void save(PrintStream output, QuestionNode current)
   {
      if(current != null) //checks if the current node is empty
      {
			//saves the current node's data
         output.println(current.data);
         //performs a preorder traversal of the tree
			save(output, current.yes); //saves the yes subtree
         save(output, current.no);  //saves the no subtree 
		}
	}//end of save method
   
   //reads a file and uses its contents to replace the current tree
   //accepts Scanner input
   public void load(Scanner input)
   {
		if(input == null) //if Scanner is null
      {
			throw new IllegalArgumentException();
      }
		if(input.hasNextLine())
      {
			root = new QuestionNode(input.nextLine());
         //calls the recursive overload load method to load yes and no subtrees
			root.yes = load(root.yes, input);
			root.no = load(root.no, input);
		}
	}//end of load method
   
   //recursive overload load method (say that 5 times fast)
   //loads yes and no subtrees
   private QuestionNode load(QuestionNode node, Scanner input)
   {
		if(input.hasNext()) //if there is still data in the file
      {
			String data = input.nextLine();
			if(node == null)  //if this node is null
         {
				node = new QuestionNode(data); //create a new node
				if(data.startsWith("Q:")) //if the data is a question
            {
					//load yes and no subtrees (recursive call)
               node.yes = load(node.yes, input);
					node.no = load(node.no, input);
				}
			}	
      }
		else  //if there is no more data in the file
      {
	      node = null;
		}
		return node;
	}//end of load method
   
   //returns the total number of games played
	public int totalGames()
   {
		return totalGames;
	}//end of totalGames method
	
	//returns the number of games won by the program
	public int gamesWon()
   {
		return gamesWon;
	}//end of gamesWon method
}//end of class