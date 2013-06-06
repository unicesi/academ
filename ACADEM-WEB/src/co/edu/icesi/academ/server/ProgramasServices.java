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

import co.edu.icesi.academ.bloques.BloqueBeanRemote;
import co.edu.icesi.academ.bo.BloqueBO;
import co.edu.icesi.academ.bo.ProgramaBO;
import co.edu.icesi.academ.programas.ProgramaBeanRemote;

public class ProgramasServices {
	private InitialContext context;
	private ProgramaBeanRemote programaBeanRemote;
	private BloqueBeanRemote bloqueBeanRemote;
	
	public ProgramasServices() {
		doLookup();
	}
	private void doLookup() 
	{
		try 
		{
			context = new InitialContext();
			programaBeanRemote = (ProgramaBeanRemote)context.lookup("java:global/ACADEM-EAR/ACADEM-EJB/ProgramaBean!co.edu.icesi.academ.programas.ProgramaBeanRemote");
			bloqueBeanRemote = (BloqueBeanRemote)context.lookup("java:global/ACADEM-EAR/ACADEM-EJB/BloqueBean!co.edu.icesi.academ.bloques.BloqueBeanRemote");
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
	}
	public ProgramaBO crearPrograma(ProgramaBO programaBO){
		return programaBeanRemote.crearPrograma(programaBO);
	}
	
	//Muestra NullPointer
	public List<ProgramaBO> cargarProgramasInicial() {
		System.out.println("entrooooo a cargar programas");
		return programaBeanRemote.consultarProgramas();
	}
	public void asociarBloquesAPrograma(List<BloqueBO> b, ProgramaBO p) {
		programaBeanRemote.asociarPrograma(b, p);
		bloqueBeanRemote.asociarPrograma(b,p);
		
	}
}