package com.rc.lab.temafinal.dotsandboxes.connection.client;

import com.google.gson.Gson;
import com.rc.lab.temafinal.dotsandboxes.connection.encryption.Encryption;
import com.rc.lab.temafinal.dotsandboxes.connection.message.Player;
import com.rc.lab.temafinal.dotsandboxes.controllers.ClientPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class Client extends Application {
    private Socket socket;
    private Player player;
    private ClientPage clientPageController;

    private final Gson json = new Gson();
    private final Encryption encryption = new Encryption();

    public Client() throws IOException {
    }

    public Socket getSocket(){
        return socket;
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public ClientPage getClientPageController(){
        return clientPageController;
    }

    public void setClientPageController(ClientPage clientPageController){
        this.clientPageController = clientPageController;
    }

    @Override
    public void start(Stage stage) throws Exception{
        if(connect()) {
            loadStartPage(stage);
        }
        else {
            loadErrorConnectionPage(stage);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private boolean connect() {
        try {
            setSocket(new Socket(InetAddress.getLocalHost().getHostAddress(), 5001));
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private void loadStartPage(Stage primaryStage) throws IOException {
        URL pageURL = new File("src/main/java/com/rc/lab/temafinal/dotsandboxes/pages/ClientPage.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(pageURL);
        Parent root = loader.load();

        clientPageController = loader.getController();
        clientPageController.setClient(this);

        primaryStage.setTitle(String.format("Hello"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void loadErrorConnectionPage(Stage errorStage) throws IOException {
        URL pageURL = new File("src/main/java/com/rc/lab/temafinal/dotsandboxes/pages/ErrorConnection.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(pageURL);

        errorStage.setTitle("Error");
        errorStage.setScene(new Scene(root));
        errorStage.show();
    }

    public void getPlayerForOutputStream(String username) throws IOException {
        setPlayer(new Player(username, socket.getInetAddress()));
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println(encryption.encryptMessage(json.toJson(player)));
        printWriter.flush();
    }

    public Player getPlayer2() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return json.fromJson(encryption.decryptMessage(bufferedReader.readLine()), Player.class);
    }
}

