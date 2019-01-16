package com.hitomi.tilibrary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.tilibrary.style.view.photoview.PhotoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * 展示高清图的图片数据适配器
 * Created by hitomi on 2017/1/23.
 */
public class TransferPagerAdapter extends PagerAdapter {
    @IdRes
    private static final int ID_IMAGE = 1001;

    private int size;
    private Map<Integer, FrameLayout> containnerLayoutMap;
    private OnDismissListener onDismissListener;

    public TransferPagerAdapter(int imageSize) {
        size = imageSize;
        containnerLayoutMap = new WeakHashMap<>();
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 获取指定索引页面中的 ImageView
     *
     * @param position
     * @return
     */
    public ImageView getImageItem(int position) {
        FrameLayout parentLayout = containnerLayoutMap.get(position);
        int childCount = parentLayout.getChildCount();
        ImageView imageView = null;
        for (int i = 0; i < childCount; i++) {
            View view = parentLayout.getChildAt(i);
            if (view instanceof ImageView) {
                imageView = (ImageView) view;
                break;
            }
        }
        return imageView;
    }

    public FrameLayout getParentItem(int position) {
        return containnerLayoutMap.get(position);
    }

    public void setOnDismissListener(OnDismissListener listener) {
        this.onDismissListener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        FrameLayout parentLayout = containnerLayoutMap.get(position);
        if (parentLayout == null) {
            parentLayout = newParentLayout(container.getContext());
            containnerLayoutMap.put(position, parentLayout);
        }
        container.addView(parentLayout);
        return parentLayout;
    }

    @NonNull
    private FrameLayout newParentLayout(final Context context) {
        // create inner ImageView
        final PhotoView imageView = new PhotoView(context);
        imageView.setId(ID_IMAGE);
        imageView.enable();
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        FrameLayout.LayoutParams imageLp = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        imageLp.gravity = Gravity.CENTER;
        imageView.setLayoutParams(imageLp);

        // create outer ParentLayout
        FrameLayout parentLayout = new FrameLayout(context);
        FrameLayout.LayoutParams parentLp = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        parentLayout.setLayoutParams(parentLp);

        parentLayout.addView(imageView);

        // add listener to parentLayout
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.reset();
                onDismissListener.onDismiss();
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //获取照片的BitmapDrawable
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                if (drawable != null) {
                    //得到bitmap
                    Bitmap bitmap = drawable.getBitmap();
                    //保存弹窗
                    showSaveDialog(context, bitmap);
                }
                return true;
            }
        });

        return parentLayout;
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    /**
     * 长按保存弹窗
     */
    private void showSaveDialog(final Context context, final Bitmap bitmap) {

        final AlertDialog mDialog = new AlertDialog.Builder(context, R.style.ActionSheetDialogStyle).create();
        mDialog.show();
        Window window = mDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setContentView(R.layout.dialog_save_image);
        window.setGravity(Gravity.BOTTOM);

        //确定按钮
        TextView btn_ok = (TextView) window.findViewById(R.id.btn_ok);
        //设置确定按钮的点击
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //关闭弹窗
                    mDialog.dismiss();
                    //保存照片到本地
                    saveImage(context, bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //取消按钮
        TextView btn_cancel = (TextView) window.findViewById(R.id.btn_cancel);
        //设置取消按钮点击
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

    }


//    /**
//      * 点击获取图片
//      */
//    public void loadImageBitmap(final Context context, final String imageUrl) {
//        new AsyncTask<Object, Object, Bitmap>() {
//
//            @Override
//            protected Bitmap doInBackground(Object... params) {
//                Bitmap bitmap = null;
//                try {
//                    //获取照片bitmap
//                    bitmap = Picasso.with(context).load(imageUrl).get();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return bitmap;
//            }
//
//            @Override
//            protected void onPostExecute(Bitmap bitmap) {
//                if (bitmap != null) {
//                    //保存照片
//                    saveImage(context,bitmap);
//                } else {
//                   Toast.makeText(context, "保存失败",Toast.LENGTH_LONG).show();
//                }
//
//            }
//        }.execute();
//    }


    /**
     * 保存图片
     * @param bmp
     */
    private void saveImage(Context context, Bitmap bmp) {
        //照片地址
        String picPath = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";//保存到SD卡
        File appDir = new File(picPath, "ChanxaOA");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //照片名称
        String fileName = System.currentTimeMillis() + ".jpg";
        //file
        File file = new File(appDir, fileName);
        try {
            //输出流
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            //发送到系统相册
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
            Toast.makeText(context, "保存成功", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
