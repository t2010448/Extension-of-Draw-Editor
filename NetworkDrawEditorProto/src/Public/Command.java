package Public;

// クライアント・サーバー間でメソッドを呼び出すためのEnum
public enum Command {
    ADD_FIGURE,     // 図形の追加
    SET_FIGURES,    // 図形リストのセット
    DELETE_FIGURE,  // 図形の削除
    REPLACE_FIGURE  // 図形の置き換え(変形，移動)
}
