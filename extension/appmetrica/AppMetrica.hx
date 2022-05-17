package extension.appmetrica;

import nme.JNI;
import nme.Lib;

class AppMetrica {

#if android
	private static var instance:AppMetrica = new AppMetrica();
#elseif ios
	
#end

	private static var initialized:Bool = false;

	public static var __init:AppMetrica->String->Void = JNI.createStaticMethod("org/haxe/extension/appmetrica/AppMetricaEx", "init", "(Lorg/haxe/lime/HaxeObject;Ljava/lang/String;)V");

	public static var __purchaseEvent:String->String->String->String->Void = JNI.createStaticMethod("org/haxe/extension/appmetrica/AppMetricaEx", "purchaseEvent", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");

	public static var __sendVideoEvent:String->String->String->String->Void = JNI.createStaticMethod("org/haxe/extension/appmetrica/AppMetricaEx", "sendVideoEvent", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");

	public static var __sendLevelEvent:String->String->Void = JNI.createStaticMethod("org/haxe/extension/appmetrica/AppMetricaEx", "sendLevelEvent", "(Ljava/lang/String;Ljava/lang/String;)V");



	public static function purchaseEvent(val:String, curr:String, id:String, type:String) {
#if android
		try {
			__purchaseEvent(val, curr, id, type);
		} catch(e:Dynamic) {
			trace("AppMetrica Error purchaseEvent: " + e);
		}
#elseif ios
		
#end
	}

	public static function sendLevelEvent(name:String, level:String) {
#if android
		try {
			__sendLevelEvent(name, level);
		} catch(e:Dynamic) {
			trace("AppMetrica Error sendLevelEvent: " + e);
		}
#elseif ios
		
#end
	}

	public static function sendVideoEvent(name:String, placement:String, result:String, connection:String) {
#if android
		try {
			__sendVideoEvent(name, placement, result, connection);
		} catch(e:Dynamic) {
			trace("AppMetrica Error sendVideoEvent: " + e);
		}
#elseif ios
		
#end
	}


	public static function init(appkey:String) {
		if (initialized) return;
		initialized = true;
#if android
		try {
			__init(instance, appkey);
		} catch(e:Dynamic) {
			trace("AppMetrica Error: "+e);
		}
#elseif ios
		
#end
	}

	private function new() {}

}
