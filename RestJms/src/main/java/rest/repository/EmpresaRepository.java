package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import rest.entity.Empresa;
 
@RepositoryRestResource(path = "/empresa")
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {}
//public interface EmpresaRepository extends CrudRepository<Empresa, Long> {}
