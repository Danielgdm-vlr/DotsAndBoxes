package com.rc.lab.temafinal.dotsandboxes.controllers;

import com.google.gson.Gson;
import com.rc.lab.temafinal.dotsandboxes.connection.encryption.Encryption;

import com.rc.lab.temafinal.dotsandboxes.connection.message.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class ServerPage{
    @FXML
    Label labelPlayer1, labelPlayer2;

    private Player player1, player2;
    private boolean turn = true;
    private final Encryption encryption = new Encryption();
    private final Gson json = new Gson();

    public void initialize(){


    }

    public void setPlayers(Player player1, Player player2) throws IOException {
        this.player1 = player1;
        labelPlayer1.setText(player1.getUsername());
        this.player2 = player2;
        labelPlayer2.setText(player2.getUsername());
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
