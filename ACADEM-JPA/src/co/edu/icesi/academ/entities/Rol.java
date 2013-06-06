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

import co.edu.icesi.academ.bo.RolBO;

import java.util.List;


/**
 * The persistent class for the Roles database table.
 * 
 */
@Entity
@Table(name="Roles")
@NamedQueries({
	@NamedQuery(name="obtenerRolesEvaluacion", query="SELECT r FROM Rol r where r.evaluacion.id like :evaluacion ")
})
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RolPK id;

	//bi-directional many-to-one association to FactorDeImpacto
	@OneToMany(mappedBy="rol")
	private List<FactorDeImpacto> factoresDeImpacto;

	//bi-directional many-to-one association to Evaluacion
	@ManyToOne
	@JoinColumn(name="evaluacion", insertable=false, updatable=false)
	private Evaluacion evaluacion;

	//bi-directional many-to-many association to Usuario
	@ManyToMany(mappedBy="roles")
	private List<Usuario> usuarios;

	public Rol() {
	}

	public RolPK getId() {
		return this.id;
	}

	public void setId(RolPK id) {
		this.id = id;
	}

	public List<FactorDeImpacto> getFactoresDeImpacto() {
		return this.factoresDeImpacto;
	}

	public void setFactoresDeImpacto(List<FactorDeImpacto> factoresDeImpacto) {
		this.factoresDeImpacto = factoresDeImpacto;
	}

	public Evaluacion getEvaluacion() {
		return this.evaluacion;
	}

	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public RolBO toBO(){
		RolBO rolBO = new RolBO();
		rolBO.setEvaluacion(evaluacion.getId());
		rolBO.setNombre(id.getNombre());
		return rolBO;
	}

}