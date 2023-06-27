package rest.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

/**
 * Entity implementation class for Entity: Empresa
 *
 */

@Entity
@Data
@SuppressWarnings("serial")
public class Empresa implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String empresa;
	private String grupo;
		
	  /* Getter
	  public Long getId() {
	    return id;
	  }

	  // Setter
	  public void setId(Long id) {
	    this.id = id;
	  }

	// Getter
	  public String getEmpresa() {
	    return empresa;
	  }

	  // Setter
	  public void setEmpresa(String empresa) {
	    this.empresa = empresa;
	  }
	
	  // Getter
	  public String getRollEmpresa() {
	    return rollEmpresa;
	  }

	  // Setter
	  public void setRollEmpresa(String rollEmpresa) {
	    this.rollEmpresa = rollEmpresa;
	  }
	  */

}
