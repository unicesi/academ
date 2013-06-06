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

package co.edu.icesi.academ.programas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.edu.icesi.academ.bo.BloqueBO;
import co.edu.icesi.academ.bo.ProgramaBO;
import co.edu.icesi.academ.entities.Bloque;
import co.edu.icesi.academ.entities.Programa;
import co.edu.icesi.academ.excepciones.ProgramaException;
import co.edu.icesi.academ.programas.ProgramaBeanLocal;
import co.edu.icesi.academ.programas.ProgramaBeanRemote;

@Stateless
public class ProgramaBean implements ProgramaBeanLocal, ProgramaBeanRemote {

	@PersistenceContext(unitName = "DTPersistenceUnit")
	protected EntityManager entityManager;

	public ProgramaBean() {

	}

	@Override
	public ProgramaBO crearPrograma(ProgramaBO programaBO) {
		Programa programa = new Programa();
		programa.setCodigo(programaBO.getCodigo());
		programa.setNombre(programaBO.getNombre());
		programa.setDescripcion(programaBO.getDescripcion());
		entityManager.persist(programa);
		entityManager.flush();
		return programaBO;
	}

	@Override
	public ProgramaBO editarPrograma(ProgramaBO programaBO) {

		return null;
	}

	@Override
	public List<ProgramaBO> consultarProgramas() {
		TypedQuery<Programa> query = entityManager.createNamedQuery(
				"obtenerProgramas", Programa.class);
		List<Programa> list = query.getResultList();

		List<ProgramaBO> retorno = new ArrayList<ProgramaBO>();

		for (Iterator<Programa> iterator = list.iterator(); iterator.hasNext();) {
			Programa programa = (Programa) iterator.next();
			System.err.println(programa);
			retorno.add(programa.toBO());
		}
		return retorno;
	}

	@Override
	public void asociarPrograma(List<BloqueBO> b, ProgramaBO p) {
		
//		TypedQuery<Programa> query = entityManager.createNamedQuery(
//				"obtenerProgramaPorCodigo",Programa.class);
//		
//		query.setParameter("cod", p.getCodigo());
//		
//		Programa programa = query.getSingleResult();
//		
//		List<Bloque> bloquesPrograma = new ArrayList<Bloque>();
//		
//		for (BloqueBO bloque : b) {
//			TypedQuery<Bloque> query2 = entityManager.createNamedQuery(
//					"obtenerBloquePorNombre",Bloque.class);
//			query2.setParameter("nom", bloque.getNombre());
//			bloquesPrograma.add(query2.getSingleResult());
//		}
//		
//		programa.setBloques(bloquesPrograma);
		/*TypedQuery<Bloque> query = entityManager.createNamedQuery(
				"obtenerBloques", Bloque.class);
		List<Bloque> listEntidadesBloques = query.getResultList();
		List<BloqueBO> bloquesBos = new ArrayList<BloqueBO>();
		List<Bloque> bloquesPrograma = new ArrayList<Bloque>();

		TypedQuery<Programa> query2 = entityManager.createNamedQuery(
				"obtenerProgramas", Programa.class);
		List<Programa> listEntitdadesProgramas = query2.getResultList();
		List<ProgramaBO> programasBos = new ArrayList<ProgramaBO>();

		for (Bloque bloque : listEntidadesBloques) {
			bloquesBos.add(bloque.toBO());
		}		
		for(Programa programa: listEntitdadesProgramas){
			programasBos.add(programa.toBO());
		}

		Programa programa = new Programa();
		for (int i = 0; i < programasBos.size(); i++) {
			if(p.getCodigo()==programasBos.get(i).getCodigo()){
				programa = listEntitdadesProgramas.get(i);
			}
		}

		for (int i = 0; i < bloquesBos.size(); i++) {
			if (b.contains(bloquesBos.get(i))) {
				bloquesPrograma.add(listEntidadesBloques.get(i));
			}
		}

		programa.setBloques(bloquesPrograma);
		entityManager.persist(programa);
		entityManager.flush();*/
	}

	@Override
	public void asociarBloqueAPrograma(BloqueBO bloque, ProgramaBO programa)
			throws ProgramaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void desAsociarBloqueDePrograma(BloqueBO bloque, ProgramaBO programa)
			throws ProgramaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProgramaBO consultarProgramaPorBloque(BloqueBO bloqueBO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BloqueBO> consultarBloquesAsociadosPrograma(
			ProgramaBO programaBO) throws ProgramaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BloqueBO> consultarBloquesNoAsociadosPrograma(
			ProgramaBO programa) throws ProgramaException {
		// TODO Auto-generated method stub
		return null;
	}
}