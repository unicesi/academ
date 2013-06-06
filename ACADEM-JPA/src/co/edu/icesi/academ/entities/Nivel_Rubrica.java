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

import co.edu.icesi.academ.bo.NivelCriterioBO;


/**
 * The persistent class for the Niveles_Rubricas database table.
 * 
 */
@Entity
@Table(name="Niveles_Rubricas")
public class Nivel_Rubrica implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Nivel_RubricaPK id;

	private String descripcion;

	private int nivel;

	//bi-directional many-to-one association to Criterio_Rubrica
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="criterio", referencedColumnName="id", insertable=false, updatable=false),
		@JoinColumn(name="programa", referencedColumnName="programa", insertable=false, updatable=false),
		@JoinColumn(name="tema", referencedColumnName="tema", insertable=false, updatable=false)
		})
	private Criterio_Rubrica criteriosRubrica;

	public Nivel_Rubrica() {
	}

	public Nivel_RubricaPK getId() {
		return this.id;
	}

	public void setId(Nivel_RubricaPK id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getNivel() {
		return this.nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public Criterio_Rubrica getCriteriosRubrica() {
		return this.criteriosRubrica;
	}

	public void setCriteriosRubrica(Criterio_Rubrica criteriosRubrica) {
		this.criteriosRubrica = criteriosRubrica;
	}
	
	public NivelCriterioBO toBO( )
	{
		NivelCriterioBO nivelBO = new NivelCriterioBO();
		nivelBO.setDescripcion( descripcion );
		nivelBO.setId( id.getId() );
		nivelBO.setNivel( this.nivel );
		
		return nivelBO;
		
	}

}