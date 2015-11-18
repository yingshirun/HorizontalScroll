package com.shirun.wechatsendgroupchat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shirun.wechatsendgroupchat.R;
import com.shirun.wechatsendgroupchat.bean.UserBean;
import com.shirun.wechatsendgroupchat.view.MyHorizontalScroll;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ==========================================
 * <p/>
 * 作    者 : ying
 * <p/>
 * 创建时间 ： 2015/11/17.
 * <p/>
 * 用   途 :
 * <p/>
 * <p/>
 * ==========================================
 */
public class MyAdapter extends BaseAdapter {

    private List<UserBean> mList;
    private Context mContext;
    private MyHorizontalScroll mScroll;

    private Set<String> set;

    public MyAdapter(List<UserBean> mList, Context mContext,MyHorizontalScroll mScroll) {
        this.mList = mList;
        this.mScroll = mScroll;
        this.mContext = mContext;
        set = new HashSet<String>();
    }

    public Set<String> getSet(){
        return set;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_layout, null);
            viewHolder.cb = (ImageView) convertView.findViewById(R.id.item_selected_cb);
            viewHolder.head = (ImageView) convertView.findViewById(R.id.item_head_img);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.item_nick_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final UserBean userBean = mList.get(position);
        Picasso.with(mContext).load(userBean.headpath).placeholder(R.mipmap.ic_launcher).error(R.mipmap.sel_no_grey).into(viewHolder.head);
        viewHolder.tv.setText(userBean.nick);

        viewHolder.cb.setImageResource(R.mipmap.sel_no_grey);
        if (set.contains(userBean.uid)) {
            viewHolder.cb.setImageResource(R.mipmap.sel_l_yes);
        }
        viewHolder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) v;
                if (set.contains(userBean.uid)) {
                    set.remove(userBean.uid);
                    img.setImageResource(R.mipmap.sel_no_grey);
                    mScroll.remove(userBean);
                } else {
                    img.setImageResource(R.mipmap.sel_l_yes);
                    set.add(userBean.uid);
                    mScroll.add(userBean);
                }
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        ImageView head;
        TextView tv;
        ImageView cb;
    }
}
