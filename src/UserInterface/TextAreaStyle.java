package UserInterface;

import javax.swing.*;
import java.awt.*;

public class TextAreaStyle extends JTextArea {

    TextAreaStyle(boolean editable){
        this.setEditable(editable);
        Font defaultFont = new Font("Arial", Font.PLAIN, 15);
        this.setFont(defaultFont);
        this.setBackground(Color.decode("#111111"));
        this.setForeground(Color.decode("#aaaaaa"));
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
    }
}
