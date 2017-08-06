package pl.spring.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.mapper.BookMapper;
import pl.spring.demo.repository.BookRepository;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookTo> findAllBooks() {
        return BookMapper.map2To(bookRepository.findAll());
    }

    @Override
    public List<BookTo> findBooksByTitle(String title) {
        return BookMapper.map2To(bookRepository.findBookByTitle(title));
    }

    @Override
    public List<BookTo> findBooksByAuthor(String author) {
        return BookMapper.map2To(bookRepository.findBookByAuthor(author));
    }
    
    @Override
    public BookTo findBookByID(Long id) {
    	List<BookTo> allBooks = findAllBooks();
    	return allBooks.stream().filter(b -> b.getId().equals(id)).findFirst().get();
    }
    
    @Override
    public List<BookTo> findBooksByCriteria(String[] criteriaArray) {
    	String authorCriteria = criteriaArray[0];
    	String titleCriteria = criteriaArray[1];
    	List<BookTo> filtered = findAllBooks().stream()
    			.filter(t ->t.getAuthors().startsWith(authorCriteria))
    			.filter(t ->t.getTitle().startsWith(titleCriteria))
    			.collect(Collectors.toList());
    	return filtered;
    }

    @Override
    @Transactional(readOnly = false)
    public BookTo saveBook(BookTo book) {
        BookEntity entity = BookMapper.map(book);
        entity = bookRepository.save(entity);
        return BookMapper.map(entity);
    }

	@Override
	@Transactional(readOnly = false)
	public void deleteBook(Long id) {
		bookRepository.delete(id);
		
	}
}
