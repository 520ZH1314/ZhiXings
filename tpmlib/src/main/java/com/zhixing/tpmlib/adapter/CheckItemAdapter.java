package com.zhixing.tpmlib.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.bean.CheckItem;
import com.zhixing.tpmlib.bean.CheckItemBean;

import java.util.List;

public class CheckItemAdapter extends BaseQuickAdapter<CheckItem,BaseViewHolder> {

    public CheckItemAdapter(List<CheckItem> data) {
        super(R.layout.item_check_item_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckItem item) {
        helper.setText(R.id.tv_serial,item.getClassId());
    }
}
