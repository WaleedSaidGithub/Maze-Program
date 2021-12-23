# Maze-Program
Java project in which imports a text file that represents the dimensions of a maze. 
The program will find the path from the start square to the finish square in the maze. 
If the maze is not solvable, then the program will output a statement that the maze can't be solved.

There are two files: Maze.java and MazeSquare.java. The MazeSquare file is a class that represents a single square 
within a Maze. This class checks whether or not the specified mazesquare has a top and or right wall in order to know
if that square can lead us to the finish square or not. There are methods within this class which check the walls around
the square, the location of the square within the maze, whether or not the square is visited or not, and whether or not
the square has the correct structure of a maze square.

The Maze.java file loads the maze from the text file and attempts to solve. This file uses a stack data structure
that contains the sequence of squares visited to go from the start square (bottom of the stack) to the finish
square (top of the stack). If there is no solution, an empty stack is returned. The load method uses a scanner in order to 
read and load the maze file from the specified directory. This method reads the text file line-by-line in order
evaluate the maze. There are several try-catch statements that catch all the possible errors that could occur
(i.e the text file isn't found, text file isn't in right format of a maze). 
