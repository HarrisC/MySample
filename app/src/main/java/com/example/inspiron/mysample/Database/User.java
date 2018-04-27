package com.example.inspiron.mysample.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Harris on 28/2/2018
 * Project Name MySample.
 */

@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String id;

    public String userName;

}
