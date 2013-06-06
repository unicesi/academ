/**
* Copyright © 2013 Universidad Icesi
* 
* This file is part of ACADEM.
* 
* ACADEM is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* ACADEM is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with ACADEM.  If not, see <http://www.gnu.org/licenses/>.
**/

package co.edu.icesi.academ.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Calificaciones_Roles database table.
 * 
 */
@Embeddable
public class Calificacion_RolPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int evaluacion;

	private int nivelDeConocimiento;

	private String tema;

	private String evaluador;

	private String rol;

	public Calificacion_RolPK() {
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
	public String getRol() {
		return this.rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Calificacion_RolPK)) {
			return false;
		}
		Calificacion_RolPK castOther = (Calificacion_RolPK)other;
		return 
			(this.evaluacion == castOther.evaluacion)
			&& (this.nivelDeConocimiento == castOther.nivelDeConocimiento)
			&& this.tema.equals(castOther.tema)
			&& this.evaluador.equals(castOther.evaluador)
			&& this.rol.equals(castOther.rol);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.evaluacion;
		hash = hash * prime + this.nivelDeConocimiento;
		hash = hash * prime + this.tema.hashCode();
		hash = hash * prime + this.evaluador.hashCode();
		hash = hash * prime + this.rol.hashCode();
		
		return hash;
	}
}