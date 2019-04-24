package com.library.database;

import android.arch.persistence.room.RoomDatabase;

import com.library.interfaces.dao.LibraryDao;


@android.arch.persistence.room.Database(entities = {Library.class} ,version = 1,exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract LibraryDao libraryDaoAccess();
}
