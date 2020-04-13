package com.loftblog.loftmoney.screens.addItem;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.web.WebFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class AddItemActivity extends AppCompatActivity {

    private List<Disposable> disposables = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        final EditText textName = findViewById(R.id.txtAddName);
        final EditText textValue = findViewById(R.id.txtAddValue);
        Button buttonAdd = findViewById(R.id.addButton);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textName.getText().toString();
                String value = textValue.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {
                    return;
                }

                sendNewExpense(Integer.valueOf(value), name);
            }
        });
    }

    private void sendNewExpense(Integer price, String name) {
        Disposable response = WebFactory.getInstance().postItemRequest()
                .request(price, name, "expense")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Toast.makeText(getApplicationContext(), getString(R.string.success_added), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        disposables.add(response);
    }
}
