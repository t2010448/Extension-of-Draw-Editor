package Server;

import java.io.Serializable;
import java.util.*;

import Public.*;

////////////////////////////////////////////
// Server Main
public class DrawEditorServer {
    public static void main(String args[]) {
        int n = 0;
        ServerObservable so = new ServerObservable();
        CommServer cs = new CommServer(10500);
        new ServerThread(cs,so,++n).start();
        while(true) {
            cs = new CommServer(cs);
            new ServerThread(cs,so,++n).start();
        }
    }
}

////////////////////////////////////////////
// Server Model
@SuppressWarnings("deprecation")
class ServerObservable extends Observable implements Serializable{
    protected ArrayList<Figure> figures; // 描画する図形のリスト

    public ServerObservable() {
        figures = new ArrayList<Figure>();
    }

    // 図形リストを返す
    public ArrayList<Figure> getFigures() {
        return figures;
    }
    // 図形リストの idx 番目の要素を返す
    public Figure getFigure(int idx) {
        return figures.get(idx);
    }
    // 図形リストに Figure f を追加
    public void addFigure(Figure f) { // 図形の追加
        figures.add(f);
        setChanged();
        notifyObservers();
    }
    // 図形リストから Figure f を削除
    public void deleteFigure(Figure f) {
        figures.remove(f);
    }
    // 図形リストのサイズを返す
    public int getFiguresSize() {
        return figures.size();
    }
}

////////////////////////////////////////////
// Server Thread
@SuppressWarnings("deprecation")
class ServerThread extends Thread implements Observer {
    CommServer cs;
    ServerObservable so;
    int clientNumber;

    public ServerThread(CommServer cs, ServerObservable so, int n) {
        this.cs = cs;
        this.so = so;
        this.clientNumber = n;
        so.addObserver(this);
        sendFigures();
    }

    // クライアントにサーバーの図形リストを送信するメソッド
    public void sendFigures() {
        DataBox dataBox = new DataBox(Command.SET_FIGURES,so.getFigures());
        cs.send(dataBox);
    }
    // Observable の変化で自動実行
    public void update(Observable o,Object arg) {
        sendFigures();
    }
    // スレッド生成で自動実行
    public void run() {
        DataBox dataBox = null;
        Command command = null;
        while((dataBox=cs.recv())!=null) { // データ受信
            if((command=dataBox.getCommand())!=null) { // command が送られてきたら
                switch(command){ // command で分岐
                    case ADD_FIGURE : // 受信した図形をリストに追加
                        so.addFigure(dataBox.getFigure());
                        break;
                    default :
                        break;
                }
            }
        }
    }
}