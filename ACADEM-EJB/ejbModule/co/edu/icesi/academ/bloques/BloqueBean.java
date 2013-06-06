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

package co.edu.icesi.academ.bloques;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.edu.icesi.academ.bo.BloqueBO;
import co.edu.icesi.academ.bo.MateriaBO;
import co.edu.icesi.academ.bo.ProgramaBO;
import co.edu.icesi.academ.entities.Bloque;
import co.edu.icesi.academ.entities.Materia;
import co.edu.icesi.academ.entities.Programa;


/**
 * Session Bean implementation class AssessmentBean
 */
@Stateless
public class BloqueBean implements BloqueBeanLocal, BloqueBeanRemote {

	@PersistenceContext(unitName = "DTPersistenceUnit")
	protected EntityManager entityManager;
	
	public BloqueBean() {	}

	@Override
	public BloqueBO crearBloque(BloqueBO bloqueBO) {
		Bloque bloque = new Bloque();
		bloque.setNombre(bloqueBO.getNombre());
		entityManager.persist(bloque);
		entityManager.flush();
		return bloque.toBO();
	}

	@Override
	public BloqueBO consultarBloque(String nombre) {
		Bloque bloque = entityManager.find(Bloque.class, nombre);
		BloqueBO bloqueBO = bloque.toBO();
		return bloqueBO;
	}

	@Override
	public BloqueBO editarBloqueMaterias(BloqueBO bloqueBO) {
		Bloque bloque = new Bloque();
		bloque.setNombre(bloque.getNombre());
		entityManager.persist(bloque);
		entityManager.flush();
		return bloque.toBO();
	}

	@Override
	public List<BloqueBO> consultarBloques() {		
		TypedQuery<Bloque> query = entityManager.createNamedQuery("obtenerBloques", Bloque.class);
		List<Bloque> listBloques = query.getResultList();
		
		List<BloqueBO> retornoBloqueBOs = new ArrayList<BloqueBO>();
		
		for (int i = 0; i < listBloques.size(); i++) {			
			retornoBloqueBOs.add(listBloques.get(i).toBO());
		}
		return retornoBloqueBOs;
	}

	@Override
	public List<MateriaBO> consultarMateriasAsociadasBloque(BloqueBO bloqueBO) {
		Bloque bloque = entityManager.find(Bloque.class, bloqueBO.getNombre());
		List<Materia>list = new ArrayList<>();
		list = bloque.getMaterias();
		List<MateriaBO>retorno = new ArrayList<>();
		for (Materia materia : list) {
			retorno.add(materia.toBO());
		}
		return retorno;
	}

	@Override
	public List<MateriaBO> consultarMateriasNoAsociadasBloque(BloqueBO bloqueBO,List<MateriaBO> materiasAsociadas) {
		TypedQuery<Materia>query = entityManager.createNamedQuery("obtenerMaterias", Materia.class);
		
		List<Materia>todasMaterias = query.getResultList();
		List<MateriaBO>retorno = new ArrayList<>();
		
		for (Materia materia : todasMaterias) {
			retorno.add(materia.toBO());
		}
		
		for (MateriaBO materiaBO : materiasAsociadas) {
			retorno.remove(materiaBO);
		}
		return retorno;
	}

	@Override
	public void asociarPrograma(List<BloqueBO> b, ProgramaBO p) {
		
		TypedQuery<Programa>query =  entityManager.createNamedQuery("obtenerProgramaPorCodigo", Programa.class);
		query.setParameter("cod", p.getCodigo());
		Programa programa= query.getSingleResult();
				
		List<Bloque> bloques= new ArrayList<Bloque>();
		for (BloqueBO bloqueBO : b) {
			TypedQuery<Bloque>query2 = entityManager.createNamedQuery("obtenerBloquePorNombre", Bloque.class);
			query2.setParameter("nom", bloqueBO.getNombre());
			bloques.add(query2.getSingleResult());
		}
		
		for (Bloque bloque : bloques) {
			List<Programa> proBq = bloque.getProgramas();
			proBq.add(programa);
			bloque.setProgramas(proBq);
			entityManager.persist(bloque);
			entityManager.flush();
		}
	}

	@Override
	public void asociarMateriaBloque(List<MateriaBO> materias, BloqueBO bTmp) {
		
	}
	
	public void setEntityManager(EntityManager entityManager) 
	{
		this.entityManager = entityManager;
	}
}