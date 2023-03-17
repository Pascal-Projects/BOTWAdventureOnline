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
        String message;
        String response;
        while (true) {
            try {
                dos.writeUTF("Test");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
