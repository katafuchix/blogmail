/*
********************************************************************************
* �V�X�e���F ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* �N���X�@�FEncoder                  �b���W���[���FEncoder.java                *
* �T�v�@�@�F�G���R�[�h�u���N���X                                               *
* �쐬�@�@�F2004/04/20 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* �X�V�@�@�F2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode ���ڐA											   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.util;

/*-- �C���|�[�g --------------------------------------------------------------*/


/*-- �N���X��` --------------------------------------------------------------*/


public class Encoder {
	private static String defaultEncode = "EUC_JP";
	
	public Encoder() {}
	/**
	* �f�t�H���g(EUC)��
	*/
	public static String encode(String str) {
		if (str != null) {
			return encode(str, defaultEncode);
		} else {
			return str;
		}
	}
	/**
	* �z��Ή�
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
	* �z��Ή�
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
	* �w��G���R�[�h��
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
