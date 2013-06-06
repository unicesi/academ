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

package co.edu.icesi.academ.client.usuarios;


import java.util.ArrayList;
import java.util.List;

//import com.patron.PanelPatron;
import com.vaadin.ui.Label;

import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.client.Controlador;
import co.edu.icesi.academ.client.perfiles.ListadoEvaluaciones;
import co.edu.icesi.academ.client.perfiles.PanelOpciones;
import co.edu.icesi.academ.client.perfiles.administrador.ControladorAdministrador;
import co.edu.icesi.academ.client.perfiles.administrador.GestionEvaluaciones;
import co.edu.icesi.academ.client.perfiles.administrador.PanelBloquesMaterias;
import co.edu.icesi.academ.client.perfiles.administrador.PanelProgramasBloques;
import co.edu.icesi.academ.client.perfiles.evaluador.ControladorEvaluador;
import co.edu.icesi.academ.client.perfiles.evaluador.ListadoCalificacionesEvaluacion;
import co.edu.icesi.academ.client.perfiles.propietario.Competencias;
import co.edu.icesi.academ.client.perfiles.propietario.ConsolidadoEvaluacion;
import co.edu.icesi.academ.client.perfiles.propietario.ControladorPropietario;
import co.edu.icesi.academ.client.perfiles.propietario.FactoresDeImpacto;
import co.edu.icesi.academ.client.perfiles.propietario.ListadoEvaluadores;
import co.edu.icesi.academ.client.perfiles.propietario.PanelAsignarRolEvaluador;
import co.edu.icesi.academ.client.perfiles.propietario.PanelRubricas;
import co.edu.icesi.academ.excepciones.IniciarSesionException;
import co.edu.icesi.academ.server.UsuarioServices;

public class ControladorInicioSesion extends Controlador {

	private static ControladorInicioSesion controladorInicioSesion;
	private UsuarioServices usuarioServices;
	
	private ControladorInicioSesion() {
		usuarioServices = new UsuarioServices();
	}

	public static ControladorInicioSesion getInstance() {
		if (controladorInicioSesion == null) {
			controladorInicioSesion = new ControladorInicioSesion();
		}
		return controladorInicioSesion;
	}
	
