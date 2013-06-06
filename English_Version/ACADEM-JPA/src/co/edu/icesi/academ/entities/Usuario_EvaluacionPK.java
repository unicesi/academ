package co.edu.icesi.academ.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Usuarios_Evaluaciones database table.
 * 
 */
@Embeddable
public class Usuario_EvaluacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String usuario;

	private int evaluacion;

	public Usuario_EvaluacionPK() {
	}
	public String getUsuario() {
		return this.usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getEvaluacion() {
		return this.evaluacion;
	}
	public void setEvaluacion(int evaluacion) {
		this.evaluacion = evaluacion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Usuario_EvaluacionPK)) {
			return false;
		}
		Usuario_EvaluacionPK castOther = (Usuario_EvaluacionPK)other;
		return 
			this.usuario.equals(castOther.usuario)
			&& (this.evaluacion == castOther.evaluacion);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.usuario.hashCode();
		hash = hash * prime + this.evaluacion;
		
		return hash;
	}
}