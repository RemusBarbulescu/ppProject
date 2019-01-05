package UserInterface;

import javax.swing.*;
import java.awt.*;

class TextFieldStyle extends JTextField {

    TextFieldStyle(boolean editable, Dimension dimension){
        this.setEditable(editable);
        this.setPreferredSize(dimension);
        this.setHorizontalAlignment(JTextField.CENTER);
        FontSyle defaultFont = new FontSyle();
        this.setFont(defaultFont.getDefaultFont());
    }
}
