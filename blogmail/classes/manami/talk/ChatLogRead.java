/*
********************************************************************************
* �V�X�e���F ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* �N���X�@�FChatLogRead            �b���W���[���FChatLogRead.java              *
* �T�v�@�@�F�`���b�g���O�t�@�C���Ǘ��N���X                                     *
* �ڐA�@�@�F2004/07/20 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* �X�V�@�@�F2004/07/03 K.katafuchi(affinity) �Ǘ���ʑΉ�					   *
*			2004/07/18 K.Katafuchi(affinity) HTML��ʑΉ�					   *
*		   : 2004/07/17 K.Katafuchi(affinity) ���s�R�[�h�Ή�				   *
*		   : 2004/08/18 K.Katafuchi(affinity) �����ޏo�Ή�					   *
*		   : 2004/08/18 K.Katafuchi(affinity) HTML�G�����Ή�				   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.talk;

/*-- �C���|�[�g --------------------------------------------------------------*/
import  java.io.*;
import  java.util.*;
import  javax.servlet.*;
import  javax.servlet.jsp.*;	
import  manami.system.*;
import  manami.util.*;

/*-- �N���X��` --------------------------------------------------------------*/
public class ChatLogRead {


	static		String     Classname = "ChatLogRead";
	static		String     _Encode;
	static		String	   _MaxLine;
	static		String	   _FileName;
	static		String 	   _LogData;
	static		String 	   _Directory; 
	static		boolean    _IsGetProperty = false;

	static		Hashtable _chat = new Hashtable();

	static		String	   _uid;		//add 2004.08.18
	static		String 	   _EmojiHome;  //add 2004.08.18

	static String[] emojiS = new String[]{  //add 2004.08.18
	  "&#63647;","&#63648;","&#63649;","&#63650;","&#63651;","&#63652;","&#63653;","&#63654;",
	  "&#63655;","&#63656;","&#63657;","&#63658;","&#63659;","&#63660;","&#63661;","&#63662;",
	  "&#63663;","&#63664;","&#63665;","&#63666;","&#63667;","&#63668;","&#63669;","&#63670;",
	  "&#63671;","&#63672;","&#63673;","&#63674;","&#63675;","&#63676;","&#63875;","&#63678;",
	  "&#63679;","&#63680;","&#63681;","&#63682;","&#63683;","&#63684;","&#63685;","&#63686;",
	  "&#63687;","&#63688;","&#63689;","&#63690;","&#63691;","&#63692;","&#63693;","&#63694;",
	  "&#63695;","&#63696;","&#63697;","&#63698;","&#63699;","&#63700;","&#63701;","&#63702;",
	  "&#63703;","&#63704;","&#63705;","&#63706;","&#63707;","&#63708;","&#63709;","&#63710;",
	  "&#63711;","&#63712;","&#63713;","&#63714;","&#63715;","&#63716;","&#63717;","&#63718;",
	  "&#63719;","&#63720;","&#63721;","&#63722;","&#63723;","&#63724;","&#63725;","&#63726;",
	  "&#63727;","&#63728;","&#63729;","&#63730;","&#63731;","&#63732;","&#63733;","&#63734;",
	  "&#63735;","&#63736;","&#63737;","&#63738;","&#63739;","&#63740;","&#63808;","&#63809;",
	  "&#63810;","&#63811;","&#63812;","&#63813;","&#63814;","&#63815;","&#63816;","&#63817;",
	  "&#63677;","&#63858;","&#63859;","&#63860;","&#63861;","&#63862;","&#63863;","&#63864;",
	  "&#63865;","&#63866;","&#63867;","&#63868;","&#63869;","&#63870;","&#63872;","&#63873;",
	  "&#63874;","&#63876;","&#63877;","&#63878;","&#63879;","&#63880;","&#63881;","&#63882;",
	  "&#63883;","&#63884;","&#63885;","&#63886;","&#63887;","&#63888;","&#63889;","&#63890;",
	  "&#63891;","&#63892;","&#63893;","&#63894;","&#63895;","&#63896;","&#63897;","&#63898;",
	  "&#63899;","&#63900;","&#63901;","&#63902;","&#63903;","&#63904;","&#63905;","&#63906;",
	  "&#63907;","&#63908;","&#63909;","&#63910;","&#63911;","&#63912;","&#63913;","&#63914;",
	  "&#63915;","&#63916;","&#63917;","&#63918;","&#63919;","&#63920;"};

/*
//��L�̑Ή��\
	String[] emmoji = new String[]{
	  "��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��",
	  "��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��",
	  "��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��",
	  "��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��",
	  "��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��",
	  "��","��","��","��","�@","�A","�B","�C","�D","�E","�F","�G","�H","�I","��","�r","�s","�t",
	  "�u","�v","�w","�x","�y","�z","�{","�|","�}","�~","��","��","��","��","��","��","��","��",
	  "��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��",
	  "��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��",
	  "��","��","��","��"};
*/

