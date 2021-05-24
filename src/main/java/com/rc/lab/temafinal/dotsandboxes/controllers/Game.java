package com.rc.lab.temafinal.dotsandboxes.controllers;

import com.google.gson.Gson;
import com.rc.lab.temafinal.dotsandboxes.connection.encryption.Encryption;

import com.rc.lab.temafinal.dotsandboxes.connection.message.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Game{
    @FXML
    Label labelPlayer1, labelPlayer2;

    private Player player1, player2;
    private int turns = 0;
    private final Encryption encryption = new Encryption();
    private final Gson json = new Gson();

    public void initialize(){

    }

    public Player getPlayer1(){
        return player1;
    }

    public void setPlayer1(Player player){
        this.player1 = player;
        labelPlayer1.setText(player1.getUsername());
    }

    public Player getPlayer2(){
        return player2;
    }

    public void setPlayer2(Player player) {
        this.player2 = player;
        labelPlayer2.setText(player2.getUsername());
    }

    public void startGame(){

    }


//    public void changeColorLine25(){
//        if(turn) {
//            line25.setStyle("-fx-stroke: red;");
//            line25.setStrokeWidth(12);
//            turn = false;
//        }
//        else{
//            line25.setStyle("-fx-stroke: green;");
//            line25.setStrokeWidth(12);
//            turn = true;
//        }
//    }
}
