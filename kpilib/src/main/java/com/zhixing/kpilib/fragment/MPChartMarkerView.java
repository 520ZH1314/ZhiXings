package com.zhixing.kpilib.fragment;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.zhixing.kpilib.R;

public class MPChartMarkerView extends MarkerView {
    private TextView tvContent;
    private  MPPointF mOffset;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.    *    * @param context    * @param layoutResource the layout resource to use for the MarkerView
     */
    public MPChartMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            tvContent.setText(Utils.formatNumber(ce.getHigh(), 0, true));

        } else {
            tvContent.setText(Utils.formatNumber(e.getY(), 0, true));
        }
        super.refreshContent(e, highlight);//必须加上该句话;This sentence must be added.    }


    }


    @Override
    public MPPointF getOffset() {
            if (mOffset == null) {            // center the marker horizontally and vertically
                mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
            }
            return mOffset;
        }
    }

