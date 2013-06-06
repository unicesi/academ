package co.edu.icesi.academ.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Calificaciones database table.
 * 
 */
@Embeddable
public class CalificacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int evaluacion;

	private int nivelDeConocimiento;

	private String tema;

	private String evaluador;

	public CalificacionPK() {
	}
	public int getEvaluacion() {
		return this.evaluacion;
	}
	public void setEvaluacion(int evaluacion) {
		this.evaluacion = evaluacion;
	}
	public int getNivelDeConocimiento() {
		return this.nivelDeConocimiento;
	}
	public void setNivelDeConocimiento(int nivelDeConocimiento) {
		this.nivelDeConocimiento = nivelDeConocimiento;
	}
	public String getTema() {
		return this.tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	public String getEvaluador() {
		return this.evaluador;
	}
	public void setEvaluador(String evaluador) {
		this.evaluador = evaluador;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CalificacionPK)) {
			return false;
		}
		CalificacionPK castOther = (CalificacionPK)other;
		return 
			(this.evaluacion == castOther.evaluacion)
			&& (this.nivelDeConocimiento == castOther.nivelDeConocimiento)
			&& this.tema.equals(castOther.tema)
			&& this.evaluador.equals(castOther.evaluador);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.evaluacion;
		hash = hash * prime + this.nivelDeConocimiento;
		hash = hash * prime + this.tema.hashCode();
		hash = hash * prime + this.evaluador.hashCode();
		
		return hash;
	}
}