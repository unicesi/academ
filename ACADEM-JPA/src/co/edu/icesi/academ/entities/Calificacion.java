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

import co.edu.icesi.academ.bo.CalificacionBO;


/**
 * The persistent class for the Calificaciones database table.
 * 
 */
@Entity
@Table(name="Calificaciones")
@NamedQueries({
	@NamedQuery(name="obtenerCalificacionesEvaluadorEvaluacion", query="SELECT c FROM Calificacion c WHERE c.id.evaluador LIKE :evaluador AND c.id.evaluacion LIKE :evaluacion"),
	@NamedQuery(name="obtenerCalificacionesEvaluacion", query="SELECT c FROM Calificacion c WHERE c.id.evaluacion LIKE :evaluacion"),
	@NamedQuery(name="removerCalificacionPrevia", query="DELETE FROM Calificacion c WHERE c.id.evaluador LIKE :evaluador AND c.id.evaluacion LIKE :evaluacion AND c.id.tema LIKE :tema")
})
public class Calificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CalificacionPK id;

	//bi-directional many-to-one association to Evaluacion
	@ManyToOne
	@JoinColumn(name="evaluacion", insertable=false, updatable=false)
	private Evaluacion evaluacion;

	//bi-directional many-to-one association to NivelDeConocimiento
	@ManyToOne
	@JoinColumn(name="nivelDeConocimiento", insertable=false, updatable=false)
	private NivelDeConocimiento nivelDeConocimiento;

	//bi-directional many-to-one association to Tema
	@ManyToOne
	@JoinColumn(name="tema", insertable=false, updatable=false)
	private Tema tema;

	//bi-directional many-to-one association to Usuario_Evaluacion
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="evaluacion", referencedColumnName="evaluacion", insertable=false, updatable=false),
		@JoinColumn(name="evaluador", referencedColumnName="usuario", insertable=false, updatable=false)
		})
	private Usuario_Evaluacion usuarioEvaluacion;

	public Calificacion() {
	}

	public CalificacionPK getId() {
		return this.id;
	}

	public void setId(CalificacionPK id) {
		this.id = id;
	}

	public Evaluacion getEvaluacion() {
		return this.evaluacion;
	}

	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}

	public NivelDeConocimiento getNivelDeConocimiento() {
		return this.nivelDeConocimiento;
	}

	public void setNivelDeConocimiento(NivelDeConocimiento nivelDeConocimiento) {
		this.nivelDeConocimiento = nivelDeConocimiento;
	}

	public Tema getTema() {
		return this.tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario_Evaluacion getUsuarioEvaluacion() {
		return this.usuarioEvaluacion;
	}

	public void setUsuarioEvaluacion(Usuario_Evaluacion usuarioEvaluacion) {
		this.usuarioEvaluacion = usuarioEvaluacion;
	}
	
	public CalificacionBO toBO() {
		CalificacionBO calificacionBO = new CalificacionBO();
		calificacionBO.setEvaluacion(evaluacion.toBO());
		calificacionBO.setEvaluador(usuarioEvaluacion.getId().getUsuario());
		calificacionBO.setNivelDeConocimiento(nivelDeConocimiento.toBO());
		calificacionBO.setTema(tema.toBO());
		return calificacionBO;
	}

}