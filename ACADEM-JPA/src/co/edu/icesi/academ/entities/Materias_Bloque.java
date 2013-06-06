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
import java.util.List;


/**
 * The persistent class for the Materias_Bloques database table.
 * 
 */
@Entity
@Table(name="Materias_Bloques")
public class Materias_Bloque implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Materias_BloquePK id;

	//bi-directional many-to-one association to Alcance
	@OneToMany(mappedBy="materiaBloque")
	private List<Alcance> alcances;

	public Materias_Bloque() {
	}

	public Materias_BloquePK getId() {
		return this.id;
	}

	public void setId(Materias_BloquePK id) {
		this.id = id;
	}

	public List<Alcance> getAlcances() {
		return this.alcances;
	}

	public void setAlcances(List<Alcance> alcances) {
		this.alcances = alcances;
	}

}