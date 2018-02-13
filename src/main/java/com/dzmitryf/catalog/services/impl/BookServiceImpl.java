package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.dao.BookDao;
import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Book save(Book book) {
        entityManager.persist(book);
        return book;
    }

    public Book findBookByName(String name) {
        return bookDao.findBookByName(name);
    }

    public List<Book> getBooksByCountPagesDesc() {
        return bookDao.getBooksByCountPagesDesc();
    }

    public List<Book> getBooksByAuthorName(String authorName) {

        Query query = entityManager.createNativeQuery("SELECT * FROM hbschema.books WHERE author_name = ?1", Book.class);
        query.setParameter(1, authorName);
        List<Book> books = (List<Book>) query.getResultList();

        return books;
    }
}
