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

public class ResultadoAprendizajeBO implements Serializable
{
	/**
	 * constante de serializacion
	 */
	private static final long serialVersionUID = 1L;

	private ProgramaBO programa;
	
	private TemaBO tema;
	
	private NivelDeConocimientoBO nivelDeConocimiento;

	public ProgramaBO getPrograma() {
		return programa;
	}

	public void setPrograma(ProgramaBO programa) {
		this.programa = programa;
	}

	public TemaBO getTema() {
		return tema;
	}

	public void setTema(TemaBO tema) {
		this.tema = tema;
	}

	public NivelDeConocimientoBO getNivelDeConocimiento() {
		return nivelDeConocimiento;
	}

	public void setNivelDeConocimiento(NivelDeConocimientoBO nivelDeConocimiento) {
		this.nivelDeConocimiento = nivelDeConocimiento;
	}
	
	public boolean equals( Object o )
	{
		if( o instanceof ResultadoAprendizajeBO )
		{
			ResultadoAprendizajeBO resultado = (ResultadoAprendizajeBO)o;
			
			return programa.equals(resultado.programa) && tema.equals(resultado.tema);
		}
		
		return false;
	}
	
	public String toString( )
	{
		return tema.toString( );
	}
}
