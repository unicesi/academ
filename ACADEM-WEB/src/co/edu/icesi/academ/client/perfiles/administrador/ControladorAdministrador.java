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

package co.edu.icesi.academ.client.perfiles.administrador;

import java.util.List;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import co.edu.icesi.academ.bo.BloqueBO;
import co.edu.icesi.academ.bo.CompetenciaBO;
import co.edu.icesi.academ.bo.EvaluacionBO;
import co.edu.icesi.academ.bo.MateriaBO;
import co.edu.icesi.academ.bo.ProgramaBO;
import co.edu.icesi.academ.bo.ResultadoAprendizajeBO;
import co.edu.icesi.academ.bo.UsuarioBO;
import co.edu.icesi.academ.client.Controlador;
import co.edu.icesi.academ.excepciones.BloqueException;
import co.edu.icesi.academ.excepciones.CompetenciaException;
import co.edu.icesi.academ.excepciones.CrearBloqueException;
import co.edu.icesi.academ.excepciones.CrearEvaluacionException;
import co.edu.icesi.academ.excepciones.EditarBloqueException;
import co.edu.icesi.academ.excepciones.EditarEvaluacionException;
import co.edu.icesi.academ.excepciones.MateriaException;
import co.edu.icesi.academ.excepciones.ProgramaException;
import co.edu.icesi.academ.server.BloqueMateriasServices;
import co.edu.icesi.academ.server.CompetenciaServices;
import co.edu.icesi.academ.server.EvaluacionServices;
import co.edu.icesi.academ.server.ProgramasServices;
import co.edu.icesi.academ.server.UsuarioServices;

public class ControladorAdministrador extends Controlador {

	private static ControladorAdministrador controladorAdministrador;
	private EvaluacionServices evaluacionServices;
	private UsuarioServices usuarioServices;
	private BloqueMateriasServices bloqueMateriasServices;
	private ProgramasServices programasServices;
	private CompetenciaServices competenciaServices;
	
	private ControladorAdministrador() {
		evaluacionServices = new EvaluacionServices();
		usuarioServices = new UsuarioServices();
		bloqueMateriasServices = new BloqueMateriasServices();
		programasServices = new ProgramasServices();
		competenciaServices = new CompetenciaServices();
	}

	public static ControladorAdministrador getInstance() {
		if (controladorAdministrador == null) {
			controladorAdministrador = new ControladorAdministrador();
		}
		return controladorAdministrador;
	}

	public void obtenerEvaluaciones() {
		// Obtener todas las evaluaciones
		List<EvaluacionBO> evaluaciones = this.evaluacionServices.obtenerEvaluaciones();
		
		// Cargar las evaluaciones en la lista de evaluaciones del administrador
		getAcademUI().getGestionEvaluaciones().cargarEvaluaciones(evaluaciones);
	}

	public void mostrarFormularioCrearEditarEvaluacion(EvaluacionBO evaluacionBO) {
		// Inicializar formulario para crear evaluación
		FormularioCrearEditarEvaluacion formularioCrearEditarEvaluacion = new FormularioCrearEditarEvaluacion();
		
		// Obtener todos los programas disponibles y cargarlos en la lista desplegable
		List<ProgramaBO> programas = this.evaluacionServices.obtenerProgramas();
		formularioCrearEditarEvaluacion.cargarProgramas(programas);
		
		// Obtener todos los usuarios propietarios disponibles y cargarlos en la lista desplegable
		List<UsuarioBO> usuarios = this.usuarioServices.obtenerUsuariosPropietarios();
		formularioCrearEditarEvaluacion.cargarUsuarios(usuarios);
		
		String titulo = "Crear Evaluación";
		
		if (evaluacionBO != null) {
			// Obtener la información de la evaluación seleccionada
			EvaluacionBO evaluacion = this.evaluacionServices.obtenerEvaluacion(evaluacionBO);
			formularioCrearEditarEvaluacion.cargarEvaluacion(evaluacion);
			
			titulo = "Editar Evaluación";
		}
		
		// Mostrar el formulario para crear evaluación como una ventana emergente
		getAcademUI().mostrarVentanaEmergente(formularioCrearEditarEvaluacion, titulo);
	}
	
	public void mostrarPanelCrearBloque(){
		PanelCrearBloque panelCrearBloque = new PanelCrearBloque();
		getAcademUI().mostrarVentanaEmergente(panelCrearBloque, "Crear Bloque");
	}

	public void crearEvaluacion(EvaluacionBO evaluacion) {
		try {
			// Intentar crear evaluacion
			this.evaluacionServices.crearEvaluacion(evaluacion);
			
			cerrarFormularioCrearEditarEvaluacion("Evaluación creada");
		} catch (CrearEvaluacionException e) {
			getAcademUI().getFormularioCrearEditarEvaluacion().setMensajeError(e.getMessage());
		}
	}

