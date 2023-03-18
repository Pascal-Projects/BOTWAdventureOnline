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

    private final String playerName;

    public ClientThread(Socket client, DataInputStream pdis, DataOutputStream pdos, String name) {
        clientSocket = client;
        dis = pdis;
        dos = pdos;
        playerName = name;
    }

    public static void addCommand(String command, String method) throws NoSuchMethodException {
        commands.put(command, ClientThread.class.getMethod(method));
    }

    @Override
    public void run() {
        Thread.currentThread().setName(playerName);
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
                e.printStackTrace();
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addCommands() throws NoSuchMethodException {
        addCommand("ping", "ping");

        addCommand("test", "test");
        addCommand("forward", "forward");
        addCommand("backward", "backward");
        addCommand("left", "left");
        addCommand("right", "right");
        addCommand("look", "printState");
    }

    public void ping() throws IOException {
        String msg = dis.readUTF();
        dos.writeUTF(msg);
    }

    public void test() throws IOException {
        System.out.println("Running test");
        System.out.println(dis.readUTF());
        dos.writeUTF("Received answer from Server");
    }

    public void forward() throws IOException {
        int playerX = dis.readInt();
        dos.writeBoolean(playerX != Server.getHeight() - 1);
        System.out.println("Answered command from Client: " + Thread.currentThread().getName() + " (" + clientSocket + ")");
    }

    public void backward() throws IOException {
        int playerX = dis.readInt();
        dos.writeBoolean(playerX != 0);
        System.out.println("Answered command from Client: " + Thread.currentThread().getName() + " (" + clientSocket + ")");
    }

    public void left() throws IOException {
        int playerY = dis.readInt();
        dos.writeBoolean(playerY != 0);
        System.out.println("Answered command from Client: " + Thread.currentThread().getName() + " (" + clientSocket + ")");
    }

    public void right() throws IOException {
        int playerY = dis.readInt();
        dos.writeBoolean(playerY != Server.getWidth() - 1);
        System.out.println("Answered command from Client: " + Thread.currentThread().getName() + " (" + clientSocket + ")");
    }

    public void printState() throws IOException {
        int playerX = dis.readInt();
        int playerY = dis.readInt();
        StringBuilder message = new StringBuilder("You look around and see: \n\n");
        Field playerField = Server.getField(playerX, playerY);
        if (playerField.getMonsters().isEmpty() && playerField.getMerchant() == null && playerField.getLoot().isEmpty() && !playerField.hasKoroks() && playerField.getHestu() == null) {
            dos.writeUTF("You look around, but don't see anything interesting.");
        } else {
            if (!playerField.getMonsters().isEmpty()) {
                message.append("Monsters: ");
                for (Character monster : playerField.getMonsters()) {
                    message.append("\n• ").append(monster.getName());
                }
            }
            if (!playerField.getLoot().isEmpty()) {
                message.append("\nLoot: ");
                for (Item item : playerField.getLoot()) {
                    if (item.getName().equals("Experience Potion")) {
                        message.append("\n• ").append(item.getName()).append(" (Experience points: ").append(item.getExperiencePoints()).append(", Value: ").append(item.getValue()).append(" Rupees)");
                    } else if (item.getName().equals("Health Potion")) {
                        message.append("\n• ").append(item.getName()).append(" (Health points: ").append(item.getHealthPoints()).append(", Value: ").append(item.getValue()).append(" Rupees)");
                    } else {
                        message.append("\n• ").append(item.getName()).append(" (Damage: ").append(item.getDamage()).append(", Value: ").append(item.getValue()).append(" Rupees)");
                    }
                }
            }
            if (playerField.hasKoroks()) {
                for (Korok korok : playerField.getKoroks()) {
                    message.append("\nKoroks: ").append(korok.getName());
                }
            }
            if (playerField.getHestu() != null) {
                message.append("\nHestu");
            }
            if (playerField.getMerchant() != null) {
                if (!playerField.getMonsters().isEmpty()) {
                    message.append("\n\n").append(playerField.getMerchant().getName()).append(", the merchant, hides from monsters.\n");
                    message.append("You can trade with ").append(playerField.getMerchant().getName()).append(" when there are no monsters on the field");
                } else {
                    message.append("\n").append(playerField.getMerchant().getName()).append(", the merchant who greets you.\n");
                }
            }
            dos.writeUTF(message.toString());
        }
    }
}
