package br.com.framework.utils;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class UtilFramework implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * Responsavel por identificar o usuario que est� realizando a requisi��o.
	 */
	private static ThreadLocal<Long>  threadLocal = new ThreadLocal<Long>();
	//Para n�o existir concorrencia
	public synchronized static ThreadLocal<Long> getThreadLocal(){
		return threadLocal;
	}
}
