package com.wxx.app.base.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.shuben.zhixing.www.R


/**
 * 通知栏操作
 *
 * @author wxx
 */

object NotificationTool {
  /**
   * 通知栏id从10开始递增，前10个id留给特殊处理(以后未知操作)
   */
  private var notificationId = 10

  @JvmStatic
  fun showDefaultNotification(context: Context, content: String?) {
    content?.let {

      makeNotification(context, it, null)
    }
  }

  private fun makeNotification(context: Context?, content: String, intent: Intent?) {
    val builder = NotificationCompat.Builder(context)
    builder.apply {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        setSmallIcon(R.mipmap.logo)
      } else {
        setSmallIcon(R.mipmap.logo)
      }
      notification.flags = Notification.FLAG_AUTO_CANCEL;
      setContentTitle(context?.getString(R.string.app_name))
      setContentText(content)
      setAutoCancel(true)
      setOngoing(true)
      setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
      setWhen(System.currentTimeMillis())
      if (intent != null) {
        setContentIntent(PendingIntent.getActivity(context, 0, intent, 0))
      }
    }

    synchronized(notificationId){
      val notification = builder.build()
      val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
      notification.sound = uri
      getNotificationManager(context).notify(notificationId, notification)
      notificationId++
    }
  }

  private fun getNotificationManager(context: Context?) = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}