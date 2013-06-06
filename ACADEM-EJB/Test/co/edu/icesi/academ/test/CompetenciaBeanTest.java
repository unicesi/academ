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

package co.edu.icesi.academ.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import co.edu.icesi.academ.bo.CompetenciaBO;
import co.edu.icesi.academ.bo.ResultadoAprendizajeBO;
import co.edu.icesi.academ.competencias.CompetenciaBean;
import co.edu.icesi.academ.entities.Competencia;
import co.edu.icesi.academ.entities.NivelDeConocimiento;
import co.edu.icesi.academ.entities.Programa;
import co.edu.icesi.academ.entities.ResultadoAprendizaje;
import co.edu.icesi.academ.entities.ResultadoAprendizajePK;
import co.edu.icesi.academ.entities.Tema;
import co.edu.icesi.academ.excepciones.CrearCompetenciaException;

public class CompetenciaBeanTest {

	private static CompetenciaBean bean;
	private static EntityManager ent;
	private static TypedQuery<ResultadoAprendizaje> query;
	
	/** 
	 * 	Metodo en el que se inyecta al entityManager con el mockito
	 */
	@BeforeClass
	public static void injectMockEntityManager() {  
		ent = mock(EntityManager.class);
		query = mock(TypedQuery.class);
		when(ent.createNamedQuery("obtenerResultadosAprendizajeCompetencia", ResultadoAprendizaje.class)).thenReturn(query);
	}

	/**
	 * 	Metodo que se ejecuta al finalizar la ejecucion de la clase, cuando se han corrido todas las pruebas.
	 *  Se encarga de cerrar el entityManager.
	 */
	@AfterClass
	public static void tearDownAfterClass(){
		ent.close();
	}

	/** Metodo que se ejecuta antes de cada prueba. Se encarga de crear el, y settear el entity Manager al, 
	 * 	bean EvaluacionBean.
	 */
	@Before	
	public void setUp(){
		bean = new CompetenciaBean();
		bean.setEntityManager(ent);
	}
	
