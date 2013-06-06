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

package co.edu.icesi.academ.bo;

import java.io.Serializable;

public class FactorDeImpactoBO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String rol;

	private int evaluacion;

	private String tema;

	private double factorDeImpacto;

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public int getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(int evaluacion) {
		this.evaluacion = evaluacion;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public double getFactorDeImpacto() {
		return factorDeImpacto;
	}

	public void setFactorDeImpacto(double factorDeImpacto) {
		this.factorDeImpacto = factorDeImpacto;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FactorDeImpactoBO))
			return false;
		else {
			FactorDeImpactoBO factor = (FactorDeImpactoBO) obj;
			return factor.getEvaluacion() == evaluacion
					&& factor.getRol().equals(rol)
					&& factor.getTema().equals(tema);
		}
	}
	
	@Override
	public int hashCode() {
		return (evaluacion+rol+tema).hashCode();
	}
}
