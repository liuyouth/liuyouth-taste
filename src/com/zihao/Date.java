package com.zihao;

import java.util.ArrayList;

import com.zihao.adapter.STSongMessage;
import com.zihao.service.MusicService1;

import android.app.Application;
import android.view.WindowManager;

public class Date extends Application {

	private MusicService1 musicService1;
	private String mp3info;
	private int playingPosition;
	private ArrayList<STSongMessage> list ;
	private WindowManager wm;
	
	

	
	public String getMp3info() {
		return mp3info;
	}



	public void setMp3info(String mp3info) {
		this.mp3info = mp3info;
	}



	public MusicService1 getMusicService1() {
		return musicService1;
	}



	public void setMusicService1(MusicService1 musicService1) {
		this.musicService1 = musicService1;
	}



	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		musicService1 = new MusicService1();
		super.onCreate();
	}



	public int getPlayingPosition() {
		return playingPosition;
	}



	public void setPlayingPosition(int playingPosition) {
		this.playingPosition = playingPosition;
	}



	public ArrayList<STSongMessage> getList() {
		
		return list;
	}



	public void setList(ArrayList<STSongMessage> list) {
		this.list = list;
	}



	public void setWindow(WindowManager windowManager) {
		// TODO Auto-generated method stub
		wm = windowManager;
	}
	public WindowManager getWindow() {
		// TODO Auto-generated method stub
		return wm;
	}
}
