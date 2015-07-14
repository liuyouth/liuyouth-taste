package com.zihao.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zihao.Date;
import com.zihao.ListUrltask;
import com.zihao.R;
import com.zihao.ui.DragLayout;
import com.zihao.ui.ViewExpandAnimation;

public class STSongListAdapter extends BaseAdapter {

	private Context context ;
	private ArrayList<STSongMessage> list;
	private LayoutInflater inflater=null;
	private int mLcdWidth = 0;
	private float mDensity = 0;
	private DragLayout mDragLayout;
	private ViewHolder mLastTouchTag = null;
	public int isPlayingSong ;
	private Date app;

	
	public STSongListAdapter(Context context,ArrayList<STSongMessage> list,Date app) {
	this.context =context;
	this.list= list;
	this.app = app ;
//	inflater = LayoutInflater.from(context);
	
	inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	

	
//	@SuppressLint({ "ResourceAsColor", "InflateParams" })
//	@Override
//	public View getView( final int position,    View convertView, ViewGroup parent) {
//		
//		 final ViewHolder holder;
//		if (convertView==null) {
//		    holder = new ViewHolder();
//			convertView =  inflater.inflate(R.layout.songlist_item1,  null);
//		    holder.img = (ImageView) convertView.findViewById(R.id.item_img);
//			holder.item_bg = (LinearLayout) convertView.findViewById(R.id.item_bg);
//			holder.text = (TextView) convertView.findViewById(R.id.item_songID);
//			holder.singer = (TextView) convertView.findViewById(R.id.item_songSingerName);
//			holder.songtext = (TextView) convertView.findViewById(R.id.item_songName);
//			holder.item_download = (ImageView) convertView.findViewById(R.id.item_download);
//			holder.item_like = (ImageView) convertView.findViewById(R.id.item_like);
//			holder.item_play = (ImageView) convertView.findViewById(R.id.item_down);
//			holder.footerView = convertView.findViewById(R.id.item_footer);
//			holder.isplaying = (TextView) convertView.findViewById(R.id.lab_playing);
//			convertView.setTag(holder);
//			
//			
//		}else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//		holder.singer.setText(list.get(position).getSinger());
//		holder.img.setImageBitmap(list.get(position).getSingerImg());   //显示图片  
//		holder.img.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				list.get(position).StartActivity(position,list);
//			}
//		});
//		
//		holder.songtext.setText(list.get(position).getSongName());
//		holder.text.setText(list.get(position).getSongID());
////		设置item这一行的背景
//	    AnimationSet set = new AnimationSet(true);
//			Animation animation = new TranslateAnimation(500, 0, 0, 0);
//			animation.setDuration(500);
//			Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
//		    alphaAnimation.setDuration(500);
//	        set.addAnimation(animation);		
//	        set.addAnimation(alphaAnimation);		
//			LayoutAnimationController laController = new LayoutAnimationController(set);
//			laController.setOrder(LayoutAnimationController.ORDER_NORMAL);
//			holder.item_bg.setLayoutAnimation(laController);
////			设置cell背景
////			holder.item_bg.setBackgroundColor((position & 1) == 1 ? R.color.item2 : 0); 
////			holder.item_bg.setBackgroundColor(R.color.item1); 
//			holder.item_bg.setBackgroundResource((position & 1) == 1 ? R.color.item1 : 0);
////		设置这一行item的歌曲名字 被点击的事件
//		holder.item_bg.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//			
//				for (int i = 0; i < list.size(); i++) {
//					list.get(i).setIsPlaying(0);
//				}
//				playingmusic(holder,position);
//		
//			
//			}
//
//			
//		});
//		
//		
//		
//		holder.item_download.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//				Toast.makeText(context, "下载", Toast.LENGTH_LONG)
//						.show();
//			}
//		});
//		
//		
//
//		holder.item_like.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Toast.makeText(context, "like", Toast.LENGTH_LONG)
//				.show();
//			}
//		});
//
//		
//		
//
//		
//		holder.item_play.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				for (int i = 0; i < list.size(); i++) {
//					if (1==list.get(i).getIsOpening()) {
//						
//					}
//				}
//				
//				holder.footerView.startAnimation(new ViewExpandAnimation(
//						holder.footerView));
//
//				float pivotX = holder.item_play.getWidth() / 2f;
//				float pivotY = holder.item_play.getHeight() / 2f;
//				float fromDegrees = 0f;
//				float toDegrees = 0f;
//				
//				if (0 == list.get(position).getIsOpening()) {
//					fromDegrees = 0f;
//					toDegrees = 180f;
//					list.get(position).setIsOpening(1);
//					list.get(position).setIsPlayingImg(R.drawable.item_top);
//				} else if (1 == list.get(position).getIsOpening()) {
//					fromDegrees = 180f;
//					toDegrees = 360f;
//				list.get(position).setIsOpening(0);
//				list.get(position).setIsPlayingImg(R.drawable.item_down);
//				}
//				RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees,
//						pivotX, pivotY);
//				animation.setDuration(400);
//				animation.setFillAfter(true);
//				holder.item_play.startAnimation(animation);
//			}});
//		
//		
//		/**
//		 * 省略掉其他操作，使用者可以自行扩展，例如长按操作，弹出自定义Toast等等。
//		 * 		 
//		 */
//		
//		
//		/**
//		 *getHeight()取到的是view的实际高度，是最终显示出来的高度；
//		 * getMeasuredHeight()取到的是最后一次调用measure方法后得到的高度，不一定是最终高度；
//		 * 通常在创建自定义视图组件时会用到getMeasuredHeight
//		 * ()，比如在onLayout(),onMeasure()等方法中。（官方文档：This should be used during
//		 * measurement and layout calculations only.）
//		 */
//		RelativeLayout footer = (RelativeLayout) convertView
//				.findViewById(R.id.item_footer);
//		int widthSpec = MeasureSpec.makeMeasureSpec(
//				(int) (mLcdWidth - 10 * mDensity), MeasureSpec.EXACTLY);
//		footer.measure(widthSpec, 0);
//		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) footer
//				.getLayoutParams();
//		params.bottomMargin = -footer.getMeasuredHeight();
//		footer.setVisibility(View.GONE);
////        把listcell的State（状态）还原
//		
//		
//		if (list.get(position).getIsOpening() == 1) {
//			holder.footerView.startAnimation(new ViewExpandAnimation(
//					holder.footerView));
//	holder.item_play.setImageResource(list.get(position).getIsPlayingImg());
//			
//		
//		}
//		if (list.get(position).getIsPlaying()==1) {
//			holder.isplaying.setText("_");
//			
//		}
//		
////		还原结束
//		return convertView;
//	}
	@SuppressLint({ "ResourceAsColor", "InflateParams" })
	@Override
	public View getView( final int position,    View convertView, ViewGroup parent) {
		
		 final ViewHolder holder;
		if (convertView==null) {
		    holder = new ViewHolder();
			convertView =  inflater.inflate(R.layout.songlist_item1,  null);
		    holder.img = (ImageView) convertView.findViewById(R.id.item_img);
			holder.item_bg = (LinearLayout) convertView.findViewById(R.id.item_bg);
			holder.text = (TextView) convertView.findViewById(R.id.item_songID);
			holder.singer = (TextView) convertView.findViewById(R.id.item_songSingerName);
			holder.songtext = (TextView) convertView.findViewById(R.id.item_songName);
			holder.item_download = (ImageView) convertView.findViewById(R.id.item_download);
			holder.item_like = (ImageView) convertView.findViewById(R.id.item_like);
			holder.item_play = (ImageView) convertView.findViewById(R.id.item_down);
			holder.footerView = convertView.findViewById(R.id.item_footer);
			holder.isplaying = (TextView) convertView.findViewById(R.id.lab_playing);
			convertView.setTag(holder);
			
			
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
	
			
		System.out.println("11111111111111111"+position);
		holder.singer.setText(list.get(position).getSinger());
//		holder.img.setImageBitmap(list.get(position).getSingerImg());   //显示图片  
//		holder.img.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				list.get(position).StartActivity(position,list);
//			}
//		});
		
		holder.songtext.setText(list.get(position).getSongName());
		holder.text.setText(list.get(position).getSongID());
//		设置item这一行的背景  与动画
//		holder.item_bg.setBackgroundResource((position & 1) == 1 ? R.color.item1 : 0);
//		holder.item_bg.setBackgroundResource(R.color.item1);
	    AnimationSet set = new AnimationSet(true);
			Animation animation = new TranslateAnimation(500, 0, 0, 0);
			animation.setDuration(500);
			Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		    alphaAnimation.setDuration(500);
	        set.addAnimation(animation);		
	        set.addAnimation(alphaAnimation);		
			LayoutAnimationController laController = new LayoutAnimationController(set);
			laController.setOrder(LayoutAnimationController.ORDER_NORMAL);
			holder.item_bg.setLayoutAnimation(laController);

//		设置这一行item的歌曲名字 被点击的事件
		holder.item_bg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setIsPlaying(0);
				}
				playingmusic(holder,position);
		
			
			}

			
		});
		
		
		
		holder.item_download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Toast.makeText(context, "下载", Toast.LENGTH_LONG)
						.show();
			}
		});
		
		

		holder.item_like.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "like", Toast.LENGTH_LONG)
				.show();
			}
		});

		
		

		
		holder.item_play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				for (int i = 0; i < list.size(); i++) {
					if (1==list.get(i).getIsOpening()) {
						
					}
				}
				
				holder.footerView.startAnimation(new ViewExpandAnimation(
						holder.footerView));

				float pivotX = holder.item_play.getWidth() / 2f;
				float pivotY = holder.item_play.getHeight() / 2f;
				float fromDegrees = 0f;
				float toDegrees = 0f;
				
				if (0 == list.get(position).getIsOpening()) {
					fromDegrees = 0f;
					toDegrees = 180f;
					list.get(position).setIsOpening(1);
					list.get(position).setIsPlayingImg(R.drawable.item_top);
				} else if (1 == list.get(position).getIsOpening()) {
					fromDegrees = 180f;
					toDegrees = 360f;
				list.get(position).setIsOpening(0);
				list.get(position).setIsPlayingImg(R.drawable.item_down);
				}
				RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees,
						pivotX, pivotY);
				animation.setDuration(400);
				animation.setFillAfter(true);
				holder.item_play.startAnimation(animation);
			}});
		
		
		/**
		 * 省略掉其他操作，使用者可以自行扩展，例如长按操作，弹出自定义Toast等等。
		 * 		 
		 */
		
		
		/**
		 *getHeight()取到的是view的实际高度，是最终显示出来的高度；
		 * getMeasuredHeight()取到的是最后一次调用measure方法后得到的高度，不一定是最终高度；
		 * 通常在创建自定义视图组件时会用到getMeasuredHeight
		 * ()，比如在onLayout(),onMeasure()等方法中。（官方文档：This should be used during
		 * measurement and layout calculations only.）
		 */
		RelativeLayout footer = (RelativeLayout) convertView
				.findViewById(R.id.item_footer);
		int widthSpec = MeasureSpec.makeMeasureSpec(
				(int) (mLcdWidth - 10 * mDensity), MeasureSpec.EXACTLY);
		footer.measure(widthSpec, 0);
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) footer
				.getLayoutParams();
		params.bottomMargin = -footer.getMeasuredHeight();
		footer.setVisibility(View.GONE);
