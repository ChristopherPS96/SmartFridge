
package com.example.christopher.smartfridge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.imageView).setOnTouchListener(new OnSwipeTouchListener(this));
    }

    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, new GestureListener());
        }

        public void onSwipeLeft() {
            Button start = findViewById(R.id.startbutton);
            start.setVisibility(View.INVISIBLE);

            FloatingActionButton exit = findViewById(R.id.exitFab);
            exit.setVisibility(View.VISIBLE);

            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.kuehlzu);
        }

        public void onSwipeRight() {
            Button start = findViewById(R.id.startbutton);
            start.setVisibility(View.VISIBLE);

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
        Intent intent = new Intent(this, BestandActivity.class);
        startActivity(intent);
    }

    public void onExitClick(View view){
        finish();
    }
}
