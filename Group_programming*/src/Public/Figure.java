package Public;

import java.awt.*;
import java.io.Serializable;

// 描画した図形を記録する Figure クラス (継承して利用する)
public class Figure implements Serializable{
    protected int x,y,width,height;
    protected Color color;
    //protected Color lineColor;

    public Figure(int x,int y,int w,int h,Color c) {
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
    public void draw(Graphics g) { }
    public Shape getShape() { // 形の名称を返す. 継承後に再定義する
        return null;
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
    public boolean judgein(int x, int y) {
        return false;
    }
}