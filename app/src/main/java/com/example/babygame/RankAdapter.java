package com.example.babygame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RankAdapter extends BaseAdapter {
    private ArrayList<Rank> listItem=new ArrayList<Rank>();

    public RankAdapter(ArrayList<Rank> listItem) {
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context=parent.getContext();

        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item_rank,parent,false);
        }

        TextView name=(TextView)convertView.findViewById(R.id.tv_rank_name);
        TextView score=(TextView)convertView.findViewById(R.id.tv_rank_score);

        Rank rank=listItem.get(position);

        name.setText(rank.name);
        score.setText(rank.score);


        return convertView;
    }
}