//        把listcell的State（状态）还原
		
		
		if (list.get(position).getIsOpening() == 1) {
			holder.footerView.startAnimation(new ViewExpandAnimation(
					holder.footerView));
	holder.item_play.setImageResource(list.get(position).getIsPlayingImg());
			
		
		}
		if (list.get(position).getIsPlaying()==1) {
			holder.isplaying.setText("_");
			
		}
//		还原结束
		return convertView;
}

	protected void playingmusic(ViewHolder holder, int position) {
		// TODO Auto-generated method stub
		
		if (0 == list.get(position).getIsPlaying()) {
			
//			holder.isplaying.setText("_");
			
			ListUrltask urltask = new ListUrltask(app,list.get(position).getSongID());
			urltask.execute();
			urltask = null;
			list.get(position).setIsPlaying(1);
			app.setPlayingPosition(position);
			
		} else if (1 == list.get(position).getIsPlaying()) {
			
      		
//      		holder.isplaying.setText("");
      	app.getMusicService1().stop();
      	list.get(position).setIsPlaying(0);
		}
		
		
	}

	public final class ViewHolder {
		public TextView text;
		public TextView singer;
		public TextView songtext;
		public ImageView img;
		public LinearLayout item_bg;
		public ImageView item_download;
		public ImageView item_like;
		public ImageView item_play;
		public View footerView;
		public TextView isplaying;

		
	}
	
	
	

}
