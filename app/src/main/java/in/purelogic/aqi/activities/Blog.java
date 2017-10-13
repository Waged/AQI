package in.purelogic.aqi.activities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import es.dmoral.toasty.Toasty;
import in.purelogic.aqi.R;

public class Blog extends BaseActivity {

    String wordPress;
    AVLoadingIndicatorView avi;
    WebView htmlWebView;
    ImageView imageView;
    Dialog quit;
    Bundle webViewBundle;
    Button btnRefresh;
    private int onStartCount = 0 ;

    public void refreshMode(View v) {
        htmlWebView.reload();
        Toasty.info(Blog.this, "Reloading..", Toast.LENGTH_LONG, true).show();
    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_right);

        } else if (onStartCount == 1) {
            onStartCount++;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        onStartCount= 1;
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        imageView = (ImageView) findViewById(R.id.imageview);
        htmlWebView = (WebView) findViewById(R.id.webview);
        btnRefresh = (Button) findViewById(R.id.btn_refresh);
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_left);
        } else // already created so reverse animation
        {
             onStartCount = 2;
        }
        if (hasConnection()) {
            Toasty.info(this, "Please wait ...", Toast.LENGTH_LONG).show();
            imageView.setVisibility(View.GONE);
            btnRefresh.setVisibility(View.GONE);
            htmlWebView.setWebViewClient(new CustomWebViewClient());
            WebSettings webSetting = htmlWebView.getSettings();
            webSetting.setJavaScriptEnabled(true);
            webSetting.setJavaScriptCanOpenWindowsAutomatically(false);
            webSetting.setSupportMultipleWindows(false);
            webSetting.setSupportZoom(false);
            wordPress = getResources().getString(R.string.word_press);
            if (savedInstanceState != null) {
                htmlWebView.restoreState(savedInstanceState);
            } else {
                htmlWebView.loadUrl(wordPress);
            }


        } else {
            avi.hide();
            imageView.setVisibility(View.VISIBLE);
            btnRefresh.setVisibility(View.VISIBLE);
            Toasty.error(Blog.this, "Check Connection: error", Toast.LENGTH_LONG, true).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        webViewBundle = new Bundle();
        htmlWebView.saveState(webViewBundle);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        htmlWebView.saveState(outState);
    }


    @Override
    public void onBackPressed() {
        if (htmlWebView.canGoBack())
            htmlWebView.goBack();
        else {
            quit = new Dialog(Blog.this);
            quit.setContentView(R.layout.exit_dialog);
            quit.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Button yes = quit.findViewById(R.id.btnYes);
            Button no =  quit.findViewById(R.id.btnNo);
            quit.show();
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quit.cancel();
                }
            });
        }

    }

    public boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }

    private class CustomWebViewClient extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            htmlWebView.setVisibility(View.INVISIBLE);
            imageView.setImageResource(R.drawable.noconnectionatall);
            imageView.setVisibility(View.VISIBLE);
            btnRefresh.setVisibility(View.VISIBLE);
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("OverrideUrlLoading", "url :" + url);
            final Uri uri = Uri.parse(url);
            //    return handleUri(uri);
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e("onPageStarted", "changed: " + url);
            imageView.setVisibility(View.GONE);
            btnRefresh.setVisibility(View.GONE);
            htmlWebView.setVisibility(View.VISIBLE);
            avi.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.e("onPageFinished", "changed: " + url);
            avi.hide();
        }
    }
}