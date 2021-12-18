import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.*;

class Figure implements Serializable {
    protected int x,y,width,height;
    protected Color color;
    public Figure(int x, int y, int h, int w, Color c) {
        this.x = x; this.y = y;
        width = w; height = h;
        color = c;
    }
    public void setSize(int w,int h) {
        width = w; height = h;
      }
      public void setLocation(int x,int y) {
        this.x = x; this.y = y;
      }
      public void reshape(int x1,int y1,int x2,int y2) {
        int newx = Math.min(x1,x2);
        int newy = Math.min(y1,y2);
        int neww = Math.abs(x1-x2);
        int newh = Math.abs(y1-y2);
        setLocation(newx,newy);
        setSize(neww,newh);
      }
      public void draw(Graphics g) {}
}

class circle extends Figure{
    public circle(int x,int y,int w,int h,Color c) {
        super(x,y,w,h,c);
        // 引数付きのコンストラクタは継承されないので，コンストラクタを定義．
        // superで親のコンストラクタを呼び出すだけ．
    }
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(x,y,width,height);
    }
}

class freeline extends Figure{

}

class drowEditorModel extends Observable {
    protected ArrayList<Figure> fig;
    protected Figure drawingFigure;
    protected String currentFigure; // 現在選択中の図形の形
    protected Color currentColor;
    public drowEditorModel() {
        fig = new ArrayList<Figure>();
        drawingFigure = null;
        currentColor = Color.black;
    }

    public ArrayList<Figure> getFigures() {
        return fig;
    }
    public Figure getFigure(int idx) {
        return fig.get(idx);
    }
    public void selectFigureShape(String shape) { //図形の形を選択する
        currentFigure = shape;
    }
    public void createFigure(int x, int y) {
        switch (currentFigure) {
            case "circle":
                Figure f = new circle(x, y, 0, 0, currentColor);
                break;
            case "rectangle":
                
                break;
            default:
                break;
        }
        Figure f = new /**/;
        fig.add(f);
        drawingFigure = f;
        setChanged();
        notifyObservers();
    }
    public void drowFreeline() {

    }
    public void drow
    public void reshapeFigure(int x1, int y1, int x2, int y2) {
        if (drawingFigure != null) {
            drawingFigure.reshape(x1,y1,x2,y2);
            setChanged();
            notifyObservers();
        }
    }
    public void undo() {

    }
    
}

class drowEditorModel_s extends drowEditorModel {
    protected drowEditorServer se;
    public drowEditorModel_s() {
        super();
        se = new drowEditorServer(10010);
    }
}