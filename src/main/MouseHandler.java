package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

    public boolean mouseClicked, mousePressed, mouseReleased, mouseEntered;
    GamePanel gp;

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) { // mouse button has been clicked (pressed and released) on a component

        if(gp.ui.pineWButtonBounds.contains(e.getX(), e.getY())) {
            mouseClicked = true;
            System.out.println("you clicked the pinewood asso app bar");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { // pressed on component/hold down a mouse button
        if(gp.ui.pineWButtonBounds.contains(e.getX(), e.getY())) {
            System.out.println("you pressed the pinewood asso app bar");
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) { // mouse button has been released
        if(gp.ui.pineWButtonBounds.contains(e.getX(), e.getY())) {
            mouseClicked = false;
            System.out.println("you released the pinewood asso app bar");
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) { // invoked when mouse enters a component like the area of a box
        if(gp.ui.pineWButtonBounds.contains(e.getX(), e.getY())) {
            System.out.println("you entered the pinewood asso app bar");
        }

    }

    @Override
    public void mouseExited(MouseEvent e) { // when mouse exits the area of a component
        if(gp.ui.pineWButtonBounds.contains(e.getX(), e.getY())) {
            System.out.println("you exited the component");
        }

    }
}
