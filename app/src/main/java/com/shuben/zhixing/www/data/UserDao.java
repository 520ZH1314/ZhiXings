package com.shuben.zhixing.www.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM userdata")
    List<UseEntity> getAllUsers();

    @Insert
    void insert(UseEntity  users);

    @Update
    void update(UseEntity users);

    @Delete
    void delete(UseEntity  users);


}
