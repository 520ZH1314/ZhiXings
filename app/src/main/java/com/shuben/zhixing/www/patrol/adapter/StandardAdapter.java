package com.shuben.zhixing.www.patrol.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.photopicker.PhotoPreview;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.bean.StandardInfo;
import com.shuben.zhixing.www.patrol.twoclass.FindLightActivity;
import com.shuben.zhixing.www.patrol.twoclass.FindQuestionActivity;
import com.shuben.zhixing.www.util.SysUtils;
import com.shuben.zhixing.www.view.ItemView;
import com.shuben.zhixing.www.view.MyGridView;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Geyan on 2018/5/29.
 */

public class StandardAdapter extends BaseAdapter implements  ItemView.OnCommentListener {
    private Activity context;
    private List<StandardInfo> list;
    private LayoutInflater inflater;
    private StandardInfo info;
    private LoadingDailog dialog;
    private int wh;
    private ViewHoloder holoder;
    private TestGridViewAdapter nearByInfoImgsAdapter;

    public StandardAdapter(Activity context, List<StandardInfo> data) {
        this.context = context;
        this.list = data;
        this.wh = (SysUtils.getScreenWidth(context) - SysUtils.Dp2Px(context, 99)) / 3;
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(context)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog = loadBuilder.create();

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holoder = new ViewHoloder();
            convertView = inflater.inflate(R.layout.patrol_standard_item, null);
            holoder.tx_index = (TextView) convertView.findViewById(R.id.tx_index);
            holoder.item01 = (TextView) convertView.findViewById(R.id.tx_itemName);

            holoder.bnt01 = (TextView) convertView.findViewById(R.id.tx_myQuestion);
            holoder.bnt02 = (TextView) convertView.findViewById(R.id.tx_mylight);
            holoder.gridView=(MyGridView) convertView.findViewById(R.id.standard_gridview);
            holoder.lay_grid=(LinearLayout) convertView.findViewById(R.id.lay_grid);
            holoder.bnt01.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            holoder.bnt02.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            convertView.setTag(holoder);
        } else {
            holoder = (ViewHoloder) convertView.getTag();
        }
        info = list.get(position);
        holoder.bnt01.setText("已发现问题" + "(" + info.getItem04() + ")");
        holoder.bnt02.setText("已发现亮点" + "(" + info.getItem05() + ")");
        holoder.bnt01.setOnClickListener(new StandardAdapter.lvButtonListener(position));
        holoder.bnt02.setOnClickListener(new StandardAdapter.lvButtonListener(position));


        if (convertView instanceof ItemView) {
            ((ItemView) convertView).setData(info,context);
            ((ItemView) convertView).setPosition(position);
            ((ItemView) convertView).setCommentListener(this);
        }

        holoder.tx_index.setText(position + 1 + "");
        holoder.item01.setText(info.getItem03());

        //是否含有图片
        if (info.getItem07() != null && !info.getItem07().equals("")) {
            holoder.lay_grid.setVisibility(View.VISIBLE);
            initInfoImages( holoder.gridView, info.getItem07());
        } else {
            holoder.lay_grid.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public void onComment(int position) {
        //showCommentView(position);
    }

    class ViewHoloder {
        TextView tx_index, item01, bnt01, bnt02;
        MyGridView gridView;
        LinearLayout lay_grid;
    }

    class lvButtonListener implements View.OnClickListener {
        private int position;

        lvButtonListener(int pos) {
            this.position = pos;
        }

        @Override
        public void onClick(View v) {

            if ((v.getId()) == ( holoder.bnt01.getId())) {
                Intent intent=new Intent();
                intent.setClass(context, FindQuestionActivity.class);
                intent.putExtra("RecordId",list.get(position).getItem01());
                intent.putExtra("StandardItemId",list.get(position).getItem02());
                context.startActivity(intent);
            }
            if ((v.getId()) == ( holoder.bnt02.getId())) {
                Intent intent=new Intent();
                intent.setClass(context, FindLightActivity.class);
                intent.putExtra("RecordId",list.get(position).getItem01());
                intent.putExtra("StandardItemId",list.get(position).getItem02());
                context.startActivity(intent);
            }
        }
    }
    /**
     * 加载信息中包含的图片内容
     *
     * @param
     */
    public void initInfoImages(MyGridView gv_images, final String imgspath) {
        if (imgspath != null && !imgspath.equals("")) {
            String[] imgs = imgspath.split("#");
            final List<String> mlist = new ArrayList<String>();
            for (int i = 0; i < imgs.length; i++) {
                mlist.add(imgs[i]);
            }
            holoder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PhotoPreview.init().setPhotoPaths(mlist).setCurrentPos(position).setPreviewOnly(true).startPreview(context, null);

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
                    w = 2 * wh + SysUtils.Dp2Px(context, 2);
                    gv_images.setNumColumns(2);
                    break;
                case 3:
                case 5:
                case 6:
                    w = wh * 3 + SysUtils.Dp2Px(context, 2) * 2;
                    gv_images.setNumColumns(3);
                    break;
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, RelativeLayout.LayoutParams.WRAP_CONTENT);
            gv_images.setLayoutParams(lp);
            nearByInfoImgsAdapter = new TestGridViewAdapter(context, mlist);
            gv_images.setAdapter(nearByInfoImgsAdapter);
           /* gv_images.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Toast.makeText(context, "点击了第"+(arg2+1)+"张图片", Toast.LENGTH_LONG).show();
                }
            });*/
        }

    }




    }


