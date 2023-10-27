package br.com.lucasalves.app.repositories;

import br.com.lucasalves.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM User u WHERE u.userName =:userName") // JPQL
    User findByUsername(@Param("userName") String userName);
}
