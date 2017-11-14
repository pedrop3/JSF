package br.com.framework.interfac.crud;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component // Injeção de dependencia do spring
@Transactional // Transação com o banco de dados
public interface InterfaceCrud<T> extends Serializable {
	// Metodo generico que pode trabalhar com qualquer objeto <T>

	// Salvar dados
	// throws Exception - Enviar para usuario
	void Save(T obj) throws Exception;

	void persist(T obj) throws Exception;

	// Salva ou atualizada
	void saveOrUpdate(T obj) throws Exception;

	// Realiza o update/atualização de dados
	void update(T obj) throws Exception;

	// Realiza o delete dos dados
	void delete(T obj) throws Exception;

	// Merge salve e retorna o objeto em estado persistente
	T merge(T obj) throws Exception;

	// Carrega lista de dados de um determinda classe
	List<T> findList(Class<T> objs) throws Exception;

	// Buscar objeto por ID
	Object findById(Class<T> entidade, Long id) throws Exception;

	T findByPorId(Class<T> entidade, Long id) throws Exception;

	// Busca Query dinamica
	List<T> findListByQueryDinamica(String s) throws Exception;

	// Executar update de query dinamica - HQL
	void executeUpdateQueruDinamica(String s) throws Exception;
	
	// Executar update de query dinamica - SQL puro
	void executeUpdateSQLDinamica(String s) throws Exception;
	
	//Limpa a sessão de Hibernate
	void clearSession() throws Exception;
	
	//Retira objeto sessão hibernate
	void evict (Object objs) throws Exception;
	
	//Retorna sessão do Hibernate
	Session getSession() throws Exception;
	
	List<?> getListSQLDinamica(String sql) throws Exception;
	
	//JDBC Spring
	JdbcTemplate getJdbcTemplete();
	
	SimpleJdbcTemplate getSimpleJdbcTemplate();
	
	SimpleJdbcInsert getSimpleJdbcInsert();
	
	//Total registro de um tabela
	Long totalRegistro(String table) throws Exception;
	
	Query obterQuery(String query) throws Exception;
	
	
	//Carregamento dinamico com JSF e PrimeFaces
	List<T> findListByQueryDinamica(String query, int iniciaNoRegistro, int MaximoResultado) throws Exception;
	
	
	
	
	
}
