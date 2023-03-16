package com.botwAdventureOnline;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket("87.78.179.241", 1337)){
            //reading the input from server
            //BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));

            //returning the output to the server : true statement is to flush the buffer otherwise
            //we have to do it manually
            PrintWriter output = new PrintWriter(socket.getOutputStream(),true);

            //taking the user input
            Scanner scanner = new Scanner(System.in);
            String userInput;
            String clientName = "empty";

            ClientRunnable clientRun = new ClientRunnable(socket);


            new Thread(clientRun).start();
            //loop closes when user enters exit command

            do {

                //reading the input from server
                if (clientName.equals("empty")) {
                    System.out.println("Enter your name ");
                    userInput = scanner.nextLine();
                    clientName = userInput;
                    output.println(userInput);
                }
                else {
                    String message = ( "(" + clientName + ")" + " message : " );
                    System.out.println(message);
                    userInput = scanner.nextLine();
                    output.println(message + " " + userInput);
                }
                if (userInput.equals("exit")) {
                    break;
                }

            } while (!"exit".equals(userInput));




        } catch (Exception e) {
            System.out.println("Exception occurred in client main: " + Arrays.toString(e.getStackTrace()));
        }
    }
}