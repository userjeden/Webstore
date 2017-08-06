package pl.spring.demo.constants;

import java.io.Serializable;

/**
 * Class containing constants for Model
 * 
 * @author mmotowid
 *
 */
public final class ViewNames implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String FIND_BOOK = "findBook";
	public static final String ADD_BOOK = "addBook";
	public static final String ALL_BOOKS = "books";
	public static final String ONE_BOOK= "book";
	
	public static final String WELCOME = "welcome";
	public static final String LOGIN = "login";
	
	public static final String ERROR_403 = "403";
	public static final String ERROR_500 = "500";
}
