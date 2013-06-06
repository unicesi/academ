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

package co.edu.icesi.academ.competencias;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.edu.icesi.academ.bo.CompetenciaBO;
import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.ResultadoAprendizajeBO;
import co.edu.icesi.academ.entities.Competencia;
import co.edu.icesi.academ.entities.Programa;
import co.edu.icesi.academ.entities.ResultadoAprendizaje;
import co.edu.icesi.academ.entities.ResultadoAprendizajePK;
import co.edu.icesi.academ.excepciones.CrearCompetenciaException;
@Stateless
public class CompetenciaBean implements CompetenciasBeanLocal, CompetenciasBeanRemote {

	@PersistenceContext(unitName = "DTPersistenceUnit")
	protected EntityManager entityManager;

	@Override
	public CompetenciaBO crearCompetencia(CompetenciaBO competenciaBO) throws CrearCompetenciaException {
		
		System.err.println("entro al bean");
		try {
			Competencia competencia = ActualizarCompetencia(new Competencia(), competenciaBO);
			return competencia.toBO();

		} catch (Exception e) {
			throw new CrearCompetenciaException(e.getMessage());
		}

	}
	
	private Competencia ActualizarCompetencia(Competencia competencia,
			CompetenciaBO competenciaBO) {
		System.err.println("esta actualizando");
		competencia.setDescripcion(competenciaBO.getDescripcion());
		competencia.setId(competenciaBO.getId());
		Programa programa = entityManager.find(Programa.class, competenciaBO.getPrograma().getCodigo());
		System.err.println("encontro el programa");
		competencia.setPrograma(programa);
		entityManager.persist(competencia);
		entityManager.flush();
		System.err.println("termino de actualizar");
		return competencia;
	}

	@Override
	public CompetenciaBO editarCompetencia(CompetenciaBO competenciaBO) 
			throws CrearCompetenciaException {
		try {
			Competencia competencia = ActualizarCompetencia(entityManager.find(Competencia.class, competenciaBO.getId()), competenciaBO);
			return competencia.toBO();

		} catch (Exception e) {
			throw new CrearCompetenciaException(e.getMessage());
		}
	}

	@Override
	public CompetenciaBO guardarResultadosAprendizaje(
			CompetenciaBO competenciaBO) throws CrearCompetenciaException {

		List<ResultadoAprendizajeBO> resultadosCompetencia = obtenerResultadosAprendizajeCompetencia(competenciaBO);
		List<ResultadoAprendizajeBO> nuevosResultados = competenciaBO.getResultadosAprendizaje();

		for (ResultadoAprendizajeBO resultadoAprendizajeBO : nuevosResultados) {
			if(!resultadosCompetencia.contains(resultadoAprendizajeBO))
			{
				ResultadoAprendizaje ResultadoAprendizaje = new ResultadoAprendizaje();
				ResultadoAprendizajePK resultadoAprendizajePK = new ResultadoAprendizajePK();
				resultadoAprendizajePK.setPrograma(resultadoAprendizajeBO.getPrograma().getCodigo());
				resultadoAprendizajePK.setTema(resultadoAprendizajeBO.getTema().getId());
				ResultadoAprendizaje.setId(resultadoAprendizajePK);
				Competencia competencia = entityManager.find(Competencia.class, competenciaBO.getId());
				ResultadoAprendizaje.setCompetencia(competencia);
				entityManager.persist(ResultadoAprendizaje);
			}
		} return entityManager.find(Competencia.class, competenciaBO.getId()).toBO();
	}

	@Override
	public List<ResultadoAprendizajeBO> obtenerResultadosAprendizajeCompetencia(
			CompetenciaBO competenciaBO) {

		TypedQuery<ResultadoAprendizaje> query = entityManager.createNamedQuery("obtenerResultadosAprendizajeCompetencia", ResultadoAprendizaje.class);
		query.setParameter("competencia", competenciaBO.getId());
		List<ResultadoAprendizaje> resultados = query.getResultList();
		List<ResultadoAprendizajeBO> retorno = new ArrayList<ResultadoAprendizajeBO>(resultados.size());
		for (ResultadoAprendizaje resultadoAprendizaje : resultados) {
			retorno.add(resultadoAprendizaje.toBO());
		}
		return retorno;
	}

	@Override
	public List<ResultadoAprendizajeBO> obtenerResultadosAprendizajePrograma(EvaluacionBO evaluacionBO) {
		TypedQuery<ResultadoAprendizaje> query = entityManager.createNamedQuery("obtenerResultadosAprendizajePrograma", ResultadoAprendizaje.class);
		query.setParameter("programa",""+evaluacionBO.getPrograma().getCodigo());
		List<ResultadoAprendizaje> resultados = query.getResultList();
		List<ResultadoAprendizajeBO> retorno = new ArrayList<ResultadoAprendizajeBO>( );
		
		for (ResultadoAprendizaje rActual : resultados ) 
		{
			retorno.add( rActual.toBO() );
		}
		return retorno;
	}
	
	
	@Override
	public List<CompetenciaBO> obtenerCompetenciasPrograma(EvaluacionBO evaluacionBO){
		TypedQuery<Competencia> query = entityManager.createNamedQuery("obtenerCompetenciasPrograma", Competencia.class);
		System.out.println("Parametro:"+evaluacionBO.getPrograma().getCodigo());
		query.setParameter("codigo",evaluacionBO.getPrograma().getCodigo());
		List<Competencia> resultados = query.getResultList();
		List<CompetenciaBO> retorno = new ArrayList<CompetenciaBO>( );
		
		for (Competencia rActual : resultados ) 
		{
			CompetenciaBO competencia=rActual.toBO();
			List<ResultadoAprendizajeBO> resul=obtenerResultadosAprendizajeCompetencia(competencia);
			competencia.setResultadosAprendizaje(resul);
			retorno.add(competencia);
			
		}
		return retorno;		
	}
	
	/**
	 * Metodo que se utiliza para poder settear el entityManager para realizar pruebas funcionales.
	 * @param ent, el mock del entityManager a utilizar.
	 */
	public void setEntityManager(EntityManager ent) {
		entityManager = ent;
	}
	
}