/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.io;

// === jnostromo imports === //
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Mapping;
import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.profiles.Profile;
// === java imports === //
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;

/**
 * Generates an image that shows the keybindings.
 * @version 1.0
 */
public class GenerateBindingsImage{

// ============= Class variables ============== //
    private URL templateURL;
    private Color textColor;
    private Color textColor2;
    private BufferedImage image;
    private Device device;
// ============= Constructors ============== //
    public GenerateBindingsImage(Device device){
	this.device = device;
	try{
	    templateURL = getClass().getResource(device.getDeviceInformation().getImageBindingsTemplate());
	    textColor = Color.BLACK;
	    textColor2 = Color.BLACK;

	}catch(Exception e){
	    e.printStackTrace();
	}
	//120, 75
    }
// ============= Public Methods ============== //
    /**
     * Generates 1 image per keymap for the profile.
     * @param profile contains the keymaps to generate the images.
     * @return 1 image per keymap with the output and descriptions.
     */
    public Image[] generateImages(Profile profile){
	URL url = templateURL;
	Image[] images = new Image[8];
	try{
	    for(int j = 0; j < 8; j++){
		Keymap keymap = profile.getKeymap(j);
		images[j] = Toolkit.getDefaultToolkit().createImage(generateImage(keymap).getSource());
	    }
	}catch(Exception e){
	    e.printStackTrace();
	    return null;
	}
	return images;
    }
    /**
     * Generates a single image from the specified keymap.
     * @param keymap the image is based on this keymap.
     * @return the image of the descriptions and null on failure.
     */
    public Image generateImage(Keymap keymap){
	try{
	    URL url = templateURL;
	    image = ImageIO.read(url);
	    this.writeTitle(keymap);

	    // write the mappings to the imaage
	    for(Mapping mapping: keymap.getMappings()){
		writeBinding(mapping);
	    }

	    return Toolkit.getDefaultToolkit().createImage(image.getSource());
	}catch(Exception e){
	    e.printStackTrace();
	    return null;
	}
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void writeTitle(Keymap keymap){
	Graphics2D g2 = image.createGraphics();
	Font font = new Font("Dialog",Font.BOLD,18);
	g2.setFont(font);
	g2.setColor(textColor);
	g2.drawString("keymap "+keymap.getID(), 15,15);
	if(!keymap.getDescription().equals("keymap description")){
	    font = new Font("Dialog",Font.PLAIN,15);
	    g2.setFont(font);
	    g2.setColor(textColor2);
	    g2.drawString(keymap.getDescription(), 15,30);

	}
    }
    private void writeBinding(Mapping mapping){
	Rectangle rect = device.getBindingOutputAndDescriptionLocation(mapping);
	Output output = mapping.getOutput();
	Graphics2D g2 = image.createGraphics();
	g2.setColor(textColor);
	g2.drawString(output.getName(),rect.x,rect.y);
	writeDescription(g2, output, rect.width, rect.height);
    }
    /**
     * Writes the description of the output.
     */
    private void writeDescription(Graphics2D g2, Output output, int posx, int posy){
	if(!output.getName().equals(output.getDescription())){
	    // write description
	    Font font = new Font("Dialog",Font.ITALIC,9);
	    g2.setFont(font);
	    g2.setColor(textColor2);
	    g2.drawString(output.getDescription(), posx,posy);
	}
    }
    private boolean saveImage(String path){
	File outputfile = new File(path);
	try {
	    ImageIO.write(image, "png", outputfile);
	} catch (IOException ex) {
	    return false;
	}
	return true;
    }
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
