package org.haxe.extension.appmetrica;

import android.util.Log;
import org.haxe.extension.Extension;
import org.haxe.lime.HaxeObject;
import android.opengl.GLSurfaceView;
import java.util.Currency;

import com.yandex.metrica.YandexMetricaConfig;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.Revenue;


public class AppMetricaEx extends Extension
{
	protected AppMetricaEx () { }

	protected static HaxeObject _callback = null;
	protected static String TAG = "AppMetrica";
	
	//protected static String apiKey = null;
	

	public static void init(HaxeObject callback, String api_key)
	{
		_callback = callback;
		//apiKey = api_key;

		Log.d(TAG, api_key);

		YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(api_key).withSessionTimeout(300).withLogs().build();
		YandexMetrica.activate(Extension.mainActivity, config);
		YandexMetrica.enableActivityAutoTracking(Extension.mainActivity.getApplication());
		
	}

	public static void purchaseEvent(String price, String curr, String id, String type)
	{
		Revenue revenue = Revenue.newBuilder(Double.parseDouble(price), Currency.getInstance(curr))
			.withProductID(id)
			.withQuantity(1)
			.build();
			
		YandexMetrica.reportRevenue(revenue);

		String eventParameters = "{\"inapp_id\":" + id +
								 ", \"currency\":" + curr +
								 ", \"price\":" + price +
								 ", \"inapp_type\":" + type + "}";

		YandexMetrica.reportEvent("payment_succeed", eventParameters);

		YandexMetrica.sendEventsBuffer();

		Log.d(TAG, "payment_succeed:" + eventParameters);
	}

	public static void sendVideoEvent(String name, String placement, String result, String connection)
	{
		String eventParameters = "{\"ad_type\":\"rewarded\"" +
									", \"placement\":" + placement +
									", \"result\":" + result +
									", \"connection\":\"true\"}";

		YandexMetrica.reportEvent(name, eventParameters);

		YandexMetrica.sendEventsBuffer();

		Log.d(TAG, name + ":" + eventParameters);
	}

	public static void sendLevelEvent(String name, String level)
	{
		Integer lvl = Integer.parseInt(level)-1;
		String eventParameters_start = "{\"level_number\":" + level + "}";
		String eventParameters_finish = "{\"level_number\":" + Integer.toString(lvl) + "}";
		YandexMetrica.reportEvent("level_start", eventParameters_start);
		YandexMetrica.reportEvent("level_finish", eventParameters_finish);

		YandexMetrica.sendEventsBuffer();

		Log.d(TAG, "levelSF:" + level);
	}

}
