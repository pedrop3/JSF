package br.com.project.exception;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.hibernate.SessionFactory;

import br.com.framework.hibernate.session.HibernateUtil;

/**
 * 
 * Responsavel por interceptar os erros ExceptionHandlerWrapper
 */

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapperd;

	final FacesContext facesContext = FacesContext.getCurrentInstance();

	// Obter mapa da requisi��o
	final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();

	// Estado atual da navega��o entre as paginas
	final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();

	// Obrigando toda vez que tiver a instancia��o da classe, dever� passar um
	// construtor
	public CustomExceptionHandler(ExceptionHandler exceptionHandler) {
		this.wrapperd = exceptionHandler;
	}

	/**
	 * Sobrescreve o m�todo ExceptionHandler que retorna a "pilha" de exce��es
	 * 
	 * @return wrapperd
	 */
	@Override
	public ExceptionHandler getWrapped() {
		return wrapperd;
	}

	/**
	 * Sobrescreve mo m�to handle que � responsavel por manipular as exce��es do
	 * JSF
	 * 
	 * @throws FacesException
	 */
	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> iterator = getHandledExceptionQueuedEvents().iterator();
		while (iterator.hasNext()) {
			ExceptionQueuedEvent event = iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			// Recuperar a Exception do context
			Throwable exeption = context.getException();

			// Trabalhando a exeption
			try {
				requestMap.put("exceptionMessage", exeption.getMessage());
				
				if(exeption!= null && exeption.getMessage() !=null
						&& exeption.getMessage().indexOf("ConstraintViolationException")!=-1){
					
				}

			} finally {
				SessionFactory sf = HibernateUtil.getSessionFactory();
				if (sf.getCurrentSession().getTransaction().isActive()) {
					sf.getCurrentSession().getTransaction().rollback();
				}
				// Imprimir erro no console
				exeption.printStackTrace();
				iterator.remove();
			}
		}
		//Finalizar
		getWrapped().handle();
	}

}
