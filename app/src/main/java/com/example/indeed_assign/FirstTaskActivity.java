package com.example.indeed_assign;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class FirstTaskActivity extends AppCompatActivity {
    ArrayList<MyCard> cardViews = new ArrayList<>();
    RelativeLayout root;
    int id=0;
    View.OnTouchListener onTouchListener;
    private int _xDelta,_yDelta;
    ArrayList<String> X = new ArrayList<>();
    ArrayList<String> Y = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_task);
        root = findViewById(R.id.relativeLayout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        for(int i=0;i<width;i++){
            X.add(String.valueOf(i));
        }
        for(int i=0;i<height;i++){
            Y.add(String.valueOf(i));
        }

        onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        view.setScaleX(1.1f);
                        view.setScaleY(1.1f);
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        _xDelta = X - lParams.leftMargin;
                        _yDelta = Y - lParams.topMargin;
                        break;
                    case MotionEvent.ACTION_UP:
                        view.setScaleY(1f);
                        view.setScaleX(1f);
                        if (isColliding(view)){
                            TraceBack(view);
                        }
                        else{
                            cardViews.get(view.getId()).route.clear();
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isColliding(view)){

                            break;
                        }
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        layoutParams.leftMargin = X - _xDelta;
                        layoutParams.topMargin = Y - _yDelta;
                        cardViews.get(view.getId()).route.add(new MyCard.Trajectory(X - _xDelta,Y - _yDelta));
                        layoutParams.rightMargin = -250;
                        layoutParams.bottomMargin = -250;
                        view.setLayoutParams(layoutParams);
                        break;
                }
                root.invalidate();
                return true;
            }
        };
    }

    @SuppressLint("ClickableViewAccessibility")
    public void addCircle(View view) {
        MyCard card = new MyCard(getApplicationContext(), new ArrayList<MyCard.Trajectory>());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200,200);
        int[] i =getRandomCoordinates();
        layoutParams.leftMargin = i[0]-(100);
        layoutParams.topMargin = i[1]-(100);
        card.setLayoutParams(layoutParams);
        card.setCardBackgroundColor(Color.GREEN);
        StartAnimation(card);
        float z = 100f;
        card.setRadius(z);
        card.setId(id);
        root.addView(card);
        root.invalidate();
        cardViews.add(card);
        card.setOnTouchListener(onTouchListener);
        CountAvailableSpae(card);
        id++;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void addSquare(View view) {
        MyCard card = new MyCard(getApplicationContext(), new ArrayList<MyCard.Trajectory>());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200,200);
        int[] i =getRandomCoordinates();
        layoutParams.leftMargin = i[0];
        layoutParams.topMargin = i[1];
        card.setLayoutParams(layoutParams);
        card.setCardBackgroundColor(Color.GREEN);
        StartAnimation(card);
        float z = 10f;
        card.setRadius(z);
        card.setId(id);
        root.addView(card);
        root.invalidate();
        cardViews.add(card);
        card.setOnTouchListener(onTouchListener);
        id++;
    }

    private void StartAnimation(MyCard card) {
        Handler handler = new Handler();
        (new Thread(){
            @Override
            public void run(){
                for(int i=0; i<255; i++){
                    int finalI = i;
                    handler.post(new Runnable(){
                        public void run(){
                            card.setCardBackgroundColor(Color.argb(255, finalI, 255-finalI, 0));
                        }
                    });
                    // next will pause the thread for some time
                    try {
                        sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void CountAvailableSpae(MyCard card) {
        int x1,x2,y1,y2;
        x1 = card.getLeft()-(card.getWidth()/2);
        x2 = card.getRight()+(card.getWidth()/2);
        y1 = card.getTop()-(card.getHeight()/2);
        y2 = card.getBottom()+(card.getHeight()/2);

        for(int i = x1;i<x2;i++){
            X.remove(String.valueOf(i));
        }
        for(int i=y1;i<y2;i++){
            Y.remove(String.valueOf(i));
        }
    }

    private void TraceBack(View view) {
        ArrayList<MyCard.Trajectory> list = cardViews.get(view.getId()).route;
        Collections.reverse(list);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        for(MyCard.Trajectory t:list){
            layoutParams.leftMargin = t.x;
            layoutParams.topMargin = t.y;
            view.setLayoutParams(layoutParams);
        }
        cardViews.get(view.getId()).route.clear();
    }

    public void undo(View view) {
        if (cardViews.size()>0){
            MyCard m = cardViews.get(id-1);
            cardViews.remove(m);
            root.removeView(m);
            root.invalidate();
            id--;
        }
        else{
            Toast.makeText(getApplicationContext(), "No more views are there", Toast.LENGTH_SHORT).show();
        }


    }

    public int[] getRandomCoordinates(){
        int sizeX = X.size()-1;
        int ranX = (int)(Math.random()*(sizeX+1)+0);

        int sizeY = Y.size()-1;
        int ranY= (int)(Math.random()*(sizeY+1)+0);

        int[] i = new int[2];
        i[0]=ranX;
        i[1]=ranY;
        return i;
    }

    public boolean isColliding(View v1){
        Rect r1 = new Rect(v1.getLeft(),v1.getTop(),v1.getRight(),v1.getBottom());
        ArrayList<MyCard> list = new ArrayList<>();
        for(MyCard c:cardViews){
            if (c.getId()!=v1.getId()){
                list.add(c);
            }
        }
        for(MyCard c:list){
            Rect r2 = new Rect(c.getLeft(),c.getTop(),c.getRight(),c.getBottom());
            if (r1.intersect(r2)){
                return true;
            }
        }
        return false;
    }
}

    /*public void RepelOthers(View myView, View OtherView){
        int centerX1,centerY1,centerX2,centerY2;
        centerX1 = (int) (myView.getX()+(myView.getWidth()/2));
        centerY1 = (int) (myView.getY()+(myView.getHeight()/2));
        centerX2 = (int) (OtherView.getX()+(OtherView.getWidth()/2));
        centerY2 = (int) (OtherView.getY()+(OtherView.getHeight()/2));
        while(diff(centerX1,centerX2)<(myView.getWidth()/2)+(OtherView.getWidth()/2)){
            if(centerX2>centerX1){
                SetCenter(OtherView,++centerX2,1);
            }
            else{
                SetCenter(OtherView,--centerX2,1);
            }
        }
        while(diff(centerY1,centerY2)<(myView.getHeight()/2)+(OtherView.getHeight()/2)){
            if(centerY2>centerY1){
                SetCenter(OtherView,++centerY2,0);
            }
            else{
                SetCenter(OtherView,--centerY2,0);
            }
        }
    }*/