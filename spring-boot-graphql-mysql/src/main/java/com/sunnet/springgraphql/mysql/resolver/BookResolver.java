package com.sunnet.springgraphql.mysql.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.coxautodev.graphql.tools.GraphQLResolver;
import com.sunnet.springgraphql.mysql.model.Author;
import com.sunnet.springgraphql.mysql.model.Book;
import com.sunnet.springgraphql.mysql.repository.AuthorRepository;

@Component
public class BookResolver implements GraphQLResolver<Book>
{
	@Autowired
	private AuthorRepository authorRepository;

	public BookResolver(AuthorRepository authorRepository)
	{
		this.authorRepository = authorRepository;
	}

	public Author getAuthor(Book book)
	{
		return authorRepository.findById(book.getAuthor().getId()).orElseThrow(null);
	}
}
