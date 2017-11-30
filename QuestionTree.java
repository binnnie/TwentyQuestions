import java.util.*;
import java.io.*;

public class QuestionTree{
   private QuestionNode root;
   private UserInterface ui;
   private int totalGames;
   private int gamesWon;
   
   public QuestionTree(UserInterface ui){
      nullCheck(ui);
      this.ui = ui;
      this.root = new QuestionNode("computer");
      this.totalGames = 0;
      this.gamesWon = 0;
   }
   
   public void play(){
      this.root = play(this.root);
      this.totalGames++;
   }
   
   private QuestionNode play(QuestionNode root){
      if(root.left == null && root.right == null){
         ui.print("Would your object happen to be " + root.data + "? ");
         if(!ui.nextBoolean()){
            root = addition(root);
            return root;
         }else{
            ui.println("I win!");
            gamesWon++;
            return root;
         }
      }else{
         ui.print(root.data);
         if(ui.nextBoolean()){
            root.left = play(root.left);
            return root;
         }else{
            root.right = play(root.right);
            return root;
         }
      }
   }
   
   private QuestionNode addition(QuestionNode root){
      ui.print("I lose. What  is your object? ");
      String correct = ui.nextLine();
      ui.print("Type a yes/no question to distinguish your\nitem from " + root.data + ": ");
      String question = ui.nextLine();
      ui.print("And what is the answer for your object? ");
      boolean answer = ui.nextBoolean();
      if(answer){
         return new QuestionNode(question, new QuestionNode(correct), root);
      }else{
         return new QuestionNode(question, root, new QuestionNode(correct));
      }
   }
   
   public void save(PrintStream output){
      nullCheck(output);
      save(output, this.root);
   }
   
   private void save(PrintStream output, QuestionNode root){
      if(root.left == null && root.right == null){
         output.println("A:" + root.data);
      }else{
         output.println("Q:" + root.data);
         save(output, root.left);
         save(output, root.right);
      }
   }
   
   public void load(Scanner input){
      nullCheck(input);
      this.root = load(input, input.nextLine());
   }
   
   private QuestionNode load(Scanner input, String dataIn){
      String[] splitData = dataIn.split(":", 2);
      if(splitData[0].equals("A")){
         return new QuestionNode(splitData[1]);
      }else{
         return new QuestionNode(splitData[1], load(input, input.nextLine()), 
                                 load(input, input.nextLine()));
      }
   }
   
   public int totalGames(){
      return this.totalGames;
   }
   
   public int gamesWon(){
      return this.gamesWon;
   }
   
   private void nullCheck(Object check){
      if(check == null){
         throw new IllegalArgumentException("Argument cannot be null.");
      }
   } 
}