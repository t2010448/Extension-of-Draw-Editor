package Client;

import Public.*;

import java.net.*;
import java.io.*;

class CommClient {
    private Socket clientS = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    CommClient() {}
    CommClient(String host,int port) { open(host,port); }

    // クライアントソケット(通信路)のオープン　
    // 接続先のホスト名とポート番号が必要．
    // ※一部改変
    boolean open(String host,int port){
        try{
            clientS = new Socket(InetAddress.getByName(host), port);
            in = new ObjectInputStream(clientS.getInputStream());
            out = new ObjectOutputStream(clientS.getOutputStream());
        } catch (UnknownHostException e) {
            System.err.println("ホストに接続できません。");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOコネクションを得られません。");
            System.exit(1);
        }
        return true;
    }

    // データ送信 ※一部改変
    boolean send(DataBox dataBox){
        if (out == null) { return false; }
        try {
            out.writeObject(dataBox);
        } catch (IOException e) {
            System.err.println("送信に失敗しました。");
        }
        return true;
    }

    // データ受信 ※一部改変
    DataBox recv(){
        DataBox dataBox = null;
        if (in == null) { return null; }
            try {
                dataBox = (DataBox)in.readObject();
            } catch (SocketTimeoutException e){
                return null;
            } catch (ClassNotFoundException | IOException e) {
                System.err.println("受信に失敗しました。");
            }
        return dataBox;
    }

    // タイムアウトの設定
    int setTimeout(int to){
        try{
            clientS.setSoTimeout(to);
        } catch (SocketException e){
            System.err.println("タイムアウト時間を変更できません．");
            System.exit(1);
        }
        return to;
    }

    // ソケットのクローズ (通信終了)
    void close(){
        try {
            in.close();  out.close();
            clientS.close();
        } catch (IOException e) {
            System.err.println("ソケットのクローズに失敗しました。");
            System.exit(1);
        }  
    in=null;  out=null;
    clientS=null;
    }
}
