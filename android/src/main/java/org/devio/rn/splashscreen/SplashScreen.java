package org.devio.rn.splashscreen;

import android.app.Activity;
import android.app.Dialog;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * SplashScreen
 * 启动屏
 * from：http://www.devio.org
 * Author:CrazyCodeBoy
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class SplashScreen {
    private static Dialog mSplashDialog;
    private static WeakReference<Activity> mActivity;
    private static ImageView imgCenter, imgRight, imgLeft, imgBottom;

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity, final int themeResId) {
        mSplashDialog = new Dialog(activity, themeResId);
        mSplashDialog.setContentView(R.layout.launch_screen);
        mSplashDialog.setCancelable(false);

        if (activity == null) return;
        mActivity = new WeakReference<Activity>(activity);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!activity.isFinishing()) {

                    //move all in
                    imgCenter = (ImageView) mSplashDialog.findViewById(R.id.imageLogo);
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                    alphaAnimation.setDuration(2000);
                    imgCenter.startAnimation(alphaAnimation);

                    imgRight = (ImageView) mSplashDialog.findViewById(R.id.imageRight);
                    Animation animation = new TranslateAnimation(500, 0,0, 0);
                    animation.setDuration(2000);
                    animation.setFillAfter(true);
                    imgRight.startAnimation(animation);

                    imgLeft = (ImageView) mSplashDialog.findViewById(R.id.imageLeft);
                    Animation animation1 = new TranslateAnimation(-500, 0,0, 0);
                    animation1.setDuration(2000);
                    animation1.setFillAfter(true);
                    imgLeft.startAnimation(animation1);

                    imgBottom = (ImageView) mSplashDialog.findViewById(R.id.imageBottom);
                    Animation animation2 = new TranslateAnimation(0, 0,1000, 0);
                    animation2.setDuration(2000);
                    animation2.setFillAfter(true);
                    imgBottom.startAnimation(animation2);

                    if (!mSplashDialog.isShowing()) {
                        mSplashDialog.show();
                    }

                }
            }
        });
    }

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity, final boolean fullScreen) {
        int resourceId = fullScreen ? R.style.SplashScreen_Fullscreen : R.style.SplashScreen_SplashTheme;

        show(activity, resourceId);
    }

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity) {
        show(activity, false);
    }

    /**
     * 关闭启动屏
     */
    public static void hide(Activity activity) {
        if (activity == null) {
            if (mActivity == null) {
                return;
            }
            activity = mActivity.get();
        }

        if (activity == null) return;

        final Activity _activity = activity;

        //hiding all views
        Log.e("SplashScreen", "hiding all views");
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(2000);
        imgCenter.startAnimation(alphaAnimation);
        imgCenter.setVisibility(View.INVISIBLE);

        Animation animation = new TranslateAnimation(0, 5000,0, 0);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        imgRight.startAnimation(animation);
        imgRight.setVisibility(View.INVISIBLE);

        Animation animation1 = new TranslateAnimation(0, -500,0, 0);
        animation1.setDuration(2000);
        animation1.setFillAfter(true);
        imgLeft.startAnimation(animation1);
        imgLeft.setVisibility(View.INVISIBLE);

        Animation animation2 = new TranslateAnimation(0, 0,0, 1000);
        animation2.setDuration(2000);
        animation2.setFillAfter(true);
        imgBottom.startAnimation(animation2);
        imgBottom.setVisibility(View.INVISIBLE);

        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        if (mSplashDialog != null && mSplashDialog.isShowing()) {
                            boolean isDestroyed = false;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                isDestroyed = _activity.isDestroyed();
                            }

                            if (!_activity.isFinishing() && !isDestroyed) {

                                mSplashDialog.dismiss();
                            }
                            mSplashDialog = null;
                        }
                    }
                }, 3000);

            }
        });
    }
}
