package Client;

import Public.*;

import java.awt.event.*;

////////////////////////////////////////////
// Controller
public class DrawController implements MouseListener,MouseMotionListener,KeyListener {
    protected DrawModel model;
    protected int dragStartX,dragStartY,oldX,oldY,oldWidth, oldHeight;
    protected String reshapeMode;

    public DrawController(DrawModel m) {
        model = m;
    }

    public void mouseClicked(MouseEvent e) { }
    public void mousePressed(MouseEvent e) {
        dragStartX = e.getX(); dragStartY = e.getY();
        switch(model.getMode()) {
            case "draw" :
                model.createFigure(dragStartX,dragStartY);
                break;
            case "select" : // 選択モードの場合
                if(model.getDrawingFigure()!=null) {
                    // 図形選択中なら、図形の位置と幅と高さを記憶
                    oldX = model.getDrawingFigure().getX();
                    oldY = model.getDrawingFigure().getY();
                    oldWidth  = model.getDrawingFigure().getWidth();
                    oldHeight = model.getDrawingFigure().getHeight();
                    Handle handle = (Handle) model.getHandle();
                    // どの変形をするか判別
                    reshapeMode = handle.selectHandle(dragStartX,dragStartY);
                    if(reshapeMode == null) {
                        // 選択中の図形を変形しない場合
                        Figure f = model.selectFigure(dragStartX, dragStartY);
                        if(f != null) {
                            // 別の図形上なら新たに図形を選択、図形の位置と幅と高さを記憶
                            model.setDrawingFigure(f);
                            model.setHandle();
                            oldX = f.getX();
                            oldY = f.getY();
                            oldWidth  = f.getWidth();
                            oldHeight = f.getHeight();
                            // そのままドラッグで図形移動できるようにする
                            reshapeMode = "move";
                        }else{
                            // 図形上以外なら選択解除
                            model.setDrawingFigure(null);
                        }
                    }
                }else{
                    // 図形選択中でない場合
                    Figure f = model.selectFigure(dragStartX, dragStartY);
                    if(f != null) {
                        // 図形上なら図形を選択、図形の位置と幅と高さを記憶
                            model.setDrawingFigure(f);
                            model.setHandle();
                            oldX = f.getX();
                            oldY = f.getY();
                            oldWidth  = f.getWidth();
                            oldHeight = f.getHeight();
                            // そのままドラッグで図形移動できるようにする
                            reshapeMode = "move";
                    }
                }
                break;
            default :
                break;
        }
    }
    public void mouseDragged(MouseEvent e) {
        switch(model.getMode()) {
            case "draw" :
                model.reshapeFigure(dragStartX,dragStartY,e.getX(),e.getY());
                break;
            case "select" :
                if(model.getDrawingFigure()!=null) {
                    // 変形
                    switch(reshapeMode) {
                        case "move" :
                            // 図形の移動
                            model.moveFigure(oldX+e.getX()-dragStartX, oldY+e.getY()-dragStartY);
                            model.setHandle();
                            break;
                        case "lower right" :
                            // 右下
                            model.reshapeFigure(oldX, oldY, e.getX(), e.getY());
                            model.setHandle();
                            break;
                        case "upper right" :
                            // 右上
                            model.reshapeFigure(oldX, e.getY(), e.getX(), oldY+oldHeight);
                            model.setHandle();
                            break;
                        case "lower left" :
                            // 左下
                            model.reshapeFigure(e.getX(), oldY, oldX+oldWidth, e.getY());
                            model.setHandle();
                            break;
                        case "upper left" :
                            // 左上
                            model.reshapeFigure(e.getX(), e.getY(), oldX+oldWidth, oldY+oldHeight);
                            model.setHandle();
                            break;
                        case "left" :
                            // 左
                            model.reshapeFigure(e.getX(), oldY, oldX+oldWidth, oldY+oldHeight);
                            model.setHandle();
                            break;
                        case "upper" :
                            // 上
                            model.reshapeFigure(oldX, e.getY(), oldX+oldWidth, oldY+oldHeight);
                            model.setHandle();
                            break;
                        case "right" :
                            // 右
                            model.reshapeFigure(oldX, oldY, e.getX(), oldY+oldHeight);
                            model.setHandle();
                            break;
                        case "lower" :
                            // 下
                            model.reshapeFigure(oldX, oldY, oldX+oldWidth, e.getY());
                            model.setHandle();
                            break;
                        default :
                            break;
                    }
                }
                break;
            default :
                break;
        }
    }
    public void mouseReleased(MouseEvent e) {
        switch(model.getMode()) {
            case "draw" :
                model.sendData(new DataBox(Command.ADD_FIGURE, model.getDrawingFigure()));
                model.setDrawingFigure(null);
                break;
            case "select" :
                // 図形選択中なら
                if(model.getDrawingFigure()!=null) {
                    // 変形を確定(ここでは選択解除しない)
                    model.sendData(new DataBox(Command.REPLACE_FIGURE, model.getDrawingFigure()));
                }
                break;
            default :
                break;
        }
    }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseMoved(MouseEvent e) {
        switch(model.getMode()) {
            case "laser" :
                if(model.getDrawingFigure()==null) {
                    // レーザーポインタを選択
                    Figure f = model.selectFigure(-8, -8);
                    model.setDrawingFigure(f);
                }else{
                    // レーザーポインタを移動
                    model.moveFigure(e.getX()-8, e.getY()-8);
                    // 他クライアントにも反映
                    model.sendData(new DataBox(Command.REPLACE_FIGURE, model.getDrawingFigure()));
                }
                break;
            default :
                break;
        }
    }

    public void keyTyped(KeyEvent e) { }
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if(k == KeyEvent.VK_X || k == KeyEvent.VK_DELETE) { // 選択している図形を削除
            if((model.getDrawingFigure())!=null) {
                model.sendData(new DataBox(Command.DELETE_FIGURE, model.getDrawingFigure()));
                model.setDrawingFigure(null);
            }
        }
    }
    public void keyReleased(KeyEvent e) { }
}
