/*
 *  This file is part of Strick'N'Tick.
 *
 *  Strick'N'Tick is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Strick'N'Tick is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with EasyTimeTracker.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Strick'N'Tick: First creation by 2BlackCoffees: http://www.twoblackcoffees.com/
 *
 **/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * Here are stored all groups of keys
 * 
 * @author Nino
 * 
 */
public interface KeyFamilies {
    final List<Integer> functionKeys = new ArrayList<Integer>(
	    Arrays.asList(new Integer[] { NativeKeyEvent.VK_F1,
		    NativeKeyEvent.VK_F2, NativeKeyEvent.VK_F3,
		    NativeKeyEvent.VK_F4, NativeKeyEvent.VK_F5,
		    NativeKeyEvent.VK_F6, NativeKeyEvent.VK_F7,
		    NativeKeyEvent.VK_F8, NativeKeyEvent.VK_F9,
		    NativeKeyEvent.VK_F10, NativeKeyEvent.VK_F11,
		    NativeKeyEvent.VK_F12, NativeKeyEvent.VK_F13,
		    NativeKeyEvent.VK_F14, NativeKeyEvent.VK_F15,
		    NativeKeyEvent.VK_F16, NativeKeyEvent.VK_F17,
		    NativeKeyEvent.VK_F18, NativeKeyEvent.VK_F19,
		    NativeKeyEvent.VK_F20, NativeKeyEvent.VK_F21,
		    NativeKeyEvent.VK_F22, NativeKeyEvent.VK_F23,
		    NativeKeyEvent.VK_F24 }));
    final List<Integer> controlKeys = new ArrayList<Integer>(
	    Arrays.asList(new Integer[] { NativeKeyEvent.VK_BACK_SPACE,
		    NativeKeyEvent.VK_TAB, NativeKeyEvent.VK_CANCEL,
		    NativeKeyEvent.VK_SHIFT, NativeKeyEvent.VK_CONTROL,
		    NativeKeyEvent.VK_ALT, NativeKeyEvent.VK_META,
		    NativeKeyEvent.VK_WINDOWS, NativeKeyEvent.VK_CONTEXT_MENU,
		    NativeKeyEvent.VK_PAUSE, NativeKeyEvent.VK_CAPS_LOCK,
		    NativeKeyEvent.VK_ESCAPE, NativeKeyEvent.VK_UP,
		    NativeKeyEvent.VK_DOWN, NativeKeyEvent.VK_LEFT,
		    NativeKeyEvent.VK_RIGHT, NativeKeyEvent.VK_KP_UP,
		    NativeKeyEvent.VK_KP_DOWN, NativeKeyEvent.VK_KP_LEFT,
		    NativeKeyEvent.VK_KP_RIGHT, NativeKeyEvent.VK_DELETE,
		    NativeKeyEvent.VK_CLEAR, NativeKeyEvent.VK_NUM_LOCK,
		    NativeKeyEvent.VK_SCROLL_LOCK,
		    NativeKeyEvent.VK_PRINTSCREEN, NativeKeyEvent.VK_INSERT,
		    NativeKeyEvent.VK_HELP, NativeKeyEvent.VK_PAGE_UP,
		    NativeKeyEvent.VK_PAGE_DOWN, NativeKeyEvent.VK_HOME,
		    NativeKeyEvent.VK_END, NativeKeyEvent.VK_FINAL,
		    NativeKeyEvent.VK_CONVERT, NativeKeyEvent.VK_NONCONVERT,
		    NativeKeyEvent.VK_ACCEPT, NativeKeyEvent.VK_MODECHANGE,
		    NativeKeyEvent.VK_KANA, NativeKeyEvent.VK_KANJI,
		    NativeKeyEvent.VK_ALPHANUMERIC, NativeKeyEvent.VK_CUT,
		    NativeKeyEvent.VK_COPY, NativeKeyEvent.VK_PASTE,
		    NativeKeyEvent.VK_UNDO, NativeKeyEvent.VK_AGAIN,
		    NativeKeyEvent.VK_FIND, NativeKeyEvent.VK_PROPS,
		    NativeKeyEvent.VK_STOP, NativeKeyEvent.VK_COMPOSE,
		    NativeKeyEvent.VK_ALT_GRAPH, NativeKeyEvent.VK_BEGIN,
		    NativeKeyEvent.VK_UNDEFINED }));
    // There is no way to differentiate digits of normal keyboard from signs
    // associated to the same key. As digits are most of the time used on the
    // digit pad, we associate sound of digits of the normal keyboard to signs
    final List<Integer> signKeys = new ArrayList<Integer>(
	    Arrays.asList(new Integer[] { NativeKeyEvent.VK_0,
		    NativeKeyEvent.VK_1, NativeKeyEvent.VK_2,
		    NativeKeyEvent.VK_3, NativeKeyEvent.VK_4,
		    NativeKeyEvent.VK_5, NativeKeyEvent.VK_6,
		    NativeKeyEvent.VK_7, NativeKeyEvent.VK_8,
		    NativeKeyEvent.VK_9, NativeKeyEvent.VK_ENTER,
		    NativeKeyEvent.VK_SPACE, NativeKeyEvent.VK_COMMA,
		    NativeKeyEvent.VK_MINUS, NativeKeyEvent.VK_PERIOD,
		    NativeKeyEvent.VK_SLASH, NativeKeyEvent.VK_EQUALS,
		    NativeKeyEvent.VK_SEMICOLON,
		    NativeKeyEvent.VK_OPEN_BRACKET,
		    NativeKeyEvent.VK_BACK_SLASH,
		    NativeKeyEvent.VK_CLOSE_BRACKET,
		    NativeKeyEvent.VK_MULTIPLY, NativeKeyEvent.VK_ADD,
		    NativeKeyEvent.VK_SUBTRACT, NativeKeyEvent.VK_DECIMAL,
		    NativeKeyEvent.VK_DIVIDE, NativeKeyEvent.VK_QUOTE,
		    NativeKeyEvent.VK_BACK_QUOTE, NativeKeyEvent.VK_DEAD_GRAVE,
		    NativeKeyEvent.VK_DEAD_ACUTE,
		    NativeKeyEvent.VK_DEAD_CIRCUMFLEX,
		    NativeKeyEvent.VK_DEAD_TILDE,
		    NativeKeyEvent.VK_DEAD_MACRON,
		    NativeKeyEvent.VK_DEAD_BREVE,
		    NativeKeyEvent.VK_DEAD_ABOVEDOT,
		    NativeKeyEvent.VK_DEAD_DIAERESIS,
		    NativeKeyEvent.VK_DEAD_ABOVERING,
		    NativeKeyEvent.VK_DEAD_DOUBLEACUTE,
		    NativeKeyEvent.VK_DEAD_CARON,
		    NativeKeyEvent.VK_DEAD_CEDILLA,
		    NativeKeyEvent.VK_DEAD_OGONEK, NativeKeyEvent.VK_DEAD_IOTA,
		    NativeKeyEvent.VK_DEAD_VOICED_SOUND,
		    NativeKeyEvent.VK_DEAD_SEMIVOICED_SOUND,
		    NativeKeyEvent.VK_AMPERSAND, NativeKeyEvent.VK_ASTERISK,
		    NativeKeyEvent.VK_QUOTEDBL, NativeKeyEvent.VK_LESS,
		    NativeKeyEvent.VK_GREATER, NativeKeyEvent.VK_BRACELEFT,
		    NativeKeyEvent.VK_BRACERIGHT, NativeKeyEvent.VK_AT,
		    NativeKeyEvent.VK_COLON, NativeKeyEvent.VK_CIRCUMFLEX,
		    NativeKeyEvent.VK_DOLLAR, NativeKeyEvent.VK_EURO_SIGN,
		    NativeKeyEvent.VK_EXCLAMATION_MARK,
		    NativeKeyEvent.VK_INVERTED_EXCLAMATION_MARK,
		    NativeKeyEvent.VK_LEFT_PARENTHESIS,
		    NativeKeyEvent.VK_NUMBER_SIGN, NativeKeyEvent.VK_PLUS,
		    NativeKeyEvent.VK_RIGHT_PARENTHESIS,
		    NativeKeyEvent.VK_UNDERSCORE

	    }));
    final List<Integer> alphaKeys = new ArrayList<Integer>(
	    Arrays.asList(new Integer[] { NativeKeyEvent.VK_A,
		    NativeKeyEvent.VK_B, NativeKeyEvent.VK_C,
		    NativeKeyEvent.VK_D, NativeKeyEvent.VK_E,
		    NativeKeyEvent.VK_F, NativeKeyEvent.VK_G,
		    NativeKeyEvent.VK_H, NativeKeyEvent.VK_I,
		    NativeKeyEvent.VK_J, NativeKeyEvent.VK_K,
		    NativeKeyEvent.VK_L, NativeKeyEvent.VK_M,
		    NativeKeyEvent.VK_N, NativeKeyEvent.VK_O,
		    NativeKeyEvent.VK_P, NativeKeyEvent.VK_Q,
		    NativeKeyEvent.VK_R, NativeKeyEvent.VK_S,
		    NativeKeyEvent.VK_T, NativeKeyEvent.VK_U,
		    NativeKeyEvent.VK_V, NativeKeyEvent.VK_W,
		    NativeKeyEvent.VK_X, NativeKeyEvent.VK_Y,
		    NativeKeyEvent.VK_Z }));
    final List<Integer> numericalKeys = new ArrayList<Integer>(
	    Arrays.asList(new Integer[] { NativeKeyEvent.VK_NUMPAD0,
		    NativeKeyEvent.VK_NUMPAD1, NativeKeyEvent.VK_NUMPAD2,
		    NativeKeyEvent.VK_NUMPAD3, NativeKeyEvent.VK_NUMPAD4,
		    NativeKeyEvent.VK_NUMPAD5, NativeKeyEvent.VK_NUMPAD6,
		    NativeKeyEvent.VK_NUMPAD7, NativeKeyEvent.VK_NUMPAD8,
		    NativeKeyEvent.VK_NUMPAD9 }));

}
