package co.edu.icesi.academ.evaluaciones;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.CalificacionBO;
import co.edu.icesi.academ.bo.NivelDeConocimientoBO;
import co.edu.icesi.academ.bo.ProgramaBO;
import co.edu.icesi.academ.bo.RolBO;
import co.edu.icesi.academ.bo.TemaBO;
import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.entities.Calificacion;
import co.edu.icesi.academ.entities.CalificacionPK;
import co.edu.icesi.academ.entities.Evaluacion;
import co.edu.icesi.academ.entities.NivelDeConocimiento;
import co.edu.icesi.academ.entities.Programa;
import co.edu.icesi.academ.entities.Rol;
import co.edu.icesi.academ.entities.RolPK;
import co.edu.icesi.academ.entities.Tema;
import co.edu.icesi.academ.entities.Usuario;
import co.edu.icesi.academ.entities.Usuario_Evaluacion;
import co.edu.icesi.academ.entities.Usuario_EvaluacionPK;
import co.edu.icesi.academ.evaluaciones.EvaluacionBeanLocal;
import co.edu.icesi.academ.evaluaciones.EvaluacionBeanRemote;
import co.edu.icesi.academ.excepciones.CrearEvaluacionException;
import co.edu.icesi.academ.excepciones.EditarEvaluacionException;

/**
 * Session Bean implementation class AssessmentBean
 */
@Stateless
public class EvaluacionBean implements EvaluacionBeanRemote, EvaluacionBeanLocal {

	@PersistenceContext(unitName = "DTPersistenceUnit")
	protected EntityManager entityManager;

	public EvaluacionBean() {

	}

	@Override
	public EvaluacionBO crearEvaluacion(EvaluacionBO evaluacionBO) throws CrearEvaluacionException {
		try {
			Evaluacion evaluacion = actualizarEvaluacion(new Evaluacion(), evaluacionBO);
			return evaluacion.toBO();
		} catch (Exception e) {
			throw new CrearEvaluacionException(e.getMessage());
		}
	}

	@Override
	public EvaluacionBO editarEvaluacion(EvaluacionBO evaluacionBO) throws EditarEvaluacionException {
		try {
			Evaluacion evaluacion = actualizarEvaluacion(entityManager.find(Evaluacion.class, evaluacionBO.getId()), evaluacionBO);
			return evaluacion.toBO();
		} catch (Exception e) {
			throw new EditarEvaluacionException(e.getMessage());
		}
	}

	private Evaluacion actualizarEvaluacion(Evaluacion evaluacion, EvaluacionBO evaluacionBO) {
		evaluacion.setFechaInicial(evaluacionBO.getFechaInicial());
		evaluacion.setFechaFinal(evaluacionBO.getFechaFinal());
		Programa programa = entityManager.find(Programa.class, evaluacionBO.getPrograma().getCodigo());
		evaluacion.setPrograma(programa);
		Usuario propietario = entityManager.find(Usuario.class, evaluacionBO.getPropietario().getNombre());
		evaluacion.setPropietario(propietario);
		entityManager.persist(evaluacion);
		entityManager.flush();
		return evaluacion;
	}

	@Override
    public EvaluacionBO guardarEvaluadores(EvaluacionBO evaluacionBO) {
		List<UsuarioBO> evaluadores = obtenerEvaluadoresDeEvaluacion(evaluacionBO);
		
		for (UsuarioBO usuarioBO : evaluacionBO.getEvaluadores()) {
			Usuario_Evaluacion ue = new Usuario_Evaluacion();
			Usuario_EvaluacionPK uePK = new Usuario_EvaluacionPK();
			uePK.setEvaluacion(evaluacionBO.getId());
			uePK.setUsuario(usuarioBO.getNombre());
			ue.setId(uePK);
			if(!evaluadores.contains(usuarioBO))
				entityManager.persist(ue);
		}
		
		for (UsuarioBO usuarioBO : evaluadores) {
			Usuario_EvaluacionPK uePK = new Usuario_EvaluacionPK();
			uePK.setEvaluacion(evaluacionBO.getId());
			uePK.setUsuario(usuarioBO.getNombre());
			if(!evaluacionBO.getEvaluadores().contains(usuarioBO)){
				TypedQuery<Usuario_Evaluacion> query = entityManager.createNamedQuery("removerEvaluadoresDeEvaluacion", Usuario_Evaluacion.class);
				query.setParameter("usuario", "%" + usuarioBO.getNombre() + "%");
				query.setParameter("evaluacion", "%" + evaluacionBO.getId() + "%");
				
				query.executeUpdate();
			}
		}
		entityManager.flush();
		return entityManager.find(Evaluacion.class, evaluacionBO.getId()).toBO();
    }

