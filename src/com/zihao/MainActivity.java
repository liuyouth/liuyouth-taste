package com.zihao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zihao.adapter.GetMenu;
import com.zihao.adapter.STSongListAdapter;
import com.zihao.adapter.STSongListAdapter.ViewHolder;
import com.zihao.adapter.tryhttp;
import com.zihao.ui.AutoScrollTextView;
import com.zihao.ui.DragLayout;
import com.zihao.ui.SegmentControl;
import com.zihao.ui.DragLayout.DragListener;
import com.zihao.ui.RefreshableView.PullToRefreshListener;

public class MainActivity extends Activity {

	  
	/** 声明变量 */
	private tryhttp tryhttp;
	private STSongListAdapter adapter;
	private ViewHolder mLastTouchTag = null;
	private String zhengze;
	private int sNameNumb=2,sIdNumb=3,sImgNumb=6,singerNumb=4;
	private DragLayout mDragLayout;
	private com.zihao.ui.RefreshableView refreshableView;
	private ListView menuListView, songListView;// 设置ListView变量
	private ImageButton menuSettingBtn;// 声明liftMenuBar 左边窗口
	private SegmentControl m_SegmentControl ;
	private String[] sTMenuStrings = { "Home", "Anything",
			"China", "Cantonese", "English",
			"Other", "J&K" };
	private String indexMenu;
	private Date app;
	private TextView menuListTop;
	
	private SeekBar seekBar ;
	private ImageView singerIMG;
	private ImageView btn_play,btn_last,btn_next,btn_like;
	private TextView lab_songName,lab_songid,lab_taste;
	private AutoScrollTextView  lab_songName1;
	
	


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	    final Handler msgHandler = new Handler(){  
	       
	    	public void handleMessage(Message msg) {  
	                switch (msg.arg1) {  
	                case R.string.msg_not_network:  
	                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_not_network), Toast.LENGTH_SHORT).show();  
	                        break;  
	                default:  
	                        break;  
	                }  
	        }  
	};  
		
	   app = (Date) getApplication();
	   
	   app.setHandler(msgHandler);
		/**
		 * 设置列表 设置窗体
		 */
		refreshableView = (com.zihao.ui.RefreshableView) findViewById(R.id.refreshable_view);

		mDragLayout = (DragLayout) findViewById(R.id.dl);
		menuListTop = (TextView) findViewById(R.id.playingName);
		mDragLayout.setDragListener(new DragListener() {// 设置总窗体
					@Override
					public void onOpen() {
					}

					@Override
					public void onClose() {
					}

					@Override
					public void onDrag(float percent) {

					}
				});

	
		// 设置list数据

		List<Map<String, Object>> data1 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < sTMenuStrings.length; i++) {
			Map<String, Object> item;
			item = new HashMap<String, Object>();
			item.put("item", sTMenuStrings[i]);

			data1.add(item);
		}

		menuListView = (ListView) findViewById(R.id.menu_listview);
		menuListView.setCacheColorHint(Color.TRANSPARENT);
		menuListView.setAdapter(new SimpleAdapter(this, data1,
				R.layout.menulist_item_text, new String[] { "item" },
				new int[] { R.id.menu_text }));

		
		menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sNameNumb=4	;	
				sIdNumb=3;
				sImgNumb=7;singerNumb=8;
				switch (arg2) {
				case 1:
					indexMenu =  getString(R.string.stm_url_yz_suoyou);
					zhengze = getString(R.string.stm_zhengze_yz);
					break;
				case 2:
					indexMenu =  getString(R.string.stm_url_yz_china);
					zhengze = getString(R.string.stm_zhengze_yz);
					break;
				case 3:
					indexMenu =  getString(R.string.stm_url_yz_yue);
					zhengze = getString(R.string.stm_zhengze_yz);
					break;
				case 4:
					indexMenu =  getString(R.string.stm_url_yz_english);
					zhengze = getString(R.string.stm_zhengze_yz);
					break;
				case 5:
					indexMenu =  getString(R.string.stm_url_yz_other);
					zhengze = getString(R.string.stm_zhengze_yz);
					break;
				case 6:
					indexMenu =  getString(R.string.stm_url_yz_rh);
					zhengze = getString(R.string.stm_zhengze_yz);
					break;

				default:
					sNameNumb=2;sIdNumb=3;sImgNumb=6;singerNumb=4;
					indexMenu =  getString(R.string.stm_url_dajiatuijian);
					zhengze = getString(R.string.stm_zhengze_dajiatuijian);
					break;
				}
				mDragLayout.close();
			
				listRefresh();
				Log.d("listindex", String.format("%s", arg2));
