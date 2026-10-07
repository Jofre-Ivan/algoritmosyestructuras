package ejercicios.guia1;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * necesito implementar en java un algoritmo que cuente
 *  cuantas veces aparece un determinado valor dentro de un vector,
 *  hacelo con un for que tenga un contador, 
 * y el vector se genera de forma aleatoria 
 * e implementar el analisis de su complejidad
 * Complejidad del conteo: tiempo O(n) y espacio adicional O(1).
 */
public class ej5 {

	private static final int LONGITUD_VECTOR = 20;
	private static final int VALOR_MINIMO = 0;
	private static final int VALOR_MAXIMO = 9;

	/**
	 * Genera un vector de enteros aleatorios dentro del rango especificado.
	 *
	 * @param longitud cantidad de elementos que tendrá el vector
	 * @param minimo valor mínimo posible, incluido
	 * @param maximo valor máximo posible, incluido
	 * @return vector con valores aleatorios
	 * @throws IllegalArgumentException si la longitud es negativa o el rango es inválido
	 */
	public static int[] generarVectorAleatorio(int longitud, int minimo, int maximo) {
		if (longitud < 0) {
			throw new IllegalArgumentException("La longitud no puede ser negativa.");
		}
		if (minimo > maximo) {
			throw new IllegalArgumentException("El valor mínimo no puede superar al máximo.");
		}

		int[] vector = new int[longitud];
		Random generador = new Random();
		int cantidadValores = maximo - minimo + 1;

		for (int i = 0; i < vector.length; i++) {
			vector[i] = generador.nextInt(cantidadValores) + minimo;
		}

		return vector;
	}

	/**
	 * Cuenta las posiciones que contienen el valor buscado.
	 *
	 * @param vector vector que se recorrerá
	 * @param valorBuscado valor cuya frecuencia se desea calcular
	 * @return cantidad de veces que aparece el valor
	 * @throws IllegalArgumentException si el vector es null
	 */
	public static int contarApariciones(int[] vector, int valorBuscado) {
		if (vector == null) {
			throw new IllegalArgumentException("El vector no puede ser null.");
		}

		int contador = 0;
		for (int i = 0; i < vector.length; i++) {
			if (vector[i] == valorBuscado) {
				contador++;
			}
		}

		return contador;
	}

	/**
	 * Genera el vector, solicita el valor y muestra su cantidad de apariciones.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		int[] vector = generarVectorAleatorio(
				LONGITUD_VECTOR,
				VALOR_MINIMO,
				VALOR_MAXIMO
		);

		System.out.println("Vector generado: " + Arrays.toString(vector));
		System.out.println("Ingrese un número entre " + VALOR_MINIMO + " y " + VALOR_MAXIMO + ":");

		try (Scanner scanner = new Scanner(System.in)) {
			if (!scanner.hasNextInt()) {
				System.out.println("Entrada inválida: debe ingresar un número entero.");
				return;
			}

			int valorBuscado = scanner.nextInt();
			int cantidad = contarApariciones(vector, valorBuscado);
			System.out.println("El valor " + valorBuscado + " aparece " + cantidad + " veces.");
		}
	}
}
