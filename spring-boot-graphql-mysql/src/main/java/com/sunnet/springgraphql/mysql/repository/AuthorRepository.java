package com.sunnet.springgraphql.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sunnet.springgraphql.mysql.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}