package Public;

import java.awt.*;
import java.util.ArrayList;

public class FreeHandFigure extends Figure {
    protected ArrayList<LineFigure> line;
    protected int x_end, y_end;
    
    public FreeHandFigure(int x,int y,Color c) {
        super(x,y,5,5,c);
        line = new ArrayList<LineFigure>();
        line.add(new LineFigure(x, y, width, height, color));
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g.setColor(color);
        for(LineFigure f: line) {
            g.drawLine(f.x, f.y, f.x_end, f.y_end);
        }
    }
    public void writeFreeHand(int x1, int y1) { //Arraylistに点を追加していく
        LineFigure f = line.get(line.size()-1);
        f.x_end = x1;
        f.y_end = y1;
        line.set(line.size()-1, f);
        f = new LineFigure(x1, y1, width, height, color);
        line.add(f);
    }
}
