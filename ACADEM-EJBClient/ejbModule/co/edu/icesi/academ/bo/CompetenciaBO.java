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

public class CompetenciaBO implements Serializable
{
	/**
	 * default serial version
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String descripcion;
	
	private ProgramaBO programa;
	
	private List<ResultadoAprendizajeBO> resultadosAprendizaje;
	
	public CompetenciaBO() 
	{
	
	}

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

	public ProgramaBO getPrograma() {
		return programa;
	}

	public void setPrograma(ProgramaBO programa) {
		this.programa = programa;
	}

	public List<ResultadoAprendizajeBO> getResultadosAprendizaje() {
		return resultadosAprendizaje;
	}

	public void setResultadosAprendizaje(
			List<ResultadoAprendizajeBO> resultadosAprendizaje) {
		this.resultadosAprendizaje = resultadosAprendizaje;
	}
	
	public String toString(){
		return descripcion;
	}
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0==null) return false;
		else if(arg0==this) return true;
		else if(!(arg0 instanceof CompetenciaBO)) return false;
		CompetenciaBO compara = (CompetenciaBO) arg0;
		if(compara.getId()==this.getId()) return true;
		return false;
	}
}
