package jp.co.digitalvision.rkrkskb;

import jp.co.digitalvision.rkrkskb.db.KintaiDao;
import jp.co.digitalvision.rkrkskb.db.KintaiEntity;
import jp.co.digitalvision.rkrkskb.util.Constants;
import jp.co.digitalvision.rkrkskb.util.DateUtil;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class RkrkSkbWidgetProvider extends AppWidgetProvider {

	/** 出勤ボタンアクション名称 */
	private static final String SYUKKIN_CLICKED = "jp.co.digitalvision.SYUKKIN_CLICKED";
	/** 退勤ボタンアクション名称 */
	private static final String TAIKIN_CLICKED = "jp.co.digitalvision.TAIKIN_CLICKED";
	/** 休暇ボタンアクション名称 */
	private static final String KYUUKA_CLICKED = "jp.co.digitalvision.KYUUKA_CLICKED";

	/*
	 * AppWidgetが作成される際に呼ばれます。
	 * 同じAppWidgetを複数起動した際には、初回のみ呼ばれます。
	 * 全体的な初期化処理が必要な場合はここに記述します
	 * 
	 * @param context
	 */
	@Override
	public void onEnabled(Context context) {
		Log.v("RkrkSkbWidgetProvider", "onEnabled");

		System.out.println("onEnabled");

		super.onEnabled(context);
	}

	/*
	 * AppWidgetが更新される際に呼ばれます。
	 * updatePeriodMillis等で更新間隔を設定していれば、そのタイミングで呼ばれます。
	 * また、AppWidgetを起動した際にも一度呼ばれます。
	 * 
	 * @param context
	 * @param appWidgetManager
	 * @param appWidgetIds
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.v("RkrkSkbWidgetProvider", "onUpdate start");

//		super.onUpdate(context, appWidgetManager, appWidgetIds);

		Log.v("RkrkSkbWidgetProvider", "onUpdate end");
	}

	/*
	 * AppWidgetが削除された際に呼ばれます。
	 * 終了処理が必要な場合はここに記述します。
	 * 
	 * @param context
	 * @param appWidgetIds
	 */
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.v("RkrkSkbWidgetProvider", "onDeleted");
		super.onDeleted(context, appWidgetIds);
	}

	/*
	 * AppWidgetが全て削除された際に呼ばれます。
	 * 全体的な終了処理が必要な場合はここに記述します。
	 * 
	 * @param context
	 */
	@Override
	public void onDisabled(Context context) {
		Log.v("RkrkSkbWidgetProvider", "onDisabled");
		super.onDisabled(context);
	}

	/*
	 * アクションを受け取り、AppWidgetProviderの各メソッドの呼び出しを処理します。
	 * 
	 * @param context
	 * @param intent インテント
	 */
	@Override
	public void onReceive(Context context, Intent intent) {

		Log.v("RkrkSkbWidgetProvider", "onReceive");

		// Debug用
//		android.os.Debug.waitForDebugger();

		RemoteViews rview = new RemoteViews(context.getPackageName(), R.layout.widget_rkrk_skb);

		// 自作Intentに自作アクションを設定
		Intent skClickIntent = new Intent();
		Intent tkClickIntent = new Intent();
		Intent kkClickIntent = new Intent();

		skClickIntent.setAction(RkrkSkbWidgetProvider.SYUKKIN_CLICKED);
		tkClickIntent.setAction(RkrkSkbWidgetProvider.TAIKIN_CLICKED);
		kkClickIntent.setAction(RkrkSkbWidgetProvider.KYUUKA_CLICKED);

		// ペンディングインテント作成
		PendingIntent skPdIntent = PendingIntent.getBroadcast(context, 0, skClickIntent, 0);
		PendingIntent tkPdIntent = PendingIntent.getBroadcast(context, 0, tkClickIntent, 0);
		PendingIntent kkPdIntent = PendingIntent.getBroadcast(context, 0, kkClickIntent, 0);

		// リモートビューにクリックイベントを登録
		rview.setOnClickPendingIntent(R.id.syukkin_button, skPdIntent);
		rview.setOnClickPendingIntent(R.id.taikin_button, tkPdIntent);
		rview.setOnClickPendingIntent(R.id.kyuuka_buttton, kkPdIntent);

		// ウィジェットマネージャをContextから取得する
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		ComponentName cn = new ComponentName(context, RkrkSkbWidgetProvider.class);
		manager.updateAppWidget(cn, rview);

		super.onReceive(context, intent);

		String action = intent.getAction();

		// 出勤ボタン押下時判定
		if (RkrkSkbWidgetProvider.SYUKKIN_CLICKED.equals(action)) {
			// 出勤処理
			syukkinOnClicked(context);

		// 退勤ボタン押下時判定
		} else if(RkrkSkbWidgetProvider.TAIKIN_CLICKED.equals(action)){
			// 退勤処理
			taikinOnClicked(context);

		// 休暇ボタン押下時判定
		} else if(RkrkSkbWidgetProvider.KYUUKA_CLICKED.equals(action)){
			// 休暇処理
			kyuukaOnClicked(context);

		}
	}

	/*
	 * 出勤ボタン押下時処理
	 * 
	 * @param context
	 */
	public void syukkinOnClicked(Context context) {

		Log.v("RkrkSkbWidgetProvider", "syukkinOnClicked start");

		// DB更新
		KintaiDao kintai = new KintaiDao(context);
		long status = kintai.doSave(setKintaiEntityData(Constants.KBN_SYUKKIN));
		if(status != 0){
			Log.v("RkrkSkbWidgetProvider", "DB処理にてエラーが発生しました[ ERROR_CODE = " + status + " ]");
		}
		
		Log.v("RkrkSkbWidgetProvider", "syukkinOnClicked end");
	}

	/*
	 * 退勤ボタン押下時処理
	 * 
	 * @param context
	 */
	public void taikinOnClicked(Context context) {

		Log.v("RkrkSkbWidgetProvider", "taikinOnClicked start");

		// DB更新
		KintaiDao kintai = new KintaiDao(context);
		kintai.doSave(setKintaiEntityData(Constants.KBN_TAIKIN));

		Log.v("RkrkSkbWidgetProvider", "taikinOnClicked end");
	}

	/*
	 * 休暇ボタン押下時処理
	 * 
	 * @param context
	 */
	public void kyuukaOnClicked(Context context) {

		Log.v("RkrkSkbWidgetProvider", "kyuukaOnClicked start");

		// DB更新
		KintaiDao kintai = new KintaiDao(context);
		kintai.doSave(setKintaiEntityData(Constants.KBN_KYUUKA));

		Log.v("RkrkSkbWidgetProvider", "kyuukaOnClicked end");
	}

	/*
	 * 勤怠エンティティデータ設定
	 * 
	 * @param kbn 勤怠区分
	 * @return 勤怠エンティティ
	 */
	private KintaiEntity setKintaiEntityData(String kbn) {

		// 現在時刻取得（システム日付取得（YYYYMMDD形式））
		String strDate = DateUtil.getFormatDate("yyyyMMdd");

		KintaiEntity entity = new KintaiEntity();
		entity.setKbn(kbn);
		entity.setInputDate(strDate);

		// 休暇以外の場合は時間、分をセット
		if(!Constants.KBN_KYUUKA.equals(kbn)){
			entity.setHour(DateUtil.getFormatDate("HH"));
			entity.setMin(DateUtil.getFormatDate("mm"));
		// それ以外の場合はブランク
		} else {
			entity.setHour("");
			entity.setMin("");
		}
		entity.setBikou("");
		entity.setTekiyou("");

		return entity;
	}
}
