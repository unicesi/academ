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

package co.edu.icesi.academ.client.perfiles.propietario;

import java.util.ArrayList;
import java.util.List;

import co.edu.icesi.academ.bo.CalificacionRolBO;
import co.edu.icesi.academ.bo.CompetenciaBO;
import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.FactorDeImpactoBO;
import co.edu.icesi.academ.bo.ResultadoAprendizajeBO;
import co.edu.icesi.academ.bo.RolBO;
import co.edu.icesi.academ.bo.RubricaBO;
import co.edu.icesi.academ.bo.TemaBO;
import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.client.Controlador;
import co.edu.icesi.academ.excepciones.CrearCompetenciaException;
import co.edu.icesi.academ.excepciones.EvaluacionException;
import co.edu.icesi.academ.server.CompetenciaServices;
import co.edu.icesi.academ.server.EvaluacionServices;

public class ControladorPropietario extends Controlador {

	private static ControladorPropietario controladorPropietario;
	private EvaluacionServices evaluacionServices;
	private CompetenciaServices competenciaServices;
	
	private ControladorPropietario() {
		evaluacionServices = new EvaluacionServices();
		competenciaServices = new CompetenciaServices();
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
	
	/**
	 * refresca el panel consolidado para mostrar los promedios por rol y global
	 */
	public void cargarConsolidado() 
	{
		EvaluacionBO evaluacionBO = (EvaluacionBO)getAcademUI().getHTTPSession().getAttribute("evaluacion");
				
		List<TemaBO> temas = evaluacionServices.obtenerTemas();
		List<RolBO> roles = evaluacionBO.getRoles();
		List<List<CalificacionRolBO>> calificaciones = new ArrayList<List<CalificacionRolBO>>();
		for (RolBO rolBO : roles) 
		{
			List<CalificacionRolBO> calificaionesRol = evaluacionServices.obtenerPromedioPorRol(evaluacionBO,rolBO);
			calificaciones.add(calificaionesRol);
		}
		
		List<Integer> promediosG = evaluacionServices.obtenerPromedioGlobal(evaluacionBO);
		
		getAcademUI().getConsolidadoEvaluacion().cargarColumnasPorRol(roles);
		getAcademUI().getConsolidadoEvaluacion().cargarTemas(temas,calificaciones, promediosG);
	}
	
	public void cargarRolesEvaluacion(){
		EvaluacionBO evaluacionBO = (EvaluacionBO) getAcademUI().getHTTPSession().getAttribute("evaluacion");
		UsuarioBO usuarioBO = (UsuarioBO) getAcademUI().getHTTPSession().getAttribute("usuario");
		
		List<RolBO> roles = evaluacionServices.obtenerRolesEvaluacion(evaluacionBO);
		List<TemaBO> temas = evaluacionServices.obtenerTemas();
		List<FactorDeImpactoBO> factoresDeImpacto = evaluacionServices.obtenerFactoresDeImpacto(evaluacionBO, usuarioBO);
		getAcademUI().getFactoresDeImpacto().cargarRoles(roles, temas, factoresDeImpacto, evaluacionBO);
	}
	
	public void cargarColumnasRoles()
	{
		EvaluacionBO evaluacionBO = (EvaluacionBO)getAcademUI().getHTTPSession().getAttribute("evaluacion");
		List<RolBO> roles = evaluacionBO.getRoles();
		getAcademUI().getConsolidadoEvaluacion().cargarColumnasPorRol(roles);
	}
	
	public void cargarFactoresImpacto(){
		EvaluacionBO evaluacionBO = (EvaluacionBO) getAcademUI().getHTTPSession().getAttribute("evaluacion");
		UsuarioBO usuarioBO = (UsuarioBO) getAcademUI().getHTTPSession().getAttribute("usuario");
		
		List<RolBO> roles = evaluacionServices.obtenerRolesEvaluacion(evaluacionBO);
		List<TemaBO> temas = evaluacionServices.obtenerTemas();
		List<FactorDeImpactoBO> factoresDeImpacto = evaluacionServices.obtenerFactoresDeImpacto(evaluacionBO, usuarioBO);
		getAcademUI().getFactoresDeImpacto().cargarRoles(roles, temas, factoresDeImpacto, evaluacionBO);
	}
	
	public void cargarRolesEvaluacion(EvaluacionBO evaluacionBO){
		getAcademUI().getPanelAsignarRol().actualizar( evaluacionBO.getRoles() );
	}
	
	public void cargarRubricas(EvaluacionBO evaluacionBO ){
		List<ResultadoAprendizajeBO> resultados = evaluacionServices.obtenerResultadosAprendizaje( evaluacionBO.getPrograma() );
		if( !resultados.isEmpty() )
			getAcademUI().getPanelRubricas().actualizar(resultados);
	}
	
	public void mostrarPanelCrearCompetencia() 
	{
		//TODO
		CrearCompetencia ventana = new CrearCompetencia();
		EvaluacionBO evaluacionBO = (EvaluacionBO) getAcademUI().getHTTPSession().getAttribute("evaluacion");
		List<ResultadoAprendizajeBO> resultados = competenciaServices.obtenerResultadosAprendizajePrograma(evaluacionBO);
		ventana.cargarResultadosAprendizajePrograma(resultados);
		getAcademUI().mostrarVentanaEmergente(ventana, "Crear Competencia");
	}
	
	public void guardarFactoresDeImpacto(List<FactorDeImpactoBO> factoresDeImpacto){
		evaluacionServices.guardarFactoresDeImpacto(factoresDeImpacto);
		getAcademUI().mostrarNotificacion("Factores de Impacto", "Guardados correctamente!");
	}

	public void guardarResultadosAprendizaje(List<ResultadoAprendizajeBO> resultados){
		EvaluacionBO evaluacionBO = (EvaluacionBO) getAcademUI().getHTTPSession().getAttribute("evaluacion");
		evaluacionServices.guardarResultadosAprendizaes(resultados, evaluacionBO.getPrograma());
		getAcademUI().mostrarNotificacion("Resultados de Aprendizajes", "Guardados correctamente!");
	}
	
	/**
	 * Guarda los roles asignados a los evaluadores de la evaluacion actual.
	 * Muestra un mensaje informando si se pudo guardar o no la informacion.
	 */
	public void guardarRolesAsignados( EvaluacionBO evaluacionBO )
	{		
		try {
			evaluacionServices.guardarRolesAsignados(evaluacionBO);
			getAcademUI().mostrarNotificacion("Roles", "Guardados correctamente!");
		} catch (EvaluacionException e) {
			getAcademUI().mostrarNotificacion("Roles", "Error al guardar!\n\n" + e.getMessage());
		}
	}
	
	/**
	 * Guarda la rubrica de un resulta de aprendizaje
	 * @param rubricaBO la rubrica a guardar
	 */
	public void guardarRubrica( RubricaBO rubricaBO )
	{
		try {
			evaluacionServices.guardarRubrica(rubricaBO);
			getAcademUI().mostrarNotificacion("Rubrica", "Guardada correctamente!");
		} catch (EvaluacionException e) {
			getAcademUI().mostrarNotificacion("Rubrica", "Error al guardar!\n\n" + e.getMessage());
		}
	}
	
	/**
	 * Optiene la rubrica de un resultado de aprendizaje
	 */
	public RubricaBO obtenerRubrica( ResultadoAprendizajeBO resultadoBO ) throws EvaluacionException
	{
		return evaluacionServices.obtenerRubrica(resultadoBO);
	}

	public void crearCompetencia(CompetenciaBO competenciaBO) {
		// TODO Auto-generated method stub
		try {
			System.err.println("entro al controlador");
			EvaluacionBO evaluacionBO = (EvaluacionBO)getAcademUI().getHTTPSession().getAttribute("evaluacion");
			competenciaBO.setPrograma(evaluacionBO.getPrograma());
			competenciaServices.crearCompetencia(competenciaBO);
		} catch (CrearCompetenciaException e) {
			// TODO Auto-generated catch block
			System.err.println("error creando la competencia");
			System.err.println(e.getMessage());
		}
	}
	
	public void cargarCompentencias(){
		EvaluacionBO evaluacionBO = (EvaluacionBO) getAcademUI().getHTTPSession().getAttribute("evaluacion");
		List<CompetenciaBO> competencias=competenciaServices.obtenerCompetenciasPrograma(evaluacionBO);
		getAcademUI().getPanelCompetencias().refescarCompetencias(competencias);
	}
	
	public void mostrarPanelCrearCompetencia(CompetenciaBO value) 
	{
		CrearCompetencia ventana = new CrearCompetencia();
		EvaluacionBO evaluacionBO = (EvaluacionBO) getAcademUI().getHTTPSession().getAttribute("evaluacion");
		List<ResultadoAprendizajeBO> resultados = competenciaServices.obtenerResultadosAprendizajePrograma(evaluacionBO);
		ventana.cargarResultadosAprendizajePrograma(resultados);
		ventana.cargarResultadosAprendizajeAsociados( value.getResultadosAprendizaje() );
		ventana.setDescripcion( value.getDescripcion() );
		getAcademUI().mostrarVentanaEmergente(ventana, "Editar Competencia");
	
	}
	
}
