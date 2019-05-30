package org.syahndra.diaryku;

import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.ag.floatingactionmenu.OptionsFabLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class WebViewActivity extends AppCompatActivity {
    String URL;

    WebView wb;

    OptionsFabLayout optionsFabLayout;
    ClipboardManager clipboardManager;

    AdView BannerAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        URL = intent.getStringExtra("link");

        wb = (WebView)findViewById(R.id.wb_main);
        optionsFabLayout = (OptionsFabLayout)findViewById(R.id.fab_options);
        clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        BannerAD = findViewById(R.id.web_banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        BannerAD.loadAd(adRequest);

        wb.getSettings().setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient());
        wb.loadUrl(URL);

        optionsFabLayout.setMainFabOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        optionsFabLayout.setMiniFabSelectedListener(new OptionsFabLayout.OnMiniFabSelectedListener() {
            @Override
            public void onMiniFabSelected(MenuItem fabItem) {
                switch (fabItem.getItemId()){
                    case R.id.fab_link:
                        clipboardManager.setText(wb.getUrl());
                        break;
                    case R.id.fab_share:
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"title");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "content");

                        Intent chooser = Intent.createChooser(shareIntent, "Bagikan");
                        startActivity(chooser);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && wb.canGoBack()){

            wb.goBack();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
