package Client;

import Public.*;

import java.awt.*;
import java.util.*;
import java.io.*;

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
    protected String mode; // 描画:"draw",選択:"select",レーザーポインター:"laser"
    protected Figure handle; // 選択図形を強調するため＆選択の当たり判定のためのハンドル

    public DrawModel(CommClient cc) {
        this.cc = cc;
        figures = new ArrayList<Figure>();
        drawingFigure = null;
        currentColor = Color.BLACK;
        currentShape = FigShape.RECTANGLE;
        mode = "draw";
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
    public void setFigures(ArrayList<Figure> figList) {
        figures = figList;
        setChanged();
        notifyObservers();
    }
    public void setDrawingFigure(Figure f) {
        drawingFigure = f;
        setChanged();
        notifyObservers();
    }
    public void setColor(Color c) {
        currentColor = c;
    }
    public void setFigShape(FigShape s) {
        currentShape = s;
    }
    public void deleteFigure(Figure f) {
        figures.remove(f);
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
            case FREEHAND :
                f = new FreeHandFigure(x,y,currentColor);
                drawingFigure = f;
                break;
            case LASERPOINT :
                f = new LaserPointFigure(x,y,currentColor);
                drawingFigure = f;
                break;
        }
        setChanged();
        notifyObservers();
    }
    public void addFigure(Figure f) {
        figures.add(f);
        setChanged();
        notifyObservers();
    }
    public void reshapeFigure(int x1,int y1,int x2,int y2) {
        switch(currentShape) {
            case FREEHAND :
                FreeHandFigure f = (FreeHandFigure) drawingFigure; 
                f.writeFreeHand(x2,y2);
                setChanged();
                notifyObservers();
                break;
            default :
                if (drawingFigure != null) {
                    drawingFigure.reshape(x1,y1,x2,y2);
                    setChanged();
                    notifyObservers();
                }
        }
    }
    public void moveFigure(int x, int y) {
        if (drawingFigure != null) {
            drawingFigure.setLocation(x, y);
            setChanged();
            notifyObservers();
        }
    }
    public Figure selectFigure(int x, int y) {
        for(ListIterator<Figure> it = figures.listIterator(figures.size()); it.hasPrevious();) {
            Figure f = it.previous();
            if(f.judgein(x, y)) {
                return f;
            }
        }
        return null;
    }
    public void setMode(String s) {
        mode = s;
    }
    public String getMode() {
        return mode;
    }
    public void setHandle() {
        if(drawingFigure!=null) {
            handle = new Handle(drawingFigure);
        }else{
            handle = null;
        }
    }
    public Figure getHandle() {
        return handle;
    }
    public void sendFigure(Figure f) {
        DataBox dataBox = new DataBox(Command.ADD_FIGURE, f);
        cc.send(dataBox);
    }
    public void sendData(DataBox d) {
        cc.send(d);
    }
    public void sendData(ArrayList<Figure> f) {
        DataBox dataBox = new DataBox(Command.SET_FIGURES, f);
        cc.send(dataBox);
    }

    // ファイルセーブ
    public void writeFile(String filename) {
        try {
            FileOutputStream f = new FileOutputStream(new File(filename));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(figures);
            o.close();
            f.close();
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }
    //ファイルロード
    public void readFile(String filename) {
        try {
            FileInputStream fi = new FileInputStream(new File(filename));
            ObjectInputStream oi = new ObjectInputStream(fi);
            figures.clear();
            sendData((ArrayList<Figure>) oi.readObject());
            fi.close();
            oi.close();
            setChanged();
            notifyObservers();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}