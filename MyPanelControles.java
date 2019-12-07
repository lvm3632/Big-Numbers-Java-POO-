// Programa hecho por Michel Lujano (A01636172)

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;


import javax.swing.*;
import javax.swing.event.*;

public class MyPanelControles extends JPanel implements ActionListener, ChangeListener, MouseListener {
	private JTextField tfNumero1, tfNumero2, tfResultado, tfAbrir, tfGuardar;
	private JButton btnGuardar, btnAbrir, btnResultado, btnNombre, btnGenerarResultados;
	private JLabel lblNum1, lblNum2, lblAbrirArchivo, lblGuardarArchivo, lblEjecutarOperaciones;
	private JRadioButton rbMultiplica, rbSuma, rbResta;
	private JFileChooser fc;
	private String path, filename = "entrada.txt", path1 = "salida.txt", filename1;
	boolean guardar = false, abrir = false;

	public MyPanelControles() {
		super();

		this.setPreferredSize(new Dimension(400, 650));

		this.tfNumero1 = new JTextField(30);
		this.tfNumero1.setPreferredSize(new Dimension(400, 32));

		this.tfNumero2 = new JTextField(30);
		this.tfNumero2.setPreferredSize(new Dimension(400, 32));

		this.tfResultado = new JTextField(30);
		this.tfResultado.setPreferredSize(new Dimension(400, 100));

		this.tfAbrir = new JTextField(30);
		this.tfAbrir.setPreferredSize(new Dimension(400, 30));

		this.tfGuardar = new JTextField(30);
		this.tfGuardar.setPreferredSize(new Dimension(400, 30));

		this.lblNum1 = new JLabel("Número 1: ");
		this.lblNum2 = new JLabel("Número 2: ");

		this.lblAbrirArchivo = new JLabel("Busca archivo: entrada.txt");
		this.lblGuardarArchivo = new JLabel("Guardar Archivo salida.txt");
		this.lblEjecutarOperaciones = new JLabel("Ejecutar operaciones");

		this.rbMultiplica = new JRadioButton("Multiplica", true);
		this.rbSuma = new JRadioButton("Suma");
		this.rbResta = new JRadioButton("Resta");

		ButtonGroup bg = new ButtonGroup();

		bg.add(this.rbMultiplica);
		bg.add(this.rbSuma);
		bg.add(this.rbResta);

		this.add(this.lblNum1);
		this.add(this.tfNumero1);
		this.add(this.lblNum2);
		this.add(this.tfNumero2);

		this.add(this.rbMultiplica);
		this.add(this.rbSuma);
		this.add(this.rbResta);

		this.btnResultado = new JButton("Ver resultado");
		this.btnResultado.addActionListener(this);

		this.add(this.tfResultado);

		this.btnResultado.setPreferredSize(new Dimension(250, 60));
		this.add(this.btnResultado);

		this.add(this.lblAbrirArchivo);
		this.add(this.tfAbrir);
		this.btnAbrir = new JButton("Buscar archivo: entrada.txt");
		this.btnAbrir.addActionListener(this);
		this.btnAbrir.setPreferredSize(new Dimension(325, 40));
		this.add(this.btnAbrir);

		this.add(this.lblGuardarArchivo);
		this.add(this.tfGuardar);
		this.btnGuardar = new JButton("Guardar archivo: salida.txt");
		this.btnGuardar.addActionListener(this);
		this.btnGuardar.setPreferredSize(new Dimension(325, 40));
		this.add(this.btnGuardar);

		this.add(this.lblEjecutarOperaciones);
		this.btnGenerarResultados = new JButton("Generar resultados en salida.txt");

		this.btnGenerarResultados.setPreferredSize(new Dimension(280, 100));
		this.btnGenerarResultados.addActionListener(this);
		this.add(this.btnGenerarResultados);

		this.fc = new JFileChooser();
	}

	@Override
	public void actionPerformed(ActionEvent evt) {

		Numerote archivo;

		if (evt.getSource() == this.btnResultado) {

			Numerote numero1, numero2, total;

			if (this.rbMultiplica.isSelected()) {

				numero1 = new Numerote(this.tfNumero1.getText());
				numero2 = new Numerote(this.tfNumero2.getText());

				total = numero1.multiplica(numero2);

				this.tfResultado.setText(total.toString());

			} else if (this.rbSuma.isSelected()) {

				numero1 = new Numerote(this.tfNumero1.getText());
				numero2 = new Numerote(this.tfNumero2.getText());

				total = numero1.suma(numero2);

				this.tfResultado.setText(total.toString());

			} else if (this.rbResta.isSelected()) {

				numero1 = new Numerote(this.tfNumero1.getText());
				numero2 = new Numerote(this.tfNumero2.getText());

				total = numero1.resta(numero2);

				this.tfResultado.setText(total.toString());

			}

		} else if (evt.getSource() == this.btnAbrir) {

			int opcion = this.fc.showSaveDialog(this);

			if (opcion == JFileChooser.APPROVE_OPTION) {

				path = fc.getSelectedFile().getAbsolutePath();
				filename = fc.getSelectedFile().getName();
				this.tfAbrir.setText(path);
				path1 = path.replace('\\', '/');
				abrir = true;

			}

		} else if (evt.getSource() == this.btnGuardar) {

			fc.setSelectedFile(new File("salida.txt"));
			path1 = fc.getSelectedFile().getAbsolutePath();
			filename1 = fc.getSelectedFile().getName();

			fc.showSaveDialog(null);
			this.tfGuardar.setText(path1);
			path1 = path1.replace('\\', '/');

			guardar = true;

		} else if (evt.getSource() == this.btnGenerarResultados) {

			archivo = new Numerote();

			if (guardar && abrir) {
				archivo.ejecutaArchivo(path, path1);
				JOptionPane.showMessageDialog(null, "Archivo generado en " + path1);

			} else {
				JOptionPane.showMessageDialog(null, "Necesitas abrir o guardar el archivo primero");
			}

		}

	}

	public void stateChanged(ChangeEvent arg0) {

	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

}
