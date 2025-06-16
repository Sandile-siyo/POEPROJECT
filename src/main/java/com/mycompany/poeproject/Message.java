// Updated Message.java
package com.mycompany.poeproject;

import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Message {
    private static int messageCount = 0;
    private static int totalMessagesSent = 0;

    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status;
    private boolean sent;
    private boolean read;

    public String generateMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        this.messageID = sb.toString();
        return this.messageID;
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public boolean checkRecipientCell(String number) {
        return number.matches("^\\+27\\d{9}$");

    }

    public String createMessageHash() {
        if (messageID == null || messageText == null || messageText.trim().isEmpty())
            return "INVALID";

        String[] words = messageText.trim().split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String idPart = messageID.substring(0, 2);
        messageHash = (idPart + ":" + messageCount + ":" + firstWord + lastWord).toUpperCase();
        return messageHash;
    }

    public String sendMessageOption(String option) {
        switch (option) {
            case "1" -> {
                totalMessagesSent++;
                sent = true;
                status = "Sent";
                return "Message successfully sent.";
            }
            case "2" -> {
                status = "Disregard";
                return "Press 0 to delete message.";
            }
            case "3" -> {
                storeMessage();
                status = "Stored";
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid option.";
            }
        }
    }

    public void storeMessage() {
        JSONObject obj = new JSONObject();
        obj.put("MessageID", this.messageID);
        obj.put("Recipient", this.recipient);
        obj.put("Message", this.messageText);
        obj.put("Hash", this.messageHash);
        obj.put("Sent", this.sent);
        obj.put("Read", this.read);

        try (FileWriter file = new FileWriter("stored_messages.json", true)) {
            file.write(obj.toJSONString() + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving message: " + e.getMessage());
        }
    }

    public static ArrayList<String> readStoredMessages() {
        ArrayList<String> messages = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try (Scanner scanner = new Scanner(new File("stored_messages.json"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                JSONObject obj = (JSONObject) parser.parse(line);
                String formatted = "ID: " + obj.get("MessageID") + ", To: " + obj.get("Recipient") +
                                   ", Msg: " + obj.get("Message") + ", Hash: " + obj.get("Hash");
                messages.add(formatted);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error parsing messages: " + e.getMessage());
        }
        return messages;
    }

    public String printMessageDetails() {
        return "Message ID: " + messageID + "\n" +
               "Hash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + messageText;
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public void processMessage() {
        messageCount++;

        recipient = JOptionPane.showInputDialog("Enter recipient number (+27xxxxxxxxx):");
        while (!checkRecipientCell(recipient)) {
            recipient = JOptionPane.showInputDialog("Invalid number. Must start with +27 and contain 12 digits. Try again:");
        }

        messageText = JOptionPane.showInputDialog("Enter your message (max 250 characters):");
        while (messageText.length() > 250) {
            messageText = JOptionPane.showInputDialog("Message exceeds 250 characters by " + (messageText.length() - 250) + ". Please reduce size:");
        }

        generateMessageID();
        createMessageHash();
        read = false;

        JOptionPane.showMessageDialog(null, printMessageDetails());

        String choice = JOptionPane.showInputDialog("Choose an option:\n1) Send Message\n2) Disregard Message\n3) Store Message");
        String result = sendMessageOption(choice);
        JOptionPane.showMessageDialog(null, result);
    }

    public String getStatus() {
        return status;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }
}