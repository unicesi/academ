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
import java.util.List;


/**
 * The persistent class for the Usuarios_Evaluaciones database table.
 * 
 */
@Entity
@Table(name="Usuarios_Evaluaciones")
@NamedQueries({
	@NamedQuery(name="obtenerEvaluacionesDeEvaluador", query="SELECT ue FROM Usuario_Evaluacion ue WHERE ue.id.usuario LIKE :evaluador"),
	@NamedQuery(name="obtenerEvaluadoresDeEvaluacion", query="SELECT ue FROM Usuario_Evaluacion ue WHERE ue.id.evaluacion LIKE :evaluacion"),
	@NamedQuery(name="removerEvaluadoresDeEvaluacion", query="DELETE FROM Usuario_Evaluacion ue WHERE ue.id.evaluacion LIKE :evaluacion AND ue.id.usuario LIKE :usuario")
})
public class Usuario_Evaluacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Usuario_EvaluacionPK id;

	//bi-directional many-to-one association to Calificacion
	@OneToMany(mappedBy="usuarioEvaluacion")
	private List<Calificacion> calificaciones;

	public Usuario_Evaluacion() {
	}

	public Usuario_EvaluacionPK getId() {
		return this.id;
	}

	public void setId(Usuario_EvaluacionPK id) {
		this.id = id;
	}

	public List<Calificacion> getCalificaciones() {
		return this.calificaciones;
	}

	public void setCalificaciones(List<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}

}