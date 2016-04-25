package com.example.asus.bdcricketteam;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.asus.bdcricketteam.ads.GoogleAds;
import com.google.android.gms.ads.AdView;

/**
 * Created by ASUS on 3/6/2016.
 */
public class LiveScoreActivity extends AppCompatActivity {

    private AdView mAdView;
    private WebView mWebview;
    private ProgressBar mProgressBar;

    public LiveScoreActivity() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_live_score);
        String link = getIntent().getStringExtra("link");
        mAdView = (AdView) findViewById(R.id.ad_view);
        getSupportActionBar().setTitle(getResources().getString(R.string.live_score));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBarLoading);
        com.google.android.gms.ads.AdRequest adrequest = (new com.google.android.gms.ads.AdRequest.Builder()).addTestDevice("18D9D4FB40DF048C506091E42E0FDAFD").build();
        mAdView.loadAd(adrequest);
        mWebview = (WebView) findViewById(R.id.webview_liveScore);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // Show progressbar
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Show error
                // Stop spinner or progressbar
                //mProgressBar.setVisibility(View.GONE);
                Toast.makeText(LiveScoreActivity.this, "Connectivity problem", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Stop spinner or progressBar
                mProgressBar.setVisibility(View.GONE);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        mWebview.loadUrl(link);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        // requestNewInterstitial();
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
     //   GoogleAds.getGoogleAds(this).showInterstitial();
        super.onDestroy();
    }

}
