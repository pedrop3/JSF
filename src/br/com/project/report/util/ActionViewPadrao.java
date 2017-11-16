package br.com.project.report.util;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import br.com.project.util.all.EstatusPersistencia;


public interface ActionViewPadrao extends Serializable {
	abstract void limparLista() throws Exception;

	abstract String save() throws Exception;

	abstract void saveNotReturn() throws Exception;

	abstract void savaEdit() throws Exception;

	abstract void excluir() throws Exception;

	abstract String ativar() throws Exception;

	/**@PostConstruct Realiza inicialização de metodos, valores ou variais
	 *
	 * @throws Exception
	 */
	@PostConstruct
	abstract String novo() throws Exception;

	abstract String editar() throws Exception;

	abstract void setarVariaveisNulas() throws Exception;

	abstract void consultarEntidade() throws Exception;

	abstract void statusOperation(EstatusPersistencia a) throws Exception;
	
	abstract String redirecinarNewEntidade() throws Exception;
	
	abstract String redirecinarFindEntidade() throws Exception;
	
	abstract void addMsg(String msg); 
}
