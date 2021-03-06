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

package co.edu.icesi.academ.client.perfiles.propietario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.edu.icesi.academ.bo.CalificacionRolBO;
import co.edu.icesi.academ.bo.NivelDeConocimientoBO;
import co.edu.icesi.academ.bo.ResultadoAprendizajeBO;
import co.edu.icesi.academ.bo.RolBO;
import co.edu.icesi.academ.bo.TemaBO;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Item;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
//import com.vaadin.ui.components.Che;;
public class ConsolidadoEvaluacion extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button buttonGuardarConsolidado;
	@AutoGenerated
	private Button buttonConfigurarConsolidado;
	@AutoGenerated
	private Button buttonCalcularConsolidado;
	@AutoGenerated
	private Button buttonConsolidar;
	@AutoGenerated
	private Table tablaConsolidado;
	private static final long serialVersionUID = 1L;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public ConsolidadoEvaluacion() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		// Define the names and data types of columns.
		// Allow selecting items from the table.
		tablaConsolidado.setSelectable(true);

		// Send changes in selection immediately to server.
		tablaConsolidado.setImmediate(true);

		buttonConfigurarConsolidado.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
			}
		});
		
		buttonGuardarConsolidado.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				guardarConsolidado();
			}
		});

		buttonCalcularConsolidado.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				calcularConsolidado();
			}
		});
	}

	public void cargarTemas(List<TemaBO> temas, List<List<CalificacionRolBO>> calificaciones, List<Integer> promediosG) {

		tablaConsolidado.removeAllItems();

		for(int i =0;i< temas.size();i++)
		{
			TemaBO tema = temas.get(i);
			boolean calificado = false;
			ArrayList<Object>info = new ArrayList<Object>();
			info.add(tema.getId());
			info.add(tema.getNombre());
			for(int j=0;j<calificaciones.size();j++)
			{
				List<CalificacionRolBO> calificacionesRol = calificaciones.get(j);
				if( i < calificacionesRol.size() )
				{
					int nivel = calificacionesRol.get(i).getNivelDeConocimiento().getId();
					info.add( nivel + "");
					calificado = true;
				}
				else
				{
					calificado = false;
					info.add("NC");
				}
			}
			ComboBox caja = new ComboBox();
			caja.setNullSelectionAllowed(false);
			caja.setTextInputAllowed(false);
			for(int j=0;j<5;j++)
			{
				caja.addItem(j);
			}
			if( calificado )
				caja.select( promediosG.get(i));
		
			info.add(caja);
			info.add( new CheckBox() );
			tablaConsolidado.addItem(info.toArray(), tema);
		}
	}

	public void cargarColumnasPorRol(List<RolBO> roles)
	{
		this.tablaConsolidado.addContainerProperty("Numeral", String.class, null);
		this.tablaConsolidado.setColumnWidth("Numeral", 60);
		this.tablaConsolidado.addContainerProperty("Tema", String.class, null);
		this.tablaConsolidado.setColumnWidth("Tema", 300);

		for (RolBO rolBO : roles) 
		{
			this.tablaConsolidado.addContainerProperty("Nivel de Conocimiento "+rolBO.getNombre(), String.class, null);
			this.tablaConsolidado.setColumnWidth("Nivel de Conocimiento "+rolBO.getNombre(), 60);
		}

		this.tablaConsolidado.addContainerProperty("Nivel de Conocimiento Resultante", ComboBox.class, null);
		this.tablaConsolidado.setColumnWidth("Nivel de Conocimiento Resultante", 150);
		this.tablaConsolidado.addContainerProperty("Seleccione", CheckBox.class, null);
		this.tablaConsolidado.setColumnWidth("Selecicone", 60);

	}
	protected void guardarConsolidado() 
	{
		// Collect the results of the iteration into this string.
		String items = "";
		ResultadoAprendizajeBO resultadoBO;
		NivelDeConocimientoBO nivelBO;
		List<ResultadoAprendizajeBO> listaResultadoAprendizajeBOs=new ArrayList<ResultadoAprendizajeBO>();
		// Iterate over the item identifiers of the table.
		for (Iterator i = this.tablaConsolidado.getItemIds().iterator(); i.hasNext();) {
		    // Get the current item identifier, which is an integer.
		    TemaBO iid = (TemaBO) i.next();
		    // Now get the actual item from the table.
		    Item item = this.tablaConsolidado.getItem(iid);
			    
		    // And now we can get to the actual checkbox object.
		    CheckBox cb = (CheckBox)(item.getItemProperty("Seleccione").getValue());

		    // If the checkbox is selected.
		    if ((Boolean)cb.getValue() == true) {
		        // Do something with the selected item; collect the
		        // first names in a string.
		    	resultadoBO=new ResultadoAprendizajeBO();
		    	nivelBO=new NivelDeConocimientoBO();
		    	//int nivel = Integer.parseInt((String) item.getItemProperty("Nivel de Conocimiento Resultante").getValue());
		    	ComboBox elCb = (ComboBox) item.getItemProperty("Nivel de Conocimiento Resultante").getValue();
		    	int nivel = (int)elCb.getValue();
		    	nivelBO.setId(nivel);
		    	resultadoBO.setNivelDeConocimiento(nivelBO);
		    	//resultadoBO.setTema((TemaBO) item.getItemProperty("Numeral").getValue());
		    	resultadoBO.setTema(iid);
		    	listaResultadoAprendizajeBOs.add(resultadoBO);

		    }
		}
		ControladorPropietario.getInstance().guardarResultadosAprendizaje(listaResultadoAprendizajeBOs);
	}

	/**
	 * Este metodo llama al respondable de cargar el consolidado y pide la operacion
	 */
	protected void calcularConsolidado() 
	{
		ControladorPropietario.getInstance().cargarConsolidado();
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// tablaConsolidado
		tablaConsolidado = new Table();
		tablaConsolidado.setImmediate(false);
		tablaConsolidado.setWidth("100.0%");
		tablaConsolidado.setHeight("100.0%");
		mainLayout.addComponent(tablaConsolidado,
				"top:60.0px;right:10.0px;bottom:10.0px;left:10.0px;");
		
		// buttonConsolidar
		buttonConsolidar = new Button();
		buttonConsolidar.setCaption("Consolidate");
		buttonConsolidar.setImmediate(true);
		buttonConsolidar.setWidth("-1px");
		buttonConsolidar.setHeight("-1px");
		mainLayout.addComponent(buttonConsolidar, "top:460.0px;left:906.0px;");
		
		// buttonCalcularConsolidado
		buttonCalcularConsolidado = new Button();
		buttonCalcularConsolidado.setCaption("Calcular Consolidado");
		buttonCalcularConsolidado.setImmediate(true);
		buttonCalcularConsolidado.setWidth("-1px");
		buttonCalcularConsolidado.setHeight("-1px");
		mainLayout.addComponent(buttonCalcularConsolidado,
				"top:20.0px;left:10.0px;");
		
		// buttonConfigurarConsolidado
		buttonConfigurarConsolidado = new Button();
		buttonConfigurarConsolidado.setCaption("Configurar Consolidado");
		buttonConfigurarConsolidado.setImmediate(true);
		buttonConfigurarConsolidado.setWidth("-1px");
		buttonConfigurarConsolidado.setHeight("-1px");
		mainLayout.addComponent(buttonConfigurarConsolidado,
				"top:20.0px;left:180.0px;");
		
		// buttonGuardarConsolidado
		buttonGuardarConsolidado = new Button();
		buttonGuardarConsolidado.setCaption("Guardar Consolidado");
		buttonGuardarConsolidado.setImmediate(false);
		buttonGuardarConsolidado.setWidth("-1px");
		buttonGuardarConsolidado.setHeight("-1px");
		mainLayout.addComponent(buttonGuardarConsolidado,
				"top:20.0px;left:360.0px;");
		
		return mainLayout;
	}

}
