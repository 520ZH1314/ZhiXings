package com.shuben.zhixing.www.dataBase;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.shuben.zhixing.www.data.UseEntity;
import com.shuben.zhixing.www.data.UserDao;

/**
 *
 *@author zjq
 *create at 2018/12/20 下午4:28
 * 创建user数据库
 */
@Database(entities = { UseEntity.class }, version = 1,exportSchema = false)
public abstract class UserDatabase  extends RoomDatabase {

    private static final String DB_NAME = "UserDatabase.db";
    private static volatile UserDatabase instance;


   public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static UserDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                UserDatabase.class,
                DB_NAME).build();
    }


    public abstract UserDao getUserDao();

}
