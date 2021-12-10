package Public;

import java.awt.*;

public class FillRectFigure extends Figure{
    
    public FillRectFigure(int x, int y, int w, int h, Color c) {
        super(x,y,w,h,c);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x,y,width,height);
    }
    public FigShape getFigShape() { // 形を返す
        return FigShape.FILLRECT;
    }
}
