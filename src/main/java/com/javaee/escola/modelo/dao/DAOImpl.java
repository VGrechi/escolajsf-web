package com.javaee.escola.modelo.dao;

import java.sql.Timestamp;
import java.util.Date;

import com.javaee.escola.utils.RNBancoException;

public class DAOImpl {
	

	public void verificaParametrosDeSaida(Integer codResult) throws RNBancoException {
		if(codResult != 0){
			throw new RNBancoException(codResult);
		}
	}
	
	public String extraiPrimeiroCaractere(String texto){
		if(texto.length() > 0){
			return texto.substring(0,1);
		}else{
			return texto;
		}
	}
	
	public Double seNullEntaoZero(Double objeto){
		if(objeto == null){
			return new Double(0);
		}else{
			return objeto;			
		}
	}
	
	public Long seNullEntaoZero(Long objeto){
		if(objeto == null){
			return new Long(0);
		}else{
			return objeto;			
		}
	}
	
	public Integer seNullEntaoZero(Integer objeto){
		if(objeto == null){
			return new Integer(0);
		}else{
			return objeto;			
		}
	}
	
	protected Date deSQLTimestampParaJavaDate(Timestamp timestamp) {
		if (timestamp == null) return null;
		
		return new Date(timestamp.getTime());
	}
	
	protected Timestamp deJavaDateParaSQLTimestamp(Date date) {
		if (date == null) return null;
		
		return new Timestamp(date.getTime());
	} 
}
