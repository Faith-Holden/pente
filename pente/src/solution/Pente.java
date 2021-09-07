package solution;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Pente extends Application {
    Button newGameButton;
    Button resignGameButton;

    PenteBoard canvas = new PenteBoard();

    boolean isPlayer1 = true;

    public void start (Stage primaryStage){
        Pane root = new Pane();
        root.setPrefWidth(950);
        root.setStyle("-fx-background-color: black; "
                + "-fx-border-color: black; -fx-border-width:3");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        canvas.relocate(10,10);

        String buttonStyle1 = " -fx-background-color: rgb(100,100,100); -fx-text-fill: black; -fx-font-weight:bold; -fx-font-size:18px";
        String buttonStyle2 = " -fx-background-color: rgb(130,130,130); -fx-text-fill: black;-fx-font-weight:bold; -fx-font-size:18px";
        String labelStyle =  " -fx-background-color: rgba(100,100,100, 0.8);-fx-text-fill: black; -fx-font-weight:bold; -fx-font-size:18px";
        String labelStyle2 =  " -fx-background-color: rgb(150,150,150);-fx-text-fill: black; -fx-font-weight:bold; -fx-font-size:18px";

        Label message = new Label("When you're ready to start your game, click \"Start Game\"");
        message.setPrefWidth(250);
        message.setTextAlignment(TextAlignment.CENTER);
        message.setWrapText(true);
        message.setStyle(labelStyle2);
        message.relocate(680,300);



        Label player1Wins = new Label("Player 1 has won " + canvas.player1Wins + " games.");
        Label player2Wins = new Label("Player 2 has won " + canvas.player2Wins + " games.");

        player1Wins.relocate(680,25);
        player2Wins.relocate(680, 75);
        player1Wins.setPrefHeight(40);
        player2Wins.setPrefHeight(40);
        player1Wins.setPrefWidth(250);
        player2Wins.setPrefWidth(250);
        player1Wins.setAlignment(Pos.BASELINE_CENTER);
        player2Wins.setAlignment(Pos.BASELINE_CENTER);
        player1Wins.setStyle(labelStyle);
        player2Wins.setStyle(labelStyle);




        newGameButton = new Button("New Game");
        newGameButton.relocate(680,600);
        newGameButton.setPrefWidth(250);
        newGameButton.setPrefHeight(30);
        newGameButton.setStyle(buttonStyle1);

        resignGameButton = new Button("Resign");
        resignGameButton.relocate(680, 550);
        resignGameButton.setDisable(true);
        resignGameButton.setPrefWidth(250);
        resignGameButton.setPrefHeight(30);
        resignGameButton.setOnMouseExited(evt-> {
            resignGameButton.setStyle(buttonStyle1);
            resignGameButton.setText("Resign");
        });

        resignGameButton.setStyle(buttonStyle1);


        newGameButton.setOnMouseEntered(evt -> {
            if(!newGameButton.isDisabled()){
                newGameButton.setStyle(buttonStyle2);
                newGameButton.setText("New Game?");
            }
                });
        newGameButton.setOnMouseExited(evt-> {
            newGameButton.setStyle(buttonStyle1);
            newGameButton.setText("New Game");
        });
        newGameButton.setOnMousePressed(evt -> {
            if(!newGameButton.isDisabled()){
                canvas.newGameClicked = true;
                newGameButton.setStyle(buttonStyle1);
                canvas.onGoingGame=true;
                canvas.newPenteGame();
                isPlayer1 = true;
                newGameButton.setText("New Game");
                message.setText("Player 1's turn.");
                message.setTextAlignment(TextAlignment.CENTER);
                toggleButtons();
            }
        });
        newGameButton.setOnMouseReleased(evt -> {
            if(!newGameButton.isDisabled()){
                newGameButton.setStyle(buttonStyle2);
            }
        });


        resignGameButton.setOnMouseEntered(evt -> {
            if(!resignGameButton.isDisabled()){
                resignGameButton.setStyle(buttonStyle2);
                resignGameButton.setText("Resign?");
            }
        });
        resignGameButton.setOnMousePressed(evt -> {
            if(!resignGameButton.isDisabled()){
                resignGameButton.setStyle(buttonStyle1);
                if(isPlayer1){
                    canvas.player2Wins++;
                }else{
                    canvas.player1Wins++;
                }
                if(isPlayer1){
                    message.setText("Player 1 resigned.");
                }else{
                    message.setText("Player 2 resigned.");
                }
                resignGameButton.setText("Resign");
                player1Wins.setText("Player 1 has won " + canvas.player1Wins + " games.");
                player2Wins.setText("Player 2 has won " + canvas.player2Wins + " games.");
                canvas.onGoingGame = false;
                canvas.newPenteGame();
                canvas.newGameClicked = false;

            }
        });
        resignGameButton.setOnMouseReleased(evt -> {
            if(!resignGameButton.isDisabled()){
                resignGameButton.setStyle(buttonStyle2);
                toggleButtons();
            }
        });

        canvas.setOnMouseClicked(evt -> {
            if(canvas.newGameClicked){
                canvas.addDisc(evt, isPlayer1);
                canvas.drawPenteBoard(canvas.penteDiscs);
                if(canvas.checkWinConditions(canvas.penteDiscs.get(canvas.penteDiscs.size()-1))){
                    player1Wins.setText("Player 1 has won " + canvas.player1Wins + " games.");
                    player2Wins.setText("Player 2 has won " + canvas.player2Wins + " games.");
                    canvas.newGameClicked=false;
                    if(canvas.penteDiscs.get(canvas.penteDiscs.size()-1).isPlayer1){
                        message.setText("Player 1 won. \nIf you would like to play again, click \"New Game\"");
                    }else{
                        message.setText("Player 2 won. \nIf you would like to play again, click \"New Game\"");
                    }
                }else{
                    if(canvas.penteDiscs.get(canvas.penteDiscs.size()-1).isPlayer1){
                        message.setText("Player 2's turn.");
                    }else{
                        message.setText("Player 1's turn.");
                    }
                }

            }


        });

        root.getChildren().add(canvas);
        root.getChildren().add(message);
        root.getChildren().add(newGameButton);
        root.getChildren().add(resignGameButton);
        root.getChildren().add(player1Wins);
        root.getChildren().add(player2Wins);

        canvas.drawPenteBoard(canvas.penteDiscs);



        primaryStage.show();

    }

    private void toggleButtons(){
        newGameButton.setDisable(!newGameButton.isDisabled());
        resignGameButton.setDisable(!resignGameButton.isDisabled());
    }

    public static void main(String[] Args){
        launch(Args);
    }


    private class PenteBoard extends Canvas{
        boolean newGameClicked = false;
        boolean onGoingGame = false;
        public ArrayList<PenteDisc> penteDiscs;
        int[][] boardArray;
        private int player1Wins = 0;
        private int player2Wins = 0;

        PenteBoard(){
            super(650,650);
            penteDiscs = new ArrayList<>();
            boardArray = new int[13][13];
            for(int[] squares : boardArray){
                for(int square : squares){
                    square = 0;
                }
            }

        }

        public void newPenteGame(){
            boardArray = new int[13][13];
            for(int[] squares : boardArray){
                for(int square : squares){
                    square = 0;
                }
            }
            penteDiscs = new ArrayList<>();
            canvas.drawPenteBoard(penteDiscs);
        }

        public void addDisc(MouseEvent evt, boolean isFirstPlayer){
            int colNum = (int)(evt.getX()/50)+1;
            int rowNum = (int)(evt.getY()/50)+1;
            boolean spotTaken = false;

            if(onGoingGame){
                for(PenteDisc penteDisc : penteDiscs){
                    if(penteDisc.colNum == colNum && penteDisc.rowNum == rowNum){
                        spotTaken = true;
                        break;
                    }
                }

                if(!spotTaken){
                    penteDiscs.add(new PenteDisc(colNum, rowNum, isFirstPlayer));
                    if(isFirstPlayer){
                        boardArray[rowNum-1][colNum-1] = 1;
                    }else{
                        boardArray[rowNum-1][colNum-1] = 2;
                    }
                    isPlayer1 = !isPlayer1;



                    onGoingGame = !(checkWinConditions(penteDiscs.get(penteDiscs.size()-1)));
                    if(!onGoingGame){
                        if(isFirstPlayer){
                            player1Wins++;
                        }else{
                            player2Wins++;
                        }
                        toggleButtons();
                    }
                    if(onGoingGame){
                        onGoingGame = checkDrawCondition();
                    }
                }
            }

        }

        private boolean checkDrawCondition(){
            boolean drawnGame = false;
            for(int[] i : boardArray){
                for(int j : i){
                    if(j == 0){
                        drawnGame = true;
                    }
                }
            }
            return drawnGame;
        }

        private boolean checkWinConditions(PenteDisc penteDisc){
            int playerNum;
            if(penteDisc.isPlayer1){
                playerNum = 1;
            }else{
                playerNum = 2;
            }
            int row = penteDisc.rowNum-1;
            int col = penteDisc.colNum-1;
            boolean gameWon = false;

            int dir1 = 1;
            int dir2 = 1;
            int dir3 = 1;
            int dir4 = 1;

            int tempCol = col;
            int tempRow = row;
            for(int i = 0; i< 5; i++){
                if(tempCol+1<13 && boardArray[tempRow][tempCol+1]==playerNum){
                    dir1++;
                    tempCol++;
                }else{
                    break;
                }
            }
            //direction 1 (horizontal) -> same row -> check to the left
            tempCol = col;
            tempRow = row;
            for(int i = 0; i< 5; i++){
                if(tempCol-1>0 && boardArray[tempRow][tempCol-1]==playerNum){
                    dir1++;
                    tempCol--;
                }else{
                    break;
                }
            }
            //direction 2 (Vertical) -> same column -> check down
            tempCol = col;
            tempRow = row;
            for(int i = 0; i< 5; i++){
                if(tempRow+1<13 && boardArray[tempRow+1][tempCol]==playerNum){
                    dir2++;
                    tempRow++;
                }else{
                    break;
                }
            }
            //direction 2 (Vertical) -> same column -> check up
            tempCol = col;
            tempRow = row;
            for(int i = 0; i< 5; i++){
                if(tempRow-1>0 && boardArray[tempRow-1][tempCol]==playerNum){
                    dir2++;
                    tempRow--;
                }else{
                    break;
                }
            }
            //direction 3(diagonal from top left to bottom right) -> check to the right and down
            tempCol = col;
            tempRow = row;
            for(int i = 0; i< 5; i++){
                if(tempCol+1<13 && tempRow+1<13 && boardArray[tempRow+1][tempCol+1]==playerNum){
                    dir3++;
                    tempCol++;
                    tempRow++;
                }else{
                    break;
                }
            }
            //direction 3(diagonal from top left to bottom right) -> check to the left and up
            tempCol = col;
            tempRow = row;
            for(int i = 0; i<5; i++){
                if(tempCol-1>-1 && tempRow-1 >-1 && boardArray[tempRow-1][tempCol-1]==playerNum){
                    dir3++;
                    tempCol--;
                    tempRow--;
                }else{
                    break;
                }
            }
            //direction 4(diagonal from bottom left to top right) -> check to the right and up
            tempCol = col;
            tempRow = row;
            for(int i = 0; i<5; i++){
                if(tempCol+1<13 && tempRow-1>-1 && boardArray[tempRow-1][tempCol+1]==playerNum){
                    dir4++;
                    tempCol++;
                    tempRow--;
                }else{
                    break;
                }
            }
            //direction 4(diagonal from bottom left to top right) -> check to the left and down
            tempCol = col;
            tempRow = row;
            for(int i = 0; i<5; i++){
                if(tempCol-1>-1 && tempRow+1<13 &&  boardArray[tempRow+1][tempCol-1]==playerNum){
                    dir4++;
                    tempCol--;
                    tempRow++;
                }else{
                    break;
                }
            }

            if(dir1>=5 || dir2>=5 || dir3>=5 || dir4>=5){
                gameWon = true;
            }

            return gameWon;
        }

        private void drawPenteBoard(ArrayList<PenteDisc> penteDiscs){

            GraphicsContext graphicsContext = getGraphicsContext2D();
            graphicsContext.setFill(Color.rgb(100,100,100));
            graphicsContext.fillRect(0,0,getWidth(),getHeight());
            graphicsContext.setStroke(Color.rgb(0,0,0, .2));

            for(int i = 0; i<15; i++){
                for(int j = 0; j<15; j++){
                    graphicsContext.strokeRect(50*i,50*j, 50, 50);
                }
            }
            for(PenteDisc penteDisc : penteDiscs){
                drawDisc(penteDisc.colNum, penteDisc.rowNum, penteDisc.isPlayer1);
            }
        }

        private void drawDisc (int colNum, int rowNum, boolean isPlayer1){
            GraphicsContext graphicsContext = getGraphicsContext2D();
            if(isPlayer1){
                graphicsContext.setFill(Color.NAVY);
            }else{
                graphicsContext.setFill(Color.MAROON);
            }

            graphicsContext.fillOval((colNum*50)-40,(rowNum*50)-40, 30,30);
        }

        private class PenteDisc{
            private final int colNum;
            private final int rowNum;
            private final boolean isPlayer1;

            PenteDisc(int colNum, int rowNum, boolean isPlayer1){
                this.colNum = colNum;
                this.rowNum = rowNum;
                this.isPlayer1 = isPlayer1;
            }
        }


    }




}
