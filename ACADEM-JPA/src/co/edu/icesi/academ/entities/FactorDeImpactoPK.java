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