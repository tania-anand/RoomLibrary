package com.library.interfaces.contract;

public interface MainContract {

    interface viewer{
        void onClickAdd(String bookName);
        void onClickIssue(String bookName, boolean availableStatus);
        void onClickReturn(String bookName,boolean availableStatus);
    }

    interface presenter{
        void clearEditText();

    }
}
