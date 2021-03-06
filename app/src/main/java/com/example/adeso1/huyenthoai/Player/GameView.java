package com.example.adeso1.huyenthoai.Player;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;


import com.example.adeso1.huyenthoai.Player.work.Bullet;
import com.example.adeso1.huyenthoai.Player.work.Fight;
import com.example.adeso1.huyenthoai.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.graphics.Paint.Cap.ROUND;
import static androidx.core.content.ContextCompat.startActivity;


public  class  GameView extends SurfaceView implements Runnable{


        private  Thread thread;
        private int screenX,screenY,score=0;

        private Paint paint;

        public static float screenRatioX, screenRatioY;
        private  boolean isplaying;

        private GameActivity activity;
        private Planes[] planes;
        private Random random;
        private Fight fight;
        private List<Bullet> bullets;
        private boolean flagScrore=false;
        private static Context context;
        //thanhhp
        public int HP=360;
        //Game kết thúc
        private  boolean isgameover=false;
        private Background background1,background2;


        public GameView(GameActivity gameActivity,int screenX,int screenY) {
            super(gameActivity);
           this.activity=gameActivity;
            this.screenX=screenX;
            this.screenY=screenY;
            screenRatioX=1;
            screenRatioY=1;
            background1=new Background(screenX,screenY,getResources());
            background2=new Background(screenX,screenY,getResources());
            background2.y=-screenY;
            fight=new Fight(this,screenY,getResources());
            bullets =new ArrayList<>();
            paint = new Paint();
            planes=new Planes[4];
            for (int i=0;i<4;i++)
            {
                Planes plane=new Planes(getResources());
               planes[i]=plane;
            }
            //
            random=new Random();

        }

