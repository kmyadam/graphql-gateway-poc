package com.graphqljava.tutorial.authordetails;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.graphqljava.tutorial.authordetails.model.Author;

import graphql.schema.DataFetcher;

@Component
public class GraphQLDataFetchers {

	/*
	 * private static List<Map<String, String>> books = Arrays.asList(
	 * ImmutableMap.of("id", "book-1", "name",
	 * "Harry Potter and the Philosopher's Stone", "pageCount", "223", "authorId",
	 * "author-1"), ImmutableMap.of("id", "book-2", "name", "Moby Dick",
	 * "pageCount", "635", "authorId", "author-2"), ImmutableMap.of("id", "book-3",
	 * "name", "Interview with the vampire", "pageCount", "371", "authorId",
	 * "author-3") );
	 */

    private static List<Author> authors = Arrays.asList(
            new Author(1L, "Joanne", "Rowling"),
    		new Author(2L, "Herman", "Melville"),
			new Author(3L, "Anne", "Rice")
    );

	/*
	 * public DataFetcher getBookByIdDataFetcher() { return dataFetchingEnvironment
	 * -> { String bookId = dataFetchingEnvironment.getArgument("id"); return books
	 * .stream() .filter(book -> book.get("id").equals(bookId)) .findFirst()
	 * .orElse(null); }; }
	 */

    public DataFetcher getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
        	String authorId = dataFetchingEnvironment.getArgument("id");
            return authors
                    .stream()
                    .filter(author -> author.getId().equals(Long.parseLong(authorId)))
                    .findFirst()
                    .orElse(null);
        };
    }
}
