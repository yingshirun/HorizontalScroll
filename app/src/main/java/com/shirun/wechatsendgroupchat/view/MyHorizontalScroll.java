package com.shirun.wechatsendgroupchat.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shirun.wechatsendgroupchat.R;
import com.shirun.wechatsendgroupchat.bean.UserBean;
import com.shirun.wechatsendgroupchat.util.DensityUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class MyHorizontalScroll extends HorizontalScrollView {


    private LinearLayout mLinearLayout;

    private Map<String,UserBean> map;
    private List<UserBean> list;

    /**
     * 默认文字
     */
    private TextView defaultTv;

    private boolean isFirst;

    /**
     * 内容控件的宽高
     */
    private int contentWidthPx;
    private int contentHeightPx;
    public MyHorizontalScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    public MyHorizontalScroll(Context context) {
        super(context);
        init();
    }

    public MyHorizontalScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        if(map == null){
            map = new HashMap<String,UserBean>();
        }

        contentWidthPx = DensityUtil.dip2px(getContext(), 60);
        contentHeightPx = DensityUtil.dip2px(getContext(), 80);
        //设置默认提示文字样式
        defaultTv = new TextView(getContext());
        defaultTv.setText("请添加群聊成员");
        defaultTv.setPadding(DensityUtil.dip2px(getContext(), 3), 0, 0, 0);
        android.view.ViewGroup.LayoutParams vl = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        defaultTv.setLayoutParams(vl);
        defaultTv.setGravity(Gravity.CENTER_VERTICAL);
        defaultTv.setTextColor(Color.parseColor("#ffffff"));

        defaultTv.setTextSize(15);

        list = new ArrayList<UserBean>();
        mLinearLayout = new LinearLayout(getContext());
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
        android.widget.LinearLayout.LayoutParams lp = new android.widget.LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        mLinearLayout.setLayoutParams(lp);
        mLinearLayout.setPadding(DensityUtil.dip2px(getContext(), 11), 0, DensityUtil.dip2px(getContext(), 35), 0);

        //将容器添加到Scroll中
        addView(mLinearLayout);

        mLinearLayout.addView(defaultTv);
        isFirst = true;
    }

    /**
     * 加入一个用户
     * @param uid	id
     * @param headpath	头像地址
     */
    public void add(String uid,String headpath,String nick){
        UserBean ub = new UserBean();
        ub.uid = uid;
        ub.headpath = headpath;
        ub.nick = nick;
        add(ub);
    }

    /**
     * 加入一个用户头像
     * @param ub
     */
    public void add(final UserBean ub){
        if(isFirst){
            //第一次添加的时候讲提示文字取出
            mLinearLayout.removeView(defaultTv);
            isFirst = false;
        }

        list.add(ub);
        map.put(ub.uid, ub);
        if(changeListener!=null){
            changeListener.changeAction(list.size(),ChangeListener.PUSH,ub.uid);
        }
        View v = View.inflate(getContext(), R.layout.scrol_item_layout,null);

        final ImageView rv = (ImageView) v.findViewById(R.id.scrol_item_iv);
        TextView tv = (TextView) v.findViewById(R.id.scrol_item_tv);
        tv.setText(ub.nick);

        //
        LinearLayout.LayoutParams vl = new LinearLayout.LayoutParams(contentWidthPx, contentHeightPx);
        vl.setMargins(DensityUtil.dip2px(getContext(), 5),0,0,0);
        v.setLayoutParams(vl);


        addClickListener(v, ub.uid);

        mLinearLayout.addView(v);

        scrollTo(mLinearLayout.getWidth(), 0);
        //将该控件设置上图片
        setUserAvatar(ub.headpath, rv);
    }

    /**
     * 设置头像
     * @param headpath
     * @param rv
     */
    private void setUserAvatar(String headpath, ImageView rv) {
        Picasso.with(getContext()).load(headpath).into(rv);
    }

    /**
     * 每添加一个头像进去  就给该头像添加一个点击事件
     * 用户点击头像 取消动作
     * @param rv
     * @param id
     */
    private void addClickListener(final View rv,final String id){
        OnClickListener click = new OnClickListener() {

            @Override
            public void onClick(View v) {
                remove(id);
            }
        };
        rv.setOnClickListener(click);
    }

    /**
     * 移除一个用户头像
     * @param uid	用户的uid
     */
    public void remove(String uid){
        UserBean ub = map.remove(uid);
        int in = list.indexOf(ub);
        mLinearLayout.removeViewAt(in);
        list.remove(in);


        if(changeListener!=null){
            changeListener.changeAction(list.size(),ChangeListener.POP,uid);
        }
        if(list.size()==0){
            //当控件中没有内容时  将提示文字加上
            mLinearLayout.addView(defaultTv);
            isFirst = true;
        }
    }

    /**
     * 移除一个用户头像
     * @param ub
     */
    public void remove(UserBean ub){
        remove(ub.uid);
    }

    /**
     * 得到选择的好友id
     * @return
     */
    public String[] getUsers(){
        Set<String> set = map.keySet();
        if(set==null){
            return null;
        }
        String[] str = new String[set.size()];
        set.toArray(str);
        return str;
    }

    /**
     * 得到选择好友的nick
     * @return
     */
    public String[] getUsersNick(){
        int size = list.size();
        String[] str = new String[size];
        for(int i=0; i<size; i++){
            String nick = list.get(i).nick;
            if(nick !=null){
                str[i] = nick;
            }else{
                str[i] = list.get(i).uid;
            }
        }
        return str;
    }



    /**
     * 添加监听  当往该控件中添加头像时 会调用该监听 并把当前的总个数传出去
     * @author Administrator
     *
     */
    public interface ChangeListener{
        /**
         * 添加一个
         */
        public static final int PUSH = 1;
        /**
         * 减少一个
         */
        public static final int POP = 2;
        /**
         * 传出当前控件中的总个数
         * @param count
         */
        public void changeAction(int count,int action,String uid);
    }

    public void setChangeListener(ChangeListener changeListener){
        this.changeListener = changeListener;
    }
    private ChangeListener changeListener;
}
