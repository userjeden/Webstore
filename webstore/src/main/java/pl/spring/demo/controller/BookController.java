package pl.spring.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.spring.demo.constants.ModelConstants;
import pl.spring.demo.constants.ViewNames;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;


/**
 * Book controller
 * 
 * @author mmotowid
 */

/*
 * @author wpajzder
 */
@Controller
@RequestMapping("/books")
public class BookController {

	
	@Autowired
	private BookService bookService;
	
	@ModelAttribute("newBook")
	public BookTo construct(){
		return new BookTo();
	}
	
	
	/*
	 * Default Method displays all books
	 */
	@RequestMapping
	public String list(Model model) {
		model.addAttribute(ModelConstants.BOOK_LIST, bookService.findAllBooks());
		return ViewNames.BOOKS;
	}

	/**
	 * Method collects info about all books
	 */
	@RequestMapping("/all")
	public ModelAndView allBooks() {
		ModelAndView modelAndView = new ModelAndView(ViewNames.BOOKS);
		modelAndView.addObject(ModelConstants.BOOK_LIST, bookService.findAllBooks());
		return modelAndView;
	}
	
	/*
	 * Method collects detailed info about specified book
	 */
	@RequestMapping("/book")
	public ModelAndView bookDetails(@RequestParam ("id") Long id){
		ModelAndView modelAndView = new ModelAndView(ViewNames.BOOK);
		modelAndView.addObject(ModelConstants.BOOK, bookService.findBookByID(id));
		return modelAndView;
	}

	/*
	 * Method to initialize the Add Book screen.
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addBook(Model model){
		model.addAttribute(ModelConstants.CREATOR_HINT, "PLEASE PROVIDE INPUT");
		model.addAttribute(ModelConstants.NEW_BOOK, new BookTo());
		return ViewNames.ADD_BOOK;
	}
	
	/*
	 * Method to continue adding books.
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addBook(@ModelAttribute ("newBook") BookTo newBook, ModelMap model){
		ModelAndView modelAndView = new ModelAndView(ViewNames.ADD_BOOK);
		if(bookInputComplete(newBook)){
			bookService.saveBook(newBook);
			System.out.println("book saved !");
			model.addAttribute(ModelConstants.CREATOR_HINT, "BOOK SAVED SUCCESSFULLY");
			modelAndView.addObject(ModelConstants.NEW_BOOK, new BookTo());
			return modelAndView;
		}else{
			model.addAttribute(ModelConstants.CREATOR_HINT, "INPUT NOT COMPLETE !!!");
			return modelAndView;
		}
	}

	
	
	// TODO: here implement methods which displays book info based on query
	// arguments


	/**
	 * Binder initialization
	 */
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setAllowedFields("id", "title", "authors", "status");
	}

	private boolean bookInputComplete(BookTo newBook){
		return !(newBook.getAuthors().isEmpty() || newBook.getTitle().isEmpty() || newBook.getStatus() == null);
	}
	
}
