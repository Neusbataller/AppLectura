package Paquete1;

public class App {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Botones view=new Botones();
				new AppController(view);
				view.getFrame().setVisible(true);
				
			}
		});
	}
}