//				menuListTop.setText("正在读取数据..."+sTMenuStrings[arg2]);
				
			}
			
			
		});
//		声明顶部控件
		initPlay();


		songListView = (ListView) findViewById(R.id.songlistView);
	

//		设置列表动画
	    AnimationSet set = new AnimationSet(true);
			Animation animation = new TranslateAnimation(0, 000, 500, 0);
			animation.setDuration(500);
			Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		    alphaAnimation.setDuration(500);
	        set.addAnimation(animation);		
	        set.addAnimation(alphaAnimation);		
			LayoutAnimationController laController = new LayoutAnimationController(set);
			laController.setOrder(LayoutAnimationController.ORDER_NORMAL);
			songListView.setLayoutAnimation(laController);
			
		indexMenu =  getString(R.string.stm_url_dajiatuijian);
		zhengze = getString(R.string.stm_zhengze_dajiatuijian);
		songListView.setItemsCanFocus(true);
		
		try {
			GetMenu getMenu = new GetMenu(this,app, songListView, indexMenu, zhengze, sNameNumb, sIdNumb, singerNumb, sImgNumb,sTMenuStrings[1]);
			getMenu.execute();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		//

		// 设置列表上拉事件
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {

			@Override
			public void onRefresh() {

				/*
				 * doinbackground里面要做后台的任务操作,不能对UI组件修改,要在住进程中设置你的图片,
				 * onPostExecute这里面写UI操作就行. AsyncTask 安卓封装的轻量级异步处理 用子线程来进行工作
				 * 【这里是从网络中获得数据】 doinbackgroud 后台执行 这里是进行网络申请数据 onPostExecute
				 * 申请结束后处理的事件 现在这里用来处理ui
				 */
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						try {
							adapter = com.zihao.adapter.tryhttp.testGetHtml(
									MainActivity.this,
									indexMenu,zhengze,sNameNumb,sIdNumb,singerNumb,sImgNumb,app,(String) menuListTop.getText());
							Log.d("adapter try ", "adapter 成功");
						} catch (Exception e) {
							e.printStackTrace();
							Log.d("adapter try ", "adapter 失败");
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						songListView.setAdapter(adapter);
						adapter.notifyDataSetChanged();
						songListView.refreshDrawableState();
						refreshableView.finishRefreshing();
				
					}
				}.execute(null, null, null);
			}
		}, 0);

		// 设置左边窗体按钮 导航栏左边的bar
		menuSettingBtn = (ImageButton) findViewById(R.id.menu_imgbtn1);
		menuSettingBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 原本打开左侧窗口的功能
				mDragLayout.open();
			}
		});
	}
	private void listRefresh() {
	songListView.removeAllViewsInLayout();
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... params) {
				try {
					adapter = com.zihao.adapter.tryhttp.testGetHtml(
							MainActivity.this,
							indexMenu,zhengze,sNameNumb,sIdNumb,singerNumb,sImgNumb,app,(String) menuListTop.getText());
					Log.d("adapter try ", "adapter 成功");
				} catch (Exception e) {
					e.printStackTrace();
					Log.d("adapter try ", "adapter 失败");
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				songListView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				songListView.refreshDrawableState();
				
			}
		}.execute(null, null, null);

	}
	
	public void initPlay() {
		seekBar =  (SeekBar) findViewById(R.id.seekBar1);
		app.getMusicService1().musicServiceOnCreate(seekBar);
		lab_songid = (TextView) findViewById(R.id.playingID);
		lab_songName = (TextView) findViewById(R.id.playingName);
		lab_songName1 = (AutoScrollTextView) findViewById(R.id.playingName1);
		m_SegmentControl = (SegmentControl) findViewById(R.id.segment);
		lab_songName1.init(getWindowManager());
		app.setWindow(getWindowManager());
		lab_taste = (TextView) findViewById(R.id.playingTaste);
		singerIMG = (ImageView) findViewById(R.id.img_singerimg);
		btn_play = (ImageView) findViewById(R.id.menu_imgbtn_play);
		app.getMusicService1().initMp3Info(lab_songName1,lab_songid, lab_songName, singerIMG,lab_taste,btn_play);
		btn_like = (ImageView) findViewById(R.id.menu_imgbtn_like);
		btn_last = (ImageView) findViewById(R.id.menu_imgbtn_last);
		btn_next = (ImageView) findViewById(R.id.menu_imgbtn_next);
		m_SegmentControl.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
			
			@Override
			public void onSegmentControlClick(int index) {
				// TODO Auto-generated method stub
				
				switch (index) {
				
				
				case 0:
					sNameNumb=2;
					sIdNumb=3;
					sImgNumb=6;
					singerNumb=4;
					indexMenu =  getString(R.string.stm_url_dajiatuijian);
					zhengze = getString(R.string.stm_zhengze_dajiatuijian);
					break;
				case 1:
					sNameNumb=4	;	
					sIdNumb=3;
					sImgNumb=7;singerNumb=8;
					indexMenu =  getString(R.string.stm_url_yz_suoyou);
					zhengze = getString(R.string.stm_zhengze_yz);
					break;
				default:
					break;
				}
				listRefresh();
//				songListView.setSelectionFromTop(1, 100);
			
			}
		});
		ImageView btn_sView = (ImageView) findViewById(R.id.menu_imgbtn_next1);
		btn_sView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
			}
		});
		btn_last.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int inte = app.getPlayingPosition();
				inte = inte -1 ;
			playingmusic(inte);
			
			}
		});

		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int inte = app.getPlayingPosition();
				inte = inte +1 ;
			playingmusic(inte);
			}
		});
		btn_play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			if (app.getMusicService1().getisPause()) {
				btn_play.setBackgroundResource(R.drawable.img_button_pause_landscape_normal);
			}else {
				btn_play.setBackgroundResource(R.drawable.img_button_play_landscape_normal);
			}
		
			app.getMusicService1().pause();
			}	
		
		});
	}
	
	protected void playingmusic(int position) {
		// TODO Auto-generated method stub
		
		if (0 == app.getList().get(position).getIsPlaying()) {
			
//			holder.isplaying.setText("_");
			ListUrltask urltask = new ListUrltask(app,app.getList().get(position).getSongID());
			urltask.execute();
			urltask = null;
			app.getList().get(position).setIsPlaying(1);
			app.setPlayingPosition(position);
		} else if (1 == app.getList().get(position).getIsPlaying()) {
			
			
//      		holder.isplaying.setText("");
      	app.getMusicService1().stop();
      	app.getList().get(position).setIsPlaying(0);}
		}
	
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK  
                && event.getAction() == KeyEvent.ACTION_DOWN) {  
  
            new AlertDialog.Builder(this)  
                    .setIcon(R.drawable.ic_launcher)  
                    .setTitle("退出")  
                    .setMessage("您确定要退出？")  
                    .setNegativeButton("取消", null)  
                    .setPositiveButton("确定",  
                            new DialogInterface.OnClickListener() {  
  
                                @Override  
                                public void onClick(DialogInterface dialog,  
                                        int which) {  
                                	
                                	app.getMusicService1().stopSelf();
                                	System.exit(0); // 停止后台服务  
                                	ActivityManager am = (ActivityManager)getSystemService (Context.ACTIVITY_SERVICE);
                                	  am.restartPackage(getPackageName()); 
                                }  
                            }).show();  
  
        }  
        return super.onKeyDown(keyCode, event);  
    }  

	
}