	/**
	 * 
	 */
	@Test
	public void CrearCompetenciaCorrectamente(){
		//////
		//
		//DEFINICION DEL CONTEXTO
		//
		//////
		
		//CREACION PROGRAMA
		Programa pr = new Programa();
		pr.setCodigo(3);
		pr.setNombre("Ingenieria de Sistemas");
		pr.setDescripcion("Ingenieria de Sistemas");
		
		//CREACION DE LA COMPETENCIA
		Competencia c = new Competencia();
		c.setId(0);
		c.setDescripcion("Descripcion A");
		c.setPrograma(pr);
		c.setResultadosAprendizaje(new ArrayList<ResultadoAprendizaje>());
		
		//OBJETO BO
		CompetenciaBO cBO = c.toBO();
		cBO.setResultadosAprendizaje(new ArrayList<ResultadoAprendizajeBO>());
		
		
		//////
		//
		//RELIZACION DE LA PRUEBA
		//
		//////
		when(ent.find(Programa.class, cBO.getPrograma().getCodigo())).thenReturn(pr);
//		when(query.getResultList()).thenReturn(new ArrayList<ResultadoAprendizaje>());
//		when(ent.find(Competencia.class, cBO.getId())).thenReturn(c);
		
		try {
			CompetenciaBO c2BO = bean.crearCompetencia(cBO);
			
			
		//////
		//
		//EJECUCION DE LA ASSERTION
		//
		//////
		assertEquals(cBO, c2BO);
		
		
		} catch (CrearCompetenciaException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 */
	@Test
	public void EditarCompetenciaCorrectamente(){
		//////
		//
		//DEFINICION DEL CONTEXTO
		//
		//////
		
		//CREACION DE NIVELES DE CONOCIMIENTO
		NivelDeConocimiento n0 = new NivelDeConocimiento();
		n0.setId(0);
		n0.setDescripcion("No tener experiencia o no haber sido expuesto a");
		
		NivelDeConocimiento n1 = new NivelDeConocimiento();
		n1.setId(1);
		n1.setDescripcion("Tener experiencia o haber sido expuesto a");
		
		NivelDeConocimiento n2 = new NivelDeConocimiento();
		n2.setId(2);
		n2.setDescripcion("Ser capaz de participar en y contribuir");
		
		NivelDeConocimiento n3 = new NivelDeConocimiento();
		n3.setId(3);
		n3.setDescripcion("Ser capaz de entender y explicar");
		
		//CREACION PROGRAMA
		Programa pr = new Programa();
		pr.setCodigo(3);
		pr.setNombre("Ingenieria de Sistemas");
		pr.setDescripcion("Ingenieria de Sistemas");
		
		//CREACION DEL TEMA
		Tema t211 = new Tema();
		t211.setId("2.1.1");
		t211.setNombre("Identificacion y Formulacion de Problemas");
		
		Tema t212 = new Tema();
		t212.setId("2.1.2");
		t212.setNombre("Modelado");
		
		//CREACION RESULTADOS DE APRENDIZAJE
		ResultadoAprendizaje r1 = new ResultadoAprendizaje();
		r1.setPrograma(pr);
		r1.setTema(t211);
		r1.setNivelDeConocimiento(n0);
		
		ResultadoAprendizaje r2 = new ResultadoAprendizaje();
		r2.setPrograma(pr);
		r2.setTema(t211);
		r2.setNivelDeConocimiento(n1);
		
		ResultadoAprendizaje r3 = new ResultadoAprendizaje();
		r3.setPrograma(pr);
		r3.setTema(t211);
		r3.setNivelDeConocimiento(n2);
		
		ResultadoAprendizaje r4 = new ResultadoAprendizaje();
		r4.setPrograma(pr);
		r4.setTema(t212);
		r4.setNivelDeConocimiento(n3);
		
		//CREACION DE LA COMPETENCIA
		Competencia cOriginal = new Competencia();
		cOriginal.setId(0);
		cOriginal.setDescripcion("Descripcion A");
		cOriginal.setPrograma(pr);
		List<ResultadoAprendizaje> lista =  Arrays.asList(r1, r2);
		cOriginal.setResultadosAprendizaje(lista);
		
		//OBJETO BO
		CompetenciaBO cOriginalBO = cOriginal.toBO();
		
		
		//////
		//
		//RELIZACION DE LA PRUEBA
		//
		//////
		
		//CREACION DE LA COMPETENCIA A MODIFICAR
		Competencia cMod = new Competencia();
		cMod.setId(0);
		cMod.setDescripcion("Descripcion A");
		cMod.setPrograma(pr);
		List<ResultadoAprendizaje> listaM = Arrays.asList(r3, r4);
		cMod.setResultadosAprendizaje(listaM);
		
		//OBJETO BO
		CompetenciaBO cModBO = cMod.toBO();
		
		//CREACION PKs DE LOS RESULTADOS DE APRENDIZAJE
		ResultadoAprendizajePK pk1 = new ResultadoAprendizajePK();
		pk1.setPrograma(3);
		pk1.setTema("2.1.1");
		
		ResultadoAprendizajePK pk2 = new ResultadoAprendizajePK();
		pk2.setPrograma(3);
		pk2.setTema("2.1.2");
		
		//AQUI SETTEAMOS LOS RESULTADO A RETORNAR CUANDO SE USE UN METODO CONCRETO DEL ENTITY MANAGER
		when(ent.find(Programa.class, cOriginalBO.getPrograma().getCodigo())).thenReturn(pr);
		when(ent.find(Competencia.class, cMod.getId())).thenReturn(cOriginal);
		when(ent.find(ResultadoAprendizaje.class, pk1)).thenReturn(r3);
		when(ent.find(ResultadoAprendizaje.class, pk2)).thenReturn(r4);

		
		//////
		//
		//RELIZACION DE LA PRUEBA
		//
		//////
		try {
			
		//CREACION DE LA COMPETENCIA ESPERADA
		Competencia cEsperada = new Competencia();
		cEsperada.setId(0);
		cEsperada.setDescripcion("Descripcion A");
		cEsperada.setPrograma(pr);
		List<ResultadoAprendizaje> listaEsperada = Arrays.asList(r1, r2, r3, r4);
		cEsperada.setResultadosAprendizaje(listaEsperada);
		
		//////
		//
		//EJECUCION DE LA ASSERTION
		//
		//////
		
		//OBJETO BO
		CompetenciaBO resultadoEsperado = cEsperada.toBO();
		resultadoEsperado.setResultadosAprendizaje(new ArrayList<ResultadoAprendizajeBO>());
		
		//LLAMADA AL METODO A PROBAR
		CompetenciaBO resultadoReal = bean.editarCompetencia(cModBO);
		resultadoReal.setResultadosAprendizaje(new ArrayList<ResultadoAprendizajeBO>());
			
		//OBTECION ARREGLOS A COMPARAR		
		ResultadoAprendizajeBO[] resultadosEsperados = (resultadoEsperado.getResultadosAprendizaje()).
				toArray(new ResultadoAprendizajeBO[resultadoEsperado.getResultadosAprendizaje().size()]);

		ResultadoAprendizajeBO[] resultadosReales = (resultadoReal.getResultadosAprendizaje()).
				toArray(new ResultadoAprendizajeBO[resultadoReal.getResultadosAprendizaje().size()]);
		
		
		assertEquals(resultadoEsperado, resultadoReal);
		assertArrayEquals(resultadosEsperados, resultadosReales);
		
		} catch (CrearCompetenciaException e) {
			e.printStackTrace();
		}
		
	}

}
