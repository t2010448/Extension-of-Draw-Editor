package Public;

import java.io.Serializable;
import java.util.*;

// データを送受信する用の箱
// DataBox(Command, Data) でコマンドとデータを格納
// getCommand(), getFigList(), getFigure() でコマンドやデータの取り出しが可能
// (ArrayList の送受信は正常に動かなかったので内部で配列に変換して送受信するようにした)
public class DataBox implements Serializable{
    protected Command command = null;
    protected Figure[] figArray = null;
    protected Figure figure = null;

    // コマンドと図形リストのデータ
    public DataBox(Command com,ArrayList<Figure> figL) {
        figArray = figL.toArray(new Figure[figL.size()]);
        command = com;
    }
    // コマンドと図形のデータ
    public DataBox(Command com, Figure fig) {
        command = com;
        figure = fig;
    }

    // データの取り出しメソッド
    public Command getCommand() {
        return command;
    }
    public  ArrayList<Figure> getFigList() {
        ArrayList<Figure> figList = new ArrayList<Figure>(Arrays.asList(figArray));
        return figList;
    }
    public Figure getFigure() {
        return figure;
    }
}
