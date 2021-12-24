package Server;

import Public.*;

import java.io.Serializable;
import java.util.*;

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
    protected int lastId; // Figureに付与するID

    public ServerObservable() {
        figures = new ArrayList<Figure>();
        lastId = 1;
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
    public void addFigure(Figure f) {
        if(f!=null) {
            f.setId(lastId);
            lastId++;
            figures.add(f);
            setChanged();
            notifyObservers();
        }
    }
    // 図形リストから Figure f を削除
    public void deleteFigure(Figure f) {
        for (int i = 0; i < figures.size(); i++) {
            if(f.getId() == figures.get(i).getId()) {
                figures.remove(i);
                setChanged();
                notifyObservers();
                break;
            }
        }
    }
    // 図形リストのサイズを返す
    public int getFiguresSize() {
        return figures.size();
    }
    // 図形リストの置き換え
    public void setFigures(ArrayList<Figure> figList) {
        figures = figList;
        setChanged();
        notifyObservers();
    }
    // 図形の置き換え
    public void replaceFigure(Figure f) {
        for (int i = 0; i < figures.size(); i++) {
            if(f.getId() == figures.get(i).getId()) {
                figures.set(i, f);
                setChanged();
                notifyObservers();
                break;
            }
        }
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
        cs.send(new DataBox(Command.SET_FIGURES,so.getFigures()));
    }
    
    // Observable の変化で自動実行
    public void update(Observable o,Object arg) {
        cs.send(new DataBox(Command.SET_FIGURES,so.getFigures()));
    }
    // スレッド生成で自動実行
    public void run() {
        DataBox dataBox = null;
        Command command = null;
        while((dataBox=cs.recv())!=null) { // データ受信
            if((command=dataBox.getCommand())!=null) { // command が送られてきたら
                switch(command){ // command で分岐
                    case ADD_FIGURE :
                        so.addFigure(dataBox.getFigure());
                        break;
                    case DELETE_FIGURE :
                        so.deleteFigure(dataBox.getFigure());
                        break;
                    case REPLACE_FIGURE :
                        so.replaceFigure(dataBox.getFigure());
                        break;
                    case SET_FIGURES :
                        so.setFigures(dataBox.getFigList());
                        break;
                    default :
                        break;
                }
            }
        }
    }
}
