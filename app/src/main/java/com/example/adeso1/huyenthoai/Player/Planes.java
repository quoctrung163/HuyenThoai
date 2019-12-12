package com.example.adeso1.huyenthoai.Player;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.adeso1.huyenthoai.R;

import static com.example.adeso1.huyenthoai.Player.GameView.screenRatioX;
import static com.example.adeso1.huyenthoai.Player.GameView.screenRatioY;


public class Planes {
    public  int speed=20;
    int x,y,width,height,planeCounter=1;
    Bitmap plane1,plane2,plane3,plane4;

    Planes(Resources res)
    {
        plane1= BitmapFactory.decodeResource(res, R.drawable.bird1);
        plane2= BitmapFactory.decodeResource(res, R.drawable.bird2);
        plane3= BitmapFactory.decodeResource(res, R.drawable.bird3);
        plane4= BitmapFactory.decodeResource(res, R.drawable.bird4);
        width=plane1.getWidth();
        height=plane1.getHeight();
        width /=6;
        height/=6;
        width *=(int)screenRatioX;
        height *=(int)screenRatioY;
        plane1=Bitmap.createScaledBitmap(plane1,width,height,false);
        plane2=Bitmap.createScaledBitmap(plane2,width,height,false);
        plane3=Bitmap.createScaledBitmap(plane3,width,height,false);
        plane4=Bitmap.createScaledBitmap(plane4,width,height,false);

       // y=-height;

    }
    Bitmap getPlanes() {
        if (planeCounter == 1) {
            planeCounter++;
            return  plane1;
        }
        if (planeCounter == 2) {
            planeCounter++;
            return  plane2;
        }
        if (planeCounter == 3) {
            planeCounter++;
            return  plane3;
        }
       planeCounter=1;
        return  plane4;
    }

    Rect getShape()
    {
        return  new Rect(x,y,x+width,y+height);
    }


}
