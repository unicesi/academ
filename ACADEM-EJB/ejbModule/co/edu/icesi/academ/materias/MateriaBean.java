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

package co.edu.icesi.academ.materias;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.dao.IMateriaDAO;
import com.factory.DAOFactory;

import co.edu.icesi.academ.bo.BloqueBO;
import co.edu.icesi.academ.bo.MateriaBO;
import co.edu.icesi.academ.entities.Bloque;
import co.edu.icesi.academ.entities.Materia;
import co.edu.icesi.academ.materias.MateriaBeanLocal;
import co.edu.icesi.academ.materias.MateriaBeanRemote;

/**
 * Session Bean implementation class LoginService
 */
@Stateless
public class MateriaBean implements MateriaBeanLocal, MateriaBeanRemote {

	@PersistenceContext(unitName = "DTPersistenceUnit")
	protected EntityManager entityManager;
	
	
	private IMateriaDAO materiaDAO = DAOFactory.getDAOFactory(DAOFactory.PGDB).getMateriaDAO();
	
	public MateriaBean() { }

	@Override
	public MateriaBO crearMateria(MateriaBO materiaBO) {
		Materia materia = new Materia();
		materia.setCodigo(materiaBO.getCodigo());
		materia.setNombre(materiaBO.getNombre());
		entityManager.persist(materia);
		entityManager.flush();
		return materia.toBO();
	}

	@Override
	public MateriaBO consultarMateria(String codigo) {
		Materia materia = entityManager.find(Materia.class, codigo);
		MateriaBO materiaBO = materia.toBO();
		return materiaBO;
	}

	@Override
	public MateriaBO editarMateria(MateriaBO materiaBO) {
		Materia materia = entityManager.find(Materia.class, materiaBO.getCodigo());
		materia.setNombre(materiaBO.getNombre());
		materia.setCodigo(materia.getCodigo());
		// por impl para que reasignar lista bloques y lista programas ¿?
		entityManager.persist(materia);
		entityManager.flush();
		return materiaBO;
	}

	@Override
	public List<MateriaBO> consultarMaterias() {	
		System.err.println("ENTROOO AL EVENTOOO CONSULTAR MATERIAS!!");
		TypedQuery<Materia> query = entityManager.createNamedQuery("obtenerMaterias", Materia.class);
		List<Materia> listMaterias = query.getResultList();		
		List<MateriaBO> retornoMateriasBOs = new ArrayList<MateriaBO>();		
		for (int i = 0; i < listMaterias.size(); i++) {
			retornoMateriasBOs.add(listMaterias.get(i).toBO());		
		}		
		return retornoMateriasBOs;
	}

	/**
	 * 
	 * 
	 * 
	 * PRUEBA DAO
	 * 
	 * 
	 * 
	 */
	@Override
	public List<MateriaBO> consultarMateriasDAO(){
		List<MateriaBO> m = new ArrayList<MateriaBO>();
		
		List<MateriaBO> m2 = materiaDAO.buscarMaterias();
		
		if(m2!=null){
			return m2;
		}
		
		return m;
	}
	
	
	
	
	
	
	
	
	
	@Override
	public void asociarMateriaBloque(List<MateriaBO> mTmp, BloqueBO bTmp) {
		
		System.err.println("ENTRO!!!!!!!!");
		
		System.err.println("###Bloque "+bTmp+" "+(bTmp instanceof BloqueBO));
		
		for (MateriaBO materiaBO : mTmp) {
			System.err.println("Prueba %%%%%% :p"+materiaBO +"  "+(materiaBO instanceof MateriaBO));
		}
		
//		try{
//		
//		Bloque bloque = entityManager.find(Bloque.class, bTmp.getNombre());
//		List<Materia> materiasDelBloque = new ArrayList<>();
//				
//		for (MateriaBO materiaBO : mTmp) {
//			Materia materia = entityManager.find(Materia.class, materiaBO.getCodigo());
//			materiasDelBloque.add(materia);
//		}
//		
//		bloque.setMaterias(materiasDelBloque);
//		
//		entityManager.persist(bloque);			
//		}catch(Exception e){
//			System.err.println(e.getMessage());
//		};
	}

	public void setEntityManager(EntityManager entityManager) 
	{
		this.entityManager = entityManager;
	}
}