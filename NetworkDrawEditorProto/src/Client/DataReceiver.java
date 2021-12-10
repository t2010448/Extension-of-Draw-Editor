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
        receiving();
    }
    
    // プログラム終了まで動き続ける
    // コマンドとデータを受け取ってメソッドを呼び出す
    public void receiving() {
        DataBox dataBox = null;
        Command command = null;
        while((dataBox=cc.recv())!=null) { // データ受信
            command = dataBox.getCommand();
            switch(command){ // command で分岐
                case SET_FIGURES :
                    model.setFigures(dataBox.getFigList());
                    break;
                case ADD_FIGURE : // addFigure で figures に Figure を追加
                    model.addFigure(dataBox.getFigure());
                    break;
                default :
                    break;
            }
        }
    }
}