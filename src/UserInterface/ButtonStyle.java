package UserInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class ButtonStyle extends JButton implements MouseListener {
    private FontSyle defaultFont = new FontSyle();
    private Color textColor = Color.decode("#ededed");
    private Color backgroundColor = Color.decode("#0587a8");
    private Color hoverColor = Color.decode("#06b2dd");

    ButtonStyle(String s) {
        this.setFocusPainted(false);
        this.setText(s);
        this.setBorder(null);
        this.setForeground(textColor);
        this.setBackground(backgroundColor);
        this.setFont(defaultFont.getDefaultFont());
        this.setOpaque(true);
        addMouseListener(this);
    }

    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }
    public void setHoverColor(Color color) {
        hoverColor = color;
    }

    @Override public void mouseClicked(MouseEvent me) {}
    @Override public void mouseReleased(MouseEvent me) {}
    @Override public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == this && this.isEnabled()) {
            this.setBackground(this.hoverColor);
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource()==this) {
            this.setBackground(this.backgroundColor);
        }
    }
}