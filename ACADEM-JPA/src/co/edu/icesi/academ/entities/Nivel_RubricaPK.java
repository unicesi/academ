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
 * The primary key class for the Niveles_Rubricas database table.
 * 
 */
@Embeddable
public class Nivel_RubricaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int id;

	private int criterio;

	private int programa;

	private String tema;

	public Nivel_RubricaPK() {
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCriterio() {
		return this.criterio;
	}
	public void setCriterio(int criterio) {
		this.criterio = criterio;
	}
	public int getPrograma() {
		return this.programa;
	}
	public void setPrograma(int programa) {
		this.programa = programa;
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
		if (!(other instanceof Nivel_RubricaPK)) {
			return false;
		}
		Nivel_RubricaPK castOther = (Nivel_RubricaPK)other;
		return 
			(this.id == castOther.id)
			&& (this.criterio == castOther.criterio)
			&& (this.programa == castOther.programa)
			&& this.tema.equals(castOther.tema);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.criterio;
		hash = hash * prime + this.programa;
		hash = hash * prime + this.tema.hashCode();
		
		return hash;
	}
}