package com.yame.participantContent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yame.yameandroid.R;

import java.util.ArrayList;

public class ParticipantListAdapter extends BaseAdapter {

    private ArrayList<ParticipantVO> list = new ArrayList<ParticipantVO>();

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public ParticipantVO getItem(int index)
    {
        return list.get(index);
    }

    @Override
    public long getItemId(int index)
    {
        return 0;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.participant_list, parent, false);
        }
        ImageView IV_participantImg = (ImageView) convertView.findViewById(R.id.participantImg) ;
        TextView TV_participantNum = (TextView) convertView.findViewById(R.id.participantNum);
        TextView TV_participantName = (TextView) convertView.findViewById(R.id.participantName);

        ParticipantVO participantVO = getItem(index);

        if("prof0".equals(participantVO.getIcon()))
            IV_participantImg.setImageResource(R.drawable.prof0);
        else if("prof1".equals(participantVO.getIcon()))
            IV_participantImg.setImageResource(R.drawable.prof1);
        else if("prof2".equals(participantVO.getIcon()))
            IV_participantImg.setImageResource(R.drawable.prof2);
        else if("prof3".equals(participantVO.getIcon()))
            IV_participantImg.setImageResource(R.drawable.prof3);

        TV_participantNum.setText(participantVO.getNum());
        TV_participantName.setText(participantVO.getName());

        return convertView;
    }



    public void addItem(String num, String name, String img) {

        ParticipantVO participantVO = new ParticipantVO();

        participantVO.setNum(num);
        participantVO.setName(name);
        participantVO.setIcon(img);

        list.add(participantVO);
    }
}
