package br.com.project.acesso;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controle de acesso do sistema
 * @author pedro martins
 *
 */
public enum Permissao {
	ADMIN("ADMIN","Administrador"),
	USER("USER","Usuário Padrão"),
	CADASTRO_ACESSAR("CADASTRO_ACESSAR","Cadastro - acessar"),
	FINANCEIRO_ACESSAR("FINANCEIRO_ACESSAR","Financeiro - Acessar"),
	MENSSAGEM_ACESSAR("MENSSAGEM_ACESSAR","Messagem recebida- Acessar"),
	
	BAIRRO_ACESSAR("BAIRRO_ACESSAR","Bairro Acessar"),
	BAIRRO_NOVO("BAIRRO_NOVO","Bairro - Novo"),
	BAIRRO_EDITAR("BAIRRO_EDITAR","Bairro Editar"),
	BAIRRO_EXCLUIR("BAIRRO_EXCLUIR","Bairro Excluir");
	
	private String valor="";
	private String descricao="";
	
	private Permissao(String name, String descricao){
		this.valor=name;
		this.descricao=descricao;	
	}
	
	private Permissao() {
	
	}
	
	
	public String getDescricao() {
		return descricao;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	/**
	 * Retorna o valor - CADASTRO_ACESSAR
	 */
	@Override
	public String toString() {
		return getValor();
	}
	/**
	 * Listando os enum em ordem alfabetica - Realiza um comparação e depois ordenação dos enum
	 * declarados permissoes
	 * @return 
	 */
	public static List<Permissao> getListPermissaso(){
		List<Permissao> permissoes = new ArrayList<Permissao>();
		for(Permissao permissao : Permissao.values()){
			permissoes.add(permissao);
		}
		java.util.Collections.sort(permissoes, new Comparator<Permissao>(){

			@Override
			public int compare(Permissao o1, Permissao o2) {
				// TODO Auto-generated method stub
				return new Integer(o1.ordinal()).compareTo(o2.ordinal());
			}
			
		});
		
		return permissoes;
	}
}
