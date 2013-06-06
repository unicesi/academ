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

package co.edu.icesi.academ.evaluaciones;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.edu.icesi.academ.bo.CalificacionRolBO;
import co.edu.icesi.academ.bo.CriterioBO;
import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.CalificacionBO;
import co.edu.icesi.academ.bo.FactorDeImpactoBO;
import co.edu.icesi.academ.bo.NivelCriterioBO;
import co.edu.icesi.academ.bo.NivelDeConocimientoBO;
import co.edu.icesi.academ.bo.ProgramaBO;
import co.edu.icesi.academ.bo.ResultadoAprendizajeBO;
import co.edu.icesi.academ.bo.RolBO;
import co.edu.icesi.academ.bo.RubricaBO;
import co.edu.icesi.academ.bo.TemaBO;
import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.entities.Calificacion;
import co.edu.icesi.academ.entities.CalificacionPK;
import co.edu.icesi.academ.entities.Criterio_Rubrica;
import co.edu.icesi.academ.entities.Criterio_RubricaPK;
import co.edu.icesi.academ.entities.Evaluacion;
import co.edu.icesi.academ.entities.FactorDeImpacto;
import co.edu.icesi.academ.entities.FactorDeImpactoPK;
import co.edu.icesi.academ.entities.NivelDeConocimiento;
import co.edu.icesi.academ.entities.Nivel_Rubrica;
import co.edu.icesi.academ.entities.Nivel_RubricaPK;
import co.edu.icesi.academ.entities.Programa;
import co.edu.icesi.academ.entities.ResultadoAprendizaje;
import co.edu.icesi.academ.entities.ResultadoAprendizajePK;
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
import co.edu.icesi.academ.excepciones.EvaluacionException;

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
	public List<CalificacionBO> obtenerCalificacionesEvaluacion( EvaluacionBO evaluacionBO) 
	{
		TypedQuery<Calificacion> query = entityManager.createNamedQuery("obtenerCalificacionesEvaluacion", Calificacion.class);
		query.setParameter("evaluacion", "%" + evaluacionBO.getId() + "%");
		List<Calificacion> calificaciones = query.getResultList();
		List<CalificacionBO> calificacionesBO = new ArrayList<CalificacionBO>();
		for (Calificacion calificacion : calificaciones) 
		{
			calificacionesBO.add(calificacion.toBO());
		}
		return calificacionesBO;
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

	@Override
	public List<RolBO> obtenerRolesEvaluacion(EvaluacionBO evaluacionBO) {
		TypedQuery<Rol> query = entityManager.createNamedQuery("obtenerRolesEvaluacion", Rol.class);
		query.setParameter("evaluacion", "%" + evaluacionBO.getId() + "%");
		List<Rol> roles = query.getResultList();
		List<RolBO> rolesBO = new ArrayList<RolBO>();

		for (Rol rol : roles) {
			rolesBO.add(rol.toBO());
		}
		return rolesBO;
	}

	@Override
	public List<Integer> obtenerPromedioGlobal(EvaluacionBO evaluacionBO) {
		List<Integer> resultado = new ArrayList<Integer>();
		List<CalificacionBO> calificaciones = obtenerCalificacionesEvaluacion(evaluacionBO);
		for (int i = 0; i < calificaciones.size(); i++) 
		{
			List<RolBO> roles = evaluacionBO.getRoles();
			double prom = 0;
			for (int j = 0; j < roles.size(); j++) 
			{
				List<CalificacionRolBO> calificacionDeRol = obtenerPromedioPorRol(evaluacionBO, roles.get(j) );
				List<FactorDeImpactoBO> factores = obtenerFactoresDeImpactoRol(evaluacionBO, roles.get(j));
				FactorDeImpactoBO factor = null;
				for (int k = 0; k < factores.size(); k++) 
				{
					if( factores.get(k).getTema().equals( calificaciones.get(i).getTema().getId() ) )
						factor = factores.get(k);
						
				}				
				try 
				{
					prom += calificacionDeRol.get(i).getNivelDeConocimiento().getId() * ( factor.getFactorDeImpacto() / 100 );
				} 
				catch (Exception e) 
				{
					
				}
			}
			resultado.add( (int) ( prom + 0.5 ) );
		}
		
		
		return resultado;
	}



	@Override
	public List<CalificacionRolBO>obtenerPromedioPorRol(EvaluacionBO evaluacionBO,RolBO rolBO)
	{
		List<CalificacionRolBO> calificacionesProm = new ArrayList<CalificacionRolBO>();		
		List<UsuarioBO>usuariosdelRol=filtarRoles(evaluacionBO.getEvaluadores(),rolBO);
		List<CalificacionBO> calificaciones = obtenerCalificacionesEvaluacion(evaluacionBO);
		TreeMap<String, NivelDeConocimientoBO> tree = new TreeMap<String, NivelDeConocimientoBO>();
		TreeMap<String, TemaBO> treeAux = new TreeMap<String, TemaBO>();
		for (int i = 0; i < usuariosdelRol.size(); i++) 
		{
			String nombreUsuarioRolActual = usuariosdelRol.get(i).getNombre();
			for (int j = 0; j < calificaciones.size(); j++) 
			{
				String evaluadorActual = calificaciones.get(j).getEvaluador();
				if( evaluadorActual.equals(nombreUsuarioRolActual) )
				{
					TemaBO tema = calificaciones.get(j).getTema();
					if( tree.containsKey( tema.getId() ) )
					{
						NivelDeConocimientoBO nivel = tree.get(tema.getId());
						int suma = tree.get( tema.getId() ).getId() + calificaciones.get(j).getNivelDeConocimiento().getId();
						nivel.setId(suma);
						tree.put( tema.getId(), nivel );
						treeAux.put(tema.getId(), tema);
					}
					else
						tree.put( tema.getId(), calificaciones.get(j).getNivelDeConocimiento() );
				}
			}
		}
		Set<String> ids = tree.keySet();
		for( String idAct : ids )
		{
			NivelDeConocimientoBO nivel = tree.get(idAct);
			double aux = ( (double)nivel.getId() / (double)usuariosdelRol.size() ) + 0.5;
			int div = (int) aux;
			nivel.setId(div);
			CalificacionRolBO crBO = new CalificacionRolBO(treeAux.get(idAct), rolBO, nivel );
			calificacionesProm.add(crBO);
		}
		return calificacionesProm;
	}

	private List<UsuarioBO> filtarRoles(List<UsuarioBO> usuariosBO , RolBO rolBO){
		List<UsuarioBO> usuarios= new ArrayList<UsuarioBO>();
		for(int i=0;i<usuariosBO.size();i++){
			UsuarioBO usuario=usuariosBO.get(i);
			for(int j=0;j<usuario.getRoles().size();j++){
				if(usuario.getRoles().get(j).getNombre().equalsIgnoreCase(rolBO.getNombre())){
					usuarios.add(usuariosBO.get(i));
				}
			}
		}
		return usuarios;
	}


	@Override
	public List<FactorDeImpactoBO> obtenerFactoresDeImpacto(EvaluacionBO evaluacionBO, UsuarioBO usuarioBO) {
		TypedQuery<FactorDeImpacto> query = entityManager.createNamedQuery("obtenerFactoresDeImpacto", FactorDeImpacto.class);
		Usuario usuario = entityManager.find(Usuario.class, usuarioBO.getNombre());
		query.setParameter("propietario", usuario);
		query.setParameter("evaluacion",  evaluacionBO.getId());
		List<FactorDeImpacto> factoresDeImpacto = query.getResultList();
		List<FactorDeImpactoBO> factoresDeImpactoBO = new ArrayList<FactorDeImpactoBO>();

		for (FactorDeImpacto factor : factoresDeImpacto) {
			factoresDeImpactoBO.add(factor.toBO());
		}
		return factoresDeImpactoBO;
	}

	@Override
	public List<FactorDeImpactoBO> obtenerFactoresDeImpactoRol(EvaluacionBO evaluacionBO, RolBO rolBO) {
		TypedQuery<FactorDeImpacto> query = entityManager.createNamedQuery("obtenerFactoresDeImpactoRol", FactorDeImpacto.class);
		query.setParameter("evaluacion",evaluacionBO.getId());
		query.setParameter("rol",rolBO.getNombre());
		List<FactorDeImpacto> factoresDeImpacto = query.getResultList();
		List<FactorDeImpactoBO> factoresDeImpactoBO = new ArrayList<FactorDeImpactoBO>();

		for (FactorDeImpacto factor : factoresDeImpacto) {
			factoresDeImpactoBO.add(factor.toBO());
		}
		return factoresDeImpactoBO;
	}


	@Override
	public void guardarFactoresDeImpacto(List<FactorDeImpactoBO> factoresDeImpactoBO) {
		FactorDeImpacto factorDeImpacto;
		FactorDeImpactoPK factorDeImpactoPK;
		for (FactorDeImpactoBO factorDeImpactoBO : factoresDeImpactoBO) {
			factorDeImpacto = new FactorDeImpacto();
			factorDeImpactoPK = new FactorDeImpactoPK();

			factorDeImpactoPK.setEvaluacion(factorDeImpactoBO.getEvaluacion());
			factorDeImpactoPK.setRol(factorDeImpactoBO.getRol());
			factorDeImpactoPK.setTema(factorDeImpactoBO.getTema());

			factorDeImpacto.setId(factorDeImpactoPK);
			factorDeImpacto.setFactorDeImpacto(factorDeImpactoBO.getFactorDeImpacto());

			// Remover el factor de impacto previo, si existe uno
			TypedQuery<FactorDeImpacto> query = entityManager.createNamedQuery("removerFactorDeImpactoPrevio", FactorDeImpacto.class);
			query.setParameter("rol", "%" + factorDeImpactoPK.getRol() + "%");
			query.setParameter("evaluacion", "%" + factorDeImpactoPK.getEvaluacion() + "%");
			query.setParameter("tema", "%" + factorDeImpactoPK.getTema() + "%");
			query.executeUpdate();

			entityManager.persist(factorDeImpacto);
			entityManager.flush();
		}
	}

	/**
	 * Metodo que se utiliza para poder settear el entityManager para realizar pruebas funcionales.
	 * @param ent, el mock del entityManager a utilizar.
	 */
	public void setEntityManager(EntityManager ent) {
		entityManager = ent;
	}

	@Override
	public RubricaBO guardarRubrica(RubricaBO rubricaBO) throws EvaluacionException {
		ResultadoAprendizajePK resultadoPK = new ResultadoAprendizajePK();
		resultadoPK.setPrograma( rubricaBO.getResultadoAprendizaje().getPrograma().getCodigo() );
		resultadoPK.setTema( rubricaBO.getResultadoAprendizaje().getTema().getId() );
		
		ResultadoAprendizaje resultado = entityManager.find( ResultadoAprendizaje.class, resultadoPK );
		resultado.getCriteriosRubricas().clear();//Borra la rubrica anterior
		
		for( CriterioBO criterioBO : rubricaBO.getCriterios() )
		{
			Criterio_RubricaPK criterioPK = new Criterio_RubricaPK();
			criterioPK.setId( criterioBO.getId() );
			criterioPK.setPrograma( rubricaBO.getResultadoAprendizaje().getPrograma().getCodigo() );
			criterioPK.setTema( rubricaBO.getResultadoAprendizaje().getTema().getId() );
			
			Criterio_Rubrica criterio = new Criterio_Rubrica();
			criterio.setId( criterioPK );
			criterio.setDescripcion( criterioBO.getDescripcion( ) );
			criterio.setNivelesRubricas( new LinkedList<Nivel_Rubrica>( ) );
			criterio.setResultadosAprendizaje(resultado);
			resultado.getCriteriosRubricas().add( criterio );
			
			for( NivelCriterioBO nivelBO : criterioBO.getNiveles() )
			{
				Nivel_RubricaPK nivelPK = new Nivel_RubricaPK();
				nivelPK.setId( nivelBO.getId() );
				nivelPK.setPrograma( rubricaBO.getResultadoAprendizaje().getPrograma().getCodigo() );
				nivelPK.setTema( rubricaBO.getResultadoAprendizaje().getTema().getId() );
				nivelPK.setCriterio( criterio.getId().getId() );
				
				Nivel_Rubrica nivel = new Nivel_Rubrica();
				nivel.setId(nivelPK);
				nivel.setNivel( nivelBO.getNivel() );
				nivel.setDescripcion( nivelBO.getDescripcion() );
				nivel.setCriteriosRubrica(criterio);
				criterio.getNivelesRubricas().add( nivel );
			}
		}
		
		entityManager.merge( resultado );
		entityManager.flush();
		
		return obtenerRubrica( rubricaBO.getResultadoAprendizaje( ) );
	}

	@Override
	public RubricaBO obtenerRubrica(ResultadoAprendizajeBO resultadoBO)throws EvaluacionException {
		ResultadoAprendizajePK resultadoPK = new ResultadoAprendizajePK();
		resultadoPK.setPrograma( resultadoBO.getPrograma().getCodigo() );
		resultadoPK.setTema( resultadoBO.getTema().getId() );
		
		ResultadoAprendizaje resultado = entityManager.find(ResultadoAprendizaje.class, resultadoPK);
		
		RubricaBO rubricaBO = new RubricaBO();
		rubricaBO.setResultadoAprendizaje( resultado.toBO() );
		
		List<CriterioBO> criterios = new LinkedList<CriterioBO>();
		for( Criterio_Rubrica criterio : resultado.getCriteriosRubricas() )
			criterios.add( criterio.toBO() );
		
		rubricaBO.setCriterios(criterios);
		
		return rubricaBO;
	}

	@Override
	public UsuarioBO guardarRoles(UsuarioBO usuarioBO)throws EvaluacionException {
		Usuario usuario = entityManager.find( Usuario.class, usuarioBO.getNombre());
		List<Rol> roles = usuario.getRoles();
		roles.clear();
		
		for( RolBO rolBO : usuarioBO.getRoles() )
		{
			RolPK rolPK = new RolPK();
			rolPK.setEvaluacion( rolBO.getEvaluacion() );
			rolPK.setNombre( rolBO.getNombre() );
			
			Rol rol = entityManager.find( Rol.class, rolPK );
			
			if( rol == null )
				throw new EvaluacionException( "El rol " + rolBO.getNombre() + " en la evaluacion " + rolBO.getEvaluacion() + " no existe. " );
			
			roles.add( rol );
		}
		
		entityManager.merge( usuario );
		entityManager.flush( );
		
		return entityManager.find( Usuario.class, usuarioBO.getNombre()).toBO();
	}

	@Override
	public void guardarResultadosAprendizajes(List<ResultadoAprendizajeBO> ListaResultadosAprendizajeBO,	ProgramaBO programaBO) {
		ResultadoAprendizaje resultadoAprendizaje;
		ResultadoAprendizajePK resultadoAprendizajePK;
		for (ResultadoAprendizajeBO resultado: ListaResultadosAprendizajeBO) {
			
			//Crear un nuevo Resultado de aprendizaje
			resultadoAprendizaje= new ResultadoAprendizaje();
			resultadoAprendizajePK = new ResultadoAprendizajePK();
			NivelDeConocimiento nivelDeConocimiento = entityManager.find(NivelDeConocimiento.class, resultado.getNivelDeConocimiento().getId());
			resultadoAprendizajePK.setPrograma(programaBO.getCodigo());
			resultadoAprendizajePK.setTema(resultado.getTema().getId());

			resultadoAprendizaje.setId(resultadoAprendizajePK);
			//resultadoAprendizaje.setCompetencia(null);
			resultadoAprendizaje.setNivelDeConocimiento(nivelDeConocimiento);
			//Eliminar el resultado de aprendizae previo
			/**TypedQuery<ResultadoAprendizaje> query = entityManager.createNamedQuery("removerResultadoAprendizaje", ResultadoAprendizaje.class);
			query.setParameter("programa", "%" + resultadoAprendizajePK.getPrograma() + "%");
			query.setParameter("tema", "%" + resultadoAprendizajePK.getTema() + "%");
			query.executeUpdate();*/
			
			System.out.println("Persist");
			System.out.println("Programa "+resultadoAprendizaje.getId().getPrograma());
			System.out.println("Tema "+resultadoAprendizaje.getId().getTema());
			System.out.println("Nivel "+resultadoAprendizaje.getNivelDeConocimiento().getId());
			entityManager.persist(resultadoAprendizaje);
			
		}	
		entityManager.flush();
	}
	
	@Override
	public List<ResultadoAprendizajeBO> obtenerResultadosAprendizaje( ProgramaBO programaBO )
	{
		TypedQuery<ResultadoAprendizaje> query = entityManager.createNamedQuery("obtenerResultadosAprendizajePrograma", ResultadoAprendizaje.class );
		query.setParameter("programa", "%" + programaBO.getCodigo() + "%");
		
		List<ResultadoAprendizaje> resultados = query.getResultList();
		
		List<ResultadoAprendizajeBO> resultadosBO = new LinkedList<ResultadoAprendizajeBO>( );
		
		for( ResultadoAprendizaje resultado : resultados )
			resultadosBO.add( resultado.toBO( ) );
		
		return resultadosBO;
	}
	
}
