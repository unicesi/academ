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


import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import co.edu.icesi.academ.bo.CalificacionBO;
import co.edu.icesi.academ.bo.CalificacionRolBO;
import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.entities.Calificacion;
import co.edu.icesi.academ.entities.Evaluacion;
import co.edu.icesi.academ.entities.FactorDeImpacto;
import co.edu.icesi.academ.entities.FactorDeImpactoPK;
import co.edu.icesi.academ.entities.NivelDeConocimiento;
import co.edu.icesi.academ.entities.Perfil;
import co.edu.icesi.academ.entities.Programa;
import co.edu.icesi.academ.entities.Rol;
import co.edu.icesi.academ.entities.RolPK;
import co.edu.icesi.academ.entities.Tema;
import co.edu.icesi.academ.entities.Usuario;
import co.edu.icesi.academ.entities.Usuario_Evaluacion;
import co.edu.icesi.academ.entities.Usuario_EvaluacionPK;
import co.edu.icesi.academ.evaluaciones.EvaluacionBean;

/**
 * 	Clase de pruebas unitarias -- bean EvaluacionBean.
 * @author Romeo
 */
public class EvaluacionBeanTest {

	/**
	 * Atributos de la clase
	 */
	private static EntityManager ent;
	private static EvaluacionBean bean;
	private static TypedQuery<Calificacion> query;
	private static TypedQuery<FactorDeImpacto> query2;
	
	/** 
	 * 	Metodo en el que se inyecta al entityManager con el mockito
	 */
	@BeforeClass
	public static void injectMockEntityManager() {  
		ent = mock(EntityManager.class);
		query = mock(TypedQuery.class);
		query2 = mock(TypedQuery.class);
        when(ent.createNamedQuery("obtenerCalificacionesEvaluacion", Calificacion.class)).thenReturn(query);
        when(ent.createNamedQuery("obtenerFactoresDeImpactoRol", FactorDeImpacto.class)).thenReturn(query2);
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
		bean = new EvaluacionBean();
		bean.setEntityManager(ent);
	}