	public void editarEvaluacion(EvaluacionBO evaluacion) {
		try {
			// Intentar crear evaluacion
			this.evaluacionServices.editarEvaluacion(evaluacion);
			
			cerrarFormularioCrearEditarEvaluacion("Evaluación editada");
		} catch (EditarEvaluacionException e) {
			getAcademUI().getFormularioCrearEditarEvaluacion().setMensajeError(e.getMessage());
		}
	}
	
	private void cerrarFormularioCrearEditarEvaluacion(String mensaje) {
		// Cerrar la ventana emergente
		getAcademUI().cerrarVentanaEmergente();
		// Mostrar notificación de creación exitosa
		getAcademUI().mostrarNotificacion("ACaDeM", mensaje);
		// Cargar nuevamente la lista de evaluaciones del administrador
		obtenerEvaluaciones();
	}

	public void crearMateria(MateriaBO materiaBO){
		this.bloqueMateriasServices.crearMateria(materiaBO);		
	}
	
	public void crearBloque(BloqueBO bloqueBO) {
		try {
			this.bloqueMateriasServices.crearBloque(bloqueBO);
			cargarBloques();
		} catch (CrearBloqueException e) {
			getAcademUI().mostrarNotificacion("Error al crear el bloque "+bloqueBO, e.getMessage());
		}
	}
	
	public void cargarBloques(){
//		List<BloqueBO> listBlqoes;
//		try {
//			listBlqoes = this.bloqueMateriasServices.consultarBloqueBOs();
//			getAcademUI().getPanelBloquesMaterias().cargarBloques(listBlqoes);//aoi
//			getAcademUI().getPanelAlcance().cargarBloques(listBlqoes);
//		} catch (BloqueException e) {
//			getAcademUI().mostrarNotificacion("Error en la carga de bloques", e.getMessage());
//		}
	}
	
	public void cargarBloquesAsociadosPrograma(ProgramaBO programaBO){
//		List<BloqueBO> list;
//		try {
//			list = programasServices.consultarBloquesAsociados(programaBO);
//			getAcademUI().getPanelProgramasBloques().cargarBloquesAsociados(list);
//		} catch (ProgramaException e) {
//			getAcademUI().mostrarNotificacion("Error al cargar los bloque de "+programaBO, e.getMessage());
//		}
	}
	
	public void cargarBloquesNoAsociadosPrograma(ProgramaBO programaBO){
//		List<BloqueBO> list;
//		try {
//			list = programasServices.consultarBloquesNoAsociados(programaBO);
//			getAcademUI().getPanelProgramasBloques().cargarBloquesNoAsociados(list);
//		} catch (ProgramaException e) {
//			getAcademUI().mostrarNotificacion("Error al cargar los bloque de "+programaBO, e.getMessage());
//		}
	}
	
	public void AsociarMateriaBloque(MateriaBO materia, BloqueBO bTmp){
//		try {
//			this.bloqueMateriasServices.asociarAMateriaBloque(materia, bTmp);	
//		} catch (Exception e) {
//			Notification.show("Error AsociarMateriaBloque - ControladorAdministrador",Type.ERROR_MESSAGE);
//		}
	}
	
	public void desAsociarMateriaBloque(MateriaBO mat, BloqueBO blo) {
//		try {
//			this.bloqueMateriasServices.desAsociarAMateriaBloque(mat, blo);	
//		} catch (Exception e) {
//			Notification.show("Error AsociarMateriaBloque - ControladorAdministrador",Type.ERROR_MESSAGE);
//		}
	}

	public void crearPrograma(ProgramaBO programaBO) {
		this.programasServices.crearPrograma(programaBO);
		getAcademUI().cerrarVentanaEmergente();
	}

	public void cargarProgramas() {
		List<ProgramaBO> ps = this.programasServices.cargarProgramasInicial();
		getAcademUI().getPanelProgramasBloques().cargarProgramas(ps);
	}

	public void asociarBloquesAPrograma(BloqueBO bloque, ProgramaBO programa) throws ProgramaException {
//		try {
//			this.programasServices.asociarBloquesAPrograma(bloque,programa);
//		} catch (BloqueException e) {
//			getAcademUI().mostrarNotificacion("Error al asociar programas a bloque", e.getMessage());
//		}
	}
	
	public void desAsociarBloquesAPrograma(BloqueBO bloque, ProgramaBO programa) throws ProgramaException {
//		try {
//			this.programasServices.desAsociarBloquesAPrograma(bloque,programa);
//		} catch (BloqueException e) {
//			getAcademUI().mostrarNotificacion("Error al asociar programas a bloque", e.getMessage());
//		}
	}

	public void mostrarPanelCrearMateria() {
		getAcademUI().mostrarVentanaEmergente(new PanelCrearMateria(), "Crear Materia");
	}

