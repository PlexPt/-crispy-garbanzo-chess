
//开始界面


package cn.Jiangyiping.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import cn.Jiangyiping.reversi.R;
import cn.Jiangyiping.game.Constant;


public class MainActivity extends Activity {

	private Button play; //开始游戏
	private Button rule; //游戏规则
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		play = (Button)findViewById(R.id.play_btn);
		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			//	byte playColor = (byte)preferences.getInt("playerColor", Constant.BLACK);
			//	int difficulty = preferences.getInt("difficulty", 1);

				Intent intent = new Intent(MainActivity.this, GameActivity.class);
				//Bundle bundle = new Bundle();  //跳转到主游戏页
			//	bundle.putByte("playerColor", playColor);
			//	bundle.putInt("difficulty", difficulty);
				//intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			}
		});

		rule = (Button)findViewById(R.id.rule_btn);  //跳转到游戏规则页
		rule.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, GameRuleActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
		});
	}

}
