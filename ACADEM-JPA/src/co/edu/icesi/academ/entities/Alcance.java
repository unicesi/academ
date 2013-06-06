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
 * The persistent class for the Alcances database table.
 * 
 */
@Entity
@Table(name="Alcances")
public class Alcance implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AlcancePK id;

	private String alcance;

	//bi-directional many-to-one association to ResultadoAprendizaje
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="programa", referencedColumnName="programa", insertable=false, updatable=false),
		@JoinColumn(name="tema", referencedColumnName="tema", insertable=false, updatable=false)
		})
	private ResultadoAprendizaje resultadoAprendizaje;

	//bi-directional many-to-one association to Materias_Bloque
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="bloque", referencedColumnName="bloque", insertable=false, updatable=false),
		@JoinColumn(name="materia", referencedColumnName="materia", insertable=false, updatable=false)
		})
	private Materias_Bloque materiaBloque;

	public Alcance() {
	}

	public AlcancePK getId() {
		return this.id;
	}

	public void setId(AlcancePK id) {
		this.id = id;
	}

	public String getAlcance() {
		return this.alcance;
	}

	public void setAlcance(String alcance) {
		this.alcance = alcance;
	}

	public ResultadoAprendizaje getResultadoAprendizaje() {
		return this.resultadoAprendizaje;
	}

	public void setResultadoAprendizaje(ResultadoAprendizaje resultadoAprendizaje) {
		this.resultadoAprendizaje = resultadoAprendizaje;
	}

	public Materias_Bloque getMateriaBloque() {
		return this.materiaBloque;
	}

	public void setMateriaBloque(Materias_Bloque materiaBloque) {
		this.materiaBloque = materiaBloque;
	}

}