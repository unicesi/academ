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

import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.excepciones.IniciarSesionException;
import co.edu.icesi.academ.usuarios.UsuarioBeanRemote;

public class UsuarioServices {

	private InitialContext context;
	private UsuarioBeanRemote usuarioBean;
	
	public UsuarioServices() {
		doLookup();
	}
	
	private void doLookup() 
	{
		try 
		{
			context = new InitialContext();
			usuarioBean = (UsuarioBeanRemote) context.lookup("java:global/ACADEM-EAR/ACADEM-EJB/UsuarioBean!co.edu.icesi.academ.usuarios.UsuarioBeanRemote");											
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
	}
	
	public UsuarioBO iniciarSesion(UsuarioBO userBO) throws IniciarSesionException {
		return usuarioBean.iniciarSesion(userBO);
	}

	public List<UsuarioBO> obtenerUsuariosPropietarios() {
		return usuarioBean.obtenerUsuariosPropietarios();
	}
}