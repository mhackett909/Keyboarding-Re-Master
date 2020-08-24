/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.driver;

// == java imports === //
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.JoystickMapping;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Mapping;

public interface Mapper{

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
     * Returns the joystick mapping based on the index.
     * @param index the index of the mapping.
     * @return the mapping.
     */
    public JoystickMapping getJoystickMapping(int index, Keymap keymap);
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
