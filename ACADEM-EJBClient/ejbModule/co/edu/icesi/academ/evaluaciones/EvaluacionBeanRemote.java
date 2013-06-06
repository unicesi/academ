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

import java.util.List;

import javax.ejb.Remote;

import co.edu.icesi.academ.bo.CalificacionRolBO;
import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.CalificacionBO;
import co.edu.icesi.academ.bo.FactorDeImpactoBO;
import co.edu.icesi.academ.bo.NivelDeConocimientoBO;
import co.edu.icesi.academ.bo.ProgramaBO;
import co.edu.icesi.academ.bo.ResultadoAprendizajeBO;
import co.edu.icesi.academ.bo.RolBO;
import co.edu.icesi.academ.bo.RubricaBO;
import co.edu.icesi.academ.bo.TemaBO;
import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.excepciones.CrearEvaluacionException;
import co.edu.icesi.academ.excepciones.EditarEvaluacionException;
import co.edu.icesi.academ.excepciones.EvaluacionException;

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
	
	public List<RolBO> obtenerRolesEvaluacion(EvaluacionBO evaluacionBO);
	
	/**
	* <b>pre:</b>El orden de lista de calificaciones de un evaluador a otro no varia.
	* Este método permite calcular el nivel de conocimiento promedio por rol en cada uno de los temas de la evaluacion
	* @param evaluacionBO !=null.La evaluacion de la cual se desea obtener la informacion.
	* @param rolBO !=null. El rol del cual se quiere saber los promedios.
	* @return Una lista de objetos de la clase CalificacionRolBO donde se encuentra la informacion requerida.
	* <b>post:</b> Se ha creado una nueva lista con la calificacion promedio por rol en cada tema. 
	*/	
	public List<CalificacionRolBO>obtenerPromedioPorRol(EvaluacionBO evaluacionBO,RolBO rolBO);
	
	public List<FactorDeImpactoBO> obtenerFactoresDeImpacto(EvaluacionBO evaluacionBO, UsuarioBO usuarioBO);
	
	public void guardarFactoresDeImpacto(List<FactorDeImpactoBO> factoresDeImpacto);

	public List<FactorDeImpactoBO> obtenerFactoresDeImpactoRol(EvaluacionBO evaluacionBO, RolBO rolBO);

	/**
	 * Este metodo calcula el promedio ponderado segun el promedio que arroja las calificaciones de cada rol asignado a la evaluacionBO
	 * @param evaluacionBO - evaluacion para promediar
	 * @return listado con los promedios
	 */
	public List<Integer> obtenerPromedioGlobal(EvaluacionBO evaluacionBO);

	public List<CalificacionBO> obtenerCalificacionesEvaluacion(EvaluacionBO evaluacionBO);
	
	/**
	 * Crea una rubrica a un resultado de aprendizaje
	 * Si el resultado de aprendizaje ya tiene una rubrica, esta es reemplazada por la nueva rubrica
	 * @param resultadoDeAprendizajeBO El resultado de aprendizaje al cual se le va a crear la rubrica
	 * @param rubricaBO La rubrica que se desea crear.
	 * @return El objeto BO de la rubrica que se ha creado
	 * @throws EvaluacionException  - Si rubricaBO es nulo
	 * 								- Si el resultado de aprendizaje no existe
	 */
	public RubricaBO guardarRubrica( RubricaBO rubricaBO ) throws EvaluacionException;
	
	/**
	 * Recupera la rubrica de un resultado de aprendizaje 
	 * @param resultadoDeAprendizajeBO
	 * @return La rubrica del resultado de aprendizaje.
	 * @throws EvaluacionException Si el resultado de aprendizaje no existe
	 */
	public RubricaBO obtenerRubrica( ResultadoAprendizajeBO resultadoBO ) throws EvaluacionException;
	
	/**
	 * Guarda los roles que tiene un usario en la su lista de roles y elimina los que no se encuentran en dicha lista
	 * @param usuarioBO El usuario al que se le van a guardar los roles
	 * @return El usuario con los roles guardados
	 * @throws EvaluacionException - Si el parametro es nulo
	 * 							   - Si algun rol en la lista de roles no existe.
	 */
	public UsuarioBO guardarRoles( UsuarioBO usuarioBO )throws EvaluacionException;

	public void guardarResultadosAprendizajes(List<ResultadoAprendizajeBO> ListaResultadosAprendizajeBO, ProgramaBO programaBO);
	
	/**
	 * Obtiene los resultados de aprendizaje asosciados a un programa
	 * @param programaBO El programa del que se quieren los resultados
	 * @return La lista de resultados de aprendizaje del programa.
	 */
	public List<ResultadoAprendizajeBO> obtenerResultadosAprendizaje( ProgramaBO programaBO );
}
