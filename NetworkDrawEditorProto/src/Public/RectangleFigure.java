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
        int r = 6;
        if( ( (this.x-r <= x && x <= this.x+this.width+r) && (this.y-r <= y && y <= this.y+this.height+r) ) &&
            ( (x < this.x+r || this.x+this.width-r < x) || (y < this.y+r || this.y+this.height-r < y) ) ) {
            return true;
        }else{
            return false;
        }
    }
}
