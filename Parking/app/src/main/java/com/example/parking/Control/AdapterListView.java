package com.example.parking.Control;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.parking.R;

import java.util.ArrayList;

public class AdapterListView extends BaseAdapter {
    ArrayList<String> arrayList;

    public AdapterListView(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }
    public class ListViewItem {
        TextView content;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewItem listViewItem = null;
        if(convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.noti_fragment_custome,null);
            listViewItem = new ListViewItem();
            listViewItem.content = convertView.findViewById(R.id.contentNoti);
            convertView.setTag(listViewItem);
        }
        else {
            listViewItem =(ListViewItem) convertView.getTag();
        }
        String txt = arrayList.get(position);
        listViewItem.content.setText("Biển số xe "+txt+" đã gửi quá 24 giờ");
        return convertView;
    }
}
