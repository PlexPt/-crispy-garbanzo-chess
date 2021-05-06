package cn.Jiangyiping.game;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import cn.Jiangyiping.reversi.R;

/**
 *  棋盘界面
 */
public class ChessView extends SurfaceView implements Callback {

	private RenderThread thread;
	private  float X;
	private  float Y;
	private  int Row=0;
	private  int Col=0;
	Path linePath;
	/**
	 * 屏幕宽度
	 */
	private float screenWidth;

	/**
	 * 本View背景宽高
	 */
	private float bgLength;
	private  float blackwidth;
	/**
	 * 本View棋盘宽高
	 */
	private float chessBoardLength;


	private static final int M = 9; //9个格子
	private  static  final  int  Line = 10; //10根线
	/**
	 * 棋格边长
	 */
	private float a;
	private  float b;
	private Rect rect;
	private boolean act = false;
	private  float width;
	private float chessBoardLeft;
	private float chessBoardRight;
	private float chessBoardTop;
	private float chessBoardBottom;
	private int StartX=0,StartY=0,EndX=0,EndY=0;
	private int Startx[];
	private int Starty[];
	private int Endx[];
	private boolean flag[];
	private boolean sign;
	private int Endy[];
	private static int times=46;
	Bitmap chess;
	private static final byte NULL = Constant.NULL;
	private static final byte BLACK = Constant.BLACK;
	//private static final byte WHITE = Constant.WHITE;//constant 里面定义了NULL BLACK WHITE的值

	private float margin; //与边缘的距离
	private  int time=0;
	private byte[][] chessBoard;
	private int[][] index;

	private Bitmap[] images; //棋子转换bitmap

	private Bitmap background; //背景Bitmap

	private float ratio = 0.9f;

	public ChessView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ReversiView);
		ratio = typedArray.getFloat(R.styleable.ReversiView_ratio, 0.9f);
		getHolder().addCallback(this);   //回调方法
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);//将当前窗口的一些信息,宽高维度 以pixel为单位 放在DisplayMetrics
		screenWidth = dm.widthPixels; //获取不同机型的屏幕宽
		bgLength = screenWidth *0.9f; //对应比例本view长
		chessBoardLength = 8f / 9f * bgLength; //棋盘长
		width = chessBoardLength / M; //每个棋格长 正方形 长宽相等
		a = width; //棋子大小
		b = a/2; //移动a/2 这样就在棋格线上
		margin = 1f / 18f * bgLength;
		chessBoardLeft = margin;
		blackwidth = 0.8f*bgLength;
		chessBoardRight = chessBoardLeft + M * width; //棋盘右边边线
		chessBoardTop = margin; //线与棋盘的边距
		chessBoardBottom = chessBoardTop + M * width; //棋盘底部

		initialChessBoard();
		initiallinepoint();
	//	update(Row, Col);
		time++;
		Log.d("timeS", time+"次数");
	}

	public ChessView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ChessView(Context context) {
		this(context, null, 0);
	}

	public void initialChessBoard(){    //初始化棋盘
		chessBoard = new byte[Line][Line];
		index = new int[Line][Line];

		for (int i = 0; i < Line; i++) {
			for (int j = 0; j < Line; j++) {
				chessBoard[i][j] = NULL;
				index[i][j]=0;
			}
		}

	}
	public void initiallinepoint(){
		Startx = new int[times];//times46 0-45; 10个点最多45根连线
		Starty = new int[times];
		Endx = new int[times];
		Endy = new int[times];
		flag = new boolean[times];
		if(time!=1){
			for (int i=0;i<times;i++)
			{
				Starty[i]=-1;  //Startxy 所有连线起点 Endxy所以连线终点
				Startx[i]=-1;
				Endx[i]=-1;
				Endy[i]=-1;
				flag[i]=false;
			}
		}

	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) bgLength, View.MeasureSpec.EXACTLY);
		heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) bgLength, View.MeasureSpec.EXACTLY);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}  //重新测量宽高，确保宽高一样

	public void update(int Row,int Col) {  //更新棋盘数组
		chessBoard[Row][Col] = BLACK;
		act = true;

	}

	public boolean inChessBoard(float x, float y) {
		return x >= chessBoardLeft && x <= chessBoardRight && y >= chessBoardTop && y <= chessBoardBottom;
	}  //在合理范围里面

	public int getRow(float y) {  //获取在几行
		//return (int) Math.floor((y - chessBoardTop) / width);
		this.Row = (int) Math.round((y - chessBoardTop) / width);
		Log.v("String", Row+" 行");
		return Row;
	} //最小取整

	public int getCol(float x) {  //获取在几列
		this.Col= (int) Math.round((x - chessBoardLeft) / width);
		return Col;
	}


	public void  getX(float X){
		this.X = X;    //获取X Y坐标
	}
	public  void getY(float Y){
		this.Y = Y;
	}

	public  void  getStart(int row,int col,int count){
		this.Startx[count]= col;
		this.Starty[count] = row;
		Log.d("getstart", row+"行");
		Log.d("getStart", Starty[count]+"行");
		Log.d("getStart", count+"count");
	}
	public  void  getEnd(int row,int col,int count){
		this.Endy[count] = col;
		this.Endy[count] = row;
		Log.d("getEnd", row+"行");
	}
	public void updateline(int Startx,int Starty,int Endx,int Endy,int count) {  //更新棋盘数组
		this.Startx[count] =Startx;
		this.Starty[count] = Starty;
		this.Endx[count] = Endx;
		this.Endy[count] = Endy;
		act = true;

	}
