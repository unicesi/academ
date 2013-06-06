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

public class CalificacionRolBO implements Serializable 
{
	/**
	 * tema al que pertenece la calificacion
	 */
	private TemaBO tema;
	
	/**
	 * rol calificador
	 */
	private RolBO rol;
	
	/**
	 * nivel de conocimiento de la calificacion
	 */
	private NivelDeConocimientoBO nivelDeConocimiento;
	
	public CalificacionRolBO (TemaBO tema,RolBO rol,NivelDeConocimientoBO nivelConocimiento ){
		this.tema=tema;
		this.rol=rol;
		this.nivelDeConocimiento=nivelConocimiento;
	}
	
	public CalificacionRolBO (){
		tema=null;
		rol=null;
		nivelDeConocimiento=null;
	}
	
	public TemaBO getTema() {
		return tema;
	}

	public void setTema(TemaBO tema) {
		this.tema = tema;
	}

	public RolBO getRol() {
		return rol;
	}

	public void setRol(RolBO rol) {
		this.rol = rol;
	}

	public NivelDeConocimientoBO getNivelDeConocimiento() {
		return nivelDeConocimiento;
	}

	public void setNivelDeConocimiento(NivelDeConocimientoBO nivelDeConocimiento) {
		this.nivelDeConocimiento = nivelDeConocimiento;
	}
	
	@Override
	public String toString() 
	{
		return "tema:" +tema.getNombre()+" rol: "+ rol.getNombre()+" calficacion: "+nivelDeConocimiento.getId();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		CalificacionRolBO c = (CalificacionRolBO)obj;
		if(tema.equals(c.getTema()) && rol.equals(c.getRol()) && nivelDeConocimiento.equals(c.getNivelDeConocimiento()))
			return true;
		return false;
	}
	
}
