package com.botwAdventureOnline;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String ip = "87.78.179.241";

        Socket connection = new Socket(ip, 1337);

        DataInputStream dis = new DataInputStream(connection.getInputStream());
        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

        System.out.println(dis.readUTF());
        while (true) {
            //Run
        }
    }
}