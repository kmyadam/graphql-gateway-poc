package com.graphqljava.tutorial.bookdetails.model;

public class Book {
	private String id;
	private String name;
	private int pageCount;
	private Author author;

	public Book() {}
	
	public Book(String id) {
		this.id = id;
	}
	
	public Book(String id, String name, int pageCount, Author author) {
		this.id = id;
		this.name = name;
		this.pageCount = pageCount;
		this.author = author;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the pageCount
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount the pageCount to set
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * @return the author
	 */
	public Author getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", pageCount=" + pageCount + ", author=" + author + "]";
	}

}
