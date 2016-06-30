package com.xyh.robotchat.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.xyh.robotchat.R;
import com.xyh.robotchat.data.MyBaseAdapter;
import com.xyh.robotchat.httputils.GetResultFromHttp;
import com.xyh.robotchat.httputils.JsonParse;
import com.xyh.robotchat.model.UserBean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Bind(R.id.listView_id)
    ListView mListView;
    @Bind(R.id.et_input_id)
    EditText et_input;
    @Bind(R.id.btn_send_id)
    Button btn_send;
    private ArrayList<UserBean> datas = new ArrayList<>();
    private MyBaseAdapter baseAdapter;
    private static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String API_KEY = "080390332ce0704a54cd5a3aaf70f443";
    private String me_content;

    // 返回  可以访问的url    参数  msg  输入的内容
    public static String getPath(String msg) {
        try {
            String path = URL + "?key=" + API_KEY + "&info=" + URLEncoder.encode(msg, "utf-8");
            return path;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        baseAdapter = new MyBaseAdapter(MainActivity.this, mListView);
        setClick();
    }

    private void setClick() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                me_content = et_input.getText().toString().trim();
                Log.d(TAG, "onClick: me_content = " + me_content);
                if (isNetworkConnected()) {
                    if (me_content.equals("")) {
                        Toast.makeText(MainActivity.this, "不能发送空消息!", Toast.LENGTH_SHORT).show();
                    } else {
                        UserBean userBean = new UserBean(me_content, 0, getTime());
                        datas.add(userBean);
                        //更新数据
                        baseAdapter.onDataChange(datas);
                        mListView.setSelection(datas.size() - 1);
                        et_input.setText("");
                        new MyAsyncTask().execute(getPath(me_content));
                    }
                } else
                    Toast.makeText(MainActivity.this, "请连接网络,否则不能正常聊天!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //获得时间 格式为 6-30 9:22
    private String getTime() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        Date date = new Date(currentTime);
        String time = sdf.format(date);
        return time;
    }


    class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            //获得json数据
            String result = GetResultFromHttp.getString(params[0]);
            //解析Json数据, 获得机器人的消息
            String content = JsonParse.jsonParse(result);
            return content;
        }

        @Override
        protected void onPostExecute(String content) {
            super.onPostExecute(content);
            UserBean userBean = new UserBean(content, 1);
            datas.add(userBean);
            baseAdapter.onDataChange(datas);
            mListView.setAdapter(baseAdapter);
            mListView.setSelection(datas.size() - 1);
        }
    }

    //判断是否有网络,有则返回true
    public boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }
}
