package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.model.eventbus.SelectTeamEvent;
import com.zhixing.employlib.model.eventbus.UpdateEmployeeEvent;
import com.zhixing.employlib.model.eventbus.UpdateTeamSelectEvent;
import com.zhixing.employlib.model.gardenplot.UpLoadBean;
import com.zhixing.employlib.viewmodel.activity.UpTeamViewModel;
import com.zhixing.netlib.base.BaseResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UpdateTeamDataActivity extends BaseActvity {
    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.tv_updata_team_select_type)
    TextView tvUpdataTeamSelectType;
    @BindView(R2.id.cl_update_select_type)
    ConstraintLayout clUpdateSelectType;
    @BindView(R2.id.tv_updata_team_select_people)
    TextView tvUpdataTeamSelectPeople;
    @BindView(R2.id.cl_update_select_people)
    ConstraintLayout clUpdateSelectPeople;//1.2.3.4
    @BindView(R2.id.editText)
    EditText editText;//发布公告 5
    @BindView(R2.id.cl_update_team)
    ConstraintLayout clUpdateTeam;//发布公告 5
    @BindView(R2.id.tv_updata_team_select_work)
    EditText tvUpdataTeamSelectWork;
    @BindView(R2.id.cl_update_select_work)
    ConstraintLayout clUpdateSelectWork;//1.2.3.4
    @BindView(R2.id.tv_updata_team_select_address)
    EditText tvUpdataTeamSelectAddress;
    @BindView(R2.id.cl_update_select_address)
    ConstraintLayout clUpdateSelectAddress;//1.2.3.4
    @BindView(R2.id.tv_updata_team_select_score)
    EditText tvUpdataTeamSelectScore;
    @BindView(R2.id.cl_update_select_score)
    ConstraintLayout clUpdateSelectScore;//1.2.3
    @BindView(R2.id.tv_updata_team_select_rank)
    EditText tvUpdataTeamSelectRank;
    @BindView(R2.id.cl_update_select_rank)
    ConstraintLayout clUpdateSelectRank;//1.2.3
    @BindView(R2.id.tv_updata_team_select_team_name)
    EditText tvUpdataTeamSelectTeamName;
    @BindView(R2.id.cl_update_select_team_name)
    ConstraintLayout clUpdateSelectTeamName;//5
    @BindView(R2.id.tv_updata_team_select_team_go_time)
    TextView tvUpdataTeamSelectTeamGoTime;
    @BindView(R2.id.cl_update_select_team_go_time)
    ConstraintLayout clUpdateSelectTeamGoTime;//4
    @BindView(R2.id.iv_out_carme_photo)
    ImageView ivOutCarmePhoto;
    @BindView(R2.id.cardView_iv_out_ad)
    CardView cardViewIvOutAd;
    @BindView(R2.id.iv_out_carme_delete)
    ImageView ivOutCarmeDelete;
    @BindView(R2.id.iv_out_carme)
    ImageView ivOutCarme;
    @BindView(R2.id.ed_out_ad_desc)
    EditText edOutAdDesc;
    private Unbinder bind;
    private String LinkedTabledId;
    private String LinkedTable;
    private List<LocalMedia> selectList=new ArrayList<>();
    private String Url;
    private UpTeamViewModel upTeamViewModel;
    private String type;
    private String UseCode;
    private String UserName;
    private String commonTime1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_team_data;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        bind = ButterKnife.bind(this);
        setStatus(-1);
        EventBus.getDefault().register(this);
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("更新园地");
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -1); //日减1
        Date lastDay = ca.getTime(); //结果
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sf.format(lastDay);
         commonTime1 = TimeUtil.getCommonTime1(format);

         upTeamViewModel = ViewModelProviders.of(this).get(UpTeamViewModel.class);
    }


    @OnClick({R2.id.iv_work_add_work, R2.id.tv_work_send, R2.id.cl_update_select_type,
            R2.id.cl_update_select_people, R2.id.cardView_iv_out_ad,
            R2.id.iv_out_carme_delete, R2.id.iv_out_carme})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();

        } else if (i == R.id.tv_work_send) {


            if (TextUtils.isEmpty(type)){
                Toasty.INSTANCE.showToast(this,"请选择类型");

            }else if ("5".equals(type)){

                if (TextUtils.isEmpty(editText.getText().toString().trim())){
                    Toasty.INSTANCE.showToast(this,"主题不能为空");

                }else if (TextUtils.isEmpty(edOutAdDesc.getText().toString().trim())){
                    Toasty.INSTANCE.showToast(this,"描述不能为空");
                }else{
                    showDialog("");
                    upTeamViewModel.UpLoadThree(LinkedTabledId,editText.getText().toString().trim(),edOutAdDesc.getText().toString().trim()).observe(this
                            , new Observer<BaseResponse>() {
                                @Override
                                public void onChanged(@Nullable BaseResponse baseResponse) {
                                    if (baseResponse!=null) {
                                        if (baseResponse.getStatus().equals("success")) {
                                            Toasty.INSTANCE.showToast(UpdateTeamDataActivity.this, "更新成功");
                                            dismissDialog();
                                            EventBus.getDefault().post(new UpdateEmployeeEvent("5"));
                                            AppManager.getAppManager().finishActivity();

                                        } else {
                                            dismissDialog();
                                            Toasty.INSTANCE.showToast(UpdateTeamDataActivity.this, "更新失败");
                                        }
                                    }
                                }
                            });
                    dismissDialog();
                }



                } else if ("4".equals(type)){

                if ("请选择".equals(tvUpdataTeamSelectPeople.getText().toString())){
                    Toasty.INSTANCE.showToast(this,"员工姓名不能为空");

                }else if (TextUtils.isEmpty(edOutAdDesc.getText().toString().trim())){
                    Toasty.INSTANCE.showToast(this,"描述不能为空");
                }else{

                    showDialog("");
                    upTeamViewModel.UpLoadTwo(LinkedTabledId,UserName,UseCode,edOutAdDesc.getText().toString().trim()).observe(this, new Observer<BaseResponse>() {
                        @Override
                        public void onChanged(@Nullable BaseResponse baseResponse) {
                            if (baseResponse!=null){
                                if (baseResponse.getStatus().equals("success")){
                                    Toasty.INSTANCE.showToast(UpdateTeamDataActivity.this,"更新成功");
                                    dismissDialog();
                                    EventBus.getDefault().post(new UpdateEmployeeEvent("4"));
                                    AppManager.getAppManager().finishActivity();

                                }else{
                                    dismissDialog();
                                    Toasty.INSTANCE.showToast(UpdateTeamDataActivity.this,"更新失败");
                                }
                            }
                        }
                    });

                    dismissDialog();

                }


            }else {
            if ("请选择".equals(tvUpdataTeamSelectPeople.getText().toString())){
                Toasty.INSTANCE.showToast(this,"员工姓名不能为空");

            }else if (TextUtils.isEmpty(edOutAdDesc.getText().toString().trim())){
                Toasty.INSTANCE.showToast(this,"描述不能为空");
            }else{
                showDialog("");
                upTeamViewModel.UpLoadOne(LinkedTabledId,type,UserName,UseCode,commonTime1,edOutAdDesc.getText().toString().trim()).observe(this, new Observer<BaseResponse>() {
                    @Override
                    public void onChanged(@Nullable BaseResponse baseResponse) {
                        if (baseResponse!=null){
                            if (baseResponse.getStatus().equals("success")){
                                Toasty.INSTANCE.showToast(UpdateTeamDataActivity.this,"更新成功");
                                dismissDialog();
                                EventBus.getDefault().post(new UpdateEmployeeEvent("3"));
                                AppManager.getAppManager().finishActivity();

                            }else{
                                dismissDialog();
                                Toasty.INSTANCE.showToast(UpdateTeamDataActivity.this,"更新失败");
                            }
                        }
                    }
                });
                dismissDialog();
            }
            }





        } else if (i == R.id.cl_update_select_type) {

            startActivity(SelectTeamTypeActivity.class);

        } else if (i == R.id.cl_update_select_people) {
            startActivity(UpdateSelectPeopleActivity.class);

        } else if (i == R.id.cardView_iv_out_ad) {

        } else if (i == R.id.iv_out_carme_delete) {
        } else if (i == R.id.iv_out_carme) {
            if (tvUpdataTeamSelectType.getText().toString().equals("请选择")){
                Toasty.INSTANCE.showToast(this,"请选择类型!!");

            }else{
                setImage();
            }




        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(SelectTeamEvent teamEvent) {
        if ("1".contains(teamEvent.event)) {


            type=teamEvent.event;
            //月度
            clUpdateSelectWork.setVisibility(View.GONE);
            clUpdateSelectPeople.setVisibility(View.VISIBLE);
            clUpdateTeam.setVisibility(View.GONE);
            clUpdateSelectAddress.setVisibility(View.GONE);
            clUpdateSelectScore.setVisibility(View.GONE);
            clUpdateSelectRank.setVisibility(View.GONE);
            clUpdateSelectTeamName.setVisibility(View.GONE);
            clUpdateSelectTeamGoTime.setVisibility(View.GONE);

             LinkedTabledId=UUID.randomUUID().toString();
             LinkedTable="ems_excellentemployee";
             tvUpdataTeamSelectType.setText("月度优秀员工");



        } else if ("2".contains(teamEvent.event)) {
            type=teamEvent.event;
            //季度

            clUpdateSelectWork.setVisibility(View.GONE);
            clUpdateSelectPeople.setVisibility(View.VISIBLE);
            clUpdateTeam.setVisibility(View.GONE);
            clUpdateSelectAddress.setVisibility(View.GONE);
            clUpdateSelectScore.setVisibility(View.GONE);
            clUpdateSelectRank.setVisibility(View.GONE);
            clUpdateSelectTeamName.setVisibility(View.GONE);
            clUpdateSelectTeamGoTime.setVisibility(View.GONE);
            tvUpdataTeamSelectType.setText("季度优秀员工");
            LinkedTabledId=UUID.randomUUID().toString();
            LinkedTable="ems_excellentemployee";
        } else if ("3".contains(teamEvent.event)) {
            type=teamEvent.event;
            //年度
            clUpdateSelectWork.setVisibility(View.GONE);
            clUpdateSelectPeople.setVisibility(View.VISIBLE);
            clUpdateTeam.setVisibility(View.GONE);
            clUpdateSelectAddress.setVisibility(View.GONE);
            clUpdateSelectScore.setVisibility(View.GONE);
            clUpdateSelectRank.setVisibility(View.GONE);
            clUpdateSelectTeamName.setVisibility(View.GONE);
            clUpdateSelectTeamGoTime.setVisibility(View.GONE);
            LinkedTabledId=UUID.randomUUID().toString();
            tvUpdataTeamSelectType.setText("年度优秀员工");
            LinkedTable="ems_excellentemployee";
        } else if ("4".contains(teamEvent.event)) {
            type=teamEvent.event;
            //新员工
            //月度
            clUpdateSelectWork.setVisibility(View.GONE);
            clUpdateSelectPeople.setVisibility(View.VISIBLE);
            clUpdateTeam.setVisibility(View.GONE);
            clUpdateSelectAddress.setVisibility(View.GONE);
            clUpdateSelectScore.setVisibility(View.GONE);
            clUpdateSelectRank.setVisibility(View.GONE);
            clUpdateSelectTeamName.setVisibility(View.GONE);
            clUpdateSelectTeamGoTime.setVisibility(View.GONE);
            LinkedTabledId=UUID.randomUUID().toString();
            tvUpdataTeamSelectType.setText("新员工");
            LinkedTable="ems_newemployee";
        } else if ("5".contains(teamEvent.event)) {
            type=teamEvent.event;
            //班组风采

            clUpdateSelectWork.setVisibility(View.GONE);
            clUpdateSelectPeople.setVisibility(View.GONE);
            clUpdateTeam.setVisibility(View.VISIBLE);
            clUpdateSelectAddress.setVisibility(View.GONE);
            clUpdateSelectScore.setVisibility(View.GONE);
            clUpdateSelectRank.setVisibility(View.GONE);
            clUpdateSelectTeamName.setVisibility(View.GONE);
            clUpdateSelectTeamGoTime.setVisibility(View.GONE);
            LinkedTabledId=UUID.randomUUID().toString();
            tvUpdataTeamSelectType.setText("班组风采");
            LinkedTable="ems_teamdemeanor";
        }


    }


    private void setImage() {
        PictureSelector.create(UpdateTeamDataActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_QQ_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false

                .isCamera(true)// 是否显示拍照按钮 true or false
//                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(true)// 是否显示gif图片 true or false
//                .compressSavePath(Environment.getExternalStorageState()+"/Images")//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(500)// 小于100kb的图片不压缩
//                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                .videoQuality(0)// 视频录制质量 0 or 1 int
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia bean: selectList) {
                        String compressPath = bean.getCompressPath();
                        Url=compressPath;
                         showDialog("");
                        //上传文件
                        upTeamViewModel.UpLoadImage(LinkedTabledId,LinkedTable,compressPath).observe(UpdateTeamDataActivity.this, new Observer<BaseResponse<UpLoadBean>>() {
                            @Override
                            public void onChanged(@Nullable BaseResponse<UpLoadBean> baseResponse) {

                                dismissDialog();

                            }
                        });


                        cardViewIvOutAd.setVisibility(View.VISIBLE);
//                          ImageLoader.loadListeren(this,compressPath,ivMyInfoDetailHead);
                        MyImageLoader.loads(this,compressPath,ivOutCarmePhoto);
                    }
                    break;
            }
        }

    }







    @Subscribe(threadMode = ThreadMode.MAIN)
    public void select(UpdateTeamSelectEvent event){
         UserName =event.UseName;
         UseCode=event.UseCode;
        tvUpdataTeamSelectPeople.setText(event.UseName);

    }

}
