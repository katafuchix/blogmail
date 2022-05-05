/*
********************************************************************************
* �V�X�e���F ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* �N���X�@�FChatLog                 �b���W���[���FChatLog.java                 *
* �T�v�@�@�F�`���b�g���O�t�@�C���Ǘ��N���X                                     *
* �ڐA�@�@�F2004/07/20 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* �X�V�@�@�F2004/07/03 K.Katafuchi(affinity) �Ǘ���ʑΉ�					   *
*		   : 2004/07/17 K.Katafuchi(affinity) ���s�R�[�h�Ή�				   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.talk;

/*-- �C���|�[�g --------------------------------------------------------------*/
import  java.io.*;
import  java.sql.*;
import  javax.servlet.*;
import  javax.servlet.jsp.*;	
import  manami.system.*;
import  manami.util.*;

/*-- �N���X��` --------------------------------------------------------------*/
public class ChatLog {

	static    String     Classname = "ChatLog";

	static    boolean    _IsGetProperty = false;
	static    String     _Directory;
	static    String     _Encode;

	private   String     _LogDirectory;
	private   String     _LogFilename  = null;
	private   String     _LogExtension = ".log";
	private   String     _LogEncode;
	private   boolean    _LogIsOut     = true;
	private   boolean    _LogIsAddDate = true;
	private   String     _LogSubDir     = "";

	private   OutputStreamWriter _Out;
	private   BufferedWriter     _Buf;

	protected ServletRequest     _Request    = null;
	private   String             _Clientname = "";

	DateTime d = new DateTime();

	  ////////////////////
	 // �R���X�g���N�^ //
	////////////////////
	public ChatLog() {
		_initialize();
	}
	public ChatLog( String SubDir ) {
		_LogSubDir = SubDir;
		_initialize();
	}

	  ////////////
	 // ������ //
	////////////
	private void _initialize() {

		// �v���p�e�B�擾
		if( ! _IsGetProperty ) {
			Property P = new Property();
			P.open( Classname );
			_Directory = FileIO.addPath( Static.HomeDir, P.getString( "LogDirectory" ) );
//			_Directory = "C:/Program Files/Apache Software Foundation/Tomcat 5.0/webapps/chat/";
			_Encode    = P.getString( "Encode" );

			_IsGetProperty = true;
		}

		// �e��ݒ�
		String Pathname = _Directory;
		if( ! _LogSubDir.equals( "" ) ) Pathname += _LogSubDir + "/";
		setDirectory( Pathname );
		setEncode( _Encode );

		_LogFilename = "ChatLog" + d.getString();
	}

	  ////////////////////////
	 // �N���C�A���g���擾 //
	////////////////////////
	public String getClientname() {
		return( _Clientname );
	}

	  //////////////////////
	 // �f�B���N�g���擾 //
	//////////////////////
	public String getDirectory() {
		return( _LogDirectory );
	}

	  //////////////////////
	 // �f�B���N�g���ݒ� //
	//////////////////////
	public void setDirectory( String Directory ) {
		_LogDirectory = Directory;
	}

	  ////////////////////
	 // �t�@�C�����ݒ� //
	////////////////////
	public void setFilename( String Filename ) {
		_LogFilename = Filename;
	}

	  ////////////////////
	 // �t�@�C�����擾 //
	////////////////////
	public String getFilename() {
		return ( _LogFilename +  _LogExtension);
	}


	  ////////////////
	 // �g���q�ݒ� //
	////////////////
	public void setExtension( String Extension ) {
		_LogExtension = Extension;
	}

	  //////////////////////////
	 // �T�u�f�B���N�g���ݒ� //
	//////////////////////////
	public void setSubDir( String SubDir ) {
		_LogSubDir = SubDir;
		_initialize();
	}

	  ////////////////////
	 // �G���R�[�h�ݒ� //
	////////////////////
	public void setEncode( String Encode ) {
		_LogEncode = Encode;
	}

