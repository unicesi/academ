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
 * The primary key class for the Materias_Bloques database table.
 * 
 */
@Embeddable
public class Materias_BloquePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String materia;

	private String bloque;

	public Materias_BloquePK() {
	}
	public String getMateria() {
		return this.materia;
	}
	public void setMateria(String materia) {
		this.materia = materia;
	}
	public String getBloque() {
		return this.bloque;
	}
	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Materias_BloquePK)) {
			return false;
		}
		Materias_BloquePK castOther = (Materias_BloquePK)other;
		return 
			this.materia.equals(castOther.materia)
			&& this.bloque.equals(castOther.bloque);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.materia.hashCode();
		hash = hash * prime + this.bloque.hashCode();
		
		return hash;
	}
}