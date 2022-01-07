package Public;

import java.awt.*;

public class LaserPointFigure extends Figure {
    protected boolean selectable = true;

    public LaserPointFigure(int x,int y,int w,int h,Color c) {
        super(x,y,w,h,c);
    }
    public LaserPointFigure() {
        super(-16,-16,16,16,Color.RED);
    }
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x,y,width,height);
        g.setColor(new Color(255, 150, 150));
        g.fillOval(x+4, y+4, width-8, height-8);
        g.setColor(Color.WHITE);
        g.fillOval(x+6, y+6, width-12, height-12);
    }
    public boolean judgein(int x, int y) {
        if(selectable) {
            double rx = width/2, ry = height/2;
            double cx = this.x+rx, cy = this.y+ry;
            double px = x - cx, py = rx/ry * (y - cy);
            if( px*px + py*py <= rx*rx ) {
                selectable = false;
                return true;
            }
        }
        return false;
    }
}
