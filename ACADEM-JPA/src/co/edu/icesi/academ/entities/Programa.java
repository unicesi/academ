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

import co.edu.icesi.academ.bo.ProgramaBO;

import java.util.List;


/**
 * The persistent class for the Programas database table.
 * 
 */
@Entity
@Table(name="Programas")
@NamedQueries({
	@NamedQuery(name="obtenerProgramas", query="SELECT p FROM Programa p"),
	@NamedQuery(name="obtenerProgramaPorCodigo", query="SELECT p FROM Programa p WHERE p.codigo LIKE :cod")
})
public class Programa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int codigo;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-many association to Bloque
	@ManyToMany(mappedBy="programas")
	private List<Bloque> bloques;

	//bi-directional many-to-one association to Evaluacion
	@OneToMany(mappedBy="programa")
	private List<Evaluacion> evaluaciones;

	//bi-directional many-to-many association to Materia
	@ManyToMany(mappedBy="programas")
	private List<Materia> materias;

	//bi-directional many-to-one association to ResultadoAprendizaje
	@OneToMany(mappedBy="programa")
	private List<ResultadoAprendizaje> resultadosAprendizaje;

	//bi-directional many-to-one association to Competencia
	@OneToMany(mappedBy="programa")
	private List<Competencia> competencias;

	public Programa() {
	}

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public List<Evaluacion> getEvaluaciones() {
		return this.evaluaciones;
	}

	public void setEvaluaciones(List<Evaluacion> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}

	public List<Materia> getMaterias() {
		return this.materias;
	}

	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}

	public List<ResultadoAprendizaje> getResultadosAprendizaje() {
		return this.resultadosAprendizaje;
	}

	public void setResultadosAprendizaje(List<ResultadoAprendizaje> resultadosAprendizaje) {
		this.resultadosAprendizaje = resultadosAprendizaje;
	}

	public List<Competencia> getCompetencias() {
		return this.competencias;
	}

	public void setCompetencias(List<Competencia> competencias) {
		this.competencias = competencias;
	}
	
	public ProgramaBO toBO() {
		ProgramaBO programaBO = new ProgramaBO();
		programaBO.setCodigo(codigo);
		programaBO.setNombre(nombre);
		programaBO.setDescripcion(descripcion);
		return programaBO;
	}

}