package co.edu.icesi.academ.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FactoresDeImpacto database table.
 * 
 */
@Embeddable
public class FactorDeImpactoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String rol;

	private int evaluacion;

	private String tema;

	public FactorDeImpactoPK() {
	}
	public String getRol() {
		return this.rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public int getEvaluacion() {
		return this.evaluacion;
	}
	public void setEvaluacion(int evaluacion) {
		this.evaluacion = evaluacion;
	}
	public String getTema() {
		return this.tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FactorDeImpactoPK)) {
			return false;
		}
		FactorDeImpactoPK castOther = (FactorDeImpactoPK)other;
		return 
			this.rol.equals(castOther.rol)
			&& (this.evaluacion == castOther.evaluacion)
			&& this.tema.equals(castOther.tema);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.rol.hashCode();
		hash = hash * prime + this.evaluacion;
		hash = hash * prime + this.tema.hashCode();
		
		return hash;
	}
}