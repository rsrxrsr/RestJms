package rest.repository;

import java.util.Optional;

import rest.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "/user")
//public interface UserRepository extends CrudRepository<User, Long> {
public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByUser(@Param("user") String user);
	public Optional<User> findByUserAndPassword(@Param("name") String user, @Param("password") String password);

}

/*
 * 
import org.springframework.data.jpa.repository.Query;

@Query(value = "SELECT u FROM User u"
		+ " WHERE u.name = :name "
		+ " AND   u.password = :password "
		+ " AND   u.name LIKE '%' || :name || '%'"
public User login(@Param("name") String name, @Param("password") String password);
 
*/
