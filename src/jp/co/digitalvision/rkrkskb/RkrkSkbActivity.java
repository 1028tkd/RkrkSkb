package jp.co.digitalvision.rkrkskb;

import java.util.ArrayList;
import java.util.List;

import jp.co.digitalvision.rkrkskb.db.KintaiDao;
import jp.co.digitalvision.rkrkskb.db.KintaiEntity;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;

public class RkrkSkbActivity extends Activity {

	ListView listView;
	Button addButton;
	static List<String> dataList = new ArrayList<String>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.v("RkrkSkbActivity", "onCreate start");
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rkrk_skb);
        
        findViews();
        
    	Log.v("RkrkSkbActivity", "onCreate end");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	Log.v("RkrkSkbActivity", "onCreateOptionsMenu start");
        getMenuInflater().inflate(R.menu.activity_rkrk_skb, menu);
        
    	Log.v("RkrkSkbActivity", "onCreateOptionsMenu end");
        return true;
    }
    
    protected void findViews(){
        listView = (ListView)findViewById(R.id.listView);
    }

    @Override
    protected void onResume() {
    	Log.v("RkrkSkbActivity", "onResume start");
    	
    	super.onResume();
    	Context context = this;
    	
        // DBからデータ読み込み
        dataList = getKintaiList(context);
    	
    	Log.v("RkrkSkbActivity", "onResume end");
    }
    
    /**
     * 勤怠テーブル一覧を取得する
     * 
     * @return 検索結果リスト
     */
    public List<String> getKintaiList(Context context) {

		Log.v("KintaiDao", "getKintaiEntityList start");

		KintaiDao kintai = new KintaiDao(context);
		List<KintaiEntity> kintaiList = kintai.getKintaiEntityList();
		
		for(KintaiEntity entity : kintaiList){
			// 時間 + 分
			dataList.add(entity.getHour() + ":" + entity.getMin());
		}
		
		return dataList; 
    }
}
