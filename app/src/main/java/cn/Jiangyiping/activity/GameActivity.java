/**
 * 游戏界面目前有三个，第一个是游戏首页，对应java：MainActivity.java 对应的XML布局：activity_main.xml。
 * 第二个是游戏主页对应的，对应java:GameActivity.java 对应的XML布局：game.xml。
 * 在game.xml里有自定义的surfaceView 所对应的刷新线程是：RenderThread.java;surfaceView所对应的画布在ChessView里。
 * 第三个是游戏规则界面，对应java:GameRuleActivity.java 对应的XML布局：gamerule.xml
 *
 *
 *
 * //  游戏界面主要代码部分
 */
package cn.Jiangyiping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.Jiangyiping.Keynodes.Graph;
import cn.Jiangyiping.bean.Move;
import cn.Jiangyiping.game.ChessView;
import cn.Jiangyiping.game.Constant;
import cn.Jiangyiping.game.Rule;
import cn.Jiangyiping.reversi.R;
import cn.Jiangyiping.widget.MessageDialog;
import cn.Jiangyiping.widget.NewGameDialog;
import java.util.Stack;

//import cn.Jiangyiping.game.Algorithm;

public class GameActivity extends Activity {

    public int Counts = 0; //记录连通的点数

    public int[] id; //每个节点所对应的联通分量标记

    public boolean[] Visited = new boolean[11]; //记录是否已访问

    private static final byte NULL = Constant.NULL;  //无棋子

    private static final byte BLACK = Constant.BLACK;  //有棋子

    private static final int STATE_GAME_OVER = 1;

    private int row2, row1, col2, col1;  //row1 col1是的连线起点的行和列 row2 col2是连线终点的行和列

    private ChessView chessView;

    Graph G = new Graph(); //图论的 我还在调试

    private LinearLayout playerLayout;

    private LinearLayout aiLayout;

    private TextView chessnum;

    private TextView distantnum;

    public float distant = 50;

    // private ImageView playerImage;
    private ImageView aiImage;

    //  private TextView nameOfAI;
    private TextView newGame; //重新开始

    private TextView recall; //重新开始

    private TextView tip;

    TextView nameOfAI;

    private int chessCount = 0; //计数，下完10个棋子后开始连线

    // private byte playerColor;
    private byte aiColor;

    private int difficulty;

    private int StartP, EndP;  //起点以及终点

    private float weight = 0;  //连线权值

    private static final int M = 8;

    private static final int Line = 10; //10根线

    public byte[][] chessBoard = new byte[Line][Line]; //棋盘是否有棋子

    private int flag = 0;  //判断是否是有效点击的标志

    private int sign = 0;   //同上判断

    private static final String MULTIPLY = " × ";

    int lineCount = 0;

    /**
     * 晦棋用的
     */
    Stack<Move> history = new Stack<>();

    int right = 0;

    private NewGameDialog dialog;

    private MessageDialog msgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game);
        chessView = (ChessView) findViewById(R.id.chessView);
        playerLayout = (LinearLayout) findViewById(R.id.player);
        aiLayout = (LinearLayout) findViewById(R.id.ai);
        chessnum = (TextView) findViewById(R.id.chessnum);//变化
        distantnum = (TextView) findViewById(R.id.distantnum);
        nameOfAI = (TextView) findViewById(R.id.julishu);
        recall = (TextView) findViewById(R.id.reback);
        newGame = (TextView) findViewById(R.id.congkai);
        tip = (TextView) findViewById(R.id.over);
        //reversiView.initiallinepoint();
        //  Bundle bundle = getIntent().getExtras();

