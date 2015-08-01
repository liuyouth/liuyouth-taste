package com.zihao;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.easing.BaseEasingMethod;
import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class MainActivity1 extends Activity {
	
	Skill s;
	private TextView lab_for,lab_Name;
	private Button btn;
	
 @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main1);
	lab_Name = (TextView) findViewById(R.id.lab_name);
	btn = (Button) findViewById(R.id.btn);

	
	final Intent localIntent = new Intent(this, MainActivity.class);  
//    Timer timer = new Timer();  
//    TimerTask tast = new TimerTask() {  
//        @Override  
//        public void run() {  
//            startActivity(localIntent);  
//            finish();
//        }  
//    };  
//    timer.schedule(tast, 1500);

	btn.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			  startActivity(localIntent);
			  finish();
		}
	});
 }
}
