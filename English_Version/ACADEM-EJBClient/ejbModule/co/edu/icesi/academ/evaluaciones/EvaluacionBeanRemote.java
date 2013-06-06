package co.edu.icesi.academ.evaluaciones;

import java.util.List;

import javax.ejb.Remote;

import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.CalificacionBO;
import co.edu.icesi.academ.bo.NivelDeConocimientoBO;
import co.edu.icesi.academ.bo.ProgramaBO;
import co.edu.icesi.academ.bo.TemaBO;
import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.excepciones.CrearEvaluacionException;
import co.edu.icesi.academ.excepciones.EditarEvaluacionException;

@Remote
public interface EvaluacionBeanRemote {

	public List<EvaluacionBO> obtenerEvaluacionesDePropietario(UsuarioBO propietario);
	
	public EvaluacionBO crearEvaluacion(EvaluacionBO evaluacionBO) throws CrearEvaluacionException;

	public EvaluacionBO guardarEvaluadores(EvaluacionBO evaluacionBO);

	public EvaluacionBO configurarRolesDeEvaluacion(EvaluacionBO evaluacionBO);
	
	public List<EvaluacionBO> obtenerEvaluacionesDeEvaluador(UsuarioBO evaluador);
	
	public List<TemaBO> obtenerTemas();
	
	public List<NivelDeConocimientoBO> obtenerNivelesDeConocimiento();
	
	public void guardarCalificacionEvaluacion(List<CalificacionBO> calificacionesBO);
	
	public List<UsuarioBO> obtenerUsuariosDisponibles(EvaluacionBO evaluacionBO);

	public List<UsuarioBO> obtenerEvaluadoresDeEvaluacion(EvaluacionBO evaluacionBO);
	
	public EvaluacionBO obtenerEvaluacion(EvaluacionBO evaluacionBO);

	public List<CalificacionBO> obtenerCalificacionesEvaluadorEvaluacion(UsuarioBO evaluadorBO, EvaluacionBO evaluacionBO);

	public List<EvaluacionBO> obtenerEvaluaciones();

	public List<ProgramaBO> obtenerProgramas();

	public EvaluacionBO editarEvaluacion(EvaluacionBO evaluacionBO) throws EditarEvaluacionException;

}
