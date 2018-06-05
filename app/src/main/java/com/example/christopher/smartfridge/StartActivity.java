
package com.example.christopher.smartfridge;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import pl.droidsonroids.gif.GifTextView;
import pl.droidsonroids.gif.GifTextureView;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if(shouldAskPermissions()) {
            askPermissions();
        }
        findViewById(R.id.imageView).setOnTouchListener(new OnSwipeTouchListener(this));


        RotateAnimation rotate = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setInterpolator(new LinearInterpolator());

        TranslateAnimation translate = new TranslateAnimation(0, 0, 500, 0 );

        GifTextView image= findViewById(R.id.logoImage);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(rotate);
        set.addAnimation(translate);
        set.setDuration(1500);

        image.startAnimation(set);
    }

    // bei swipe Kühlschrank auf/zu
    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;
        Button start = findViewById(R.id.startbutton);


        public OnSwipeTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, new GestureListener());
        }

        public void onSwipeLeft() {
            start.setVisibility(View.INVISIBLE);

            FloatingActionButton exit = findViewById(R.id.exitFab);
            exit.setVisibility(View.VISIBLE);

            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.kuehlzu);
        }

        public void onSwipeRight() {
            start.setVisibility(View.VISIBLE);
            RotateAnimation rotate = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setInterpolator(new LinearInterpolator());
            rotate.setDuration(250);
            start.startAnimation(rotate);

            FloatingActionButton exit = findViewById(R.id.exitFab);
            exit.setVisibility(View.INVISIBLE);

            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.kuehlauf);
        }

        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_DISTANCE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float distanceX = e2.getX() - e1.getX();
                float distanceY = e2.getY() - e1.getY();
                if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (distanceX > 0)
                        onSwipeRight();
                    else
                        onSwipeLeft();
                    return true;
                }
                return false;
            }
        }
    }

    public void onStartClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onExitClick(View view){
        finish();
    }

    protected boolean shouldAskPermissions() {      //Quelle: https://stackoverflow.com/questions/8854359/exception-open-failed-eacces-permission-denied-on-android
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {       //Quelle: https://stackoverflow.com/questions/8854359/exception-open-failed-eacces-permission-denied-on-android
        String[] permissions = {
                "android.permission.CAMERA",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }
}
