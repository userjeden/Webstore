package pl.spring.demo.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.spring.demo.constants.ModelConstants;
import pl.spring.demo.constants.ViewNames;
import pl.spring.demo.searchcriteria.SearchCriteria;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@RequestMapping
	public String list() {
		return "redirect:/books/all";
	}

	@RequestMapping(value = "/book")
	public String bookDetails(@RequestParam("id") Long id, Model model) {
		model.addAttribute(ModelConstants.BOOK_SINGLE, bookService.findBookByID(id));
		return ViewNames.ONE_BOOK;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String allBooks(Model model) {
		model.addAttribute(ModelConstants.HINT_MESSAGE, "Information about all books");
		model.addAttribute(ModelConstants.BOOKS_LIST, bookService.findAllBooks());
		return ViewNames.ALL_BOOKS;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addBook(Model model) {
		model.addAttribute(ModelConstants.HINT_MESSAGE, "Please provide input");
		model.addAttribute(ModelConstants.NEW_BOOK, new BookTo());
		return ViewNames.ADD_BOOK;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addBook(@ModelAttribute("newBook") BookTo newBook, Model model) {

		if (bookInputComplete(newBook)) {
			bookService.saveBook(newBook);
			model.addAttribute(ModelConstants.HINT_MESSAGE, "Book saved successfully");
			model.addAttribute(ModelConstants.NEW_BOOK, new BookTo());
			return ViewNames.ADD_BOOK;

		} else {
			model.addAttribute(ModelConstants.HINT_MESSAGE, "Input NOT complete !!!");
			model.addAttribute(ModelConstants.NEW_BOOK, newBook);
			return ViewNames.ADD_BOOK;
		}
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String findBook(Model model) {
		model.addAttribute(ModelConstants.HINT_MESSAGE, "Please provide input");
		model.addAttribute(ModelConstants.NEW_CRIT, new SearchCriteria());
		return ViewNames.FIND_BOOK;
	}

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public String findBook(@ModelAttribute("newCriteria") SearchCriteria criteria, Model model) {
		String[] critArr = {criteria.getAuthors(), criteria.getTitle()};
		List<BookTo> booksFound = bookService.findBooksByCriteria(critArr);
		String hint = "Books for author: " + critArr[0] + " title: " + critArr[1];
		model.addAttribute(ModelConstants.HINT_MESSAGE, hint);
		model.addAttribute(ModelConstants.BOOKS_LIST, booksFound);
		return ViewNames.ALL_BOOKS;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteBook(@RequestParam("id") Long id, Model model) {
		bookService.deleteBook(id);
		model.addAttribute(ModelConstants.HINT_MESSAGE, "Books of id: " + id + " deleted !");
		model.addAttribute(ModelConstants.BOOKS_LIST, bookService.findAllBooks());
		return ViewNames.ALL_BOOKS;
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex) {
		return ViewNames.ERROR_500;
	}

	private boolean bookInputComplete(BookTo newBook) {
		return !(newBook.getAuthors().isEmpty() || 
				newBook.getTitle().isEmpty() || newBook.getStatus() == null);
	}

}
