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

public class ProgramaBO implements Serializable{

	private static final long serialVersionUID = 1L;

	private int codigo;

	private String descripcion;

	private String nombre;

	private List<EvaluacionBO> evaluaciones;

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<EvaluacionBO> getEvaluaciones() {
		return this.evaluaciones;
	}

	public void setEvaluaciones(List<EvaluacionBO> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
	    if (obj == this) return true;
	    ProgramaBO programa = (ProgramaBO) obj;
	    if (codigo == programa.codigo) return true;
	    return false;
	}
	
	@Override
	public int hashCode() {
		return codigo;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
}
