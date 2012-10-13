package jp.co.digitalvision.rkrkskb;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class RkrkSkbActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.v("RkrkSkbActivity", "onCreate start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rkrk_skb);
        
//        Intent intent = (Intent) getIntent();
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	Log.v("RkrkSkbActivity", "onCreateOptionsMenu start");
        getMenuInflater().inflate(R.menu.activity_rkrk_skb, menu);
        return true;
    }
}
