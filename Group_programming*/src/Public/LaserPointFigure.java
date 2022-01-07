package Public;

import java.awt.*;

public class LaserPointFigure extends Figure {

    public LaserPointFigure(int x,int y,Color c) {
        super(x,y,5,5,c);
    }
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x,y,width,height);
    }
    public FigShape getFigShape() { // 形を返す
        return FigShape.FILLRECT;
    }
}
