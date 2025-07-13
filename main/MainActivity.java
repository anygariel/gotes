package id.web.wapes.nygotes;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.Activity;
import android.graphics.Color; 
import android.os.Build;
import android.view.Window; 
import android.view.WindowManager; 
import android.view.View;

public class MainActivity extends Activity {

    private WebView webView;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // Pastikan Anda memiliki activity_main.xml yang sesuai

        webView = findViewById(R.id.webView); // Pastikan ada WebView dengan ID 'webView' di activity_main.xml

        // Konfigurasi WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Aktifkan JavaScript
        webSettings.setDomStorageEnabled(true); // Aktifkan DOM Storage (termasuk localStorage)
        webSettings.setDatabaseEnabled(true); // Aktifkan Database API (jika diperlukan, untuk localStorage juga)
        webSettings.setAllowFileAccess(true); // Izinkan akses file lokal

        // Set WebViewClient untuk menangani navigasi di dalam WebView
        webView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					// Mengembalikan false agar URL dimuat di WebView itu sendiri
					return false;
				}
			});

        // Muat file HTML dari folder assets
        // Pastikan Anda menempatkan index.html di app/src/main/assets/
        webView.loadUrl("file:///android_asset/index.html");
		
		
        setSystemBarAppearance(
                Color.WHITE, // Warna Status Bar (misalnya, putih agar ikon gelap terlihat)
                Color.parseColor("#FFFFFF"), // Warna Navigation Bar (misalnya, merah)
                true, // Apakah ikon Status Bar harus gelap? (true = gelap, false = terang)
                false // Apakah ikon Navigation Bar harus gelap? (true = gelap, false = terang)
        );
    }
    private void setSystemBarAppearance(int statusBarColor, int navigationBarColor,
                                        boolean isStatusBarIconsDark, boolean isNavigationBarIconsDark) {
        // Pastikan API level adalah Lollipop (21) atau lebih tinggi untuk mengatur warna bilah sistem
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            // Flag ini diperlukan agar aplikasi dapat menggambar di bawah bilah sistem
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // Mengatur warna Status Bar
            window.setStatusBarColor(statusBarColor);

            // Mengatur warna Navigation Bar
            window.setNavigationBarColor(navigationBarColor);

            // Mengatur ikon Status Bar menjadi gelap (membutuhkan API 23 / Android 6.0 Marshmallow)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decor = window.getDecorView();
                int flags = decor.getSystemUiVisibility(); // Dapatkan flag UI saat ini

                if (isStatusBarIconsDark) {
                    // Tambahkan flag untuk ikon status bar gelap
                    flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    // Hapus flag jika ikon tidak ingin gelap (kembali ke terang)
                    flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decor.setSystemUiVisibility(flags); // Terapkan flag yang diperbarui
            }

            // Mengatur ikon Navigation Bar menjadi gelap (membutuhkan API 26 / Android 8.0 Oreo)

                View decor = window.getDecorView();
                int flags = decor.getSystemUiVisibility(); // Dapatkan flag UI saat ini

                if (isNavigationBarIconsDark) {
                    // Tambahkan flag untuk ikon navigation bar gelap
                    flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                } else {
                    // Hapus flag jika ikon tidak ingin gelap (kembali ke terang)
                    flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                }
                decor.setSystemUiVisibility(flags); // Terapkan flag yang diperbarui
            
        }
    }
    @Override
    public void onBackPressed() {
        // Tangani tombol kembali di WebView (jika ada riwayat navigasi)
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
