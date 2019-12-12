package com.example.adeso1.huyenthoai.Player.work;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.adeso1.huyenthoai.Player.GameView;
import com.example.adeso1.huyenthoai.R;

import static com.example.adeso1.huyenthoai.Player.GameView.screenRatioX;
import static com.example.adeso1.huyenthoai.Player.GameView.screenRatioY;


public class Fight {
    public float x;
    public float y;
    float height;
    float width;
    float wingCount=0;
    float shootCounter=1;
    Point i=new Point();
    int toshoot=1;
    Bitmap dead;
    public boolean isGoing=false;

    private GameView gameView;
    Bitmap fight1,fight2,shoot1,shoot2,shoot3,shoot4,shoot5;
    public Fight(GameView gameView, int screenY, Resources res)
    {
        this.gameView=gameView;
        fight1= BitmapFactory.decodeResource(res, R.drawable.fly1);

        fight2= BitmapFactory.decodeResource(res,R.drawable.fly2);
        width=fight1.getWidth();
        height=fight2.getHeight();
        width /=5;
        height /=4.5;

       width *=(int)screenRatioX;
        height *=(int)screenRatioY;
        fight1=Bitmap.createScaledBitmap(fight1,(int)width,(int)height,false);
        fight2=Bitmap.createScaledBitmap(fight2,(int)width,(int)height,false);

        shoot1 =BitmapFactory.decodeResource(res,R.drawable.imgmaybay);
        shoot2 =BitmapFactory.decodeResource(res,R.drawable.imgmaybay);
        shoot3 =BitmapFactory.decodeResource(res,R.drawable.imgmaybay);
        shoot4 =BitmapFactory.decodeResource(res,R.drawable.imgmaybay);
        shoot5 =BitmapFactory.decodeResource(res,R.drawable.imgmaybay);

        shoot1=Bitmap.createScaledBitmap(shoot1,(int)width,(int)height,false);
        shoot2=Bitmap.createScaledBitmap(shoot2,(int)width,(int)height,false);
        shoot3=Bitmap.createScaledBitmap(shoot3,(int)width,(int)height,false);
        shoot4=Bitmap.createScaledBitmap(shoot4,(int)width,(int)height,false);
        shoot5=Bitmap.createScaledBitmap(shoot5,(int)width,(int)height,false);
        dead=BitmapFactory.decodeResource(res,R.drawable.dead);

    }
    public Bitmap getFight()
    {   if(toshoot !=0)
    {
        if (shootCounter==0)
        {
            shootCounter++;
            return shoot1;
        }
        if (shootCounter==1)
        {
            shootCounter++;
            return shoot2;
        }
        if (shootCounter==2)
        {
            shootCounter++;
            return shoot3;
        }
        if (shootCounter==3)
        {
            shootCounter++;
            return shoot4;
        }if (shootCounter==4)

        shootCounter-=shootCounter;
        toshoot ++;

        gameView.newBullet();
        return shoot5;

    }
        if(wingCount==0)
        {
            wingCount++;
            return  fight1;
        }
        wingCount--;
        return fight2;
    }


   public Rect getShape()
    {
        return  new Rect((int)x,(int)y,(int)(x+width),(int)(y+height));
    }
   public Bitmap getDead()
    {
        return  dead;
    }

}
