package co.edu.icesi.academ.client.perfiles.propietario;

import java.util.List;

import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.TemaBO;
import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.client.Controlador;
import co.edu.icesi.academ.server.EvaluacionServices;

public class ControladorPropietario extends Controlador {

	private static ControladorPropietario controladorPropietario;
	private EvaluacionServices evaluacionServices;
	
	private ControladorPropietario() {
		evaluacionServices = new EvaluacionServices();
	}
	
	public static ControladorPropietario getInstance() {
		if (controladorPropietario == null) {
			controladorPropietario = new ControladorPropietario();
		}
		return controladorPropietario;
	}
	
	public void obtenerEvaluacionesDelPropietario() {
		// Obtener el usuario del propietario
		UsuarioBO propietario = (UsuarioBO) getAcademUI().getHTTPSession().getAttribute("usuario");
		
		// Obtener las evaluaciones del propietario
		List<EvaluacionBO> evaluaciones = this.evaluacionServices.obtenerEvaluacionesDePropietario(propietario);
		
		// Cargar los assessments del propietario en la lista de evaluaciones
		getAcademUI().getListadoEvaluaciones().cargarEvaluaciones(evaluaciones);
	}
	
	public void cargarEvaluadores(EvaluacionBO evaluacion){
		List<UsuarioBO> usuariosDisponibles = evaluacionServices.obtenerUsuariosDisponibles(evaluacion);
		getAcademUI().getListadoEvaluadores().cargarUsuariosDisponibles(usuariosDisponibles);
		List<UsuarioBO> evaluadores = evaluacionServices.obtenerEvaluadoresDeEvaluacion(evaluacion);
		getAcademUI().getListadoEvaluadores().cargarEvaluadores(evaluadores);
	}
	
	public void guardarEvaluadores(List<UsuarioBO> evaluadores){
		EvaluacionBO evaluacion = (EvaluacionBO) getAcademUI().getHTTPSession().getAttribute("evaluacion");
		evaluacion.setEvaluadores(evaluadores);
		evaluacionServices.guardarEvaluadores(evaluacion);
		getAcademUI().mostrarNotificacion("ACaDeM", "Evaluadores configurados");
	}

	public void cargarTemasConsolidado() {
		List<TemaBO> temas = evaluacionServices.obtenerTemas();
		getAcademUI().getConsolidadoEvaluacion().cargarTemas(temas);
	}
}
