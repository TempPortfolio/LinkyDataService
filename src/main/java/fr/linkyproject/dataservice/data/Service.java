package fr.linkyproject.dataservice.data;

import java.util.List;

/**
 * Déclaration des méthodes d'un service permettant
 * l'accès en lecture et écriture d'objets de type T.
 * 
 * @param <T> donnée à lire ou écrire
 */
public interface Service<T> {
	public void register(T object) throws ServiceException;
	
	public List<T> get(Object... args) throws ServiceException;
}