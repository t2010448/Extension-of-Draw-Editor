package Public;

import java.awt.*;

// 選択図形を強調するため＆選択の当たり判定のためのハンドル
public class Handle extends Figure{
    Figure handles[];

    public Handle(Figure f) {
        super(f.getX(), f.getY(), f.getWidth(), f.getHeight(), Color.BLACK);

        handles = new Figure[9];
        handles[0] = new RectangleFigure(x-1, y-1, width+2, height+2, Color.BLACK);   // 枠
        handles[1] = new FillRectFigure(x-6,       y-6,        12, 12, Color.BLACK);  // 左上
        handles[2] = new FillRectFigure(x-6,       y-6+height, 12, 12, Color.BLACK);  // 左下
        handles[3] = new FillRectFigure(x-6+width, y-6,        12, 12, Color.BLACK);  // 右上
        handles[4] = new FillRectFigure(x-6+width, y-6+height, 12, 12, Color.BLACK);  // 右下
        handles[5] = new FillRectFigure(x-6,         y-6+height/2, 12, 12, Color.BLACK); // 左
        handles[6] = new FillRectFigure(x-6+width/2, y-6,          12, 12, Color.BLACK); // 上
        handles[7] = new FillRectFigure(x-6+width/2, y-6+height,   12, 12, Color.BLACK); // 下
        handles[8] = new FillRectFigure(x-6+width,   y-6+height/2, 12, 12, Color.BLACK); // 右
    }
    public void draw(Graphics g) {
        for(int i=0; i < handles.length; i++) {
            handles[i].draw(g);
        }
    }

    public String selectHandle(int x,int y) {
        if(handles[4].judgein(x, y)) {
            return "lower right";
        }else if(handles[3].judgein(x, y)) {
            return "upper right";
        }else if(handles[2].judgein(x, y)) {
            return "lower left";
        }else if(handles[1].judgein(x, y)) {
            return "upper left";
        }else if(handles[8].judgein(x, y)) {
            return "right";
        }else if(handles[7].judgein(x, y)) {
            return "lower";
        }else if(handles[6].judgein(x, y)) {
            return "upper";
        }else if(handles[5].judgein(x, y)) {
            return "left";
        }else if(handles[0].judgein(x, y)) {
            return "move";
        }else{
            return null;
        }
    }
}