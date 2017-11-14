package br.com.framework.implementacao.crud;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.regexp.internal.recompile;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.framework.interfac.crud.InterfaceCrud;
import sun.print.resources.serviceui;

@Component
@Transactional
public class ImplementacaoCrud<T> implements InterfaceCrud<T> {

	private static final long serialVersionUID = 1L;

	// Irá carregar apenas um instancia para aplicação
	private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	// Criação de injeção de dependencias
	@Autowired
	private JdbcTempleImpl jdbcTemplate;
	@Autowired
	private SimpleJdbcTempleImpl simpleJdbcTemplate;
	@Autowired
	private SimpleJdbcInsertImplents simpleJdbcInsert;

	private SimpleJdbcTempleImpl getSimpleJdbcTemplate1() {
		return simpleJdbcTemplate;
	}

	@Override
	public void Save(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().save(obj);
		executeFlushSession();
	}

	@Override
	public void persist(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().persist(obj);
		executeFlushSession();

	}

	// Não retorna e verifica se já existe
	@Override
	public void saveOrUpdate(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().saveOrUpdate(obj);
		executeFlushSession();

	}

	@Override
	public void update(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().update(obj);
		executeFlushSession();
	}

	@Override
	public void delete(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().delete(obj);
		executeFlushSession();

	}

	// Atualiza ou salva objeto; Retorna o objeto
	@Override
	public T merge(T obj) throws Exception {
		validaSessionFactory();
		obj = (T) sessionFactory.getCurrentSession().merge(obj);
		executeFlushSession();
		return obj;
	}

	@Override
	public List<T> findList(Class<T> entidade) throws Exception {
		validaSessionFactory();
		StringBuilder query = new StringBuilder();
		query.append(" select distinct(entity) from ").append(entidade.getSimpleName()).append(" entity ");
		List<T> lista = sessionFactory.getCurrentSession().createQuery(query.toString()).list();

		return lista;
	}

	@Override
	public Object findById(Class<T> entidade, Long id) throws Exception {
		validaSessionFactory();
		Object obj = sessionFactory.getCurrentSession().load(getClass(), id);

		return obj;
	}

	@Override
	public T findByPorId(Class<T> entidade, Long id) throws Exception {
		validaSessionFactory();
		T obj = (T) sessionFactory.getCurrentSession().load(getClass(), id);

		return obj;
	}

	@Override
	public List<T> findListByQueryDinamica(String s) throws Exception {
		validaSessionFactory();

		List<T> lista = new ArrayList<T>();
		lista = sessionFactory.getCurrentSession().createQuery(s).list();

		return null;
	}

	/**
	 * HQL
	 */
	@Override
	public void executeUpdateQueruDinamica(String s) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().createQuery(s).executeUpdate();
		executeFlushSession();
	}

	/**
	 * SQL Puro
	 */
	@Override
	public void executeUpdateSQLDinamica(String s) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().createSQLQuery(s).executeUpdate();
		executeFlushSession();
	}

	@Override
	public void clearSession() throws Exception {
		sessionFactory.getCurrentSession().clear();
	}

	/**
	 * Identificar o objeto
	 */
	@Override
	public void evict(Object objs) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().evict(objs);
	}

	/**
	 * Retornar sessão
	 */
	@Override
	public Session getSession() throws Exception {
		validaSessionFactory();
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<?> getListSQLDinamica(String sql) throws Exception {
		List<?> lista = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return lista;
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public SimpleJdbcTemplate getSimpleJdbcTemplate() {

		return simpleJdbcTemplate;
	}

	@Override
	public SimpleJdbcInsert getSimpleJdbcInsert() {
		return simpleJdbcInsert;
	}

	@Override
	public Long totalRegistro(String table) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(1) from ").append(table);
		return jdbcTemplate.queryForLong(sql.toString());
	}

	// Criar tabela generica de consulta
	@Override
	public Query obterQuery(String query) throws Exception {
		validaSessionFactory();
		Query queryReturn = sessionFactory.getCurrentSession().createQuery(query.toString());
		return queryReturn;
	}

	/**
	 * Realiza consulta no banco de dados iniciando o carregamento a partir do
	 * registro passado no paramentro -> iniciaNoRegistro e obtendo maximo de 
	 * resultados passados em -> maximoResultado
	 * @param query
	 * @param iniciaNoRegistro
	 * @param MaximoResultado
	 * @return List<T>
	 * @throws Exception
	 * @author pedro.martins
	 * 
	 */

	@Override
	public List<T> findListByQueryDinamica(String query, int iniciaNoRegistro, int MaximoResultado) throws Exception {
		validaSessionFactory();
		
		List<T> lista = new ArrayList<T>();
		
		lista = sessionFactory.getCurrentSession().createQuery(query).setFirstResult(iniciaNoRegistro).
				setMaxResults(MaximoResultado).list();
	    return lista;
	}

	private void validaSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = HibernateUtil.getSessionFactory();
		}
		validarTrasaction();
	}

	/**
	 * Validar transação do hibernate
	 */
	private void validarTrasaction() {
		if (!sessionFactory.getCurrentSession().getTransaction().isActive()) {
			sessionFactory.getCurrentSession().beginTransaction();
		}
	}

	/**
	 * Filtro que irá intercipatar todas a transições
	 */
	private void commitProcessoAjax() {
		sessionFactory.getCurrentSession().beginTransaction().commit();
	}

	private void rollbackProcessoAjax() {
		sessionFactory.getCurrentSession().beginTransaction().rollback();
	}

	/**
	 * Roda instantaneamente o SQL no banco de dados
	 */
	private void executeFlushSession() {
		sessionFactory.getCurrentSession().flush();
	}

	// Traz mais de um registro em um SQL dinamica
	@Override
	public List<Object[]> getListSQLDinamicaArray(String sql) throws Exception {
		validaSessionFactory();

		List<Object[]> lista = (List<Object[]>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return lista;
	}

}
