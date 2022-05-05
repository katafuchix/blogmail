/*
********************************************************************************
* システム： ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* クラス　：Encoder                  ｜モジュール：Encoder.java                *
* 概要　　：エンコード置換クラス                                               *
* 作成　　：2004/04/20 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.util;

/*-- インポート --------------------------------------------------------------*/


/*-- クラス定義 --------------------------------------------------------------*/


public class Encoder {
	private static String defaultEncode = "EUC_JP";
	
	public Encoder() {}
	/**
	* デフォルト(EUC)で
	*/
	public static String encode(String str) {
		if (str != null) {
			return encode(str, defaultEncode);
		} else {
			return str;
		}
	}
	/**
	* 配列対応
	*/
	public static String[] encode(String[] str) {
		if (str == null) return str;
		String[] tmp = new String[str.length];
		for (int i = 0; i < str.length; i++) {
			tmp[i] = encode(str[i]);
		}
		return tmp;
	}
	/**
	* 配列対応
	*/
	public static String[] encode(String[] str,String encode) {
		if (str == null) return str;
		String[] tmp = new String[str.length];
		for (int i = 0; i < str.length; i++) {
			tmp[i] = encode(str[i],encode);
		}
		return tmp;
	}
	
	/**
	* 指定エンコードで
	*/
	public static String encode(String str,String encode) {
		if (str == null) return str;
		String tmp = "";
		try {
			tmp = new String( str.getBytes("ISO8859_1"), encode );
		} catch (Exception ioe) {
		}
		return tmp;
	}
}
