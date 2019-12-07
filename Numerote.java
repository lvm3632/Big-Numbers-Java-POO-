// Programa hecho por Michel Lujano (A01636172)

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Numerote {

	private byte[] numerote;
	private boolean signo;

	public Numerote() {

		this.signo = true;
		this.numerote = new byte[0];

	}

	public Numerote(String a) {

		boolean caracteres = false;
		boolean signo_correcto = false;
		boolean solo_un_cero = false;
		int contador_signo = 0;

		this.numerote = a.getBytes();

		// Si hay un solo cero, entonces el signo cambia a positivo
		if (this.numerote.length == 1 && this.numerote[0] == 48) {
			solo_un_cero = true;
			this.signo = true;

		}

		// En caso que solo hubiera un -0, entonces pasa a 0 con signo positivo
		if (this.numerote.length == 2 && this.numerote[1] == 48 && this.numerote[0] == 45) {
			solo_un_cero = true;
			this.signo = true;

		}

		// Cuenta los signos en el arreglo de bytes
		for (int i = 0; i < numerote.length; i++) {
			if (this.numerote[i] == 45) {
				contador_signo = contador_signo + 1;
			}
		}

		// Checa que no halla signos de menos repetidos
		if (this.numerote[0] == 45 && contador_signo == 1 && !solo_un_cero) {
			signo_correcto = true;
			this.signo = false;
		}

		// Detecta si el signo es positivo
		if (this.numerote[0] >= 48 || this.numerote[0] <= 57 && contador_signo == 0) {
			signo_correcto = true;
			this.signo = true;
		}

		// Revisa si hay carácteres, diferentes a números
		if (signo_correcto) {
			for (int i = 1; i < numerote.length; i++) {

				if (this.numerote[i] < 48 || this.numerote[i] > 57) {

					//System.out.println("hay caracteres");
					caracteres = true;
					break;

				} else {
					caracteres = false;
				}

			}

		}

		if (!solo_un_cero) {
			// En caso que hubiera un -0000 ... n, se hace inválido.

			int contador = 1;
			int pos = 0;

			if (this.numerote.length > 1) {
				if (this.numerote[1] == 48 && !this.signo) {

					for (int i = 1; i < numerote.length; i++) {

						if (this.numerote[i] == 48) {
							contador++;
						}

					}

					pos = contador - 1;

					if ((pos + 1) == this.numerote.length) {

						signo_correcto = false;
					}

				}
			}

		}

		
		if (signo_correcto && !caracteres) {
			String[] guardador = new String[this.numerote.length];

			if (this.numerote[0] != 45) {

				String string = new String(this.numerote);
				guardador = string.split("\\B");

				for (int i = this.numerote.length - 1; i >= 0; i--) {

					this.numerote[i] = Byte.parseByte(guardador[this.numerote.length - 1 - i]);

				}

				this.signo = true;
			} else {

				String string = new String(this.numerote);
				guardador = string.split("\\B");

				numerote = new byte[guardador.length];

				int positivo = Integer.parseInt((guardador[0]));
				positivo = positivo * -1;
				guardador[0] = String.valueOf(positivo);

				for (int i = this.numerote.length - 1; i >= 0; i--) {

					this.numerote[i] = Byte.parseByte(guardador[numerote.length - 1 - i]);

				}

				this.signo = false;
			}
		} else {
	
			JOptionPane.showMessageDialog(null, "Ingrese un número válido: NO esta permitido números como -0000 o 000 ó -0 ni carácteres");
			
		}

	}

	public Numerote suma(Numerote a) {

		int longitud_numero1 = this.numerote.length;
		int longitud_numero2 = a.numerote.length;
		byte[] copy_numerote, copy_a;

		Numerote suma = new Numerote();
		ArrayList sumaLista = new ArrayList();

		if (this.signo && a.signo) { // Caso 1 [+ y +]

			if (longitud_numero1 == longitud_numero2) { // Si num1 tiene la misma longitud que num2

				for (int i = 0; i < longitud_numero2; i++) {

					if ((this.numerote[i] + a.numerote[i]) > 9) {

						sumaLista.add((this.numerote[i] + a.numerote[i]) % 10);

						if (i < this.numerote.length - 1) {
							this.numerote[i + 1] = (byte) (this.numerote[i + 1] + 1);

						} else {
							sumaLista.add(1);
						}

					} else {
						sumaLista.add(this.numerote[i] + a.numerote[i]);
					}
				}

				for (int i = longitud_numero2 - 1; i < longitud_numero1 - 1; i++) {

					sumaLista.add(this.numerote[i + 1]);
				}

			}

			if (longitud_numero1 > longitud_numero2) { // Si num1 es mas grande que num2

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero1);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero1);

				for (int i = 0; i < longitud_numero1; i++) {

					if ((copy_numerote[i] + copy_a[i]) > 9) {

						sumaLista.add((copy_numerote[i] + copy_a[i]) % 10);

						if (i < copy_numerote.length - 1) {
							copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] + 1);

						}

					} else {
						sumaLista.add(copy_numerote[i] + copy_a[i]);
					}

				}

				if ((copy_numerote[longitud_numero1 - 1] + copy_a[longitud_numero1 - 1]) > 9) {
					if (copy_numerote[longitud_numero2 - 1] == 9) {
						sumaLista.add(1);
					}
				}

			}

			if (longitud_numero1 < longitud_numero2) { // Si num2 es mas grande que num1

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero2);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero2);

				for (int i = 0; i < longitud_numero2; i++) {

					if ((copy_numerote[i] + copy_a[i]) > 9) {

						sumaLista.add((copy_numerote[i] + copy_a[i]) % 10);

						if (i < copy_a.length - 1) {
							copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] + 1);

						}

					} else {
						sumaLista.add(copy_numerote[i] + copy_a[i]);
					}

				}

				if ((copy_numerote[longitud_numero1 - 1] + copy_a[longitud_numero1 - 1]) > 9) {

					if (copy_a[longitud_numero1 - 1] == 9) {
						sumaLista.add(1);
					}

				}

			}

			suma.signo = true;

			// Termina 1er caso

			/***********************************************************************************************/

			// Inicia 2do caso
		} else if (!this.signo && a.signo) { // Caso 2 [ - y + ]

			// n1 > n2
			if (longitud_numero1 > longitud_numero2) { // Si num1 es mayor con signo negativo, y num2 positivo con menor
														// longitud
				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero1);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero1);

				for (int i = 0; i < longitud_numero1; i++) {

					if (copy_a[i] > copy_numerote[i]) {

						copy_numerote[i] = (byte) (copy_numerote[i] + 10);
						copy_numerote[i] = (byte) (copy_numerote[i] - copy_a[i]);
						sumaLista.add(copy_numerote[i]);
						copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] - 1);

					}

					else {

						copy_numerote[i] = (byte) (copy_numerote[i] - copy_a[i]);
						sumaLista.add(copy_numerote[i]);

					}

				}

				byte cero = (byte) sumaLista.get(sumaLista.size() - 1);

				if (cero == 0) {

					sumaLista.remove(sumaLista.size() - 1);
				}

				suma.signo = false;
			}

			// n1 < n2
			if (longitud_numero1 < longitud_numero2) {
				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero2);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero2);
				suma.signo = true;

				for (int i = 0; i < copy_a.length; i++) {

					if (copy_a[i] < copy_numerote[i]) {

						copy_a[i] = (byte) (copy_a[i] + 10);
						copy_a[i] = (byte) (copy_a[i] - copy_numerote[i]);
						sumaLista.add(copy_a[i]);
						copy_a[i + 1] = (byte) (copy_a[i + 1] - 1);

					} else {

						copy_a[i] = (byte) (copy_a[i] - copy_numerote[i]);
						sumaLista.add(copy_a[i]);

					}

				}

			}

			// n1 = n2
			if (longitud_numero1 == longitud_numero2) {

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero1);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero1);

				if (copy_numerote[longitud_numero1 - 1] > copy_a[longitud_numero1 - 1]) {

					suma.signo = false;

				} else if (copy_numerote[longitud_numero1 - 1] < copy_a[longitud_numero1 - 1]) {

					suma.signo = true;
				}

				if (copy_numerote[longitud_numero1 - 1] == copy_a[longitud_numero1 - 1]) {

					for (int i = longitud_numero1 - 1; i > 0; i--) {

						if (copy_numerote[i - 1] > copy_a[i - 1]) {
							suma.signo = false;
							break;

						} else if (copy_numerote[i - 1] < copy_a[i - 1]) {
							suma.signo = true;

						}

					}
				}

				if (copy_numerote == copy_a) {
					suma.signo = true;
				}

				for (int i = 0; i < longitud_numero1; i++) {

					if (!suma.signo) {

						if (copy_a[i] > copy_numerote[i]) {

							copy_numerote[i] = (byte) (copy_numerote[i] + 10);
							copy_numerote[i] = (byte) (copy_numerote[i] - copy_a[i]);
							sumaLista.add(copy_numerote[i]);

							if (i < longitud_numero1 - 1) {

								copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] - 1);

							}

						} else {

							copy_numerote[i] = (byte) (copy_numerote[i] - copy_a[i]);
							sumaLista.add(copy_numerote[i]);

						}

					} else if (suma.signo) {

						if (copy_a[i] > copy_numerote[i]) {
							copy_a[i] = (byte) (copy_a[i] - copy_numerote[i]);
							sumaLista.add(copy_a[i]);

						} else {

							copy_a[i] = (byte) (copy_a[i] - copy_numerote[i]);
							sumaLista.add(copy_a[i]);

						}

					}

				}


			}

			// Termina 2do caso

			/***********************************************************************************************/

			// Inicia 3er caso

		} else if (this.signo && !a.signo) { // Caso 3 [+ y -]

			// n1 > n2
			if (longitud_numero1 > longitud_numero2) { // Si num1 es mayor con signo negativo, y num2 positivo con menor
														// longitud
				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero1);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero1);

				for (int i = 0; i < longitud_numero1; i++) {

					if (copy_a[i] > copy_numerote[i]) {

						copy_numerote[i] = (byte) (copy_numerote[i] + 10);
						copy_numerote[i] = (byte) (copy_numerote[i] - copy_a[i]);
						sumaLista.add(copy_numerote[i]);
						copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] - 1);

					} else {

						copy_numerote[i] = (byte) (copy_numerote[i] - copy_a[i]);
						sumaLista.add(copy_numerote[i]);

					}

				}

				byte cero = (byte) sumaLista.get(sumaLista.size() - 1);

				if (cero == 0) {

					sumaLista.remove(sumaLista.size() - 1);
				}

				suma.signo = true;
			}

			// n1 < n2
			if (longitud_numero1 < longitud_numero2) {
				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero2);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero2);

				for (int i = 0; i < copy_a.length; i++) {

					if (copy_a[i] < copy_numerote[i]) {

						copy_a[i] = (byte) (copy_a[i] + 10);
						copy_a[i] = (byte) (copy_a[i] - copy_numerote[i]);
						sumaLista.add(copy_a[i]);
						copy_a[i + 1] = (byte) (copy_a[i + 1] - 1);

					} else {

						copy_a[i] = (byte) (copy_a[i] - copy_numerote[i]);
						sumaLista.add(copy_a[i]);

					}

				}

				byte cero = (byte) sumaLista.get(sumaLista.size() - 1);

				if (cero == 0 && sumaLista.size() != 1) {

					sumaLista.remove(sumaLista.size() - 1);
				}
				suma.signo = false;
			}

			// n1 = n2
			if (longitud_numero1 == longitud_numero2) {

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero1);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero1);

				if (copy_numerote[longitud_numero1 - 1] > copy_a[longitud_numero1 - 1]) {

					suma.signo = true;

				} else if (copy_numerote[longitud_numero1 - 1] < copy_a[longitud_numero1 - 1]) {

					suma.signo = false;
				}

				if (copy_numerote[longitud_numero1 - 1] == copy_a[longitud_numero1 - 1]) {

					for (int i = longitud_numero1 - 1; i > 0; i--) {

						if (copy_numerote[i - 1] > copy_a[i - 1]) {
							suma.signo = true;
							break;

						} else if (copy_numerote[i - 1] < copy_a[i - 1]) {
							suma.signo = false;

						}

					}
				}

				if (copy_numerote == copy_a) {
					suma.signo = true;
				}

				for (int i = 0; i < longitud_numero1; i++) {

					if (suma.signo) {

						if (copy_a[i] > copy_numerote[i]) {

							copy_numerote[i] = (byte) (copy_numerote[i] + 10);
							copy_numerote[i] = (byte) (copy_numerote[i] - copy_a[i]);
							sumaLista.add(copy_numerote[i]);

							if (i < longitud_numero1 - 1) {
								copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] - 1);
							}

						} else {

							copy_numerote[i] = (byte) (copy_numerote[i] - copy_a[i]);
							sumaLista.add(copy_numerote[i]);

						}

					} else if (!suma.signo) {

						if (copy_a[i] < copy_numerote[i]) {

							copy_a[i] = (byte) (copy_a[i] + 10);
							copy_a[i] = (byte) (copy_a[i] - copy_numerote[i]);
							sumaLista.add(copy_a[i]);

							if (i < longitud_numero1 - 1) {

								copy_a[i + 1] = (byte) (copy_a[i + 1] - 1);

							}

						} else {

							copy_a[i] = (byte) (copy_a[i] - copy_numerote[i]);
							sumaLista.add(copy_a[i]);

						}

					}

				}
			
			}

			// Termina 3er caso

			/***********************************************************************************************/
			// Inicia 4to caso
		} else if (!this.signo && !a.signo) { // Caso 4 [- y -]

			if (longitud_numero1 == longitud_numero2) { // Si num1 tiene la misma longitud que num2

				for (int i = 0; i < longitud_numero2; i++) {

					if ((this.numerote[i] + a.numerote[i]) > 9) {

						sumaLista.add((this.numerote[i] + a.numerote[i]) % 10);

						if (i < this.numerote.length - 1) {
							this.numerote[i + 1] = (byte) (this.numerote[i + 1] + 1);

						} else {
							sumaLista.add(1);
						}

					} else {
						sumaLista.add(this.numerote[i] + a.numerote[i]);
					}
				}

				for (int i = longitud_numero2 - 1; i < longitud_numero1 - 1; i++) {

					sumaLista.add(this.numerote[i + 1]);
				}

			}

			if (longitud_numero1 > longitud_numero2) { // Si num1 es mas grande que num2

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero1);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero1);

				for (int i = 0; i < longitud_numero1; i++) {

					if ((copy_numerote[i] + copy_a[i]) > 9) {

						sumaLista.add((copy_numerote[i] + copy_a[i]) % 10);

						if (i < copy_numerote.length - 1) {
							copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] + 1);

						}

					} else {
						sumaLista.add(copy_numerote[i] + copy_a[i]);
					}

				}

				if ((copy_numerote[longitud_numero1 - 1] + copy_a[longitud_numero1 - 1]) > 9) {
					sumaLista.add(1);
				}

			}

			if (longitud_numero1 < longitud_numero2) { // Si num2 es mas grande que num1

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero2);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero2);

				for (int i = 0; i < longitud_numero2; i++) {

					if ((copy_numerote[i] + copy_a[i]) > 9) {

						sumaLista.add((copy_numerote[i] + copy_a[i]) % 10);

						if (i < copy_a.length - 1) {
							copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] + 1);

						}

					} else {
						sumaLista.add(copy_numerote[i] + copy_a[i]);
					}

				}
				if ((copy_numerote[longitud_numero1 - 1] + copy_a[longitud_numero1 - 1]) > 9) {
					sumaLista.add(1);
				}

			}

			suma.signo = false;
		}
		// Termina 4to caso

		/***********************************************************************************************/

		
		int primerDigito = 0, ceros = 0;
		for (int i = sumaLista.size() - 1; i > 0; i--) {
			if (!String.valueOf(sumaLista.get(i)).equalsIgnoreCase("0")) {
				primerDigito = i;
				
				break;
			}
			ceros++;
		}

		ceros = ceros + 1;
		

		if(ceros == sumaLista.size()) {
				int maximo = sumaLista.size()-1;
			for (int i = maximo; i > 0; i--) {
				sumaLista.remove(i);
			}
		}
		else if(primerDigito != 0) {
			
			for (int i = sumaLista.size()-1; i > primerDigito; i--) {
				
				sumaLista.remove(i);
			}
			
		}
		
		
		
		suma.numerote = new byte[sumaLista.size()];

		String[] guardador = new String[sumaLista.size()];

		for (int i = 0; i < sumaLista.size(); i++) {

			guardador[i] = String.valueOf(sumaLista.get(i));
			suma.numerote[i] = Byte.parseByte(guardador[i]);

		}

		return suma;
	}

	/***********************************************************************************************/
	public Numerote resta(Numerote a) {

		int longitud_numero1 = this.numerote.length;
		int longitud_numero2 = a.numerote.length;
		byte[] copy_numerote, copy_a;

		Numerote resta = new Numerote();
		ArrayList restaLista = new ArrayList();

		/***********************************************************************************************/

		if (this.signo && a.signo) { // Caso 1 [+ y +]

			if (longitud_numero1 == longitud_numero2) { // n1 == n2

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero1);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero1);

				if (copy_numerote[longitud_numero1 - 1] > copy_a[longitud_numero1 - 1]) {

					resta.signo = true;

				} else if (copy_numerote[longitud_numero1 - 1] < copy_a[longitud_numero1 - 1]) {

					resta.signo = false;
				}

				if (copy_numerote[longitud_numero1 - 1] == copy_a[longitud_numero1 - 1]) {

					for (int i = longitud_numero1 - 1; i > 0; i--) {

						if (copy_numerote[i - 1] > copy_a[i - 1]) {
							resta.signo = true;
							break;

						} else if (copy_numerote[i - 1] < copy_a[i - 1]) {
							resta.signo = false;

						}

					}
				}

				for (int i = 0; i < longitud_numero2; i++) {

					if (resta.signo) {

						if (copy_numerote[i] - copy_a[i] < 0) {

							copy_numerote[i] = (byte) (copy_numerote[i] + 10);
							restaLista.add(copy_numerote[i] - copy_a[i]);
							copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] - 1);
						} else {
							restaLista.add(copy_numerote[i] - copy_a[i]);
						}

					} else if (!resta.signo) {

						if (copy_a[i] - copy_numerote[i] < 0) {

							copy_a[i] = (byte) (copy_a[i] + 10);
							restaLista.add(copy_a[i] - copy_numerote[i]);
							copy_a[i + 1] = (byte) (copy_a[i + 1] - 1);
						} else {
							restaLista.add(copy_a[i] - copy_numerote[i]);
						}

					}

				}

			}

			if (longitud_numero1 > longitud_numero2) { // n1 > n2

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero1);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero1);

				resta.signo = true;

				for (int i = 0; i < longitud_numero1; i++) {

					if (copy_numerote[i] - copy_a[i] < 0) {

						copy_numerote[i] = (byte) (copy_numerote[i] + 10);
						restaLista.add(copy_numerote[i] - copy_a[i]);
						copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] - 1);
					} else {
						restaLista.add(copy_numerote[i] - copy_a[i]);
					}
				}

			}

			if (longitud_numero1 < longitud_numero2) { // n1 < n2

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero2);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero2);

				resta.signo = false;

				for (int i = 0; i < copy_a.length; i++) {

					if (copy_a[i] - copy_numerote[i] < 0) {

						copy_a[i] = (byte) (copy_a[i] + 10);
						restaLista.add(copy_a[i] - copy_numerote[i]);
						copy_a[i + 1] = (byte) (copy_a[i + 1] - 1);

					} else {
						restaLista.add(copy_a[i] - copy_numerote[i]);
					}
				}

			}

			/***********************************************************************************************/

		} else if (!this.signo && a.signo) { // Caso 2 [- y +]

			// n1 == n2
			if (longitud_numero1 == longitud_numero2) { // n1 == n2

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero1);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero1);

				for (int i = 0; i < longitud_numero2; i++) {

					if ((this.numerote[i] + a.numerote[i]) > 9) {

						restaLista.add((this.numerote[i] + a.numerote[i]) % 10);

						if (i < this.numerote.length - 1) {
							this.numerote[i + 1] = (byte) (this.numerote[i + 1] + 1);

						} else {
							restaLista.add(1);
						}

					} else {
						restaLista.add(this.numerote[i] + a.numerote[i]);
					}
				}

				for (int i = longitud_numero2 - 1; i < longitud_numero1 - 1; i++) {

					restaLista.add(this.numerote[i + 1]);
				}

			}

			if (longitud_numero1 > longitud_numero2) { // n1 > n2

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero1);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero1);

				for (int i = 0; i < longitud_numero1; i++) {

					if ((copy_numerote[i] + copy_a[i]) > 9) {

						restaLista.add((copy_numerote[i] + copy_a[i]) % 10);

						if (i < copy_numerote.length - 1) {
							copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] + 1);

						}

					} else {
						restaLista.add(copy_numerote[i] + copy_a[i]);
					}

				}

				if ((copy_numerote[longitud_numero1 - 1] + copy_a[longitud_numero1 - 1]) > 9) {
					restaLista.add(1);
				}

			}

			if (longitud_numero1 < longitud_numero2) { // n1 < n2

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero2);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero2);

				for (int i = 0; i < longitud_numero2; i++) {

					if ((copy_numerote[i] + copy_a[i]) > 9) {

						restaLista.add((copy_numerote[i] + copy_a[i]) % 10);

						if (i < copy_a.length - 1) {
							copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] + 1);

						}

					} else {
						restaLista.add(copy_numerote[i] + copy_a[i]);
					}

				}
				if ((copy_numerote[longitud_numero1 - 1] + copy_a[longitud_numero1 - 1]) > 9) {
					if (copy_a[longitud_numero1 - 1] == 9) {
						restaLista.add(1);
					}
				}

			}

			resta.signo = false;
			// Termina 2do caso

			/***********************************************************************************************/

			// Inicia 3do caso

		} else if (this.signo && !a.signo) { // Caso 3 [+ y -]

			if (longitud_numero1 == longitud_numero2) { // n1 == n2

				for (int i = 0; i < longitud_numero2; i++) {

					if ((this.numerote[i] + a.numerote[i]) > 9) {

						restaLista.add((this.numerote[i] + a.numerote[i]) % 10);

						if (i < this.numerote.length - 1) {
							this.numerote[i + 1] = (byte) (this.numerote[i + 1] + 1);

						} else {
							restaLista.add(1);
						}

					} else {
						restaLista.add(this.numerote[i] + a.numerote[i]);
					}
				}

				for (int i = longitud_numero2 - 1; i < longitud_numero1 - 1; i++) {

					restaLista.add(this.numerote[i + 1]);
				}

			}

			if (longitud_numero1 > longitud_numero2) { // n1 > n2

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero1);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero1);

				for (int i = 0; i < longitud_numero1; i++) {

					if ((copy_numerote[i] + copy_a[i]) > 9) {

						restaLista.add((copy_numerote[i] + copy_a[i]) % 10);

						if (i < longitud_numero1 - 1) {
							copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] + 1);

						}

					} else {
						restaLista.add(copy_numerote[i] + copy_a[i]);
					}

				}

				if ((copy_numerote[longitud_numero1 - 1] + copy_a[longitud_numero1 - 1]) > 9) {

					restaLista.add(1);

				}

			}

			if (longitud_numero1 < longitud_numero2) { // n1 < n2

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero2);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero2);

				for (int i = 0; i < longitud_numero2; i++) {

					if ((copy_numerote[i] + copy_a[i]) > 9) {

						restaLista.add((copy_numerote[i] + copy_a[i]) % 10);

						if (i < copy_a.length - 1) {
							copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] + 1);

						}

					} else {
						restaLista.add(copy_numerote[i] + copy_a[i]);
					}

				}
				if ((copy_numerote[longitud_numero1 - 1] + copy_a[longitud_numero1 - 1]) > 9) {

					if (copy_a[longitud_numero1 - 1] == 9) {
						restaLista.add(1);
					}
				}

			}

			resta.signo = true;

			// Termina 3do caso
			/***********************************************************************************************/

			// Falta n1> n2 y n2 < n1
			// Inicia 4er caso
		} else if (!this.signo && !a.signo) {

			// inicio n1 == n2
			if (longitud_numero1 == longitud_numero2) {

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero1);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero1);

				if (copy_numerote[longitud_numero1 - 1] > copy_a[longitud_numero1 - 1]) {

					resta.signo = false;

				} else if (copy_numerote[longitud_numero1 - 1] < copy_a[longitud_numero1 - 1]) {

					resta.signo = true;
				}

				if (copy_numerote[longitud_numero1 - 1] == copy_a[longitud_numero1 - 1]) {

					for (int i = longitud_numero1 - 1; i > 0; i--) {

						if (copy_numerote[i - 1] > copy_a[i - 1]) {
							resta.signo = false;
							break;

						} else if (copy_numerote[i - 1] < copy_a[i - 1]) {
							resta.signo = true;

						}

					}
				}

				if (copy_numerote == copy_a) {
					resta.signo = true;
				}

				for (int i = 0; i < longitud_numero1; i++) {

					if (!resta.signo) {

						if (copy_a[i] > copy_numerote[i]) {

							copy_numerote[i] = (byte) (copy_numerote[i] + 10);
							copy_numerote[i] = (byte) (copy_numerote[i] - copy_a[i]);
							restaLista.add(copy_numerote[i]);

							if (i < longitud_numero1 - 1) {

								copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] - 1);

							}

						} else {

							copy_numerote[i] = (byte) (copy_numerote[i] - copy_a[i]);
							restaLista.add(copy_numerote[i]);

						}

					} else if (resta.signo) {

						if (copy_numerote[i] > copy_a[i]) {

							// 90 copy_a
							// 45 copy_num

							copy_a[i] = (byte) (copy_a[i] + 10);
							restaLista.add(copy_a[i] - copy_numerote[i]);

							if (i < copy_a[longitud_numero1 - 1]) {

								copy_a[i + 1] = (byte) (copy_a[i + 1] - 1);
							}

						} else {

							copy_a[i] = (byte) (copy_a[i] - copy_numerote[i]);
							restaLista.add(copy_a[i]);

						}

					}

				}

			}
			if (longitud_numero1 > longitud_numero2) { // n1 > n2
				// longitud
				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero1);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero1);

				for (int i = 0; i < longitud_numero1; i++) {

					if (copy_a[i] > copy_numerote[i]) {

						copy_numerote[i] = (byte) (copy_numerote[i] + 10);
						copy_numerote[i] = (byte) (copy_numerote[i] - copy_a[i]);
						restaLista.add(copy_numerote[i]);
						copy_numerote[i + 1] = (byte) (copy_numerote[i + 1] - 1);

					}

					else {

						copy_numerote[i] = (byte) (copy_numerote[i] - copy_a[i]);
						restaLista.add(copy_numerote[i]);

					}

				}

				resta.signo = false;
			}

			if (longitud_numero1 < longitud_numero2) {

				copy_numerote = Arrays.copyOf(this.numerote, longitud_numero2);
				copy_a = Arrays.copyOf(a.numerote, longitud_numero2);

				for (int i = 0; i < copy_a.length; i++) {

					if (copy_a[i] < copy_numerote[i]) {

						copy_a[i] = (byte) (copy_a[i] + 10);
						copy_a[i] = (byte) (copy_a[i] - copy_numerote[i]);
						restaLista.add(copy_a[i]);
						copy_a[i + 1] = (byte) (copy_a[i + 1] - 1);

					} else {

						copy_a[i] = (byte) (copy_a[i] - copy_numerote[i]);
						restaLista.add(copy_a[i]);

					}

				}

				resta.signo = true;
			}

			// fin n1 > n2

			// falta n1 < n2

		}

		// Termina 4er caso

		/***********************************************************************************************/

		/***********************************************************************************************/

		int primerDigito = 0, ceros = 0;
		for (int i = restaLista.size() - 1; i > 0; i--) {
			if (!String.valueOf(restaLista.get(i)).equalsIgnoreCase("0")) {
				primerDigito = i;
				
				break;
			}
			ceros++;
		}

		ceros = ceros + 1;
		

		if(ceros == restaLista.size()) {
				int maximo = restaLista.size()-1;
			for (int i = maximo; i > 0; i--) {
				restaLista.remove(i);
			}
		}
		else if(primerDigito != 0) {
			
			for (int i = restaLista.size()-1; i > primerDigito; i--) {
				
				restaLista.remove(i);
			}
			
		}
		
		
		String[] guardador = new String[restaLista.size()];
		resta.numerote = new byte[restaLista.size()];
	

		for (int i = 0; i < restaLista.size(); i++) {

			guardador[i] = String.valueOf(restaLista.get(i));

			resta.numerote[i] = Byte.parseByte(guardador[i]);

		}


		return resta;
	}

	public Numerote multiplica(Numerote a) {

		ArrayList multiLista1 = new ArrayList();
		Numerote multi = new Numerote();

		Numerote suma1 = new Numerote();
		Numerote suma2 = new Numerote();
		Numerote suma3 = new Numerote();
		Numerote total = new Numerote();

		int contador = 0;
		int acarreo1 = 0;
		boolean ultimo_arreglo = false;
		
		ArrayList sumaLista1 = new ArrayList();
		ArrayList sumaLista2 = new ArrayList();
		ArrayList sumaLista3 = new ArrayList();

		// Sirve para caso n2>n1 y n1 == n2 en longitud

		for (int i = 0; i < a.numerote.length; i++) {
			acarreo1 = 0;

			for (int j = 0; j < numerote.length; j++) {

				if ((a.numerote[i] * this.numerote[j]) + acarreo1 > 9) { // Cuando la multiplicación de un digito con
																			// otro excede
					// de 9 + su acarreo

					if (i == 0) { // Se agrega el número como tal, sin agregar ceros

						
						// Add to sumaLista2

						sumaLista2.add((byte) (((a.numerote[i] * this.numerote[j]) + acarreo1) % 10));

						acarreo1 = ((((this.numerote[j] * a.numerote[i]) + acarreo1)
								- (((this.numerote[j] * a.numerote[i]) + acarreo1) % 10)) / 10);

						if (j == numerote.length - 1) {

							sumaLista2.add((byte) acarreo1);
							acarreo1 = 0;

						}

						// Solo una vez entraría el ciclo y lo guardaría en sumaLista2
						suma2.numerote = new byte[sumaLista2.size()];

					} else if (i > 0) { // Si I es mayor que cero, entonces se agregan los ceros, la I representa el
										// numeros de ceros.

						// If para saber en que array meto los números con jota
						if (i % 2 == 0) {

							if (i == a.numerote.length - 1) {

								// For para agregar los ceros al ArrayList, se agregan en la posicion 0


								// For para agregar los ceros al ArrayList, se agregan en la posicion 0

							
								// Add to sumaLista1 (impar)
							
								sumaLista3.add((byte) (((a.numerote[i] * this.numerote[j]) + acarreo1) % 10));

								acarreo1 = ((((this.numerote[j] * a.numerote[i]) + acarreo1)
										- (((this.numerote[j] * a.numerote[i]) + acarreo1) % 10)) / 10);

		

								if (j == numerote.length - 1) {

									sumaLista3.add((byte) acarreo1);
									acarreo1 = 0;
									for (int k = 0; k < i; k++) {
										sumaLista3.add(0, 0);
									}

								}

								// Fin del for
							
								// Add to sumaLista2 (pares)
								suma3.numerote = new byte[sumaLista3.size()];

								ultimo_arreglo = true;

							} else {

								// For para agregar los ceros al ArrayList, se agregan en la posicion 0
								// Add to sumaLista1 (impar)
								

								sumaLista2.add((byte) (((a.numerote[i] * this.numerote[j]) + acarreo1) % 10));

								acarreo1 = ((((this.numerote[j] * a.numerote[i]) + acarreo1)
										- (((this.numerote[j] * a.numerote[i]) + acarreo1) % 10)) / 10);

								if (j == numerote.length - 1) {

									sumaLista2.add((byte) acarreo1);
									acarreo1 = 0;
									for (int k = 0; k < i; k++) {
										sumaLista2.add(0, 0);
									}

								}

								// Fin del for
								// Add to sumaLista2 (pares)
							}

							// i % 2 = 1 y i>0 a partir de i >= 1
						} else if (i % 2 == 1) {

					
							// For para agregar los ceros al ArrayList, se agregan en la posicion 0	
							// Add to sumaLista1 (impar)
						

							sumaLista1.add((byte) (((a.numerote[i] * this.numerote[j]) + acarreo1) % 10));

							acarreo1 = ((((this.numerote[j] * a.numerote[i]) + acarreo1)
									- (((this.numerote[j] * a.numerote[i]) + acarreo1) % 10)) / 10);

						
							if (j == numerote.length - 1) {

								sumaLista1.add((byte) acarreo1);
								acarreo1 = 0;
								for (int k = 0; k < i; k++) {
									sumaLista1.add(0, 0);
								}

							}
						}

					}

				} else { // Si la multiplicación de un digito con otro digito fue menor a 9, recuerda J
							// es numerote
					// Cuando la multiplicación de un digito con
					// otro excede
					// de 9 + su acarreo

					if (i == 0) { // Se agrega el número como tal, sin agregar ceros

						// Add to sumaLista2

						sumaLista2.add((byte) (((a.numerote[i] * this.numerote[j]) + acarreo1) % 10));

						acarreo1 = ((((this.numerote[j] * a.numerote[i]) + acarreo1)
								- (((this.numerote[j] * a.numerote[i]) + acarreo1) % 10)) / 10);

						if (j == numerote.length - 1) {

							if (acarreo1 != 0) {
								sumaLista2.add((byte) acarreo1);
								acarreo1 = 0;
							}

						}

						// Solo una vez entraría el ciclo y lo guardaría en sumaLista2
						suma2.numerote = new byte[sumaLista2.size()];

					} else if (i > 0) { // Si I es mayor que cero, entonces se agregan los ceros, la I representa el
						// numeros de ceros.

						// If para saber en que array meto los números con jota
						if (i % 2 == 0) {

							if (i == a.numerote.length - 1) {

								// For para agregar los ceros al ArrayList, se agregan en la posicion 0

								// For para agregar los ceros al ArrayList, se agregan en la posicion 0

							
								// Add to sumaLista1 (impar)
						

								sumaLista3.add((byte) (((a.numerote[i] * this.numerote[j]) + acarreo1) % 10));

								acarreo1 = ((((this.numerote[j] * a.numerote[i]) + acarreo1)

										- (((this.numerote[j] * a.numerote[i]) + acarreo1) % 10)) / 10);

						

								if (j == numerote.length - 1) {

									if (acarreo1 != 0) {
										sumaLista3.add((byte) acarreo1);
										acarreo1 = 0;
									}

									for (int k = 0; k < i; k++) {
										sumaLista3.add(0, 0);
									}

								}

								// Fin del for

								// Add to sumaLista2 (pares)
								suma3.numerote = new byte[sumaLista3.size()];

							
								ultimo_arreglo = true;

							} else {


								sumaLista2.add((byte) (((a.numerote[i] * this.numerote[j]) + acarreo1) % 10));

								acarreo1 = ((((this.numerote[j] * a.numerote[i]) + acarreo1)
										- (((this.numerote[j] * a.numerote[i]) + acarreo1) % 10)) / 10);

							

								if (j == numerote.length - 1) {

									if (acarreo1 != 0) {

										sumaLista2.add((byte) acarreo1);
										acarreo1 = 0;
									}

									for (int k = 0; k < i; k++) {
										sumaLista2.add(0, 0);
									}

								}

								// Fin del for
					
								// Add to sumaLista2 (pares)
							}

							// i % 2 = 1 y i>0 a partir de i >= 1
						} else if (i % 2 == 1) {


							sumaLista1.add((byte) (((a.numerote[i] * this.numerote[j]) + acarreo1) % 10));

							acarreo1 = ((((this.numerote[j] * a.numerote[i]) + acarreo1)
									- (((this.numerote[j] * a.numerote[i]) + acarreo1) % 10)) / 10);

			

							if (j == numerote.length - 1) {

								if (acarreo1 != 0) {

									sumaLista1.add((byte) acarreo1);
									acarreo1 = 0;

								}

								for (int k = 0; k < i; k++) {
									sumaLista1.add(0, 0);
								}

							}
						}

					}

				}

				// Fin

			}

			// Cuando llega este contador a 1; se suman los dos números guardados en los
			// arreglos


			if (contador == 1 && !ultimo_arreglo) {

				// La suma de los dos números

				String[] guardador_suma1 = new String[sumaLista1.size()];
				String[] guardador_suma2 = new String[sumaLista2.size()];

				suma1.numerote = new byte[sumaLista1.size()];
				suma2.numerote = new byte[sumaLista2.size()];
				// total.numerote = new byte[12];

				for (int k = 0; k < sumaLista1.size(); k++) {

					guardador_suma1[k] = String.valueOf(sumaLista1.get(k));
					suma1.numerote[k] = Byte.parseByte(guardador_suma1[k]);
				}
				for (int k = 0; k < sumaLista2.size(); k++) {

					guardador_suma2[k] = String.valueOf(sumaLista2.get(k));
					suma2.numerote[k] = Byte.parseByte(guardador_suma2[k]);
				}

				// La asignación de la suma de estos dos números a Numerote multi

				if (i > 1) {

					// total = suma2+suma1
					total = suma1.suma(suma2);

					multi = multi.suma(total);

					// multi = multi.suma(total);
				} else {
					multi = suma2.suma(suma1);
				}

				// Se limpian las listas
				sumaLista1.clear();
				sumaLista2.clear();
				// Se inicializa el contador
				contador = -1;

			}

			if (ultimo_arreglo) { // Recordar que I es a el numero de abajo

				// Se suma el Numerote de Multi en caso de que nuestro arreglo sea un número
				String[] guardador_suma3 = new String[sumaLista3.size()];
				suma3.numerote = new byte[sumaLista3.size()];
				for (int k = 0; k < sumaLista3.size(); k++) {

					guardador_suma3[k] = String.valueOf(sumaLista3.get(k));
					suma3.numerote[k] = Byte.parseByte(guardador_suma3[k]);
				}
			
				// impar en n1>n2 o n2 < n1
				// Con sumaLista1 porque siempre sera impar

				multi = multi.suma(suma3);
				sumaLista3.clear();
			}

			contador++;
		}

		if (a.numerote.length == 1) {

			for (int k = 0; k < sumaLista2.size(); k++) {

				suma2.numerote[k] = (byte) sumaLista2.get(k);
			}

			multi = suma2;

		}

		if (!this.signo && !a.signo) {
			multi.signo = true;
		} else if (!this.signo) {
			multi.signo = false;
		} else if (!a.signo) {
			multi.signo = false;
		}

	
		return multi;
	}

	public String toString() {

		String[] guardador = new String[this.numerote.length];
		

		
		for (int i = 0; i < this.numerote.length ; i++) {
		
			guardador[i] = String.valueOf(this.numerote[this.numerote.length-i-1]);
			
		}
		
		 String todojunto = Arrays.stream(guardador)
		            .collect(Collectors.joining());
		
		
		if (!this.signo) {

			return "-" + todojunto;

		}

		return todojunto;
	}

	public static void ejecutaArchivo(String entrada, String salida) {

		try {

			StringTokenizer st;
			String linea, num1, num2, opc;
			boolean numero1, numero2;
			
			PrintWriter pw = new PrintWriter(new FileWriter(salida));
			BufferedReader bf = new BufferedReader(new FileReader(entrada));

			while ((linea = bf.readLine()) != null) {
				
				st = new StringTokenizer(linea);
				num1 = st.nextToken(",");
				num2 = st.nextToken(",");
				opc = st.nextToken();
				
				numero1 = (num1 != null && num1.matches("-?\\d+(\\d+)?"));
				numero2 = (num2 != null && num2.matches("-?\\d+(\\d+)?"));

				
				if (opc.equals("s")) {
					if (numero1 && numero2) {

						Numerote a = new Numerote(num1);
						pw.println(a.suma(new Numerote(num2)));
					} else {
						pw.println("Datos inválidos");
					}

				} else if (opc.equals("r")) {
					if (numero1 && numero2) {
						Numerote a = new Numerote(num1);
						pw.println(a.resta(new Numerote(num2)));

					} else {
						pw.println("Datos inválidos");
					}
				} else if (opc.equals("m")) {
					if (numero1 && numero2) {
						Numerote a = new Numerote(num1);
						pw.println(a.multiplica(new Numerote(num2)));
					} else {
						pw.println("Datos inválidos");
					}
				} else {
					System.out.print("La operación es incorrecta");
				}
			}

			pw.close();
			bf.close();

		} catch (FileNotFoundException ex) {
			System.out.println("No se encontró el archivo");
		} catch (IOException ex) {
			System.out.println("Ocurrió un error con el archivo");
		}

	}

}