/*	public void getMessage(int down){
		int x;
		x=down;
		Log.d("getmessagedown", down+"值");
		if (x == 1)
		{
			this.sign=true;
		}

		else
		{
			this.sign=false;
		}

		Log.d("getmessageSIGN", sign+"值");
	}*/

	public void render(Canvas canvas) {
		/**
		 * 画棋盘边框
		 */
		Paint paint2 = new Paint();
		paint2.setColor(Color.YELLOW);
		paint2.setStrokeWidth(6);
		for (int i = 0; i < 10; i++) {  //10行9格 画在线上
			canvas.drawLine(chessBoardLeft, chessBoardTop + i * width, chessBoardRight, chessBoardTop + i * width, paint2);
			canvas.drawLine(chessBoardLeft + i * width, chessBoardTop, chessBoardLeft + i * width, chessBoardBottom, paint2);
			canvas.save();

		}Log.d("TAG", "画棋格");
		/**
		 * 画棋子
		 */
		chess = loadBitmap(a, a, getContext().getResources().getDrawable(R.drawable.darkyellow));
		Paint paint3 = new Paint();
		paint3.setAntiAlias(true);
		paint3.setDither(true);
		for (int col = 0; col < Line; col++) {
			for (int row = 0; row < Line; row++) {
				if (chessBoard[row][col] == BLACK&&true) {  //怎么画在线上是问题
					Log.v("dian","00");
					canvas.drawBitmap(chess, chessBoardLeft + col * width-a/2, chessBoardTop + row * width-a/2, paint3);
					canvas.save();

				}
			}
		}
		Log.d("TAG", "画棋子");
//                       画连线
		Paint paint1 =new Paint();
		paint1.setColor(Color.RED);
		paint1.setStrokeWidth(6);
		paint1.setAntiAlias(true);   // 不断刷新已经连线的点 保持连线一直在最上方
		paint1.setDither(true);
		for(int f=0;f<times;f++)
		{
			if(Endx[f]!=-1&&Endy[f]!=-1&&Startx[f]!=-1&&Starty[f]!=-1)
			{
				Log.d("hang", Starty[f]+"行");
				canvas.drawLine(chessBoardLeft + Startx[f]* width, chessBoardTop+Starty[f]*width, chessBoardLeft + Endx[f] * width, chessBoardTop+Endy[f]*width, paint1);
				canvas.save(); //画连线
			}
		}
		Log.d("SIGN", sign+"值");

		StartX=0;StartY=0;EndY=0;EndX=0;
	}
//有效点击才能连线！！！ 反复重画之前的连线 存起来反复画 但是怎么存次序呢
	/*public int defaultIndex(int color) {
		if (color == WHITE)
			return 11;
		else if (color == BLACK)
			return 0;
		return -1;
	}*/

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread = new RenderThread(getHolder(), this);
		thread.setRunning(true);
		thread.start();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread.setRunning(false);
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}


	public Bitmap loadBitmap(float width, float height, Drawable drawable) {  //画图

		Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, (int) width, (int) height);
		drawable.draw(canvas);
		return bitmap;
	}


}
