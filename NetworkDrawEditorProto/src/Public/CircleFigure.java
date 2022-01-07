package Public;

import java.awt.*;

public class CircleFigure extends Figure{
    
    public CircleFigure(int x, int y, int w, int h, Color c) {
        super(x,y,w,h,c);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(x,y,width,height);
    }
    public boolean judgein(int x, int y) {
        double rx = width/2, ry = height/2;
        double cx = this.x+rx, cy = this.y+ry; 
        double px = x - cx, py = rx/ry * (y - cy);
        int r = 6;
        if( (px*px + py*py <= (rx+r)*(rx+r)) && (px*px + py*py > (rx-r)*(rx-r)) ) {
            return true;
        }else{
            return false;
        }
    }
}
