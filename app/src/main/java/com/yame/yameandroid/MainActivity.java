package com.yame.yameandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.yame.participantContent.ParticipantActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button testBtn = (Button) findViewById(R.id.testBTN);

        testBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ParticipantActivity.class);
                startActivity(intent);
            }
        });
    }
}
