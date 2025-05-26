// Updated PoEProject.java (main)
package com.mycompany.poeproject;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PoEProject {
    public static void main(String[] args) {
        Login objLogin = new Login();
        ArrayList<String> recentMessages = new ArrayList<>();

        // Registration Phase
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

        // Login Phase
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
            String option = JOptionPane.showInputDialog("Choose an option:\n1) Send Messages\n2) Coming Soon \n3) Quit");

            switch (option) {
                case "1" -> {
                    int count = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));
                    for (int i = 0; i < count; i++) {
                        Message msg = new Message();
                        msg.processMessage();
                        recentMessages.add(msg.printMessageDetails());
                    }
                    JOptionPane.showMessageDialog(null, "Total messages sent: " + Message.returnTotalMessages());
                }

                case "2" -> {
                    if (recentMessages.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No messages sent yet.");
                    } else {
                        StringBuilder sb = new StringBuilder("Recently Sent Messages:\n\n");
                        for (String detail : recentMessages) {
                            sb.append(detail).append("\n\n");
                        }
                        JOptionPane.showMessageDialog(null, sb.toString());
                    }
                }

                case "3" -> {
                    exit = true;
                    JOptionPane.showMessageDialog(null, "Exiting QuickChat. Goodbye!");
                }

                default -> JOptionPane.showMessageDialog(null, "Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }
}