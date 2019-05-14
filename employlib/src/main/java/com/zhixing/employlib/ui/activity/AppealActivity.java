package com.zhixing.employlib.ui.activity;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.ChangeTime;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;

import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.eventbus.MessageEvent;
import com.zhixing.employlib.model.gardenplot.UpLoadBean;
import com.zhixing.employlib.utils.AppUtils;
import com.zhixing.employlib.utils.PermissionsUtil;
import com.zhixing.employlib.view.CustomGridView;
import com.zhixing.employlib.viewmodel.activity.AppealPersonViewModel;
import com.zhixing.employlib.viewmodel.activity.UpTeamViewModel;
import com.zhixing.netlib.base.BaseResponse;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 我要申诉
 */
public class AppealActivity extends BaseActvity implements PermissionsUtil.IPermissionsCallback {
    @BindView(R2.id.tetle_text)
    TextView tvTite;//标题文本标签

    @BindView(R2.id.btn_finish)
    Button btnFinish;
    @BindView(R2.id.tv_dae)
    TextView tv_date;
    @BindView(R2.id.gv_pic_img)
    CustomGridView gv_pic_img;

    @BindView(R2.id.et_tel)
    EditText et_tel;
    private Button btnDialogSave;
    private Button btnFinishDialog;
    private AlertDialog dialog;
    private List<String> imageList = new ArrayList<>();
    private LeaveImageAdapter leaveImageAdapter;
    private PermissionsUtil permissionsUtil;
    private String imagePth;
    @BindView(R2.id.tetle_tv_ok)
    TextView tetle_tv_ok;
    @BindView(R2.id.tv_later)
    TextView tv_later;
    private String tel;
    private Unbinder bind;
    private AppealPersonViewModel appealPersonViewModel;
    private UpTeamViewModel upTeamViewModel;
    @Override
    public int getLayoutId() {
        return R.layout.activity_appeal;
    }

    @Override
    public void process(Message msg) {

    }
    private SharedUtils sharedUtils;
    @Override
    public void initLayout() {

        sharedUtils = new SharedUtils(PerformanceApi.FLIESNAME);
         bind = ButterKnife.bind(this);
        setStatus(-1);
        appealPersonViewModel = ViewModelProviders.of(this).get(AppealPersonViewModel.class);
        upTeamViewModel = ViewModelProviders.of(this).get(UpTeamViewModel.class);
        initView();
        initData();
    }




