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