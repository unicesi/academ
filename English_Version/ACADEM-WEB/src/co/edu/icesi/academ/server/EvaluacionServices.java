package co.edu.icesi.academ.server;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.CalificacionBO;
import co.edu.icesi.academ.bo.NivelDeConocimientoBO;
import co.edu.icesi.academ.bo.ProgramaBO;
import co.edu.icesi.academ.bo.TemaBO;
import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.evaluaciones.EvaluacionBeanRemote;
import co.edu.icesi.academ.excepciones.CrearEvaluacionException;
import co.edu.icesi.academ.excepciones.EditarEvaluacionException;

public class EvaluacionServices {

	private InitialContext context;
	private EvaluacionBeanRemote evaluacionBean;
	
	public EvaluacionServices() {
		doLookup();
	}
	
	private void doLookup() 
	{
		try 
		{
			context = new InitialContext();
			evaluacionBean = (EvaluacionBeanRemote)context.lookup("java:global/ACADEM-EAR/ACADEM-EJB/EvaluacionBean!co.edu.icesi.academ.evaluaciones.EvaluacionBeanRemote");											
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
	}
	
	public List<EvaluacionBO> obtenerEvaluacionesDeParticipante(UsuarioBO evaluador){
		return evaluacionBean.obtenerEvaluacionesDeEvaluador(evaluador);
	}
	
	public List<TemaBO> obtenerTemas(){
		return evaluacionBean.obtenerTemas();
	}
	
	public List<NivelDeConocimientoBO> obtenerNivelesDeConocimiento(){
		return evaluacionBean.obtenerNivelesDeConocimiento();
	}
	
	public void guardarCalificacionEvaluacion(List<CalificacionBO> calificaciones){
		evaluacionBean.guardarCalificacionEvaluacion(calificaciones);
	}
	
	public List<EvaluacionBO> obtenerEvaluacionesDePropietario(UsuarioBO propietario) {
		return evaluacionBean.obtenerEvaluacionesDePropietario(propietario);
	}
	
	public EvaluacionBO crearEvaluacion(EvaluacionBO evaluacion) throws CrearEvaluacionException {
		return evaluacionBean.crearEvaluacion(evaluacion);
	}

	public EvaluacionBO obtenerEvaluacion(EvaluacionBO evaluacion) {
		return evaluacionBean.obtenerEvaluacion(evaluacion);
	}

	public List<CalificacionBO> obtenerCalificacionesEvaluacion(UsuarioBO evaluador, EvaluacionBO evaluacion) {
		return evaluacionBean.obtenerCalificacionesEvaluadorEvaluacion(evaluador, evaluacion);
	}

	public List<EvaluacionBO> obtenerEvaluaciones() {
		return evaluacionBean.obtenerEvaluaciones();
	}

	public List<ProgramaBO> obtenerProgramas() {
		return evaluacionBean.obtenerProgramas();
	}

	public EvaluacionBO editarEvaluacion(EvaluacionBO evaluacion) throws EditarEvaluacionException {
		return evaluacionBean.editarEvaluacion(evaluacion);
	}
	
	public List<UsuarioBO> obtenerUsuariosDisponibles(EvaluacionBO evaluacionBO){
		return evaluacionBean.obtenerUsuariosDisponibles(evaluacionBO);
	}
	
	public List<UsuarioBO> obtenerEvaluadoresDeEvaluacion(EvaluacionBO evaluacionBO){
		return evaluacionBean.obtenerEvaluadoresDeEvaluacion(evaluacionBO);
	}
	
	public EvaluacionBO guardarEvaluadores(EvaluacionBO evaluacionBO){
		return evaluacionBean.guardarEvaluadores(evaluacionBO);
	}
}
