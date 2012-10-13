package jp.co.digitalvision.rkrkskb.db;

import java.io.Serializable;

/*
 * �ΑӃe�[�u���G���e�B�e�B�N���X
 * 
 */
public class KintaiEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7073396938120184519L;

	// �e�[�u����
	public static final String TABLE_NAME = "KINTAI";
	
	// �J������
	public static final String KBN = "KBN";					// �敪�i�P�F�o�΁A�Q�F�ދ΁A�R�F�x�Ɂj
	public static final String INPUT_DATE = "INPUT_DATE";		// ���͓��t�iYYYYMMDD�j
	public static final String HOUR = "HOUR";					// ���͎���
	public static final String MIN = "MIN";					// ���͕�
	public static final String TEKIYOU = "TEKIYOU";			// �E�v
	public static final String BIKOU = "BIKOU";				// ���l

	// �v���p�e�B
	private String kbn = null;
	private String inputDate = null;
	private String hour = null;
	private String min = null;
	private String tekiyou = null;
	private String bikou = null;
	
	public String getKbn() {
		return kbn;
	}
	public void setKbn(String kbn) {
		this.kbn = kbn;
	}
	public String getInputDate() {
		return inputDate;
	}
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getTekiyou() {
		return tekiyou;
	}
	public void setTekiyou(String tekiyou) {
		this.tekiyou = tekiyou;
	}
	public String getBikou() {
		return bikou;
	}
	public void setBikou(String bikou) {
		this.bikou = bikou;
	}
}
