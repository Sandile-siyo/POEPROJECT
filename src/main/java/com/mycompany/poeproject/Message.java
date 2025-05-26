package com.mycompany.poeproject;

import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import org.json.simple.JSONObject;

public class Message {

    private static int messageCount = 0;
    private static int totalMessagesSent = 0;

    String messageID;
    String recipient;
    String messageText;
    private String messageHash;

    // Generate a random 10-digit message ID
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
                return "Message successfully sent.";
            }
            case "2" -> {
                return "Press 0 to delete message.";
            }
            case "3" -> {
                storeMessage();
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

        try (FileWriter file = new FileWriter("stored_messages.json", true)) {
            file.write(obj.toJSONString() + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving message: " + e.getMessage());
        }
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

    // GUI-based interaction method for one message
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

        JOptionPane.showMessageDialog(null, printMessageDetails());

        String choice = JOptionPane.showInputDialog("Choose an option:\n1) Send Message\n2) Disregard Message\n3) Store Message");
        String result = sendMessageOption(choice);
        JOptionPane.showMessageDialog(null, result);
    }
}
