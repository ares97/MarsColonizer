package com.mygdx.game;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class AndroidLauncher extends AndroidApplication implements RewardedVideoAdListener{
	private static final String APP_ID = "ca-app-pub-5573564924159397~3593823754";
	private static final String TAG = "AndroidLauncher";
	private static final String AD_UNIT_ID_VIDEO = "ca-app-pub-5573564924159397/6343177965";
	protected AdView adView;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		keepScreenOn();

		RelativeLayout relativeLayout = new RelativeLayout(this);
		View gameView = initializeForView(new MyGame(), config);
		relativeLayout.addView(gameView);



		addTopAd(relativeLayout);
		setContentView(relativeLayout);

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
		relativeLayout.addView(adView,adParams);
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

	}

	@Override
	public void onRewardedVideoAdLeftApplication() {

	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int i) {

	}
}
