package com.xyh.robotchat.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xyh.robotchat.R;
import com.xyh.robotchat.model.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 向阳湖 on 2016/6/29.
 */
public class MyBaseAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<UserBean> datas;
    private ListView mListView;
    public MyBaseAdapter(Context mContext, ListView mListView) {
        inflater = LayoutInflater.from(mContext);
        this.mListView = mListView;
    }

    public void onDataChange(ArrayList<UserBean> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        int type = datas.get(position).getType();
        //代表自己说的话
        if (type == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        UserBean userBean = datas.get(position);
        //如果是自己发出的消息就发送到界面上,否则机器人消息显示到界面上
        if (getItemViewType(position) == 0) {
            view = inflater.inflate(R.layout.item_me, null);
            TextView me = (TextView) view.findViewById(R.id.item_content_id);
            TextView time = (TextView) view.findViewById(R.id.show_time_id);
            me.setText(userBean.getContent());
            time.setText(userBean.getTime());
        } else {
            view = inflater.inflate(R.layout.item_robot, null);
            TextView robot = (TextView) view.findViewById(R.id.item_robotContent_id);
            robot.setText(userBean.getContent());

        }
        return view;
    }
}
