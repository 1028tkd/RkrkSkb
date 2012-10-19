package jp.co.digitalvision.rkrkskb.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 勤怠テーブルアクセスクラス
 */
public class KintaiDao {

	private DatabaseOpenHelper helper = null;
	private SQLiteDatabase db = null;

	/*
	 * コンストラクタ
	 * 
	 * @param context
	 */
	public KintaiDao(Context context) {
        helper = new DatabaseOpenHelper(context);
    }

	/*
	 * 勤怠テーブルの保存
	 * 新規データはINSERT、既存データの場合はUPDATEを実施する
	 * 
	 * @param entity 勤怠エンティティ
	 * @return 実行結果（0：正常、90：異常）
	 */
	public long doSave(KintaiEntity entity){

		Log.v("KintaiDao", "doSave start");

		long status = 0;		// 処理結果
		db = helper.getWritableDatabase();

		try {

			String kbn = entity.getKbn();
			String inputDate = entity.getInputDate();

			ContentValues values = new ContentValues();
			// 区分
			values.put(KintaiEntity.KBN, kbn);
			// 入力日付
			values.put(KintaiEntity.INPUT_DATE, inputDate);
			// 時間
			values.put(KintaiEntity.HOUR, entity.getHour());
			// 分
			values.put(KintaiEntity.MIN, entity.getMin());
			// 摘要
			values.put(KintaiEntity.TEKIYOU, entity.getTekiyou());
			// 備考
			values.put(KintaiEntity.BIKOU, entity.getBikou());

//			Log.v("KintaiDao", "KBN       ===>>> " + entity.getKbn());
//			Log.v("KintaiDao", "INPUTDATE ===>>> " + entity.getInputDate());
//			Log.v("KintaiDao", "HOUR    ===>>> " + entity.getHour());
//			Log.v("KintaiDao", "MIN     ===>>> " + entity.getMin());
//			Log.v("KintaiDao", "TEKIYOU ===>>> " + entity.getTekiyou());
//			Log.v("KintaiDao", "BIKOU   ===>>> " + entity.getBikou());
			
			// データロード
			KintaiEntity ldEnt = load(kbn, inputDate);

			if(ldEnt != null){
				Log.v("KintaiDao", "doSave UPDATE start");
				
				// プライマリキーが取得できた場合は更新処理を行う
				db.update(KintaiEntity.TABLE_NAME, values, getKintaiWherePrimaryKey(), getKintaiPrimaryData(entity));

				Log.v("KintaiDao", "doSave UPDATE end");

			} else {
				Log.v("KintaiDao", "doSave INSERT start");

				// それ以外は新規作成処理を行う
				db.insert(KintaiEntity.TABLE_NAME, null, values);
				// コミット
				db.setTransactionSuccessful();
				
				Log.v("KintaiDao", "doSave INSERT end");
			}

		} catch (Exception e){
			Log.v("KintaiDao", "エラー発生[ " + e.toString() + " ]");
			status = 90;
		} finally {
			db.close();
		}

		Log.v("KintaiDao", "doSave end");

		return status;
	}

	/**
     * 勤怠テーブルのレコード削除
     * 
     * @param entity 削除対象の勤怠エンティティ
     */
    public void doDelete(KintaiEntity entity) {

		Log.v("KintaiDao", "doDelete start");

        try {
        	db.delete(KintaiEntity.TABLE_NAME, getKintaiWherePrimaryKey(), getKintaiPrimaryData(entity));
        } finally {
        }

		Log.v("KintaiDao", "doDelete end");
    }

    /**
     * 区分と入力日付で勤怠をロードする
     * 
     * @param 区分
     * @param 入力日付
     * @return ロード結果
     */
    public KintaiEntity load(String kbn, String inputDate) {

		Log.v("KintaiDao", "load start");

        KintaiEntity entity = new KintaiEntity();
        entity.setKbn(kbn);
        entity.setInputDate(inputDate);

        try {
            Cursor cursor = db.query(KintaiEntity.TABLE_NAME, getKintaiCols(), getKintaiWherePrimaryKey(), getKintaiPrimaryData(entity), null, null, null, null);
            boolean result = cursor.moveToFirst();
            if(result){
            	entity = getKintaiEntity(cursor);
            } else {
            	entity = null;
            }
        } finally {
        }

		Log.v("KintaiDao", "load end");

        return entity;
    }

