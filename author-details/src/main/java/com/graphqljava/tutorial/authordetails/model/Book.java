package com.graphqljava.tutorial.authordetails.model;

public class Book {
	private String id;
	private String name;
	private int pageCount;
	private Long authorId;

	public Book() {}
	
	public Book(String id) {
		this.id = id;
	}
	
	public Book(String id, String name, int pageCount, Long authorId) {
		this.id = id;
		this.name = name;
		this.pageCount = pageCount;
		this.authorId = authorId;
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
	 * @return the authorId
	 */
	public Long getAuthorId() {
		return authorId;
	}

	/**
	 * @param authorId the authorId to set
	 */
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", pageCount=" + pageCount + ", authorId=" + authorId + "]";
	}
	
}
