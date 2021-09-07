# pente

My solution for Chapter 7 Exercise 7 of “Introduction to Programming Using Java”.
Implementation note: I did not actually use the implementation provided in the "hint" (see problem description).
I believe I did not see the hint until after I had finished my program.

NOTE: This is a javafx program. It requires the javafx library as a dependency. (See bottom of this README for javafx instructions).

Problem Description:
The game of Go Moku (also known as Pente or Five Stones) is similar to Tic-Tac-Toe,
except that it is played on a much larger board and the object is to get five squares in a
row rather than three. The board should have 13 rows and 13 columns of squares. Players
take turns placing pieces on a board. A piece can be placed in any empty square. The
first player to get five pieces in a row—horizontally, vertically, or diagonally—wins. If all
squares are filled before either player wins, then the game is a draw. Write a program
that lets two players play Go Moku against each other.
Your program will be simpler than the Checkers program from Subsection 7.5.3. Play
alternates strictly between the two players, and there is no need to highlight the legal
moves. You will only need one nested subclass, a subclass of Canvas to draw the board
and do all the work of the game, like the nested CheckersBoard in the Chekers program.
You will probably want to look at the source code for the checkers program, Checkers.java,
for ideas about the general outline of the program.
The hardest part of the program is checking whether the move that a player makes is
a winning move. To do this, you have to look in each of the four possible directions from
the square where the user has placed a piece. You have to count how many pieces that
player has in a row in that direction. If the number is five or more in any direction, then
that player wins. As a hint, here is part of the code from my program. This code counts
the number of pieces that the user has in a row in a specified direction. The direction is
specified by two integers, dirX and dirY. The values of these variables are 0, 1, or -1, and
at least one of them is non-zero. For example, to look in the horizontal direction, dirX is
1 and dirY is 0.
int ct = 1; // Number of pieces in a row belonging to the player.
int r, c; // A row and column to be examined
r = row + dirX; // Look at square in specified direction.
c = col + dirY;
while ( r >= 0 && r < 13 && c >= 0 && c < 13
&& board[r][c] == player ) {
// Square is on the board, and it
// contains one of the player’s pieces.
ct++;
r += dirX; // Go on to next square in this direction.
c += dirY;
}
r = row - dirX; // Now, look in the opposite direction.
c = col - dirY;
while ( r >= 0 && r < 13 && c >= 0 && c < 13
&& board[r][c] == player ) {
ct++;
r -= dirX; // Go on to next square in this direction.
c -= dirY;
}

Javafx setup instructions:
Download javafx from: https://gluonhq.com/products/javafx/ (I used javafx 12). Save it to a location of your choice.
Unpack the zip folder.
Open my project with your IDE of choice (I use intellij IDEA).
Add the javafx/lib folder as an external library for the project. For intellij, this means going to "project structure" -> "libraries" -> "add library" ->{javafx location}/lib
Add the following as a VM argument for the project: --module-path "{full path to your javafx/lib folder}" --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics,javafx.media,javafx.swing,javafx.web
Build and run the project as normal.

