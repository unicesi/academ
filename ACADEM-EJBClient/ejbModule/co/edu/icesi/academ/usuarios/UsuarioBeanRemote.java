package co.edu.icesi.academ.usuarios;

import java.util.List;

import javax.ejb.Remote;

import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.excepciones.IniciarSesionException;

@Remote
public interface UsuarioBeanRemote {

	public UsuarioBO iniciarSesion(UsuarioBO userBO) throws IniciarSesionException;

	public UsuarioBO crearUsuario(UsuarioBO usuarioBO);

	public UsuarioBO editarUsuario(UsuarioBO usuarioBO);

	public List<UsuarioBO> obtenerUsuariosPropietarios();
	
}
