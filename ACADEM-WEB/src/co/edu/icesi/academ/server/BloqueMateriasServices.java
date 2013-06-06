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

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import co.edu.icesi.academ.bloques.BloqueBeanRemote;
import co.edu.icesi.academ.bo.BloqueBO;
import co.edu.icesi.academ.bo.MateriaBO;
import co.edu.icesi.academ.excepciones.BloqueException;
import co.edu.icesi.academ.excepciones.CrearBloqueException;
import co.edu.icesi.academ.materias.MateriaBeanRemote;

public class BloqueMateriasServices {

	private InitialContext context;
	private BloqueBeanRemote bloqueBean;
	private MateriaBeanRemote materiaBean;
	
	public BloqueMateriasServices() {
		doLookup();
	}
	
	private void doLookup() 
	{
		try 
		{
			context = new InitialContext();
			bloqueBean = (BloqueBeanRemote)context.lookup("java:global/ACADEM-EAR/ACADEM-EJB/BloqueBean!co.edu.icesi.academ.bloques.BloqueBeanRemote");	
			materiaBean = (MateriaBeanRemote)context.lookup("java:global/ACADEM-EAR/ACADEM-EJB/MateriaBean!co.edu.icesi.academ.materias.MateriaBeanRemote");
			//                                               java:global/ACADEM-EAR/ACADEM-EJB/EvaluacionBean!co.edu.icesi.academ.evaluaciones.EvaluacionBeanRemote"
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
	}
	
	public BloqueBO crearBloque(BloqueBO bloqueBO) throws CrearBloqueException {
		return bloqueBean.crearBloque(bloqueBO);
	}
	
	public MateriaBO crearMateria(MateriaBO materiaBO){
		return materiaBean.crearMateria(materiaBO);
	}
	
	public List<BloqueBO> consultarBloqueBOs() throws BloqueException{
		return bloqueBean.consultarBloques();
	}
	
	public List<MateriaBO> consultarMateriaBOs(){
		return materiaBean.consultarMaterias();
	}

	public void asociarAMateriaBloque(List<MateriaBO> materias, BloqueBO bTmp) {
		try {
			System.err.println("###Bloque "+bTmp+" "+(bTmp instanceof BloqueBO));
			
			for (MateriaBO materiaBO : materias) {
				System.err.println("Prueba %%%%%% :p"+materiaBO +"  "+(materiaBO instanceof MateriaBO));
			}
			
			materiaBean.asociarMateriaBloque(materias,bTmp);
		} catch (Exception e) {
			Notification.show("Materia Asociada",Type.HUMANIZED_MESSAGE);
//			Notification.show("Error AsociarMateriaBloque - BloqueMateriasServices",Type.ERROR_MESSAGE);
		}
//		bloqueBean.asociarMateriaBloque(materias,bTmp);
	}

	public List<MateriaBO> consultarMateriasNoAsociadas(BloqueBO bloqueBO, List<MateriaBO> materiasAsociadas) throws BloqueException {
		return bloqueBean.consultarMateriasNoAsociadasBloque(bloqueBO,materiasAsociadas);
	}

	public List<MateriaBO> consultarMateriasAsociadas(BloqueBO bloqueBO) throws BloqueException {
		return bloqueBean.consultarMateriasAsociadasBloque(bloqueBO);
	}

	public void editarMateria(MateriaBO materiaBO) {
		materiaBean.editarMateria(materiaBO);
	}
	
	/**
	 * 
	 * 
	 * PRUEBA DAO
	 * 
	 */
	public List<MateriaBO> consultarMateriasDAO(){
		return materiaBean.consultarMateriasDAO();
	}
}