	public void cargarMateriasBlqoue(BloqueBO bloqueBO) {
		if(bloqueBO!=null){
			List<MateriaBO> materiaBOs = null;
			List<MateriaBO> listMatNOsociadas;
			try {
				materiaBOs = bloqueMateriasServices.consultarMateriasAsociadas(bloqueBO);
				getAcademUI().getPanelBloquesMaterias().cargarMateriasAsociadas(materiaBOs);			
			
				listMatNOsociadas = bloqueMateriasServices.consultarMateriasNoAsociadas(bloqueBO, materiaBOs);
				getAcademUI().getPanelBloquesMaterias().cargarMateriasNoAsociadas(listMatNOsociadas);
			} catch (BloqueException e) {
				getAcademUI().mostrarNotificacion("Error al cargar las materias asociadas y no asociadas del bloque "+bloqueBO, e.getMessage());
			}
		}
	}

	public void mostrarPanelListaMateriasParaEditar(BloqueBO bloqueBO) {
//		PanelListaMateriasParaEditar panel = getAcademUI().getPanelListaMateriasParaEditar();
//		panel = new PanelListaMateriasParaEditar(bloqueBO);
//		List<MateriaBO> materiaBOs;
//		try {
//			materiaBOs = bloqueMateriasServices.consultarMateriaBOs();
//			panel.cargarMaterias(materiaBOs);
//			getAcademUI().mostrarVentanaEmergente(panel, "Editar Materia");
//		} catch (MateriaException e) {
//			getAcademUI().mostrarNotificacion("Error al ostrar la lista de materias", e.getMessage());
//		}
	}

	public void mostrarPanelEditarMateria(MateriaBO materiaBO,BloqueBO bloqueBO) {
		PanelEditarMateria panelEditarMateria = new PanelEditarMateria(materiaBO,bloqueBO);
		getAcademUI().mostrarVentanaEmergente(panelEditarMateria, "Editar Materia");
	}

	public void editarMateria(MateriaBO materiaBO) {
		bloqueMateriasServices.editarMateria(materiaBO);
		controladorAdministrador.cerrarPanelEditarMateria();
	}
	
	public void cerrarPanelEditarMateria(){
		getAcademUI().cerrarVentanaEmergente();
	}

	public void mostrarFormularioCrearPrograma() {
		PanelCrearPrograma panelCrearPrograma = new PanelCrearPrograma();
		getAcademUI().mostrarVentanaEmergente(panelCrearPrograma, "Crear Programa");
	}
	
	/**
	 * Método Encargado de Asociar el Alcance a las materias
	 * @param bloqueBO
	 */
	public void cargarMateriasAlcance(BloqueBO bloqueBO){
//		List<MateriaBO> materiaBOs;
//		try {
//			materiaBOs = bloqueMateriasServices.consultarMateriasAsociadas(bloqueBO);
//			getAcademUI().getPanelAlcance().cargarMaterias(materiaBOs);
//		} catch (BloqueException e) {
//			getAcademUI().mostrarNotificacion("Error al cargar las materias alcance del bloque "+bloqueBO, e.getMessage());
//		}
	}

	public void cargarCompetencias(BloqueBO bloqueBO) {
//		ProgramaBO programaBO = programasServices.consultarProgramaPorBloque(bloqueBO);
//		List<CompetenciaBO> listCompetenciaBOs;
//		try {
//			listCompetenciaBOs = competenciaServices.consultarCompetencias(programaBO);
//			getAcademUI().getPanelAlcance().cargarCompetencias(listCompetenciaBOs);
//		} catch (CompetenciaException e) {
//			getAcademUI().mostrarNotificacion("Error al cargar las competencias del bloque "+bloqueBO, e.getMessage());
//		}
	}

	public void editarBloque(BloqueBO bloqueBO,String nuevoNombre) {
//		try {
//			bloqueMateriasServices.editarBloque(bloqueBO,nuevoNombre);
//		} catch (EditarBloqueException e) {
//			getAcademUI().mostrarNotificacion("Error al editar el bloque "+bloqueBO, e.getMessage());
//		}
	}

	public void mostrarPanelEditarBloque(BloqueBO bloqueBO) {
		getAcademUI().mostrarVentanaEmergente(new PanelEditarBloque(bloqueBO), "Editar bloque");
	}

	public List<MateriaBO> darListaMaterias() {
		return bloqueMateriasServices.consultarMateriaBOs();
	}

	public void editarPrograma(ProgramaBO bo) {
//		programasServices.editarPrograma(bo);		
	}

	public List<ProgramaBO> darListaProgramas() {
		return programasServices.cargarProgramasInicial();
	}

	public List<ResultadoAprendizajeBO> consultarResultadosAprendizaje(CompetenciaBO bo) {
		return null;
//		return competenciaServices.consultarResultadosAprendizaje(bo);
	}
}