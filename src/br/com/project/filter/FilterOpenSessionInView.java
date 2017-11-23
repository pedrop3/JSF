package br.com.project.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.filter.DelegatingFilterProxy;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.framework.utils.UtilFramework;
import br.com.project.listener.ContextLoaderListenerUtils;
import br.com.project.model.classes.Entidade;

@WebFilter(filterName = "conexaoFilter")
public class FilterOpenSessionInView extends DelegatingFilterProxy implements Serializable {

	private static final long serialVersionUID = 1L;

	private static SessionFactory sf;

	// Executa apenas uma vez
	// Quando o servidor estiver subindo a aplica��o esse metodo ser� iniciada.
	@Override
	protected void initFilterBean() throws ServletException {
		sf = HibernateUtil.getSessionFactory();

		super.initFilterBean();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// JDBC Spring -Ter um status
		BasicDataSource springDataSource = (BasicDataSource) ContextLoaderListenerUtils.getBean("springDataSource");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		// Gerenciador de transa��o
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(springDataSource);
		TransactionStatus status = transactionManager.getTransaction(def);

		try {

			request.setCharacterEncoding("UTF-8"); // Define a codifica��o

			// Captura o usuario que fez a altera��o
			HttpServletRequest request2 = (HttpServletRequest) request;
			HttpSession sessao = request2.getSession();
			Entidade userLogadoSessao = (Entidade) sessao.getAttribute("userLogadoSessao");

			if (userLogadoSessao != null) {
				UtilFramework.getThreadLocal().set(userLogadoSessao.getEnt_codigo());

			}
			// Iniciar uma transa��o
			sf.getCurrentSession().beginTransaction();

			// Antes de executar a��o (Request)
			chain.doFilter(request, response);// Executa nossa a��o no servidor
			// Apos executar a��o (Resposta)

			transactionManager.commit(status);

			if (sf.getCurrentSession().getTransaction().isActive()) {
				sf.getCurrentSession().flush();
				sf.getCurrentSession().getTransaction().commit();
			}
			if (sf.getCurrentSession().isOpen()) {
				sf.getCurrentSession().close();
			}

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html/charset=UTF-8");

		} catch (Exception e) {
			transactionManager.rollback(status);
			e.printStackTrace();

			if (sf.getCurrentSession().getTransaction().isActive()) {
				sf.getCurrentSession().getTransaction().rollback();
			}
			if (sf.getCurrentSession().isOpen()) {
				sf.getCurrentSession().close();
			}

		} finally {
			if (sf.getCurrentSession().isOpen()) {
				if (sf.getCurrentSession().beginTransaction().isActive()) {
					sf.getCurrentSession().flush();
					sf.getCurrentSession().clear();
					
				}
				if (sf.getCurrentSession().isOpen()) {
					sf.getCurrentSession().close();
				}
			}
		}

	}

}
