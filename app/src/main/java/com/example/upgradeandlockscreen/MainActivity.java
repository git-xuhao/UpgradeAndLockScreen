package com.example.upgradeandlockscreen;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mToLeftRotate,mToRightRotate;
    private Animation operatingAnimLeft,operatingAnimRight;
    private View mView;
    private WindowManager wm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = LayoutInflater.from(this).inflate(R.layout.activity_main,null);
        mToLeftRotate = (ImageView) mView.findViewById(R.id.to_left_rotate);
        mToRightRotate = (ImageView) mView.findViewById(R.id.to_right_rotate);

        //逆时针旋转
        operatingAnimLeft = AnimationUtils.loadAnimation(this, R.anim.rotate_left);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnimLeft.setInterpolator(lin);
        if(operatingAnimLeft!=null){
            mToLeftRotate.startAnimation(operatingAnimLeft);
        }

        //顺时针旋转
        operatingAnimRight = AnimationUtils.loadAnimation(this, R.anim.rotate_right);
        LinearInterpolator lin1 = new LinearInterpolator();
        operatingAnimRight.setInterpolator(lin1);
        if(operatingAnimRight!=null){
            mToRightRotate.startAnimation(operatingAnimRight);
        }


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        wm=(WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        wmParams.format = PixelFormat.TRANSPARENT;
        wmParams.flags=WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;
        wmParams.width=  dm.widthPixels;
        wmParams.height=dm.heightPixels;
        wm.addView(mView, wmParams);
        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);

        /**
         * 防止为了退不出来，我设置一个点击事件移除view
         */
        mToLeftRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mView != null) {
                    wm.removeView(mView);
                    MainActivity.this.finish();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        if(operatingAnimLeft!=null && operatingAnimRight!=null){
            mToLeftRotate.clearAnimation();
            mToRightRotate.clearAnimation();
        }
        super.onDestroy();

    }
}
