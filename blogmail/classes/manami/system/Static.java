/*
********************************************************************************
* �V�X�e���Fai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* �N���X�@�FStatic                  �b���W���[���FStatic.java                  *
* �T�v�@�@�F���O�o�͂Ȃ�                                                       *
* �쐬�@�@�F2004/04/16 K.katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* �X�V�@�@�F2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode ���ڐA											   *
*		  : 2004/07/17 K.Katafuchi(affinity)								   *
*			Tommy�ǉ�														   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.system;

/*-- �C���|�[�g --------------------------------------------------------------*/

/*-- �N���X��` --------------------------------------------------------------*/
public class Static {

	static        String  _MyName   = "Static";

	static public Property Property = new Property();
	static public String   HomeDir  = Property.getHomeDir();

	static public Log      Log      = new Log();
	static public ErrorLog Error    = new ErrorLog();
	static public Trace    Trace    = new Trace();

	static public Debug    Debug    = new Debug();

   	static public Trace    XXX     = new Trace( "XXX" );

	static public Tommy    Tommy    = new Tommy();


	  ////////////////////
	 // �R���X�g���N�^ //
	////////////////////
	public Static() {
	}

}
