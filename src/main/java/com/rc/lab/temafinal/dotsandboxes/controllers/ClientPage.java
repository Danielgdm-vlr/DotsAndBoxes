package com.rc.lab.temafinal.dotsandboxes.controllers;

import com.google.gson.Gson;
import com.rc.lab.temafinal.dotsandboxes.connection.client.Client;
import com.rc.lab.temafinal.dotsandboxes.connection.encryption.Encryption;
import com.rc.lab.temafinal.dotsandboxes.connection.message.Player;
import com.rc.lab.temafinal.dotsandboxes.connection.server.ServerThread;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ClientPage {
    @FXML
    TextField textFieldUsername;
    @FXML
    Button buttonStartGame;

    private Client client;
    private Encryption encryption = new Encryption();
    private Gson json = new Gson();

    public Client getClient(){
        return client;
    }

    public void setClient(Client client){
        this.client = client;
    }


    public void initialize(){
        buttonStartGame.setDisable(true);
    }

    public void keyReleasedProperty(){
        buttonStartGame.setDisable(isNullOrEmpty(textFieldUsername.getText()));
    }

    private boolean isNullOrEmpty(String... strArr){
        for (String str : strArr) {
            if  (str == null || str.equals(""))
                return true;
        }
        return false;
    }

    public void startGame() throws IOException {
        getUsername();
        loadGame();
    }

    private void loadGame() throws IOException {
        buttonStartGame.getScene().getWindow().hide();
        URL pageURL = new File("src/main/java/com/rc/lab/temafinal/dotsandboxes/pages/Game.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(pageURL);
        Parent root = fxmlLoader.load();

        Game gameController = fxmlLoader.getController();
        //gameController.setPlayer(getPlayer1FromInputStream());
        gameController.setPlayer1(client.getPlayer());
        gameController.setPlayer2(getPlayer2());

        Stage gameStage = new Stage();
        gameStage.setTitle(String.format("%s`s game", gameController.getPlayer1().getUsername()));
        gameStage.setScene(new Scene(root));
        gameStage.show();
    }

    public void getUsername() throws IOException {
        if(client != null)
            client.getPlayerForOutputStream(textFieldUsername.getText());
    }

    public Player getPlayer2() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(client.getSocket().getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        while (true){
            try{
                String encryptedMessage = bufferedReader.readLine();
                return json.fromJson(encryption.decryptMessage(encryptedMessage), Player.class);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
