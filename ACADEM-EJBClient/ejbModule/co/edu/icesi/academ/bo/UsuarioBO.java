package co.edu.icesi.academ.bo;

import java.io.Serializable;

public class UsuarioBO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombre;

	private String contraseña;

	private String perfil;

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContraseña() {
		return this.contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getPerfil() {
		return this.perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
	    if (obj == this) return true;
	    if (!(obj instanceof TemaBO)) return false;
	    UsuarioBO usuario = (UsuarioBO) obj;
	    if (nombre.equals(usuario.nombre)) return true;
	    return false;
	}

	@Override
	public String toString() {
		return nombre;
	}

}
