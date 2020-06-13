/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === javafx imports === //
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Handles displaying an Image in a ListCell.
 * @version 1.0
 */
public class ListCellImage extends ListCell<Image>{


// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override 
    protected void updateItem(Image item, boolean empty) {
	super.updateItem(item, empty);
	 
	if (item == null || empty) {
	    setGraphic(null);
	} else {
	    setGraphic(new ImageView(item));
	}
    }
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
