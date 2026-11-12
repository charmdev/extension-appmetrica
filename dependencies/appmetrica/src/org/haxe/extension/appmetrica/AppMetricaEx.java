package org.haxe.extension.appmetrica;

import android.util.Log;
import org.haxe.extension.Extension;
import org.haxe.lime.HaxeObject;
import android.opengl.GLSurfaceView;
import java.util.Currency;

import io.appmetrica.analytics.AppMetrica;
import io.appmetrica.analytics.AppMetricaConfig;
import io.appmetrica.analytics.Revenue;


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

		AppMetricaConfig config = AppMetricaConfig.newConfigBuilder(api_key).withSessionTimeout(300).withLogs().build();
		AppMetrica.activate(Extension.mainActivity, config);
		AppMetrica.enableActivityAutoTracking(Extension.mainActivity.getApplication());
		
	}

	public static void purchaseEvent(String price, String curr, String id, String type)
	{
		Revenue revenue = Revenue.newBuilder(Math.round(Double.parseDouble(price) * 1000000), Currency.getInstance(curr))
			.withProductID(id)
			.withQuantity(1)
			.build();
			
		AppMetrica.reportRevenue(revenue);

		String eventParameters = "{\"inapp_id\":" + id +
								 ", \"currency\":" + curr +
								 ", \"price\":" + price +
								 ", \"inapp_type\":" + type + "}";

		AppMetrica.reportEvent("payment_succeed", eventParameters);

		AppMetrica.sendEventsBuffer();

		Log.d(TAG, "payment_succeed:" + eventParameters);
	}

	public static void sendVideoEvent(String name, String placement, String result, String connection)
	{
		String eventParameters = "{\"ad_type\":\"rewarded\"" +
									", \"placement\":" + placement +
									", \"result\":" + result +
									", \"connection\":\"true\"}";

		AppMetrica.reportEvent(name, eventParameters);

		AppMetrica.sendEventsBuffer();

		Log.d(TAG, name + ":" + eventParameters);
	}

	public static void sendLevelEvent(String name, String level)
	{
		Integer lvl = Integer.parseInt(level)-1;
		String eventParameters_start = "{\"level_number\":" + level + "}";
		String eventParameters_finish = "{\"level_number\":" + Integer.toString(lvl) + "}";
		AppMetrica.reportEvent("level_start", eventParameters_start);
		AppMetrica.reportEvent("level_finish", eventParameters_finish);

		AppMetrica.sendEventsBuffer();

		Log.d(TAG, "levelSF:" + level);
	}

}