	public void iniciarSesion(UsuarioBO usuarioBO) {
		UsuarioBO usuario;
		try {
			// Intentar login
			usuario = usuarioServices.iniciarSesion(usuarioBO);
			
			// Si login exitoso
			getAcademUI().mostrarNotificacion("ACaDeM", "Bienvenido(a) " + usuario.getNombre());
			
			// Guarda el nombre de usuario en la sesión mediante el atributo 'usuario' 
			getAcademUI().getHTTPSession().setAttribute("usuario", usuario);
			getAcademUI().getPanelSesion().setNombreUsuario(usuario.getNombre());
			
			List<String> perfiles = new ArrayList<String>();
			
			// Limpia el formulario de login
			getAcademUI().getFormularioInicioSesion().clear();

			// Mostrar el botón de logout
			getAcademUI().getPanelSesion().setVisible(true);
			
			// Ocultar el panel de login
			getAcademUI().getPanelContenido().removerTodoElContenido();
			
			// Mostrar los paneles de acuerdo al perfil registrado 
			
			// Si el usuario tiene el perfil de propietario se cargan las opciones de este rol
			PanelOpciones panelOpciones = new PanelOpciones();
			getAcademUI().setPanelOpciones(panelOpciones);
			switch(usuario.getPerfil()) {
				case "Administrador":
					// Se cargan las opciones de administrador
					
					// Perfiles disponibles
					perfiles.add("Administrador");
					perfiles.add("Evaluador");
					getAcademUI().getPanelSesion().setPerfiles(perfiles);
					getAcademUI().getPanelSesion().seleccionarPerfil("Administrador");
					
					//Panel ProgramasBloques
					PanelProgramasBloques panelPB = new PanelProgramasBloques();
					getAcademUI().setPanelProgramasBloques(panelPB);
					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(getAcademUI().getPanelProgramasBloques(), "Programas");
					ControladorAdministrador.getInstance().cargarProgramas();
					
					//Aoi PanelBloquesMaterias
					
					PanelBloquesMaterias panelBloquesMaterias = new PanelBloquesMaterias();
					getAcademUI().setPanelBloquesMaterias(panelBloquesMaterias);
					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(getAcademUI().getPanelBloquesMaterias(), "Panel de bloques y materias");
					ControladorAdministrador.getInstance().cargarBloques();//cargar bloques panel bloques materias
					
					// Panel evaluaciones
					GestionEvaluaciones gestionEvaluaciones = new GestionEvaluaciones();
					getAcademUI().setGestionEvaluaciones(gestionEvaluaciones);
					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(getAcademUI().getGestionEvaluaciones(), "Evaluaciones");
					ControladorAdministrador.getInstance().obtenerEvaluaciones();
					
					//pANEL PATRON
//					PanelPatron panelPatron = new PanelPatron();
//					getAcademUI().setPanelPatron(panelPatron);
//					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(getAcademUI().getPanelPatron(), "FAST LANE READER");
					
					
					// TODO Panel usuarios
					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(new Label("Pestaña para crear y editar usuarios. [En construcción]"), "Usuarios");
					
					getAcademUI().getPanelContenido().setContenido(getAcademUI().getPanelOpciones(), "top:0.0px;left:0.0px;");
					break;
				case "Propietario":
					// Se cargan las opciones de propietario
					
					// Perfiles disponibles
					perfiles.add("Propietario");
					perfiles.add("Evaluador");
					getAcademUI().getPanelSesion().setPerfiles(perfiles);
					getAcademUI().getPanelSesion().seleccionarPerfil("Propietario");
					
					// Listado de evaluaciones del propietario
					ListadoEvaluaciones listadoEvaluacionesPropietario = new ListadoEvaluaciones();
					getAcademUI().setListadoEvaluaciones(listadoEvaluacionesPropietario);
					ControladorPropietario.getInstance().obtenerEvaluacionesDelPropietario();
					getAcademUI().getPanelContenido().setContenido(getAcademUI().getListadoEvaluaciones(), "top:0.0px;left:0.0px;");
					
					// Habilitar opciones
					// Panel asociar evaluadores
					ListadoEvaluadores listadoEvaluadores = new ListadoEvaluadores();
					getAcademUI().setListadoEvaluadores(listadoEvaluadores);
					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(listadoEvaluadores, "Evaluadores");
					// Panel configurar roles
					PanelAsignarRolEvaluador asignarRolEvaluador= new PanelAsignarRolEvaluador();
					getAcademUI().setPanelAsignarRol(asignarRolEvaluador);
					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(asignarRolEvaluador, "Roles");
					//Panel Factores de Impacto
					FactoresDeImpacto factores = new FactoresDeImpacto();
					getAcademUI().setFactoresDeImpacto(factores);
					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(factores, "Factores de Impacto");
					// Panel consolidar evaluacion
					ConsolidadoEvaluacion consolidadoEvaluacion = new ConsolidadoEvaluacion();
					getAcademUI().setConsolidadoEvaluacion(consolidadoEvaluacion);
					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(consolidadoEvaluacion, "Consolidar");
					// Panel Competencias
					Competencias competencias = new Competencias();
					getAcademUI().setCompetencias( competencias );
//					ControladorPropietario.getInstance().cargarCompentencias();
					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(competencias, "Competencias");
					
					// Panel Rubrica
					getAcademUI().setPanelRubricas( new PanelRubricas());
					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(getAcademUI().getPanelRubricas(), "Rubricas" );
					
					getAcademUI().getPanelContenido().setContenido(getAcademUI().getPanelOpciones(), "top:0.0px;left:310.0px;");
					// Las opciones permanecen deshabilitadas hasta que seleccione una evaluación.
					getAcademUI().getPanelOpciones().getTabSheetOpciones().setEnabled(false);
					break;
				case "Evaluador":
					// Se cargan las opciones de evaluador
					
					// Perfiles disponibles
					perfiles.add("Evaluador");
					getAcademUI().getPanelSesion().setPerfiles(perfiles);
					getAcademUI().getPanelSesion().seleccionarPerfil("Evaluador");
					
					// Listado de evaluaciones del evaluador
					ListadoEvaluaciones listadoEvaluacionesEvaluador = new ListadoEvaluaciones();
					getAcademUI().setListadoEvaluaciones(listadoEvaluacionesEvaluador);
					ControladorEvaluador.getInstance().obtenerEvaluacionesDeParticipante();
					getAcademUI().getPanelContenido().setContenido(getAcademUI().getListadoEvaluaciones(), "top: 0.0px;left: 0.0px;");
					
					// Habilitar opciones
					// Panel participar en evaluación
					ListadoCalificacionesEvaluacion listadoCalificaciones = new ListadoCalificacionesEvaluacion();
					getAcademUI().setListadoCalificacionesEvaluacion(listadoCalificaciones);
					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(listadoCalificaciones, "Evaluar");
					getAcademUI().getPanelContenido().setContenido(getAcademUI().getPanelOpciones(), "top:0.0px;left:310.0px;");
					// Las opciones permanecen deshabilitadas hasta que seleccione una evaluación.
					getAcademUI().getPanelOpciones().getTabSheetOpciones().setEnabled(false);
					break;
			}
		} catch (IniciarSesionException e) {
			getAcademUI().getFormularioInicioSesion().setMensajeError(e.getMessage());
		}
	}
	
