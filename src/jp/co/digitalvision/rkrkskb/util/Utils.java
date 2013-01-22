package jp.co.digitalvision.rkrkskb.util;

/**
 * ユーティリティクラス
 * 
 */
public class Utils {

	/*
	 * 文字列がnullの場合、空文字として返却する
	 * 
	 * @param value 文字列
	 * @return 変換文字列
	 */
	public static String nullToEmpty(String value){
		if(value == null) return "";
		return value;
	}
	
	/*
	 * 文字列がブランクまたはnullかどうか判定処理を行う
	 * 
	 * @param value 判定文字列
	 * @return true：ブランクまたはnull、false：それ以外
	 */
	public static boolean isBlankOrNull(String value){
		
		if(value.equals("") || value == null){
			return true;
		} else {
			return false;
		}
	}
	
	
}
