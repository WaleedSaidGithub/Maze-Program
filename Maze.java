//author: Aris Karamustafic and Waleed Said


import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Stack;


/**
* Maze represents a maze that can be navigated. The maze
* should indicate its start and end squares, and where the
* walls are.
*
* Eventually, this class will be able to load a maze from a
* file, and solve the maze.
* The starter code has part of the implementation of load, but
* it does not read and store the information about where the walls of the maze are.
*
*/
public class Maze {
    // Number of rows in the maze.
    private int numRows;

    // Number of columns in the maze.
    private int numColumns;

    // Grid coordinates for the starting maze square
    private int startRow;
    private int startColumn;

    // Grid coordinates for the final maze square
    private int finishRow;
    private int finishColumn;

    // Storing the maze squares in a list
    private List<MazeSquare> mazeSquares;

    /**
     * Creates an empty maze with no squares.
     */
    public Maze() {

        //  this default constructor creates an empty maze
        numRows = 0;
        numColumns = 0;
        startRow = 0;
        startColumn = 0;
        finishRow = 0;
        finishColumn = 0;
        mazeSquares = new ArrayList<MazeSquare>();

    }

    /**
     * Loads the maze that is written in the given fileName.
     */
    public boolean load(String fileName) {
        Scanner scanner = null;
        try {
            //Open a scanner to read the file
            scanner = new Scanner(new File(fileName));
            numColumns = scanner.nextInt();
            numRows = scanner.nextInt();
            startColumn = scanner.nextInt();
            startRow = scanner.nextInt();
            finishColumn = scanner.nextInt();
            finishRow = scanner.nextInt();

            //Check if the start or finish squares are out of bounds
            if(!isInRange(startRow, 0, numRows)
                    || !isInRange(startColumn, 0, numColumns)
                    || !isInRange(finishRow, 0, numRows)
                    || !isInRange(finishColumn, 0, numColumns)) {
                System.err.println("Start or finish square is not in maze.");
                scanner.close();
                return false;
            }
            scanner.nextLine();
            for (int i = 0; i < numRows; i++) {
              String rowLine = scanner.nextLine();
              if (rowLine.length() != numColumns) {
                return false;
              }
              for (int j = 0; j < rowLine.length(); j++) {
                if (MazeSquare.isAllowedCharacter(rowLine.charAt(j)) == false){
                  return false;
                }
                else {
                  MazeSquare newSquare = new MazeSquare(rowLine.charAt(j), i, j);
                  mazeSquares.add(newSquare);
                }
              }

            }
            
            //Add in code below here to finish reading the file and load and
            //store each square.

        } catch(FileNotFoundException e) {
            System.err.println("The requested file, " + fileName + ", was not found.");
            return false;
        } catch(InputMismatchException e) {
            System.err.println("Maze file not formatted correctly.");
            scanner.close();
            return false;
        } catch(NoSuchElementException e) {
            System.err.println("Maze squares inconsistent with the number of rows and columns. Lack maze squares.");
            scanner.close();
            return false;
        }

        return true;
    }

    /**
     * Returns true if number is greater than or equal to lower bound
     * and less than upper bound.
     * @param number
     * @param lowerBound
     * @param upperBound
     * @return true if lowerBound ≤ number < upperBound
     */
    private static boolean isInRange(int number, int lowerBound, int upperBound) {
        return number < upperBound && number >= lowerBound;
    }

    /**
     * Prints the maze with the start and finish squares marked. Does
     * not include a solution.
     */
     public void print(Boolean solveOrNot, Stack<MazeSquare> solutionStack) {
       List<MazeSquare> stackToList = new ArrayList<>();

         if (solveOrNot == true) {
           while (!solutionStack.isEmpty()) {
             stackToList.add(solutionStack.pop());
           }
           for (int i = stackToList.size() - 1; i > 0; i--) {
             solutionStack.push(stackToList.get(i));
           }
         }
         for(int row = 0; row < numRows; row++) {

             //Print each of the lines of text in the row
             for(int charInRow = 0; charInRow < 4; charInRow++) {
                 //Need to start with the initial left wall.
                 if(charInRow == 0) {
                     System.out.print("+");
                 } else {
                     System.out.print("|");
                 }

                 for(int col = 0; col < numColumns; col++) {
                     MazeSquare curSquare = this.getMazeSquare(row, col);
                     if(charInRow == 0) {

                         if(curSquare.hasTopWall()) {
                             System.out.print(getTopWallString());
                         } else {
                             System.out.print(getTopOpenString());
                         }
                     } else if(charInRow == 1 || charInRow == 3) {

                         if(curSquare.hasRightWall()) {
                             System.out.print(getRightWallString());
                         } else {
                             System.out.print(getOpenWallString());
                         }
                     } else {

                         if(startRow == row && startColumn == col) {
                             System.out.print("  S  ");
                         } else if(finishRow == row && finishColumn == col) {
                             System.out.print("  F  ");
                         } else if (solveOrNot == true && stackToList.contains(curSquare)){

                               System.out.print("  *  ");


                         } else {
                           System.out.print("     ");
                         }
                         if(curSquare.hasRightWall()) {
                             System.out.print("|");
                         } else {
                             System.out.print(" ");
                         }
                     }
                 }


                 System.out.print("\n");
             }
         }


         printFullHorizontalRow(numColumns);
     }


