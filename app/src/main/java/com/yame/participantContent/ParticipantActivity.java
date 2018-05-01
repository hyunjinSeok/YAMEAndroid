package com.yame.participantContent;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.yame.common.XmlParse;
import com.yame.yameandroid.R;

import java.util.ArrayList;

public class ParticipantActivity extends AppCompatActivity {

    private ListView listView;
    private MediaPlayer mediaPlayer;
    private TelephonyManager telephonyManager;
    private PhoneStateListener phoneStateListener;
    private XmlParse xmlParse;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Drawable alpha = (findViewById(R.id.participant_base)).getBackground();
        alpha.setAlpha(180);

        ImageView lion_bga = (ImageView) findViewById(R.id.background_lion);

        GlideDrawableImageViewTarget lion_gif = new GlideDrawableImageViewTarget(lion_bga);
        Glide.with(this).load(R.raw.dance_lion).into(lion_gif);


        listView = (ListView)findViewById(R.id.participant_list);

        try {
            listView.setAdapter(putData());

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


        mediaPlayer = MediaPlayer.create(this, R.raw.participant_bgm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber)
            {
                switch(state){
                    case TelephonyManager.CALL_STATE_IDLE://통화중이 아닐 때
                        mediaPlayer.start();
                        break;
                    case TelephonyManager.CALL_STATE_RINGING://전화벨 울릴 때
                        mediaPlayer.pause();
                    case TelephonyManager.CALL_STATE_OFFHOOK://통화 중
                        break;
                    default :
                        break;
                }
            }
        };

        telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        /*Button button = (Button)findViewById(R.id.btn_back);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "이전 액티비티로 돌아갑니다.", Toast.LENGTH_LONG).show();

                finish();
            }
        });*/
    }

    private ParticipantListAdapter putData() throws Exception
    {
        ParticipantListAdapter participantListAdapter = new ParticipantListAdapter();
        try {
            ArrayList<ParticipantVO> list;
            xmlParse = new XmlParse();
            System.out.println("####parseStart");
            xmlParse.start();

            try{
                xmlParse.join();
            } catch (Exception te){
                Toast.makeText(getApplicationContext(), te.toString(), Toast.LENGTH_LONG).show();
            }

            list = xmlParse.exportArr();

            System.out.println("############### 리스트 사이즈 : " + list.size());
            for(int i = 0; i < list.size(); i++)
            {
                //participantListAdapter.addItem(list.get(i).getNum(), list.get(i).getName(), ContextCompat.getDrawable(getApplicationContext(), getResources().getIdentifier("pro"+ String.valueOf(i), "drawable", getPackageName())));
                participantListAdapter.addItem(list.get(i).getNum(), list.get(i).getName(), "prof" + String.valueOf(i % 4));
                System.out.println("번호 : " + list.get(i).getNum() + " 이름 : " + list.get(i).getName() + " 이미지 : " + "prof" + String.valueOf(i));
            }


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            System.out.println(e.toString());
            /*
            participantListAdapter.addItem("0", "석현진", ContextCompat.getDrawable(getApplicationContext(), R.drawable.prof0));
            participantListAdapter.addItem("1", "송승기", ContextCompat.getDrawable(getApplicationContext(), R.drawable.prof1));
            participantListAdapter.addItem("2", "성효진", ContextCompat.getDrawable(getApplicationContext(), R.drawable.prof2));
            participantListAdapter.addItem("3", "김수연", ContextCompat.getDrawable(getApplicationContext(), R.drawable.prof3));

            */

            /*
            participantListAdapter.addItem("0", "석현진", "prof0");
            participantListAdapter.addItem("1", "송승기", "prof1");
            participantListAdapter.addItem("2", "성효진", "prof2");
            participantListAdapter.addItem("3", "김수연", "prof3");
            */
        }

       /* new Thread() {
            public void run() {
                XmlParse xmlParse = new XmlParse();
                Bundle bundle = new Bundle();
                Handler handler = new Handler();
                try {
                    ArrayList<ParticipantVO> list;
                    xmlParse.parseStart();
                    list = xmlParse.exportArr();
                    bundle.putSerializable("list", list);

                    Message msg = handler.obtainMessage();
                    msg.setData(bundle);
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }


            }
        }.start();

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                Bundle bun = msg.getData();
                String naverHtml = bun.getString("HTML_DATA");
                edtHtml.setText(naverHtml);
            }
        };



        try {
            xmlParse.parseStart();
            list = xmlParse.exportArr();

            for(int i = 0; i < list.size(); i++)
                participantListAdapter.addItem(list.get(i).getNum(), list.get(i).getName(), ContextCompat.getDrawable(getApplicationContext(), getResources().getIdentifier("pro"+ String.valueOf(i), "drawable", getPackageName())));

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            participantListAdapter.addItem("0", "석현진", ContextCompat.getDrawable(getApplicationContext(), R.drawable.prof0));
            participantListAdapter.addItem("1", "송승기", ContextCompat.getDrawable(getApplicationContext(), R.drawable.prof1));
            participantListAdapter.addItem("2", "성효진", ContextCompat.getDrawable(getApplicationContext(), R.drawable.prof2));
            participantListAdapter.addItem("3", "김수연", ContextCompat.getDrawable(getApplicationContext(), R.drawable.prof3));

            participantListAdapter.addItem("0", "석현진", "prof0");
            participantListAdapter.addItem("1", "송승기", "prof1");
            participantListAdapter.addItem("2", "성효진", "prof2");
            participantListAdapter.addItem("3", "김수연", "prof3");

        }*/


        /*for (int i = 0; i < 4; i++)
            participantListAdapter.addItem("name_" + i, "contents_" + i);*/
            //participantListAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon), "name_" + i, "contents_" + i);

        return participantListAdapter;
    }




    @Override
    protected void onUserLeaveHint()
    {
        mediaPlayer.pause();
        super.onUserLeaveHint();
    }

    public void onPause(){
        mediaPlayer.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        mediaPlayer.stop();
        if(telephonyManager != null)
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        super.onDestroy();
    }

    @Override
    protected void onResume()
    {
        mediaPlayer.start();
        super.onResume();
    }

    @Override
    public void onBackPressed()
    {
        mediaPlayer.stop();
        super.onBackPressed();
    }
}
