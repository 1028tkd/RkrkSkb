package jp.co.digitalvision.rkrkskb.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 時間関係共通クラス
 * 
 * <PRE>
 * | Ver |   日付   |       更新者       |               変更内容・コメント
 * |-----|----------|--------------------|-------------------------------------------
 * | 1.0 |2012/10/01| T.Takada           | 新規作成
 * </PRE>
 * 
 * @version
 * @author T.Takada
 */
public class DateUtil {
	
	/** タイムスタンプフォーマット */
	private final static SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyyMMddHHmm"); 
	
	/**
	 * 現在のタイムスタンプを"yyyyMMddHHmm"形式の文字列で返す
	 * 
	 * @return 文字列
	 */
	public static String getYyyyMMddHHmm() {
		return yyyyMMddHHmm.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 現在のタイムスタンプをフォーマット形式の文字列で返す
	 * 
	 * @param format フォーマット形式
	 * @return 文字列
	 */
	public static String getFormatDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 現在日付取得
	 * 
	 * @return 日付(時、分、秒、ミリ秒は０)
	 */
	public static java.sql.Date getCurrDate() {
		java.sql.Date currDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		return java.sql.Date.valueOf(currDate.toString());
	}
}
