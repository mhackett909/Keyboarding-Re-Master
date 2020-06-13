/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;

/**
 * Handles converting javafx specific to awt.
 * @version 1.0
 */
public class JavaFXToAwt{

// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
    /**
     * Returns the awt modifiers for the specified JavaFX Key Event.
     * @param jfxKeyEvent the JavaFX Keyevent to get the awt modifier.
     * @return the awt modifier(s).
     */
    public static int getAWTModifiers(javafx.scene.input.KeyEvent jfxKeyEvent) {
	int mods = 0;
	if (jfxKeyEvent.isAltDown()) {
	    mods |= KeyEvent.ALT_MASK | KeyEvent.ALT_DOWN_MASK;
	}
	if (jfxKeyEvent.isControlDown()) {
	    mods |= KeyEvent.CTRL_MASK | KeyEvent.CTRL_DOWN_MASK;
	}
	if (jfxKeyEvent.isShiftDown()) {
	    mods |= KeyEvent.SHIFT_MASK | KeyEvent.SHIFT_DOWN_MASK;
	}
	if (jfxKeyEvent.isMetaDown()) {
	    mods |= KeyEvent.META_MASK | KeyEvent.META_DOWN_MASK;
	}
	return mods;
    }

    /**
     * Gets the awt key code form the javafx event.
     * @param jfxKeyEvent the event to get the key code.
     */
    public static int getAWTKeyCode(javafx.scene.input.KeyEvent jfxKeyEvent) {
	int code = java.awt.event.KeyEvent.VK_UNDEFINED;
	switch (jfxKeyEvent.getCode()) {
	    case A:
		code = java.awt.event.KeyEvent.VK_A;
		break;
	    case ACCEPT:
		code = java.awt.event.KeyEvent.VK_ACCEPT;
		break;
	    case ADD:
		code = java.awt.event.KeyEvent.VK_ADD;
		break;
	    case AGAIN:
		code = java.awt.event.KeyEvent.VK_AGAIN;
		break;
	    case ALL_CANDIDATES:
		code = java.awt.event.KeyEvent.VK_ALL_CANDIDATES;
		break;
	    case ALPHANUMERIC:
		code = java.awt.event.KeyEvent.VK_ALPHANUMERIC;
		break;
	    case ALT:
		code = java.awt.event.KeyEvent.VK_ALT;
		break;
	    case ALT_GRAPH:
		code = java.awt.event.KeyEvent.VK_ALT_GRAPH;
		break;
	    case AMPERSAND:
		code = java.awt.event.KeyEvent.VK_AMPERSAND;
		break;
	    case ASTERISK:
		code = java.awt.event.KeyEvent.VK_ASTERISK;
		break;
	    case AT:
		code = java.awt.event.KeyEvent.VK_AT;
		break;
	    case B:
		code = java.awt.event.KeyEvent.VK_B;
		break;
	    case BACK_QUOTE:
		code = java.awt.event.KeyEvent.VK_BACK_QUOTE;
		break;
	    case BACK_SLASH:
		code = java.awt.event.KeyEvent.VK_BACK_SLASH;
		break;
	    case BACK_SPACE:
		code = java.awt.event.KeyEvent.VK_BACK_SPACE;
		break;
	    case BEGIN:
		code = java.awt.event.KeyEvent.VK_BEGIN;
		break;
	    case BRACELEFT:
		code = java.awt.event.KeyEvent.VK_BRACELEFT;
		break;
	    case BRACERIGHT:
		code = java.awt.event.KeyEvent.VK_BRACERIGHT;
		break;
	    case C:
		code = java.awt.event.KeyEvent.VK_C;
		break;
	    case CANCEL:
		code = java.awt.event.KeyEvent.VK_CANCEL;
		break;
	    case CAPS:
		code = java.awt.event.KeyEvent.VK_CAPS_LOCK;
		break;
	    case CHANNEL_DOWN:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case CHANNEL_UP:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case CIRCUMFLEX:
		code = java.awt.event.KeyEvent.VK_CIRCUMFLEX;
		break;
	    case CLEAR:
		code = java.awt.event.KeyEvent.VK_CLEAR;
		break;
	    case CLOSE_BRACKET:
		code = java.awt.event.KeyEvent.VK_CLOSE_BRACKET;
		break;
	    case CODE_INPUT:
		code = java.awt.event.KeyEvent.VK_CODE_INPUT;
		break;
	    case COLON:
		code = java.awt.event.KeyEvent.VK_COLON;
		break;
	    case COLORED_KEY_0:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case COLORED_KEY_1:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case COLORED_KEY_2:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case COLORED_KEY_3:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case COMMA:
		code = java.awt.event.KeyEvent.VK_COMMA;
		break;
	    case COMPOSE:
		code = java.awt.event.KeyEvent.VK_COMPOSE;
		break;
	    case CONTEXT_MENU:
		code = java.awt.event.KeyEvent.VK_CONTEXT_MENU;
		break;
	    case CONTROL:
		code = java.awt.event.KeyEvent.VK_CONTROL;
		break;
	    case CONVERT:
		code = java.awt.event.KeyEvent.VK_CONVERT;
		break;
	    case COPY:
		code = java.awt.event.KeyEvent.VK_COPY;
		break;
	    case CUT:
		code = java.awt.event.KeyEvent.VK_CUT;
		break;
	    case D:
		code = java.awt.event.KeyEvent.VK_D;
		break;
	    case DEAD_ABOVEDOT:
		code = java.awt.event.KeyEvent.VK_DEAD_ABOVEDOT;
		break;
	    case DEAD_ABOVERING:
		code = java.awt.event.KeyEvent.VK_DEAD_ABOVERING;
		break;
	    case DEAD_ACUTE:
		code = java.awt.event.KeyEvent.VK_DEAD_ACUTE;
		break;
	    case DEAD_BREVE:
		code = java.awt.event.KeyEvent.VK_DEAD_BREVE;
		break;
	    case DEAD_CARON:
		code = java.awt.event.KeyEvent.VK_DEAD_CARON;
		break;
	    case DEAD_CEDILLA:
		code = java.awt.event.KeyEvent.VK_DEAD_CEDILLA;
		break;
	    case DEAD_CIRCUMFLEX:
		code = java.awt.event.KeyEvent.VK_DEAD_CIRCUMFLEX;
		break;
	    case DEAD_DIAERESIS:
		code = java.awt.event.KeyEvent.VK_DEAD_DIAERESIS;
		break;
	    case DEAD_DOUBLEACUTE:
		code = java.awt.event.KeyEvent.VK_DEAD_DOUBLEACUTE;
		break;
	    case DEAD_GRAVE:
		code = java.awt.event.KeyEvent.VK_DEAD_GRAVE;
		break;
	    case DEAD_IOTA:
		code = java.awt.event.KeyEvent.VK_DEAD_IOTA;
		break;
	    case DEAD_MACRON:
		code = java.awt.event.KeyEvent.VK_DEAD_MACRON;
		break;
	    case DEAD_OGONEK:
		code = java.awt.event.KeyEvent.VK_DEAD_OGONEK;
		break;
	    case DEAD_SEMIVOICED_SOUND:
		code = java.awt.event.KeyEvent.VK_DEAD_SEMIVOICED_SOUND;
		break;
	    case DEAD_TILDE:
		code = java.awt.event.KeyEvent.VK_DEAD_TILDE;
		break;
	    case DEAD_VOICED_SOUND:
		code = java.awt.event.KeyEvent.VK_DEAD_VOICED_SOUND;
		break;
	    case DECIMAL:
		code = java.awt.event.KeyEvent.VK_DECIMAL;
		break;
	    case DELETE:
		code = java.awt.event.KeyEvent.VK_DELETE;
		break;
	    case DIGIT0:
		code = java.awt.event.KeyEvent.VK_0;
		break;
	    case DIGIT1:
		code = java.awt.event.KeyEvent.VK_1;
		break;
	    case DIGIT2:
		code = java.awt.event.KeyEvent.VK_2;
		break;
	    case DIGIT3:
		code = java.awt.event.KeyEvent.VK_3;
		break;
	    case DIGIT4:
		code = java.awt.event.KeyEvent.VK_4;
		break;
	    case DIGIT5:
		code = java.awt.event.KeyEvent.VK_5;
		break;
	    case DIGIT6:
		code = java.awt.event.KeyEvent.VK_6;
		break;
	    case DIGIT7:
		code = java.awt.event.KeyEvent.VK_7;
		break;
	    case DIGIT8:
		code = java.awt.event.KeyEvent.VK_8;
		break;
	    case DIGIT9:
		code = java.awt.event.KeyEvent.VK_9;
		break;
	    case DIVIDE:
		code = java.awt.event.KeyEvent.VK_DIVIDE;
		break;
	    case DOLLAR:
		code = java.awt.event.KeyEvent.VK_DOLLAR;
		break;
	    case DOWN:
		code = java.awt.event.KeyEvent.VK_DOWN;
		break;
	    case E:
		code = java.awt.event.KeyEvent.VK_E;
		break;
	    case EJECT_TOGGLE:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case END:
		code = java.awt.event.KeyEvent.VK_END;
		break;
	    case ENTER:
		code = java.awt.event.KeyEvent.VK_ENTER;
		break;
	    case EQUALS:
		code = java.awt.event.KeyEvent.VK_EQUALS;
		break;
	    case ESCAPE:
		code = java.awt.event.KeyEvent.VK_ESCAPE;
		break;
	    case EURO_SIGN:
		code = java.awt.event.KeyEvent.VK_EURO_SIGN;
		break;
	    case EXCLAMATION_MARK:
		code = java.awt.event.KeyEvent.VK_EXCLAMATION_MARK;
		break;
	    case F:
		code = java.awt.event.KeyEvent.VK_F;
		break;
	    case F1:
		code = java.awt.event.KeyEvent.VK_F1;
		break;
	    case F10:
		code = java.awt.event.KeyEvent.VK_F10;
		break;
	    case F11:
		code = java.awt.event.KeyEvent.VK_F11;
		break;
	    case F12:
		code = java.awt.event.KeyEvent.VK_F12;
		break;
	    case F13:
		code = java.awt.event.KeyEvent.VK_F13;
		break;
	    case F14:
		code = java.awt.event.KeyEvent.VK_F14;
		break;
	    case F15:
		code = java.awt.event.KeyEvent.VK_F15;
		break;
	    case F16:
		code = java.awt.event.KeyEvent.VK_F16;
		break;
	    case F17:
		code = java.awt.event.KeyEvent.VK_F17;
		break;
	    case F18:
		code = java.awt.event.KeyEvent.VK_F18;
		break;
	    case F19:
		code = java.awt.event.KeyEvent.VK_F19;
		break;
	    case F2:
		code = java.awt.event.KeyEvent.VK_F2;
		break;
	    case F20:
		code = java.awt.event.KeyEvent.VK_F20;
		break;
	    case F21:
		code = java.awt.event.KeyEvent.VK_F21;
		break;
	    case F22:
		code = java.awt.event.KeyEvent.VK_F22;
		break;
	    case F23:
		code = java.awt.event.KeyEvent.VK_F23;
		break;
	    case F24:
		code = java.awt.event.KeyEvent.VK_F24;
		break;
	    case F3:
		code = java.awt.event.KeyEvent.VK_F3;
		break;
	    case F4:
		code = java.awt.event.KeyEvent.VK_F4;
		break;
	    case F5:
		code = java.awt.event.KeyEvent.VK_F5;
		break;
	    case F6:
		code = java.awt.event.KeyEvent.VK_F6;
		break;
	    case F7:
		code = java.awt.event.KeyEvent.VK_F7;
		break;
	    case F8:
		code = java.awt.event.KeyEvent.VK_F8;
		break;
	    case F9:
		code = java.awt.event.KeyEvent.VK_F9;
		break;
	    case FAST_FWD:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case FINAL:
		code = java.awt.event.KeyEvent.VK_FINAL;
		break;
	    case FIND:
		code = java.awt.event.KeyEvent.VK_FIND;
		break;
	    case FULL_WIDTH:
		code = java.awt.event.KeyEvent.VK_FULL_WIDTH;
		break;
	    case G:
		code = java.awt.event.KeyEvent.VK_G;
		break;
	    case GAME_A:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case GAME_B:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case GAME_C:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case GAME_D:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case GREATER:
		code = java.awt.event.KeyEvent.VK_GREATER;
		break;
	    case H:
		code = java.awt.event.KeyEvent.VK_H;
		break;
	    case HALF_WIDTH:
		code = java.awt.event.KeyEvent.VK_HALF_WIDTH;
		break;
	    case HELP:
		code = java.awt.event.KeyEvent.VK_HELP;
		break;
	    case HIRAGANA:
		code = java.awt.event.KeyEvent.VK_HIRAGANA;
		break;
	    case HOME:
		code = java.awt.event.KeyEvent.VK_HOME;
		break;
	    case I:
		code = java.awt.event.KeyEvent.VK_I;
		break;
	    case INFO:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case INPUT_METHOD_ON_OFF:
		code = java.awt.event.KeyEvent.VK_INPUT_METHOD_ON_OFF;
		break;
	    case INSERT:
		code = java.awt.event.KeyEvent.VK_INSERT;
		break;
	    case INVERTED_EXCLAMATION_MARK:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case J:
		code = java.awt.event.KeyEvent.VK_J;
		break;
	    case JAPANESE_HIRAGANA:
		code = java.awt.event.KeyEvent.VK_JAPANESE_HIRAGANA;
		break;
	    case JAPANESE_KATAKANA:
		code = java.awt.event.KeyEvent.VK_JAPANESE_KATAKANA;
		break;
	    case JAPANESE_ROMAN:
		code = java.awt.event.KeyEvent.VK_JAPANESE_ROMAN;
		break;
	    case K:
		code = java.awt.event.KeyEvent.VK_K;
		break;
	    case KANA:
		code = java.awt.event.KeyEvent.VK_KANA;
		break;
	    case KANA_LOCK:
		code = java.awt.event.KeyEvent.VK_KANA_LOCK;
		break;
	    case KANJI:
		code = java.awt.event.KeyEvent.VK_KANJI;
		break;
	    case KATAKANA:
		code = java.awt.event.KeyEvent.VK_KATAKANA;
		break;
	    case KP_DOWN:
		code = java.awt.event.KeyEvent.VK_KP_DOWN;
		break;
	    case KP_LEFT:
		code = java.awt.event.KeyEvent.VK_KP_LEFT;
		break;
	    case KP_RIGHT:
		code = java.awt.event.KeyEvent.VK_KP_RIGHT;
		break;
	    case KP_UP:
		code = java.awt.event.KeyEvent.VK_KP_UP;
		break;
	    case L:
		code = java.awt.event.KeyEvent.VK_L;
		break;
	    case LEFT:
		code = java.awt.event.KeyEvent.VK_LEFT;
		break;
	    case LEFT_PARENTHESIS:
		code = java.awt.event.KeyEvent.VK_LEFT_PARENTHESIS;
		break;
	    case LESS:
		code = java.awt.event.KeyEvent.VK_LESS;
		break;
	    case M:
		code = java.awt.event.KeyEvent.VK_M;
		break;
	    case META:
		code = java.awt.event.KeyEvent.VK_META;
		break;
	    case MINUS:
		code = java.awt.event.KeyEvent.VK_MINUS;
		break;
	    case MODECHANGE:
		code = java.awt.event.KeyEvent.VK_MODECHANGE;
		break;
	    case MULTIPLY:
		code = java.awt.event.KeyEvent.VK_MULTIPLY;
		break;
	    case MUTE:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case N:
		code = java.awt.event.KeyEvent.VK_N;
		break;
	    case NONCONVERT:
		code = java.awt.event.KeyEvent.VK_NONCONVERT;
		break;
	    case NUMBER_SIGN:
		code = java.awt.event.KeyEvent.VK_NUMBER_SIGN;
		break;
	    case NUMPAD0:
		code = java.awt.event.KeyEvent.VK_NUMPAD0;
		break;
	    case NUMPAD1:
		code = java.awt.event.KeyEvent.VK_NUMPAD1;
		break;
	    case NUMPAD2:
		code = java.awt.event.KeyEvent.VK_NUMPAD2;
		break;
	    case NUMPAD3:
		code = java.awt.event.KeyEvent.VK_NUMPAD3;
		break;
	    case NUMPAD4:
		code = java.awt.event.KeyEvent.VK_NUMPAD4;
		break;
	    case NUMPAD5:
		code = java.awt.event.KeyEvent.VK_NUMPAD5;
		break;
	    case NUMPAD6:
		code = java.awt.event.KeyEvent.VK_NUMPAD6;
		break;
	    case NUMPAD7:
		code = java.awt.event.KeyEvent.VK_NUMPAD7;
		break;
	    case NUMPAD8:
		code = java.awt.event.KeyEvent.VK_NUMPAD8;
		break;
	    case NUMPAD9:
		code = java.awt.event.KeyEvent.VK_NUMPAD9;
		break;
	    case NUM_LOCK:
		code = java.awt.event.KeyEvent.VK_NUM_LOCK;
		break;
	    case O:
		code = java.awt.event.KeyEvent.VK_O;
		break;
	    case OPEN_BRACKET:
		code = java.awt.event.KeyEvent.VK_OPEN_BRACKET;
		break;
	    case P:
		code = java.awt.event.KeyEvent.VK_P;
		break;
	    case PAGE_DOWN:
		code = java.awt.event.KeyEvent.VK_PAGE_DOWN;
		break;
	    case PAGE_UP:
		code = java.awt.event.KeyEvent.VK_PAGE_UP;
		break;
	    case PASTE:
		code = java.awt.event.KeyEvent.VK_PASTE;
		break;
	    case PAUSE:
		code = java.awt.event.KeyEvent.VK_PAUSE;
		break;
	    case PERIOD:
		code = java.awt.event.KeyEvent.VK_PERIOD;
		break;
	    case PLAY:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case PLUS:
		code = java.awt.event.KeyEvent.VK_PLUS;
		break;
	    case POUND:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case POWER:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case PREVIOUS_CANDIDATE:
		code = java.awt.event.KeyEvent.VK_PREVIOUS_CANDIDATE;
		break;
	    case PRINTSCREEN:
		code = java.awt.event.KeyEvent.VK_PRINTSCREEN;
		break;
	    case PROPS:
		code = java.awt.event.KeyEvent.VK_PROPS;
		break;
	    case Q:
		code = java.awt.event.KeyEvent.VK_Q;
		break;
	    case QUOTE:
		code = java.awt.event.KeyEvent.VK_QUOTE;
		break;
	    case QUOTEDBL:
		code = java.awt.event.KeyEvent.VK_QUOTEDBL;
		break;
	    case R:
		code = java.awt.event.KeyEvent.VK_R;
		break;
	    case RECORD:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case REWIND:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case RIGHT:
		code = java.awt.event.KeyEvent.VK_RIGHT;
		break;
	    case RIGHT_PARENTHESIS:
		code = java.awt.event.KeyEvent.VK_RIGHT_PARENTHESIS;
		break;
	    case ROMAN_CHARACTERS:
		code = java.awt.event.KeyEvent.VK_ROMAN_CHARACTERS;
		break;
	    case S:
		code = java.awt.event.KeyEvent.VK_S;
		break;
	    case SCROLL_LOCK:
		code = java.awt.event.KeyEvent.VK_SCROLL_LOCK;
		break;
	    case SEMICOLON:
		code = java.awt.event.KeyEvent.VK_SEMICOLON;
		break;
	    case SEPARATOR:
		code = java.awt.event.KeyEvent.VK_SEPARATOR;
		break;
	    case SHIFT:
		code = java.awt.event.KeyEvent.VK_SHIFT;
		break;
	    case SHORTCUT:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case SLASH:
		code = java.awt.event.KeyEvent.VK_SLASH;
		break;
	    case SOFTKEY_0:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case SOFTKEY_1:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case SOFTKEY_2:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case SOFTKEY_3:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case SOFTKEY_4:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case SOFTKEY_5:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case SOFTKEY_6:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case SOFTKEY_7:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case SOFTKEY_8:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case SOFTKEY_9:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case SPACE:
		code = java.awt.event.KeyEvent.VK_SPACE;
		break;
	    case STAR:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case STOP:
		code = java.awt.event.KeyEvent.VK_STOP;
		break;
	    case SUBTRACT:
		code = java.awt.event.KeyEvent.VK_SUBTRACT;
		break;
	    case T:
		code = java.awt.event.KeyEvent.VK_T;
		break;
	    case TAB:
		code = java.awt.event.KeyEvent.VK_TAB;
		break;
	    case TRACK_NEXT:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case TRACK_PREV:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case U:
		code = java.awt.event.KeyEvent.VK_U;
		break;
	    case UNDEFINED:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case UNDERSCORE:
		code = java.awt.event.KeyEvent.VK_UNDERSCORE;
		break;
	    case UNDO:
		code = java.awt.event.KeyEvent.VK_UNDO;
		break;
	    case UP:
		code = java.awt.event.KeyEvent.VK_UP;
		break;
	    case V:
		code = java.awt.event.KeyEvent.VK_V;
		break;
	    case VOLUME_DOWN:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case VOLUME_UP:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
		break;
	    case W:
		code = java.awt.event.KeyEvent.VK_W;
		break;
	    case WINDOWS:
		code = java.awt.event.KeyEvent.VK_WINDOWS;
		break;
	    case X:
		code = java.awt.event.KeyEvent.VK_X;
		break;
	    case Y:
		code = java.awt.event.KeyEvent.VK_Y;
		break;
	    case Z:
		code = java.awt.event.KeyEvent.VK_Z;
		break;
	    default:
		code = java.awt.event.KeyEvent.VK_UNDEFINED;
	}
	return code;
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
