package Public;

import java.awt.*;

public class FillCircleFigure extends Figure{
    
    public FillCircleFigure(int x, int y, int w, int h, Color c) {
        super(x,y,w,h,c);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x,y,width,height);
    }
    public FigShape getFigShape() { // 形を返す
        return FigShape.FILLRECT;
    }
    public boolean judgein(int x, int y) {
        double rx = width/2, ry = height/2;
        double cx = this.x+rx, cy = this.y+ry;
        double px = x - cx, py = rx/ry * (y - cy);
        if( px*px + py*py <= rx*rx ) {
            return true;
        }else{
            return false;
        }
    }
}