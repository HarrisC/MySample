package com.example.inspiron.mysample.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Harris on 28/2/2018
 * Project Name MySample.
 */

@Dao
public interface UserDao {

    @Query("select * from user")
    List<User> loadAllUsers();

    @Query("select * from user where id = :id")
    User loadUserById(int id);

    @Insert(onConflict = REPLACE)
    void insertUser(User... users);

    @Query("delete from user where name like :name")
    int deleteUsersByName(String name);

}
