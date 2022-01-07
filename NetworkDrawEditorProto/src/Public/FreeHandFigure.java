package Public;

import java.awt.*;
import java.util.ArrayList;

public class FreeHandFigure extends Figure {
    public ArrayList<Figure> dots;
    
    public FreeHandFigure(int x,int y,Color c) {
        super(x,y,5,5,c);
        dots = new ArrayList<Figure>();
    }

    public void draw(Graphics g) {
        g.setColor(color);
        for(Figure f: dots) {
            g.fillOval(f.x, f.y, width, height);
        }
    }
    public FigShape getFigShape() { // 形を返す 
        return FigShape.RECTANGLE;
    }
    public void writeFreeHand(int x1, int y1) { //Arraylistに点を追加していく
        Figure f = new FillCircleFigure(x1, y1, width, height, color);
        dots.add(f);
    }
}