	/**	Metodo de tipo test que se encarga de corroborar que el metodo obtenerPromedioPorRol, retorne el resultado
	 * esperado.
	 */
	@Test
	public void obtenerPromedioPorRolCorrectamente(){
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
		
		//CREACION PERFIL
		Perfil pf = new Perfil();
		pf.setNombre("Propietario");
		Perfil pfEva = new Perfil();
		pfEva.setNombre("Evaluador");
		
		//CREACION USUARIO-PROPIETARIO
		Usuario pro = new Usuario();
		pro.setNombre("nvillega");
		pro.setPerfil(pf);
		pro.setRoles(new ArrayList<Rol>());
		
		//CREACION EVALUACION
		Evaluacion ev = new Evaluacion();
		ev.setId(1);
		ev.setFechaInicial(new GregorianCalendar(2013, 3, 19).getTime());
		ev.setFechaFinal(new GregorianCalendar(2013, 3, 21).getTime());
		ev.setPrograma(pr);
		ev.setPropietario(pro);
		
		//CREACION ROLES
		RolPK r1 = new RolPK();
		r1.setNombre("Docente");
		Rol docente = new Rol();
		docente.setEvaluacion(ev);
		docente.setId(r1);
		
		RolPK r2 = new RolPK();
		r2.setNombre("Egresado");
		Rol egresado = new Rol();
		egresado.setEvaluacion(ev);
		egresado.setId(r2);
		
		RolPK r3 = new RolPK();
		r3.setNombre("Industria");
		Rol industria = new Rol();
		industria.setEvaluacion(ev);
		industria.setId(r3);
		
		//CREACION USUARIOS-EVALUADORES
		Usuario aVil = new Usuario();
		aVil.setNombre("avillota");
		aVil.setPerfil(pfEva);
		aVil.setRoles(Arrays.asList(docente));
		
		Usuario lCas = new Usuario();
		lCas.setNombre("lcastañeda");
		lCas.setPerfil(pfEva);
		lCas.setRoles(Arrays.asList(docente));
		
		Usuario jCam = new Usuario();
		jCam.setNombre("jcampaz");
		jCam.setPerfil(pfEva);
		jCam.setRoles(Arrays.asList(egresado));
		
		Usuario mPiz = new Usuario();
		mPiz.setNombre("mpiza");
		mPiz.setPerfil(pfEva);
		mPiz.setRoles(Arrays.asList(egresado));
		
		Usuario gLon = new Usuario();
		gLon.setNombre("glondono");
		gLon.setPerfil(pfEva);
		gLon.setRoles(Arrays.asList(industria));
		
		Usuario hFar = new Usuario();
		hFar.setNombre("hfarboleda");
		hFar.setPerfil(pfEva);
		hFar.setRoles(Arrays.asList(industria));
		
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
		
		NivelDeConocimiento n4 = new NivelDeConocimiento();
		n4.setId(4);
		n4.setDescripcion("Ser experto en la practica o realizacion de");
		
		//CREACION DE LOS TEMAS
		Tema t211 = new Tema();
		t211.setId("2.1.1");
		t211.setNombre("Identificacion y Formulacion de Problemas");
		
		Tema t212 = new Tema();
		t212.setId("2.1.2");
		t212.setNombre("Modelado");
		
		//CREACION DE CALIFICACIONES
		Calificacion m211 = new Calificacion();
		m211.setEvaluacion(ev);
		Usuario_Evaluacion u11 = new Usuario_Evaluacion();
		Usuario_EvaluacionPK up11 = new Usuario_EvaluacionPK();
		up11.setUsuario("mpiza");
		u11.setId(up11);
		m211.setUsuarioEvaluacion(u11);
		m211.setNivelDeConocimiento(n0);
		m211.setTema(t211);
				
		Calificacion m212 = new Calificacion();
		m212.setEvaluacion(ev);
		Usuario_Evaluacion u12 = new Usuario_Evaluacion();
		u12.setId(up11);
		m212.setUsuarioEvaluacion(u12);
		m212.setNivelDeConocimiento(n4);
		m212.setTema(t212);
		
		Calificacion j211 = new Calificacion();
		j211.setEvaluacion(ev);
		Usuario_Evaluacion u21 = new Usuario_Evaluacion();
		Usuario_EvaluacionPK up21 = new Usuario_EvaluacionPK();
		up21.setUsuario("jcampaz");
		u21.setId(up21);
		j211.setUsuarioEvaluacion(u21);
		j211.setNivelDeConocimiento(n1);
		j211.setTema(t211);
				
		Calificacion j212 = new Calificacion();
		j212.setEvaluacion(ev);
		Usuario_Evaluacion u22 = new Usuario_Evaluacion();
		u22.setId(up21);
		j212.setUsuarioEvaluacion(u22);
		j212.setNivelDeConocimiento(n2);
		j212.setTema(t212);
				
		Calificacion h211 = new Calificacion();
		h211.setEvaluacion(ev);
		Usuario_Evaluacion u31 = new Usuario_Evaluacion();
		Usuario_EvaluacionPK up31 = new Usuario_EvaluacionPK();
		up31.setUsuario("hfarboleda");
		u31.setId(up31);
		h211.setUsuarioEvaluacion(u31);
		h211.setNivelDeConocimiento(n4);
		h211.setTema(t211);
				
		Calificacion h212 = new Calificacion();
		h212.setEvaluacion(ev);
		Usuario_Evaluacion u32 = new Usuario_Evaluacion();
		u32.setId(up31);
		h212.setUsuarioEvaluacion(u32);
		h212.setNivelDeConocimiento(n1);
		h212.setTema(t212);
				
		Calificacion g211 = new Calificacion();
		g211.setEvaluacion(ev);
		Usuario_Evaluacion u41 = new Usuario_Evaluacion();
		Usuario_EvaluacionPK up41 = new Usuario_EvaluacionPK();
		up41.setUsuario("glondono");
		u41.setId(up41);
		g211.setUsuarioEvaluacion(u41);
		g211.setNivelDeConocimiento(n2);
		g211.setTema(t211);
				
		Calificacion g212 = new Calificacion();
		g212.setEvaluacion(ev);
		Usuario_Evaluacion u42 = new Usuario_Evaluacion();
		u42.setId(up41);
		g212.setUsuarioEvaluacion(u42);
		g212.setNivelDeConocimiento(n4);
		g212.setTema(t212);
				
		Calificacion l211 = new Calificacion();
		l211.setEvaluacion(ev);
		Usuario_Evaluacion u51 = new Usuario_Evaluacion();
		Usuario_EvaluacionPK up51 = new Usuario_EvaluacionPK();
		up51.setUsuario("lcastañeda");
		u51.setId(up51);
		l211.setUsuarioEvaluacion(u51);
		l211.setNivelDeConocimiento(n4);
		l211.setTema(t211);
				
		Calificacion l212 = new Calificacion();
		l212.setEvaluacion(ev);
		Usuario_Evaluacion u52 = new Usuario_Evaluacion();
		u52.setId(up51);
		l212.setUsuarioEvaluacion(u52);
		l212.setNivelDeConocimiento(n2);
		l212.setTema(t212);
				
		Calificacion a211 = new Calificacion();
		a211.setEvaluacion(ev);
		Usuario_Evaluacion u61 = new Usuario_Evaluacion();
		Usuario_EvaluacionPK up61 = new Usuario_EvaluacionPK();
		up61.setUsuario("avillota");
		u61.setId(up61);
		a211.setUsuarioEvaluacion(u61);
		a211.setNivelDeConocimiento(n4);
		a211.setTema(t211);
				
		Calificacion a212 = new Calificacion();
		a212.setEvaluacion(ev);
		Usuario_Evaluacion u62 = new Usuario_Evaluacion();
		u62.setId(up61);
		a212.setUsuarioEvaluacion(u62);
		a212.setNivelDeConocimiento(n3);
		a212.setTema(t212);
		
		//ASOCIACION DE LOS ROLES A LA EVALUACION
		List<Rol> roles = Arrays.asList(docente, egresado, industria); 
		ev.setRoles(roles);
		
		//ASOCIACION DE LOS USUARIOS A LA EVALUACION
		List<Usuario> usuarios = Arrays.asList(aVil, lCas, jCam, mPiz, gLon, hFar);
		ev.setUsuarios(usuarios);
		
		//CREACION DE EVALUACIONBO
		EvaluacionBO evBO = ev.toBO();
		
		//ASOCIACION DE LAS CALIFICACIONES A LA EVALUACIONBO
		List<CalificacionBO> calificacionesBO = Arrays.asList(m211.toBO(), m212.toBO(), j211.toBO(), j212.toBO(), h211.toBO(), h212.toBO(), 
				g211.toBO(), g212.toBO(), l211.toBO(), l212.toBO(), a211.toBO(), a212.toBO());
		evBO.setCalificaciones(calificacionesBO);
		
		
		
		//////
		//
		//RELIZACION DE LA PRUEBA
		//
		//////
		
		//AQUI DECIMOS QUE CUANDO SE LLAME EL METODO QUERY.GETRESULTLIST(), NOS RETORNE LAS CALIFICACIONES ALMACENADAS EN LA BD
		List<Calificacion> calificaciones = Arrays.asList(m211, m212, j211, j212, h211, h212, g211, g212, l211, l212, a211, a212);
		when(query.getResultList()).thenReturn(calificaciones);
		
		
		//////
		//
		//EJECUCION DE LAS ASSERTIONS
		//
		//////
		
		//CON PARAMETRO ROL: DOCENTE
		CalificacionRolBO cr1 = new CalificacionRolBO(t211.toBO(), docente.toBO(), n4.toBO());
		CalificacionRolBO cr2 = new CalificacionRolBO(t212.toBO(), docente.toBO(), n3.toBO());
		CalificacionRolBO[] resultadoEsperado = {cr1, cr2}; 
		
		List<CalificacionRolBO> resultadoReal = bean.obtenerPromedioPorRol(evBO, docente.toBO());
		CalificacionRolBO[] arrayValoresReales = resultadoReal.toArray(new CalificacionRolBO[resultadoReal.size()]);
				
		assertArrayEquals(resultadoEsperado, arrayValoresReales);	
		
		//CON PARAMETRO ROL: EGRESADO
		CalificacionRolBO cr3 = new CalificacionRolBO(t211.toBO(), egresado.toBO(), n1.toBO());
		CalificacionRolBO cr4 = new CalificacionRolBO(t212.toBO(), egresado.toBO(), n3.toBO());
		CalificacionRolBO[] resultadoEsperado2 = {cr3, cr4}; 
		
		List<CalificacionRolBO> resulReal2 = bean.obtenerPromedioPorRol(evBO, egresado.toBO());
		CalificacionRolBO[] arrayValoresReales2 = resulReal2.toArray(new CalificacionRolBO[resulReal2.size()]);

		assertArrayEquals(resultadoEsperado2, arrayValoresReales2);
		
		//CON PARAMETRO ROL: INDUSTRIAL
		CalificacionRolBO cr5 = new CalificacionRolBO(t211.toBO(), industria.toBO(), n3.toBO());
		CalificacionRolBO cr6 = new CalificacionRolBO(t212.toBO(), industria.toBO(), n3.toBO());
		CalificacionRolBO[] resultadoEsperado3 = {cr5, cr6}; 
		
		List<CalificacionRolBO> resulReal3 = bean.obtenerPromedioPorRol(evBO, industria.toBO());
		CalificacionRolBO[] arrayValoresReales3 = resulReal3.toArray(new CalificacionRolBO[resulReal3.size()]);
		
		assertArrayEquals(resultadoEsperado3, arrayValoresReales3);
		
	}
	