	  ////////////////////
	 // �R���X�g���N�^ //
	////////////////////
	public ChatLogRead() {
		_initialize();
	}


	  ////////////
	 // ������ //
	////////////
	private void _initialize() {

		if( ! _IsGetProperty ) {
			Property P = new Property();
			P.open( Classname );
			_Encode    = P.getString( "Encode" );
			_MaxLine   = P.getString( "Line" );
			_EmojiHome = P.getString( "EmojiHome" );

			_IsGetProperty = true;
		}

		_LogData = "";
		_chat.clear();
	}


	  ////////////////////
	 // �G���R�[�h�ݒ� //
	////////////////////
	public void setEncode( String Encode ) {
		_Encode = Encode;
	}

	  //////////////////////
	 // �f�B���N�g���ݒ� //
	//////////////////////
	public void setDirectory( String Directory ) {
		_Directory = Directory;
	}

	  ////////////////////
	 // �t�@�C�����ݒ� //
	////////////////////
	public void setFilename( String Filename ) {
		_FileName = Filename;
	}

	  /////////////
	 // UID�ݒ� //
	/////////////	add 2004.08.18
	public void setUid( String Uid ) {
		_uid = Uid;
	}

	  //////////
	 // �Ǐo //
	//////////
	public synchronized String getLogData() {
					
		ChatLog _LogFile = new ChatLog();
		if( _Directory == null ) _Directory = _LogFile.getDirectory();
		if( _FileName == null  ) _FileName  = _LogFile.getFilename();

		String LogNameFull = _Directory + _FileName;

//Static.Trace.out( "LogNameFull : " + LogNameFull );

			try {

				/* FileInputStream���g���ăt�@�C�����J�� */
				//BufferedReader reader = new BufferedReader(new InputStreamReader( 
				//	 new FileInputStream( LogNameFull )));
				BufferedReader reader = new BufferedReader(new InputStreamReader( 
					 new FileInputStream( LogNameFull ), _Encode));

				/* �t�@�C���̓��e���P�s�Âǂ� */
				String line;
				int h = 0;
				int max = Integer.parseInt( _MaxLine );

				/* Hashtable�ɔ������i�[���čŐV�̂��̂���\���ł���悤�ɂ��� */

				while ((line=reader.readLine()) != null ) {

//add 2004.07.17
						line = line.replaceAll("\n"," ");
						line = line.replaceAll("\r"," ");

					if( line.substring(line.length()-1,line.length()).equals("1")){
						_chat.put( Integer.toString(h), line.substring(0,line.lastIndexOf(",") ));
						h++;
					}				//if add 2004.07.03
						//_chat.put( Integer.toString(h), line );
						//h++;
				}
								
				if( h < max ) max = h;
			
				for( int m=0; m<max ;m++ ){
					if(!_chat.get(Integer.toString(_chat.size()-1-m)).equals("") &&
						!_chat.get(Integer.toString(_chat.size()-1-m)).equals("null") ){

						String str = (String)_chat.get(Integer.toString(_chat.size()-1-m));
						if( str.indexOf("getout") !=-1 ){ //�����ޏo�̌�傪�������ꍇ
							if( str.indexOf(_uid) !=-1 ){	// add 2004.08.18
								_LogData += "\n" + _chat.get(Integer.toString(_chat.size()-1-m));	
								//�����ޏo�̌��� uid=getout �ŗ���			
							}
						}else{

							_LogData += "\n" + _chat.get(Integer.toString(_chat.size()-1-m));
						}
					}
				}

//Static.Trace.out( "_LogData : " + _LogData );


			}
			catch (Exception ex) {
				Static.Error.out( ex.toString() );
			}

				_chat.clear(); //�Z�b�V�����̒��Ŏc�邩������Ȃ��̂ŃN���A���Ă���

			if( _LogData == null ) _LogData = "";
			return _LogData;

	}



