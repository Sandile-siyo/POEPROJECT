// Updated PoEProject.java (Main class)
package com.mycompany.poeproject;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class PoEProject {
    public static void main(String[] args) {
        Login objLogin = new Login();
        ArrayList<String> sentMessages = new ArrayList<>();
        ArrayList<String> disregardedMessages = new ArrayList<>();
        ArrayList<String> storedMessages = new ArrayList<>();
        ArrayList<String> messageHashes = new ArrayList<>();
        ArrayList<String> messageIDs = new ArrayList<>();

        String username = JOptionPane.showInputDialog("Enter your username:");
        while (!objLogin.checkUserName(username)) {
            JOptionPane.showMessageDialog(null, "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            username = JOptionPane.showInputDialog("Re-enter your username:");
        }
        JOptionPane.showMessageDialog(null, "Username successfully captured.");

        String password = JOptionPane.showInputDialog("Enter your password:");
        while (!objLogin.checkPasswordComplexity(password)) {
            JOptionPane.showMessageDialog(null, "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            password = JOptionPane.showInputDialog("Re-enter your password:");
        }
        JOptionPane.showMessageDialog(null, "Password successfully captured.");

        String cellphone = JOptionPane.showInputDialog("Enter your cellphone and include SA country code:");
        while (!objLogin.checkCellPhoneNumber(cellphone)) {
            JOptionPane.showMessageDialog(null, "Cellphone number incorrectly formatted or does not contain international code.");
            cellphone = JOptionPane.showInputDialog("Re-enter your cellphone:");
        }
        JOptionPane.showMessageDialog(null, "Cellphone number successfully added.");

        boolean loggedIn = false;
        while (!loggedIn) {
            String loginUser = JOptionPane.showInputDialog("Enter your username to log in:");
            String loginPass = JOptionPane.showInputDialog("Enter your password to log in:");
            if (!objLogin.loginUser(loginUser, loginPass, username, password)) {
                JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again.");
            } else {
                loggedIn = true;
            }
        }

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        boolean exit = false;
        while (!exit) {
            String option = JOptionPane.showInputDialog("Choose an option:\n1) Send Messages\n2) View Recent Messages\n3) Quit\n4) Reports\n5) Load Stored Messages");

            switch (option) {
                case "1" -> {
                    int count = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));
                    for (int i = 0; i < count; i++) {
                        Message msg = new Message();
                        msg.processMessage();

                        String status = msg.getStatus();
                        String hash = msg.getMessageHash();
                        String id = msg.getMessageID();
                        String content = msg.getMessageText();

                        messageHashes.add(hash);
                        messageIDs.add(id);

                        switch (status) {
                            case "Sent" -> sentMessages.add(content);
                            case "Stored" -> storedMessages.add(content);
                            case "Disregard" -> disregardedMessages.add(content);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Total messages sent: " + Message.returnTotalMessages());
                }

                case "2" -> {
                    if (sentMessages.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No messages sent yet.");
                    } else {
                        StringBuilder sb = new StringBuilder("Recently Sent Messages:\n\n");
                        for (String msg : sentMessages) {
                            sb.append("Sender: You\nMessage: ").append(msg).append("\n\n");
                        }
                        JOptionPane.showMessageDialog(null, sb.toString());
                    }
                }

                case "3" -> {
                    exit = true;
                    JOptionPane.showMessageDialog(null, "Exiting QuickChat. Goodbye!");
                }

                case "4" -> {
                    String reportOption = JOptionPane.showInputDialog("Choose a report option:\n1) Show sender & recipient of sent messages\n2) Show longest message\n3) Search by Message ID\n4) Search messages by recipient\n5) Delete message by hash\n6) Display full sent messages report");
                    switch (reportOption) {
                        case "1" -> {
                            StringBuilder info = new StringBuilder();
                            for (String msg : sentMessages) {
                                info.append("Sender: You\nRecipient: N/A\nMessage: ").append(msg).append("\n\n");
                            }
                            JOptionPane.showMessageDialog(null, info.toString());
                        }

                        case "2" -> {
                            String longest = "";
                            for (String msg : sentMessages) {
                                if (msg.length() > longest.length()) longest = msg;
                            }
                            JOptionPane.showMessageDialog(null, "Longest message: " + longest);
                        }

                        case "3" -> {
                            String searchID = JOptionPane.showInputDialog("Enter Message ID:");
                            int index = messageIDs.indexOf(searchID);
                            if (index != -1) {
                                JOptionPane.showMessageDialog(null, "Message: " + sentMessages.get(index));
                            } else {
                                JOptionPane.showMessageDialog(null, "Message ID not found.");
                            }
                        }

                        case "4" -> {
                            String recipient = JOptionPane.showInputDialog("Enter recipient:");
                            StringBuilder result = new StringBuilder();
                            for (String msg : sentMessages) {
                                if (msg.contains(recipient)) result.append(msg).append("\n");
                            }
                            JOptionPane.showMessageDialog(null, result.length() > 0 ? result : "No messages found.");
                        }

                        case "5" -> {
                            String hashToDelete = JOptionPane.showInputDialog("Enter hash to delete:");
                            int idx = messageHashes.indexOf(hashToDelete);
                            if (idx != -1) {
                                sentMessages.remove(idx);
                                messageHashes.remove(idx);
                                JOptionPane.showMessageDialog(null, "Message deleted.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Hash not found.");
                            }
                        }

                        case "6" -> {
                            StringBuilder report = new StringBuilder("Sent Messages Report:\n\n");
                            for (int i = 0; i < sentMessages.size(); i++) {
                                report.append("Hash: ").append(messageHashes.get(i)).append("\n")
                                      .append("Recipient: N/A\nMessage: ").append(sentMessages.get(i)).append("\n\n");
                            }
                            JOptionPane.showMessageDialog(null, report.toString());
                        }
                    }
                }

                case "5" -> {
                    ArrayList<String> loaded = Message.readStoredMessages();
                    JOptionPane.showMessageDialog(null, "Stored messages:\n" + String.join("\n", loaded));
                }

                default -> JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }
    }
}