	  ////////////////////
	 // �o�̓��[�h�ݒ� //
	////////////////////
	public void setIsOut( boolean IsOut ) {
		_LogIsOut = IsOut;
	}

	  //////////////////
	 // ���t�o�͐ݒ� //
	//////////////////
	public void setIsAddDate( boolean IsAddDate ) {
		_LogIsAddDate = IsAddDate;
	}

	  ////////////////////
	 // ���N�G�X�g�ݒ� //
	////////////////////
	public void setRequest( ServletRequest Request ) {
		_Request    = Request;
		_Clientname = Request.getRemoteHost();
	}

	  //////////
	 // �o�� //
	//////////
	public void out( String OutString ) {
		out( OutString, false );
	}

	public synchronized void out( String OutString, boolean isStandardOut ) {
	//public void out( String OutString, boolean isStandardOut ) {

		// ��o�̓��[�h�`�F�b�N
		if( ! _LogIsOut ) return;

		try {
			// �f�B���N�g���쐬
			String Pathname = _LogDirectory;
			File f = new File( Pathname );
			f.mkdirs();

			// ���O�t�@�C���I�[�v��
			if( _LogFilename != null ) Pathname += _LogFilename;
			else                       Pathname += "ChatLog" + d.getString();
			Pathname += _LogExtension;

			_Out = new OutputStreamWriter( new FileOutputStream( Pathname, true ), _LogEncode );
			_Buf = new BufferedWriter( _Out );

			// ���O�ҏW
			String DateString = "";
			//if( _LogIsAddDate ) DateString = d.getString( "yyyy/MM/dd HH:mm:ss" ) + " ";
			//if( _LogIsAddDate ) DateString =  " " + "(" + d.getString( "HH:mm" ) + ")";

//chg 2004.07.03
			if( _LogIsAddDate ) DateString =  " " + "(" + d.getString( "HH:mm" ) + ")" ;


//add 2004.07.17
			OutString = OutString.replaceAll("\n"," ");
			OutString = OutString.replaceAll("\r"," ");

			// �W���o��
			if( isStandardOut ) System.out.println( OutString );
			// ���O�t�@�C���o��
			//_Buf.write( DateString + OutString );

 			_Buf.write( OutString.substring(0,OutString.lastIndexOf(",")) + DateString 
						+ OutString.substring(OutString.lastIndexOf(","),OutString.length()));

			//_Buf.write( OutString + DateString );
			_Buf.newLine();
		} catch ( Exception Ex ) {
			System.out.println( "Log: unexpected error." );
		} finally {
			// ���O�t�@�C���N���[�Y
			try {
				if( _Buf != null ) _Buf.close();
				if( _Out != null ) _Out.close();
			} catch( IOException Ex ) {}
		}

	}
	  //////////////////////////
	 // �G���[���b�Z�[�W�o�� //
	//////////////////////////
	public void out( Exception X ) {
		out( X, false );
	}
	public void out( Exception X, boolean isStandardOut ) {
		// �G���[���b�Z�[�W�o��
		out( X.toString(), isStandardOut );
        String Message = X.getMessage();
		if( Message != null) out( Message, isStandardOut );
		StringWriter StringOut = new StringWriter();
		PrintWriter  PrintOut  = new PrintWriter( StringOut );
		X.printStackTrace( PrintOut );
        out( StringOut.toString(), isStandardOut );
	}
	public void out( SQLException X ) {
		out( X, false );
	}
	public void out( SQLException X, boolean isStandardOut ) {
		// �G���[���b�Z�[�W�擾
		String ErrorString = "";
		for( SQLException SX = X; SX != null; SX = SX.getNextException() ) {
			ErrorString += SX.toString() + "\n";
		}
		// �G���[���b�Z�[�W�o��
		out( ErrorString, isStandardOut );
        String Message = X.getMessage();
		if( Message != null) out( Message, isStandardOut );
		StringWriter StringOut = new StringWriter();
		PrintWriter  PrintOut  = new PrintWriter( StringOut );
		X.printStackTrace( PrintOut );
        out( StringOut.toString(), isStandardOut );
	}


}