//        nameOfAI.setText("距离数");
//        initialdfs();
//        initialChessboard();
//        //每一盘新的游戏就有新的数 数
//        //初始化图
//        G.graph();
//
//        setListener();

        newgame();
    }

    /**
     * 重开
     */
    void newgame() {
        Counts = 0; //记录连通的点数

        Visited = new boolean[11]; //记录是否已访问

        G = new Graph(); //图论的 我还在调试

        distant = 50;

        chessCount = 0; //计数，下完10个棋子后开始连线

        weight = 0;  //连线权值

        chessBoard = new byte[Line][Line]; //棋盘是否有棋子

        flag = 0;  //判断是否是有效点击的标志

        sign = 0;   //同上判断
        lineCount = 0;

        history = new Stack<>();

        right = 0;
        
        chessView.reinit();
        
        nameOfAI.setText("距离数");
        initialdfs();
        initialChessboard();
        //每一盘新的游戏就有新的数 数
        //初始化图
        G.graph();

        setListener();

    }

    private void setListener() {
        newGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    newgame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        recall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    undo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //chessnum.setText(Count);

        chessView.setOnTouchListener(new OnTouchListener() { //绑定控件 监听chessView棋盘

            boolean down = false;

            int downRow;

            int downCol;

            int EndRow;

            int EndCol;

//            float distant = 50;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
               /* if (gameState != STATE_PLAYER_MOVE) {
                    return false;
                }*/
                Log.e("Count", chessCount + "Count");
                if (chessCount != 11) {  //下10颗棋
                    float x = event.getX();
                    float y = event.getY();  //获取下棋的坐标
                    chessView.getX(x);
                    chessView.getY(y);  //调用chessView函数 获取XY值 没有用暂时
                    if (!chessView.inChessBoard(x, y)) { //判断是否在棋盘内
                        return false;
                    }
                    int row = chessView.getRow(y);
                    int col = chessView.getCol(x);  //调用chessview的函数 通过坐标算在棋盘几行几列

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:  //按下
                            down = true;
                            downRow = row;
                            downCol = col;
                            break;
                        case MotionEvent.ACTION_UP:  //离开
                            if (down && downRow == row && downCol == col) {
                                down = false;
                                if (!Rule.isLegalMove(chessBoard, new Move(row, col))) {
                                    Log.e("String", " 违反rule");
                                    return true;
                                }

                                chessOne(row, col);
                            }
                            break;

                        case MotionEvent.ACTION_CANCEL:
                            down = false;
                            break;

                    }
                }

                //       连线的部分
                if (chessCount == 11) {  //10颗棋子下完开始 连线
                    float startX, startY, moveX, moveY, endX, endY;

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startX = event.getX();  //获取按下连线的点的值
                            startY = event.getY();
                            down = true;
                            col1 = chessView.getCol(startX);
                            row1 = chessView.getRow(startY);
                            //reversiView.getStart(row1, col1);
                            Log.v("startX", row1 + "开始的row");
                            sign = 1;  //连线过程要求先点下（down），再move 最后在up的时候取最终的连线值 标志位用于move中判断是否先执行了down
                            Log.v("downsign", sign + "down de sign ");
                            downRow = row1;
                            downCol = col1;
                            break;

                        case MotionEvent.ACTION_MOVE:
                            moveX = event.getX();
                            moveY = event.getY();
                            chessView.getX(moveX); //move获取值
                            chessView.getY(moveY);
                            row2 = chessView.getRow(moveY);  //调用计算几行几列
                            col2 = chessView.getCol(moveX);
                            if (sign == 1) {
                                flag = 1;  //确定down后再move
                            }
                            Log.v("movesign", sign + "flag值");
                            Log.v("moveflag", flag + "flag值");
                            EndRow = row2;
                            EndCol = col2;  //好像没有用这个了
                            Log.v("moveX", row2 + "移动的row");
                            break;

                        case MotionEvent.ACTION_UP:
                            if (down && downRow == row1 && downCol == col1 && EndRow == row2 && EndCol == col2) {
                                down = false;
                                Log.d("islegal", Rule.isLegalConnect(row1, col1, chessBoard) + "islegal 起点");
                                //问题出在chessboard
                                //     Log.d("islegal", Rule.isLegalConnect(row2, col2, chessBoard)+"islegal 终点");
                                if (flag == 1 && Rule.isLegalConnect(row1, col1, chessBoard) && Rule
                                        .isLegalConnect(row2, col2, chessBoard)) {

                                    chessLine();

                                }   //数组溢出
                            }
                            flag = 0;
                            sign = 0;
                            // weight =  G.getWeight(col1, row1,col2, row2 ); //获取两连线之间的权值
                            //  StartP = G.lookVertex(row1, col1); //连线起始点
                            //    EndP = G.lookVertex(row2, col2); //连线终点
                            //  G.setMatrix(StartP, EndP, weight); //设置Matrix
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            down = false;
                            break;
                    }
                    //  }
                    //  flag=0;sign=0;
                }
                ///////////////////////////////////////////////
                String board = "q\n";
                for (int i = 0; i < Line; i++) {
                    String line = " ";
                    for (int j = 0; j < Line; j++) {
                        line = line + "  " + String.valueOf(chessBoard[i][j]);
                    }
                    board = board + line + "\n";
                }
                Log.e("棋盘", board);
                ///////////////////////////////////////////////

                if (right == 1) {
                    Log.d("ANS", distant + "nowdis标志");
                    Log.d("ANS", right + "now标志");
                    getbombload();
                    int ans = 0;
                    int maxpiece = -1;
                    for (int z = 1; z < 11; z++) {
                        initialdfs();//初始化
                        // Log.d("ANS", G.getVertex(z)+"点连通分量");
                        if (G.getVertex(z) != -1) {  //存在的点都进行一遍DFS 防止有两个分支不同的大小 取最大值
                            dfs(z);
                            ans = Counts();
                            if (ans > maxpiece) {
                                maxpiece = ans;
                            }

                        }
                    }
                    Log.d("ANS", maxpiece + "最大连通分量");
                }
                return true;
            }


        });

    }


    /**
     * 画线
     */
    void chessLine() {
        chessView.setStart(row1, col1, lineCount); //获取连线起点 linecount是数组下标
        chessView.setEnd(row2, col2, lineCount);   //获取连线终点
        weight = G.getWeight(col1, row1, col2, row2); //获取两连线之间的权值
        distant = distant - weight;//变化的距离
        Log.d("GRAPH", weight + "距离");
        chessView.updateline(col1, row1, col2, row2, lineCount); //更新连线
        StartP = G.lookVertex(row1, col1); //连线起始点
        EndP = G.lookVertex(row2, col2); //连线终点
        Log.d("GRAPH", StartP + "连线起点");
        Log.d("GRAPH", EndP + "连线终点");
        G.setMatrix(StartP, EndP, weight); //设置Matrix
        lineCount++;
        Log.d("ANS", distant + "距离");
        if (distant - 1 < 1) {
            right = 1;
        }
        Log.d("ANS", right + "标志");
    }

    /**
     * 画线悔棋
     */
    private void undochessLine() {
//        chessView.unsetStart(lineCount); //获取连线起点 linecount是数组下标
//        chessView.unsetEnd(lineCount);   //获取连线终点
//        weight = G.getWeight(col1, row1, col2, row2); //获取两连线之间的权值
//        distant = distant - weight;//变化的距离
        this.lineCount--;
        if (lineCount == 0) {
            right = 0;
        }
        chessView.undoline(lineCount); //更新连线
        StartP = G.lookVertex(row1, col1); //连线起始点
        EndP = G.lookVertex(row2, col2); //连线终点
        G.unsetMatrix(StartP, EndP); //设置Matrix
//        this.lineCount--;
    }

    /**
     * 下棋
     */
    private void chessOne(final int row, final int col) {
        Log.e("下棋", "下一个棋开始");
        chessView.update(row, col);  //完整下棋子后 更新棋盘值
        chessBoard[row][col] = BLACK;
        history.push(new Move(row, col));

//        Log.d(String.valueOf(chessBoard), chessBoard[row][col] + "upchessboard");
        G.setVertex(chessCount); //标记点1-10
//        Log.v("Count", Count + "Count");
        G.Point[row][col] = chessCount; //标记Point
        chessCount++;
    }

    // 悔棋
    public synchronized void undo() {
        Log.e("悔棋", "悔棋开始");

        //有线
        if (lineCount > 0) {
            Log.e("悔棋", " 线悔棋 ");

            undochessLine();
        } else {   //下棋阶段
            if (!history.isEmpty()) {
                Log.e("悔棋", " 悔棋子 ");
                Move p1 = history.pop();

                int col = p1.col;
                int row = p1.row;

                chessCount--;
                chessBoard[row][col] = NULL;
                chessView.undoPoint(row, col);  //完整下棋子后 更新棋盘值
                G.unsetVertex(chessCount); //标记点1-10
                G.Point[row][col] = -1; //标记Point

//                Log.e("悔棋", " chessCount " + chessCount);

            }
        }


    }

    private void initialChessboard() {
        for (int i = 0; i < Line; i++) {
            for (int j = 0; j < Line; j++) {
                chessBoard[i][j] = NULL;
            }  //初始化棋盘
        }

    }

    public void getbombload() {
        Log.d("ANS", "get运行");
        // int point[] =new int[3];
        //point[0]=-1;point[1]=-1;point[2]=-1;
        int point[] = G.DijkstraAlogrim();
        Log.d("ANS", point[0] + "第一个点");
        Log.d("ANS", point[1] + "第二个点");
        Log.d("ANS", point[2] + "第三个点"); //这里point[0]\[1]\[2]分别是算出的3个点 可以再用Graph.java中的findrow、findcol获取坐标。

    }

    public void initialdfs() {
        Counts = 0;
        for (int i = 0; i < 11; i++) {
            Visited[i] = false; //初始化标记数组
        }
    }

    void dfs(int v) { //深度优先遍历

        //Visited[v]=true;
        for (int j = 1; j < 11; j++) {
            if (!Visited[j] && G.HasEdge(v, j)) {
                Visited[j] = true; //标记已访问
                Counts++;  //记录连通分量
                dfs(j); //递归
            }
        }
        //*Visited[v]=true; //维护联通分量
        //    id[v]=ccount;
        //   for(int i:G.adj(v)){//遍历所有元素
        //      if(!Visited[i]){
        ///         dfs(i);
        //      }
    }

    // }
    int Counts() {
        return Counts; //返回连通片大小
    }

    private void gameOver(int winOrLoseOrDraw) {  //没有用 还没写

        String msg = "";
        if (winOrLoseOrDraw > 0) {
            //     msg = "你击败了" + NAME_OF_AI[difficulty - 1];
        } else if (winOrLoseOrDraw == 0) {
            msg = "平局";
        } else if (winOrLoseOrDraw < 0) {
            //       msg = "你被" + NAME_OF_AI[difficulty - 1] + "击败了";
        }
        msgDialog = new MessageDialog(GameActivity.this, msg);
        msgDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            setResult(RESULT_CANCELED, intent);
            GameActivity.this.finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    // private UpdateHandlerUI UIupdate = new UpdateHandlerUI();
    // class UpdateHandlerUI extends Handler{

    //    public  void handleMassage(Message msg){

    //    }
    // }


}
