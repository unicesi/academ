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
		getAcademUI().mostrarNotificacion("ACaDeM", "Response saved.");
	}
}
