package com.ryzin.login;

import android.content.Intent;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest extends BaseEspresso<MainActivity>{

    @Test
    public void onCreate() {
        //启动MainActivity
        getActivity();
        Assert.assertNotNull(mActivity);
        sleep(1000); //便于开发者查看情况

        //启动LoginActivity
        testClick(R.id.login_btn);

        //测试正确登录/////////////////////////////////////////
        //测试View点击行为
        testClick(R.id.username);
        sleep(1000); //便于开发者查看情况

        //测试View输入与文本变化是否正确
        testInputText(R.id.username, "a");
        testTextEquals(R.id.username, "a");

        //测试hint是否正确出现
        testViewVisible(R.string.invalid_username, 1);
        sleep(1000);

        testInputText(R.id.username, "admin");
        sleep(1000);

        //测试View点击行为
        testClick(R.id.password);
        sleep(1000);

        //测试View输入与文本变化是否正确
        testInputText(R.id.password, "a");
        testTextEquals(R.id.password, "a");

        //测试hint是否正确出现
        testViewVisible(R.string.invalid_password,1);
        sleep(1000);

        testInputText(R.id.password, "admin");
        sleep(1000);

        //测试登录后，Toast提示是否正确
        testClick(R.id.login);
        testViewVisible(R.id.loading);
        sleep(2000); //等一会，Toast出来得比较慢
        testViewVisible(R.string.welcome,1);
        sleep(1000);


        //测试错误登录/////////////////////////////////////////
        //启动LoginActivity
        testClick(R.id.login_btn);
        sleep(1000); //便于开发者查看情况

        //测试View点击行为
        testClick(R.id.username);
        sleep(1000); //便于开发者查看情况

        //测试View输入
        testInputText(R.id.username, "asd");
        testInputText(R.id.password, "dsad");

        //测试登录后，第一次Toast提示是否正确
        testViewEnable(R.id.login);
        testClick(R.id.login);
        testViewVisible(R.id.loading);
        sleep(2000); //等一会，Toast出来得比较慢
        testViewVisible(R.string.login_failed,1);
        sleep(1000);

        //测试登录后，第二次Toast提示是否正确
        testViewEnable(R.id.login);
        testClick(R.id.login);
        testViewVisible(R.id.loading);
        sleep(2000); //等一会，Toast出来得比较慢
        testViewVisible(R.string.login_failed,1);
        sleep(1000);

        //测试登录后，第三次Toast提示是否正确
        testViewEnable(R.id.login);
        testClick(R.id.login);
        testViewVisible(R.id.loading);
        sleep(2000); //等一会，Toast出来得比较慢
        testViewVisible(R.string.login_failed,1);
        sleep(1000);

        //测试登录后，第四次Toast提示是否正确
        testViewEnable(R.id.login);
        testClick(R.id.login);
        testViewVisible(R.id.loading);
        sleep(2000); //等一会，Toast出来得比较慢
        testViewVisible(R.string.lock_login,1);
        testViewUnEnable(R.id.login); //错误次数达到上限，登录按钮不可用
        sleep(1000);
    }
}