package com.rc.lab.temafinal.dotsandboxes.connection.message;

import java.net.InetAddress;
import java.util.Objects;

public class Player {
    private String username;
    private InetAddress inetAddress;

    public Player(){
    }

    public Player(String username, InetAddress inetAddress){
        this.username = username;
        this.inetAddress = inetAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    @Override
    public String toString() {
        return "Player{username= " + username + ", inetAddress= " + inetAddress + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
