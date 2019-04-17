package com.example.mhzhaog.wakeupandunlock;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewCall;
    private Button button3;
    private Button button4;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams param;
    private FloatView mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewCall = findViewById(R.id.imageViewCall);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        imageViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"呼叫成功，请锁屏",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //点亮屏幕
                        wakeUpAndUnlock(MainActivity.this);
//启动跳转Activity即可唤醒app
                        Intent intent = new Intent(MainActivity.this,ReciveCallActivity.class);
                        startActivity(intent);
                    }
                },5000);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLayout == null){
                    showView();
                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLayout != null){
                    //移除悬浮窗
                    mWindowManager.removeView(mLayout);
                    mLayout = null;
                }
            }
        });
    }

    private void showView(){
        mLayout=new FloatView(getApplicationContext());
        mLayout.setBackgroundResource(R.drawable.ic_call);
        mLayout.setClickable(true);
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"点击悬浮窗",Toast.LENGTH_SHORT).show();
            }
        });

        //获取WindowManager
        mWindowManager=(WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //设置LayoutParams(全局变量）相关参数
        param = ((MyApplication)getApplication()).getMywmParams();

        param.type=WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;     // 系统提示类型,重要
        param.format=1;
        param.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不能抢占聚焦点
        param.flags = param.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        param.flags = param.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排版不受限制

        param.alpha = 1.0f;

        param.gravity= Gravity.RIGHT| Gravity.TOP;   //调整悬浮窗口至右上角
        //以屏幕左上角为原点，设置x、y初始值
        param.x=0;
        param.y=100;

        //设置悬浮窗口长宽数据
        param.width=140;
        param.height=140;

        //显示myFloatView图像
        mWindowManager.addView(mLayout, param);

    }

    public static void wakeUpAndUnlock(Context context){
        //屏锁管理器
        KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
    }
}
