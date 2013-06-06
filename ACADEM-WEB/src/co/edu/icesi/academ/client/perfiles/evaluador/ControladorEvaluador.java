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

package co.edu.icesi.academ.client.perfiles.evaluador;

import java.util.List;

import co.edu.icesi.academ.bo.CalificacionBO;
import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.NivelDeConocimientoBO;
import co.edu.icesi.academ.bo.TemaBO;
import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.client.Controlador;
import co.edu.icesi.academ.server.EvaluacionServices;

public class ControladorEvaluador extends Controlador{

	private static ControladorEvaluador controladorEvaluador;
	private EvaluacionServices evaluacionServices;
	
	private ControladorEvaluador() {
		evaluacionServices = new EvaluacionServices();
	}

	public static ControladorEvaluador getInstance() {
		if (controladorEvaluador == null) {
			controladorEvaluador = new ControladorEvaluador();
		}
		return controladorEvaluador;
	}
	
	public void obtenerEvaluacionesDeParticipante() {
		// Obtener el usuario del evaluador
		UsuarioBO evaluador = (UsuarioBO) getAcademUI().getHTTPSession().getAttribute("usuario");
		
		// Obtener las evaluaciones del evaluador
		List<EvaluacionBO> evaluaciones = this.evaluacionServices.obtenerEvaluacionesDeParticipante(evaluador);
		
		// Cargar los assessments del evaluador en la lista de evaluaciones
		getAcademUI().getListadoEvaluaciones().cargarEvaluaciones(evaluaciones);
	}

	public void cargarCalificacionesEvaluadorEvaluacion(EvaluacionBO evaluacionBO) {
		// Obtener el usuario del evaluador
		UsuarioBO evaluador = (UsuarioBO) getAcademUI().getHTTPSession().getAttribute("usuario");
		// Obtener la evaluación seleccionada
		EvaluacionBO evaluacion = evaluacionServices.obtenerEvaluacion(evaluacionBO);
		// Obtener las calificaciones previas de la evaluación
		List<CalificacionBO> calificacionesPrevias = evaluacionServices.obtenerCalificacionesEvaluacion(evaluador, evaluacion);
		
		// Obtener el listado de temas
		List<TemaBO> temas = obtenerListaDeTemas();
		// Obtener el listado de niveles de conocimiento
		List<NivelDeConocimientoBO> nivelesDeConocimiento = obtenerNivelesDeConocimiento();
		
		// Llenar el listado de calificaciones
		getAcademUI().getListadoCalificacionesEvaluacion().cargarCalificacionesEvaluacion(evaluacion, evaluador, temas, nivelesDeConocimiento, calificacionesPrevias);
	}

	private List<NivelDeConocimientoBO> obtenerNivelesDeConocimiento() {
		List<NivelDeConocimientoBO> nivelesDeConocimiento = evaluacionServices.obtenerNivelesDeConocimiento();
		return nivelesDeConocimiento;
	}

	private List<TemaBO> obtenerListaDeTemas() {
		List<TemaBO> temas = evaluacionServices.obtenerTemas();
		return temas;
	}
	
	public void guardarCalificaciones(List<CalificacionBO> calificaciones) {
		evaluacionServices.guardarCalificacionEvaluacion(calificaciones);
		getAcademUI().mostrarNotificacion("ACaDeM", "Calificación guardada");
	}
}
