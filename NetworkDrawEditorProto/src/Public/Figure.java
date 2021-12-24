package Public;

import java.awt.*;
import java.io.Serializable;

// 描画した図形を記録する Figure クラス (継承して利用する)
public class Figure implements Serializable{
    protected int x,y,width,height;
    protected Color color;
    protected int id; // 変形された図形の判別等に必要

    public Figure(int x,int y,int w,int h,Color c) {
        this.x = x; this.y = y;
        width = w; height = h;
        color = c;
        id = 0;
    }
    
    public void setLocation(int x,int y) {
        this.x = x; this.y = y;
    }
    public void setSize(int w,int h) {
        width = w; height = h;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Color getColor() {
        return color;
    }
    public int getId() {
        return id;
    }

    public void reshape(int x1,int y1,int x2,int y2) {
        int newx = Math.min(x1,x2);
        int newy = Math.min(y1,y2);
        int neww = Math.abs(x1-x2);
        int newh = Math.abs(y1-y2);
        setLocation(newx,newy);
        setSize(neww,newh);
    }

    
    // 以下、継承後に再定義が必要なメソッド


    // 描画する
    public void draw(Graphics g) { }

    // 図形内に座標が含まれているか判別する
    public boolean judgein(int x, int y) {
        return false;
    }

    // 形の名称を(FigShapeクラスで)返す
    public FigShape getShape() { 
        return null;
    }
}
