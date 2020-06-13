/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.io;

// === java imports === //
import java.io.File;
import javax.swing.filechooser.FileFilter;


/**
 * A filter for profile exported files.
 * @version 1.0
 */
public class ProfileFileFilter extends FileFilter{

// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
     //Accept all directories and all gif, jpg, tiff, or png files.

    /**
     * Accept prof files only.
     * @param f the file to filter.
     */
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

	String extension = getExtension(f);
	if(extension != null && extension.equals("xml")){
	    return true;
	}
	return false;
    }

    //The description of this filter
    public String getDescription() {
        return "Profiles";
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /*
     * Get the extension of a file.
     */  
    private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
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
