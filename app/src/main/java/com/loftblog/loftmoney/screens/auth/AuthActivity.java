package com.loftblog.loftmoney.screens.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.screens.main.MainActivity;
import com.loftblog.loftmoney.screens.main.adapter.Item;
import com.loftblog.loftmoney.web.AuthRequest;
import com.loftblog.loftmoney.web.WebFactory;
import com.loftblog.loftmoney.web.models.AuthResponse;
import com.loftblog.loftmoney.web.models.GetItemsResponseModel;
import com.loftblog.loftmoney.web.models.ItemRemote;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AuthActivity extends AppCompatActivity {

    private List<Disposable> disposables = new ArrayList();
    private Button btnAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btnAuth = findViewById(R.id.auth_button);
        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    @Override
    public void onStop() {
        for(Disposable disposable: disposables) {
            disposable.dispose();
        }
        super.onStop();
    }

    private void signIn() {
        btnAuth.setVisibility(View.INVISIBLE);

        Disposable response = WebFactory.getInstance().getAuthRequest().request("leela")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AuthResponse>() {
                    @Override
                    public void accept(AuthResponse authResponse) throws Exception {
                        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString(AuthResponse.AUTH_TOKEN_KEY, authResponse.getAuthToken()).apply();

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        btnAuth.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Error - " + throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        disposables.add(response);
    }
}
