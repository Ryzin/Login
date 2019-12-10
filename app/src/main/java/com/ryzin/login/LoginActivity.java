package com.ryzin.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class LoginActivity extends AppCompatActivity {

    //登录错误计数器
    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final ImageButton loginButton = findViewById(R.id.login);
        final ImageButton languageBtn = findViewById(R.id.language);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final ImageButton loginBackBtn = findViewById(R.id.cancel);


        //添加监听事件
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isPasswordValid(passwordEditText.getText().toString()))
                    passwordEditText.setError(getResources().getString(R.string.invalid_password));
                if(!isUserNameValid(usernameEditText.getText().toString()))
                    usernameEditText.setError(getResources().getString(R.string.invalid_username));
            }
        };

        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE); //加载圈转一会，缓解用户等待情绪
                loginButton.setEnabled(false); //让按钮在加载过程中禁用

                new Handler().postDelayed(new Runnable() { //延时1s执行，让加载圈转一会
                    @Override
                    public void run() {
                        //do something
                        loadingProgressBar.setVisibility(View.GONE);

                        //如果输入框没有变化就按了登录的情况
                        if(!isUserNameValid(usernameEditText.getText().toString())) {
                            usernameEditText.setError(getResources().getString(R.string.invalid_username));
                            loginButton.setEnabled(true);
                            return;
                        }
                        if(!isPasswordValid(passwordEditText.getText().toString())) {
                            passwordEditText.setError(getResources().getString(R.string.invalid_password));
                            loginButton.setEnabled(true);
                            return;
                        }

                        //错误次数达到上限
                        if(counter <= 0) {
                            Toast.makeText(getApplicationContext(), R.string.lock_login, Toast.LENGTH_LONG).show();
                            return;
                        }
                        //成功登录
                        if (usernameEditText.getText().toString().equals("admin") && passwordEditText.getText().toString().equals("admin")) {
                            //提示
                            Toast.makeText(getApplicationContext(), R.string.welcome, Toast.LENGTH_LONG).show();
                            finish(); //输入正确
                        }
                        else {
                            counter--;
                            //提示
                            Toast.makeText(getApplicationContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
                        }
                        loginButton.setEnabled(true);
                    }
                }, 1000);

            }
        });

        loginBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //waiting fix
                finish();
                //System.exit(0);
            }
        });

        //超链接点击跳转
        TextView forget_password_textview = findViewById(R.id.forget_password_text_view);
        forget_password_textview.setMovementMethod(LinkMovementMethod.getInstance());  //其实就这一句是关键

        //切换语言按钮绑定响应事件
        languageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = getResources().getConfiguration().locale; //获取当前语言
                String language = locale.getLanguage();
                shiftLanguage(language);
            }
        });


    }

    // 检测账号有效性
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // 检测密码有效性
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 1;
    }

    //切换语言
    public void shiftLanguage(String sta) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (sta.equals("zh")) {
            Locale.setDefault(Locale.ENGLISH);
            config.locale = Locale.ENGLISH;
        } else {
            Locale.setDefault(Locale.CHINESE);
            config.locale = Locale.CHINESE;
        }
        getBaseContext().getResources().updateConfiguration(config
                , getBaseContext().getResources().getDisplayMetrics());
        refreshSelf();
    }

    //重启顶部Activity
    public void refreshSelf() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
