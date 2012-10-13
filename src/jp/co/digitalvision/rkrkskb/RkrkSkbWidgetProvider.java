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

	/** �o�΃{�^���A�N�V�������� */
	private static final String SYUKKIN_CLICKED = "jp.co.digitalvision.SYUKKIN_CLICKED";
	/** �ދ΃{�^���A�N�V�������� */
	private static final String TAIKIN_CLICKED = "jp.co.digitalvision.TAIKIN_CLICKED";
	/** �x�Ƀ{�^���A�N�V�������� */
	private static final String KYUUKA_CLICKED = "jp.co.digitalvision.KYUUKA_CLICKED";

	/*
	 * AppWidget���쐬�����ۂɌĂ΂�܂��B
	 * ����AppWidget�𕡐��N�������ۂɂ́A����̂݌Ă΂�܂��B
	 * �S�̓I�ȏ��������K�v�ȏꍇ�͂����ɋL�q���܂�
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
	 * AppWidget���X�V�����ۂɌĂ΂�܂��B
	 * updatePeriodMillis���ōX�V�Ԋu��ݒ肵�Ă���΁A���̃^�C�~���O�ŌĂ΂�܂��B
	 * �܂��AAppWidget���N�������ۂɂ���x�Ă΂�܂��B
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
	 * AppWidget���폜���ꂽ�ۂɌĂ΂�܂��B
	 * �I���������K�v�ȏꍇ�͂����ɋL�q���܂��B
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
	 * AppWidget���S�č폜���ꂽ�ۂɌĂ΂�܂��B
	 * �S�̓I�ȏI���������K�v�ȏꍇ�͂����ɋL�q���܂��B
	 *
	 * @param context
	 */
	@Override
	public void onDisabled(Context context) {
		Log.v("RkrkSkbWidgetProvider", "onDisabled");
		super.onDisabled(context);
	}

	/*
	 * �A�N�V�������󂯎��AAppWidgetProvider�̊e���\�b�h�̌Ăяo�����������܂��B
	 *
	 * @param context
	 * @param intent �C���e���g
	 */
	@Override
	public void onReceive(Context context, Intent intent) {

		Log.v("RkrkSkbWidgetProvider", "onReceive");

		// Debug用
//		android.os.Debug.waitForDebugger();

		RemoteViews rview = new RemoteViews(context.getPackageName(), R.layout.widget_rkrk_skb);

		// ����Intent�Ɏ���A�N�V������ݒ�
		Intent skClickIntent = new Intent();
		Intent tkClickIntent = new Intent();
		Intent kkClickIntent = new Intent();

		skClickIntent.setAction(RkrkSkbWidgetProvider.SYUKKIN_CLICKED);
		tkClickIntent.setAction(RkrkSkbWidgetProvider.TAIKIN_CLICKED);
		kkClickIntent.setAction(RkrkSkbWidgetProvider.KYUUKA_CLICKED);

		// �y���f�B���O�C���e���g�쐬
		PendingIntent skPdIntent = PendingIntent.getBroadcast(context, 0, skClickIntent, 0);
		PendingIntent tkPdIntent = PendingIntent.getBroadcast(context, 0, tkClickIntent, 0);
		PendingIntent kkPdIntent = PendingIntent.getBroadcast(context, 0, kkClickIntent, 0);

		// �����[�g�r���[�ɃN���b�N�C�x���g��o�^
		rview.setOnClickPendingIntent(R.id.syukkin_button, skPdIntent);
		rview.setOnClickPendingIntent(R.id.taikin_button, tkPdIntent);
		rview.setOnClickPendingIntent(R.id.kyuuka_buttton, kkPdIntent);

		// �E�B�W�F�b�g�}�l�[�W����Context����擾����
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		ComponentName cn = new ComponentName(context, RkrkSkbWidgetProvider.class);
		manager.updateAppWidget(cn, rview);

		super.onReceive(context, intent);

		String action = intent.getAction();

		// �o�΃{�^������������
		if (RkrkSkbWidgetProvider.SYUKKIN_CLICKED.equals(action)) {
			// �o�Ώ���
			syukkinOnClicked(context);

		// �ދ΃{�^������������
		} else if(RkrkSkbWidgetProvider.TAIKIN_CLICKED.equals(action)){
			// �ދΏ���
			taikinOnClicked(context);

		// �x�Ƀ{�^������������
		} else if(RkrkSkbWidgetProvider.KYUUKA_CLICKED.equals(action)){
			// �x�ɏ���
			kyuukaOnClicked(context);

		}
	}

	/*
	 * �o�΃{�^������������
	 *
	 * @param context
	 */
	public void syukkinOnClicked(Context context) {

		Log.v("RkrkSkbWidgetProvider", "syukkinOnClicked start");

		// DB�X�V
		KintaiDao kintai = new KintaiDao(context);
		kintai.doSave(setKintaiEntityData(Constants.KBN_SYUKKIN));

		Log.v("RkrkSkbWidgetProvider", "syukkinOnClicked end");
	}

	/*
	 * �ދ΃{�^������������
	 *
	 * @param context
	 */
	public void taikinOnClicked(Context context) {

		Log.v("RkrkSkbWidgetProvider", "taikinOnClicked start");

		// DB�X�V
		KintaiDao kintai = new KintaiDao(context);
		kintai.doSave(setKintaiEntityData(Constants.KBN_TAIKIN));

		Log.v("RkrkSkbWidgetProvider", "taikinOnClicked end");
	}

	/*
	 * �x�Ƀ{�^������������
	 *
	 * @param context
	 */
	public void kyuukaOnClicked(Context context) {

		Log.v("RkrkSkbWidgetProvider", "kyuukaOnClicked start");

		// DB�X�V
		KintaiDao kintai = new KintaiDao(context);
		kintai.doSave(setKintaiEntityData(Constants.KBN_KYUUKA));

		Log.v("RkrkSkbWidgetProvider", "kyuukaOnClicked end");
	}

	/*
	 * �ΑӃG���e�B�e�B�f�[�^�ݒ�
	 *
	 * @param kbn �ΑӋ敪
	 * @return �ΑӃG���e�B�e�B
	 */
	private KintaiEntity setKintaiEntityData(String kbn) {

		// ���ݎ����擾�i�V�X�e����t�擾�iYYYYMMDD�`���j�j
		String strDate = DateUtil.getFormatDate("yyyyMMdd");

		KintaiEntity entity = new KintaiEntity();
		entity.setKbn(kbn);
		entity.setInputDate(strDate);

		// �x�ɈȊO�̏ꍇ�͎��ԁA�����Z�b�g
		if(!Constants.KBN_KYUUKA.equals(kbn)){
			entity.setHour(DateUtil.getFormatDate("HH"));
			entity.setMin(DateUtil.getFormatDate("mm"));
		// ����ȊO�̏ꍇ�̓u�����N
		} else {
			entity.setHour("");
			entity.setMin("");
		}
		entity.setBikou("");
		entity.setTekiyou("");

		return entity;
	}
}
