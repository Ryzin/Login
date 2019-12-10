//BaseEspresso基类

package com.ryzin.login;

import android.app.Activity;
import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.ParameterizedType;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(JUnit4.class)

public class BaseEspresso<T extends Activity> {

    @Rule
    public IntentsTestRule<T> mActivityRule = new IntentsTestRule<T>((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0], true, false);

    public T mActivity = null;


    /**
     * 初始化Activity
     *
     * @return
     */
    public T getActivity() {
        return getActivity(null);
    }


    /**
     * 初始化Activity
     *
     * @return mActivity
     */
    public T getActivity(Intent intent) {
        if (intent == null) {
            intent = new Intent();
        }
        if (mActivity == null) {
            mActivityRule.launchActivity(intent);
            mActivity = mActivityRule.getActivity();
        }
        return mActivity;
    }


    /**
     * 模拟用户的点击行为
     *
     * @param id
     */
    public void testClick(final int id) {
        onView(withId(id)).perform(closeSoftKeyboard(), click());
    }

    /**
     * 模拟用户的点击行为
     *
     * @param text
     */
    public void testClick(String text) {
        onView(withText(text)).perform(closeSoftKeyboard(), click());
    }


    /**
     * 模拟用户的点击行为
     *
     * @param id
     * @param text
     */
    public void testClick(final int id, String text) {
        onView(allOf(withId(id), withText(text))).perform(closeSoftKeyboard(), click());
    }


    /**
     * 模拟用户的点击行为
     *
     * @param id
     * @param scrollTo
     */
    public void testClick(final int id, boolean scrollTo) {
        if (scrollTo) {
            onView(allOf(withId(id))).perform(closeSoftKeyboard(), scrollTo(), click());
        } else {
            onView(allOf(withId(id))).perform(closeSoftKeyboard(), click());

        }
    }


    /**
     * 模拟用户的输入文本行为
     *
     * @param id
     * @param text
     * @return
     */
    public String testInputText(final int id, String text) {
        onView(withId(id)).perform(clearText(), replaceText(text), closeSoftKeyboard());
        return text;
    }


    /**
     * 检查View的文本变化是否正确
     *
     * @param id
     * @param text
     */
    public void testTextEquals(final int id, String text) {
        onView(withId(id)).check(matches(withText(text)));
    }


    /**
     * 检查View是否可见
     *
     * @param id
     */
    public void testViewVisible(final int id) {
        onView(withId(id))
                .check(matches(isDisplayed()));
    }


    /**
     * 检查View是否可见
     *
     * @param text
     */
    public void testViewVisible(String text) {
        onView(withText(text))
                .check(matches(isDisplayed()));
    }


    /**
     * 检查View是否可见（Toast等）
     *
     * @param id
     * @param uselessViable
     */
    public void testViewVisible(final int id, int uselessViable) {
        onView(withText(id))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }


    /**
     * 检查View是否可见
     *
     * @param id
     * @param text
     */
    public void testViewVisible(final int id, String text) {
        onView(allOf(withId(id), withText(text)))
                .check(matches(isDisplayed()));
    }


    /**
     * 检查View是否不可见
     *
     * @param id
     */
    public void testViewUnVisible(final int id) {
        onView(withId(id))
                .check(matches(not(isDisplayed())));
    }


    /**
     * 检查View是否可用
     *
     * @param id
     */
    public void testViewEnable(int id) {
        onView(withId(id))
                .check(matches(isEnabled()));
    }


    /**
     * 检查View是否不可用
     *
     * @param id
     */
    public void testViewUnEnable(int id) {
        onView(withId(id))
                .check(matches(not(isEnabled())));
    }


    /**
     * 休眠
     *
     * @param time
     */
    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取字符串
     *
     * @param resId
     * @return
     */
    public String getString(int resId) {
        if (mActivity == null) {
            getActivity();
        }
        return mActivity.getString(resId);
    }
}
