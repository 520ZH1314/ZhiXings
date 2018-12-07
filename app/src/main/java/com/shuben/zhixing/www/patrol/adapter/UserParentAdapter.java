package com.shuben.zhixing.www.patrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.bean.UserListInfo;
import com.shuben.zhixing.www.util.ScrollListview;

import java.util.List;

/**
 * Created by Geyan on 2018/5/17.
 */

public class UserParentAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<UserListInfo> data;
    private UserListInfo userListInfo;

    public UserParentAdapter(Context mContext, List<UserListInfo> data) {
        this.mContext = mContext;
        this.data=data;
    }

    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            holder = new UserParentAdapter.ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.patrol_user_parent_item, null);
            holder.item01=(TextView) convertView.findViewById(R.id.tx_org);
            holder.listView=(ListView) convertView.findViewById(R.id.listview_user);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);

        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        userListInfo=data.get(position);
        holder.item01.setText(userListInfo.getItem02());
        UserAdapter adapter =new UserAdapter(mContext,userListInfo.getItem03());
        holder.listView.setAdapter(adapter);
        new ScrollListview(holder.listView);
        return convertView;
    }
    @Override
    //在此适配器中所代表的数据集中的条目数
    public int getCount() {
        return data.size();
    }

    @Override
    //获取数据集中与指定索引对应的数据项
    public Object getItem(int position) {
        return null;
    }

    @Override
    //获取在列表中与指定索引对应的行id
    public long getItemId(int position) {
        return position;
    }



    class ViewHolder
    {
        public TextView item01;
        private ListView listView;
    }




}



