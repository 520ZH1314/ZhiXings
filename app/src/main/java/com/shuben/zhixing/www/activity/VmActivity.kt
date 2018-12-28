package com.shuben.zhixing.www.activity
import android.app.Activity
import android.content.Intent
import android.os.Message
import android.widget.Button
import android.widget.ImageView
import com.base.zhixing.www.BaseActvity
import com.base.zhixing.www.util.MyImageLoader
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.shuben.zhixing.www.R
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
class VmActivity : BaseActvity() {
    @BindView(R.id.iv_add_vm)
    internal var ivAddVm: ImageView? = null
    @BindView(R.id.btn_add_vm)
    internal var btnAddVm: Button? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_vm
    }
    override fun process(msg: Message) {
    }
    override fun initLayout() {
        ButterKnife.bind(this)
    }
    @OnClick(R.id.btn_add_vm)
    fun onViewClicked() {
        setImage()
    }
    private fun setImage() {
        PictureSelector.create(this@VmActivity)
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
                .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    // 图片、视频、音频选择结果回调
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for (bean in selectList) {
                        val compressPath = bean.compressPath
                        //                          ImageLoader.loadListeren(this,compressPath,ivMyInfoDetailHead);
                        MyImageLoader.loadListeren(this, compressPath, ivAddVm, true, "数本科技")
                    }
                }
            }
        }

    }

}
