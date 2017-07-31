package com.slowinski.radoslaw;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.slowinskiradoslawgame.AdHandler;
import com.slowinskiradoslawgame.MyGame;


public class AndroidLauncher extends AndroidApplication implements RewardedVideoAdListener, AdHandler {
    private static final String APP_ID = "ca-app-pub-5573564924159397~3593823754";
    private static final String AD_UNIT_ID_VIDEO = "ca-app-pub-5573564924159397/6343177965";
    private static final String AD_UNIT_ID_GRAPHIC = "ca-app-pub-5573564924159397/2766841131";
    private static final String AD_UNIT_ID_BANNER = "ca-app-pub-5573564924159397/5727008983";
    private final int SHOW_VID_AD = 1;
    private final int SHOW_GRAPHIC_AD = 0;
    private InterstitialAd mInterstitialAd;
    // TODO shop's ad not loading when user watcher welcome's ad
    protected AdView adView;
    private RewardedVideoAd mRewardedVideoAd;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_VID_AD:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mRewardedVideoAd.isLoaded())
                                mRewardedVideoAd.show();
                            else
                                loadRewardedVideoAd();

                        }
                    });
                    break;
                case SHOW_GRAPHIC_AD:
                    if(mInterstitialAd.isLoaded()){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mInterstitialAd.show();
                            }
                        });
                    }
                    else {
                        loadFullScreenAd();
                    }
                    break;
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
                handler.sendEmptyMessage(show ? SHOW_VID_AD : SHOW_GRAPHIC_AD);
            }
        }), config);
        relativeLayout.addView(gameView);

        MobileAds.initialize(this, APP_ID);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getContext());
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AD_UNIT_ID_GRAPHIC);
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                loadFullScreenAd();
                super.onAdClosed();
            }

        });

        addTopAd(relativeLayout);
        setContentView(relativeLayout);

    }

    private void loadRewardedVideoAd() {
        if (!mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.loadAd(AD_UNIT_ID_VIDEO, new AdRequest.Builder().build());
        }

    }

    private void addTopAd(RelativeLayout relativeLayout) {
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(AD_UNIT_ID_BANNER);

        AdRequest.Builder builder = new AdRequest.Builder();

        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        relativeLayout.addView(adView, adParams);
        adView.loadAd(builder.build());
    }

    private void keepScreenOn() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onPause() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onPause();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        loadFullScreenAd();
    }

    private void loadFullScreenAd() {
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
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
        loadRewardedVideoAd();
    }

    @Override
    public void showAds(boolean showVideoAd) {
        handler.sendEmptyMessage(showVideoAd ? SHOW_VID_AD : SHOW_GRAPHIC_AD);
    }
}
