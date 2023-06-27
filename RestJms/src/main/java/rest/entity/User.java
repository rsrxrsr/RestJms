package rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="Usuario")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @Column(name="usuario") 
	private String user;
	private String password;
	private String roles;
	private String empresa;
	private String estatus;
	
	/* Getter y Setter
	  public Long getId() {
	    return id;
	  }
	  public void setId(Long id) {
	    this.id = id;
	  }
	
	  public String getName() {
	    return name;
	  }
	  public void setName(String name) {
		this.name = name;
	  }	
	  public String getPassword() {
		return password;
	  }
	  public void setPassword(String password) {
		this.password = password;
	  }
	  public String getEmail() {
	    return email;
	  }
	  public void setEmail(String email) {
	    this.email = email;
	  }
	  public String getRoles() {
	    return roles;
	  }
	  public void setRoles(String roles) {
	    this.roles = roles;
	  }
	  */

}
