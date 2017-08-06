package pl.spring.demo.web.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.spring.demo.constants.ModelConstants;
import pl.spring.demo.constants.ViewNames;
import pl.spring.demo.controller.BookController;
import pl.spring.demo.enumerations.BookStatus;
import pl.spring.demo.searchcriteria.SearchCriteria;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;


@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

	@Mock
	BookService bookService;

	@InjectMocks
	BookController bookController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/templates/");
		viewResolver.setSuffix(".html");
		mockMvc = MockMvcBuilders.standaloneSetup(bookController)
				.setViewResolvers(viewResolver).build();
	}

	
	@Test
	public void shouldPrepareAllBooksPage() throws Exception {

		// given
		List<BookTo> mockedBooks = new ArrayList<BookTo>();
		when(bookService.findAllBooks()).thenReturn(mockedBooks);

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/all"));

		// then
		resultActions.andExpect(view().name(ViewNames.ALL_BOOKS))
				.andExpect(model().attribute(ModelConstants.HINT_MESSAGE, "Information about all books"))
				.andExpect(model().attribute(ModelConstants.BOOKS_LIST, mockedBooks));
	}

	
	@Test
	public void shouldProceedDeleteOperation() throws Exception {

		// given
		String id = "1";
		List<BookTo> mockedBooks = new ArrayList<BookTo>();
		when(bookService.findAllBooks()).thenReturn(mockedBooks);

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/delete").param("id", id));

		// then
		resultActions.andExpect(view().name(ViewNames.ALL_BOOKS))
				.andExpect(model().attribute(ModelConstants.HINT_MESSAGE, "Books of id: " + id + " deleted !"))
				.andExpect(model().attribute(ModelConstants.BOOKS_LIST, mockedBooks));
	}

	
	@Test
	public void shouldPrepareAddBookPage() throws Exception {

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/add"));

		// then
		resultActions.andExpect(view().name(ViewNames.ADD_BOOK))
				.andExpect(model().attribute(ModelConstants.HINT_MESSAGE, "Please provide input"))
				.andExpect(model().attribute(ModelConstants.NEW_BOOK, new BookTo()));
	}

	
	@Test
	public void shouldPrepareNextAddBookPageAfterCompleteInput() throws Exception {

		// given
		BookTo book = new BookTo(1L, "title", "author", BookStatus.FREE);

		// when
		ResultActions resultActions = mockMvc.perform(post("/books/add")
				.flashAttr(ModelConstants.NEW_BOOK, book));

		// then
		resultActions.andExpect(view().name(ViewNames.ADD_BOOK))
				.andExpect(model().attribute(ModelConstants.HINT_MESSAGE, "Book saved successfully"))
				.andExpect(model().attribute(ModelConstants.NEW_BOOK, new BookTo()));
	}

	
	@Test
	public void shouldPrepareNextAddBookPageAfterNOTCompleteInput() throws Exception {

		// given
		BookTo book = new BookTo(1L, "", "author", BookStatus.FREE);

		// when
		ResultActions resultActions = mockMvc.perform(post("/books/add")
				.flashAttr(ModelConstants.NEW_BOOK, book));

		// then
		resultActions.andExpect(view().name(ViewNames.ADD_BOOK))
				.andExpect(model().attribute(ModelConstants.HINT_MESSAGE, "Input NOT complete !!!"))
				.andExpect(model().attribute(ModelConstants.NEW_BOOK, book));
	}

	
	@Test
	public void shouldPrepareFindBookPage() throws Exception {

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/find"));

		// then
		resultActions.andExpect(view().name(ViewNames.FIND_BOOK))
				.andExpect(model().attribute(ModelConstants.HINT_MESSAGE, "Please provide input"))
				.andExpect(model().attribute(ModelConstants.NEW_CRIT, new SearchCriteria()));
	}

	
	@Test
	public void shouldPrepareNextFindBookPages() throws Exception {

		// given
		SearchCriteria criteria = new SearchCriteria("D", "Mickiewicz");
		String[] criteriaArray = { criteria.getAuthors(), criteria.getTitle() };
		List<BookTo> booksFound = new ArrayList<BookTo>();
		when(bookService.findBooksByCriteria(criteriaArray)).thenReturn(booksFound);

		// when
		ResultActions resultActions = mockMvc.perform(post("/books/find")
				.flashAttr(ModelConstants.NEW_CRIT, criteria));

		// then
		resultActions.andExpect(view().name(ViewNames.ALL_BOOKS))
				.andExpect(model().attribute(ModelConstants.HINT_MESSAGE,
						"Books for author: " + criteriaArray[0] + " title: " + criteriaArray[1]))
				.andExpect(model().attribute(ModelConstants.BOOKS_LIST, booksFound));
	}

}

