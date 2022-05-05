/*
********************************************************************************
* システム： ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* クラス　：Numeric                 ｜モジュール：Numeric.java                 *
* 概要　　：数値関連                                                           *
* 作成　　：2004/04/20 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.util;

/*-- インポート --------------------------------------------------------------*/
import  java.text.*;
import  manami.system.*;

/*-- クラス定義 --------------------------------------------------------------*/
public class Numeric {

	static    String     Classname = "Numeric";

	private   Object     _Value  = null;

	  ////////////////////
	 // コンストラクタ //
	////////////////////
	public Numeric() {}
	public Numeric( byte   Value ) { setByte(   Value ); }
	public Numeric( short  Value ) { setShort(  Value ); }
	public Numeric( int    Value ) { setInt(    Value ); }
	public Numeric( long   Value ) { setLong(   Value ); }
	public Numeric( float  Value ) { setFloat(  Value ); }
	public Numeric( double Value ) { setDouble( Value ); }
	public Numeric( String Value ) { setString( Value ); }
	public Numeric( String Format, String Value ) {
		setString( Format, Value );
	}

	  //////////////
	 // 数値設定 //
	//////////////
	public void setByte(   byte   Value ) { _Value = new Byte(    Value ); }
	public void setShort(  short  Value ) { _Value = new Short(   Value ); }
	public void setInt(    int    Value ) { _Value = new Integer( Value ); }
	public void setLong(   long   Value ) { _Value = new Long(    Value ); }
	public void setFloat(  float  Value ) { _Value = new Float(   Value ); }
	public void setDouble( double Value ) { _Value = new Double(  Value ); }

	  //////////////
	 // 数値取得 //
	//////////////
	public byte   toByte()   { java.lang.Number n = ( java.lang.Number )_Value; return( n.byteValue()   ); }
	public short  toShort()  { java.lang.Number n = ( java.lang.Number )_Value; return( n.shortValue()  ); }
	public int    toInt()    { java.lang.Number n = ( java.lang.Number )_Value; return( n.intValue()    ); }
	public long   toLong()   { java.lang.Number n = ( java.lang.Number )_Value; return( n.longValue()   ); }
	public float  toFloat()  { java.lang.Number n = ( java.lang.Number )_Value; return( n.floatValue()  ); }
	public double toDouble() { java.lang.Number n = ( java.lang.Number )_Value; return( n.doubleValue() ); }

	  ////////////////
	 // 文字列設定 //
	////////////////
	static private char[] $zenkaku
	 = { '０','１','２','３','４','５','６','７','８','９','．','－' };
	static private char[] $hankaku
	 = {  '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-' };
	public String toHankaku( String Value ) {
		StringBuffer sb = new StringBuffer();
		for( int i = 0; i < Value.length(); i ++ ) {
			char c = Value.charAt( i );
			for( int j = 0; j < $zenkaku.length; j ++ ) {
				if( c == $zenkaku[j] ) { c = $hankaku[j]; break; }
			}
			sb.append( c );
		}
		return( sb.toString() );
	}
	public void setString( String Value ) {
		String V = toHankaku( Value );
		double doubleValue = Double.parseDouble( V );
		setDouble( doubleValue );
 	}
	public void setString( String Format, String Value ) {

		DecimalFormat df;
		try {
			df = new DecimalFormat( Format );
			double doubleValue = df.parse( Value ).doubleValue();
			setDouble( doubleValue );
		} catch( Exception X ) {
			Static.Error.out( X );
		}

	}

	  ////////////////
	 // 文字列取得 //
	////////////////
	public String getString() {
		return( getString( "0.0" ) );
	}
	public String getString( String Format ) {

		String ValueString = null;
		if( _Value == null ) return( ValueString );

		DecimalFormat df;
		try {
			df = new DecimalFormat( Format );
			ValueString = df.format( _Value );
		} catch( Exception X ) {
			Static.Error.out( X );
		} finally {

//		return( ValueString ); 

		}
		return( ValueString );
	}

}
