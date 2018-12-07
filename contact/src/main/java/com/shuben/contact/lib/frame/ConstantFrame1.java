package com.shuben.contact.lib.frame;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.base.zhixing.www.widget.WaveSideBarView;
import com.base.zhixing.www.widget.scrollablelayoutlib.ScrollAbleFragment;
import com.base.zhixing.www.widget.scrollablelayoutlib.ScrollableHelper;
import com.shuben.contact.lib.LetterComparator;
import com.shuben.contact.lib.PinnedHeaderDecoration;
import com.shuben.contact.lib.R;
import com.shuben.contact.lib.adapter.ConstantAdapter;
import com.shuben.contact.lib.bean.Type;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ConstantFrame1 extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer,Serializable {
    ConstantAdapter adapter;
    RecyclerView mRecyclerView;
    WaveSideBarView mSideBarView;
    private Handler handler;
    public static ConstantFrame1 newInstance( ) {
        ConstantFrame1 listFragment = new ConstantFrame1();
        return listFragment;
    }
    public void edit(boolean edit){
        adapter.modEdit(edit);
    }
    public void setHandler( Handler handler ){
        this.handler = handler;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHandler(handler);
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;

    }
    private ArrayList<Type> rbs;
    public void init(ArrayList<Type> rbs){
        Collections.sort(rbs,new LetterComparator());
        this.rbs = rbs;

    }
    public void updata(ArrayList<Type> rbs,int type){

        Collections.sort(rbs,new LetterComparator());
        adapter.updata(rbs,type);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.constant_frame0, container, false);
        mRecyclerView =view. findViewById(R.id.recycler_view);
        mSideBarView = view.findViewById(R.id.side_view);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(1, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
        mRecyclerView.addItemDecoration(decoration);
        adapter = new ConstantAdapter(getActivity(), rbs);
        mRecyclerView.setAdapter(adapter);


        mSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int pos = adapter.getLetterPosition(letter);

                if (pos != -1) {
                    mRecyclerView.scrollToPosition(pos);
                    LinearLayoutManager mLayoutManager =
                            (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(pos, 0);
                }
            }
        });
    }

    @Override
    public View getScrollableView() {
        return mRecyclerView;
    }



}