	/**	Metodo de tipo test que se encarga de corroborar que el metodo obtenerPromedioGlobal, retorne el resultado
	 * esperado.
	 */
	@Test
	public void obtenerPromedioGlobalCorrectamente(){
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
			
			//CREACION PERFIL
			Perfil pf = new Perfil();
			pf.setNombre("Propietario");
			Perfil pfEva = new Perfil();
			pfEva.setNombre("Evaluador");
			
			//CREACION USUARIO-PROPIETARIO
			Usuario pro = new Usuario();
			pro.setNombre("nvillega");
			pro.setPerfil(pf);
			pro.setRoles(new ArrayList<Rol>());
			
			//CREACION EVALUACION
			Evaluacion ev = new Evaluacion();
			ev.setId(1);
			ev.setFechaInicial(new GregorianCalendar(2013, 3, 19).getTime());
			ev.setFechaFinal(new GregorianCalendar(2013, 3, 21).getTime());
			ev.setPrograma(pr);
			ev.setPropietario(pro);
			//CREACION ROLES
			RolPK r1 = new RolPK();
			r1.setNombre("Docente");
			Rol docente = new Rol();
			docente.setEvaluacion(ev);
			docente.setId(r1);
			
			RolPK r2 = new RolPK();
			r2.setNombre("Egresado");
			Rol egresado = new Rol();
			egresado.setEvaluacion(ev);
			egresado.setId(r2);
			
			RolPK r3 = new RolPK();
			r3.setNombre("Industria");
			Rol industria = new Rol();
			industria.setEvaluacion(ev);
			industria.setId(r3);
			
			//CREACION USUARIOS-EVALUADORES
			Usuario aVil = new Usuario();
			aVil.setNombre("avillota");
			aVil.setPerfil(pfEva);
			aVil.setRoles(Arrays.asList(docente));
			
			Usuario lCas = new Usuario();
			lCas.setNombre("lcastañeda");
			lCas.setPerfil(pfEva);
			lCas.setRoles(Arrays.asList(docente));
			
			Usuario jCam = new Usuario();
			jCam.setNombre("jcampaz");
			jCam.setPerfil(pfEva);
			jCam.setRoles(Arrays.asList(egresado));
			
			Usuario mPiz = new Usuario();
			mPiz.setNombre("mpiza");
			mPiz.setPerfil(pfEva);
			mPiz.setRoles(Arrays.asList(egresado));
			
			Usuario gLon = new Usuario();
			gLon.setNombre("glondono");
			gLon.setPerfil(pfEva);
			gLon.setRoles(Arrays.asList(industria));
			
			Usuario hFar = new Usuario();
			hFar.setNombre("hfarboleda");
			hFar.setPerfil(pfEva);
			hFar.setRoles(Arrays.asList(industria));
			
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
			
			NivelDeConocimiento n4 = new NivelDeConocimiento();
			n4.setId(4);
			n4.setDescripcion("Ser experto en la practica o realizacion de");
			
			//CREACION DE LOS TEMAS
			Tema t211 = new Tema();
			t211.setId("2.1.1");
			t211.setNombre("Identificacion y Formulacion de Problemas");
			
			Tema t212 = new Tema();
			t212.setId("2.1.2");
			t212.setNombre("Modelado");
			
			//CREACION DE CALIFICACIONES
			Calificacion m211 = new Calificacion();
			m211.setEvaluacion(ev);
			Usuario_Evaluacion u11 = new Usuario_Evaluacion();
			Usuario_EvaluacionPK up11 = new Usuario_EvaluacionPK();
			up11.setUsuario("mpiza");
			u11.setId(up11);
			m211.setUsuarioEvaluacion(u11);
			m211.setNivelDeConocimiento(n0);
			m211.setTema(t211);
					
			Calificacion m212 = new Calificacion();
			m212.setEvaluacion(ev);
			Usuario_Evaluacion u12 = new Usuario_Evaluacion();
			u12.setId(up11);
			m212.setUsuarioEvaluacion(u12);
			m212.setNivelDeConocimiento(n4);
			m212.setTema(t212);
			
			Calificacion j211 = new Calificacion();
			j211.setEvaluacion(ev);
			Usuario_Evaluacion u21 = new Usuario_Evaluacion();
			Usuario_EvaluacionPK up21 = new Usuario_EvaluacionPK();
			up21.setUsuario("jcampaz");
			u21.setId(up21);
			j211.setUsuarioEvaluacion(u21);
			j211.setNivelDeConocimiento(n1);
			j211.setTema(t211);
					
			Calificacion j212 = new Calificacion();
			j212.setEvaluacion(ev);
			Usuario_Evaluacion u22 = new Usuario_Evaluacion();
			u22.setId(up21);
			j212.setUsuarioEvaluacion(u22);
			j212.setNivelDeConocimiento(n2);
			j212.setTema(t212);
					
			Calificacion h211 = new Calificacion();
			h211.setEvaluacion(ev);
			Usuario_Evaluacion u31 = new Usuario_Evaluacion();
			Usuario_EvaluacionPK up31 = new Usuario_EvaluacionPK();
			up31.setUsuario("hfarboleda");
			u31.setId(up31);
			h211.setUsuarioEvaluacion(u31);
			h211.setNivelDeConocimiento(n4);
			h211.setTema(t211);
					
			Calificacion h212 = new Calificacion();
			h212.setEvaluacion(ev);
			Usuario_Evaluacion u32 = new Usuario_Evaluacion();
			u32.setId(up31);
			h212.setUsuarioEvaluacion(u32);
			h212.setNivelDeConocimiento(n1);
			h212.setTema(t212);
					
			Calificacion g211 = new Calificacion();
			g211.setEvaluacion(ev);
			Usuario_Evaluacion u41 = new Usuario_Evaluacion();
			Usuario_EvaluacionPK up41 = new Usuario_EvaluacionPK();
			up41.setUsuario("glondono");
			u41.setId(up41);
			g211.setUsuarioEvaluacion(u41);
			g211.setNivelDeConocimiento(n2);
			g211.setTema(t211);
					
			Calificacion g212 = new Calificacion();
			g212.setEvaluacion(ev);
			Usuario_Evaluacion u42 = new Usuario_Evaluacion();
			u42.setId(up41);
			g212.setUsuarioEvaluacion(u42);
			g212.setNivelDeConocimiento(n4);
			g212.setTema(t212);
					
			Calificacion l211 = new Calificacion();
			l211.setEvaluacion(ev);
			Usuario_Evaluacion u51 = new Usuario_Evaluacion();
			Usuario_EvaluacionPK up51 = new Usuario_EvaluacionPK();
			up51.setUsuario("lcastañeda");
			u51.setId(up51);
			l211.setUsuarioEvaluacion(u51);
			l211.setNivelDeConocimiento(n4);
			l211.setTema(t211);
					
			Calificacion l212 = new Calificacion();
			l212.setEvaluacion(ev);
			Usuario_Evaluacion u52 = new Usuario_Evaluacion();
			u52.setId(up51);
			l212.setUsuarioEvaluacion(u52);
			l212.setNivelDeConocimiento(n2);
			l212.setTema(t212);
					
			Calificacion a211 = new Calificacion();
			a211.setEvaluacion(ev);
			Usuario_Evaluacion u61 = new Usuario_Evaluacion();
			Usuario_EvaluacionPK up61 = new Usuario_EvaluacionPK();
			up61.setUsuario("avillota");
			u61.setId(up61);
			a211.setUsuarioEvaluacion(u61);
			a211.setNivelDeConocimiento(n4);
			a211.setTema(t211);
					
			Calificacion a212 = new Calificacion();
			a212.setEvaluacion(ev);
			Usuario_Evaluacion u62 = new Usuario_Evaluacion();
			u62.setId(up61);
			a212.setUsuarioEvaluacion(u62);
			a212.setNivelDeConocimiento(n3);
			a212.setTema(t212);
			
			//ASOCIACION DE LOS ROLES A LA EVALUACION
			List<Rol> roles = Arrays.asList(docente, egresado, industria); 
			ev.setRoles(roles);
			
			//ASOCIACION DE LOS USUARIOS A LA EVALUACION
			List<Usuario> usuarios = Arrays.asList(aVil, lCas, jCam, mPiz, gLon, hFar);
			ev.setUsuarios(usuarios);
			
			//CREACION DE EVALUACIONBO
			EvaluacionBO evBO = ev.toBO();
			
			//ASOCIACION DE LAS CALIFICACIONES A LA EVALUACIONBO
			List<CalificacionBO> calificacionesBO = Arrays.asList(m211.toBO(), m212.toBO(), j211.toBO(), j212.toBO(), h211.toBO(), h212.toBO(), 
					g211.toBO(), g212.toBO(), l211.toBO(), l212.toBO(), a211.toBO(), a212.toBO());
			evBO.setCalificaciones(calificacionesBO);
			
			//CREACION DE LOS FACTORES DE IMPACTO
			FactorDeImpacto fd1 = new FactorDeImpacto();
			FactorDeImpactoPK fdpk1 = new FactorDeImpactoPK();
			fdpk1.setEvaluacion(1);
			fdpk1.setRol("Docente");
			fdpk1.setTema("2.1.1");
			fd1.setId(fdpk1);
			fd1.setFactorDeImpacto(30);
			
			FactorDeImpacto fd2 = new FactorDeImpacto();
			FactorDeImpactoPK fdpk2 = new FactorDeImpactoPK();
			fdpk2.setEvaluacion(1);
			fdpk2.setRol("Docente");
			fdpk2.setTema("2.1.2");
			fd2.setId(fdpk2);
			fd2.setFactorDeImpacto(40);
			
			FactorDeImpacto fe1 = new FactorDeImpacto();
			FactorDeImpactoPK fepk1 = new FactorDeImpactoPK();
			fepk1.setEvaluacion(1);
			fepk1.setRol("Egresado");
			fepk1.setTema("2.1.1");
			fe1.setId(fepk1);
			fe1.setFactorDeImpacto(30);
			
			FactorDeImpacto fe2 = new FactorDeImpacto();
			FactorDeImpactoPK fepk2 = new FactorDeImpactoPK();
			fepk2.setEvaluacion(1);
			fepk2.setRol("Egresado");
			fepk2.setTema("2.1.2");
			fe2.setId(fepk2);
			fe2.setFactorDeImpacto(20);
			
			FactorDeImpacto fi1 = new FactorDeImpacto();
			FactorDeImpactoPK fipk1 = new FactorDeImpactoPK();
			fipk1.setEvaluacion(1);
			fipk1.setRol("Industria");
			fipk1.setTema("2.1.1");
			fi1.setId(fipk1);
			fi1.setFactorDeImpacto(40);
			
			FactorDeImpacto fi2 = new FactorDeImpacto();
			FactorDeImpactoPK fipk2 = new FactorDeImpactoPK();
			fipk2.setEvaluacion(1);
			fipk2.setRol("Industria");
			fipk2.setTema("2.1.2");
			fi2.setId(fipk2);
			fi2.setFactorDeImpacto(40);
			
			
			
			//////
			//
			//RELIZACION DE LA PRUEBA
			//
			//////
			
			//AQUI DECIMOS QUE CUANDO SE LLAME EL METODO QUERY.GETRESULTLIST(), NOS RETORNE LAS CALIFICACIONES ALMACENADAS EN LA BD
			List<Calificacion> calificaciones = Arrays.asList(m211, m212, j211, j212, h211, h212, g211, g212, l211, l212, a211, a212);
			when(query.getResultList()).thenReturn(calificaciones);
			
			//AQUI DECIMOS QUE CUANDO SE LLAME EL METODO QUERY.GETRESULTLIST(), NOS RETORNE LOS FACTORES DE IMPACTO INDICADOS, ASOCIADOS A LA BD	
			List<FactorDeImpacto> docentes = Arrays.asList(fd1, fd2);
			List<FactorDeImpacto> egresados = Arrays.asList(fe1, fe2);
			List<FactorDeImpacto> industriales = Arrays.asList(fi1, fi2);

			when(query2.getResultList()).thenReturn(docentes);
			when(query2.getResultList()).thenReturn(egresados);
			when(query2.getResultList()).thenReturn(industriales);
			

			//////
			//
			//EJECUCION DE LA ASSERTION
			//
			//////
			Integer[] valoresEsperados = {3, 4};
			
			List<Integer> listaValoresReales = bean.obtenerPromedioGlobal(evBO);
//			Integer[] arrayValoresReales = listaValoresReales.toArray(new Integer[listaValoresReales.size()]);
			Integer[] arrayValoresReales = new Integer[2];
			
			for(int i = 0; i < 2; i++){
				arrayValoresReales[i] = listaValoresReales.get(i);
			}
			
			assertArrayEquals(valoresEsperados, arrayValoresReales);
			
	}
		
}
