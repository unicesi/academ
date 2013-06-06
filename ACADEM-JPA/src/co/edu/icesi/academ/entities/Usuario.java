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

package co.edu.icesi.academ.entities;

import java.io.Serializable;
import javax.persistence.*;

import co.edu.icesi.academ.bo.RolBO;
import co.edu.icesi.academ.bo.UsuarioBO;

import java.util.ArrayList;
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

	//bi-directional many-to-many association to Rol
	@ManyToMany
	@JoinTable(
		name="Usuarios_Roles"
		, joinColumns={
			@JoinColumn(name="usuario")
			}
		, inverseJoinColumns={
			@JoinColumn(name="rol", referencedColumnName="nombre"),
			@JoinColumn(name="evaluacion", referencedColumnName="evaluacion")
			}
		)
	private List<Rol> roles;

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

	public List<Rol> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	
	public UsuarioBO toBO() {
		UsuarioBO usuarioBO = new UsuarioBO();
		usuarioBO.setNombre(this.nombre);
		usuarioBO.setPerfil(this.perfil.getNombre());
		List<RolBO> rolesBO = new ArrayList<RolBO>(roles.size());
		for (Rol rol : roles) {
			rolesBO.add(rol.toBO());
		}
		usuarioBO.setRoles(rolesBO);
		return usuarioBO;
	}

	@Override
	public boolean equals(Object obj) {
		Usuario user = (Usuario) obj;
		return user.nombre.equals(nombre);
	}
	
	@Override
	public int hashCode() {
		return nombre.hashCode();
	}

}