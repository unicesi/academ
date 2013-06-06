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

import co.edu.icesi.academ.bo.MateriaBO;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;

public class PanelCrearMateria extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private TextField txt_nombre;
	@AutoGenerated
	private TextField txt_codigo;
	@AutoGenerated
	private Button btnGuardar;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public PanelCrearMateria() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		
		btnGuardar.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				crearMateria();
			}
		});
		
	}

	public void crearMateria(){
		String codigo = txt_codigo.getValue();
		String nombre = txt_nombre.getValue();
		if(codigo!="" && nombre!=""){
			MateriaBO materiaBO = new MateriaBO();
			materiaBO.setCodigo(codigo);
			materiaBO.setNombre(nombre);
			ControladorAdministrador.getInstance().crearMateria(materiaBO);
			ControladorAdministrador.getInstance().getAcademUI().cerrarVentanaEmergente();
		}else
			Notification.show("Ingreso mal los datos", Type.ERROR_MESSAGE);
		
	}
	
	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("360px");
		mainLayout.setHeight("180px");
		
		// top-level component properties
		setWidth("360px");
		setHeight("180px");
		
		// btnGuardar
		btnGuardar = new Button();
		btnGuardar.setCaption("Guardar");
		btnGuardar.setImmediate(true);
		btnGuardar.setWidth("240px");
		btnGuardar.setHeight("-1px");
		mainLayout.addComponent(btnGuardar, "top:120.0px;left:60.0px;");
		
		// txt_codigo
		txt_codigo = new TextField();
		txt_codigo.setCaption("Código");
		txt_codigo.setImmediate(false);
		txt_codigo.setWidth("240px");
		txt_codigo.setHeight("-1px");
		mainLayout.addComponent(txt_codigo, "top:40.0px;left:60.0px;");
		
		// txt_nombre
		txt_nombre = new TextField();
		txt_nombre.setCaption("Nombre");
		txt_nombre.setImmediate(false);
		txt_nombre.setWidth("240px");
		txt_nombre.setHeight("-1px");
		mainLayout.addComponent(txt_nombre, "top:80.0px;left:60.0px;");
		
		return mainLayout;
	}

}
