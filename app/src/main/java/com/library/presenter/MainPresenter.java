package com.library.presenter;

import android.content.Context;
import android.widget.Toast;

import com.library.database.Library;
import com.library.interfaces.contract.MainContract;
import com.library.repository.LibraryRepository;

import java.util.List;

public class MainPresenter implements MainContract.viewer ,LibraryRepository.LibraryRepositoryInterface {

    private MainContract.presenter presenter;
    private Context mContext;
    private LibraryRepository libraryRepository;




    public MainPresenter(MainContract.presenter presenter,Context context){
        this.presenter = presenter;
        this.mContext = context;
        libraryRepository  = new LibraryRepository(mContext,this);

    }


    @Override
    public void onClickAdd(String bookName) {

        if(validation(bookName)){
            library = new Library();
            library.setBookName(bookName);
            library.setAvailable(true);
            // check already book exists in library or not here book name is considered unique id
            libraryRepository.getBook(bookName,0);

        }

    }

    private Library library;
    @Override
    public void onClickIssue(String bookName, boolean availableStatus) {
        if(validation(bookName)){
            library = new Library();
            library.setId(0);
            library.setBookName(bookName);
            library.setAvailable(false);
            // check book we are trying to issue is registered with library or not
            libraryRepository.getBook(bookName,2);
        }

    }

    @Override
    public void onClickReturn(String bookName, boolean availableStatus) {
        if(validation(bookName)){
            library = new Library();
            library.setId(0);
            library.setBookName(bookName);
            library.setAvailable(true);
            // check book we are trying to return is registered with library or not
            libraryRepository.getBook(bookName,1);
        }

    }

    private boolean validation(String bookName){
        boolean flag = true;

        if(bookName == null){
            flag = false;
            Toast.makeText(mContext,"Book Name Required",Toast.LENGTH_LONG).show();

        }else{
            if(bookName.equals("")){
                flag = false;
                Toast.makeText(mContext,"Book Name Required",Toast.LENGTH_LONG).show();
            }
        }

        return flag;
    }


    @Override
    public void integerResult(int result ,int callbackStatus) {

        if(callbackStatus == 0){
            // for insert
            if(result>0){
                Toast.makeText(mContext,"Book Inserted",Toast.LENGTH_LONG).show();
                presenter.clearEditText();

            }else{
                Toast.makeText(mContext,"Book not inserted",Toast.LENGTH_LONG).show();

            }
        }else if(callbackStatus == 1){
            // for issue of book
            if(result == 1){
                Toast.makeText(mContext,"Book Issued",Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(mContext,"Book not Issued",Toast.LENGTH_LONG).show();

            }

        }
        else if(callbackStatus == 2){
            // for return of book
            if(result == 1){
                Toast.makeText(mContext,"Book returned",Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(mContext,"Book not returned",Toast.LENGTH_LONG).show();

            }

        }
    }

    @Override
    public void getDataResult(List <Library> arrayList,boolean isForUniqueCheck,int callbackStatus) {

        if(!isForUniqueCheck) {
            if (arrayList.size() > 0) {
                libraryRepository.update(library);
            } else {
                if(!library.isAvailable())
                    Toast.makeText(mContext, "Book already issued", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(mContext, "Book already returned", Toast.LENGTH_LONG).show();

            }
        }else{
            if (arrayList.size() > 0) {

                if(callbackStatus == 1) {
                    // for return
                    libraryRepository.getBook(library.getBookName(), false, 1);
                }
                else if(callbackStatus == 0) {
                    // for insert
                    Toast.makeText(mContext, "Book already exists", Toast.LENGTH_LONG).show();
                }
                else if(callbackStatus == 2) {
                    // for issue
                    libraryRepository.getBook(library.getBookName(), true, 2);
                }

            } else {

                if(callbackStatus == 1) {
                    // for return
                    Toast.makeText(mContext, "Book you are trying to return is not registered with library", Toast.LENGTH_LONG).show();
                }
                else if(callbackStatus == 0) {
                    // for insert
                    libraryRepository.insert(library);
                }
                else if(callbackStatus == 2) {
                    // for issue
                    Toast.makeText(mContext, "Book you are trying to issue is not registered with library", Toast.LENGTH_LONG).show();
                }



            }

        }

    }
}
