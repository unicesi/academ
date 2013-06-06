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

import java.util.List;

import javax.ejb.Remote;

import co.edu.icesi.academ.bo.CompetenciaBO;
import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.ResultadoAprendizajeBO;
import co.edu.icesi.academ.excepciones.CrearCompetenciaException;

@Remote
public interface CompetenciasBeanRemote {

	/**
	 * guarda una competencia en la base de datos<br>
	 * <b>pre:</b> el bean ya ha sido inicializado
	 * @param competenciaBO - debe ser != null
	 * @return retorna el BO de la competencia persistida
	 * @exception si ocurre algun error se lanza una  avisando que no se pudo crear la competencia y porque
	 */
	
	public CompetenciaBO crearCompetencia(CompetenciaBO competenciaBO) throws CrearCompetenciaException;
	
	/**
	 * edita una compentecia con la informacion de la competenciaBO que se pasa por parametro<br>
	 * <b>pre:</b> el bean ya ha sido inicializado
	 * @param compentenciaBO - debe ser !=null y ya se encuentra en la base de datos la compentencia correspondiente a este BO
	 * @return retorna el BO de la competencia ya actualizada
	 * @throws CrearCompetenciaException lanza la excepcion avisando que no se pudo actualizar la competencia y porque 
	 */
	public CompetenciaBO editarCompetencia(CompetenciaBO compentenciaBO)throws CrearCompetenciaException;
	
	/**
	 * guarda los resultados de aprendizaje de asociados a una competencia<br>
	 * <b>pre:</b> el bean ya ha sido inicializado
	 * @param competenciaBO la competencia con los resultados de aprendizaje a guardar
	 * @return retorna la competencia con los resultados de aprendizaje ya guardados
	 * @throws CrearCompetenciaException si ocurre algun error se lanza una  avisando que no se pudo crear la competencia y porque
	 */
	public CompetenciaBO guardarResultadosAprendizaje(CompetenciaBO competenciaBO)throws CrearCompetenciaException;
	
	/**
	 *  retorna una lista con todos los resultados de aprendizaje que tiene una competencia<br>
	 *  <b>pre:</b> el bean ya ha sido inicializado
	 * @param competenciaBO - debe ser !=null y ya se encuentra en la base de datos la compentencia correspondiente a este BO
	 * @return si no encuentra la competencia de la que se buscan los resultados retorna null
	 */
	public List<ResultadoAprendizajeBO> obtenerResultadosAprendizajeCompetencia(CompetenciaBO competenciaBO);

	/**
	 * retorna los resultados de Aprendizaje que estan disponibles en el momento<br>
	 * <b>pre:</b> el bean ha sido inicializado
	 * @param evaluacionBO de la que se sacara el programa
	 * @return si no hay resultados de parendizaje disponibles retorna una lista vacia
	 */
	public List<ResultadoAprendizajeBO> obtenerResultadosAprendizajePrograma(EvaluacionBO evaluacionBO);
	
	/**
	 * retorna las competencias de un programa que estan disponibles<br>
	 * <b>pre:</b> el bean ha sido inicializado
	 * @param evaluacionBO de la que se sacara el programa
	 * @return si no hay resultados de parendizaje disponibles retorna una lista vacia
	 */
	public List<CompetenciaBO> obtenerCompetenciasPrograma(EvaluacionBO evaluacionBO);

}
