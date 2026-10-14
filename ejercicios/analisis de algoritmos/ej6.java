package ejercicios.guia1;

import java.util.Arrays;

/**
 * implementar un algoritmo que determine 
 * si dos vectores son iguales y apenas vea una diferencia
 *  en el vector el algortimo tiene que finalizar, 
 * se necesita tambien un analisis del mejor caso, peor caso y promedio
 * Compara dos vectores de enteros y determina si son iguales en longitud
 * y en el valor de cada posición.
 *
 * <p>El algoritmo termina inmediatamente si las longitudes son distintas o
 * encuentra una diferencia entre elementos. El mejor caso es O(1), cuando
 * las longitudes difieren o el primer par de elementos no coincide. El peor
 * caso es O(n), cuando los vectores son iguales o su diferencia está en la
 * última posición. En promedio es O(n), suponiendo que la primera diferencia
 * puede aparecer con igual probabilidad en cualquiera de las posiciones.
 * El espacio adicional es O(1).</p>
 */
public class ej6 {

	/**
	 * Compara dos vectores posición por posición y corta al hallar una diferencia.
	 * Dos vectores son iguales si tienen la misma longitud y los mismos valores
	 * en todas las posiciones.
	 *
	 * @param primerVector primer vector a comparar
	 * @param segundoVector segundo vector a comparar
	 * @return true si los vectores son iguales; false en caso contrario
	 * @throws IllegalArgumentException si alguno de los vectores es null
	 */
	public static boolean sonIguales(int[] primerVector, int[] segundoVector) {
		if (primerVector == null || segundoVector == null) {
			throw new IllegalArgumentException("Los vectores no pueden ser null.");
		}

		if (primerVector.length != segundoVector.length) {
			return false;
		}

		for (int i = 0; i < primerVector.length; i++) {
			if (primerVector[i] != segundoVector[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Muestra los vectores de un caso de prueba y el resultado de compararlos.
	 *
	 * @param nombre descripción del caso de prueba
	 * @param primerVector primer vector a comparar
	 * @param segundoVector segundo vector a comparar
	 */
	private static void probarCaso(String nombre, int[] primerVector, int[] segundoVector) {
		System.out.println(nombre);
		System.out.println("  Primer vector:  " + Arrays.toString(primerVector));
		System.out.println("  Segundo vector: " + Arrays.toString(segundoVector));
		System.out.println("  ¿Son iguales?: " + sonIguales(primerVector, segundoVector));
	}

	/**
	 * Prueba vectores iguales, diferencias en distintas posiciones,
	 * longitudes distintas y vectores vacíos.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		probarCaso("Vectores iguales:",
				new int[]{1, 2, 3, 4}, new int[]{1, 2, 3, 4});
		probarCaso("Diferencia en el primer elemento:",
				new int[]{9, 2, 3, 4}, new int[]{1, 2, 3, 4});
		probarCaso("Diferencia en una posición intermedia:",
				new int[]{1, 2, 8, 4}, new int[]{1, 2, 3, 4});
		probarCaso("Diferencia en el último elemento:",
				new int[]{1, 2, 3, 9}, new int[]{1, 2, 3, 4});
		probarCaso("Longitudes distintas:",
				new int[]{1, 2, 3}, new int[]{1, 2});
		probarCaso("Dos vectores vacíos:", new int[]{}, new int[]{});
		probarCaso("Vector vacío y vector no vacío:", new int[]{}, new int[]{1});

		System.out.println("\nComplejidad:");
		System.out.println("Mejor caso: O(1), si las longitudes difieren o hay una diferencia al inicio.");
		System.out.println("Peor caso: O(n), si son iguales o difieren al final.");
		System.out.println("Caso promedio: O(n), bajo una distribución uniforme de la primera diferencia.");
		System.out.println("Espacio adicional: O(1).");
	}
}
