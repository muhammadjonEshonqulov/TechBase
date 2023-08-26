package uz.teach.base.ui.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import uz.teach.base.basethings.BaseFragment
import uz.teach.base.databinding.FragmentWebViewBinding


class WebViewFragment : BaseFragment<FragmentWebViewBinding>(FragmentWebViewBinding::inflate) {


    inner class WebViewClient : android.webkit.WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        override fun onPageFinished(view: WebView, url: String) {
            binding.progressBar.visibility = View.GONE
            super.onPageFinished(view, url)
            binding.progressBar.visibility = View.GONE
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)


        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreatedd(view: View, savedInstanceState: Bundle?) {
        arguments?.getString("link")?.let {
            val link: String = it
            val webSettings = binding.webView.settings
            webSettings.javaScriptEnabled = true
            webSettings.domStorageEnabled = true

            binding.webView.webViewClient = WebViewClient()
            binding.webView.loadUrl(link)
            binding.progressBar.visibility = View.VISIBLE
        }

    }

}