	@Override
	public EvaluacionBO configurarRolesDeEvaluacion(EvaluacionBO evaluacionBO) {
		Evaluacion evaluacion = entityManager.find(Evaluacion.class, evaluacionBO.getId());
		List<Rol> roles = new ArrayList<Rol>();
		for (RolBO rolBO : evaluacionBO.getRoles()) {
			Rol rol = new Rol();
			RolPK rolPK = new RolPK();
			rolPK.setNombre(rolBO.getNombre());
			rolPK.setEvaluacion(rolBO.getEvaluacion());
			rol.setId(rolPK);
			roles.add(rol);
		}
		evaluacion.setRoles(roles);
		entityManager.persist(evaluacion);
		entityManager.flush();
		return evaluacion.toBO();
	}

	@Override
	public List<EvaluacionBO> obtenerEvaluacionesDePropietario(UsuarioBO propietario) {
		TypedQuery<Evaluacion> query = entityManager.createNamedQuery("obtenerEvaluacionesDePropietario", Evaluacion.class);
		query.setParameter("propietario", "%" + propietario.getNombre() + "%");
		List<Evaluacion> evaluaciones = query.getResultList();
		List<EvaluacionBO> evaluacionesBO = new ArrayList<EvaluacionBO>();
		for (Evaluacion e : evaluaciones) {
			evaluacionesBO.add(e.toBO());
		}
		return evaluacionesBO;
	}

	@Override
	public List<EvaluacionBO> obtenerEvaluacionesDeEvaluador(UsuarioBO evaluador) {
		TypedQuery<Usuario_Evaluacion> query = entityManager.createNamedQuery("obtenerEvaluacionesDeEvaluador", Usuario_Evaluacion.class);
		query.setParameter("evaluador", "%" + evaluador.getNombre() + "%");
		List<Usuario_Evaluacion> evaluadores = query.getResultList();
		List<EvaluacionBO> evaluacionesBO = new ArrayList<EvaluacionBO>();

		for (Usuario_Evaluacion e : evaluadores) {
			evaluacionesBO.add(entityManager.find(Evaluacion.class, e.getId().getEvaluacion()).toBO());
		}
		return evaluacionesBO;
	}

	@Override
	public EvaluacionBO obtenerEvaluacion(EvaluacionBO evaluacionBO) {
		Evaluacion evaluacion = entityManager.find(Evaluacion.class, evaluacionBO.getId());
		return evaluacion.toBO();
	}

	@Override
	public List<TemaBO> obtenerTemas() {
		TypedQuery<Tema> query = entityManager.createNamedQuery("obtenerTemas",	Tema.class);
		List<Tema> temas = query.getResultList();
		List<TemaBO> temasBO = new ArrayList<TemaBO>();

		for (Tema tema : temas) {
			temasBO.add(tema.toBO());
		}
		return temasBO;
	}

	@Override
	public List<NivelDeConocimientoBO> obtenerNivelesDeConocimiento() {
		TypedQuery<NivelDeConocimiento> query = entityManager.createNamedQuery("obtenerNivelesDeConocimiento", NivelDeConocimiento.class);
		List<NivelDeConocimiento> nivelesDeConocimiento = query.getResultList();
		List<NivelDeConocimientoBO> nivelesDeConocimientoBO = new ArrayList<NivelDeConocimientoBO>();

		for (NivelDeConocimiento nivelDeConocimiento : nivelesDeConocimiento) {
			nivelesDeConocimientoBO.add(nivelDeConocimiento.toBO());
		}
		return nivelesDeConocimientoBO;
	}

