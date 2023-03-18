package com.botwAdventureOnline;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ClientThread extends Thread {
    private static final Map<String, Method> commands = new HashMap<>();
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Socket clientSocket;

    public ClientThread(Socket client, DataInputStream pdis, DataOutputStream pdos) {
        clientSocket = client;
        dis = pdis;
        dos = pdos;
    }

    public static void addCommand(String command, String method) throws NoSuchMethodException {
        commands.put(command, ClientThread.class.getMethod(method));
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

        try {
            addCommands();
        } catch (NoSuchMethodException e) {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                String input = dis.readUTF();
                commands.get(input).invoke(this);
            } catch (IOException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addCommands() throws NoSuchMethodException {
        addCommand("test", "test");
    }

    public void test() throws IOException {
        System.out.println("Running test");
        System.out.println(dis.readUTF());
        dos.writeUTF("Received command from Server");
    }
}
