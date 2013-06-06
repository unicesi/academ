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

import co.edu.icesi.academ.bo.BloqueBO;

import java.util.List;


/**
 * The persistent class for the Bloques database table.
 * 
 */
@Entity
@Table(name="Bloques")
@NamedQueries({
	@NamedQuery(name="obtenerBloques", query="SELECT e FROM Bloque e")
})
public class Bloque implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String nombre;

	//bi-directional many-to-many association to Programa
	@ManyToMany
	@JoinTable(
		name="Bloques_Programas"
		, joinColumns={
			@JoinColumn(name="bloque")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Programas_codigo")
			}
		)
	private List<Programa> programas;

	//bi-directional many-to-many association to Materia
	@ManyToMany(mappedBy="bloques")
	private List<Materia> materias;

	public Bloque() {
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Programa> getProgramas() {
		return this.programas;
	}

	public void setProgramas(List<Programa> programas) {
		this.programas = programas;
	}

	public List<Materia> getMaterias() {
		return this.materias;
	}

	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}
	
	public BloqueBO toBO(){
		BloqueBO bloqueBO = new BloqueBO();
		bloqueBO.setNombre(nombre);
		return bloqueBO;
}

}