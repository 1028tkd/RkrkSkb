package jp.co.digitalvision.rkrkskb.util;

/**
 * ���[�e�B���e�B�N���X
 * 
 */
public class Utils {

	/*
	 * ������null�̏ꍇ�A�󕶎��Ƃ��ĕԋp����
	 * 
	 * @param value ������
	 * @return �ϊ�������
	 */
	public static String nullToEmpty(String value){
		if(value == null) return "";
		return value;
	}
	
	/*
	 * �����񂪃u�����N�܂���null���ǂ������菈�����s��
	 * 
	 * @param value ���蕶����
	 * @return true�F�u�����N�܂���null�Afalse�F����ȊO
	 */
	public static boolean isBlankOrNull(String value){
		
		if(value.equals("") || value == null){
			return true;
		} else {
			return false;
		}
	}
	
	
}
