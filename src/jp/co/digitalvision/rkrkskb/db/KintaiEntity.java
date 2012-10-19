package jp.co.digitalvision.rkrkskb.db;

import java.io.Serializable;

/*
 * 勤怠テーブルエンティティクラス
 * 
 */
public class KintaiEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7073396938120184519L;

	// テーブル名
	public static final String TABLE_NAME = "KINTAI";
	
	// カラム名
	public static final String KBN = "KBN";					// 区分（１：出勤、２：退勤、３：休暇）
	public static final String INPUT_DATE = "INPUT_DATE";		// 入力日付（YYYYMMDD）
	public static final String HOUR = "HOUR";					// 入力時間
	public static final String MIN = "MIN";					// 入力分
	public static final String TEKIYOU = "TEKIYOU";			// 摘要
	public static final String BIKOU = "BIKOU";				// 備考

	// プロパティ
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
