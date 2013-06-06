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

import co.edu.icesi.academ.bo.NivelDeConocimientoBO;

import java.util.List;


/**
 * The persistent class for the NivelesDeConocimiento database table.
 * 
 */
@Entity
@Table(name="NivelesDeConocimiento")
@NamedQueries({
	@NamedQuery(name="obtenerNivelesDeConocimiento", query="SELECT n FROM NivelDeConocimiento n")
})
public class NivelDeConocimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String descripcion;

	//bi-directional many-to-one association to Calificacion
	@OneToMany(mappedBy="nivelDeConocimiento")
	private List<Calificacion> calificaciones;

	//bi-directional many-to-one association to ResultadoAprendizaje
	@OneToMany(mappedBy="nivelDeConocimiento")
	private List<ResultadoAprendizaje> resultadosAprendizaje;

	//bi-directional many-to-many association to Tema
	@ManyToMany(mappedBy="nivelesDeConocimiento")
	private List<Tema> temas;

	public NivelDeConocimiento() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Calificacion> getCalificaciones() {
		return this.calificaciones;
	}

	public void setCalificaciones(List<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public List<ResultadoAprendizaje> getResultadosAprendizaje() {
		return this.resultadosAprendizaje;
	}

	public void setResultadosAprendizaje(List<ResultadoAprendizaje> resultadosAprendizaje) {
		this.resultadosAprendizaje = resultadosAprendizaje;
	}

	public List<Tema> getTemas() {
		return this.temas;
	}

	public void setTemas(List<Tema> temas) {
		this.temas = temas;
	}
	
	public NivelDeConocimientoBO toBO() {
		NivelDeConocimientoBO nivelDeConocimientoBO = new NivelDeConocimientoBO();
		nivelDeConocimientoBO.setId(id);
		nivelDeConocimientoBO.setDescripcion(descripcion);
		return nivelDeConocimientoBO;
	}

}