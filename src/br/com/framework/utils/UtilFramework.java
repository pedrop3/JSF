package br.com.framework.utils;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class UtilFramework implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * Responsavel por identificar o usuario que está realizando a requisição.
	 */
	private static ThreadLocal<Long>  threadLocal = new ThreadLocal<Long>();
	//Para não existir concorrencia
	public synchronized static ThreadLocal<Long> getThreadLocal(){
		return threadLocal;
	}
}
