package br.com.lucasalves.app.repositories;

import br.com.lucasalves.app.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRespository extends JpaRepository<Person, Long> {

    @Modifying
    @Query(value = "UPDATE Person p set p.enabled = false WHERE p.id =:id") // JPQL
    void disablePerson(@Param("id") Long id);

    @Query(value = "SELECT p FROM Person p WHERE p.firstName LIKE LOWER(CONCAT('%' ,:firstName, '%'))") // JPQL
    Page<Person> findPersonsByName(@Param("firstName") String firstName, Pageable pageable);
}
