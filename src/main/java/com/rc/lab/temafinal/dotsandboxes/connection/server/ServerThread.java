package com.rc.lab.temafinal.dotsandboxes.connection.server;

import com.google.gson.Gson;
import com.rc.lab.temafinal.dotsandboxes.connection.encryption.Encryption;
import com.rc.lab.temafinal.dotsandboxes.connection.message.Player;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread {
    private final Socket socket;
    private Server server;

    private Player player1, player2;

    private final Gson json = new Gson();
    private final Encryption encryption = new Encryption();

    public ServerThread(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        while(true) {
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
                Player player = json.fromJson(encryption.decryptMessage(bufferedReader.readLine()), Player.class);
                if(server.getPlayerCounter() == 1) {
                    server.setPlayer1(player);
                    player1 = player;
                    System.out.println(String.format("[SERVER] New client connected with username: %1$s and ip: %2$s\nPlayer1 is: %1$s",
                            server.getPlayer1().getUsername(),
                            server.getPlayer1().getInetAddress()));
                }
                else{
                    server.setPlayer2(player);
                    player2 = player;
                    System.out.println(String.format("[SERVER] New client connected with username: %1$s and ip: %2$s\nPlayer2 is: %1$s",
                            server.getPlayer2().getUsername(),
                            server.getPlayer2().getInetAddress()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(server.getPlayerCounter() == 2) {
                try {
                    server.sendPlayerToOtherClient(encryption.encryptMessage(json.toJson(player2)), this);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public void sendPlayer(String encryptedMessage) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println(encryptedMessage);
        printWriter.flush();
    }

    public Player getPlayer1() throws IOException {
        return player1;
    }
}
