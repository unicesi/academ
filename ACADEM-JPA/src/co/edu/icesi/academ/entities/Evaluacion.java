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

import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.RolBO;
import co.edu.icesi.academ.bo.UsuarioBO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Evaluaciones database table.
 * 
 */
@Entity
@Table(name="Evaluaciones")
@NamedQueries({
	@NamedQuery(name="obtenerEvaluacionesDePropietario", query="SELECT e FROM Evaluacion e WHERE e.propietario.nombre LIKE :propietario"),
	@NamedQuery(name="obtenerEvaluaciones", query="SELECT e FROM Evaluacion e")
})
public class Evaluacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFinal;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicial;

	//bi-directional many-to-one association to Calificacion
	@OneToMany(mappedBy="evaluacion")
	private List<Calificacion> calificaciones;

	//bi-directional many-to-one association to Programa
	@ManyToOne
	@JoinColumn(name="programa")
	private Programa programa;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="propietario")
	private Usuario propietario;

	//bi-directional many-to-one association to Rol
	@OneToMany(mappedBy="evaluacion")
	private List<Rol> roles;

	//bi-directional many-to-many association to Usuario
	@ManyToMany(mappedBy="evaluacionesEvaluador")
	private List<Usuario> usuarios;

	public Evaluacion() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return this.fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public List<Calificacion> getCalificaciones() {
		return this.calificaciones;
	}

	public void setCalificaciones(List<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public Programa getPrograma() {
		return this.programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public Usuario getPropietario() {
		return this.propietario;
	}

	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}

	public List<Rol> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public EvaluacionBO toBO() {
		EvaluacionBO evaluacionBO = new EvaluacionBO();
		evaluacionBO.setId(id);
		evaluacionBO.setFechaInicial(fechaInicial);
		evaluacionBO.setFechaFinal(fechaFinal);
		evaluacionBO.setPrograma(programa.toBO());
		evaluacionBO.setPropietario(propietario.toBO());
		
		List<RolBO> rolesBO = new ArrayList<RolBO>(roles.size());
		for (Rol rol : roles) {
			rolesBO.add(rol.toBO());
		}
		evaluacionBO.setRoles(rolesBO);
		
		List<UsuarioBO> usuariosBO = new ArrayList<UsuarioBO>(usuarios.size());
		for (Usuario usuario : usuarios) {
			usuariosBO.add(usuario.toBO());
		}
		evaluacionBO.setEvaluadores(usuariosBO);
		
		return evaluacionBO;
	}

}