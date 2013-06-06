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

public class TemaBO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;

	private String nombre;
	
	private List<SubtemaBO> subtemas;

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
	
	public List<SubtemaBO> getSubtemas() {
		return subtemas;
	}

	public void setSubtemas(List<SubtemaBO> subtemas) {
		this.subtemas = subtemas;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
	    if (obj == this) return true;
	    TemaBO tema = (TemaBO) obj;
	    if (id.equals(tema.id)) return true;
	    return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id+nombre;
	}
}