    private void initData() {
        tetle_tv_ok.setText("申诉记录");
        tvTite.setText("我要申诉");
        tetle_tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(AppealActivity.this,AppealListActivity.class);
                    intent.putExtra("CreateTime",tv_date.getText());
                    startActivity(intent);


//                startActivity(AppealListActivity.class);
            }
        });

    }
    private String fileid ;
    private void initView() {

        tetle_tv_ok.setVisibility(View.VISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        leaveImageAdapter = new LeaveImageAdapter();
        gv_pic_img.setAdapter(leaveImageAdapter);
        btnFinish.setOnClickListener((v)->{
            if(tv_date.getText().length()==0){
                Toasty.INSTANCE.showToast(AppealActivity.this,"请选择时间");
                return;
            }
            showDialog("申请请求中");
            Map map = new HashMap();
            map.put("AppealId",fileid);
            map.put("KeyId",KeyId);
            map.put("KeyName",KeyName);
            map.put("KeyDate",time);
            map.put("TeamId",sharedUtils.getStringValue(PerformanceApi.TEAMID));
            map.put("ApplyUserId",SharedPreferencesTool.getMStool(AppealActivity.this).getUserId());
            map.put("ApplyUserName",SharedPreferencesTool.getMStool(AppealActivity.this).getUserName());
            map.put("PhoneNumber",et_tel.getText().toString());
            map.put("Opinion",et_input_content.getText().toString());
            appealPersonViewModel.sendAppealPerson(map).observe(this, new Observer<BaseResponse>() {
                @Override
                public void onChanged(@Nullable BaseResponse baseResponse) {
                    dismissDialog();
                    Toasty.INSTANCE.showToast(AppealActivity.this,"申诉已提交");
                    AppManager.getAppManager().finishActivity();
                }
            });
        });
    }

    @OnClick(R2.id.iv_date_layout)
    public void imageSelect(View v) {
        ChangeTime changeTime = new ChangeTime(this, "", 2);
        changeTime.setPastCanendar(1);
        changeTime.setSelect(new SelectTime() {
            @Override
            public void select(String time, long timestp) {
                long now = TimeUtil.parseTime_(TimeUtil.getTimeO(System.currentTimeMillis()));
               /* if(timestp>=now){
                    Toasty.INSTANCE.showToast(AppealActivity.this,"请选择历史时间");
                    return;
                }*/
                // P.c(TimeUtil.getTime(timestp)+"==="+TimeUtil.getTimeO(System.currentTimeMillis()));
                tv_date.setText(TimeUtil.getTimeCh(timestp));

            }
        });
        changeTime.showSheet();
    }
    @OnClick(R2.id.select_appeal)
    public void appealSelect(){
             Intent intent = new Intent(AppealActivity.this,SelectAppealPersonActivity.class);
            intent.putExtra("time",tv_date.getText().toString());
            startActivityForResult(intent,100);
    }



    private void initClickListener() {
        btnDialogSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnFinishDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tel = et_tel.getText().toString().trim();
                if(!AppUtils.isMobile(tel)){
                    Toast.makeText(AppealActivity.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
//                startActivity(new Intent(AppealActivity.this,AppealRecordActivity.class));
                dialog.dismiss();
            }
        });

    }
    private String uuid ;
    String KeyId,KeyName,time;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PictureConfig.CHOOSE_REQUEST&&resultCode == RESULT_OK) {

                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    List<String> pathList = new ArrayList<>();
                    for (int i = 0; i < selectList.size(); i++) {
                        pathList.add(selectList.get(i).getCompressPath());
                    }
                    imagePth = pathList.get(0);
                     uuid = UUID.randomUUID().toString();
//                    SharedPreferencesTool.getMStool(this).setString("imgUrl", pathList.get(0));
                    imageList.addAll(pathList);
                     upTeamViewModel.UpLoadImage(uuid,"ems_appeal",imagePth).observe(AppealActivity.this, new Observer<BaseResponse<UpLoadBean>>() {
                         @Override
                         public void onChanged(@Nullable BaseResponse<UpLoadBean> upLoadBeanBaseResponse) {
                             if(upLoadBeanBaseResponse!=null){
                                 if(upLoadBeanBaseResponse.getRows().size()==1){
                                     fileid = upLoadBeanBaseResponse.getRows().get(0).FileID;
                                 }
                             }
                         }
                     });
                    leaveImageAdapter.notifyDataSetChanged();

        }else if(requestCode==100&&resultCode==RESULT_OK){
            //选择个人关键事件
                //在此进行数据请求
            KeyName = data.getStringExtra("KeyName");
            KeyId = data.getStringExtra("KeyId");
            time = TimeUtil.getTime(TimeUtil.parseTimeC(data.getStringExtra("time")));
            tv_later.setText(KeyName);

        }
    }
    @BindView(R2.id.et_input_content)
    EditText et_input_content;


    @Override
    public void onPermissionsGranted(int requestCode, String... permission) {
        //获取权限成功
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //显示选择头像弹窗
                leaveImageAdapter.takePhoto();
            }
        });
    }

    @Override
    public void onPermissionsDenied(int requestCode, String... permission) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //权限获取失败
                Toast.makeText(AppealActivity.this, "权限获取失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 检查设备图片adapter
     */
    class LeaveImageAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return (imageList.size() + 1);
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {

            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.item_leave, null);
            }
            //照片
            final ImageView iv_image = (ImageView) view.findViewById(R.id.iv_image);
            //删除按钮
            ImageView btn_delete = (ImageView) view.findViewById(R.id.btn_delete);
            //如果是最后一个item,就是拍照按钮
            if (position == imageList.size()) {
                //设置拍照按钮图片
                iv_image.setImageResource(R.mipmap.iv_icon_select);
                //删除按钮隐藏
                btn_delete.setVisibility(View.INVISIBLE);
            } else {
                //获取图片对象
                String imageUrl = imageList.get(position);
                //删除按钮显示
                btn_delete.setVisibility(View.VISIBLE);
                //加载本地图片
                iv_image.setImageBitmap(AppUtils.getSmallBitmap(imageUrl));
            }
            //设置删除按钮点击事件
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position != imageList.size()) {
                        imageList.remove(position);
                        //刷新列表
                        leaveImageAdapter.notifyDataSetChanged();
                    }
                }
            });

            //设置图片的点击事件
            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //如果是选择图片或拍照
                    if (position == imageList.size()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            //开启log
                            permissionsUtil = PermissionsUtil
                                    .with(AppealActivity.this)
                                    .requestCode(3)
                                    .isDebug(true)//开启log
                                    .permissions(PermissionsUtil.Permission.Storage.READ_EXTERNAL_STORAGE,
                                            PermissionsUtil.Permission.Storage.WRITE_EXTERNAL_STORAGE,
                                            PermissionsUtil.Permission.Camera.CAMERA)
                                    .request();

                        } else {
//                        获取照片
                            takePhoto();
                        }

                    } else {

                    }
                }

            });

            return view;
        }

        /**
         * 包装缩略图 ImageView 集合
         *
         * @return
         */
        @NonNull
        private List<ImageView> wrapOriginImageViewList() {
            List<ImageView> originImgList = new ArrayList<>();
            for (int i = 0; i < imageList.size(); i++) {
                ImageView thumImg = (ImageView) (gv_pic_img.getChildAt(i)).findViewById(R.id.iv_image);
                originImgList.add(thumImg);
            }
            return originImgList;
        }

        private void takePhoto() {
            //简单调用
            // 进入相册 以下是例子：用不到的api可以不写
            PictureSelector.create(AppealActivity.this)
                    .openGallery(PictureMimeType.ofImage()).theme(R.style.picture_QQ_style)//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                    .maxSelectNum(4)// 最大图片选择数量 int
                    .minSelectNum(1)// 最小选择数量 int
                    .imageSpanCount(4)// 每行显示个数 int
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                    .previewImage(true)// 是否可预览图片 true or false
                    .previewVideo(true)// 是否可预览视频 true or false
                    .enablePreviewAudio(true) // 是否可播放音频 true or false
                    .isCamera(true)// 是否显示拍照按钮 true or false
                    .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                    .compress(true)// 是否压缩 true or false// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                    .isGif(true)// 是否显示gif图片 true or false
                    //压缩图片保存地址
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                    .openClickSound(true)// 是否开启点击声音 true or false
                    .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                    .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    .videoQuality(0)// 视频录制质量 0 or 1 int
                    .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                    .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                    .isDragFrame(false)// 是否可拖动裁剪框(固定)
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

//            MultiImageSelector.create().start(PictureListActivity.this,REQUEST_IMAGE);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
