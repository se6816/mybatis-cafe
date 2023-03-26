package com.cafe.classic.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class BooleanIntegerHandler implements TypeHandler<Boolean>{

	@Override
	public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
		int value=0;
		if(parameter) value=1; 
		ps.setInt(i, value);
	}

	@Override
	public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
		boolean result=false;
		if(rs.getInt(columnName)==1) result=true;
		return result;
	}

	@Override
	public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
		boolean result=false;
		if(rs.getInt(columnIndex)==1) result=true;
		return result;
	}

	@Override
	public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
		boolean result=false;
		if(cs.getInt(columnIndex)==1) result=true;
		return result;
	}

}
