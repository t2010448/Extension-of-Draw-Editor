package Client;

import Public.*;

// サーバーから受信するためのクラス
// Main の最後に実行
public class DataReceiver {
    protected DrawModel model;
    protected CommClient cc;

    public DataReceiver(DrawModel m,CommClient cc) {
        model = m;
        this.cc = cc;
        start();
    }
    
    // プログラム終了まで動き続ける
    // コマンドとデータを受け取ってメソッドを呼び出す
    public void start() {
        DataBox dataBox = null;
        Command command = null;
        while((dataBox=cc.recv())!=null) { // データ受信
            command = dataBox.getCommand();
            switch(command){ // command で分岐
                case SET_FIGURES :
                    ArrayList<Figure> figL = dataBox.getFigList();
                    model.setFigures(figL);
                    // 図形選択中の場合、選択していた図形と同じIDの図形を選択しなおす(変形されている場合に備えて)
                    if(model.getMode()=="select" && model.getDrawingFigure()!=null) {
                        int i;
                        for (i = 0; i < figL.size(); i++) {
                            if(model.getDrawingFigure().getId() == figL.get(i).getId()) {
                                model.setDrawingFigure(figL.get(i));
                                model.setHandle();
                                break;
                            }
                        }
                        // 選択中の図形が他のクライアントによって削除されていたら、選択解除
                        if(i==figL.size()) {
                            model.setDrawingFigure(null);
                        }
                    }
                    break;
                default :
                    break;
            }
        }
    }
}
