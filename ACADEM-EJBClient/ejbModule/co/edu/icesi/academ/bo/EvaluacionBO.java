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
import java.util.Date;
import java.util.List;

public class EvaluacionBO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private Date fechaFinal;

	private Date fechaInicial;

	private ProgramaBO programa;

	private UsuarioBO propietario;

	private List<CalificacionBO> calificaciones;

	private List<UsuarioBO> evaluadores;
	
	private List<RolBO> roles;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return this.fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public ProgramaBO getPrograma() {
		return this.programa;
	}

	public void setPrograma(ProgramaBO programa) {
		this.programa = programa;
	}

	public UsuarioBO getPropietario() {
		return this.propietario;
	}

	public void setPropietario(UsuarioBO usuario) {
		this.propietario = usuario;
	}

	public List<CalificacionBO> getCalificaciones() {
		return this.calificaciones;
	}

	public void setCalificaciones(List<CalificacionBO> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public List<UsuarioBO> getEvaluadores() {
		return evaluadores;
	}

	public void setEvaluadores(List<UsuarioBO> evaluadores) {
		this.evaluadores = evaluadores;
	}

	public List<RolBO> getRoles() {
		return roles;
	}

	public void setRoles(List<RolBO> roles) {
		this.roles = roles;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
	    if (obj == this) return true;
	    if (!(obj instanceof EvaluacionBO)) return false;
	    EvaluacionBO evaluacion = (EvaluacionBO) obj;
	    if (id == evaluacion.id) return true;
	    return false;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
	 return "id: "+id;
	}
}
