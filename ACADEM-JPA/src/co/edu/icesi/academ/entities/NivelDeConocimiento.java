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