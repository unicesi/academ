package co.edu.icesi.academ.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Calificaciones_Roles database table.
 * 
 */
@Entity
@Table(name="Calificaciones_Roles")
public class Calificacion_Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Calificacion_RolPK id;

	public Calificacion_Rol() {
	}

	public Calificacion_RolPK getId() {
		return this.id;
	}

	public void setId(Calificacion_RolPK id) {
		this.id = id;
	}

}