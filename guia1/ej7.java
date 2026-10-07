package ejercicios.guia1;

import java.util.Arrays;

/**
 * Invierte vectores de enteros con una versión auxiliar y otra en el lugar.
 *
 * <p>Ambas implementaciones recorren el vector una vez, por lo que tienen
 * complejidad temporal O(n). La versión con vector auxiliar utiliza O(n) de
 * espacio adicional y conserva el vector original. La versión en el lugar
 * utiliza O(1) de espacio adicional, pero modifica el vector recibido; es la
 * alternativa que conviene cuando se quiere ahorrar memoria.</p>
 */
public class ej7 {

	/**
	 * Devuelve una copia invertida del vector sin modificar el original.
	 * Complejidad: tiempo O(n), espacio adicional O(n).
	 *
	 * @param vector vector de enteros que se desea invertir
	 * @return un nuevo vector con los elementos en orden inverso
	 * @throws IllegalArgumentException si el vector es null
	 */
	public static int[] invertirConAuxiliar(int[] vector) {
		validarVector(vector);
		int[] invertido = new int[vector.length];

		for (int i = 0; i < vector.length; i++) {
			invertido[i] = vector[vector.length - 1 - i];
		}

		return invertido;
	}

	/**
	 * Invierte y devuelve el mismo vector intercambiando sus extremos hacia el centro.
	 * Complejidad: tiempo O(n), espacio adicional O(1). Modifica el vector recibido.
	 *
	 * @param vector vector de enteros que se desea invertir
	 * @return el mismo vector, ahora invertido
	 * @throws IllegalArgumentException si el vector es null
	 */
	public static int[] invertirEnElLugar(int[] vector) {
		validarVector(vector);
		int inicio = 0;
		int fin = vector.length - 1;

		while (inicio < fin) {
			int temporal = vector[inicio];
			vector[inicio] = vector[fin];
			vector[fin] = temporal;
			inicio++;
			fin--;
		}

		return vector;
	}

	/**
	 * Valida que el vector recibido no sea null.
	 *
	 * @param vector vector que se valida
	 * @throws IllegalArgumentException si el vector es null
	 */
	private static void validarVector(int[] vector) {
		if (vector == null) {
			throw new IllegalArgumentException("El vector no puede ser null.");
		}
	}

	/**
	 * Ejecuta ambas implementaciones sobre copias independientes y muestra resultados.
	 *
	 * @param nombre descripción del caso
	 * @param vector vector de entrada
	 */
	private static void probarCaso(String nombre, int[] vector) {
		int[] resultadoAuxiliar = invertirConAuxiliar(vector);
		int[] copiaParaInvertirEnElLugar = Arrays.copyOf(vector, vector.length);
		int[] resultadoEnElLugar = invertirEnElLugar(copiaParaInvertirEnElLugar);

		System.out.println(nombre);
		System.out.println("  Original:             " + Arrays.toString(vector));
		System.out.println("  Con vector auxiliar:  " + Arrays.toString(resultadoAuxiliar));
		System.out.println("  En el mismo vector:   " + Arrays.toString(resultadoEnElLugar));
		System.out.println("  Resultados iguales:   " + Arrays.equals(resultadoAuxiliar, resultadoEnElLugar));
	}

	/**
	 * Prueba vectores vacío, unitario, con varios valores y con valores repetidos.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		probarCaso("Vector vacío:", new int[]{});
		probarCaso("Un elemento:", new int[]{7});
		probarCaso("Varios elementos:", new int[]{1, 2, 3, 4, 5});
		probarCaso("Valores repetidos:", new int[]{4, 2, 4, 1, 2});

		System.out.println("\nComparación:");
		System.out.println("Con vector auxiliar: tiempo O(n), espacio O(n). "
				+ "Ventaja: conserva el vector original y es directa. "
				+ "Desventaja: necesita memoria proporcional al tamaño del vector.");
		System.out.println("En el mismo vector: tiempo O(n), espacio O(1). "
				+ "Ventaja: ahorra memoria. Desventaja: modifica el vector recibido.");
	}
}
