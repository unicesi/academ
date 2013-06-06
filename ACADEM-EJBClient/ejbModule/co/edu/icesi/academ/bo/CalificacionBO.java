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

public class CalificacionBO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private EvaluacionBO evaluacion;

	private NivelDeConocimientoBO nivelDeConocimiento;

	private TemaBO tema;
	
	private String evaluador;

	public EvaluacionBO getEvaluacion() {
		return this.evaluacion;
	}

	public void setEvaluacion(EvaluacionBO evaluacion) {
		this.evaluacion = evaluacion;
	}

	public NivelDeConocimientoBO getNivelDeConocimiento() {
		return this.nivelDeConocimiento;
	}

	public void setNivelDeConocimiento(NivelDeConocimientoBO nivelDeConocimiento) {
		this.nivelDeConocimiento = nivelDeConocimiento;
	}

	public TemaBO getTema() {
		return this.tema;
	}

	public void setTema(TemaBO tema) {
		this.tema = tema;
	}

	public String getEvaluador() {
		return evaluador;
	}

	public void setEvaluador(String evaluador) {
		this.evaluador = evaluador;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "tema: "+ tema.getId()+" evaluador: "+evaluador+" calificacion: "+nivelDeConocimiento.getId();
	}
}
