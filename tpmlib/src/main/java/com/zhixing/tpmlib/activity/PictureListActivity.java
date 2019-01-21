package com.zhixing.tpmlib.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.google.gson.JsonObject;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.bean.PicEntity;
import com.zhixing.tpmlib.bean.PicturesBean;
import com.zhixing.tpmlib.service.RetrofitInterface;
import com.zhixing.tpmlib.utils.AppUtils;
import com.zhixing.tpmlib.utils.Base64Utils;
import com.zhixing.tpmlib.utils.BitmapUtils;
import com.zhixing.tpmlib.utils.PermissionsUtil;
import com.zhixing.tpmlib.utils.RetrofitUtil;
import com.zhixing.tpmlib.view.CustomGridView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*
 * @Author smart
 * @Date 2018/12/27
 * @Des 照片
 */
public class PictureListActivity extends AppCompatActivity implements PermissionsUtil.IPermissionsCallback{
    private static final int REQUEST_IMAGE =1 ;
    private CustomGridView gv_leave_img;
    private List<String> imageList=new ArrayList<>();
    private LeaveImageAdapter leaveImageAdapter;
    private PermissionsUtil permissionsUtil;
    private SharedUtils sharedUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_list);
        sharedUtils = new SharedUtils("TPM");
        //        初始化控件
        initView();
    }

    private void initView() {
        gv_leave_img = (CustomGridView) findViewById(R.id.gv_leave_img);
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new PicEntity(imageList.size()+""));
                finish();
            }
        });
        leaveImageAdapter = new LeaveImageAdapter();
        gv_leave_img.setAdapter(leaveImageAdapter);
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表(存放的是图片路径)
                List<String> pathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                imageList.addAll(pathList);
                // 处理你自己的逻辑 ....
                Log.d("tag", "" + imageList);
                leaveImageAdapter.notifyDataSetChanged();
            }
        }
    }*/
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
                   List<String> pathList=new ArrayList<>();
                   for (int i=0;i<selectList.size();i++){
                       pathList.add(selectList.get(i).getCompressPath());
//                       上传图片到服务器
                       Map<String,String> map=new HashMap<>();
                       map.put("type","data");
                       map.put("image",Base64Utils.imageToBase64(selectList.get(i).getCompressPath()));
                       uploadPic(map);
                   }

                   SharedPreferencesTool.getMStool(this).setString("imgUrl", pathList.get(0));
                   imageList.addAll(pathList);
                   leaveImageAdapter.notifyDataSetChanged();
                   break;
           }
       }
   }

    public void uploadPic(Map<String,String> map) {
        // File file = new File(path);
        RetrofitInterface retrofitInterface = RetrofitUtil.getInstance().getRetrofitInterface();
        retrofitInterface.getPicturesBean(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PicturesBean>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(PicturesBean picturesBean) {
                        // getView().onSuccess(picturesBean,3);
                        String imgUrl=picturesBean.getFile();
                        sharedUtils.setStringValue("imgPicUrl",imgUrl);
                        Log.e("Mian",picturesBean.toString());
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("myMessage", e.toString());
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }


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
                Toast.makeText(PictureListActivity.this,"权限获取失败",Toast.LENGTH_SHORT).show();
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
                iv_image.setImageResource(R.mipmap.icon_add_pic);
                //删除按钮隐藏
                btn_delete.setVisibility(View.INVISIBLE);
            } else {
            //获取图片对象
                String  imageUrl = imageList.get(position);
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
                                    .with(PictureListActivity.this)
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

                    }else{

                    }
                }

            });

            return view;
        }
        /**
         * 包装缩略图 ImageView 集合
         * @return
         */
        @NonNull
        private List<ImageView> wrapOriginImageViewList() {
            List<ImageView> originImgList = new ArrayList<>();
            for (int i = 0; i < imageList.size(); i++) {
                ImageView thumImg = (ImageView) (gv_leave_img.getChildAt(i)).findViewById(R.id.iv_image);
                originImgList.add(thumImg);
            }
            return originImgList;
        }
        private void takePhoto() {
            //简单调用
            // 进入相册 以下是例子：用不到的api可以不写
            PictureSelector.create(PictureListActivity.this)
                    .openGallery(PictureMimeType.ofAll()).theme(R.style.picture_QQ_style)//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //需要调用onRequestPermissionsResult
        permissionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
