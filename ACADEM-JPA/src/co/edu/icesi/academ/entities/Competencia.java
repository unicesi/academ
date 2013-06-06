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

import java.util.ArrayList;
import java.util.List;
import co.edu.icesi.academ.bo.CompetenciaBO;
import co.edu.icesi.academ.bo.ResultadoAprendizajeBO;

/**
 * The persistent class for the Competencias database table.
 * 
 */
@Entity
@Table(name="Competencias")
@NamedQuery(name="obtenerCompetenciasPrograma", query="SELECT com FROM Competencia com WHERE com.programa.codigo LIKE :codigo")

public class Competencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String descripcion;

	//bi-directional many-to-one association to Programa
	@ManyToOne
	@JoinColumn(name="programa")
	private Programa programa;

	//bi-directional many-to-one association to ResultadoAprendizaje
	@OneToMany(mappedBy="competencia")
	private List<ResultadoAprendizaje> resultadosAprendizaje;

	public Competencia() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Programa getPrograma() {
		return this.programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public List<ResultadoAprendizaje> getResultadosAprendizaje() {
		return this.resultadosAprendizaje;
	}

	public void setResultadosAprendizaje(List<ResultadoAprendizaje> resultadosAprendizaje) {
		this.resultadosAprendizaje = resultadosAprendizaje;
	}

	
	public CompetenciaBO toBO()
	{
		CompetenciaBO competenciaBO = new CompetenciaBO();
		competenciaBO.setId(id);
		competenciaBO.setDescripcion(descripcion);
		competenciaBO.setPrograma(programa.toBO());
		List<ResultadoAprendizajeBO> resultados = new ArrayList<ResultadoAprendizajeBO>(resultadosAprendizaje.size());
		
		return competenciaBO;
	}
}