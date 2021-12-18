package Client;

import java.awt.*;
import java.awt.event.*;

import Public.*;

////////////////////////////////////////////
// Controller
public class DrawController implements MouseListener,MouseMotionListener,KeyListener,ActionListener {
    protected DrawModel model;
    protected int dragStartX,dragStartY;

    public DrawController(DrawModel m) {
        model = m;
    }
    
    public void mouseClicked(MouseEvent e) { }
    public void mousePressed(MouseEvent e) {
        dragStartX = e.getX(); dragStartY = e.getY();
        model.createFigure(dragStartX,dragStartY);
    }
    public void mouseDragged(MouseEvent e) {
        FigShape f = model.currentShape;
        switch(f) {
            case LaserPoint :
                model.moveFigure(e.getX(), e.getY());
                break;
            default :
                model.reshapeFigure(dragStartX,dragStartY,e.getX(),e.getY());  
        }
    }
    public void mouseReleased(MouseEvent e) {
        model.sendFigure(model.getDrawingFigure());
        model.deleteDrawingFigure();
    }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseMoved(MouseEvent e) { }
    public void keyTyped(KeyEvent e) {
        switch(e.getKeyChar()) {
            case 'b':
                model.setColor(Color.BLACK);
                break;
            case 'y':
                model.setColor(Color.YELLOW);
                break;
            case 'g':
                model.setColor(Color.GREEN);
                break;
            case 'r':
                model.setColor(Color.RED);
                break;
            case 'f':
                model.setFigShape(FigShape.FILLRECT);
                break;
            case 'e':
                model.setFigShape(FigShape.RECTANGLE);
                break;
            //自作
            case 's':
                model.writeFile("a.txt");
            case 'l':
                model.readFile("a.txt");
        }
    }
    public void keyPressed(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    public void actionPerformed(ActionEvent e) { }
}