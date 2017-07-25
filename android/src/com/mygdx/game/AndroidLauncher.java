package com.mygdx.game;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication {
	private static final String TAG = "AndroidLauncher";
	protected AdView adView;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		RelativeLayout relativeLayout = new RelativeLayout(this);
		View gameView = initializeForView(new MyGame(), config);
		relativeLayout.addView(gameView);

		adView = new AdView(this);
		adView.setAdListener(new AdListener(){
			@Override
			public void onAdLoaded() {
				Log.i(TAG, "Ad loaded");
			}
		});

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

		setContentView(relativeLayout);

	}
}
