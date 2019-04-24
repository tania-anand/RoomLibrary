package com.library.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.library.presenter.MainPresenter;
import com.library.R;
import com.library.interfaces.contract.MainContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainContract.presenter {

    @BindView(R.id.ed_book_name)
    EditText edBookName;
    @BindView(R.id.btn_add_book)
    Button btnAddBook;
    @BindView(R.id.issue_book)
    Button btnIssueBook;
    @BindView(R.id.return_book)
    Button btnReturnBook;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initView();

        presenter = new MainPresenter(this,this);
    }

    void initView(){
        btnAddBook.setOnClickListener(this);
        btnIssueBook.setOnClickListener(this);
        btnReturnBook.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_book:
                presenter.onClickAdd(edBookName.getText().toString().isEmpty()?null:edBookName.getText().toString().trim());
                break;
            case R.id.issue_book:
                presenter.onClickIssue(edBookName.getText().toString().isEmpty()?null:edBookName.getText().toString().trim(),true);
                break;
            case R.id.return_book:
                presenter.onClickReturn(edBookName.getText().toString().isEmpty()?null:edBookName.getText().toString().trim(),false);
        }
    }

    @Override
    public void clearEditText() {
        edBookName.setText(null);
    }
}
