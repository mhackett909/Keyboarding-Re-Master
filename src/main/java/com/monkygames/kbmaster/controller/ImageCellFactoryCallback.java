/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.util.Callback;

/**
 * Handles displaying an Image in the 
 * @version 1.0
 */
public class ImageCellFactoryCallback implements Callback<ListView<Image>, ListCell<Image>>{


// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    @Override
    public ListCell<Image> call(ListView<Image> item) {
	return new ListCellImage();
    }
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
