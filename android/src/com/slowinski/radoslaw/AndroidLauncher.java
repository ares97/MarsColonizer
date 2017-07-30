package com.mygdx.game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.slowinskiradoslawgame.AdHandler;
import com.slowinskiradoslawgame.MyGame;


public class AndroidLauncher extends AndroidApplication implements RewardedVideoAdListener, AdHandler {
    private static final String APP_ID = "ca-app-pub-5573564924159397~3593823754";
    private static final String TAG = "AndroidLauncher";
    private static final String AD_UNIT_ID_VIDEO = "ca-app-pub-5573564924159397/6343177965";
    private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;
    protected AdView adView;
    private RewardedVideoAd mRewardedVideoAd;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_ADS:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mRewardedVideoAd.isLoaded())
                                mRewardedVideoAd.show();
                        }
                    });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        keepScreenOn();
        RelativeLayout relativeLayout = new RelativeLayout(this);
        View gameView = initializeForView(new MyGame(new AdHandler() {
            @Override
            public void showAds(boolean show) {
                handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
            }
        }), config);
        relativeLayout.addView(gameView);

        MobileAds.initialize(this, APP_ID);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getContext());
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();


        addTopAd(relativeLayout);
        setContentView(relativeLayout);

    }

    private void loadRewardedVideoAd() {
        if (!mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.loadAd(AD_UNIT_ID_VIDEO, new AdRequest.Builder().addTestDevice("9FF1C33A9F8EC71970D48B9561468423").build());
        }

    }

    private void addTopAd(RelativeLayout relativeLayout) {
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("ca-app-pub-5573564924159397/5727008983");

        AdRequest.Builder builder = new AdRequest.Builder();
        // ---------
        builder.addTestDevice("9FF1C33A9F8EC71970D48B9561468423");
        //  -------

        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        relativeLayout.addView(adView, adParams);
        adView.loadAd(builder.build());
    }

    private void keepScreenOn() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onRewardedVideoAdLoaded() {
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        MyGame.adShown = true;
        Toast.makeText(this, "Great! Enjoy your gift!", Toast.LENGTH_SHORT).show();
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Toast.makeText(this, "turn on internet", Toast.LENGTH_LONG).show();
        loadRewardedVideoAd();
    }

    @Override
    public void showAds(boolean show) {
        handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
    }
}
