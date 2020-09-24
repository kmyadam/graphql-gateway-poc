package com.graphqljava.tutorial.bookdetails.model;

import java.util.List;

public class Author {
	private Long id;
	private List<Book> books;
	
	public Author() {}
	
	public Author(Long id) {
		this.id = id;
	}
	
	public Author(Long id, List<Book> books) {
		this.id = id;
		this.books = books;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * @param books the books to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", books=" + books + "]";
	}
	
}
