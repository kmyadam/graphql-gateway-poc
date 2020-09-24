package com.graphqljava.tutorial.bookdetails;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.graphqljava.tutorial.bookdetails.model.Author;
import com.graphqljava.tutorial.bookdetails.model.Book;

import graphql.schema.DataFetcher;

@Component
public class GraphQLDataFetchers {

    private static List<Book> books = Arrays.asList(
            new Book("book-1", "Harry Potter and the Philosopher's Stone", 223, new Author(1L)),
    		new Book("book-2", "Moby Dick", 635, new Author(1L)),
			new Book("book-3", "Interview with the vampire", 371, new Author(2L))
    );

	/*
	 * private static List<Map<String, String>> authors = Arrays.asList(
	 * ImmutableMap.of("id", "author-1", "firstName", "Joanne", "lastName",
	 * "Rowling"), ImmutableMap.of("id", "author-2", "firstName", "Herman",
	 * "lastName", "Melville"), ImmutableMap.of("id", "author-3", "firstName",
	 * "Anne", "lastName", "Rice") );
	 */

    public DataFetcher getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("id");
            return books
                    .stream()
                    .filter(book -> book.getId().equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }
    
    public DataFetcher getBooksByAuthorIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String authorId = dataFetchingEnvironment.getArgument("authorId");
            return books
                    .stream()
                    .filter(book -> book.getAuthor().getId().equals(Long.parseLong(authorId)))
                    .collect(Collectors.toList());
        };
    }

	/*
	 * public DataFetcher getAuthorDataFetcher() { return dataFetchingEnvironment ->
	 * { Map<String, String> book = dataFetchingEnvironment.getSource(); String
	 * authorId = book.get("authorId"); return authors .stream() .filter(author ->
	 * author.get("id").equals(authorId)) .findFirst() .orElse(null); }; }
	 */
}
