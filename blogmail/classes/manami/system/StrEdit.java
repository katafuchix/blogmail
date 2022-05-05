/*
********************************************************************************
* システム：ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* モジュール：StrEdit.java                                                     *
* クラス名　：StrEdit                                                          *
* 概要　　　：文字列操作クラス                                                 *
* 作成　　　：2004/04/16 K.Katafuchi(affinity)                                 *
*------------------------------------------------------------------------------*
* 更新　　　：2004/04/30 K.Katafuchi(affinity)								   *
*			: 文字列伸張メソッド追加										   *						                                           *
* 　　　　　：2004/05/16 K.Katafuchi(affinity)								   *
*			　日付文字列調整メソッド追加									   *
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.system;

/*-- インポート --------------------------------------------------------------*/

/*-- クラス定義 --------------------------------------------------------------*/

public class StrEdit
{
	private static int _CodeTab = 9;
	private static int _CodeCR = 13;
	private static int _CodeLF = 10;

	//----------------------------------------------------------------------
	// メソッド：文字列分割
	//----------------------------------------------------------------------
	public static final String[] split(String str, String delimit)
	{
		String _array[] = new String[0];

		if( str == null || str.length() == 0 ) return _array;

		String buf = str;
		while( true ) {

			// デリミタ位置
			int ps = buf.indexOf( delimit );

			// 領域確保
			String swk[] = _array;
			_array = new String[_array.length + 1];
			for(int i=0; i<swk.length; i++ ){ _array[i] = swk[i]; }

			// 見つからなかった場合
			if( ps == -1 ){
				_array[_array.length-1] = buf;
				break;
			}

			// 見つかった場合
			_array[_array.length-1] = buf.substring( 0, ps );
			buf = buf.substring( ps + delimit.length() );
		}

		return _array;
	}

	//----------------------------------------------------------------------
	// メソッド：文字列変換
	//----------------------------------------------------------------------
	public static final String replace(String Exspression, String Find, String Replace)
	{
		int nFndLen = Find.length();
		if( nFndLen == 0 ) return Exspression;
    
		String sExpBuf = Exspression;
		for(int i=0; (i+nFndLen)<=sExpBuf.length(); i++ ){
			if( sExpBuf.substring(i,i+nFndLen).equals(Find) ){
				sExpBuf = sExpBuf.substring(0,i) + Replace + sExpBuf.substring(i+nFndLen);
				i += Replace.length()-1;
			}
		}
		return sExpBuf;
	}

	//----------------------------------------------------------------------
	// メソッド：文字列中にスペースがあれば取り除く
	//----------------------------------------------------------------------
	public static final String cutSpace(String Source)
	{
		if( Source == null ) return "";

		Source = Source.trim();
		int pos = 0;
		while( pos < Source.length() ){
			pos = Source.indexOf(" ");
			if( pos < 0 ) break;

			Source = Source.substring(0,pos).trim()
			       + Source.substring(pos+1).trim();
		}
		return Source;
	}

	//----------------------------------------------------------------------
	// メソッド：文字列に指定したコードがあるか？
	//----------------------------------------------------------------------
	public  static final boolean isTab(String a) { return _isKeyCode( a, _CodeTab ); }
	public  static final boolean isCR( String a) { return _isKeyCode( a, _CodeCR );  }
	public  static final boolean isLF( String a) { return _isKeyCode( a, _CodeLF );  }
	private static final boolean _isKeyCode(String a, int key)
	{
		byte b[] = a.getBytes();

		for(int i=0; i<b.length; i++ ){
			if( b[i] == key ) return( true );
		}
		return( false );
	}

	public static final boolean isCRLF(String a)
	{
		byte b[] = a.getBytes();

		for(int i=0; i<b.length-1; i++ ){
			if( b[i] == _CodeCR && b[i] == _CodeLF) return( true );
		}
		return( false );
	}

	//----------------------------------------------------------------------
	// メソッド：Integer.parseIntを使ってintに変換する
	//----------------------------------------------------------------------
	public static final int parseInt(String source)
	{
		int wk = 0;
		try{ wk = Integer.parseInt(source); }catch(Exception e){}
		return wk;
	}
	public static final String parseIntString(String source)
	{
		int wk = 0;
		try{ wk = Integer.parseInt(source); }catch(Exception e){ return ""; }
		return wk + "";
	}

	//----------------------------------------------------------------------
	// メソッド：文字列を数値で返す
	//----------------------------------------------------------------------
	public static final double searchDouble(String sNum){ return (double)_toValue(sNum, true); }
	public static final float  searchFloat( String sNum){ return  (float)_toValue(sNum, true); }
	public static final int    searchInt(   String sNum){ return    (int)_toValue(sNum, true); }

	public static final double getDouble(String sNum){return (double)_toValue(sNum,false);}
	public static final float  getFloat( String sNum){return  (float)_toValue(sNum,false);}
	public static final int    getInt(   String sNum){return    (int)_toValue(sNum,false);}