	public void cambiarPerfilA(String perfil) {
		// Limpiar el panel contenido
		getAcademUI().getPanelContenido().removerTodoElContenido();
		
		// Mostrar los paneles de acuerdo al perfil seleccionado 
		
		// Si el usuario tiene el perfil de propietario se cargan las opciones de este rol
		PanelOpciones panelOpciones = new PanelOpciones();
		getAcademUI().setPanelOpciones(panelOpciones);
		switch(perfil) {
			case "Administrador":
				// Se cargan las opciones de administrador
				
				// Perfil seleccionado
				getAcademUI().getPanelSesion().seleccionarPerfil("Administrador");
				
				// Panel evaluaciones
				GestionEvaluaciones gestionEvaluaciones = new GestionEvaluaciones();
				getAcademUI().setGestionEvaluaciones(gestionEvaluaciones);
				getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(getAcademUI().getGestionEvaluaciones(), "Evaluaciones");
				ControladorAdministrador.getInstance().obtenerEvaluaciones();
				
				// PanelBloquesMaterias
				ControladorAdministrador.getInstance().cargarBloques();
				
				// TODO Panel usuarios
				getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(new Label("Pestaña para crear y editar usuarios. [En construcción]"), "Usuarios");
				
				getAcademUI().getPanelContenido().setContenido(getAcademUI().getPanelOpciones(), "top:0.0px;left:0.0px;");
				break;
			case "Propietario":
				// Se cargan las opciones de propietario
				
				// Perfil seleccionado
				getAcademUI().getPanelSesion().seleccionarPerfil("Propietario");
				
				// Listado de evaluaciones del propietario
				ListadoEvaluaciones listadoEvaluacionesPropietario = new ListadoEvaluaciones();
				getAcademUI().setListadoEvaluaciones(listadoEvaluacionesPropietario);
				ControladorPropietario.getInstance().obtenerEvaluacionesDelPropietario();
				getAcademUI().getPanelContenido().setContenido(getAcademUI().getListadoEvaluaciones(), "top:0.0px;left:0.0px;");
				
				// Habilitar opciones
				// Panel asociar evaluadores
				ListadoEvaluadores listadoEvaluadores = new ListadoEvaluadores();
				getAcademUI().setListadoEvaluadores(listadoEvaluadores);
				getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(listadoEvaluadores, "Evaluadores");
				// TODO Panel configurar roles
				PanelAsignarRolEvaluador asignarRolEvaluador= new PanelAsignarRolEvaluador();
				getAcademUI().setPanelAsignarRol(asignarRolEvaluador);
				getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(asignarRolEvaluador, "Roles");
				// Panel consolidar evaluacion
				ConsolidadoEvaluacion consolidadoEvaluacion = new ConsolidadoEvaluacion();
				getAcademUI().setConsolidadoEvaluacion(consolidadoEvaluacion);
				getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(consolidadoEvaluacion, "Consolidar");
				
				getAcademUI().getPanelContenido().setContenido(getAcademUI().getPanelOpciones(), "top:0.0px;left:310.0px;");
				// Las opciones permanecen deshabilitadas hasta que seleccione una evaluación.
				getAcademUI().getPanelOpciones().getTabSheetOpciones().setEnabled(false);
				break;
			case "Evaluador":
				// Se cargan las opciones de evaluador
				
				// Perfil seleccionado
				getAcademUI().getPanelSesion().seleccionarPerfil("Evaluador");
				
				// Listado de evaluaciones del evaluador
				ListadoEvaluaciones listadoEvaluacionesEvaluador = new ListadoEvaluaciones();
				getAcademUI().setListadoEvaluaciones(listadoEvaluacionesEvaluador);
				ControladorEvaluador.getInstance().obtenerEvaluacionesDeParticipante();
				getAcademUI().getPanelContenido().setContenido(getAcademUI().getListadoEvaluaciones(), "top: 0.0px;left: 0.0px;");
				
				// Habilitar opciones
				// Panel participar en evaluación
				ListadoCalificacionesEvaluacion listadoCalificaciones = new ListadoCalificacionesEvaluacion();
				getAcademUI().setListadoCalificacionesEvaluacion(listadoCalificaciones);
				getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(listadoCalificaciones, "Evaluar");
				getAcademUI().getPanelContenido().setContenido(getAcademUI().getPanelOpciones(), "top:0.0px;left:310.0px;");
				// Las opciones permanecen deshabilitadas hasta que seleccione una evaluación.
				getAcademUI().getPanelOpciones().getTabSheetOpciones().setEnabled(false);
				break;
		}
	}
	
	public void cerrarSesion() {
		// Invalidar la sesión
		getAcademUI().getHTTPSession().invalidate();
		
		// Quitar el nombre de usuario del panel de herramientas
		getAcademUI().getPanelSesion().setNombreUsuario("");
		getAcademUI().getPanelSesion().setPerfiles(new ArrayList<String>());
		getAcademUI().getPanelSesion().seleccionarPerfil(null);
		
		// Ocultar todos los paneles
		getAcademUI().getPanelSesion().setVisible(false);
		getAcademUI().getPanelContenido().removerTodoElContenido();
		
		// Mostrar el formulario de inicio de sesión.
		getAcademUI().getPanelContenido().setContenido(getAcademUI().getFormularioInicioSesion());
	}
	
}