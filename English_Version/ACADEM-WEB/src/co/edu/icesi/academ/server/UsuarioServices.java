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
