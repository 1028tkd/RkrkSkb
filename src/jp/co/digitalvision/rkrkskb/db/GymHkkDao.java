package jp.co.digitalvision.rkrkskb.db;

import java.util.ArrayList;
import java.util.List;

import jp.co.digitalvision.rkrkskb.util.Utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 業務報告テーブルアクセスクラス
 */
public class GymHkkDao {

	private DatabaseOpenHelper helper = null;
	
	/*
	 * コンストラクタ
	 * 
	 * @param context
	 */
	public GymHkkDao(Context context) {
        helper = new DatabaseOpenHelper(context);
    }
	
	/*
	 * 業務報告テーブルの保存
	 * 新規データはINSERT、既存データの場合はUPDATEを実施する
	 * 
	 * @param entity 業務報告エンティティ
	 * @return 実行結果（0：正常、90：異常）
	 */
	public int doSave(GymHkkEntity entity){
		
		Log.v("GymHkkDao", "doSave start");

		int status = 0;		// 処理結果
		SQLiteDatabase db = helper.getWritableDatabase();
		
		try {
			
			ContentValues values = new ContentValues();
			// 時間
			values.put(GymHkkEntity.SOUKATSU, entity.getSoukatsu());
			// 分
			values.put(GymHkkEntity.POINT, entity.getPoint());
			// 備考
			values.put(GymHkkEntity.BIKOU, entity.getBikou());
			
			// 入力日付
			String inputDate = entity.getInputDate();
			
			if(!Utils.isBlankOrNull(inputDate)){
				// プライマリキーが取得できた場合は更新処理を行う
				db.update(GymHkkEntity.TABLE_NAME, values, getGymHkkPrimaryKey(), getGymHkkPrimaryData(entity));
			} else {
				// それ以外は新規作成処理を行う
				db.insert( GymHkkEntity.TABLE_NAME, null, values);
			}
			
		} catch (Exception e){
			status = 90;
		} finally {
			db.close();
		}
		
		Log.v("GymHkkDao", "doSave end");

		return status;
	}
	
	/**
     * 業務報告テーブルのレコード削除
     * 
     * @param entity 削除対象の業務報告エンティティ
     */
    public void doDelete(GymHkkEntity entity) {
    	
		Log.v("GymHkkDao", "doDelete start");

        SQLiteDatabase db = helper.getWritableDatabase();
        try {
        	db.delete(GymHkkEntity.TABLE_NAME, getGymHkkPrimaryKey(), getGymHkkPrimaryData(entity));
        } finally {
            db.close();
        }
        
		Log.v("GymHkkDao", "doDelete end");
    }
    
    /**
     * 業務報告テーブル一覧を取得する
     * 
     * @return 検索結果リスト
     */
    public List<GymHkkEntity> getGymHkkEntityList() {
    	
		Log.v("GymHkkDao", "getGymHkkEntityList start");

        SQLiteDatabase db = helper.getReadableDatabase();
        
        List<GymHkkEntity> gymHkkList;
        
        try {
        	// ORDER BY の指定（区分、入力日付）
        	String sort = GymHkkEntity.INPUT_DATE;
        	
        	// 検索結果を取得する
            Cursor cursor = db.query(GymHkkEntity.TABLE_NAME, null, null, null, null, null, sort);
            
            gymHkkList = new ArrayList<GymHkkEntity>();
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
            	gymHkkList.add(getGymHkkEntity(cursor));
                cursor.moveToNext();
            }
        } finally {
            db.close();
        }

		Log.v("GymHkkDao", "getGymHkkEntityList end");

		return gymHkkList;
    }

    /**
     * カーソルから業務報告エンティティへの変換
     * 
     * @param cursor カーソル
     * @return 変換結果
     */
    private GymHkkEntity getGymHkkEntity(Cursor cursor){
    	
		Log.v("GymHkkDao", "getGymHkkEntity start");
		
    	GymHkkEntity entity = new GymHkkEntity();
    	entity.setInputDate(cursor.getString(0));
    	entity.setSoukatsu(cursor.getString(1));
    	entity.setBikou(cursor.getString(2));
        
		Log.v("GymHkkDao", "getGymHkkEntity end");
		
        return entity;
    }
    
    /*
     * 業務報告テーブルのプライマリキー文字列を返す
     * 
     * return プライマリキー文字列
     */
    private String getGymHkkPrimaryKey(){
    	
		Log.v("GymHkkDao", "getGymHkkPrimaryKey start");
		
		// プライマリキー生成
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(GymHkkEntity.INPUT_DATE + "=?");
		
		Log.v("GymHkkDao", "getGymHkkPrimaryKey start");
		
		return strBuf.toString();
    }
    
    /*
     * 業務報告テーブルのプライマリキーデータを返す
     * 
     * return プライマリキーデータ
     */
    private String[] getGymHkkPrimaryData(GymHkkEntity entity){
    	
		Log.v("GymHkkDao", "getGymHkkPrimaryData start");
		
    	// プライマリキーデータ
    	String[] data = new String[1];
    	data[0] = entity.getInputDate();
    	
		Log.v("GymHkkDao", "getGymHkkPrimaryData start");

		return data;
    }
}
