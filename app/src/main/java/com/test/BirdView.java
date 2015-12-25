package com.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class BirdView extends View {
	
	private Bitmap sourceBitmap;
	private BirdModel bird;
	private Paint paint;
	private int canvasWidth;

	public BirdView(Context context,int sourceId,int canvasWidth) {
		super(context);
		this.canvasWidth = canvasWidth;
		bird = new BirdModel();
		sourceBitmap = BitmapFactory.decodeResource(context.getResources(), sourceId);
		Bitmap[] wings = new Bitmap[6];
		int width = sourceBitmap.getWidth();
		int height = sourceBitmap.getHeight();
		for (int i = 0; i < 6; i++) {
			Bitmap wing = Bitmap.createBitmap(sourceBitmap, width*i/6, 0, width/6, height);
			wings[i] = wing;
		}
		bird.setWings(wings);
		bird.setY(20);
		bird.setX(canvasWidth);
		paint = new Paint();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(bird.getWing(), bird.getX(), bird.getY(), paint);
		bird.setX(bird.getX()-2);
		if(bird.getX()<=0){
			bird.setX(canvasWidth);
		}
		postInvalidateDelayed(100);
		super.onDraw(canvas);
	}
	
	
	
}
