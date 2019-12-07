// Programa hecho por Michel Lujano (A01636172)

import javax.swing.*;

public class MyVentana extends JFrame {

	public MyVentana() {

		super("Calculadora de numerotes");
	
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		MyPanelControles pc = new MyPanelControles();
		this.add(pc);
		this.pack();
		this.setVisible(true);
	} 


	public static void main(String[] args) {

		MyVentana ventana = new MyVentana();

	}
	

}
