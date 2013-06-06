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

import co.edu.icesi.academ.bo.CriterioBO;
import co.edu.icesi.academ.bo.NivelCriterioBO;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the Criterios_Rubricas database table.
 * 
 */
@Entity
@Table(name="Criterios_Rubricas")
public class Criterio_Rubrica implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Criterio_RubricaPK id;

	private String descripcion;

	//bi-directional many-to-one association to ResultadoAprendizaje
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="programa", referencedColumnName="programa", insertable=false, updatable=false),
		@JoinColumn(name="tema", referencedColumnName="tema", insertable=false, updatable=false)
		})
	private ResultadoAprendizaje resultadosAprendizaje;

	//bi-directional many-to-one association to Nivel_Rubrica
	@OneToMany(mappedBy="criteriosRubrica")
	private List<Nivel_Rubrica> nivelesCriterio;

	public Criterio_Rubrica() {
	}

	public Criterio_RubricaPK getId() {
		return this.id;
	}

	public void setId(Criterio_RubricaPK id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ResultadoAprendizaje getResultadosAprendizaje() {
		return this.resultadosAprendizaje;
	}

	public void setResultadosAprendizaje(ResultadoAprendizaje resultadosAprendizaje) {
		this.resultadosAprendizaje = resultadosAprendizaje;
	}

	public List<Nivel_Rubrica> getNivelesRubricas() {
		return this.nivelesCriterio;
	}

	public void setNivelesRubricas(List<Nivel_Rubrica> nivelesRubricas) {
		this.nivelesCriterio = nivelesRubricas;
	}
	
	public CriterioBO toBO( )
	{
		CriterioBO criterioBO = new CriterioBO();
		criterioBO.setDescripcion(descripcion);
		criterioBO.setId( id.getId());
		
		List<NivelCriterioBO> niveles = new ArrayList<NivelCriterioBO>();
		for( Nivel_Rubrica nivel : nivelesCriterio )
			niveles.add( nivel.toBO() );
		
		criterioBO.setNiveles(niveles);
		
		return criterioBO;
	}

}