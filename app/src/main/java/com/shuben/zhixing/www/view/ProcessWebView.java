package com.shuben.zhixing.www.view;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import com.tencent.smtt.sdk.WebView;

public class ProcessWebView extends WebView {
    private ProgressView progressView;

    public ProcessWebView(Context context) {
        super(context);
    }
    private void init(Context context){
        progressView = new ProgressView(context);
        progressView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
        progressView.setColor(Color.BLUE);
        progressView.setProgress(10);
        //把进度条加到Webview中
        addView(progressView);

    }
}
