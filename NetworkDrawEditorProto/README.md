## Folder Structure

- `src`: the folder to maintain sources

Meanwhile, the compiled output files will be generated in the `bin` folder by default.


# `Public` - サーバー・クライアント共有プログラム

## `DataBox.java`

- #### `class DataBox` 
	送受信するデータを入れる用のクラス
	
	`DataBox d = new DataBox(Command  com,ArrayList<Figure> figL)`のようにして箱にコマンドとデータを格納

	(`ArrayList<Figure>` そのままの送受信は正常に動かなかったので内部で配列に変換して送受信するようにした)
	
	| コンストラクタ |  |
	|:--|:--|
	| `DataBox(Command com,ArrayList<Figure> figL) ` |コマンドと図形リストのデータを格納|
	| `DataBox(Command com,Figure fig)` |コマンドと図形のデータを格納|
	
	|メソッド|  |
	|:--|:--|
	| `Command getCommand()` |コマンドと図形リストのデータを格納|
	| `ArrayList<Figure> getFigList()` |コマンドと図形のデータを格納|
	|`Figure getFigure()`|図形の取り出し|

##  `Command.java`

- #### `enum Command` 
	クライアント・サーバー間でメソッドを呼び出すためのEnum
	
	`DataBox` にデータと共に格納して送信することで、受信側でどのメソッドを呼び出せばよいか判別することができる
	
	| Enum |  |  
	|:---|:--| 
	| `ADD_FIGURE`|`addFigure` 図形リストに追加|
	| `SET_FIGURES` |`setFigures` 図形リストのセット|
	

## `FigShape.java`

- #### `enum FigShape` 
	Figure の形を表すEnum
	
	`createFigure` 内での分岐等に使う

	|Enum |  |  
	|:---|:--| 
	|`RECTANGLE`|`FillRectFigure` 矩形輪郭|
	|`FILLRECT` |`RectangleFigure` 矩形塗りつぶし|

## `Figure.java`

- #### `class Figure` 
	図形クラス (継承して利用する)
	
	 -  `FillRectFigure` 矩形輪郭
	 -  `RectangleFigure` 矩形塗りつぶし


# `Server` - サーバープログラム

## `CommServer.java`

- #### `class  CommServer`
	クライアントとの通信用クラス
	
	```
	CommServer cs = new  CommServer(10500);
	DataBox dataBox;
	```
	
	のように宣言した場合、
	
	`cs.send(DataBox  dataBox)` でクライアントにデータを送信
	
	`dataBox = cs.recv()` でクライアントからデータを受信して`dataBox`に格納

## `DrawEditorServer.java`

- #### `class  DrawEditorServer`
	サーバーのメインクラス
	
	クライアントが接続するたびにスレッドを生成

- #### `class  ServerObservable`
	サーバーのモデル
	
	全クライアント共有の図形リストを保存
	
	| メソッド |  |
	|:--|:--|
	| `ArrayList<Figure> getFigures()` | 図形リストを返す |
	| `Figure getFigure(int idx)` | 図形リストの idx 番目の要素を返す |
	| `void addFigure(Figure f)` | 図形リストに Figure f を追加 |
	| `void deleteFigure(Figure f)` | 図形リストから Figure f を削除 |
	| `int getFiguresSize()` | 図形リストのサイズを返す |

- #### `class  ServerThread`
	クライアントとサーバーを中継するスレッドクラス
	
	図形リストが更新されるとクライアントに図形リストを送信し、クライアントからデータを受信するとコマンドに従ってメソッドを呼び出してデータを渡す


# `Client` - クライアントプログラム

## `DrawEditorClient.java`

- #### `class DrawEditorClient`
	クライアントのメインクラス

- #### `class  DrawModel`
	クライアントのモデル
	
	図形リスト、描画する色・形、変形中の図形等を保存

	| フィールド |  |
	|:--|:--|
	| `protected  CommClient  cc;` | 通信用 |
	| `protected  ArrayList<Figure> figures;` | 図形リスト |
	| `protected  Figure  drawingFigure;` | 変形中の図形 |
	| `protected  Color  currentColor;` | 現在の色 |
	| `protected  FigShape  currentShape;` | 現在の形 |

	| メソッド |  |
	|:--|:--|
	| `ArrayList<Figure> getFigures()` | 図形リストを返す |
	| `Figure getFigure(int idx)` | 図形リストの idx 番目の要素を返す |
	| `Figure getDrawingFigure()` | 変形中の図形を返す|
	| `void setDrawingFigure(Figure f)` | 変形する図形をセット |
	| `void deleteDrawingFigure()` | 変形中の図形を削除 |
	| `void createFigure(int x,int y)` | 図形を作成(drawingFigureにセット) |
	| `void setFigures(ArrayList<Figure> figList)` | 図形リストをセット |
	| `void addFigure(Figure f)` |図形リストに Figure f を追加|
	| `void setColor(Color c)` | 現在の色をセット |
	| `void setFigShape(FigShape s)` | 現在の形をセット |
	| `void reshapeFigure(int x1,int y1,int x2,int y2)` | 図形を変形 |
	| `void sendFigure(Figure f)` | 図形をサーバーに送信 |

## `CommClient.java`

- #### `class  CommClient`
	サーバーとの通信用クラス
	```
	CommClient cc = new  CommClient("localhost",10500);
	DataBox dataBox;
	```
	のように宣言した場合、
	
	`cc.send(DataBox  dataBox)` でサーバーにデータを送信
	
	`dataBox = cc.recv()` でサーバーからデータを受信して`dataBox`に格納

##  `DataReceiver.java`

- #### `class  DataReceiver`
	サーバーからデータを受信するクラス
	
	メインメソッドの最後に実行
	
	サーバーからデータを受信するとコマンドに従ってメソッドを呼び出してデータを渡す

## `DrawController.java`
Controller

## `DrawFrame.java`
View
