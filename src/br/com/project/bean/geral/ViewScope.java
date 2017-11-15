package br.com.project.bean.geral;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;


/**
 * Reimplementa��o dos Scope do Spring
 * @author Dell
 *
 */
public class ViewScope implements Scope, Serializable{

	private static final long serialVersionUID = 1L;
	public static final String VIEW_SCOPE_CALLBACKS ="viewScope.callBacks";
	//Retorna a instancia do scopo
	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Object instance = getViewMap().get(name);
		if(instance == null){
			instance = objectFactory.getObject();
			getViewMap().put(name, instance);
		}
		return null;
	}

	@Override
	public String getConversationId() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
		//Mantem o ID unico para cada scopo de view
		return facesRequestAttributes.getSessionId() + " - "+ facesContext.getViewRoot();
		

	}

	@Override
	public void registerDestructionCallback(String name, Runnable runnable) {
		Map<String, Runnable> callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS) ;
		
		if(callbacks !=null){
			callbacks.put(VIEW_SCOPE_CALLBACKS,runnable);
		}
	}

	@Override
	public Object remove(String name) {
		Object instance = getViewMap().remove(name);
		if(instance != null){
			Map<String, Runnable> callBacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
			if(callBacks != null){
				callBacks.remove(name);
			}
		}
		return instance;
	}

	@Override
	public Object resolveContextualObject(String name) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
	
		return facesRequestAttributes.resolveReference(name);
	}
	
	//PEgar mapa de parametros  JSF
	/**
	 * Retorna o componente raiz que est� associado a esta solicita��o (request)
	 * getViewMap Retorna um Map que atua como a internface para armazenamento de dados
	 * @return
	 */
	private Map<String, Object> getViewMap(){
		return FacesContext.getCurrentInstance() !=null ?
				FacesContext.getCurrentInstance().getViewRoot().getViewMap() : new HashMap<String, Object>();
	}

}
