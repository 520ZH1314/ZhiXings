package com.zhixing.employlib.adapter;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.GradingEventEntity;

import java.util.List;

public class GradingEventAdapt extends BaseQuickAdapter<GradingEventEntity,BaseViewHolder> {


    private int mSelectedPos=-1;//保存当前选中的position 重点！
    private List<GradingEventEntity> mList;//数据源
    public GradingEventAdapt(int layoutResId, @Nullable List<GradingEventEntity> data) {
        super(layoutResId, data);

        this.mList=data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, GradingEventEntity item) {
            helper.setText(R.id.tv_grading_event_name,item.name);
         final CheckBox checkBox =(CheckBox) helper.itemView.findViewById(R.id.cb_grading_event);
        checkBox.setChecked(mSelectedPos== helper.getLayoutPosition());
        checkBox.setEnabled(false);
        checkBox.setClickable(false);
        ConstraintLayout constraintLayout =(ConstraintLayout) helper.itemView.findViewById(R.id.cl_grading_event_list);

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectedPos!= helper.getLayoutPosition()){//当前选中的position和上次选中不是同一个position 执行
                    checkBox.setChecked(true);
                    if(mSelectedPos!=-1){//判断是否有效 -1是初始值 即无效 第二个参数是Object 随便传个int 这里只是起个标志位
                        notifyItemChanged(mSelectedPos,0);
                    }
                    mSelectedPos= helper.getLayoutPosition();;
                }

            }
        });



    }


    //提供给外部Activity来获取当前checkBox选中的item，这样就不用去遍历了 重点！
    public GradingEventEntity getSelectedPosData(){
        return mList.get(mSelectedPos);
    }
}
