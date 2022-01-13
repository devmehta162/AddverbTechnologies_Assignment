package com.global.addverb_tech_assignment;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DisplayRegionModel.class}, version = 4)
public abstract class MyRoomDatabase extends RoomDatabase {

    public abstract RegionDao regionDao();

    private static volatile MyRoomDatabase INSTANCE;

    static MyRoomDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            , MyRoomDatabase.class, "DB_NAME").fallbackToDestructiveMigration().build();

                }
            }
        }
        return INSTANCE;
    }
}

