package com.test;

import android.graphics.Bitmap;

public class BirdModel {
	
	private Bitmap[] wings;
	private int x;
	private int y;
	private int curWing;
	
	public BirdModel(){
		curWing = 0;
	}
	
	public Bitmap getWing(){
		curWing++;
		if(curWing>=wings.length){
			curWing = 0;
		}
		return wings[curWing];
	}
	
	public Bitmap[] getWings() {
		return wings;
	}
	public void setWings(Bitmap[] wings) {
		this.wings = wings;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getCurWing() {
		return curWing;
	}
	public void setCurWing(int curWing) {
		this.curWing = curWing;
	}
	
	
	

}
