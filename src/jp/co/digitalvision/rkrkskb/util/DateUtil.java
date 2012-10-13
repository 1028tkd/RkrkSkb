package jp.co.digitalvision.rkrkskb.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * ���Ԋ֌W���ʃN���X
 * 
 * <PRE>
 * | Ver |   ���t   |       �X�V��       |               �ύX���e�E�R�����g
 * |-----|----------|--------------------|-------------------------------------------
 * | 1.0 |2012/10/01| T.Takada           | �V�K�쐬
 * </PRE>
 * 
 * @version
 * @author T.Takada
 */
public class DateUtil {
	
	/** �^�C���X�^���v�t�H�[�}�b�g */
	private final static SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyyMMddHHmm"); 
	
	/**
	 * ���݂̃^�C���X�^���v��"yyyyMMddHHmm"�`���̕�����ŕԂ�
	 * 
	 * @return ������
	 */
	public static String getYyyyMMddHHmm() {
		return yyyyMMddHHmm.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * ���݂̃^�C���X�^���v���t�H�[�}�b�g�`���̕�����ŕԂ�
	 * 
	 * @param format �t�H�[�}�b�g�`��
	 * @return ������
	 */
	public static String getFormatDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * ���ݓ��t�擾
	 * 
	 * @return ���t(���A���A�b�A�~���b�͂O)
	 */
	public static java.sql.Date getCurrDate() {
		java.sql.Date currDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		return java.sql.Date.valueOf(currDate.toString());
	}
}
