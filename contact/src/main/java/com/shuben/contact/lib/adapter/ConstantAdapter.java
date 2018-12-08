package com.shuben.contact.lib.adapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.shuben.contact.lib.common.ConstantS;
import com.shuben.contact.lib.event.ConstantDataEvent;
import com.shuben.contact.lib.event.ConstantIsCheck;
import com.shuben.contact.lib.event.ConstantOneEvent;
import com.shuben.contact.lib.event.TypeBean;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.widget.CharAvatarView;
import com.base.zhixing.www.widget.CircularImage;
import com.shuben.contact.lib.R;
import com.shuben.contact.lib.bean.Type;
import com.shuben.contact.lib.common.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cc.solart.turbo.BaseTurboAdapter;
import cc.solart.turbo.BaseViewHolder;

public class ConstantAdapter extends BaseTurboAdapter<Type, BaseViewHolder> {

    private boolean isSingle;
    private List<Type> datas = new ArrayList<>();
    private String modType=null;
    private Context context;
    public ConstantAdapter(Context context) {
        super(context);

    }

    private boolean isEdit;

    public void modEdit(boolean isEdit) {
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }

    public void isSingle(boolean isSingle) {
        this.isSingle = isSingle;
        notifyDataSetChanged();
    }

    private String BASE;

    public ConstantAdapter(Context context, List<Type> data) {
        super(context, data);
        this.context=context;
        BASE = SharedPreferencesTool.getMStool(context).getIp();

        EventBus.getDefault().register(this);
    }

    public void updata(List<Type> data, int type) {
        this.mData = data;
        this.type = type;
        notifyDataSetChanged();
    }

    @Override
    protected int getDefItemViewType(int position) {
        Type city = getItem(position);
        return city.getType();
    }

    private int type;

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new PersonHolder(inflateItemView(R.layout.constant_item, parent));
        else
            return new PinnedHolder(inflateItemView(R.layout.constant_item_pinned_header, parent));
    }

    /**
     * 拨打电话
     *
     * @param phoneNum
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        mContext.startActivity(intent);
    }


    @Override
    protected void convert(final BaseViewHolder holder, final Type item) {
        if (holder instanceof PersonHolder) {
            ((PersonHolder) holder).name.setText(item.getName());
            ((PersonHolder) holder).zhiwei.setText(item.getZhiwei());

            ((PersonHolder) holder).edit.setChecked(item.isCheck());

            if (item.getPhone() == null || item.getPhone().length() < 11) {
                ((PersonHolder) holder).call.setEnabled(false);

            } else {
                ImageLoader.local(R.mipmap.dianhua, ((PersonHolder) holder).call);
                ((PersonHolder) holder).call.setEnabled(true);
                ((PersonHolder) holder).call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callPhone(item.getPhone());
                    }
                });
            }

            ((PersonHolder) holder).select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (isEdit) {

                        if (mData.get(holder.getLayoutPosition() - getHeaderViewsCount()).isCheck()) {
                            mData.get(holder.getLayoutPosition() - getHeaderViewsCount()).setCheck(false);
                        } else {
                             if (isSingle){
                                 for (int i = 0; i <mData.size() ; i++) {
                                     if (mData.get(i).isCheck()){
                                         mData.get(i).setCheck(false);
                                         mData.get(holder.getLayoutPosition() - getHeaderViewsCount()).setCheck(true);
                                     }else {
                                         mData.get(holder.getLayoutPosition() - getHeaderViewsCount()).setCheck(true);

                                     }
                                 }

                             }else{
                                 mData.get(holder.getLayoutPosition() - getHeaderViewsCount()).setCheck(true);
                             }
                        }


                        notifyDataSetChanged();
                        //查询确定的数据
                        List<TypeBean> dataNum = new ArrayList<>();
                        for (int i = 0; i < mData.size(); i++) {
                            if (mData.get(i).isCheck()) {
                                dataNum.add(new TypeBean(mData.get(i).getOid(),
                                        mData.get(i).getId(),
                                        mData.get(i).getName()));
                            }
                        }
                        EventBus.getDefault().post(new ConstantIsCheck(dataNum.size()));

                    } else {

                    }
                }
            });

            if (item.getImg() == null || item.getImg().length() == 0) {
                ((PersonHolder) holder).txt_head.setVisibility(View.VISIBLE);
                ((PersonHolder) holder).imageView_head.setVisibility(View.GONE);
                ((PersonHolder) holder).txt_head.setText(item.getName());

            } else {
                ((PersonHolder) holder).imageView_head.setVisibility(View.VISIBLE);
                ((PersonHolder) holder).txt_head.setVisibility(View.GONE);
                if (type == 0) {
                    ImageLoader.load(BASE + item.getImg(), ((PersonHolder) holder).imageView_head, R.mipmap.person_icon);
                } else {
                    ImageLoader.load(BASE + item.getImg(), ((PersonHolder) holder).imageView_head, R.mipmap.bumen);
                }
            }


            if (isEdit) {
                ((PersonHolder) holder).edit.setVisibility(View.VISIBLE);
            } else {
                ((PersonHolder) holder).edit.setVisibility(View.GONE);
            }
        } else {
            String letter = item.getPys().substring(0, 1);
            ((PinnedHolder) holder).city_tip.setText(letter);
        }
    }

    public int getLetterPosition(String letter) {
        for (int i = 0; i < getData().size(); i++) {
            if (getData().get(i).getType() == 1 && getData().get(i).getPys().equals(letter)) {
                return i;
            }
        }
        return -1;
    }

    public void modgetType(String type) {
        this.modType=type;
    }

    class PersonHolder extends BaseViewHolder {

        CircularImage imageView_head;
        CharAvatarView txt_head;
        TextView name, zhiwei;
        CheckBox edit;
        LinearLayout select;
        ImageView call;

        public PersonHolder(View view) {
            super(view);
            txt_head = findViewById(R.id.txt_head);
            imageView_head = findViewById(R.id.imageView_head);
            name = findViewById(R.id.name);
            edit = findViewById(R.id.edit);
            select = findViewById(R.id.select);
            zhiwei = findViewById(R.id.zhiwei);
            call = findViewById(R.id.call);
        }
    }


    class PinnedHolder extends BaseViewHolder {

        TextView city_tip;

        public PinnedHolder(View view) {
            super(view);
            city_tip = findViewById(R.id.city_tip);
        }
    }


    //收到发送数据的事件
    @Subscribe( threadMode = ThreadMode.MAIN)
    public void Event(ConstantOneEvent event) {
        if (event.getEdit()) {
            List<TypeBean> data = new ArrayList<>();
            for (int i = 0; i < mData.size(); i++) {
                if (mData.get(i).isCheck()) {
                    data.add(new TypeBean(mData.get(i).getOid(),
                            mData.get(i).getId(),
                            mData.get(i).getName()));
                }
            }
            String menusJson = GsonUtil.getGson().toJson(data);
              if ("1".equals(modType)){
                  SharedPreferencesTool.getMStool(mContext).setString("type",modType);
                  SharedPreferencesTool.getMStool(mContext).setString(ConstantS.CONSTANTDATA,menusJson);

              }else if("2".equals(modType)){
                  SharedPreferencesTool.getMStool(mContext).setString("type",modType);
                  SharedPreferencesTool.getMStool(mContext).setString(ConstantS.CONSTANTDATA,menusJson);
              }else if("3".equals(modType)){
                  SharedPreferencesTool.getMStool(mContext).setString("type",modType);
                  SharedPreferencesTool.getMStool(mContext).setString(ConstantS.CONSTANTDATA,menusJson);

              }

        }
    }

}
