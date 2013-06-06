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

package co.edu.icesi.academ.client.perfiles;

import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.client.Controlador;
import co.edu.icesi.academ.client.perfiles.evaluador.ControladorEvaluador;
import co.edu.icesi.academ.client.perfiles.propietario.ControladorPropietario;

public class ControladorEvaluaciones extends Controlador {

	private static ControladorEvaluaciones controladorEvaluaciones;
	
	private ControladorEvaluaciones() {
		
	}
	
	public static ControladorEvaluaciones getInstance() {
		if (controladorEvaluaciones == null) {
			controladorEvaluaciones = new ControladorEvaluaciones();
		}
		return controladorEvaluaciones;
	}
	
	public void seleccionarEvaluación(EvaluacionBO evaluacionBO) {
		UsuarioBO usuario = (UsuarioBO) getAcademUI().getHTTPSession().getAttribute("usuario");
		getAcademUI().getHTTPSession().setAttribute("evaluacion", evaluacionBO);
		// Cargar información para la evaluación seleccionada dependiendo del perfil del usuario (Propietario o Evaluador)
		switch(usuario.getPerfil()) {
			// Opciones del propietario
			case "Propietario":
				//Carga el tab evaluadores	
				ControladorPropietario.getInstance().cargarEvaluadores(evaluacionBO);
				//Carga el tab de roles
				ControladorPropietario.getInstance().cargarRolesEvaluacion(evaluacionBO);
				// Carga el tab de factores de impacto
				ControladorPropietario.getInstance().cargarFactoresImpacto();
				// agrega las columnas por rol
				ControladorPropietario.getInstance().cargarColumnasRoles();
				//Carga el tab de rubricas
				ControladorPropietario.getInstance().cargarRubricas(evaluacionBO);
				break;
			case "Evaluador":
				// Opciones del evaluador
				ControladorEvaluador.getInstance().cargarCalificacionesEvaluadorEvaluacion(evaluacionBO);
				break;
		}
		
		// Habilitar las opciones sobre la evaluación
		getAcademUI().getPanelOpciones().getTabSheetOpciones().setEnabled(true);	
	}
	
}
