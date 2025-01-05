package Utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MessageModals {
	
	public static void ErrorMessage(String text, String header) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
		
		JOptionPane.showMessageDialog(frame, text, header, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void WarningMessage(String text, String header) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
		
		JOptionPane.showMessageDialog(frame, text, header, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void InformationMessage(String text, String header) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
		
		JOptionPane.showMessageDialog(frame, text, header, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static int ConfirmMessage(String text, String header) {
        int response = JOptionPane.showConfirmDialog(
                null, 
                text, 
                header, 
                JOptionPane.YES_NO_OPTION
        );
        
        return response;
	}

}
