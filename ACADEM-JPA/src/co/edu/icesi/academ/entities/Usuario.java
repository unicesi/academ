package co.edu.icesi.academ.entities;

import java.io.Serializable;
import javax.persistence.*;

import co.edu.icesi.academ.bo.UsuarioBO;

import java.util.List;


/**
 * The persistent class for the Usuarios database table.
 * 
 */
@Entity
@Table(name="Usuarios")
@NamedQueries({
	@NamedQuery(name="obtenerUsuariosPropietarios", query="SELECT u FROM Usuario u WHERE u.perfil.nombre LIKE 'Propietario'"),
	@NamedQuery(name="obtenerUsuariosDisponibles", query="SELECT u FROM Usuario u WHERE u.nombre NOT IN (SELECT ue.id.usuario FROM Usuario_Evaluacion ue WHERE ue.id.evaluacion LIKE :evaluacion)")
})
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String nombre;

	private String contraseña;

	//bi-directional many-to-one association to Evaluacion
	@OneToMany(mappedBy="propietario")
	private List<Evaluacion> evaluacionesPropietario;

	//bi-directional many-to-many association to Evaluacion
	@ManyToMany
	@JoinTable(
		name="Usuarios_Evaluaciones"
		, joinColumns={
			@JoinColumn(name="usuario")
			}
		, inverseJoinColumns={
			@JoinColumn(name="evaluacion")
			}
		)
	private List<Evaluacion> evaluacionesEvaluador;

	//bi-directional many-to-one association to Perfil
	@ManyToOne
	@JoinColumn(name="perfil")
	private Perfil perfil;

	public Usuario() {
	}

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

	public List<Evaluacion> getEvaluacionesPropietario() {
		return this.evaluacionesPropietario;
	}

	public void setEvaluacionesPropietario(List<Evaluacion> evaluacionesPropietario) {
		this.evaluacionesPropietario = evaluacionesPropietario;
	}

	public List<Evaluacion> getEvaluacionesEvaluador() {
		return this.evaluacionesEvaluador;
	}

	public void setEvaluacionesEvaluador(List<Evaluacion> evaluacionesEvaluador) {
		this.evaluacionesEvaluador = evaluacionesEvaluador;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public UsuarioBO toBO() {
		UsuarioBO usuarioBO = new UsuarioBO();
		usuarioBO.setNombre(this.nombre);
		usuarioBO.setPerfil(this.perfil.getNombre());
		return usuarioBO;
	}

	@Override
	public boolean equals(Object obj) {
		Usuario user = (Usuario)obj;
		return user.nombre.equals(nombre);
	}
	
	@Override
	public int hashCode() {
		return nombre.hashCode();
	}

}