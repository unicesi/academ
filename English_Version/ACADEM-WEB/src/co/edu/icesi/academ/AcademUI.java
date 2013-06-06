package co.edu.icesi.academ;

import co.edu.icesi.academ.client.PanelContenido;
import co.edu.icesi.academ.client.PanelPieDePagina;
import co.edu.icesi.academ.client.PanelEncabezado;
import co.edu.icesi.academ.client.PanelHerramientas;
import co.edu.icesi.academ.client.perfiles.ControladorEvaluaciones;
import co.edu.icesi.academ.client.perfiles.ListadoEvaluaciones;
import co.edu.icesi.academ.client.perfiles.PanelOpciones;
import co.edu.icesi.academ.client.perfiles.administrador.ControladorAdministrador;
import co.edu.icesi.academ.client.perfiles.administrador.FormularioCrearEditarEvaluacion;
import co.edu.icesi.academ.client.perfiles.administrador.GestionEvaluaciones;
import co.edu.icesi.academ.client.perfiles.evaluador.ControladorEvaluador;
import co.edu.icesi.academ.client.perfiles.evaluador.ListadoCalificacionesEvaluacion;
import co.edu.icesi.academ.client.perfiles.propietario.ConsolidadoEvaluacion;
import co.edu.icesi.academ.client.perfiles.propietario.ControladorPropietario;
import co.edu.icesi.academ.client.perfiles.propietario.ListadoEvaluadores;
import co.edu.icesi.academ.client.usuarios.ControladorInicioSesion;
import co.edu.icesi.academ.client.usuarios.FormularioInicioSesion;
import co.edu.icesi.academ.client.usuarios.PanelSesion;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
public class AcademUI extends UI {

	/***
	 * The HTTPSession.
	 */
	private WrappedSession httpSession;
	
	/***
	 * Panel de encabezado.
	 */
	private PanelEncabezado panelEncabezado;
	/***
	 * Panel de herramientas.
	 */
	private PanelHerramientas panelHerramientas;
	/***
	 * Panel de contenido.
	 */
	private PanelContenido panelContenido;
	/***
	 * Panel de pie de página.
	 */
	private PanelPieDePagina panelPieDePagina;
	/***
	 * Panel con opciones de la sesión (botón de cerrar sesión y label con el nombre del usuario).
	 */
	private PanelSesion panelSesion;
	/***
	 * Formulario para inicio de sesión.
	 */
	private FormularioInicioSesion formularioInicioSesion;
	/***
	 * Listado de evaluaciones.
	 */
	private ListadoEvaluaciones listadoEvaluaciones;
	/**
	 * Listado de calificaciones.
	 */
	private ListadoCalificacionesEvaluacion listadoCalificaciones;
	/***
	 * Panel de opciones.
	 */
	private PanelOpciones panelOpciones;
	/**
	 * Panel de selección de los evaluadores
	 */
	private ListadoEvaluadores listadoEvaluadores;
	/***
	 * Panel de gestión de evaluaciones
	 */
	private GestionEvaluaciones gestionEvaluaciones;
	/***
	 * Formulario para creación de una evaluación
	 */
	private FormularioCrearEditarEvaluacion formularioCrearEditarEvaluacion;
	/***
	 * Ventana emergente
	 */
	private Window ventanaEmergente;
	/***
	 * Tabla para consolidar calificaciones de una evaluación
	 */
	private ConsolidadoEvaluacion consolidadoEvaluacion;
	
	@Override
	protected void init(VaadinRequest request) {
		
		// Set the window or tab title
        getPage().setTitle("Academic Curricula Design and Management System (ACaDeM)");
        
        // Obtener la sesión del usuario (HTTPSession)
        this.httpSession = request.getWrappedSession();
        
        // Inicializar controladores
        ControladorInicioSesion.getInstance().setAcademUI(this);
        ControladorAdministrador.getInstance().setAcademUI(this);
        ControladorEvaluador.getInstance().setAcademUI(this);
        ControladorPropietario.getInstance().setAcademUI(this);
        ControladorEvaluaciones.getInstance().setAcademUI(this);
		
		final GridLayout layout = new GridLayout(1, 4);
		layout.setMargin(true);
		//layout.setHeight("100%");

		layout.setSizeFull();
		
		// ENCABEZADO
		this.panelEncabezado = new PanelEncabezado();
		layout.addComponent(this.panelEncabezado, 0, 0);
		
		// HERRAMIENTAS
		this.panelHerramientas = new PanelHerramientas();
		layout.addComponent(this.panelHerramientas, 0, 1);
		
		// Agregar herramientas a la barra de herramientas
		this.panelSesion = new PanelSesion();
		// Se muestra una vez el usuario inicia sesión.
		this.panelSesion.setVisible(false);
		this.panelHerramientas.agregarHerramienta(this.panelSesion, "top:0.0px;left:0.0px");
		
		// CONTENIDO
		this.panelContenido = new PanelContenido();
		layout.addComponent(this.panelContenido, 0, 2);

		// Cuando inicia la aplicación se muestra el formulario de login.
		this.formularioInicioSesion = new FormularioInicioSesion();
		this.panelContenido.setContenido(this.formularioInicioSesion);
		
		// Panel de opciones
		this.panelOpciones = new PanelOpciones();
		
		// PIE DE PÁGINA
		this.panelPieDePagina = new PanelPieDePagina();
		layout.addComponent(this.panelPieDePagina, 0, 3);
		
		setContent(layout);
	}
	
