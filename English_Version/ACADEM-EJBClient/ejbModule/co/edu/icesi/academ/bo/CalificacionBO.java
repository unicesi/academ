package co.edu.icesi.academ.bo;

import java.io.Serializable;

public class CalificacionBO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private EvaluacionBO evaluacion;

	private NivelDeConocimientoBO nivelDeConocimiento;

	private TemaBO tema;
	
	private String evaluador;

	public EvaluacionBO getEvaluacion() {
		return this.evaluacion;
	}

	public void setEvaluacion(EvaluacionBO evaluacion) {
		this.evaluacion = evaluacion;
	}

	public NivelDeConocimientoBO getNivelDeConocimiento() {
		return this.nivelDeConocimiento;
	}

	public void setNivelDeConocimiento(NivelDeConocimientoBO nivelDeConocimiento) {
		this.nivelDeConocimiento = nivelDeConocimiento;
	}

	public TemaBO getTema() {
		return this.tema;
	}

	public void setTema(TemaBO tema) {
		this.tema = tema;
	}

	public String getEvaluador() {
		return evaluador;
	}

	public void setEvaluador(String evaluador) {
		this.evaluador = evaluador;
	}
	
}
