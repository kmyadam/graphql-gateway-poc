package com.sunnet.springgraphql.mysql.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.sunnet.springgraphql.mysql.model.Author;
import com.sunnet.springgraphql.mysql.model.Book;
import com.sunnet.springgraphql.mysql.repository.AuthorRepository;
import com.sunnet.springgraphql.mysql.repository.BookRepository;

@Component
public class Query implements GraphQLQueryResolver
{
	private AuthorRepository authorRepository;
	private BookRepository bookRepository;

	@Autowired
	public Query(AuthorRepository authorRepository, BookRepository bookRepository)
	{
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
	}

	public Iterable<Author> findAllAuthors()
	{
		return authorRepository.findAll();
	}

	public Iterable<Book> findAllBook()
	{
		return bookRepository.findAll();
	}

	public long countAuthors()
	{
		return authorRepository.count();
	}

	public long countBook()
	{
		return bookRepository.count();
	}

}
