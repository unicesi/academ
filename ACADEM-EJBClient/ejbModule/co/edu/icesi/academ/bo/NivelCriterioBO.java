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

public class NivelCriterioBO implements Serializable{
	/**
	 * constante de serializacion
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private int nivel;
	
	private String descripcion;

	public int getId() {
		return id;
	}
	
	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
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
	
	public boolean equals( Object o )
	{
		if( o instanceof NivelCriterioBO )
		{
			NivelCriterioBO nivel = (NivelCriterioBO)o;
			
			return this.nivel == nivel.nivel && descripcion.equals(nivel.descripcion);
		}
		
		return false;
	}
}