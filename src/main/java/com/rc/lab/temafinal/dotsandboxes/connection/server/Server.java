package com.rc.lab.temafinal.dotsandboxes.connection.server;

import com.rc.lab.temafinal.dotsandboxes.connection.message.Player;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {
    private final ServerSocket serverSocket = new ServerSocket(5001);
    private Player player1, player2;
    private ServerThread serverThread1, serverThread2;
    private int playerCounter = 0;

    public Server() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        System.out.println(" --- DOTS AND BOXES --- ");
        System.out.println(String.format(
                "[SERVER] Listening for socket connections on: %s:5001",
                InetAddress.getLocalHost().getHostAddress()));

        Server server = new Server();
        server.execute();
    }

    public void execute() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(socket, this);
            playerCounter++;
            switch (playerCounter){
                case 1:
                    serverThread1 = serverThread;
                    break;
                case 2:
                    serverThread2 = serverThread;
                    break;
            }
            serverThread.start();
        }
    }

    public Player getPlayer1(){
        return player1;
    }

    public void setPlayer1(Player player){
        player1 = player;
    }

    public Player getPlayer2(){
        return player2;
    }

    public void setPlayer2(Player player){
        player2 = player;
    }

    public int getPlayerCounter(){
        return playerCounter;
    }

//    public void sendPlayerToClient(String encryptedMessage, ServerThread serverThreadClient) throws IOException {
//        for(ServerThread serverThread: serverThreadList) {
//            if (serverThread1.equals(serverThreadClient)) {
//                serverThreadClient.sendPlayer(encryptedMessage);
//            }
//        }
//    }

    public void sendPlayerToClient(String encryptedMessage, ServerThread serverThreadClient) throws IOException {
        if (serverThread1.equals(serverThreadClient)) {
            serverThread1.sendPlayer(encryptedMessage);
        } else {
            if (serverThread2.equals(serverThreadClient)) {
                serverThread2.sendPlayer(encryptedMessage);
            }
        }
    }

    public void sendPlayerToOtherClient(String encryptedMessage, ServerThread serverThreadClient) throws IOException {
        if(!serverThread1.equals(serverThreadClient)){
            serverThread1.sendPlayer(encryptedMessage);
        }
        else {
            if (!serverThread2.equals(serverThreadClient)){
                serverThread2.sendPlayer(encryptedMessage);
            }
        }
    }
}
