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
import java.util.List;

public class CriterioBO implements Serializable{
	
	/**
	 * constante de serializacion
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String descripcion;
	
	private List<NivelCriterioBO> niveles;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<NivelCriterioBO> getNiveles() {
		return niveles;
	}

	public void setNiveles(List<NivelCriterioBO> niveles) {
		this.niveles = niveles;
	}
	
	public boolean equals( Object o )
	{
		if( o instanceof CriterioBO )
		{
			CriterioBO criterio = (CriterioBO)o;
			
			return id == criterio.id && descripcion.equals(criterio.descripcion);
		}
		
		return false;
	}
}