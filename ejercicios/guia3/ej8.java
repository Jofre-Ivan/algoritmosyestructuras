/*implementar el algortimo recursivo 
mergeSORT este metodo divide sin pivote, hasta que 
no puede dividir mas, y luego recursivamente se va fusionando de tal
 modo que el vector queda completamente ordenado
  con complejidad (n log n) */
package ejercicios.guia3;

import java.util.Arrays;

/**
 * Merge Sort ordena usando divide y vencerás: divide el arreglo en mitades
 * hasta llegar a segmentos de un elemento y luego los fusiona ordenadamente.
 * Su complejidad temporal es O(n log n) en el mejor, promedio y peor caso.
 */
public class ej8 {
	public static void main(String[] args) {
		int[] arreglo = {38, 27, 43, 3, 9, 82, 10, 27};
		int[] auxiliar = new int[arreglo.length];

		System.out.println("Arreglo original: " + Arrays.toString(arreglo));
		System.out.println("Merge Sort divide sin elegir pivote; cuando llega a "
				+ "segmentos de un elemento, los fusiona en orden.\n");

		mergeSort(arreglo, auxiliar, 0, arreglo.length - 1, 0);

		System.out.println("\nArreglo ordenado: " + Arrays.toString(arreglo));
		System.out.println("Complejidad temporal: O(n log n) en todos los casos.");
		System.out.println("Espacio auxiliar: O(n), usado durante las fusiones.");
	}

	/** Divide recursivamente el segmento y después fusiona sus dos mitades ordenadas. */
	private static void mergeSort(int[] arreglo, int[] auxiliar, int inicio, int fin, int nivel) {
		if (inicio >= fin) {
			return;
		}

		int medio = inicio + (fin - inicio) / 2;
		String sangria = "  ".repeat(nivel);
		System.out.println(sangria + "Divide: " + segmentoComoTexto(arreglo, inicio, fin)
				+ " -> " + segmentoComoTexto(arreglo, inicio, medio)
				+ " y " + segmentoComoTexto(arreglo, medio + 1, fin));

		mergeSort(arreglo, auxiliar, inicio, medio, nivel + 1);
		mergeSort(arreglo, auxiliar, medio + 1, fin, nivel + 1);
		fusionar(arreglo, auxiliar, inicio, medio, fin);

		System.out.println(sangria + "Fusiona: " + segmentoComoTexto(arreglo, inicio, fin));
	}

	/** Combina dos segmentos ya ordenados en un solo segmento ordenado. */
	private static void fusionar(int[] arreglo, int[] auxiliar, int inicio, int medio, int fin) {
		int izquierda = inicio;
		int derecha = medio + 1;
		int indiceAuxiliar = inicio;

		while (izquierda <= medio && derecha <= fin) {
			if (arreglo[izquierda] <= arreglo[derecha]) {
				auxiliar[indiceAuxiliar++] = arreglo[izquierda++];
			} else {
				auxiliar[indiceAuxiliar++] = arreglo[derecha++];
			}
		}

		while (izquierda <= medio) {
			auxiliar[indiceAuxiliar++] = arreglo[izquierda++];
		}
		while (derecha <= fin) {
			auxiliar[indiceAuxiliar++] = arreglo[derecha++];
		}

		for (int i = inicio; i <= fin; i++) {
			arreglo[i] = auxiliar[i];
		}
	}

	private static String segmentoComoTexto(int[] arreglo, int inicio, int fin) {
		return Arrays.toString(Arrays.copyOfRange(arreglo, inicio, fin + 1));
	}
}
