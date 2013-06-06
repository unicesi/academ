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

package co.edu.icesi.academ.server;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

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
import co.edu.icesi.academ.evaluaciones.EvaluacionBeanRemote;
import co.edu.icesi.academ.excepciones.CrearEvaluacionException;
import co.edu.icesi.academ.excepciones.EditarEvaluacionException;
import co.edu.icesi.academ.excepciones.EvaluacionException;

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
	
	public List<RolBO> obtenerRolesEvaluacion(EvaluacionBO evaluacionBO){
		return evaluacionBean.obtenerRolesEvaluacion(evaluacionBO);
	}
	
	/**
	 * Guarda los roles que tienen los evaluadores en evalucionBO y elimina los que no esten
	 * <b>pre:</b> La lista de evaluadores no es nula y no tiene objetos nulos.
	 * 			   La lista de roles de cada evaluador no es nula y no tiene objetos nulos.
	 * 			   Los roles de cada evaluador pertenecen a evaluacion.
	 * @param evaluacionBO la evalucion a la que se le guardaran la asigancion de roles de los evaluadores
	 * @throws EvaluacionException Si se incumplen los prerrequisitos
	 */
	public void guardarRolesAsignados( EvaluacionBO evaluacionBO ) throws EvaluacionException{
		
		for( UsuarioBO evaluador : evaluacionBO.getEvaluadores( ) )
			evaluacionBean.guardarRoles(evaluador);
	}
	
	public List<FactorDeImpactoBO> obtenerFactoresDeImpacto(EvaluacionBO evaluacionBO, UsuarioBO usuarioBO){
		return evaluacionBean.obtenerFactoresDeImpacto(evaluacionBO, usuarioBO);
	}
	
	/**
	* <b>pre:</b>El orden de lista de calificaciones de un evaluador a otro no varia.
	* Este método permite calcular el nivel de conocimiento promedio por rol en cada uno de los temas de la evaluacion
	* @param evaluacionBO !=null.La evaluacion de la cual se desea obtener la informacion.
	* @param rolBO !=null. El rol del cual se quiere saber los promedios.
	* @return Una lista de objetos de la clase CalificacionRolBO donde se encuentra la informacion requerida.
	* <b>post:</b> Se ha creado una nueva lista con la calificacion promedio por rol en cada tema. 
	*/	
	public List<CalificacionRolBO> obtenerPromedioPorRol(EvaluacionBO evaluacionBO, RolBO rolBO)
	{
		return evaluacionBean.obtenerPromedioPorRol(evaluacionBO, rolBO);
	}
	
	public void guardarFactoresDeImpacto(List<FactorDeImpactoBO> factoresDeImpacto){
		evaluacionBean.guardarFactoresDeImpacto(factoresDeImpacto);
	}
	
	/**
	 * Este metodo calcula el promedio ponderado segun el promedio que arroja las calificaciones de cada rol asignado a la evaluacionBO
	 * @param evaluacionBO - evaluacion para promediar
	 * @return listado con los promedios
	 */
	public List<Integer> obtenerPromedioGlobal(EvaluacionBO evaluacionBO)
	{
		return evaluacionBean.obtenerPromedioGlobal(evaluacionBO);
	}
	
	public void guardarResultadosAprendizaes(List<ResultadoAprendizajeBO> ListaResultadosAprendizajeBO, ProgramaBO programaBO){
		evaluacionBean.guardarResultadosAprendizajes(ListaResultadosAprendizajeBO, programaBO);
	}
	
	public RubricaBO obtenerRubrica( ResultadoAprendizajeBO resultadoBO ) throws EvaluacionException
	{
		return evaluacionBean.obtenerRubrica(resultadoBO);
	}
	
	/**
	 * Guarda la rubrica de un resulta de aprendizaje
	 * @param rubricaBO la rubrica a guardar
	 */
	public void guardarRubrica( RubricaBO rubricaBO ) throws EvaluacionException
	{
		evaluacionBean.guardarRubrica(rubricaBO);
	}
	
	/**
	 * Obtiene los resultados de aprendizaje asosciados a un programa
	 * @param programaBO El programa del que se quieren los resultados
	 * @return La lista de resultados de aprendizaje del programa.
	 */
	public List<ResultadoAprendizajeBO> obtenerResultadosAprendizaje( ProgramaBO programaBO ){
		return evaluacionBean.obtenerResultadosAprendizaje(programaBO);
	}
}
