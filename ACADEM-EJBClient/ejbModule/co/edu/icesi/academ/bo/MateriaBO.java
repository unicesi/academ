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

public class MateriaBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MateriaBO() { }

	private String codigo;	
	private String nombre;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	
	@Override
	public String toString(){
		return nombre;
	}
	
	@Override
	public int hashCode() {
		return codigo.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MateriaBO == false)
			return false;
		else
			return hashCode() == ((MateriaBO)obj).hashCode();
	}
}