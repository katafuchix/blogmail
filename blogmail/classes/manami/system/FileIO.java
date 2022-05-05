/*
********************************************************************************
* �V�X�e���Fai-land i-mode live BBS for Queen 			                       *
*==============================================================================*
* �N���X�@�FFileIO                  �b���W���[���FFileIO.java                  *
* �T�v�@�@�F�t�@�C���^�f�B���N�g���֘A                                         *
* �쐬�@�@�F2004/04/16 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* �X�V�@�@�F2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode ���ڐA											   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.system;

/*-- �C���|�[�g --------------------------------------------------------------*/
import  java.io.*;

/*-- �N���X��` --------------------------------------------------------------*/
public class FileIO extends java.io.File {

	static    String     Classname = "FileIO";

	private   int        _MaxLength = 1024;

	  ////////////////////
	 // �R���X�g���N�^ //
	////////////////////
	public FileIO( String Pathname ) {
		super( Pathname );
	}

	  ////////////
	 // �R�s�[ //
	////////////
	public boolean copyTo( File file ) {

		boolean Result = false;

		// �R�s�[�����݃`�F�b�N
		if( ! exists() ) return( false );

		RandomAccessFile r = null;
		RandomAccessFile w = null;
		try {
			// �I�[�v��
			r = new RandomAccessFile( this, "rw" );
			w = new RandomAccessFile( file, "rw" );

			// �R�s�[
			byte Buf[] = new byte[_MaxLength];
			int  Length;
			while( ( Length = r.read( Buf, 0, _MaxLength ) ) > 0 ) {
				w.write( Buf, 0, Length );
			}
			// ����
			Result = true;
		} catch( Exception X ) {
			Static.Error.out( X );
		} finally {
			// �N���[�Y
			try {
				if( r != null ) { r.close(); r = null; }
				if( w != null ) { w.close(); w = null; }
			} catch( IOException Ex ) {}

		//return( Result );  //org ���ֈړ�

		}

		return( Result ); 
	}

	  ////////////////
	 // �p�X���A�� //
	////////////////
	static public String addPath( String BeforeString, String AfterString ) {

		String Separater = null;
		String ResultString;

		if( BeforeString != null ) {
			if( BeforeString.endsWith( "/" ) ) {
				Separater = "/";
			} else
			if( BeforeString.endsWith( "\\" ) ) {
				Separater = "\\";
			}
		}

		if( AfterString == null ) return( BeforeString );

		if( AfterString.startsWith( "/" ) ) {
			if( Separater == null ) {
				ResultString = BeforeString + AfterString;
			} else {
				ResultString = BeforeString + AfterString.substring( 1 );
			}
		} else
		if( AfterString.startsWith( "\\" ) ) {
			if( Separater == null ) {
				ResultString = BeforeString + AfterString;
			} else {
				ResultString = BeforeString + AfterString.substring( 1 );
			}
		} else {
			if( Separater == null ) {
				ResultString = BeforeString + "/" + AfterString;
			} else {
				ResultString = BeforeString + AfterString;
			}
		}

		return( ResultString );
	}

}
