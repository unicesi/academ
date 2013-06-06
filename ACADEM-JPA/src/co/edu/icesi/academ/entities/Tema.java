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
import co.edu.icesi.academ.bo.TemaBO;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the Temas database table.
 * 
 */
@Entity
@Table(name="Temas")
@NamedQueries({
	@NamedQuery(name="obtenerTemas", query="SELECT t FROM Tema t")
})
public class Tema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String nombre;

	//bi-directional many-to-one association to Calificacion
	@OneToMany(mappedBy="tema")
	private List<Calificacion> calificaciones;

	//bi-directional many-to-one association to FactorDeImpacto
	@OneToMany(mappedBy="tema")
	private List<FactorDeImpacto> factoresDeImpacto;

	//bi-directional many-to-one association to ResultadoAprendizaje
	@OneToMany(mappedBy="tema")
	private List<ResultadoAprendizaje> resultadosAprendizaje;

	//bi-directional many-to-one association to Subtema
	@OneToMany(mappedBy="tema")
	private List<Subtema> subtemas;

	//bi-directional many-to-many association to NivelDeConocimiento
	@ManyToMany
	@JoinTable(
		name="Calificaciones"
		, joinColumns={
			@JoinColumn(name="tema")
			}
		, inverseJoinColumns={
			@JoinColumn(name="nivelDeConocimiento")
			}
		)
	private List<NivelDeConocimiento> nivelesDeConocimiento;

	public Tema() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Calificacion> getCalificaciones() {
		return this.calificaciones;
	}

	public void setCalificaciones(List<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public List<FactorDeImpacto> getFactoresDeImpacto() {
		return this.factoresDeImpacto;
	}

	public void setFactoresDeImpacto(List<FactorDeImpacto> factoresDeImpacto) {
		this.factoresDeImpacto = factoresDeImpacto;
	}

	public List<ResultadoAprendizaje> getResultadosAprendizaje() {
		return this.resultadosAprendizaje;
	}

	public void setResultadosAprendizaje(List<ResultadoAprendizaje> resultadosAprendizaje) {
		this.resultadosAprendizaje = resultadosAprendizaje;
	}

	public List<Subtema> getSubtemas() {
		return this.subtemas;
	}

	public void setSubtemas(List<Subtema> subtemas) {
		this.subtemas = subtemas;
	}

	public List<NivelDeConocimiento> getNivelesDeConocimiento() {
		return this.nivelesDeConocimiento;
	}

	public void setNivelesDeConocimiento(List<NivelDeConocimiento> nivelesDeConocimiento) {
		this.nivelesDeConocimiento = nivelesDeConocimiento;
	}
	
	public TemaBO toBO() {
		TemaBO temaBO = new TemaBO();
		temaBO.setId(id);
		temaBO.setNombre(nombre);
		List<SubtemaBO> subtemasBO = new ArrayList<SubtemaBO>();
		for (Subtema subtema : subtemas) {
			SubtemaBO subtemaBO = subtema.toBO();
			subtemasBO.add(subtemaBO);
		}
		temaBO.setSubtemas(subtemasBO);
		return temaBO;
	}

}