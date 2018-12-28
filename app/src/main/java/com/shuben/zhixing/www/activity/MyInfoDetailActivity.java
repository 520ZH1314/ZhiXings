package com.shuben.zhixing.www.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.widget.CircularImage;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.common.ImageLoader;
import com.shuben.zhixing.www.data.UserData;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyInfoDetailActivity extends BaseActvity {
    private static final int REQUEST_CODE_CHOOSE = 23;//定义请求码常量
    @BindView(R.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.iv_my_info_detail_head)
    CircularImage ivMyInfoDetailHead;
    @BindView(R.id.tv_my_detail_name)
    TextView tvMyDetailName;
    @BindView(R.id.tv_my_info_detail_job)
    TextView tvMyInfoDetailJob;
    @BindView(R.id.tv_my_info_detail_depart_name)
    TextView tvMyInfoDetailDepartName;
    @BindView(R.id.tv_my_info_detail_phone_number)
    TextView tvMyInfoDetailPhoneNumber;
    @BindView(R.id.tv_my_info_detail_teliphone)
    TextView tvMyInfoDetailTeliphone;
    @BindView(R.id.tv_my_info_detail_email)
    TextView tvMyInfoDetailEmail;
    @BindView(R.id.tv_my_info_detail_wechat)
    TextView tvMyInfoDetailWechat;
    @BindView(R.id.tv_my_info_detail_borthday)
    TextView tvMyInfoDetailBorthday;
    @BindView(R.id.tv_my_info_detail_sub_departments)
    TextView tvMyInfoDetailSubDepartments;
    @BindView(R.id.tv_my_info_detail_sub_superior)
    TextView tvMyInfoDetailSubSuperior;
    @BindView(R.id.tv_my_info_detail_employee_number)
    TextView tvMyInfoDetailEmployeeNumber;
    private ACache aCache;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_info_detail;
    }


    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        setStatus(-1);
        aCache = ACache.get(this);
        tvWorkTitle.setText("个人资料");
        tvWorkSend.setVisibility(View.VISIBLE);
        tvWorkSend.setText("编辑");
        ivWorkAddWork.setImageResource(R.mipmap.back);
        initData();
    }

    private void initData() {
        String ip = SharedPreferencesTool.getMStool(this).getIp();
        String app_myInfo = aCache.getAsString("App_MyInfo");
        UserData userData = GsonUtil.getGson().fromJson(app_myInfo, UserData.class);
        ImageLoader.load(ip + userData.getPhotoURL(), ivMyInfoDetailHead, R.mipmap.person_icon);
        tvMyDetailName.setText(userData.getUserName());
        tvMyInfoDetailJob.setText(userData.getHeadShip());
        tvMyInfoDetailDepartName.setText(userData.getDeptName());
        tvMyInfoDetailPhoneNumber.setText(userData.getPhoneNumber());
        tvMyInfoDetailEmail.setText(userData.getEmail());

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.iv_work_add_work, R.id.tv_work_send, R.id.iv_my_info_detail_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_work_add_work:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.tv_work_send:
                break;
            case R.id.iv_my_info_detail_head:
                  setImage();
                break;
        }
    }

    private void setImage() {
        PictureSelector.create(MyInfoDetailActivity.this)
                .openGallery(PictureMimeType.ofAll())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_QQ_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(2)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
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
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
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
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia bean: selectList) {
                        String compressPath = bean.getCompressPath();
//                          ImageLoader.loadListeren(this,compressPath,ivMyInfoDetailHead);
                        MyImageLoader.loadListeren(this,compressPath,ivMyInfoDetailHead,false,"");
                    }
                    break;
            }
            }

    }

}
