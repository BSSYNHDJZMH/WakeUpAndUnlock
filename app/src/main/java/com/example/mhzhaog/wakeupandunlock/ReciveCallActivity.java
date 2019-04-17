package com.example.mhzhaog.wakeupandunlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class ReciveCallActivity extends AppCompatActivity {

    private ImageView imageViewPickUp;
    private ImageView imageViewHangUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recive_call);

        //使该Activity在锁屏界面上面显示，别忘了给视频通话的Activity也加上
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.activity_recive_call);

        imageViewPickUp = findViewById(R.id.imageViewPickUp);
        imageViewHangUp = findViewById(R.id.imageViewHangUp);

        imageViewPickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReciveCallActivity.this,"接通电话",Toast.LENGTH_SHORT).show();
            }
        });

        imageViewHangUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReciveCallActivity.this,"挂断电话",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
