package com.loftblog.loftmoney.screens.addItem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.screens.main.adapter.Item;

public class AddItemActivity extends AppCompatActivity {
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

                String valueString = value + " P";
                Item item = new Item(name, valueString);

                Intent intent = new Intent();
                intent.putExtra(Item.KEY_NAME, item);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
