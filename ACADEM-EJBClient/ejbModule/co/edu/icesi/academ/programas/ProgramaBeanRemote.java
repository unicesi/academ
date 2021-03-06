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

import java.util.List;

import javax.ejb.Remote;

import co.edu.icesi.academ.bo.BloqueBO;
import co.edu.icesi.academ.bo.ProgramaBO;
import co.edu.icesi.academ.excepciones.ProgramaException;

@Remote
public interface ProgramaBeanRemote {
	public ProgramaBO crearPrograma(ProgramaBO programaBO);
	public ProgramaBO editarPrograma(ProgramaBO programaBO);
	public List<ProgramaBO> consultarProgramas();
	public void asociarBloqueAPrograma(BloqueBO bloque, ProgramaBO programa) throws ProgramaException;
	public void desAsociarBloqueDePrograma(BloqueBO bloque, ProgramaBO programa) throws ProgramaException;
	public ProgramaBO consultarProgramaPorBloque(BloqueBO bloqueBO);
	List<BloqueBO> consultarBloquesAsociadosPrograma(ProgramaBO programaBO)
			throws ProgramaException;
	List<BloqueBO> consultarBloquesNoAsociadosPrograma(ProgramaBO programa)
			throws ProgramaException;
	void asociarPrograma(List<BloqueBO> b, ProgramaBO p);
}