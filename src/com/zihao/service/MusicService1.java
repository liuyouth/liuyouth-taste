package com.zihao.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.zihao.Date;
import com.zihao.adapter.STSongMessage;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Environment;
import android.os.IBinder;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MusicService1 extends Service implements Serializable {

	private boolean isPlaying = false;
	private boolean isPause = false;
	private boolean isReleased = false;
	private MediaPlayer mediaPlayer = null;
	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}


	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}




	private SeekBar seekBar;
	boolean isTimerRunning = true;
    boolean isChanging = false;
	private ImageView singerIMG;
	private ImageView btn_play,btn_last,btn_next,btn_back;
	private TextView lab_songName,lab_songid;
	private Date app; 
	private ArrayList<STSongMessage> list ;
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

		
	public MusicService1() {
		mediaPlayer = new MediaPlayer();

	}
	
	public void initMp3Info(TextView ID,TextView Name,ImageView singerImageView) {
		lab_songid = ID ;
		lab_songName = Name;
		singerIMG = singerImageView;
	}
	public void initMp3List( ArrayList<STSongMessage> list,Date app ) {
		this.list = list ; 
		this.app = app;
	}
public void musicServiceOnCreate(SeekBar seekBar) {
	this.seekBar=seekBar;
	seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {                 

	        @Override

	        public void onStopTrackingTouch(SeekBar seekBar) {

	                  // TODO Auto-generated method stub

	                  //当拖动停止后，控制mediaPlayer播放指定位置的音乐

	                  mediaPlayer.seekTo(seekBar.getProgress());

	      isChanging=false;  

	        }       

	        @Override

	        public void onStartTrackingTouch(SeekBar seekBar) {

	                  // TODO Auto-generated method stub

	                  isChanging=true;

	        }       

	        @Override

	        public void onProgressChanged(SeekBar seekBar, int progress,

	                           boolean fromUser) {

	                  // TODO Auto-generated method stub           

	        }

	});
	
}

	public void play(String mp3info) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
	
		
		

		lab_songid.setText(list.get(app.getPlayingPosition()).getSongID());
		singerIMG.setImageBitmap(list.get(app.getPlayingPosition()).getSingerImg());
		lab_songName.setText(list.get(app.getPlayingPosition()).getSongName());
		mediaPlayer.reset();
		mediaPlayer.setDataSource(mp3info);
		mediaPlayer.prepare();
		mediaPlayer.setLooping(true);
		seekBar.setMax(mediaPlayer.getDuration());
		mediaPlayer.start();
		mTimer.schedule(mTimerTask, 0, 10);
		isPlaying = true;
		isReleased = false;
		 mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
	            
	            @Override
	            public void onCompletion(MediaPlayer mp) {
	                // TODO Auto-generated method stub
	                
	            }
	        });
	   
	    }
	    
    public void stop() {
    	if (mediaPlayer != null) {
			if (isPlaying) {
				if (!isReleased) {
					mediaPlayer.stop();
					mediaPlayer.release();
					isReleased = true;
				}
				isPlaying = false;
			}
		}
	}

	public  void pause() {
		if (mediaPlayer != null) {
			if (!isReleased) {
				if (!isPause) {
					isChanging = false;
					mediaPlayer.pause();
					
					isPause = true;
					isPlaying = true;
				} else {
					
					mediaPlayer.start();
					isChanging = true;
					isPause = false;
				}
			}
		}
	}

	public void delete() {
		mediaPlayer.stop();
		mediaPlayer.release();
	}
	private String getMp3Path(String mp3Info) {
		String SDCardRoot = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
//		String path = SDCardRoot + File.separator + "mp3" + File.separator
//				+ mp3Info.getMp3Name();
		return mp3Info;
		
	}

	
	

	//----------定时器记录播放进度---------//

	Timer mTimer = new Timer();

	TimerTask mTimerTask = new TimerTask() {

	    @Override

	    public void run() {

	    	isTimerRunning = true;
			if(isChanging==true)//当用户正在拖动进度进度条时不处理进度条的的进度
				
			return; 
			if (isChanging==true) {
				
				seekBar.setProgress(mediaPlayer.getCurrentPosition());
			}
	    }
	};
	
}
