package com.shirun.wechatsendgroupchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.shirun.wechatsendgroupchat.adapter.MyAdapter;
import com.shirun.wechatsendgroupchat.bean.UserBean;
import com.shirun.wechatsendgroupchat.view.MyHorizontalScroll;
import com.shirun.wechatsendgroupchat.view.MyHorizontalScroll.ChangeListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChangeListener{


    String[] imgStr = {
            "http://img4.duitang.com/uploads/item/201510/07/20151007124603_4Tj2K.jpeg",
            "http://img4.imgtn.bdimg.com/it/u=1519979105,1747027397&fm=21&gp=0.jpg",
            "http://img.name2012.com/uploads/allimg/2015-06/30-023131_451.jpg",
            "http://img4.duitang.com/uploads/item/201509/26/20150926122827_3Xhum.jpeg",
            "http://img2.3lian.com/img2007/19/33/005.jpg",
            "http://zx.kaitao.cn/UserFiles/Image/beijingtupian6.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/78310a55b319ebc4090e788e8126cffc1e1716b6.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1070902365,2619384777&fm=116&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3580210867,3098509580&fm=116&gp=0.jpg"
    };


    private ListView mListView;
    private MyAdapter mAdapter;
    private List<UserBean> mList;
    private MyHorizontalScroll mHrozrontalScroll;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        mList = new ArrayList<UserBean>();
        for (int i = 0; i < 30; i++) {
            UserBean userBean = new UserBean();
            userBean.uid = i + "";
            userBean.nick = i + "呵呵";
            userBean.headpath = imgStr[i % 9];
            mList.add(userBean);
        }
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.mListView);
        mHrozrontalScroll = (MyHorizontalScroll) findViewById(R.id.myhorizontal);

        mAdapter = new MyAdapter(mList, this,mHrozrontalScroll);
        mListView.setAdapter(mAdapter);
        mHrozrontalScroll.setChangeListener(this);
        btn = (Button) findViewById(R.id.new_chat_group_btn);
    }

    /**
     * 确定按钮
     *
     * @param view
     */
    public void btn_send(View view) {
        if (mHrozrontalScroll.getUsers().length == 0) {
            Toast.makeText(this, "请添加成员", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ok" + mHrozrontalScroll.getUsers().length + " 人被选中", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void changeAction(int count, int action, String uid) {
        if(action == ChangeListener.POP){
            mAdapter.getSet().remove(uid);
            mAdapter.notifyDataSetChanged();
        }
        if(count!=0){
            btn.setText("确认("+count+")");
        }else{
            btn.setText("确认");
        }
    }
}
