package Server;

import Public.*;

import java.net.*;
import java.io.*;

class CommServer {
    private ServerSocket serverS = null;
    private Socket clientS = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private int port = 0;

    CommServer() {}
    CommServer(int port) { open(port); }
    CommServer(CommServer cs) { serverS=cs.getServerSocket(); open(cs.getPortNo()); }

    ServerSocket getServerSocket() { return serverS; } 
    int getPortNo() { return port; }

    // サーバ用のソケット(通信路)のオープン
    // サーバ用のソケットはクライアントからの接続待ち専用．
    // ポート番号のみを指定する．
    boolean open(int port){
        this.port=port;
        try{ 
            if (serverS == null) { serverS = new ServerSocket(port); }
        } catch (IOException e) {
            System.err.println("ポートにアクセスできません。");
            System.exit(1);
        }
        try{
            clientS = serverS.accept();
            out = new ObjectOutputStream(clientS.getOutputStream());
            in = new ObjectInputStream(clientS.getInputStream());
        } catch (IOException e) {
            System.err.println("Acceptに失敗しました。");
            System.exit(1);
        }
        return true;
    }

    // データ送信
    boolean send(DataBox dataBox){
        if (out == null) { return false; }
        try {
            out.writeObject(dataBox);
        } catch (IOException e) {
            System.err.println("送信に失敗しました。");
        }
        return true;
    }

    // データ受信
    DataBox recv(){
        DataBox dataBox = null;
        if (in == null) { return null; }
            try {
                dataBox = (DataBox)in.readObject();
            } catch (SocketTimeoutException e){
                System.err.println("タイムアウトです．");
                return null;
            } catch (ClassNotFoundException | IOException e) {
                System.err.println("受信に失敗しました。");
            }
        return dataBox;
    }

    // タイムアウトの設定
    int setTimeout(int to){
            try {
                clientS.setSoTimeout(to);
            } catch (SocketException e) {
                System.err.println("タイムアウト時間を変更できません．");
                System.exit(1);
            }
        return to;
    }

    // ソケットのクローズ (通信終了)
    void close(){
            try {
                in.close();  out.close();
                clientS.close();  serverS.close();
            } catch (IOException e) {
                System.err.println("ソケットのクローズに失敗しました。");
                System.exit(1);
            }
        in=null;  out=null;
        clientS=null; serverS=null;
    }
}
