import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.MouseListener;

class MyMouseListener implements MouseListener {
    private final JComponent component;
    private Clicker clicker;

    public MyMouseListener(JComponent component, Clicker clicker) {
        this.component = component;
        this.clicker = clicker;
    }

    public void mouseClicked(MouseEvent e) {
        clicker.addPoints();
        System.out.println("mouseClicked");
    }

    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEntered: " + component.getName());
//        Dimension preferredSize = component.getPreferredSize();
//        preferredSize.height += 20;
//        preferredSize.width += 20;
//        component.setPreferredSize(preferredSize);
//        component.invalidate();
//        SwingUtilities.getWindowAncestor(component).validate();
    }

    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExited: " + component.getName());
//        Dimension preferredSize = component.getPreferredSize();
//        preferredSize.height -= 20;
//        preferredSize.width -= 20;
//        component.setPreferredSize(preferredSize);
//        component.invalidate();
//        SwingUtilities.getWindowAncestor(component).validate();
    }

    public void mousePressed(MouseEvent e) {
//        System.out.println("mousePressed: " + component.getName());
    }

    public void mouseReleased(MouseEvent e) {
//        System.out.println("mouseReleased: " + component.getName());
    }
}