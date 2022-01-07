package Public;

import java.awt.*;
import java.util.ArrayList;

public class FreeHandFigure extends Figure {
    public ArrayList<Figure> dots;
    
    public FreeHandFigure(int x,int y,Color c) {
        super(x,y,5,5,c);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        
    }
    public FigShape getFigShape() { // 形を返す 
        return FigShape.RECTANGLE;
    }
    public void writeFreeHand(int x, int y) { //Arraylistに点を追加していく
        Figure f = new FillCircleFigure(x, y, width, height, color);
        dots.add(f);

    }
}
