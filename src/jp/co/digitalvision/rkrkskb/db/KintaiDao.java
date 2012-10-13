package jp.co.digitalvision.rkrkskb.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * �ΑӃe�[�u���A�N�Z�X�N���X
 */
public class KintaiDao {

	private DatabaseOpenHelper helper = null;

	/*
	 * �R���X�g���N�^
	 *
	 * @param context
	 */
	public KintaiDao(Context context) {
        helper = new DatabaseOpenHelper(context);
    }

	/*
	 * �ΑӃe�[�u���̕ۑ�
	 * �V�K�f�[�^��INSERT�A��f�[�^�̏ꍇ��UPDATE�����{����
	 *
	 * @param entity �ΑӃG���e�B�e�B
	 * @return ���s���ʁi0�F����A90�F�ُ�j
	 */
	public int doSave(KintaiEntity entity){

		Log.v("KintaiDao", "doSave start");

		int status = 0;		// ��������
		SQLiteDatabase db = helper.getWritableDatabase();

		try {

			ContentValues values = new ContentValues();
			// ����
			values.put(KintaiEntity.HOUR, entity.getHour());
			// ��
			values.put(KintaiEntity.MIN, entity.getMin());
			// �E�v
			values.put(KintaiEntity.TEKIYOU, entity.getTekiyou());
			// ���l
			values.put(KintaiEntity.BIKOU, entity.getBikou());

			String kbn = entity.getKbn();
			String inputDate = entity.getInputDate();

			KintaiEntity ldEnt = load(kbn, inputDate);

			if(ldEnt != null){
				Log.v("KintaiDao", "doSave UPDATE start");

				// �v���C�}���L�[���擾�ł����ꍇ�͍X�V�������s��
				db.update(KintaiEntity.TABLE_NAME, values, getKintaiPrimaryKey(), getKintaiPrimaryData(entity));

				Log.v("KintaiDao", "doSave UPDATE end");

			} else {

				Log.v("KintaiDao", "doSave INSERT start");

				//
				values.put(KintaiEntity.KBN, kbn);
				//
				values.put(KintaiEntity.INPUT_DATE, inputDate);

				// ����ȊO�͐V�K�쐬�������s��
				db.insert( KintaiEntity.TABLE_NAME, null, values);

				Log.v("KintaiDao", "doSave INSERT end");
			}

		} catch (Exception e){
			status = 90;
		} finally {
			db.close();
		}

		Log.v("KintaiDao", "doSave end");

		return status;
	}

	/**
     * �ΑӃe�[�u���̃��R�[�h�폜
     *
     * @param entity �폜�Ώۂ̋ΑӃG���e�B�e�B
     */
    public void doDelete(KintaiEntity entity) {

		Log.v("KintaiDao", "doDelete start");

        SQLiteDatabase db = helper.getWritableDatabase();
        try {
        	db.delete(KintaiEntity.TABLE_NAME, getKintaiPrimaryKey(), getKintaiPrimaryData(entity));
        } finally {
            db.close();
        }

		Log.v("KintaiDao", "doDelete end");
    }

    /**
     * �敪�Ɠ�͓�t�ŋΑӂ����[�h����
     *
     * @param �敪
     * @param ��͓�t
     * @return ���[�h����
     */
    public KintaiEntity load(String kbn, String inputDate) {

		Log.v("KintaiDao", "load start");

        SQLiteDatabase db = helper.getReadableDatabase();

        KintaiEntity entity = new KintaiEntity();
        entity.setKbn(kbn);
        entity.setInputDate(inputDate);

        try {
    		Log.v("KintaiDao", "111");
            Cursor cursor = db.query(KintaiEntity.TABLE_NAME, null, getKintaiPrimaryKey(), getKintaiPrimaryData(entity), null, null, null);
    		Log.v("KintaiDao", "222");
            cursor.moveToFirst();
    		Log.v("KintaiDao", "333");
            entity = getKintaiEntity(cursor);
    		Log.v("KintaiDao", "444");
        } finally {
            db.close();
        }

		Log.v("KintaiDao", "load end");

        return entity;
    }

    /**
     * �ΑӃe�[�u���ꗗ���擾����
     *
     * @return �������ʃ��X�g
     */
    public List<KintaiEntity> getKintaiEntityList() {

		Log.v("KintaiDao", "getKintaiEntityList start");

        SQLiteDatabase db = helper.getReadableDatabase();

        List<KintaiEntity> kintaiList;

        try {
        	// ORDER BY �̎w��i�敪�A��͓�t�j
        	String sort = KintaiEntity.KBN + "," + KintaiEntity.INPUT_DATE;

        	// �������ʂ��擾����
            Cursor cursor = db.query(KintaiEntity.TABLE_NAME, null, null, null, null, null, sort);

            kintaiList = new ArrayList<KintaiEntity>();
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
            	kintaiList.add(getKintaiEntity(cursor));
                cursor.moveToNext();
            }
        } finally {
            db.close();
        }

		Log.v("KintaiDao", "getKintaiEntityList end");

		return kintaiList;
    }

    /**
     * �J�[�\������ΑӃG���e�B�e�B�ւ̕ϊ�
     *
     * @param cursor �J�[�\��
     * @return �ϊ�����
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

		Log.v("KintaiDao", "getKintaiEntity end");

        return entity;
    }

    /*
     * �ΑӃe�[�u���̃v���C�}���L�[�������Ԃ�
     *
     * return �v���C�}���L�[������
     */
    private String getKintaiPrimaryKey(){

		Log.v("KintaiDao", "getKintaiPrimaryKey start");

		// �v���C�}���L�[����
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(KintaiEntity.KBN + "=?");
		strBuf.append(",");
		strBuf.append(KintaiEntity.INPUT_DATE + "=?");

		Log.v("KintaiDao", "getKintaiPrimaryKey end");

		return strBuf.toString();
    }

    /*
     * �ΑӃe�[�u���̃v���C�}���L�[�f�[�^��Ԃ�
     *
     * return �v���C�}���L�[�f�[�^
     */
    private String[] getKintaiPrimaryData(KintaiEntity entity){

		Log.v("KintaiDao", "getKintaiPrimaryData start");

    	// �v���C�}���L�[�f�[�^
    	String[] data = new String[2];
    	data[0] = entity.getKbn();
    	data[1] = entity.getInputDate();

		Log.v("KintaiDao", "getKintaiPrimaryData end");

		return data;
    }
}
