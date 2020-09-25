package com.sunnet.springgraphql.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sunnet.springgraphql.mysql.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>
{

}