	private static final double _toValue(String sNum, boolean Fuzzy)
	{
		if( sNum == null || sNum.length() == 0 ) return 0;

		String sWk = "";
		int    nPd = 0;

		sNum = sNum.trim();

		for(int i=0; i<sNum.length(); i++ ){
			String str = sNum.substring(i,i+1);

			boolean flg = false;
			for(int j=0; j<10; j++ ){
				if( str.equals( String.valueOf(j) ) ){
					flg = true;
					break;
				}
			}

			if( !flg ){
				// １個目のピリオドならＯＫ
				if( str.equals(".") ){
					nPd++;
					if( nPd == 1 ) flg = true;
				}
				// 先頭のプラス、マイナスならＯＫ
				else if( str.equals("+") || str.equals("-") ){
					if( sWk.length() == 0 ) flg = true;
				}
			}

			if( flg ) {
				sWk += str;
			}else{
				// あいまい時は連続した数値のみ取り出す
				if( Fuzzy && sWk.length() == 0 ) continue;
				break;
			}
		}
		if( sWk.length() == 0 ) return 0;

		if( sWk.substring(0,1).equals(".") ){
			sWk = "0" + sWk;
		}
		if( sWk.substring(0,1).equals("+") ||
		    sWk.substring(0,1).equals("-") ){
			if( sWk.length() == 1 ) sWk = "0.0";
		}

		return Double.parseDouble( sWk );
	}

	//----------------------------------------------------------------------
	// メソッド：文字列中の数字のみを連結して返す
	//----------------------------------------------------------------------
	public static final int getNumericInt(String sNum)
	{
		String ret = getNumericString(sNum);

		if( ret.length() == 0 ) return 0;
		else                    return Integer.parseInt(ret);
	}
	public static final String getNumericString(String sNum)
	{
		String ret = "";

		for(int i=0; i<sNum.length(); i++ ){
			String str = sNum.substring(i,i+1);

			for(int j=0; j<10; j++ ){
				if( str.equals( String.valueOf(j) ) ){
					ret += str;
					break;
				}
			}
		}
		return ret;
	}

	//----------------------------------------------------------------------
	// メソッド：文字列をバイト単位で切り出す
	//----------------------------------------------------------------------
	public static final String leftB(String source, int length)
	{
		int i, size = 0;

		for(i=0; i<source.length(); i++ ){
			size += source.substring(i,i+1).getBytes().length;
			if( length < size ) break;
		}
		return source.substring(0,i);
	}

	//----------------------------------------------------------------------
	// メソッド：全角数値文字を半角にする
	//----------------------------------------------------------------------
	public static final String toNarrowNumeric(String source)
	{
		if( source == null ) return "";

		String newStr = "";
		for(int i=0; i<source.length(); i++ ){
			String tStr = source.substring(i,i+1);
			try{
				if( ! tStr.equals("－") ){
					int wk = Integer.parseInt(source.substring(i,i+1));
					newStr += wk + "";
				}
			}catch(Exception e){
				newStr += source.substring(i,i+1);
			}
		}
		return newStr;
	}

	//----------------------------------------------------------------------
	// メソッド：数値か文字か判別
	//----------------------------------------------------------------------
	public static final boolean isInteger(String Str)
	{
		int length = Str.length();

		if( length == 0 ) {
			return( false );
		}

		for( int i = 0; i < length; i ++ ) {
			char c = Str.charAt(i);
			if( !( '0' <= c && c <= '9' ) ) {
				return( false );
			}
		}
		return( true );
	}


	//----------------------------------------------------------------------
	// メソッド：文字列の後ろに指定した長さまで" "をつける
	//----------------------------------------------------------------------
	public String lengthen(String moji, int i)
	{

		if( !moji.equals("") && !moji.equals("null") ){

			if( moji.length()  < i ){

				int l = i - moji.length();
                	for(int k=0; k<l; k++){
						moji = moji + " ";
					} 	
			}
		}
		return moji;
	}


	//----------------------------------------------------------------------
	// メソッド：YYYYMMDD を年月日に変換する
	//----------------------------------------------------------------------
	public String to年月日( String YYYYMMDD )
	{

		String _yyyy = YYYYMMDD.substring(0,4);
		String _mm	 = YYYYMMDD.substring(4,6);
		String _dd   = YYYYMMDD.substring(6,8);

		if(_mm.substring(0,1).equals("0")) _mm = _mm.substring(1,2);
		if(_dd.substring(0,1).equals("0")) _dd = _dd.substring(1,2);

		return _yyyy + "年" + _mm + "月" + _dd + "日";

	}


	//----------------------------------------------------------------------
	// メソッド：YYYYMMDD をMM.DDに変換する
	//----------------------------------------------------------------------
	public String toMMDD( String YYYYMMDD )
	{

		String _mm	 = YYYYMMDD.substring(4,6);
		String _dd   = YYYYMMDD.substring(6,8);

		if(_mm.substring(0,1).equals("0")) _mm = _mm.substring(1,2);
		if(_dd.substring(0,1).equals("0")) _dd = _dd.substring(1,2);

		return  _mm + "." + _dd ;

	}

	//----------------------------------------------------------------------
	// メソッド：YYYYMMDD をYYYY.MM.DDに変換する
	//----------------------------------------------------------------------
	public String toYYYYMMDD( String YYYYMMDD )
	{

		String _yyyy = YYYYMMDD.substring(0,4);
		String _mm	 = YYYYMMDD.substring(4,6);
		String _dd   = YYYYMMDD.substring(6,8);

		if(_mm.substring(0,1).equals("0")) _mm = _mm.substring(1,2);
		if(_dd.substring(0,1).equals("0")) _dd = _dd.substring(1,2);

		return  _yyyy + "." + _mm + "." + _dd ;

	}


}