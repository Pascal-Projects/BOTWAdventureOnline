package com.botwAdventureOnline;

import java.io.*;
import java.net.*;

public class ClientThread extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;

    final Socket clientSocket;

    public ClientThread(Socket client, DataInputStream dis, DataOutputStream dos) {
        this.clientSocket = client;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        System.out.println("Assigned to thread: " + Thread.currentThread().getName());
        String message;
        String response;
        try {
            dos.writeUTF("Welcome to the server!");

            dos.writeInt(Server.getHeight());

            dos.writeInt(Server.getWidth());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
