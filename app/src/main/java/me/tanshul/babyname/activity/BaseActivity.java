package me.tanshul.babyname.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tansdeva on 15/01/18.
 */

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    public Context getContext() {
        //Check if the context is null
        if (mContext == null) {
            mContext = this;
        }
        return mContext;
    }
}