    /**
     * 勤怠テーブル一覧を取得する
     * 
     * @return 検索結果リスト
     */
    public List<KintaiEntity> getKintaiEntityList() {

		Log.v("KintaiDao", "getKintaiEntityList start");

        List<KintaiEntity> kintaiList;

        try {
        	// ORDER BY の指定（区分、入力日付）
        	String sort = KintaiEntity.KBN + "," + KintaiEntity.INPUT_DATE;

        	// 検索結果を取得する
            Cursor cursor = db.query(KintaiEntity.TABLE_NAME, null, null, null, null, null, sort);

            kintaiList = new ArrayList<KintaiEntity>();
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
            	kintaiList.add(getKintaiEntity(cursor));
                cursor.moveToNext();
            }
        } finally {
        }

		Log.v("KintaiDao", "getKintaiEntityList end");

		return kintaiList;
    }

    /**
     * カーソルから勤怠エンティティへの変換
     * 
     * @param cursor カーソル
     * @return 変換結果
     */
    private KintaiEntity getKintaiEntity(Cursor cursor){

		Log.v("KintaiDao", "getKintaiEntity start");

    	KintaiEntity entity = new KintaiEntity();
    	entity.setKbn(cursor.getString(0));
    	entity.setInputDate(cursor.getString(1));
    	entity.setHour(cursor.getString(2));
    	entity.setMin(cursor.getString(3));
    	entity.setTekiyou(cursor.getString(4));
        entity.setBikou(cursor.getString(5));

//		Log.v("KintaiDao", "KBN        ===>>> " + entity.getKbn());
//		Log.v("KintaiDao", "INPUT_DATE ===>>> " + entity.getInputDate());
//		Log.v("KintaiDao", "HOUR       ===>>> " + entity.getHour());
//		Log.v("KintaiDao", "MIN        ===>>> " + entity.getMin());
//		Log.v("KintaiDao", "TEKIYOU    ===>>> " + entity.getTekiyou());
//		Log.v("KintaiDao", "BIKOU      ===>>> " + entity.getBikou());
        
        Log.v("KintaiDao", "getKintaiEntity end");

        return entity;
    }

    /*
     * 勤怠エンティティの取得カラムを返却する
     *
     * return 取得するカラムの配列
     */
    private String[] getKintaiCols(){

		Log.v("KintaiDao", "getKintaiCols start");

		// 取得カラム設定
    	String[] cols = new String[6];
    	cols[0] = KintaiEntity.KBN;
    	cols[1] = KintaiEntity.INPUT_DATE;
    	cols[2] = KintaiEntity.HOUR;
    	cols[3] = KintaiEntity.MIN;
    	cols[4] = KintaiEntity.TEKIYOU;
    	cols[5] = KintaiEntity.BIKOU;

		Log.v("KintaiDao", "getKintaiCols end");

		return cols;
    }

    /*
     * 勤怠テーブルの検索条件文字列を返す
     * 
     * return 検索条件文字列
     */
    private String getKintaiWherePrimaryKey(){

		Log.v("KintaiDao", "getKintaiPrimaryKey start");

		// プライマリキー生成
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(KintaiEntity.KBN + "=?");
		strBuf.append(" AND ");
		strBuf.append(KintaiEntity.INPUT_DATE + "=?");

		Log.v("KintaiDao", "getKintaiPrimaryKey end");

		return strBuf.toString();
    }

    /*
     * 勤怠テーブルのプライマリキーデータを返す
     * 
     * return プライマリキーデータ
     */
    private String[] getKintaiPrimaryData(KintaiEntity entity){

		Log.v("KintaiDao", "getKintaiPrimaryData start");

    	// プライマリキーデータ
    	String[] data = new String[2];
    	data[0] = entity.getKbn();
    	data[1] = entity.getInputDate();
    	
		Log.v("KintaiDao", "getKintaiPrimaryData end");

		return data;
    }
}