        @Override
        public void run() {
            while (isplaying){
                //Nếu thua thì thoát khỏi vòng lặp
                if(isgameover==true)
                {
                    break;
                }
                update();
                draw();
                sleep();
            }
            //Hiện cửa sổ khi thua
            activity.runOnUiThread(new Runnable() {
                public void run() {

                    /*
                    Dialog dialog=new Dialog(getContext());
                    dialog.setContentView(R.layout.activity_main);
                    dialog.show();

                     */
                    Intent myIntent = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(myIntent);
                }
            });
        }
        private void update(){
            //Cho backgroud di chuyển từ trên xuống dưới
            setBackground();
            //chỉnh đạn
            setBullet();
            //Chỉnh máy bay địch
            setPlane();
        }
        private  void draw(){


            if(getHolder().getSurface().isValid());
            {


               Canvas canvas=getHolder().lockCanvas();
                try {

                    if(canvas!=null)
                    {
                        //cho backgroud di chuyển từ trên xuống dưới
                        drawBackGroud(canvas);
                        //vẽ máy bay địch xuất hiện
                        drawPlane(canvas);
                        //vẽ máy bay mình đang bắn
                        drawMyPlane(canvas);
                        //Vẽ đạn
                        drawBullet( canvas);
                        //Vẽ điểm
                        drawScrore(canvas);
                        //Vẽ máu
                        drawBlood(canvas);

                    }

                } finally {
                    if(canvas!=null)
                    {
                        getHolder().unlockCanvasAndPost(canvas);
                    }
                }




            }

        }
        private void sleep(){
            try {
                thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void resume(){
            isplaying =true;
            thread=new Thread(this);
            thread.start();

        }
        public  void pause(){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onTouchEvent( MotionEvent event) {
            float dX = (float) event.getX();
            float dY = (float) event.getY();
            switch (event.getAction())
            {
                case MotionEvent.ACTION_POINTER_DOWN:

                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    fight.isGoing=true;

                    fight.x=dX;
                    fight.y=dY;

                    break;

            }
            return true;
        }
        public void newBullet() {
            Bullet bullet=new Bullet(getResources());;
            bullet.x = (float) (fight.x+screenX/52);
            bullet.y= (float) (fight.y-screenY/20);
            bullets.add(bullet);
        }
        public  void setBackground()
        {
            background1.y+=15-screenRatioY;
            background2.y+=15-screenRatioY;

            if(background1.y+(-background1.background.getHeight())>0){
                background1.y=-screenY;
            }
            if(background2.y+(-background1.background.getHeight())>0){
                background2.y=-screenY;
            }

            if(fight.isGoing==false)
            {
                fight.x=screenX/3+40;
                fight.y=screenY-(screenY/4);
            }
        }
        public void setPlane()
        {
            //Lấy từng máy bay
            for (int i=0;i<planes.length;i++)
            {

                planes[i].y+= planes[i].speed;

                if(planes[i].y+planes[i].height>screenY)
                {
                    int bound=(int)(30*screenRatioX);
                    planes[i].speed=random.nextInt(bound);
                    if(planes[i].speed<10*screenRatioY)
                    {
                        planes[i].speed=(int)(10*screenRatioY);

                    }
                    //cho máy bay địch di chuyển từ trên xuống dưới theo chiều dọc
                    planes[i].y=0;
                    planes[i].x=random.nextInt(screenX-planes[i].width);
                }
                //Nếu máy bay dc tạo ra mà lớn hơn chiều ngang của màn hình
                if(planes[i].width+planes[i].x>screenX)
                {
                    planes[i].y=0;
                    planes[i].x=random.nextInt(screenX-planes[i].width);
                }

            }
        }
        public void setBullet()
        {
            List<Bullet> trash=new ArrayList<>();

            for(Bullet bullet:bullets)
            {
                if (bullet.y>screenY)
                    trash .add(bullet);
                bullet.y-=300;
                //Xử lý va chạm với máy bay địch
                for (Planes pla:planes)
                {
                    //Nếu tọa độ máy bay địch với tọa độ đạn giống nhau
                    if(Rect.intersects(pla.getShape(),bullet.getShape()))
                    {
                       // bullet.getShape();
                        pla.x=-500;
                        bullet.x=screenX+500;
                        score+=5;
                    }
                    //Nếu tọa độ máy bay mình với địch giống nhau
                    if(Rect.intersects(pla.getShape(),fight.getShape()))
                    {
                        //Nếu hết máu
                        if(HP<=100)
                        {

                         isgameover=true;


                        }
                        HP-=100;
                        pla.x=-500;
                    }

                }
            }
            //Tự hủy đạn
            for(Bullet bullet:trash)
            {
                bullets.remove(bullet);
            }
        }
        public  void drawBackGroud(Canvas canvas )
        {
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);
        }
        public void drawPlane(Canvas canvas)
        {

            for (Planes pla:planes)
            {
                canvas.drawBitmap(pla.getPlanes(),pla.x,pla.y,paint);
            }
        }
        public void drawMyPlane(Canvas canvas)
        {
            canvas.drawBitmap(fight.getFight(),fight.x,fight.y,paint);
        }
        public void drawBullet(Canvas canvas)
        {
            for(Bullet bullet:bullets)
            {
                canvas.drawBitmap(bullet.bullet,bullet.x+screenX/46-1,bullet.y+screenX/35,paint);
                canvas.drawBitmap(bullet.bullet,bullet.x+screenX/9-3,bullet.y+screenX/35,paint);

            }
        }
        public void drawScrore(Canvas canvas)
        {if(flagScrore==false)
        {
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("Score:"+score + "",screenX-screenX/6,73,paint);
        }

            if(score>=95)
            {flagScrore=true;
                paint.setColor(Color.WHITE);
                paint.setTextSize(40);
                canvas.drawText("Score:"+score + "",screenX-screenX/5,70,paint);
            }
        }
        public  void drawBlood(Canvas canvas)
            {   Paint paintLineBlood=new Paint();


                    paintLineBlood.setColor(Color.GREEN);
                    paintLineBlood.setStrokeWidth(32);
                    paintLineBlood.setTextSize(35);
                //paint.setStrokeCap(ROUND);
                    canvas.drawText("HP",5,63,paintLineBlood);
                    canvas.drawLine(100,50,HP,50,paintLineBlood);



                Paint paintBlood=new Paint();


                paintBlood.setStyle(Paint.Style.STROKE);
                paintBlood.setColor(Color.GREEN);
                canvas.drawRect(100,33,screenX/3,66,paintBlood);


            }


    }

