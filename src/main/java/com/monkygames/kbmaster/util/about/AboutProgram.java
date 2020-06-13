package com.monkygames.kbmaster.util.about;

/* 
 * See COPYING in top-level directory.
 */


import javafx.scene.image.Image;

/**
 * Used for displaying about information for a product.
 * @version 1.0
 */
public class AboutProgram{

// ============= Class variables ============== //
    private Image image;
    private String title;
    private String version;
    private String vendor;
    private String url;
    private String info;
    private static String imagePath = "/com/monkygames/kbmaster/fxml/resources/icons/";
// ============= Constructors ============== //
    public AboutProgram(String imageName, String title, String version,
			  String vendor, String url, String info){
	image = new Image(imagePath+imageName);
	this.title = title;
	this.version = version;
	this.vendor = vendor;
	this.url = url;
	this.info = info;
    }

// ============= Public Methods ============== //
    public Image getImage() {
	return image;
    }

    public String getTitle() {
	return title;
    }

    public String getVersion() {
	return version;
    }

    public String getVendor() {
	return vendor;
    }

    public String getUrl() {
	return url;
    }

    public String getInfo(){
	return info;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //


}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
