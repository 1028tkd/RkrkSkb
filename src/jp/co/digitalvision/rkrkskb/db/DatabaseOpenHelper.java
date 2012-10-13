package jp.co.digitalvision.rkrkskb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	// �f�[�^�x�[�X���̒萔
    private static final String DB_NAME = "RKRK_SKB";

	//�R���X�g���N�^
    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    /*
     * ����N�����e�[�u����������
     *
     * @param db SQLiteDatabase
     */
    @Override
	public void onCreate(SQLiteDatabase db) {

    	Log.v("DatabaseOpenHelper", "onCreate start");

    	db.beginTransaction();

    	try {
    		// �ΑӃe�[�u���̐���
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

    		// �Ɩ��񍐃e�[�u���̐���
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

    	} catch(Exception e) {
    		// �G���[���O�o��
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
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
}
