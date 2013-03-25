package co.edu.icesi.academ.client.usuarios;

import com.vaadin.ui.Label;

import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.client.Controlador;
import co.edu.icesi.academ.client.perfiles.ListadoEvaluaciones;
import co.edu.icesi.academ.client.perfiles.PanelOpciones;
import co.edu.icesi.academ.client.perfiles.administrador.ControladorAdministrador;
import co.edu.icesi.academ.client.perfiles.administrador.GestionEvaluaciones;
import co.edu.icesi.academ.client.perfiles.evaluador.ControladorEvaluador;
import co.edu.icesi.academ.client.perfiles.evaluador.ListadoCalificacionesEvaluacion;
import co.edu.icesi.academ.client.perfiles.propietario.ConsolidadoEvaluacion;
import co.edu.icesi.academ.client.perfiles.propietario.ControladorPropietario;
import co.edu.icesi.academ.client.perfiles.propietario.ListadoEvaluadores;
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
			
			// Limpia el formulario de login
			getAcademUI().getFormularioInicioSesion().clear();
			
			// Ocultar el panel de login
			getAcademUI().getPanelContenido().removerTodoElContenido();
			
			// Mostrar el botón de logout
			getAcademUI().getPanelSesion().setVisible(true);
			
			// TODO Mostrar los paneles de acuerdo al rol 
			
			// Si el usuario tiene el perfil de propietario se cargan las opciones de este rol
			PanelOpciones panelOpciones = new PanelOpciones();
			getAcademUI().setPanelOpciones(panelOpciones);
			switch(usuario.getPerfil()) {
				case "Administrador":
					// Se cargan las opciones de administrador
					// Panel evaluaciones
					GestionEvaluaciones gestionEvaluaciones = new GestionEvaluaciones();
					getAcademUI().setGestionEvaluaciones(gestionEvaluaciones);
					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(getAcademUI().getGestionEvaluaciones(), "Evaluaciones");
					ControladorAdministrador.getInstance().obtenerEvaluaciones();
					
					// TODO Panel usuarios
					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(new Label("Pestaña para crear y editar usuarios. [En construcción]"), "Usuarios");
					
					getAcademUI().getPanelContenido().setContenido(getAcademUI().getPanelOpciones(), "top:0.0px;left:0.0px;");
					break;
				case "Propietario":
					// Se cargan las opciones de propietario
					
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
					getAcademUI().getPanelOpciones().getTabSheetOpciones().addTab(new Label("Pestaña para configurar los roles en una evaluación. [En construcción]"), "Roles");
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
	
	public void cerrarSesion() {
		// Invalidar la sesión
		getAcademUI().getHTTPSession().invalidate();
		
		// Quitar el nombre de usuario del panel de herramientas
		getAcademUI().getPanelSesion().setNombreUsuario("");
		
		// Ocultar todos los paneles
		getAcademUI().getPanelSesion().setVisible(false);
		getAcademUI().getPanelContenido().removerTodoElContenido();
		
		// Mostrar el formulario de inicio de sesión.
		getAcademUI().getPanelContenido().setContenido(getAcademUI().getFormularioInicioSesion());
	}
	
}
