/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver;

// == java imports === //
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Mapping;

/**
 * An interface for each device to get the mapping of the default keys.
 * @version 1.0
 */
public interface Mapper{

// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //
    /**
     * Generates the default keymapping.
     * @return the default keymapping stored in the keymap.
     */
    public Keymap generateDefaultKeymap(int id);

    /**
     * Returns the button mapping based on the index.
     * @param index the index of the mapping.
     * @return the mapping.
     */
    public ButtonMapping getButtonMapping(int index, Keymap keymap);
    /**
     * Returns the mapping for all types of input based on the index.
     * @param index the index of the mapping.
     * @param keymap the keymap the mapping is located.
     * @return the mapping.
     */
    public Mapping getMapping(int index, Keymap keymap);
    /**
     * Returns the id associated with the index useful for getting the mapping 
     * of buttons.
     * @param index the index (id) of the mapping.
     * @return the string id used for getting a Button mapping.
     */
    public String getId(int index);
}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
