package view;

import javax.swing.*;
import java.awt.*;

public class MessageView extends JPanel {
    private JTextArea messageBox;

    public MessageView(String initialMessage) {
        setLayout(null);
        setOpaque(false);
        setBounds(50, 600, 1180, 80);

        messageBox = new JTextArea(initialMessage);
        messageBox.setEditable(false);
        messageBox.setBackground(new Color(255, 255, 255, 230));
        messageBox.setFont(new Font("Monospaced", Font.BOLD, 22));
        messageBox.setLineWrap(true);
        messageBox.setWrapStyleWord(true);
        messageBox.setBounds(0, 0, 1180, 80);

        add(messageBox);
    }

    public void setMessage(String text) {
        messageBox.setText(text);
    }
}
