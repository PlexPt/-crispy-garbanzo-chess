package cn.Jiangyiping.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.Jiangyiping.bean.Move;
import cn.Jiangyiping.bean.Statistic;

/**
 * 规则
 */
public class Rule {

	/**
	 * 这步棋下得是否合法
	 */
	public static boolean isLegalMove(byte[][] chessBoard, Move move) {

		int i, j, dirx, diry, row = move.row, col = move.col;
		if (!isLegal(row, col) || chessBoard[row][col] != Constant.NULL)
			return false;
		else
			return true;
	}
	public static boolean isLegalConnect(int row,int col,byte [][]chessBoard){  //判断在给有棋子的连线
		if (chessBoard[row][col]==Constant.BLACK)
		{
			return true;
		}
		else
			return false;
	}
	public static boolean isLegal(int row, int col) {     //在合法范围
		//return row >= 0 && row <= 8 && col >= 0 && col <= 8; //0-8 = 9
		return row >= 0 && row <= 9 && col >= 0 && col <= 9;
	}

	/**
	 * 使用前务必先确认该步合法
	 */
}
