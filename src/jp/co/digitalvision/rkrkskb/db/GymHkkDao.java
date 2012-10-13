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
 * �Ɩ��񍐃e�[�u���A�N�Z�X�N���X
 */
public class GymHkkDao {

	private DatabaseOpenHelper helper = null;
	
	/*
	 * �R���X�g���N�^
	 * 
	 * @param context
	 */
	public GymHkkDao(Context context) {
        helper = new DatabaseOpenHelper(context);
    }
	
	/*
	 * �Ɩ��񍐃e�[�u���̕ۑ�
	 * �V�K�f�[�^��INSERT�A�����f�[�^�̏ꍇ��UPDATE�����{����
	 * 
	 * @param entity �Ɩ��񍐃G���e�B�e�B
	 * @return ���s���ʁi0�F����A90�F�ُ�j
	 */
	public int doSave(GymHkkEntity entity){
		
		Log.v("GymHkkDao", "doSave start");

		int status = 0;		// ��������
		SQLiteDatabase db = helper.getWritableDatabase();
		
		try {
			
			ContentValues values = new ContentValues();
			// ����
			values.put(GymHkkEntity.SOUKATSU, entity.getSoukatsu());
			// ��
			values.put(GymHkkEntity.POINT, entity.getPoint());
			// ���l
			values.put(GymHkkEntity.BIKOU, entity.getBikou());
			
			// ���͓��t
			String inputDate = entity.getInputDate();
			
			if(!Utils.isBlankOrNull(inputDate)){
				// �v���C�}���L�[���擾�ł����ꍇ�͍X�V�������s��
				db.update(GymHkkEntity.TABLE_NAME, values, getGymHkkPrimaryKey(), getGymHkkPrimaryData(entity));
			} else {
				// ����ȊO�͐V�K�쐬�������s��
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
     * �Ɩ��񍐃e�[�u���̃��R�[�h�폜
     * 
     * @param entity �폜�Ώۂ̋Ɩ��񍐃G���e�B�e�B
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
     * �Ɩ��񍐃e�[�u���ꗗ���擾����
     * 
     * @return �������ʃ��X�g
     */
    public List<GymHkkEntity> getGymHkkEntityList() {
    	
		Log.v("GymHkkDao", "getGymHkkEntityList start");

        SQLiteDatabase db = helper.getReadableDatabase();
        
        List<GymHkkEntity> gymHkkList;
        
        try {
        	// ORDER BY �̎w��i�敪�A���͓��t�j
        	String sort = GymHkkEntity.INPUT_DATE;
        	
        	// �������ʂ��擾����
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
     * �J�[�\������Ɩ��񍐃G���e�B�e�B�ւ̕ϊ�
     * 
     * @param cursor �J�[�\��
     * @return �ϊ�����
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
     * �Ɩ��񍐃e�[�u���̃v���C�}���L�[�������Ԃ�
     * 
     * return �v���C�}���L�[������
     */
    private String getGymHkkPrimaryKey(){
    	
		Log.v("GymHkkDao", "getGymHkkPrimaryKey start");
		
		// �v���C�}���L�[����
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(GymHkkEntity.INPUT_DATE + "=?");
		
		Log.v("GymHkkDao", "getGymHkkPrimaryKey start");
		
		return strBuf.toString();
    }
    
    /*
     * �Ɩ��񍐃e�[�u���̃v���C�}���L�[�f�[�^��Ԃ�
     * 
     * return �v���C�}���L�[�f�[�^
     */
    private String[] getGymHkkPrimaryData(GymHkkEntity entity){
    	
		Log.v("GymHkkDao", "getGymHkkPrimaryData start");
		
    	// �v���C�}���L�[�f�[�^
    	String[] data = new String[1];
    	data[0] = entity.getInputDate();
    	
		Log.v("GymHkkDao", "getGymHkkPrimaryData start");

		return data;
    }
}
