import java.net.*;
import java.io.*;

class drowEditorServer {
    private ServerSocket serverS = null;
    private Socket clientS = null;
    private PrintWriter out_String = null;
    private BufferedReader in_String = null;
    ObjectOutputStream out_Figure = null;
    ObjectInputStream in_Figure = null;
    private int port=0;

    drowEditorServer() {}
    drowEditorServer(int port) { open(port); }
    drowEditorServer(drowEditorServer cs) { serverS=cs.getServerSocket(); open(cs.getPortNo()); }
    
    ServerSocket getServerSocket() { return serverS; } 
    int getPortNo() { return port; }

    boolean open(int port) {
        this.port=port;
        try{ 
        if (serverS == null) { serverS = new ServerSocket(port); }
        } catch (IOException e) {
            System.err.println("ポートにアクセスできません。");
            System.exit(1);
        }
        try{
            clientS = serverS.accept();
            out_String = new PrintWriter(clientS.getOutputStream(), true);
            in_String = new BufferedReader(new InputStreamReader(clientS.getInputStream()));
        } catch (IOException e) {
            System.err.println("Acceptに失敗しました。");
            System.exit(1);
        }
        return true;
    }

    boolean send_String(String msg){
        if (out_String == null) { return false; }
        out_String.println(msg);
        return true;
    }

    String recv_string(){
        String msg=null;
        if (in_String == null) { return null; }
        try{
          msg=in_String.readLine();
        } catch (SocketTimeoutException e){
          System.err.println("タイムアウトです．");
          return null;
        } catch (IOException e) {
          System.err.println("受信に失敗しました。");
          System.exit(1);
        }
        return msg;
    }

    boolean send_Figure(Figure f){
        if(out_Figure==null) { return false; }
        /*何か入るかも*/
        return true;
    }

    Figure recv_Figure(){
        Figure f = null;
        if (in_Figure == null) { return null; }
        try{
            f=(Figure)in_Figure.readObject();
        } catch (SocketTimeoutException e){
            System.err.println("タイムアウトです．");
            return null;
        } catch (IOException e) {
            System.err.println("受信に失敗しました。");
            System.exit(1);
        }
        return f;
    }


    int setTimeout(int to){
        try{
          clientS.setSoTimeout(to);
        } catch (SocketException e){
          System.err.println("タイムアウト時間を変更できません．");
          System.exit(1);
        }
        return to;
    }

    void close(){
        try{
          in_String.close();  out_String.close();
          in_Figure.close();  out_Figure.close();
          clientS.close();  serverS.close();
        } catch (IOException e) {
            System.err.println("ソケットのクローズに失敗しました。");
            System.exit(1);
        }
        in_String=null; out_String=null;
        in_Figure=null; out_Figure=null;
        clientS=null; serverS=null;
    }

}

class drowEditorClient {
    Socket clientS = null;
    BufferedReader in_String = null;
    PrintWriter out_String = null;
    ObjectOutputStream out_Figure = null;
    ObjectInputStream in_Figure = null;
 
    drowEditorClient() {}
    drowEditorClient(String host,int port) { open(host,port); }
    
     // クライアントソケット(通信路)のオープン　
     // 接続先のホスト名とポート番号が必要．
    boolean open(String host,int port){
        try{
            clientS = new Socket(InetAddress.getByName(host), port);
            in_String = new BufferedReader(new InputStreamReader(clientS.getInputStream()));
            out_String = new PrintWriter(clientS.getOutputStream(), true);
            in_Figure = new ObjectInputStream(clientS.getInputStream());
            out_Figure = new ObjectOutputStream(clientS.getOutputStream());
        } catch (UnknownHostException e) {
            System.err.println("ホストに接続できません。");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOコネクションを得られません。");
            System.exit(1);
        }
        return true;
    }
 
    // データ送信
    boolean send_string(String msg){
        if (out_String == null) { return false; }
        out_String.println(msg);
        return true;
    }

    // データ受信
    String recv_string(){
        String msg=null;
        if (in_String == null) { return null; }
        try{
            msg=in_String.readLine();
        } catch (SocketTimeoutException e){
            return null;
        } catch (IOException e) {
            System.err.println("受信に失敗しました。");
            System.exit(1);
        }
        return msg;
    }

    boolean send_Figure(Figure f){
        if(out_Figure==null) { return false; }
        /*何か入るかも*/
        return true;
    }

    Figure recv_Figure(){
        Figure f = null;
        if (in_Figure == null) { return null; }
        try{
            f=(Figure)in_Figure.readObject();
        } catch (SocketTimeoutException e){
            System.err.println("タイムアウトです．");
            return null;
        } catch (IOException e) {
            System.err.println("受信に失敗しました。");
            System.exit(1);
        }
        return f;
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
        try{
            in_String.close();  out_String.close();
            in_Figure.close();  out_Figure.close();
            clientS.close();
        } catch (IOException e) {
            System.err.println("ソケットのクローズに失敗しました。");
            System.exit(1);
        }
        in_String=null; out_Figure=null;
        in_Figure=null; out_Figure=null;
        clientS=null;
    }
}