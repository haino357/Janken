package com.example.janken;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class JankenModelAdapter extends RealmBaseAdapter<JankenModel> {

    private static class ViewHolder {
        TextView date;
        TextView recordTitle;
    }
    public JankenModelAdapter(@Nullable OrderedRealmCollection<JankenModel> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.recordTitle = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        JankenModel jankenModel = adapterData.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String formatDate = sdf.format(jankenModel.getDate());

        viewHolder.date.setText("登録番号 : " + String.valueOf(jankenModel.getId()) + "、日付 : " + formatDate);
        viewHolder.recordTitle.setText("、 戦績 : " + String.valueOf(jankenModel.getGameCount()) + "戦"
                                                    + String.valueOf(jankenModel.getGameWinCount()) + "勝"
                                                    + String.valueOf(jankenModel.getGameLoseCount())  + "敗"
                                                    + String.valueOf(jankenModel.getGameDrawCount()) + "分");

        return convertView;
    }
}
