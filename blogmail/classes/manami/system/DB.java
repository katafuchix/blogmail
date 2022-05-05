/*
********************************************************************************
* システム： ai-land i-mode live BBS for Queen 				                   *
*==============================================================================*
* クラス　：DB                      ｜モジュール：DB.java                      *
* 概要　　：データベースアクセス関連                                           *
* 作成　　：2004/04/26 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.system;

/*-- インポート --------------------------------------------------------------*/
import  java.sql.*;
import  manami.util.*;

/*-- クラス定義 --------------------------------------------------------------*/
public class DB {

	static    String     Classname = "DB";

	static    boolean    _IsGetProperty = false;
	static    String     _Classname;
	static    String     _Engine;
	static    String     _Hostname;
	static    String     _Port;
	static    String     _DBname;
	static    String     _Username;
	static    String     _Password;

	private   Connection        _DB = null;
	private   boolean           _isPrepared;
	private   Statement         _ST = null;
	private   PreparedStatement _PS = null;
	private   ResultSet         _RS = null;
	private   ResultSetMetaData _MD = null;

	  ////////////////////
	 // コンストラクタ //
	////////////////////
	public DB() {

		if( ! _IsGetProperty ) {
			Property P = new Property();
			P.open( Classname );
			_Classname = P.getString( "Classname" );
			_Engine    = P.getString( "Engine" );
			_Hostname  = P.getString( "Hostname" );
			_Port      = P.getString( "Port" );
			_DBname    = P.getString( "DBname" );
			_Username  = P.getString( "Username" );
			_Password  = P.getString( "Password" );
			_IsGetProperty = true;
		}

	}

	  //////////////////
	 // デストラクタ //
	//////////////////
	public void finalize() {
Static.Trace.out( "disconnect();" );
		disconnect();
	}

	  //////////////////
	 // ＪＤＢＣ接続 //
	//////////////////
	public boolean connect() throws SQLException {

		try {
			if( _DB != null ) {
				if( _DB.isClosed() ) disconnect();
			}
		} catch( Exception X ) {
			Static.Error.out( X );
		}

		try {
Static.Trace.out( "Classname : " + _Classname );
			Class.forName( _Classname );
		} catch( Exception X ) {
			Static.Error.out( X );
			return( false );
		}

		try {
			String ConnectionString;
			ConnectionString  = "jdbc:" + _Engine + "://" + _Hostname;
			if( ! _Port.equals( "" ) ) ConnectionString += ":" + _Port;
			ConnectionString += "/" + _DBname;
Static.Trace.out( "ConnectionString : " + ConnectionString );
			_DB = DriverManager.getConnection( ConnectionString, _Username, _Password );
			// 自動コミット無効
			_DB.setAutoCommit( false );
		} catch( SQLException X ) {
			Static.Error.out( X );
			throw X;
		}

		return( true );
	}

	  //////////////////
	 // ＪＤＢＣ切断 //
	//////////////////
	public boolean disconnect() {

		boolean Result = close();

		try {
			if( ! _DB.isClosed() ) _DB.close();
Static.Trace.out( "DBclose : " + " close OK " );
Static.Trace.out( "DBdisconnect : " +  Result );
		} catch( SQLException X ) {
			Static.Error.out( X );
			Result = false;
		} finally {
			_DB = null;
		}

		return( Result );
	}

	  ////////////////////////
	 // ステートメント生成 //
	////////////////////////
	public Statement create() throws SQLException {

		try {
Static.Trace.out( "createStatement();" );
			_ST = _DB.createStatement();
			_isPrepared = false;
		} catch( SQLException X ) {
			Static.Error.out( X );
			throw X;
		}

		return( _ST );
	}

	  ////////////////
	 // クエリ実行 //
	////////////////
	public ResultSet select( String SelectString ) throws SQLException {
		return( query(SelectString) );
	}
	public ResultSet query( String QueryString ) throws SQLException {

		try {
			if( _isPrepared ) throw new SQLException( "ERROR:  This statement is prepared" );
Static.Trace.out( "QueryString : " + QueryString );
			_RS = _ST.executeQuery( QueryString );
		} catch( SQLException X ) {
			Static.Error.out( X );
			throw X;
		}

		return( _RS );
	}

