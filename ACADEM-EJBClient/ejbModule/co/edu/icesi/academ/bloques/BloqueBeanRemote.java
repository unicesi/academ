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

import java.util.List;

import javax.ejb.Remote;

import co.edu.icesi.academ.bo.BloqueBO;
import co.edu.icesi.academ.bo.MateriaBO;
import co.edu.icesi.academ.bo.ProgramaBO;
import co.edu.icesi.academ.excepciones.BloqueException;
import co.edu.icesi.academ.excepciones.CrearBloqueException;
import co.edu.icesi.academ.excepciones.EditarBloqueException;
@Remote
public interface BloqueBeanRemote {
	public BloqueBO crearBloque(BloqueBO bloqueBO) throws CrearBloqueException;
	public BloqueBO consultarBloque(String bo) throws BloqueException;
	public BloqueBO editarBloqueMaterias(BloqueBO bloqueBO) throws EditarBloqueException;
	public List<BloqueBO> consultarBloques() throws BloqueException;
	public List<MateriaBO> consultarMateriasAsociadasBloque(BloqueBO bloqueBO) throws BloqueException;
	public List<MateriaBO> consultarMateriasNoAsociadasBloque(BloqueBO bloqueBO,List<MateriaBO> materiasAsociadas) throws BloqueException;
	void asociarPrograma(List<BloqueBO> b, ProgramaBO p);
	void asociarMateriaBloque(List<MateriaBO> materias, BloqueBO bTmp);
}