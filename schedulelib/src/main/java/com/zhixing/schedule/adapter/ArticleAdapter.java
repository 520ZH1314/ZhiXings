package com.zhixing.schedule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.zhixing.schedule.R;
import com.zhixing.schedule.group.GroupRecyclerAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 适配器
 * Created by huanghaibin on 2017/12/4.
 */

public class ArticleAdapter extends GroupRecyclerAdapter<String, Article> {


    private RequestManager mLoader;
    private  LinkedHashMap<String, List<Article>> map ;
    private ArrayList<String> titles;
    public ArticleAdapter(Context context, LinkedHashMap<String, List<Article>> map,ArrayList<String> titles) {
        super(context);
        mLoader = Glide.with(context.getApplicationContext());
        this.map = map;
        this.titles = titles;
        resetGroups(map,titles);
    }

    public void updata(LinkedHashMap<String, List<Article>> map,ArrayList<String> titles){
        this.map = map;
        this.titles = titles;
        resetGroups(map,titles);
    }
    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ArticleViewHolder(mInflater.inflate(R.layout.item_list_article, parent, false));
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, Article item, int position) {
        ArticleViewHolder h = (ArticleViewHolder) holder;
        h.mTextTitle.setText(item.getTitle());
        h.mTextContent.setText(item.getContent());
        mLoader.load(item.getImgUrl())
                .asBitmap()
                .centerCrop()
                .into(h.mImageView);
        h.schedule_layout.setOnClickListener(c ->{
            if(click!=null){
                click.clickItem(item);
            }
        });
    }

    private static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextTitle,
                mTextContent;
        private ImageView mImageView;
        private LinearLayout schedule_layout;

        private ArticleViewHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTextContent = (TextView) itemView.findViewById(R.id.tv_content);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            schedule_layout = itemView.findViewById(R.id.schedule_layout);
        }
    }
    private onItemClick click;
    public void setOnItemClick(onItemClick click){
        this.click = click;
    }
    /**
     * 增加点击按钮事件
     */
    public interface onItemClick {
        void clickItem(Article pos);
    }

    private static Article create(String title, String content, String imgUrl) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setImgUrl(imgUrl);
        return article;
    }


}
