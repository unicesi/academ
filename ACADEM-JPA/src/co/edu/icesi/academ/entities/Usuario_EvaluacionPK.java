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