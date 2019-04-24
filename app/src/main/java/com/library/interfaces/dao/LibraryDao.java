package com.library.interfaces.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.library.database.Library;


import java.util.List;

@Dao
public interface LibraryDao {

    @Insert
    long insert(Library object);

    @Query("SELECT id,bookName,isAvailable FROM Library ORDER BY id")
    LiveData<List<Library>> fetchAllLibrary();

    @Query("SELECT id,bookName,isAvailable FROM Library WHERE bookName like :bookName and isAvailable = :isAvailable")
    List<Library> getBook(String bookName , boolean isAvailable);


    @Query("SELECT id,bookName,isAvailable FROM Library WHERE bookName like :bookName")
    List<Library> getBook(String bookName );

    @Query("UPDATE Library SET isAvailable = :isAvailable WHERE bookName like :bookName")
    void  setBookIssue(String bookName ,boolean isAvailable);


}
