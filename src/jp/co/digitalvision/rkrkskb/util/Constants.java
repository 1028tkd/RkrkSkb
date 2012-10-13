package jp.co.digitalvision.rkrkskb.util;

import java.io.Serializable;

/**
 * 定数クラス
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
public class Constants implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8534281924307466275L;
	
	/** 区分： "1"（出勤） */
	public static final String KBN_SYUKKIN = "1";
	/** 区分： "2"（退勤） */
	public static final String KBN_TAIKIN = "2";
	/** 区分： "3"（休暇） */
	public static final String KBN_KYUUKA = "3";

}
