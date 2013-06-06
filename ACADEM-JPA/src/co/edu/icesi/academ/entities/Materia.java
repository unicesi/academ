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

import co.edu.icesi.academ.bo.MateriaBO;

import java.util.List;


/**
 * The persistent class for the Materias database table.
 * 
 */
@Entity
@Table(name="Materias")
@NamedQueries({
	@NamedQuery(name="obtenerMaterias", query="SELECT e FROM Materia e")
	})
public class Materia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String codigo;

	private String nombre;

	//bi-directional many-to-many association to Bloque
	@ManyToMany
	@JoinTable(
		name="Materias_Bloques"
		, joinColumns={
			@JoinColumn(name="materia")
			}
		, inverseJoinColumns={
			@JoinColumn(name="bloque")
			}
		)
	private List<Bloque> bloques;

	//bi-directional many-to-many association to Programa
	@ManyToMany
	@JoinTable(
		name="Materias_Programas"
		, joinColumns={
			@JoinColumn(name="materia")
			}
		, inverseJoinColumns={
			@JoinColumn(name="programa")
			}
		)
	private List<Programa> programas;

	public Materia() {
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Bloque> getBloques() {
		return this.bloques;
	}

	public void setBloques(List<Bloque> bloques) {
		this.bloques = bloques;
	}

	public List<Programa> getProgramas() {
		return this.programas;
	}

	public void setProgramas(List<Programa> programas) {
		this.programas = programas;
	}
	
	public MateriaBO toBO() {
		MateriaBO materiaBO = new MateriaBO();
		materiaBO.setCodigo(codigo);
		materiaBO.setNombre(nombre);
		return materiaBO;
	}

}