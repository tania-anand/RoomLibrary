package com.library.repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.library.database.Database;
import com.library.database.Library;

import java.util.ArrayList;
import java.util.List;

public class LibraryRepository {

    private String DB_NAME = "db_library";

    private Database database;
    private LibraryRepositoryInterface mListener;



    public LibraryRepository(Context context,LibraryRepositoryInterface listener){
        database = Room.databaseBuilder(context,Database.class,DB_NAME).build();
        mListener = listener;
    }

    public void insert(final Library library){
        new AsyncTask<Integer, Void, Integer>() {
            @Override
            protected Integer doInBackground(Integer... voids) {
                return (int)database.libraryDaoAccess().insert(library);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                mListener.integerResult(integer,0);
            }
        }.execute();
    }

    public void update(final Library library){

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.libraryDaoAccess().setBookIssue(library.getBookName(),library.isAvailable());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(library.isAvailable())
                    mListener.integerResult(1,2);
                else
                    mListener.integerResult(1,1);

            }
        }.execute();
    }

    public void getBook(final String bookName, final boolean isAvailable,final int whichFunction){

        new AsyncTask<List<Library>, Void,List<Library> >() {
            @Override
            protected List<Library>  doInBackground(List<Library>... lists) {
                List<Library> list = database.libraryDaoAccess().
                        getBook(bookName,isAvailable);
                return list;
            }

            @Override
            protected void onPostExecute(List <Library> libraries) {
                mListener.getDataResult(libraries,false,whichFunction);
            }


        }.execute();

    }

    public void getBook(final String bookName ,final int whichFunction){

        new AsyncTask<List<Library>, Void,List<Library> >() {
            @Override
            protected List<Library>  doInBackground(List<Library>... lists) {
                List<Library> list = database.libraryDaoAccess().
                        getBook(bookName);
                return list;
            }

            @Override
            protected void onPostExecute(List <Library> libraries) {
                mListener.getDataResult(libraries,true,whichFunction);
            }


        }.execute();

    }


    public interface LibraryRepositoryInterface{
        void integerResult(int result ,int callbackStatus);
        void getDataResult(List<Library> arratList,boolean isForUniqueCheck,int callbackResult);

    }






}