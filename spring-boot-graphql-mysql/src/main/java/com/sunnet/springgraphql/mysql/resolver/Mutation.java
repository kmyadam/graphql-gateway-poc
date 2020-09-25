package com.sunnet.springgraphql.mysql.resolver;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.sunnet.springgraphql.mysql.model.Author;
import com.sunnet.springgraphql.mysql.model.Book;
import com.sunnet.springgraphql.mysql.repository.AuthorRepository;
import com.sunnet.springgraphql.mysql.repository.BookRepository;
import javassist.NotFoundException;

@Component
public class Mutation implements GraphQLMutationResolver
{
	private AuthorRepository authorRepository;
	private BookRepository bookRepository;

	@Autowired
	public Mutation(AuthorRepository authorRepository, BookRepository bookRepository)
	{
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
	}

	public Author createAuthor(String name, Integer age)
	{
		Author author = new Author();
		author.setName(name);
		author.setAge(age);

		authorRepository.save(author);

		return author;
	}

	public Book createBook(String title, String description, Long authorId)
	{
		Book book = new Book();
		book.setAuthor(new Author(authorId));
		book.setTitle(title);
		book.setDescription(description);

		bookRepository.save(book);

		return book;
	}

	public boolean deleteBook(Long id)
	{
		bookRepository.deleteById(id);
		return true;
	}

	public Book updateBook(Long id, String title, String description) throws NotFoundException
	{
		Optional<Book> optTutorial = bookRepository.findById(id);

		if (optTutorial.isPresent())
		{
			Book book = optTutorial.get();

			if (title != null)
				book.setTitle(title);
			if (description != null)
				book.setDescription(description);

			bookRepository.save(book);
			return book;
		}

		throw new NotFoundException("Not found Book to update!");
	}

}