	@Override
	public void guardarCalificacionEvaluacion(List<CalificacionBO> calificacionesBO) {
		Calificacion calificacion;
		CalificacionPK calificacionPK;
		for (CalificacionBO calificacionBO : calificacionesBO) {
			calificacion = new Calificacion();
			calificacionPK = new CalificacionPK();

			calificacionPK.setEvaluacion(calificacionBO.getEvaluacion().getId());
			calificacionPK.setEvaluador(calificacionBO.getEvaluador());
			calificacionPK.setNivelDeConocimiento(calificacionBO.getNivelDeConocimiento().getId());
			calificacionPK.setTema(calificacionBO.getTema().getId());

			calificacion.setId(calificacionPK);
			
			// Remover la calificación previa, si existe una
			TypedQuery<Calificacion> query = entityManager.createNamedQuery("removerCalificacionPrevia", Calificacion.class);
			query.setParameter("evaluador", "%" + calificacionPK.getEvaluador() + "%");
			query.setParameter("evaluacion", "%" + calificacionPK.getEvaluacion() + "%");
			query.setParameter("tema", "%" + calificacionPK.getTema() + "%");
			query.executeUpdate();

			entityManager.persist(calificacion);
			entityManager.flush();
		}
	}

	@Override
	public List<CalificacionBO> obtenerCalificacionesEvaluadorEvaluacion(UsuarioBO evaluadorBO, EvaluacionBO evaluacionBO) {
		TypedQuery<Calificacion> query = entityManager.createNamedQuery("obtenerCalificacionesEvaluadorEvaluacion", Calificacion.class);
		query.setParameter("evaluador", "%" + evaluadorBO.getNombre() + "%");
		query.setParameter("evaluacion", "%" + evaluacionBO.getId() + "%");
		List<Calificacion> calificaciones = query.getResultList();
		List<CalificacionBO> calificacionesBO = new ArrayList<CalificacionBO>();
		for (Calificacion calificacion : calificaciones) {
			calificacionesBO.add(calificacion.toBO());
		}
		return calificacionesBO;
	}

	@Override
	public List<EvaluacionBO> obtenerEvaluaciones() {
		TypedQuery<Evaluacion> query = entityManager.createNamedQuery("obtenerEvaluaciones", Evaluacion.class);
		List<Evaluacion> evaluaciones = query.getResultList();
		List<EvaluacionBO> evaluacionesBO = new ArrayList<EvaluacionBO>();
		for (Evaluacion e : evaluaciones) {
			evaluacionesBO.add(e.toBO());
		}
		return evaluacionesBO;
	}

	@Override
	public List<ProgramaBO> obtenerProgramas() {
		TypedQuery<Programa> query = entityManager.createNamedQuery("obtenerProgramas", Programa.class);
		List<Programa> programas = query.getResultList();
		List<ProgramaBO> programasBO = new ArrayList<ProgramaBO>();

		for (Programa programa : programas) {
			programasBO.add(programa.toBO());
		}
		return programasBO;
	}

	@Override
	public List<UsuarioBO> obtenerUsuariosDisponibles(EvaluacionBO evaluacionBO) {
		TypedQuery<Usuario> query = entityManager.createNamedQuery("obtenerUsuariosDisponibles", Usuario.class);
		query.setParameter("evaluacion", "%" + evaluacionBO.getId() + "%");
		List<Usuario> usuarios = query.getResultList();
		List<UsuarioBO> usuariosBO = new ArrayList<UsuarioBO>();
		
		for (Usuario usuario : usuarios) {
			usuariosBO.add(usuario.toBO());
		}
		return usuariosBO;
	}

	@Override
	public List<UsuarioBO> obtenerEvaluadoresDeEvaluacion(EvaluacionBO evaluacionBO) {
		TypedQuery<Usuario_Evaluacion> query = entityManager.createNamedQuery("obtenerEvaluadoresDeEvaluacion", Usuario_Evaluacion.class);
		query.setParameter("evaluacion", "%" + evaluacionBO.getId() + "%");
		List<Usuario_Evaluacion> evaluadores = query.getResultList();
		List<UsuarioBO> usuariosBO = new ArrayList<UsuarioBO>();

		UsuarioBO usuario;
		for (Usuario_Evaluacion usuarioEvaluacion : evaluadores) {
			usuario = new UsuarioBO();
			usuario.setNombre(usuarioEvaluacion.getId().getUsuario());
			usuariosBO.add(usuario);
		}
		return usuariosBO;
	}
}
