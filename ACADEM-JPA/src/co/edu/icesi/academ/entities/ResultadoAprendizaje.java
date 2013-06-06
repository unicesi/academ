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

import co.edu.icesi.academ.bo.ResultadoAprendizajeBO;

import java.util.List;


/**
 * The persistent class for the ResultadosAprendizaje database table.
 * 
 */
@Entity
@Table(name="ResultadosAprendizaje")
@NamedQueries({
	@NamedQuery(name="obtenerResultadosAprendizaje", query="SELECT ra FROM ResultadoAprendizaje ra"),
	@NamedQuery(name="obtenerResultadosAprendizajePrograma", query="SELECT r FROM ResultadoAprendizaje r WHERE r.id.programa LIKE :programa AND r.competencia IS null" ),
	@NamedQuery(name="obtenerResultadosAprendizajeCompetencia",query="SELECT r FROM ResultadoAprendizaje r WHERE r.competencia.id = :competencia"),
	@NamedQuery(name="removerResultadoAprendizaje", query="DELETE FROM ResultadoAprendizaje ra WHERE ra.id.programa = :programa AND ra.id.tema = :tema")
})
public class ResultadoAprendizaje implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ResultadoAprendizajePK id;

	//bi-directional many-to-one association to Criterio_Rubrica
	@OneToMany(mappedBy="resultadosAprendizaje")
	private List<Criterio_Rubrica> criteriosRubricas;

	//bi-directional many-to-one association to NivelDeConocimiento
	@ManyToOne
	@JoinColumn(name="nivelDeConocimiento")
	private NivelDeConocimiento nivelDeConocimiento;

	//bi-directional many-to-one association to Programa
	@ManyToOne
	@JoinColumn(name="programa", insertable=false, updatable=false)
	private Programa programa;

	//bi-directional many-to-one association to Tema
	@ManyToOne
	@JoinColumn(name="tema", insertable=false, updatable=false)
	private Tema tema;

	//bi-directional many-to-one association to Alcance
	@OneToMany(mappedBy="resultadoAprendizaje")
	private List<Alcance> alcances;

	//bi-directional many-to-one association to Competencia
	@ManyToOne
	@JoinColumn(name="competencia")
	private Competencia competencia;

	public ResultadoAprendizaje() {
	}

	public ResultadoAprendizajePK getId() {
		return this.id;
	}

	public void setId(ResultadoAprendizajePK id) {
		this.id = id;
	}

	public List<Criterio_Rubrica> getCriteriosRubricas() {
		return this.criteriosRubricas;
	}

	public void setCriteriosRubricas(List<Criterio_Rubrica> criteriosRubricas) {
		this.criteriosRubricas = criteriosRubricas;
	}

	public NivelDeConocimiento getNivelDeConocimiento() {
		return this.nivelDeConocimiento;
	}

	public void setNivelDeConocimiento(NivelDeConocimiento nivelDeConocimiento) {
		this.nivelDeConocimiento = nivelDeConocimiento;
	}

	public Programa getPrograma() {
		return this.programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public Tema getTema() {
		return this.tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public List<Alcance> getAlcances() {
		return this.alcances;
	}

	public void setAlcances(List<Alcance> alcances) {
		this.alcances = alcances;
	}

	public Competencia getCompetencia() {
		return this.competencia;
	}

	public void setCompetencia(Competencia competencia) {
		this.competencia = competencia;
	}
	
	public ResultadoAprendizajeBO toBO( )
	{
		ResultadoAprendizajeBO bo = new ResultadoAprendizajeBO();
		
		bo.setPrograma( programa.toBO());
		bo.setTema( tema.toBO());
		bo.setNivelDeConocimiento(nivelDeConocimiento.toBO());
		
		return bo;
	}

}