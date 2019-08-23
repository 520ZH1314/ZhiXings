package com.shuben.zhixing.www.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.shuben.zhixing.www.common.SpeechUtils;

/**
 * @author cloor
 */
public class VoiceService extends Service {
     @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent!=null&&intent.hasExtra("des")){
            SpeechUtils.getInstance(this).speakText(intent.getStringExtra("des"));
        }
        return super.onStartCommand(intent, flags, startId);
    }
  
}
