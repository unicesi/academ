package co.edu.icesi.academ.client.perfiles.administrador;

import java.util.List;

import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.ProgramaBO;
import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.client.Controlador;
import co.edu.icesi.academ.excepciones.CrearEvaluacionException;
import co.edu.icesi.academ.excepciones.EditarEvaluacionException;
import co.edu.icesi.academ.server.EvaluacionServices;
import co.edu.icesi.academ.server.UsuarioServices;

public class ControladorAdministrador extends Controlador {

	private static ControladorAdministrador controladorAdministrador;
	private EvaluacionServices evaluacionServices;
	private UsuarioServices usuarioServices;
	
	private ControladorAdministrador() {
		evaluacionServices = new EvaluacionServices();
		usuarioServices = new UsuarioServices();
	}

	public static ControladorAdministrador getInstance() {
		if (controladorAdministrador == null) {
			controladorAdministrador = new ControladorAdministrador();
		}
		return controladorAdministrador;
	}

	public void obtenerEvaluaciones() {
		// Obtener todas las evaluaciones
		List<EvaluacionBO> evaluaciones = this.evaluacionServices.obtenerEvaluaciones();
		
		// Cargar las evaluaciones en la lista de evaluaciones del administrador
		getAcademUI().getGestionEvaluaciones().cargarEvaluaciones(evaluaciones);
	}

	public void mostrarFormularioCrearEditarEvaluacion(EvaluacionBO evaluacionBO) {
		// Inicializar formulario para crear evaluación
		FormularioCrearEditarEvaluacion formularioCrearEditarEvaluacion = new FormularioCrearEditarEvaluacion();
		
		// Obtener todos los programas disponibles y cargarlos en la lista desplegable
		List<ProgramaBO> programas = this.evaluacionServices.obtenerProgramas();
		formularioCrearEditarEvaluacion.cargarProgramas(programas);
		
		// Obtener todos los usuarios propietarios disponibles y cargarlos en la lista desplegable
		List<UsuarioBO> usuarios = this.usuarioServices.obtenerUsuariosPropietarios();
		formularioCrearEditarEvaluacion.cargarUsuarios(usuarios);
		
		String titulo = "Crear Evaluación";
		
		if (evaluacionBO != null) {
			// Obtener la información de la evaluación seleccionada
			EvaluacionBO evaluacion = this.evaluacionServices.obtenerEvaluacion(evaluacionBO);
			formularioCrearEditarEvaluacion.cargarEvaluacion(evaluacion);
			
			titulo = "Editar Evaluación";
		}
		
		// Mostrar el formulario para crear evaluación como una ventana emergente
		getAcademUI().mostrarVentanaEmergente(formularioCrearEditarEvaluacion, titulo);
	}

	public void crearEvaluacion(EvaluacionBO evaluacion) {
		try {
			// Intentar crear evaluacion
			this.evaluacionServices.crearEvaluacion(evaluacion);
			
			cerrarFormularioCrearEditarEvaluacion("Evaluación creada");
		} catch (CrearEvaluacionException e) {
			getAcademUI().getFormularioCrearEditarEvaluacion().setMensajeError(e.getMessage());
		}
	}

	public void editarEvaluacion(EvaluacionBO evaluacion) {
		try {
			// Intentar crear evaluacion
			this.evaluacionServices.editarEvaluacion(evaluacion);
			
			cerrarFormularioCrearEditarEvaluacion("Evaluación editada");
		} catch (EditarEvaluacionException e) {
			getAcademUI().getFormularioCrearEditarEvaluacion().setMensajeError(e.getMessage());
		}
	}
	
	private void cerrarFormularioCrearEditarEvaluacion(String mensaje) {
		// Cerrar la ventana emergente
		getAcademUI().cerrarVentanaEmergente();
		// Mostrar notificación de creación exitosa
		getAcademUI().mostrarNotificacion("ACaDeM", mensaje);
		// Cargar nuevamente la lista de evaluaciones del administrador
		obtenerEvaluaciones();
	}

}
