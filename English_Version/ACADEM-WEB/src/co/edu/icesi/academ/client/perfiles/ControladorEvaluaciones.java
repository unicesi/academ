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
			case "Propietario":
				// Opciones del propietario
				ControladorPropietario.getInstance().cargarEvaluadores(evaluacionBO);
				// TODO Cargar roles
				ControladorPropietario.getInstance().cargarTemasConsolidado();
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
