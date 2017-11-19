package br.com.project.exception;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.hibernate.SessionFactory;
import org.primefaces.context.RequestContext;

import br.com.framework.hibernate.session.HibernateUtil;

/**
 * 
 * Responsavel por interceptar os erros ExceptionHandlerWrapper
 */

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapperd;

	final FacesContext facesContext = FacesContext.getCurrentInstance();

	// Obter mapa da requisição
	final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();

	// Estado atual da navegação entre as paginas
	final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();

	// Obrigando toda vez que tiver a instanciação da classe, deverá passar um
	// construtor
	public CustomExceptionHandler(ExceptionHandler exceptionHandler) {
		this.wrapperd = exceptionHandler;
	}

	/**
	 * Sobrescreve o método ExceptionHandler que retorna a "pilha" de exceções
	 * 
	 * @return wrapperd
	 */
	@Override
	public ExceptionHandler getWrapped() {
		return wrapperd;
	}

	/**
	 * Sobrescreve mo méto handle que é responsavel por manipular as exceções do
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
				//Não pode ser removido por ter chave estranjeira
				if(exeption!= null && exeption.getMessage() !=null
						&& exeption.getMessage().indexOf("ConstraintViolationException")!=-1){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage
							.SEVERITY_WARN,"Registro não pode ser removido por estar associado.",""));
				} else 	if(exeption!= null && exeption.getMessage() !=null
						&& exeption.getMessage().indexOf("org.hibernate.StaleObjectStateException")!=-1){
					
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage
							.SEVERITY_ERROR,"Registro foi atualizado ou excluido por outro usuario. Consulte novamente.",""));
				} else{
					//Informa o usuario do erro
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage
							.SEVERITY_FATAL,"O sistema se recuperou de um erro inesperado.",""));
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage
							.SEVERITY_INFO,"Você pode contiunar usando o sistema normalmente.",""));
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage
							.SEVERITY_FATAL,"Erro foi causado por:\n"+exeption.getMessage(),""));

					//PrimeFaces
					//Alert apenas é exibido se a pagina nõ redirecionar
					RequestContext.getCurrentInstance().execute("alert('O sistema se recuperou de um erro inseperado')");
					
					RequestContext.getCurrentInstance().
					showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,"Erro","O sistema se recuperou de um erro inseperado"));
					//Redireciona para a pagina de erro
					navigationHandler.handleNavigation(facesContext, null, "/error/erro.jsf?faces-redirect=true&expired=true");
				}
				//Renderiaza a pagina de erro e exibe as messagens
				facesContext.renderResponse();

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
