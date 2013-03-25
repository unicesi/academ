package co.edu.icesi.academ.usuarios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.entities.Perfil;
import co.edu.icesi.academ.entities.Usuario;
import co.edu.icesi.academ.excepciones.IniciarSesionException;
import co.edu.icesi.academ.usuarios.UsuarioBeanLocal;
import co.edu.icesi.academ.usuarios.UsuarioBeanRemote;

/**
 * Session Bean implementation class LoginService
 */
@Stateless
public class UsuarioBean implements UsuarioBeanRemote, UsuarioBeanLocal {

	@PersistenceContext(unitName = "DTPersistenceUnit")
	protected EntityManager entityManager;

	public UsuarioBean() {

	}

	@Override
	public UsuarioBO iniciarSesion(UsuarioBO usuarioBO)
			throws IniciarSesionException {
		UsuarioBO usuarioLogeado = null;
		Usuario usuario = entityManager.find(Usuario.class,
				usuarioBO.getNombre());
		if (usuario != null) {
			if (usuario.getContraseña().equals(usuarioBO.getContraseña())) {
				usuarioLogeado = usuario.toBO();
			} else {
				throw new IniciarSesionException(
						"La contraseña no es correcta.");
			}
		} else {
			throw new IniciarSesionException("El usuario no es correcto.");
		}
		return usuarioLogeado;
	}

	@Override
	public UsuarioBO crearUsuario(UsuarioBO usuarioBO) {
		Usuario usuario = new Usuario();
		usuario.setNombre(usuarioBO.getNombre());
		usuario.setContraseña(usuarioBO.getContraseña());
		Perfil perfil = entityManager.find(Perfil.class, usuarioBO.getPerfil());
		usuario.setPerfil(perfil);
		entityManager.persist(usuario);
		entityManager.flush();
		return usuario.toBO();
	}

	@Override
	public UsuarioBO editarUsuario(UsuarioBO usuarioBO) {
		Usuario usuario = entityManager.find(Usuario.class,
				usuarioBO.getNombre());
		usuario.setNombre(usuarioBO.getNombre());
		usuario.setContraseña(usuarioBO.getContraseña());
		Perfil perfil = entityManager.find(Perfil.class, usuarioBO.getPerfil());
		usuario.setPerfil(perfil);
		entityManager.persist(usuario);
		entityManager.flush();
		return usuario.toBO();
	}

	@Override
	public List<UsuarioBO> obtenerUsuariosPropietarios() {
		TypedQuery<Usuario> query = entityManager.createNamedQuery(
				"obtenerUsuariosPropietarios", Usuario.class);
		List<Usuario> usuarios = query.getResultList();
		List<UsuarioBO> usuariosBO = new ArrayList<UsuarioBO>();

		for (Usuario programa : usuarios) {
			usuariosBO.add(programa.toBO());
		}
		return usuariosBO;
	}

}
