package me.tanshul.babyname.uitl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;

import me.tanshul.babyname.NameApp;

/**
 * Created by tansdeva on 15/01/18.
 * Utility class to access common functions
 */

public class Utility {
    private static Context mContext = NameApp.getContext();
    public static final int BUILD_VERSION = Build.VERSION.SDK_INT;
    public static final int VERSION_LOLLIPOP = Build.VERSION_CODES.LOLLIPOP;
    public static final int VERSION_MARSHMALLOW = Build.VERSION_CODES.M;
    public static final int VERSION_NOUGAT = Build.VERSION_CODES.N;

    public static boolean isLollipop() {
        return BUILD_VERSION >= VERSION_LOLLIPOP;
    }

    public static boolean isMarshMallow() {
        return BUILD_VERSION >= VERSION_MARSHMALLOW;
    }

    public static boolean isNougat() {
        return BUILD_VERSION >= VERSION_NOUGAT;
    }

    public static boolean validateString(String value) {
        return value != null && !value.isEmpty();
    }

    public static int getInt(int resId) {
        return mContext.getResources().getInteger(resId);
    }

    public static String getString(int resId) {
        return mContext.getResources().getString(resId);
    }

    public static int getColor(int resId) {
        return mContext.getResources().getColor(resId);
    }

    public static int getDimen(int resId) {
        return (int) mContext.getResources().getDimension(resId);
    }

    public static String[] getStringArray(int resId) {
        return mContext.getResources().getStringArray(resId);
    }

    public static ArrayList<String> getList(int resId) {
        ArrayList<String> list = new ArrayList<>();
        String[] data = getStringArray(resId);
        for (String item : data) {
            list.add(item);
        }
        return list;
    }

    public static Drawable getDrawable(int id) {
        return mContext.getResources().getDrawable(id);
    }

    public static void hideKeyboard(EditText editText, boolean clear) {
        InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        if (clear) {
            editText.setText("");
        }
    }

    public static void showKeyboard(final EditText editText) {
        final InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 100);
    }

    public static void showMessage(int msgId, View view) {
        if (view == null) {
            return;
        }
        Snackbar.make(view, msgId, Snackbar.LENGTH_SHORT).show();
    }
}
