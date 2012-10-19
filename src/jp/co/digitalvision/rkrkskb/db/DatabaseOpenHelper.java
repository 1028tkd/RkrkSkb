package jp.co.digitalvision.rkrkskb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	// データベース名の定数
    private static final String DB_NAME = "RKRK_SKB";

	//コンストラクタ
    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    /*
     * 初回起動時テーブル生成処理
     * 
     * @param db SQLiteDatabase
     */
    @Override
	public void onCreate(SQLiteDatabase db) {

    	Log.v("DatabaseOpenHelper", "onCreate start");

    	db.beginTransaction();

    	try {
    		// 勤怠テーブルの生成
    		StringBuilder createSql = new StringBuilder();
            createSql.append("create table " + KintaiEntity.TABLE_NAME + " (");
            createSql.append(KintaiEntity.KBN + " text,");
            createSql.append(KintaiEntity.INPUT_DATE + " text,");
            createSql.append(KintaiEntity.HOUR + " text,");
            createSql.append(KintaiEntity.MIN + " text,");
            createSql.append(KintaiEntity.TEKIYOU + " text,");
            createSql.append(KintaiEntity.BIKOU + " text,");
            createSql.append("primary key(");
            createSql.append(KintaiEntity.KBN + ",");
            createSql.append(KintaiEntity.INPUT_DATE + "))");

            Log.v("DatabaseOpenHelper", createSql.toString());

            db.execSQL(createSql.toString());

            // 業務報告テーブルの生成
    		createSql = new StringBuilder();
            createSql.append("create table " + GymHkkEntity.TABLE_NAME + " (");
            createSql.append(GymHkkEntity.INPUT_DATE + " text,");
            createSql.append(GymHkkEntity.SOUKATSU + " text,");
            createSql.append(GymHkkEntity.POINT + " text,");
            createSql.append(GymHkkEntity.BIKOU + " text,");
            createSql.append("primary key(");
            createSql.append(GymHkkEntity.INPUT_DATE + "))");

            Log.v("DatabaseOpenHelper", createSql.toString());

            db.execSQL(createSql.toString());
            
            // コミット実行
            db.setTransactionSuccessful();

    	} catch(Exception e) {
    		// エラーログ出力
    		Log.e("DatabaseOpenHelper", e.toString());
    	} finally {
        	Log.v("DatabaseOpenHelper", "onCreate end");

    		db.endTransaction();
    	}
	}

    /*
     * (�� Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自動生成されたメソッド・スタブ
	}
}
