package Public;

import java.awt.*;

public class LineFigure extends Figure{
    protected int x_end, y_end;

    public LineFigure(int x,int y,int w,int h,Color c) {
        super(x,y,w,h,c);
        x_end = x;
        y_end = y;
    }
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawLine(x,y,x_end,y_end);
    }
}