	  /////////////////
	 // �Ǐo HTML�p //
	/////////////////
	public synchronized String getLogDataHtml() {
					
		ChatLog _LogFile = new ChatLog();
		if( _Directory == null ) _Directory = _LogFile.getDirectory();
		if( _FileName == null  ) _FileName  = _LogFile.getFilename();

		String LogNameFull = _Directory + _FileName;

//Static.Trace.out( "LogNameFull : " + LogNameFull );

			try {

				/* FileInputStream���g���ăt�@�C�����J�� */
				//BufferedReader reader = new BufferedReader(new InputStreamReader( 
				//	 new FileInputStream( LogNameFull )));
				BufferedReader reader = new BufferedReader(new InputStreamReader( 
					 new FileInputStream( LogNameFull ), _Encode));

				/* �t�@�C���̓��e���P�s�Âǂ� */
				String line;
				int h = 0;
				int max = Integer.parseInt( _MaxLine );

				/* Hashtable�ɔ������i�[���čŐV�̂��̂���\���ł���悤�ɂ��� */

				while ((line=reader.readLine()) != null ) {

//add 2004.07.17
						line = line.replaceAll("\n"," ");
						line = line.replaceAll("\r"," ");

					if( line.substring(line.length()-1,line.length()).equals("1")){
						_chat.put( Integer.toString(h), line.substring(0,line.lastIndexOf(",") ));
						h++;
					}				//if add 2004.07.03
						//_chat.put( Integer.toString(h), line );
						//h++;
				}
								
				if( h < max ) max = h;
			
				for( int m=0; m<max ;m++ ){
					if(!_chat.get(Integer.toString(_chat.size()-1-m)).equals("") &&
						!_chat.get(Integer.toString(_chat.size()-1-m)).equals("null") ){

						String str = (String)_chat.get(Integer.toString(_chat.size()-1-m));

						if( str.indexOf("getout") !=-1 && str.substring(12,13).equals("=") ){  //add 2004.08.18
							//�����ޏo�̏ꍇ�͕\�����Ȃ�
						}else{

							//�G�����Ή� add 2004.08.18
							for(int i=0; i<emojiS.length; i++){
								if(str.indexOf(emojiS[i]) !=-1){
									 int c = i+1;
									 String _img="";
									 if( c < 10){ _img = "00" + Integer.toString(c) + ".gif";
									 }else if(10 <= c && c < 100){ _img = "0" + Integer.toString(c) + ".gif";
									 }else if( 100 <= c ){ _img = Integer.toString(c) + ".gif"; }

									 str = str.replaceAll(emojiS[i],"<img src=" + _EmojiHome + _img + ">");		
								}
	
							}

							_LogData += "\n<br>------------------------------------------------------<br>\n"
							      	 + str;
									// + _chat.get(Integer.toString(_chat.size()-1-m));
						}		
					}
				}

//Static.Trace.out( "_LogData : " + _LogData );


			}
			catch (Exception ex) {
				Static.Error.out( ex.toString() );
			}

				_chat.clear(); //�Z�b�V�����̒��Ŏc�邩������Ȃ��̂ŃN���A���Ă���

			if( _LogData == null ) _LogData = "";
			return _LogData;

	}



}