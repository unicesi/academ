package co.edu.icesi.academ.client;

import java.io.File;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;

public class PanelEncabezado extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private static final long serialVersionUID = 1L;
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Embedded headerImage;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public PanelEncabezado() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		// Find the application directory
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

		// Image as a file resource
		FileResource headerResource = new FileResource(new File(basepath + "/WEB-INF/images/header.png"));
		headerImage.setSource(headerResource);
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
//		mainLayout.setWidth("982px");
		mainLayout.setWidth("100%");
		mainLayout.setHeight("143px");
		
		// top-level component properties
//		setWidth("982px");
		setWidth("100.0%");
		setHeight("143px");
		
		// headerImage
		headerImage = new Embedded();
		headerImage.setImmediate(false);
		headerImage.setWidth("982px");
		headerImage.setHeight("143px");
		headerImage.setSource(new ThemeResource(
				"img/component/embedded_icon.png"));
		headerImage.setType(1);
		headerImage.setMimeType("image/png");
		mainLayout.addComponent(headerImage, "top:0.0px;left:0.0px;");
		
		return mainLayout;
	}

}