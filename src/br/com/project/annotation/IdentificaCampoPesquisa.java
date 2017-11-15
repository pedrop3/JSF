package br.com.project.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dell
 * https://docs.oracle.com/javase/7/docs/api/java/lang/annotation/ElementType.html#TYPE
 */
@Target(value = ElementType.FIELD) //Restri��e - anota��o seja permitida apenas em cima de classe ou enum
@Retention(value = RetentionPolicy.RUNTIME) //As anota��es devem ser gravadas no arquivo de classe pelo compilador e retido pela VM em tempo de execu��o, para que elas possam ser lidas de forma reflexiva
@Documented
public abstract @interface IdentificaCampoPesquisa {
	
	String descricaoCampo(); //Descrocao do campo para o view
	String campoConsulta(); // Campo do banco
	int principal() default 10000; //Posi��o que ir� aparecer  no compo - Por default ir� ser 10000
	
}
