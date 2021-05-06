package cn.Jiangyiping.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

// 控制棋盘画布刷新的界面
public class RenderThread extends Thread {

		private SurfaceHolder surfaceHolder;
		private ChessView chessView;
		private boolean running;
		public RenderThread(SurfaceHolder surfaceHolder, ChessView chessView){
			this.surfaceHolder = surfaceHolder;
			this.chessView = chessView;

		}
		
		@Override
		public void run() {
			Canvas canvas;
			while(running){
				canvas = null;
				long startTime = System.currentTimeMillis();
				//this.reversiView.update();// 这个应该是落棋子的时候转的 没用
				long endTime = System.currentTimeMillis();
				
				try{
					canvas = this.surfaceHolder.lockCanvas();
					synchronized (surfaceHolder) {
						this.chessView.render(canvas); //棋盘画布
						//canvas.save();
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					if(canvas != null){
						this.surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
				try{
					if((endTime - startTime) <= 100){
						sleep(100 - (endTime - startTime));
					}
					
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				
			}
		}

		public void setRunning(boolean running) {
			this.running = running;
		}
}
