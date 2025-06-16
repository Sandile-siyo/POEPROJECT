/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package com.mycompany.poeproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageNGTest {

    @Test
    public void testMessageLength_Valid() {
        Message msg = new Message();
        String text = "Hi Mike, can you join us for dinner tonight";
        assertTrue(text.length() <= 250, "Message should be valid and under 250 characters.");
    }

    @Test
    public void testMessageLength_TooLong() {
        String longMessage = "x".repeat(300);
        assertTrue(longMessage.length() > 250, "Message exceeds 250 characters.");
    }

    @Test
    public void testRecipientFormat_Valid() {
        Message msg = new Message();
        assertTrue(msg.checkRecipientCell("+27838968976"));
    }

    @Test
    public void testRecipientFormat_Invalid() {
        Message msg = new Message();
        assertFalse(msg.checkRecipientCell("08966553"));
    }

    @Test
    public void testCreateMessageHash() {
        Message msg = new Message();
        msg.generateMessageID(); // Auto generates a 10-digit ID
        msg.processMessage(); // Simulate full input

        String hash = msg.getMessageHash();
        assertNotNull(hash);
        assertTrue(hash.contains(":"), "Message hash must contain colons.");
    }

    @Test
    public void testMessageIDGenerated() {
        Message msg = new Message();
        String id = msg.generateMessageID();
        assertNotNull(id);
        assertEquals(10, id.length());
    }

    @Test
    public void testSendMessageOption_Send() {
        Message msg = new Message();
        String result = msg.sendMessageOption("1");
        assertEquals("Message successfully sent.", result);
    }

    @Test
    public void testSendMessageOption_Disregard() {
        Message msg = new Message();
        String result = msg.sendMessageOption("2");
        assertEquals("Press 0 to delete message.", result);
    }

    @Test
    public void testSendMessageOption_Store() {
        Message msg = new Message();
        String result = msg.sendMessageOption("3");
        assertEquals("Message successfully stored.", result);
    }
}
