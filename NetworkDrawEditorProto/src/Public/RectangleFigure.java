package Public;

import java.awt.*;

public class RectangleFigure extends Figure {
    
    public RectangleFigure(int x,int y,int w,int h,Color c) {
        super(x,y,w,h,c);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(x,y,width,height);
    }
    public FigShape getFigShape() { // 形を返す 
        return FigShape.RECTANGLE;
    }
    public boolean judgein(int x, int y) {
        if( (this.x < x && x < this.x + this.width) && (this.y < y && y < this.y + this.height) ) {
            return true;
        }else{
            return false;
        }
    }
}
