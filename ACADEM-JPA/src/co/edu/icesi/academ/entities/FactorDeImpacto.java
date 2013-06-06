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

import co.edu.icesi.academ.bo.FactorDeImpactoBO;


/**
 * The persistent class for the FactoresDeImpacto database table.
 * 
 */
@Entity
@Table(name="FactoresDeImpacto")
@NamedQueries({
	@NamedQuery(name="obtenerFactoresDeImpacto", query="SELECT fc FROM FactorDeImpacto fc, Evaluacion e where fc.id.evaluacion = e.id and e.propietario = :propietario and e.id = :evaluacion"),
	@NamedQuery(name="obtenerFactoresDeImpactoRol", query="SELECT fc FROM FactorDeImpacto fc where fc.id.rol= :rol and fc.id.evaluacion = :evaluacion"),
	@NamedQuery(name="removerFactorDeImpactoPrevio", query="DELETE FROM FactorDeImpacto f WHERE f.id.rol LIKE :rol AND f.id.evaluacion LIKE :evaluacion AND f.id.tema LIKE :tema")
})
public class FactorDeImpacto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FactorDeImpactoPK id;

	private double factorDeImpacto;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="evaluacion", referencedColumnName="evaluacion", insertable=false, updatable=false),
		@JoinColumn(name="rol", referencedColumnName="nombre", insertable=false, updatable=false)
		})
	private Rol rol;

	//bi-directional many-to-one association to Tema
	@ManyToOne
	@JoinColumn(name="tema", insertable=false, updatable=false)
	private Tema tema;

	public FactorDeImpacto() {
	}

	public FactorDeImpactoPK getId() {
		return this.id;
	}

	public void setId(FactorDeImpactoPK id) {
		this.id = id;
	}

	public double getFactorDeImpacto() {
		return this.factorDeImpacto;
	}

	public void setFactorDeImpacto(double factorDeImpacto) {
		this.factorDeImpacto = factorDeImpacto;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Tema getTema() {
		return this.tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}
	
	public FactorDeImpactoBO toBO(){
		FactorDeImpactoBO factor = new FactorDeImpactoBO();
		factor.setEvaluacion(id.getEvaluacion());
		factor.setFactorDeImpacto(factorDeImpacto);
		factor.setRol(id.getRol());
		factor.setTema(id.getTema());
		return factor;
	}

}