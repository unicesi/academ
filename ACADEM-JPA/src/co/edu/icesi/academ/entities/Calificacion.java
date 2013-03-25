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