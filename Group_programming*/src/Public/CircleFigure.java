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
    public FigShape getFigShape() { // 形を返す
        return FigShape.FILLRECT;
    }
}
