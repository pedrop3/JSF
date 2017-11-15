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
@Target(value = ElementType.FIELD) //Restriçõe - anotação seja permitida apenas em cima de classe ou enum
@Retention(value = RetentionPolicy.RUNTIME) //As anotações devem ser gravadas no arquivo de classe pelo compilador e retido pela VM em tempo de execução, para que elas possam ser lidas de forma reflexiva
@Documented
public abstract @interface IdentificaCampoPesquisa {
	
	String descricaoCampo(); //Descrocao do campo para o view
	String campoConsulta(); // Campo do banco
	int principal() default 10000; //Posição que irá aparecer  no compo - Por default irá ser 10000
	
}
