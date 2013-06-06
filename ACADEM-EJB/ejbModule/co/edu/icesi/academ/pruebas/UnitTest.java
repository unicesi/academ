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

package co.edu.icesi.academ.pruebas;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import co.edu.icesi.academ.materias.*;
import co.edu.icesi.academ.bloques.*;
import co.edu.icesi.academ.bo.BloqueBO;
import co.edu.icesi.academ.bo.MateriaBO;
import co.edu.icesi.academ.entities.Bloque;
import co.edu.icesi.academ.entities.Materia;

import javax.persistence.EntityManager;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class UnitTest {
	
	MateriaBean materiaBean;
	BloqueBean bloqueBean;
	EntityManager entityManager;
	
	@Before
	public void setUp(){
		
		materiaBean = new MateriaBean();
		bloqueBean = new BloqueBean();
		entityManager = mock(EntityManager.class);
		materiaBean.setEntityManager(entityManager);
		bloqueBean.setEntityManager(entityManager);
	}

	@Test
	public void testCrearMateria(){
				
		MateriaBO mat = new MateriaBO();
		mat.setNombre("Ingenieria de software");
		mat.setCodigo("1");
		
		//materia actual
		
		MateriaBO materiaActual = materiaBean.crearMateria(mat);
		
		//materia esperada
		
		Materia materiaEsperada = new Materia();
		materiaEsperada.setCodigo("1");
		materiaEsperada.setNombre("Ingenieria de software");
		
        assertEquals(materiaEsperada.toBO().getCodigo() , materiaActual.getCodigo());       
       
	}
	
	@Test
	public void testCrearBloque(){
		
		//bloque actual
				
		BloqueBO bloque = new BloqueBO();
		bloque.setNombre("Sistemas");
		
		BloqueBO bloqueActual = bloqueBean.crearBloque(bloque);
		
		//bloque esperado
//		List programas = new ArrayList();
//		List materias = new ArrayList();
//		programas.add("Ingenieria de Sistemas");
//		materias.add("Ingenieria de Software");
		Bloque bloqueEsperado = new Bloque();
		bloqueEsperado.setNombre("Sistemas");
//		bloqueEsperado.setProgramas(programas);
//		bloqueEsperado.setMaterias(materias);
		
        assertEquals(bloqueEsperado.toBO().getNombre().toString() , bloqueActual.getNombre().toString());     
       
	}
	
	
		
	@Test
	public void testEditarMateria(){
		
		//materia actual
		
		Materia mat = new Materia();
		mat.setNombre("Ingenieria de software");
		mat.setCodigo("1");
		
		materiaBean.crearMateria(mat.toBO());
		
		//
		
		MateriaBO mat2 = new MateriaBO();
		mat2.setNombre("Logica Digital");
		mat2.setCodigo("1");
		
		//materia actual editada
		
		MateriaBO materiaActualEditada = materiaBean.editarMateria(mat2);
		
		//materia esperada
		MateriaBO materiaEsperada = new MateriaBO();
		materiaEsperada.setCodigo("1");
		materiaEsperada.setNombre("Logica Digital");
		
		assertEquals(materiaEsperada.getNombre().toString() , materiaActualEditada.getNombre().toString() ); 
		
	}
	
	@Test
	public void consultarMaterias(){
		
		// materias actuales		

		MateriaBO mat1 = new MateriaBO();
		mat1.setNombre("Ingenieria de software");
		mat1.setCodigo("1");
		
		MateriaBO materiaActual1 = materiaBean.crearMateria(mat1);
		
		MateriaBO mat2 = new MateriaBO();
		mat2.setNombre("Logica digital");
		mat2.setCodigo("2");
		
		MateriaBO materiaActual2 = materiaBean.crearMateria(mat2);		

		// materia actual buscada
		List<MateriaBO> listActual= materiaBean.consultarMaterias();

		//lista de materias esperada
		
		List<MateriaBO> listResultEsp = new ArrayList<MateriaBO>();
		listResultEsp.add(materiaActual1);
		listResultEsp.add(materiaActual2);
		
		for (int i = 0; i < listResultEsp.size(); i++) {
			
			assertEquals(listResultEsp.get(i).getCodigo() , listActual.get(i).getCodigo() ); 
		}			
		
	}
	
	
	@Test
	public void consultarBloques(){
		
		// materias actuales		

		BloqueBO bloque1 = new BloqueBO();
		bloque1.setNombre("Sistemas");
		
		
		BloqueBO bloqueActual1 = bloqueBean.crearBloque(bloque1);
		
		BloqueBO bloque2 = new BloqueBO();
		bloque2.setNombre("Sistemas");
		
		
		BloqueBO bloqueActual2 = bloqueBean.crearBloque(bloque2);		

		// materia actual buscada
		List<BloqueBO> listActual= bloqueBean.consultarBloques();

		//lista de materias esperada
		
		List<BloqueBO> listResultEsp = new ArrayList<BloqueBO>();
		listResultEsp.add(bloqueActual1);
		listResultEsp.add(bloqueActual2);
		
		for (int i = 0; i < listResultEsp.size(); i++) {			
			assertEquals(listResultEsp.get(i).getNombre().toString() , listActual.get(i).getNombre().toString() ); 
		}					
	}	
}