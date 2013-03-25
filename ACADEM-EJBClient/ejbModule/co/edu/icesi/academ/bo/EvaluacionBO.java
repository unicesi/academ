package co.edu.icesi.academ.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EvaluacionBO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private Date fechaFinal;

	private Date fechaInicial;

	private ProgramaBO programa;

	private UsuarioBO propietario;

	private List<CalificacionBO> calificaciones;

	private List<UsuarioBO> evaluadores;
	
	private List<RolBO> roles;

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

	public ProgramaBO getPrograma() {
		return this.programa;
	}

	public void setPrograma(ProgramaBO programa) {
		this.programa = programa;
	}

	public UsuarioBO getPropietario() {
		return this.propietario;
	}

	public void setPropietario(UsuarioBO usuario) {
		this.propietario = usuario;
	}

	public List<CalificacionBO> getCalificaciones() {
		return this.calificaciones;
	}

	public void setCalificaciones(List<CalificacionBO> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public List<UsuarioBO> getEvaluadores() {
		return evaluadores;
	}

	public void setEvaluadores(List<UsuarioBO> evaluadores) {
		this.evaluadores = evaluadores;
	}

	public List<RolBO> getRoles() {
		return roles;
	}

	public void setRoles(List<RolBO> roles) {
		this.roles = roles;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
	    if (obj == this) return true;
	    if (!(obj instanceof TemaBO)) return false;
	    EvaluacionBO evaluacion = (EvaluacionBO) obj;
	    if (id == evaluacion.id) return true;
	    return false;
	}

}
