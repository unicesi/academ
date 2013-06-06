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

import co.edu.icesi.academ.bo.CompetenciaBO;
import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.ResultadoAprendizajeBO;
import co.edu.icesi.academ.competencias.CompetenciasBeanRemote;
import co.edu.icesi.academ.excepciones.CrearCompetenciaException;

public class CompetenciaServices {

	private InitialContext context;
	private CompetenciasBeanRemote competenciasBean;
	
	public CompetenciaServices() {
	
		doLookup(); 
	}

	private void doLookup() {
	
		try {
			context = new InitialContext();
			competenciasBean = (CompetenciasBeanRemote)context.lookup("java:global/ACADEM-EAR/ACADEM-EJB/CompetenciaBean!co.edu.icesi.academ.competencias.CompetenciasBeanRemote");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			System.err.println("error en el lookUp");
			e.printStackTrace();
		}
	}
	
	public CompetenciaBO crearCompetencia(CompetenciaBO competenciaBO) throws CrearCompetenciaException
	{
		System.err.println("entro al competenciaServices");
		CompetenciaBO competencia = competenciasBean.crearCompetencia(competenciaBO);
		System.err.println("salio del bean");
		return competencia;
	}
	
	public CompetenciaBO actualizarCompetencia(CompetenciaBO competenciaBO) throws CrearCompetenciaException
	{
		return competenciasBean.editarCompetencia(competenciaBO);
	}
	
	public CompetenciaBO guardarResultadosAprendizaje(CompetenciaBO competenciaBO) throws CrearCompetenciaException
	{
		return competenciasBean.guardarResultadosAprendizaje(competenciaBO);
	}
	
	public List<ResultadoAprendizajeBO> obtenerResultadosAprendizajePrograma(EvaluacionBO evaluacionBO)
	{
		return competenciasBean.obtenerResultadosAprendizajePrograma(evaluacionBO);
	}
	
	public List<CompetenciaBO> obtenerCompetenciasPrograma(EvaluacionBO evaluacionBO)
	{
		return competenciasBean.obtenerCompetenciasPrograma(evaluacionBO);
	}
}
