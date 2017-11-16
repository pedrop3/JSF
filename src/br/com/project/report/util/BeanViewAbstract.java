package br.com.project.report.util;

import org.springframework.stereotype.Component;

import br.com.project.util.all.EstatusPersistencia;
import br.com.project.util.all.Messagens;

@Component
public abstract class BeanViewAbstract implements ActionViewPadrao {

	private static final long serialVersionUID = 1L;

	@Override
	public void limparLista() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveNotReturn() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void savaEdit() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String ativar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String novo() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String editar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setarVariaveisNulas() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void consultarEntidade() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void statusOperation(EstatusPersistencia a) throws Exception {
		Messagens.responseOperation(a);
	}

	protected void sucesso() throws Exception{
		statusOperation(EstatusPersistencia.SUCESSO);
	}
	protected void error() throws Exception{
		statusOperation(EstatusPersistencia.ERRO);
	}
	@Override
	public String redirecinarNewEntidade() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirecinarFindEntidade() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMsg(String msg) {
		Messagens.msg(msg);
	}

}