	  ////////////////
	 // 更新系実行 //
	////////////////
	public int insert( String InsertString ) throws SQLException {
		return( update(InsertString) );
	}
	public int delete( String DeleteString ) throws SQLException {
		return( update(DeleteString) );
	}
	public int update( String UpdateString ) throws SQLException {

		int Result = 0;
		try {
			if( _isPrepared ) throw new SQLException( "ERROR:  This statement is prepared" );
Static.Trace.out( "UpdateString : " + UpdateString );
			Result = _ST.executeUpdate( UpdateString );
		} catch( SQLException X ) {
			Static.Error.out( X );
			throw X;
		}

		return( Result );
	}
	public void execute( String ExequteString ) throws SQLException {
		update( ExequteString );
	}

/*== プリコンパイル済ＳＱＬ ==================================================*/

	  ////////////////////////////////////////
	 // プリコンパイル済ステートメント生成 //
	////////////////////////////////////////
	public PreparedStatement prepare( String QueryString ) throws SQLException {

		try {
Static.Trace.out( "prepareStatement();" );
Static.Trace.out( "QueryString : " + QueryString );
			_PS = _DB.prepareStatement( QueryString );
			_isPrepared = true;
		} catch( SQLException X ) {
			Static.Error.out( X );
			throw X;
		}

		return( _PS );
	}

	  ////////////////////////
	 // プレースホルダ設定 //
	////////////////////////
	public void setBoolean( int Index, boolean Value ) throws SQLException {
		_PS.setBoolean( Index, Value );
	}
	public void setShort( int Index, short Value ) throws SQLException {
		_PS.setShort( Index, Value );
	}
	public void setInt( int Index, int Value ) throws SQLException {
		_PS.setInt( Index, Value );
	}
	public void setLong( int Index, long Value ) throws SQLException {
		_PS.setLong( Index, Value );
	}
	public void setFloat( int Index, float Value ) throws SQLException {
		_PS.setFloat( Index, Value );
	}
	public void setDouble( int Index, double Value ) throws SQLException {
		_PS.setDouble( Index, Value );
	}
	public void setString( int Index, String Value ) throws SQLException {
		_PS.setString( Index, Value );
	}
	public void setDate( int Index, Date Value ) throws SQLException {
		_PS.setDate( Index, Value );
	}
	public void setTime( int Index, Time Value ) throws SQLException {
		_PS.setTime( Index, Value );
	}
	public void setNull( int Index, int Type ) throws SQLException {
		_PS.setNull( Index, Type );
	}

	  ////////////////
	 // クエリ実行 //
	////////////////
	public ResultSet select() throws SQLException {
		return( query() );
	}
	public ResultSet query() throws SQLException {

		try {
			if( ! _isPrepared ) throw new SQLException( "ERROR:  This statement is not prepared" );
			_RS = _PS.executeQuery();
		} catch( SQLException X ) {
			Static.Error.out( X );
			throw X;
		}

		return( _RS );
	}

	  ////////////////
	 // 更新系実行 //
	////////////////
	public int insert() throws SQLException {
		return( update() );
	}
	public int delete() throws SQLException {
		return( update() );
	}
	public int update() throws SQLException {

		int Result = 0;
		try {
			if( ! _isPrepared ) throw new SQLException( "ERROR:  This statement is not prepared" );
			Result = _PS.executeUpdate();
		} catch( SQLException X ) {
			Static.Error.out( X );
			throw X;
		}

		return( Result );
	}
	public void execute() throws SQLException {
		update();
	}

/*============================================================================*/

	  ////////////////////
	 // メタデータ取得 //
	////////////////////
	public ResultSetMetaData getMetaData() throws SQLException {

		if( _isPrepared ) {
			_MD = _PS.getMetaData();
		} else {
			_MD = _RS.getMetaData();
		}

		return( _MD );
	}
	  //////////////////
	 // カラム数取得 //
	//////////////////
	public int getColumnCount() throws SQLException {
		return( _MD.getColumnCount() );
	}
	  //////////////////
	 // カラム名取得 //
	//////////////////
	public String getColumnName( int Column ) throws SQLException {
		return( _MD.getColumnName( Column ) );
	}
	  //////////////////
	 // カラム型取得 //
	//////////////////
	public int getColumnType( int Column ) throws SQLException {
		return( _MD.getColumnType( Column ) );
	}

