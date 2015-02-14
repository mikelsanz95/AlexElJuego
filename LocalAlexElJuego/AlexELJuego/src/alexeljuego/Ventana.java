package alexeljuego;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


@SuppressWarnings("serial")
public class Ventana extends JFrame {
	 @SuppressWarnings({ "unused", "static-access" })
		private Ventana()
	    {
	        // Sets the title for this frame.
	        this.setTitle("ALEXELJUEGO");
	        
	        // Sets size of the frame.
	        if(true) // Full screen mode
	        {
	            // Disables decorations for this frame.
	            this.setUndecorated(true);
	            // Puts the frame to full screen.
	            this.setExtendedState(this.MAXIMIZED_BOTH);
	        }
	        else // Ventana mode
	        {
	            // Size of the frame.
	            this.setSize(1024, 768);
	            // Puts frame to center of the screen.
	            this.setLocationRelativeTo(null);
	            // So that frame cannot be resizable by the user.
	            this.setResizable(false);
	        }
	        
	        // Exit the application when user close frame.
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        // Creates the instance of the Framework.java that extends the Canvas.java and puts it on the frame.
	        this.setContentPane(new Framework());
	        
	        this.setVisible(true);
	    }

	    public static void main(String[] args)
	    {
	        // Use the event dispatch thread to build the UI for thread-safety.
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                new Ventana();
	            }
	        });
	    }

}
