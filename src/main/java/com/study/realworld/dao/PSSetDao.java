package com.study.realworld.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementSetter;

public class PSSetDao {
	public static class PSSForString implements PreparedStatementSetter {
		private String lValue;
		
		public PSSForString(String l_value) {
			this.lValue = l_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.lValue);
		}
	}
	
	public static class PSSForDoubleString implements PreparedStatementSetter {
		private String rValue;
		private String sValue;

		public PSSForDoubleString(String r_value, String s_value) {
			this.rValue = r_value;
			this.sValue = s_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.rValue);
			ps.setString(2, this.sValue);
			
		}
	}
	
	public static class PSSForTripleString implements PreparedStatementSetter {
		private String rValue;
		private String sValue;
		private String tValue;

		public PSSForTripleString(String r_value, String s_value, String t_value) {
			this.rValue = r_value;
			this.sValue = s_value;
			this.tValue = t_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.rValue);
			ps.setString(2, this.sValue);
			ps.setString(3, this.tValue);
			
		}
	}
	
	public static class PSSForFourString implements PreparedStatementSetter {
		private String v1;
		private String v2;
		private String v3;
		private String v4;
		
		public PSSForFourString(String v1, String v2, String v3, String v4) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			this.v4 = v4;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.v1);
			ps.setString(2, this.v2);
			ps.setString(3, this.v3);
			ps.setString(4, this.v4);
		}
	}
	
	public static class PSSForFiveString implements PreparedStatementSetter {
		private String v1;
		private String v2;
		private String v3;
		private String v4;
		private String v5;
		
		public PSSForFiveString(String v1, String v2, String v3, String v4, String v5) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			this.v4 = v4;
			this.v5 = v5;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.v1);
			ps.setString(2, this.v2);
			ps.setString(3, this.v3);
			ps.setString(4, this.v4);
			ps.setString(5, this.v5);
		}
	}
	
	public static class PSSForTripleStringLong implements PreparedStatementSetter {
		private String rValue;
		private String sValue;
		private String tValue;
		private long l_value;

		public PSSForTripleStringLong(String r_value, String s_value, String t_value, long l_value) {
			this.rValue = r_value;
			this.sValue = s_value;
			this.tValue = t_value;
			this.l_value = l_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.rValue);
			ps.setString(2, this.sValue);
			ps.setString(3, this.tValue);
			ps.setLong(4, this.l_value);
			
		}
	}
		
	public static class PSSForDoubleStringInt implements PreparedStatementSetter {
		private String rValue;
		private String sValue;
		private int iValue;

		public PSSForDoubleStringInt(String r_value, String s_value, int i_value) {
			this.rValue = r_value;
			this.sValue = s_value;
			this.iValue = i_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.rValue);
			ps.setString(2, this.sValue);
			ps.setInt(3, this.iValue);
			
		}
	}
	
	public static class PSSForDoubleStringDoubleInt implements PreparedStatementSetter {
		private String v1;
		private String v2;
		private int v3;
		private int v4;
		
		public PSSForDoubleStringDoubleInt(String v1, String v2, int v3, int v4) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			this.v4 = v4;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.v1);
			ps.setString(2, this.v2);
			ps.setInt(3, this.v3);
			ps.setInt(4, this.v4);
			
		}
	}
	
	public static class PSSForDoubleStringLong implements PreparedStatementSetter {
		private String rValue;
		private String sValue;
		private long iValue;

		public PSSForDoubleStringLong(String r_value, String s_value, long i_value) {
			this.rValue = r_value;
			this.sValue = s_value;
			this.iValue = i_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.rValue);
			ps.setString(2, this.sValue);
			ps.setLong(3, this.iValue);
			
		}
	}

	public static class PSSForIntBytes implements PreparedStatementSetter {
		private int v1;
		private Byte value[];
		
		public PSSForIntBytes(int v1, Byte... value) {
			this.v1 = v1;
			this.value = value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			
			ps.setInt(1, this.v1);
			for( int i = 1 ; i <= value.length ; i++ ) {
				ps.setByte((i+1), this.value[i]);
			}
		}
	}

	public static class PSSForInts implements PreparedStatementSetter {
		private Integer value[];
		
		public PSSForInts(Integer... value) {
			this.value = value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			
			for( int i = 0 ; i < value.length ; i++ ) {
				ps.setInt((i+1), this.value[i]);
			}
		}
	}
	
	public static class PSSForStrings implements PreparedStatementSetter {
		private String value[];
		
		public PSSForStrings(String... value) {
			this.value = value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			
			for( int i = 0 ; i < value.length ; i++ ) {
				ps.setString((i+1), this.value[i]);
			}
		}
	}
	
	public static class PSSForIntStrings implements PreparedStatementSetter {
		private int v1;
		private String value[];
		
		public PSSForIntStrings(int v1, String... value) {
			this.v1 = v1;
			this.value = value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			int pos = 1;

			ps.setInt(pos++, this.v1);
			for( int i = 0 ; i < value.length ; i++ ) {
				ps.setString(pos++, this.value[i]);
			}
		}
	}
	
	public static class PSSForInt implements PreparedStatementSetter {
		private int lValue;
		
		public PSSForInt(int l_value) {
			this.lValue = l_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.lValue);
		}
	}

	public static class PSSForDoubleInt implements PreparedStatementSetter {
		private int lValue;
		private int rValue;
		
		public PSSForDoubleInt(int l_value, int r_value) {
			this.lValue = l_value;
			this.rValue = r_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.lValue);
			ps.setInt(2, this.rValue);
		}
	}
	
	public static class PSSForDoubleIntString implements PreparedStatementSetter {
		private int lValue;
		private int rValue;
		private String tValue;
		
		public PSSForDoubleIntString(int l_value, int r_value, String t_value) {
			this.lValue = l_value;
			this.rValue = r_value;
			this.tValue = t_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.lValue);
			ps.setInt(2, this.rValue);
			ps.setString(3, this.tValue);
		}
	}
	
	public static class PSSForTirpleIntString implements PreparedStatementSetter {
		private int v1;
		private int v2;
		private int v3;
		private String v4;
		
		public PSSForTirpleIntString(int v1, int v2, int v3, String v4) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			this.v4 = v4;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.v1);
			ps.setInt(2, this.v2);
			ps.setInt(3, this.v3);
			ps.setString(4, this.v4);
		}
	}
	
	public static class PSSForDoubleIntDoubleString implements PreparedStatementSetter {
		private int lValue;
		private int rValue;
		private String tValue;
		private String sValue;
		
		public PSSForDoubleIntDoubleString(int l_value, int r_value, String t_value, String s_value) {
			this.lValue = l_value;
			this.rValue = r_value;
			this.tValue = t_value;
			this.sValue = s_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.lValue);
			ps.setInt(2, this.rValue);
			ps.setString(3, this.tValue);
			ps.setString(4, this.sValue);
		}
	}
	
	public static class PSSForLongDoubleInt implements PreparedStatementSetter {
		private long aValue;
		private int lValue;
		private int rValue;
		
		public PSSForLongDoubleInt(long a_value, int l_value, int r_value) {
			this.aValue = a_value;
			this.lValue = l_value;
			this.rValue = r_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setLong(1, this.aValue);
			ps.setInt(2, this.lValue);
			ps.setInt(3, this.rValue);
		}
	}
	
	public static class PSSForDoubleIntLong implements PreparedStatementSetter {
		private int lValue;
		private int rValue;
		private long vValue;
		
		public PSSForDoubleIntLong(int l_value, int r_value, long v_value) {
			this.lValue = l_value;
			this.rValue = r_value;
			this.vValue = v_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.lValue);
			ps.setInt(2, this.rValue);
			ps.setLong(3, this.vValue);
		}
	}
	
	public static class PSSForStringDoubleInt implements PreparedStatementSetter {
		private String sValue;
		private int lValue;
		private int rValue;
		
		public PSSForStringDoubleInt(String s_value, int l_value, int r_value) {
			this.sValue = s_value;
			this.lValue = l_value;
			this.rValue = r_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.sValue);
			ps.setInt(2, this.lValue);
			ps.setInt(3, this.rValue);
		}
	}
	
	public static class PSSForStringTripleInt implements PreparedStatementSetter {
		private String sValue;
		private int value1;
		private int value2;
		private int value3;
		
		public PSSForStringTripleInt(String s_value, int value1, int value2, int value3) {
			this.sValue = s_value;
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.sValue);
			ps.setInt(2, this.value1);
			ps.setInt(3, this.value2);
			ps.setInt(4, this.value3);
		}
	}
	
	public static class PSSForLongTripleInt implements PreparedStatementSetter {
		private long value0;
		private int value1;
		private int value2;
		private int value3;
		
		public PSSForLongTripleInt(long value0, int value1, int value2, int value3) {
			this.value0 = value0;
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setLong(1, this.value0);
			ps.setInt(2, this.value1);
			ps.setInt(3, this.value2);
			ps.setInt(4, this.value3);
		}
	}

	public static class PSSForTripleInt implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		
		public PSSForTripleInt(int value1, int value2, int value3) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
		}
	}
	
	public static class PSSForDoubleIntLongString implements PreparedStatementSetter {
		private int v1;
		private int v2;
		private long v3;
		private String v4;
		
		public PSSForDoubleIntLongString(int v1, int v2, long v3, String v4) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			this.v4 = v4;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.v1);
			ps.setInt(2, this.v2);
			ps.setLong(3, this.v3);
			ps.setString(4, this.v4);
		}
	}
	
	public static class PSSForTripleIntLongString implements PreparedStatementSetter {
		private int v1;
		private int v2;
		private int v3;
		private long v4;
		private String v5;
		
		public PSSForTripleIntLongString(int v1, int v2, int v3, long v4, String v5) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			this.v4 = v4;
			this.v5 = v5;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.v1);
			ps.setInt(2, this.v2);
			ps.setInt(3, this.v3);
			ps.setLong(4, this.v4);
			ps.setString(5, this.v5);
		}
	}
	
	public static class PSSForTripleIntString implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private String value4;
		
		public PSSForTripleIntString(int value1, int value2, int value3, String value4) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setString(4, this.value4);
		}
	}
	
	public static class PSSForTripleIntDoubleString implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private String value4;
		private String value5;
		
		public PSSForTripleIntDoubleString(int value1, int value2, int value3, String value4, String value5) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setString(4, this.value4);
			ps.setString(5, this.value5);
		}
	}
	
	public static class PSSForTripleIntFourString implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private String value4;
		private String value5;
		private String value6;
		private String value7;
		
		public PSSForTripleIntFourString(int value1, int value2, int value3, String value4, String value5, String value6, String value7) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
			this.value6 = value6;
			this.value7 = value7;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setString(4, this.value4);
			ps.setString(5, this.value5);
			ps.setString(6, this.value6);
			ps.setString(7, this.value7);
		}
	}
	
	public static class PSSForIntStringInt implements PreparedStatementSetter {
		private int value1;
		private String value2;
		private int value3;
		
		public PSSForIntStringInt(int value1, String value2, int value3) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setString(2, this.value2);
			ps.setInt(3, this.value3);
		}
	}
	
	public static class PSSForDoubleIntTripleStringInt implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private String value3;
		private String value4;
		private String value5;
		private int value6;
		
		public PSSForDoubleIntTripleStringInt(int value1, int value2, String value3, String value4, String value5, int value6) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
			this.value6 = value6;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setString(3, this.value3);
			ps.setString(4, this.value4);
			ps.setString(5, this.value5);
			ps.setInt(6, this.value6);
		}
	}
	
	public static class PSSForTripleIntTripleString implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private String value4;
		private String value5;
		private String value6;
		
		public PSSForTripleIntTripleString(int value1, int value2, int value3, String value4, String value5, String value6) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
			this.value6 = value6;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setString(4, this.value4);
			ps.setString(5, this.value5);
			ps.setString(6, this.value6);
		}
	}
	
	public static class PSSForFourInt implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private int value4;
		
		public PSSForFourInt(int value1, int value2, int value3, int value4) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setInt(4, this.value4);
		}
	}
	
	public static class PSSForFourIntString implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private int value4;
		private String value5;
		
		public PSSForFourIntString(int value1, int value2, int value3, int value4, String value5) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setInt(4, this.value4);
			ps.setString(5, this.value5);
		}
	}
	
	public static class PSSForFourIntDoubleString implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private int value4;
		private String value5;
		private String value6;
		
		public PSSForFourIntDoubleString(int value1, int value2, int value3, int value4, String value5, String value6) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
			this.value6 = value6;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setInt(4, this.value4);
			ps.setString(5, this.value5);
			ps.setString(6, this.value6);
		}
	}
	
	public static class PSSForFiveInt implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private int value4;
		private int value5;
		
		public PSSForFiveInt(int value1, int value2, int value3, int value4, int value5) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setInt(4, this.value4);
			ps.setInt(5, this.value5);
		}
	}
	
	public static class PSSForFiveIntString implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private int value4;
		private int value5;
		private String value6;
		
		public PSSForFiveIntString(int value1, int value2, int value3, int value4, int value5, String value6) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
			this.value6 = value6;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setInt(4, this.value4);
			ps.setInt(5, this.value5);
			ps.setString(6, this.value6);
		}
	}
	
	public static class PSSForFiveIntDoubleString implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private int value4;
		private int value5;
		private String value6;
		private String value7;
		
		public PSSForFiveIntDoubleString(int value1, int value2, int value3, int value4, int value5, String value6, String value7) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
			this.value6 = value6;
			this.value7 = value7;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setInt(4, this.value4);
			ps.setInt(5, this.value5);
			ps.setString(6, this.value6);
			ps.setString(7, this.value7);
		}
	}
	
	public static class PSSForSixInt implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private int value4;
		private int value5;
		private int value6;
		
		public PSSForSixInt(int value1, int value2, int value3, int value4, int value5, int value6) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
			this.value6 = value6;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setInt(4, this.value4);
			ps.setInt(5, this.value5);
			ps.setInt(6, this.value6);
		}
	}
	

	public static class PSSForSixIntString implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private int value4;
		private int value5;
		private int value6;
		private String value7;
		
		public PSSForSixIntString(int value1, int value2, int value3, int value4, int value5, int value6, String value7) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
			this.value6 = value6;
			this.value7 = value7;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setInt(4, this.value4);
			ps.setInt(5, this.value5);
			ps.setInt(6, this.value6);
			ps.setString(7, this.value7);
		}
	}
	
	public static class PSSForSixIntDoubleString implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private int value4;
		private int value5;
		private int value6;
		private String value7;
		private String value8;
		
		public PSSForSixIntDoubleString(int value1, int value2, int value3, int value4, int value5, int value6, String value7, String value8) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
			this.value6 = value6;
			this.value7 = value7;
			this.value8 = value8;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setInt(4, this.value4);
			ps.setInt(5, this.value5);
			ps.setInt(6, this.value6);
			ps.setString(7, this.value7);
			ps.setString(8, this.value8);
		}
	}
	
	public static class PSSForSevenIntString implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private int value4;
		private int value5;
		private int value6;
		private int value7;
		private String value8;
		
		public PSSForSevenIntString(int value1, int value2, int value3, int value4, int value5, int value6, int value7, String value8) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
			this.value6 = value6;
			this.value7 = value7;
			this.value8 = value8;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setInt(4, this.value4);
			ps.setInt(5, this.value5);
			ps.setInt(6, this.value6);
			ps.setInt(7, this.value7);
			ps.setString(8, this.value8);
		}
	}
	

	public static class PSSForSevenInt implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private int value4;
		private int value5;
		private int value6;
		private int value7;
		
		public PSSForSevenInt(int value1, int value2, int value3, int value4, int value5, int value6, int value7) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
			this.value6 = value6;
			this.value7 = value7;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setInt(4, this.value4);
			ps.setInt(5, this.value5);
			ps.setInt(6, this.value6);
			ps.setInt(7, this.value7);
		}
	}
	
	public static class PSSForEightInt implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private int value4;
		private int value5;
		private int value6;
		private int value7;
		private int value8;
		
		public PSSForEightInt(int value1, int value2, int value3, int value4, int value5, int value6, int value7, int value8) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
			this.value6 = value6;
			this.value7 = value7;
			this.value8 = value8;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setInt(4, this.value4);
			ps.setInt(5, this.value5);
			ps.setInt(6, this.value6);
			ps.setInt(7, this.value7);
			ps.setInt(8, this.value8);
		}
	}
	
	public static class PSSForNineInt implements PreparedStatementSetter {
		private int value1;
		private int value2;
		private int value3;
		private int value4;
		private int value5;
		private int value6;
		private int value7;
		private int value8;
		private int value9;
		
		public PSSForNineInt(int value1, int value2, int value3, int value4, int value5, int value6, int value7, int value8, int value9) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
			this.value5 = value5;
			this.value6 = value6;
			this.value7 = value7;
			this.value8 = value8;
			this.value9 = value9;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setInt(4, this.value4);
			ps.setInt(5, this.value5);
			ps.setInt(6, this.value6);
			ps.setInt(7, this.value7);
			ps.setInt(8, this.value8);
			ps.setInt(9, this.value9);
		}
	}
	
	public static class PSSForLongInt implements PreparedStatementSetter {
		private long lValue;
		private int rValue;
		
		public PSSForLongInt(long l_value, int r_value) {
			this.lValue = l_value;
			this.rValue = r_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setLong(1, this.lValue);
			ps.setInt(2, this.rValue);
		}
	}
	
	public static class PSSForLongIntLong implements PreparedStatementSetter {
		private long v1;
		private int v2;
		private long v3;
		
		public PSSForLongIntLong(long v1, int v2, long v3) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setLong(1, this.v1);
			ps.setInt(2, this.v2);
			ps.setLong(3, this.v3);
		}
	}
	
	public static class PSSForLongIntString implements PreparedStatementSetter {
		private long lValue;
		private int rValue;
		private String sValue;
		
		public PSSForLongIntString(long l_value, int r_value, String s_value) {
			this.lValue = l_value;
			this.rValue = r_value;
			this.sValue = s_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setLong(1, this.lValue);
			ps.setInt(2, this.rValue);
			ps.setString(3, this.sValue);
		}
	}
	
	public static class PSSForLongStringLong implements PreparedStatementSetter {
		private long value1;
		private String value2;
		private long value3;
		
		public PSSForLongStringLong(long value1, String value2, long value3) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setLong(1, this.value1);
			ps.setString(2, this.value2);
			ps.setLong(3, this.value3);
		}
	}
	
	public static class PSSForIntLong implements PreparedStatementSetter {
		private int rValue;
		private long lValue;
		
		public PSSForIntLong(int r_value, long l_value) {
			this.rValue = r_value;
			this.lValue = l_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.rValue);
			ps.setLong(2, this.lValue);
		}
	}
	
	public static class PSSForLong implements PreparedStatementSetter {
		private long lValue;
		
		public PSSForLong(long l_value) {
			this.lValue = l_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setLong(1, this.lValue);
		}
	}
	
	public static class PSSForDoubleLong implements PreparedStatementSetter {
		private long lValue;
		private long rValue;
		
		public PSSForDoubleLong(long l_value, long r_value) {
			this.lValue = l_value;
			this.rValue = r_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setLong(1, this.lValue);
			ps.setLong(2, this.rValue);
		}
	}
	
	public static class PSSForIntString implements PreparedStatementSetter {
		private int rValue;
		private String sValue;
		
		public PSSForIntString(int r_value, String s_value) {
			this.rValue = r_value;
			this.sValue = s_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.rValue);
			ps.setString(2, this.sValue);
		}
	}
	
	public static class PSSForIntDoubleString implements PreparedStatementSetter {
		private int rValue;
		private String sValue1;
		private String sValue2;
		
		public PSSForIntDoubleString(int r_value, String s_value1, String s_value2) {
			this.rValue = r_value;
			this.sValue1 = s_value1;
			this.sValue2 = s_value2;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.rValue);
			ps.setString(2, this.sValue1);
			ps.setString(3, this.sValue2);
		}
	}
	
	public static class PSSForIntTripleString implements PreparedStatementSetter {
		private int v1;
		private String v2;
		private String v3;
		private String v4;
		
		public PSSForIntTripleString(int v1, String v2, String v3, String v4) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			this.v4 = v4;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.v1);
			ps.setString(2, this.v2);
			ps.setString(3, this.v3);
			ps.setString(3, this.v4);
		}
	}
	
	public static class PSSForIntStringDoubleInt implements PreparedStatementSetter {
		private int v1;
		private String v2;
		private int v3;
		private int v4;
		
		public PSSForIntStringDoubleInt(int v1, String v2, int v3, int v4) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			this.v4 = v4;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.v1);
			ps.setString(2, this.v2);
			ps.setInt(3, this.v3);
			ps.setInt(4, this.v4);
		}
	}
	
	public static class PSSForIntDoubleStringDoubleInt implements PreparedStatementSetter {
		private int v1;
		private String v2;
		private String v3;
		private int v4;
		private int v5;
		
		public PSSForIntDoubleStringDoubleInt(int v1, String v2, String v3, int v4, int v5) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			this.v4 = v4;
			this.v5 = v5;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.v1);
			ps.setString(2, this.v2);
			ps.setString(3, this.v3);
			ps.setInt(4, this.v4);
			ps.setInt(5, this.v5);
		}
	}
	
	public static class PSSForIntTripleStringDoubleInt implements PreparedStatementSetter {
		private int v1;
		private String v2;
		private String v3;
		private String v4;
		private int v5;
		private int v6;
		
		public PSSForIntTripleStringDoubleInt(int v1, String v2, String v3, String v4, int v5, int v6) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			this.v4 = v4;
			this.v5 = v5;
			this.v6 = v6;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.v1);
			ps.setString(2, this.v2);
			ps.setString(3, this.v3);
			ps.setString(4, this.v4);
			ps.setInt(5, this.v5);
			ps.setInt(6, this.v6);
		}
	}
	
	public static class PSSForIntDoubleStringTripleInt implements PreparedStatementSetter {
		private int v1;
		private String v2;
		private String v3;
		private int v4;
		private int v5;
		private int v6;
		
		public PSSForIntDoubleStringTripleInt(int v1, String v2, String v3, int v4, int v5, int v6) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			this.v4 = v4;
			this.v5 = v5;
			this.v6 = v6;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.v1);
			ps.setString(2, this.v2);
			ps.setString(3, this.v3);
			ps.setInt(4, this.v4);
			ps.setInt(5, this.v5);
			ps.setInt(6, this.v6);
		}
	}
	
	public static class PSSForIntFiveString implements PreparedStatementSetter {
		private int v1;
		private String v2;
		private String v3;
		private String v4;
		private String v5;
		private String v6;
		
		public PSSForIntFiveString(int v1, String v2, String v3, String v4, String v5, String v6) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			this.v4 = v4;
			this.v5 = v5;
			this.v6 = v6;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.v1);
			ps.setString(2, this.v2);
			ps.setString(3, this.v3);
			ps.setString(4, this.v4);
			ps.setString(5, this.v5);
			ps.setString(6, this.v6);
		}
	}

	public static class PSSForStringInt implements PreparedStatementSetter {
		private String sValue;
		private int rValue;
		
		public PSSForStringInt(String s_value, int r_value) {
			this.sValue = s_value;
			this.rValue = r_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.sValue);
			ps.setInt(2, this.rValue);
		}
	}
	public static class PSSForDoubleStringDoubleIntString implements PreparedStatementSetter {
		private String sValue1;
		private String sValue2;
		private String sValue3;
		private int rValue1;
		private int rValue2;
		
		public PSSForDoubleStringDoubleIntString(String sValue1,String sValue2, int rValue1, int rValue2,String sValue3) {
			this.sValue1 = sValue1;
			this.sValue2 = sValue2;
			this.sValue3 = sValue3;
			this.rValue1 = rValue1;
			this.rValue2 = rValue2;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.sValue1);
			ps.setString(2, this.sValue2);
			ps.setInt(3, this.rValue1);
			ps.setInt(4, this.rValue2);
			ps.setString(5, this.sValue3);
		}
	}
	
	public static class PSSForStringLong implements PreparedStatementSetter {
		private String sValue;
		private long rValue;
		
		public PSSForStringLong(String s_value, long r_value) {
			this.sValue = s_value;
			this.rValue = r_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.sValue);
			ps.setLong(2, this.rValue);
		}
	}
	
	public static class PSSForStringIntLong implements PreparedStatementSetter {
		private String value1;
		private int value2;
		private long value3;
		
		public PSSForStringIntLong(String value1, int value2, long value3) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setLong(3, this.value3);
		}
	}
	
	public static class PSSForStringDoubleIntLong implements PreparedStatementSetter {
		private String value1;
		private int value2;
		private int value3;
		private long value4;
		
		public PSSForStringDoubleIntLong(String value1, int value2, int value3, long value4) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.value1);
			ps.setInt(2, this.value2);
			ps.setInt(3, this.value3);
			ps.setLong(4, this.value4);
		}
	}
	
	public static class PSSForStringLongInt implements PreparedStatementSetter {
		private String value1;
		private long value2;
		private int value3;
		
		public PSSForStringLongInt(String value1, long value2, int value3) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.value1);
			ps.setLong(2, this.value2);
			ps.setInt(3, this.value3);
		}
	}
	
	public static class PSSForStringDoubleLongInt implements PreparedStatementSetter {
		private String value1;
		private long value2;
		private long value3;
		private int value4;
		
		public PSSForStringDoubleLongInt(String value1, long value2, long value3, int value4) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.value1);
			ps.setLong(2, this.value2);
			ps.setLong(3, this.value3);
			ps.setInt(4, this.value4);
		}
	}
	
	public static class PSSForStringIntString implements PreparedStatementSetter {
		private String sValue;
		private int rValue;
		private String tValue;
		
		public PSSForStringIntString(String s_value, int r_value, String t_value) {
			this.sValue = s_value;
			this.rValue = r_value;
			this.tValue = t_value;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.sValue);
			ps.setInt(2, this.rValue);
			ps.setString(3, this.tValue);
		}
	}
	
	public static class PSSForSIIS implements PreparedStatementSetter {
		private String s1;
		private int i1;
		private int i2;
		private String s2;
		
		public PSSForSIIS(String s1, int i1, int i2, String s2) {
			this.s1 = s1;
			this.i1 = i1;
			this.i2 = i2;
			this.s2 = s2;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.s1);
			ps.setInt(2, this.i1);
			ps.setInt(3, this.i2);
			ps.setString(4, this.s2);
		}
	}
	
	public static class PSSForStringInts implements PreparedStatementSetter {
		private String s1;
		private Integer ints[];
		
		public PSSForStringInts(String s1, Integer...ints) {
			this.s1 = s1;
			this.ints = ints;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			int count = 1;
			ps.setString(count++, this.s1);
			for( int i = 0 ; i < ints.length ; i++ )  {
				ps.setInt(count++, this.ints[i]);
			}
		}
	}
	
	public static class PSSForIntsStrings implements PreparedStatementSetter {
		int intCount;
		int stringCount;
		Object objects[];
		
		public PSSForIntsStrings(int intCount, int stringCount, Object...objects) {
			this.intCount = intCount;
			this.stringCount = stringCount;
			this.objects = objects;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			int pos = 1;
			
			for( int i = 0 ; i < intCount ; i++ ) ps.setInt(pos++, (Integer)objects[i]);
			for( int i = 0 ; i < stringCount ; i++ ) ps.setString(pos++, (String)objects[i+intCount]);
		}
	}
	
	public static class PSSForStringsInts implements PreparedStatementSetter {
		int stringCount;
		int intCount;
		Object objects[];
		
		public PSSForStringsInts(int stringCount, int intCount, Object...objects) {
			this.stringCount = stringCount;
			this.intCount = intCount;
			this.objects = objects;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			int pos = 1;
			
			for( int i = 0 ; i < stringCount ; i++ ) ps.setString(pos++, (String)objects[i]);
			for( int i = 0 ; i < intCount ; i++ ) ps.setInt(pos++, (Integer)objects[i+stringCount]);
		}
	}
	
	public static class PSSForIntStringInts implements PreparedStatementSetter {
		private int i1;
		private String s1;
		private Integer ints[];
		
		public PSSForIntStringInts(int i1, String s1, Integer...ints) {
			this.i1 = i1;
			this.s1 = s1;
			this.ints = ints;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			int count = 1;
			ps.setInt(count++, this.i1);
			ps.setString(count++, this.s1);
			for( int i = 0 ; i < ints.length ; i++ )  {
				ps.setInt(count++, this.ints[i]);
			}
		}
	}
	
	public static class PSSForTripleIntStrings implements PreparedStatementSetter {
		private int v1;
		private int v2;
		private int v3;
		private String strings[];
		
		public PSSForTripleIntStrings(int v1, int v2, int v3, String...strings) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			this.strings = strings;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			int count = 1;
			ps.setInt(count++, this.v1);
			ps.setInt(count++, this.v2);
			ps.setInt(count++, this.v3);
			for( int i = 0 ; i < strings.length ; i++ )  {
				ps.setString(count++, this.strings[i]);
			}
		}
	}
	
	public static class PSSForStringsIntsStrings implements PreparedStatementSetter {
		int stringCount;
		int intCount;
		int stringCount2;
		Object objects[];
		
		public PSSForStringsIntsStrings(int stringCount, int intCount, int stringCount2, Object...objects) {
			this.stringCount = stringCount;
			this.intCount = intCount;
			this.stringCount2 = stringCount2;
			this.objects = objects;
		}
		
		public void setValues(PreparedStatement ps) throws SQLException {
			int pos = 1;
			
			for( int i = 0 ; i < stringCount ; i++ ) ps.setString(pos++, (String)objects[i]);
			for( int i = 0 ; i < intCount ; i++ ) ps.setInt(pos++, (Integer)objects[i+stringCount]);
			for( int i = 0 ; i < stringCount2 ; i++ ) ps.setString(pos++, (String)objects[i+stringCount+intCount]);
		}
	}

}
