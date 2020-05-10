package client.application.windows;

import java.awt.event.ActionListener;
import javax.swing.JButton;

/*
 * this class is used for common functions required across all views.
 */
public class WindowHelper {
	public static void removeActionListeners(JButton button) {
		for (ActionListener al : button.getActionListeners()) {
			button.removeActionListener(al);
		}
	}
}
