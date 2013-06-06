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

import co.edu.icesi.academ.bo.SubtemaBO;


/**
 * The persistent class for the Subtemas database table.
 * 
 */
@Entity
@Table(name="Subtemas")
public class Subtema implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SubtemaPK id;

	private String nombre;

	//bi-directional many-to-one association to Tema
	@ManyToOne
	@JoinColumn(name="tema", insertable=false, updatable=false)
	private Tema tema;

	public Subtema() {
	}

	public SubtemaPK getId() {
		return this.id;
	}

	public void setId(SubtemaPK id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tema getTema() {
		return this.tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}
	
	public SubtemaBO toBO() {
		SubtemaBO subtemaBO = new SubtemaBO();
		subtemaBO.setNombre(nombre);
		return subtemaBO;
	}

}