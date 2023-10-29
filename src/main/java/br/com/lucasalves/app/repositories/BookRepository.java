package br.com.lucasalves.app.repositories;

import br.com.lucasalves.app.model.Book;
import br.com.lucasalves.app.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
