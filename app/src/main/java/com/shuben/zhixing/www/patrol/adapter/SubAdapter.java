package com.shuben.zhixing.www.patrol.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.photopicker.PhotoPreview;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.bean.SubInfo;
import com.shuben.zhixing.www.util.NumberChangeToChinese;
import com.shuben.zhixing.www.util.SysUtils;
import com.shuben.zhixing.www.view.MyGridView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Geyan on 2018/6/15.
 */

public class SubAdapter extends BaseAdapter {
    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<SubInfo> data;
    private List<String> mList = new ArrayList<String>();

    private int wh;
    private TestGridViewAdapter nearByInfoImgsAdapter;

    public SubAdapter(Activity mContext, List<SubInfo> data) {
        this.mContext = mContext;
        this.data = data;
        this.wh = (SysUtils.getScreenWidth(mContext) - SysUtils.Dp2Px(mContext, 99)) / 3;

    }

    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.patrol_item_review_item, null);

            holder.item01 = (TextView) convertView.findViewById(R.id.tx_item01);//
            holder.item02 = (TextView) convertView.findViewById(R.id.tx_item02);
            holder.item03 = (TextView) convertView.findViewById(R.id.tx_item03);
            holder.item04 = (TextView) convertView.findViewById(R.id.tx_item04);

            holder.gridView = (MyGridView) convertView.findViewById(R.id.gv_images);
            holder.lay01 = (LinearLayout) convertView.findViewById(R.id.lay01);//图片布局
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SubInfo info = data.get(position);
        String item01 = null, item02 = null, item03 = null, item04 = null;
        if (info != null) {
            item01 = info.getItem01();
            item02 = info.getItem02();
            item03 = info.getItem03();
            item04 = info.getItem04();
        }

        holder.item01.setText(info.getItem01());
        holder.item02.setText(info.getItem02());
        holder.item03.setText(info.getItem03());
       NumberChangeToChinese nun=new NumberChangeToChinese();
        holder.item04.setText("亮点"+nun.numberToChinese(position+1));
        //是否含有图片
        if (item04 != null && !item04.equals("")) {
            holder.gridView.setVisibility(View.VISIBLE);
            initInfoImages(holder.gridView, item04);
        } else {
            holder.gridView.setVisibility(View.GONE);
        }

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


    class ViewHolder {
        public TextView  item01, item02, item03,item04;
        private MyGridView gridView;
        private LinearLayout lay01;
    }


    /**
     * 加载信息中包含的图片内容
     *
     * @param
     */
    public void initInfoImages(MyGridView gv_images, final String imgspath) {
        Log.e("*****imgspath****",imgspath);
        if (imgspath != null && !imgspath.equals("")) {
            String[] imgs = imgspath.split("#");
            final List<String> mlist = new ArrayList<String>();
            for (int i = 0; i < imgs.length; i++) {
                mlist.add(imgs[i]);
            }
            holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PhotoPreview.init().setPhotoPaths(mlist).setCurrentPos(position).setPreviewOnly(true).startPreview(mContext, null);

                }

            });

            int w = 0;
            switch (mlist.size()) {
                case 1:
                    w = wh;
                    gv_images.setNumColumns(1);
                    break;
                case 2:
                case 4:
                    w = 2 * wh + SysUtils.Dp2Px(mContext, 2);
                    gv_images.setNumColumns(2);
                    break;
                case 3:
                case 5:
                case 6:
                    w = wh * 3 + SysUtils.Dp2Px(mContext, 2) * 2;
                    gv_images.setNumColumns(3);
                    break;
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, RelativeLayout.LayoutParams.WRAP_CONTENT);
            gv_images.setLayoutParams(lp);
            nearByInfoImgsAdapter = new TestGridViewAdapter(mContext, mlist);
            gv_images.setAdapter(nearByInfoImgsAdapter);
           /* gv_images.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Toast.makeText(mContext, "点击了第"+(arg2+1)+"张图片", Toast.LENGTH_LONG).show();
                }
            });*/
        }

    }
}