    private static void printFullHorizontalRow(int numColumns) {
        System.out.print("+");
        for(int row = 0; row < numColumns; row++) {

            System.out.print(getTopWallString());
        }
        System.out.print("\n");
    }



    private static String getTopWallString() {
        return "-----+";
    }


    private static String getTopOpenString() {
        return "     +";
    }


    private static String getRightWallString() {
        return "     |";
    }


    private static String getOpenWallString() {
        return "      ";
    }


    public MazeSquare getMazeSquare(int row, int col) {
      int sqIndex = ((numColumns * row) + col);
      return mazeSquares.get(sqIndex);

    }

    private void unmarkSquares() {
      for (MazeSquare mazeSq : mazeSquares) {
        mazeSq.unmarkVisited();
        }

    }

    private MazeSquare getUnvisitedNeighbor(MazeSquare square) {
      int row = square.getRow();
      int col = square.getColumn();

      // Check "UP"
      if (row > 0 && !square.hasTopWall()) { // to make sure row-1 is not negative
        MazeSquare up = getMazeSquare(row-1, col);
        if (!up.getVisited()) {
          return up;
        }
      }

        // Check "DOWN"
      if (row + 1 < numRows) { // to make sure row+1 is not out of bound
        MazeSquare down = getMazeSquare(row+1, col);
        if (!down.hasTopWall() && !down.getVisited()) {
          return down;
        }
      }

   // Check "LEFT"
   // Your code
      if (col > 0) {
        MazeSquare left = getMazeSquare(row, col-1);
        if (!left.hasRightWall() && !left.getVisited()) {
          return left;
        }
      }

   // Check "RIGHT"
   // Your code
      if (col + 1 < numColumns && !square.hasRightWall()) {
        MazeSquare right = getMazeSquare(row, col+1);
        if (!right.getVisited()) {
          return right;
        }
      }

      return null;
    }

    /**
    * Computes and returns a solution to this maze. If there are multiple
    * solutions, only one is returned, and getSolution() makes no guarantees about
    * which one. However, the returned solution will not include visits to dead
    * ends or any backtracks, even if backtracking occurs during the solution
    * process.
    *
    * @return a stack of MazeSquare objects containing the sequence of squares
    * visited to go from the start square (bottom of the stack) to the finish
    * square (top of the stack). If there is no solution, an empty stack is
    * returned.
    */
    public Stack<MazeSquare> getSolution() {
      unmarkSquares();
      Stack<MazeSquare> mazeStack = new Stack<>();
      MazeSquare startSquare = getMazeSquare(startRow, startColumn);
      mazeStack.push(startSquare);
      startSquare.markVisited();

      while (mazeStack.peek() != getMazeSquare(finishRow, finishColumn)) {


        if (getUnvisitedNeighbor(mazeStack.peek()) == null) {

            mazeStack.pop();

        }
         if (mazeStack.isEmpty()) {
             System.out.println("The maze is unsolvable");
             break;
        }



        if (getUnvisitedNeighbor(mazeStack.peek()) != null) {
          mazeStack.push(getUnvisitedNeighbor(mazeStack.peek()));
          mazeStack.peek().markVisited();
        }

      }



      return mazeStack;

    }

    public static void main(String[] args) {

      Maze mazeTrial = new Maze();
      if (args.length == 1) {
        if (mazeTrial.load(args[0])) {
          mazeTrial.print(false, null);
        }
      } else if (args.length == 2) {
          if (mazeTrial.load(args[0])) {
            if (args[1].equals("--solve")) {
              Stack<MazeSquare> solutionMaze = mazeTrial.getSolution();
              if (solutionMaze.isEmpty()) {
                mazeTrial.print(false, null);
                System.out.println("Maze can't be solved!");
              } else {
                mazeTrial.print(true, solutionMaze);
                System.out.println("Here is the solution to the maze!");
              }
            }

          }
      }
    }
}
