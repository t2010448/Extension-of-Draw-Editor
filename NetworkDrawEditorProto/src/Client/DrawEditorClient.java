package Client;

import Public.*;

import java.awt.*;
import java.util.*;

////////////////////////////////////////////
// Client Main
public class DrawEditorClient {
    protected CommClient cc;
    protected DrawModel model;
    protected DrawFrame frame;
    protected DrawController cont;
    
    public DrawEditorClient() {
        cc = new CommClient("localhost",10500);
        model  = new DrawModel(cc);
        cont   = new DrawController(model);
        frame  = new DrawFrame(model,cont);
        new DataReceiver(model, cc);
    }
    public static void main(String args[]) {
        new DrawEditorClient();
    }
}
////////////////////////////////////////////
// Client Model
@SuppressWarnings("deprecation")
class DrawModel extends Observable{
    protected CommClient cc;
    protected ArrayList<Figure> figures;
    protected Figure drawingFigure;
    protected Color currentColor;
    protected FigShape currentShape;

    public DrawModel(CommClient cc) {
        this.cc = cc;
        figures = new ArrayList<Figure>();
        drawingFigure = null;
        currentColor = Color.BLACK;
        currentShape = FigShape.RECTANGLE;
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }
    public Figure getFigure(int idx) {
        return figures.get(idx);
    }
    public Figure getDrawingFigure() {
        return drawingFigure;
    }
    public void setDrawingFigure(Figure f) {
        drawingFigure = f;
    }
    public void deleteDrawingFigure() {
        drawingFigure = null;
    }
    public void createFigure(int x,int y) {
        Figure f;
        switch(currentShape) {
            case FILLRECT :
                f = new FillRectFigure(x,y,0,0,currentColor);
                drawingFigure = f;
                break;
            case RECTANGLE :
                f = new RectangleFigure(x,y,0,0,currentColor);
                drawingFigure = f;
                break;
        }
        setChanged();
        notifyObservers();
    }
    public void setFigures(ArrayList<Figure> figList) {
        figures = figList;
        setChanged();
        notifyObservers();
    }
    public void addFigure(Figure f) {
        figures.add(f);
        setChanged();
        notifyObservers();
    }
    public void setColor(Color c) {
        currentColor = c;
    }
    public void setFigShape(FigShape s) {
        currentShape = s;
    }
    public void reshapeFigure(int x1,int y1,int x2,int y2) {
        if (drawingFigure != null) {
            drawingFigure.reshape(x1,y1,x2,y2);
            setChanged();
            notifyObservers();
        }
    }
    public void sendFigure(Figure f) {
        DataBox dataBox = new DataBox(Command.ADD_FIGURE, f);
        cc.send(dataBox);
    }
}