	public void mostrarNotificacion(String titulo, String mensaje) {
		Notification.show(titulo, mensaje, Type.TRAY_NOTIFICATION);
	}
	
	public PanelContenido getPanelContenido() {
		return this.panelContenido;
	}

	public FormularioInicioSesion getFormularioInicioSesion() {
		return this.formularioInicioSesion;
	}

	public WrappedSession getHTTPSession() {
		return this.httpSession;
	}

	public PanelSesion getPanelSesion() {
		return this.panelSesion;
	}
	
	public ListadoEvaluaciones getListadoEvaluaciones() {
		return this.listadoEvaluaciones;
	}

	public void setListadoEvaluaciones(ListadoEvaluaciones listadoEvaluaciones) {
		this.listadoEvaluaciones = listadoEvaluaciones;
	}
	
	public PanelOpciones getPanelOpciones() {
		return this.panelOpciones;
	}
	
	public void setPanelOpciones(PanelOpciones panelOpciones) {
		this.panelOpciones = panelOpciones;
	}
	
	public ListadoCalificacionesEvaluacion getListadoCalificacionesEvaluacion(){
		return this.listadoCalificaciones;
	}

	public void setListadoCalificacionesEvaluacion(ListadoCalificacionesEvaluacion listadoCalificaciones) {
		this.listadoCalificaciones = listadoCalificaciones;
	}

	public PanelEncabezado getPanelEncabezado() {
		return panelEncabezado;
	}

	public PanelPieDePagina getPanelPieDePagina() {
		return panelPieDePagina;
	}

	public GestionEvaluaciones getGestionEvaluaciones() {
		return gestionEvaluaciones;
	}

	public void setGestionEvaluaciones(GestionEvaluaciones gestionEvaluaciones) {
		this.gestionEvaluaciones = gestionEvaluaciones;
	}

	public FormularioCrearEditarEvaluacion getFormularioCrearEditarEvaluacion() {
		return formularioCrearEditarEvaluacion;
	}
	
	public void setFormularioCrearEditarEvaluacion(FormularioCrearEditarEvaluacion formularioCrearEditarEvaluacion) {
		this.formularioCrearEditarEvaluacion = formularioCrearEditarEvaluacion;
	}

	public void mostrarVentanaEmergente(Component contenido, String titulo) {
		// Crear una nueva ventana
        ventanaEmergente = new Window(titulo);
        ventanaEmergente.setModal(true);
        
        // Agregar el contenido a la ventana
        ventanaEmergente.setContent(contenido);
        ventanaEmergente.setHeight(contenido.getHeight() + 50, contenido.getHeightUnits());
        ventanaEmergente.setWidth(contenido.getWidth(), contenido.getWidthUnits());

        // Agregar la ventana a la ventana principal de la aplicación
        addWindow(ventanaEmergente);
	}
	
	public void cerrarVentanaEmergente() {
		removeWindow(ventanaEmergente);
	}

	public ListadoEvaluadores getListadoEvaluadores(){
		return listadoEvaluadores;
	}
	
	public void setListadoEvaluadores(ListadoEvaluadores listadoEvaluadores){
		this.listadoEvaluadores = listadoEvaluadores;
	}

	public ConsolidadoEvaluacion getConsolidadoEvaluacion() {
		return consolidadoEvaluacion;
	}

	public void setConsolidadoEvaluacion(ConsolidadoEvaluacion consolidadoEvaluacion) {
		this.consolidadoEvaluacion = consolidadoEvaluacion;
	}
}