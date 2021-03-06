package com.islamdidarmd.adblockerwebviewsample

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.AppCompatEditText
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import com.islamdidarmd.adblockerwebview.AdBlockerWebView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview.settings.javaScriptEnabled = true
        webview.settings.domStorageEnabled = true

        webview.webViewClient = object: WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d(TAG, "onPageStarted $url")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d(TAG, "onPageFinished $url")
            }
        }

        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                Log.d(TAG, "Current progress $newProgress")
            }
        }

        btnGo.setOnClickListener {
            webview.setAdBlockEnabled(switchAdblock.isChecked)
            webview.visibility = View.VISIBLE
            editor.visibility = View.GONE

            var url = etUrl.text.toString()

            if (!url.startsWith("http")) {
                url = "http://$url"
            }
            webview.loadUrl(url)
        }
    }

    override fun onBackPressed() {
        when {
            editor.visibility == View.VISIBLE -> super.onBackPressed()
            webview.canGoBack() -> webview.goBack()
            else -> {
                webview.visibility = View.GONE
                editor.visibility = View.VISIBLE
            }
        }
    }
}
