/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

/**
 * Handles the animation of the button.
 * @version 1.0
 */
public class ButtonController{

// ============= Class variables ============== //
    private HandleMousePressed handleMousePressed;
    private HandleMouseReleased handleMouseReleased;
// ============= Constructors ============== //
    public ButtonController(){
	handleMousePressed = new HandleMousePressed();
	handleMouseReleased = new HandleMouseReleased();
    }
// ============= Public Methods ============== //
    /**
     * Adds the animation effects for the specified node.
     * @param node the node to apply the animation effects.
     */
    public void addNode(Node node){
	node.setOnMousePressed(handleMousePressed);
	node.setOnMouseReleased(handleMouseReleased);
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
/**
 * Handles mouse pressed events.
 */
class HandleMousePressed implements EventHandler<MouseEvent>{
    public void handle(MouseEvent me) {
	Object obj = me.getSource();
	/*
	if(obj instanceof Button){
	    ((Button)obj).setOpacity(0.5);
	}
	*/
	if(obj instanceof Node){
	    ((Node)obj).setOpacity(0.5);
	}
    }
}
/**
 * Handles mouse released events.
 */
class HandleMouseReleased implements EventHandler<MouseEvent>{
    public void handle(MouseEvent me) {
	Object obj = me.getSource();
	/*
	if(obj instanceof Button){
	    ((Button)obj).setOpacity(1.0);
	}
	*/
	if(obj instanceof Node){
	    ((Node)obj).setOpacity(1.0);
	}
    }
}
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
