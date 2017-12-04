package com.example.hassanusman.things2do.presistence.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by HassanUsman on 1/12/17.
 */
@Database(entities = {Task.class},exportSchema = false,version = 1)
public abstract class LocalDatabase extends RoomDatabase {

    private static volatile LocalDatabase INSTANCE;
    public abstract TaskDao taskDao();

    public static LocalDatabase newInstance(Context context){
        if (INSTANCE == null) {
            synchronized (LocalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            LocalDatabase.class, "Task.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