	  ////////////////////
	 // カラム型名取得 //
	////////////////////
	public String getColumnTypeName( int Column ) throws SQLException {
		return( _MD.getColumnTypeName( Column ) );
	}

	  ////////////////
	 // データ取得 //
	////////////////
	public boolean getBoolean( int ColumnNo ) throws SQLException {
		return( _RS.getBoolean( ColumnNo ) );
	}
	public short getShort( int ColumnNo ) throws SQLException {
		return( _RS.getShort( ColumnNo ) );
	}
	public int getInt( int ColumnNo, int Default ) throws SQLException {

    	String Str;
		int    Value;
		try {
			Str = _RS.getString( ColumnNo );
            if( Str != null ) Value = Integer.parseInt( Str );
			else              Value = Default;
		} catch( SQLException X ) {
			Static.Error.out( X );
			return( Default );
		}

		return( Value );
	}
	public int getInt( String ColumnName, int Default ) throws SQLException {

    	String Str;
		int    Value;
		try {
			Str = _RS.getString( ColumnName );
            if( Str != null ) Value = Integer.parseInt( Str );
			else              Value = Default;
		} catch( SQLException X ) {
			Static.Error.out( X );
			return( Default );
		}

		return( Value );
	}
	public int getInt( int ColumnNo ) throws SQLException {

		int Value = 0;
		try {
			 Value = _RS.getInt( ColumnNo );
		} catch( SQLException X ) {
			Static.Error.out( X );
		}

		return( Value );
	}
	public int getInt( String ColumnName ) throws SQLException {

		int Value = 0;
		try {
			Value = _RS.getInt( ColumnName );
		} catch( SQLException X ) {
			Static.Error.out( X );
		}

		return( Value );
	}
	public long getLong( int ColumnNo ) throws SQLException {
		return( _RS.getLong( ColumnNo ) );
	}
	public float getFloat( int ColumnNo ) throws SQLException {
		return( _RS.getFloat( ColumnNo ) );
	}
	public double getDouble( int ColumnNo ) throws SQLException {
		return( _RS.getDouble( ColumnNo ) );
	}
	public String getString( int ColumnNo ) throws SQLException {

		String Str = null;
		try {
			Str = _RS.getString( ColumnNo );
		} catch( SQLException X ) {
			Static.Error.out( X );
			return( null );
		}

		return( Str );
	}
	public String getString( String ColumnName ) throws SQLException {

		String Str = null;
		try {
			Str = _RS.getString( ColumnName );
		} catch( SQLException X ) {
			Static.Error.out( X );
			return( null );
		}

		return( Str );
	}
	public Date getDate( int ColumnNo ) throws SQLException {
		return( _RS.getDate( ColumnNo ) );
	}
	public Time getTime( int ColumnNo ) throws SQLException {
		return( _RS.getTime( ColumnNo ) );
	}

	  //////////////
	 // コミット //
	//////////////
	public void commit() throws SQLException {

		try {
			_DB.commit();
		} catch( SQLException X ) {
			Static.Error.out( X );
			throw X;
		}

	}

	  //////////////////
	 // ロールバック //
	//////////////////
	public void rollback() throws SQLException {

		try {
			_DB.rollback();
		} catch( SQLException X ) {
			Static.Error.out( X );
			throw X;
		}

	}

	  ////////////////
	 // 次レコード //
	////////////////
	public boolean next() throws SQLException {
		return( fetch() );
	}
	public boolean fetch() throws SQLException {

		boolean Result = false;
		try {
			Result = _RS.next();
		} catch( SQLException X ) {
			Static.Error.out( X );
			return( Result );
		}

		return( Result );
	}

	  //////////////
	 // クローズ //
	//////////////
	public boolean close() {

		boolean Result = true;

		try {
			if( _RS != null ) _RS.close();

			if( _isPrepared ) {
				if( _ST != null ) _ST.close();
			} else {
				if( _PS != null ) _PS.close();
			}
		} catch( SQLException X ) {
			Static.Error.out( X );
			Result = false;
		} finally {
			_RS = null;

			if( _isPrepared ) _ST = null;
			else              _PS = null;

//		return( Result ); }

		}
//Static.Trace.out( "DBclose : " + Result );
		return( Result ); 
	}

}
