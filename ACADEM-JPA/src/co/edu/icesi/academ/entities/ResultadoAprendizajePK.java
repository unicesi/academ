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
 * The primary key class for the ResultadosAprendizaje database table.
 * 
 */
@Embeddable
public class ResultadoAprendizajePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int programa;

	private String tema;

	public ResultadoAprendizajePK() {
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
		if (!(other instanceof ResultadoAprendizajePK)) {
			return false;
		}
		ResultadoAprendizajePK castOther = (ResultadoAprendizajePK)other;
		return 
			(this.programa == castOther.programa)
			&& this.tema.equals(castOther.tema);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.programa;
		hash = hash * prime + this.tema.hashCode();
		
		return hash;
	}
}