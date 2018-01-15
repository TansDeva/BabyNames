package me.tanshul.babyname.activity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.tanshul.babyname.R;
import me.tanshul.babyname.uitl.Utility;

/**
 * Created by tansdeva on 15/01/18.
 * Splash activity (initial screen)
 */

public class SplashActivity extends BaseActivity {
    private int mDelay, mDuration;
    private ObjectAnimator mRotateAnimation;

    @BindView(R.id.tv_player_text)
    TextView tvOlaPlayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        startAnimation();
    }

    private void startAnimation() {
        tvOlaPlayText.setVisibility(View.VISIBLE);
        mDelay = Utility.getInt(R.integer.anim_fast);
        mDuration = Utility.getInt(R.integer.anim_slow);
        mRotateAnimation = (ObjectAnimator) AnimatorInflater.loadAnimator(mContext, R.animator.view_flip);
        mRotateAnimation.setTarget(tvOlaPlayText);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRotateAnimation.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(mContext, HomeActivity.class));
                    }
                }, mDuration);
            }
        }, mDelay);
    }
}
