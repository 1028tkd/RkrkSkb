package jp.co.digitalvision.rkrkskb.db;

import java.io.Serializable;

/*
 * 業務報告テーブルエンティティクラス
 */
public class GymHkkEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7073396938120184519L;

	// テーブル名
	public static final String TABLE_NAME = "GYMHKK";
	
	// カラム名
	public static final String INPUT_DATE = "INPUT_DATE";
	public static final String SOUKATSU = "SOUKATSU";
	public static final String POINT = "POINT";
	public static final String BIKOU = "BIKOU";

	// プロパティ
	private String inputDate = null;
	private String soukatsu = null;
	private String point = null;
	private String bikou = null;

	public String getInputDate() {
		return inputDate;
	}
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	public String getSoukatsu() {
		return soukatsu;
	}
	public void setSoukatsu(String soukatsu) {
		this.soukatsu = soukatsu;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getBikou() {
		return bikou;
	}
	public void setBikou(String bikou) {
		this.bikou = bikou;
	}
	
}
