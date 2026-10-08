/*implementar algoritmo recursivo QUICKSORT y tomar como pivote el primer elemento, 
el programa debe mostrar el pivote elegido, 
los dos subarrays el derecho y el izquierdo
 y el arreglo final ordenado relacionalo con 
 divides y venceras */
package ejercicios.guia3;

import java.util.Arrays;

/**
 * QuickSort aplica divide y vencerás: divide el arreglo alrededor de un pivote,
 * ordena recursivamente las particiones y combina el resultado sin pasos extra.
 */
public class ej6 {
	public static void main(String[] args) {
		int[] arreglo = {8, 3, 1, 7, 0, 10, 2, 6, 4, 9, 5};
		System.out.println("Arreglo original: " + Arrays.toString(arreglo));
		System.out.println("QuickSort elige el primer elemento de cada subarreglo como pivote.\n");
		System.out.println("Divide y vencerás: divide alrededor del pivote, ordena "
				+ "recursivamente los subarreglos y los deja concatenados en orden, "
				+ "sin una fase adicional de combinación.\n");

		quickSort(arreglo, 0, arreglo.length - 1, 0);

		System.out.println("\nArreglo final ordenado: " + Arrays.toString(arreglo));
	}

	/** Ordena recursivamente el segmento entre los índices inicio y fin. */
	private static void quickSort(int[] arreglo, int inicio, int fin, int nivel) {
		if (inicio >= fin) {
			return;
		}

		int pivote = arreglo[inicio];
		int indicePivote = particionar(arreglo, inicio, fin, pivote);

		String sangria = "  ".repeat(nivel);
		System.out.println(sangria + "Pivote elegido: " + pivote);
		System.out.println(sangria + "Subarreglo izquierdo (< pivote): "
				+ segmentoComoTexto(arreglo, inicio, indicePivote - 1));
		System.out.println(sangria + "Subarreglo derecho (>= pivote): "
				+ segmentoComoTexto(arreglo, indicePivote + 1, fin));

		// Divide: ya se ubicó el pivote y se separaron los valores menores y mayores o iguales.
		// Vencer: ordena cada partición aplicando QuickSort de nuevo.
		quickSort(arreglo, inicio, indicePivote - 1, nivel + 1);
		quickSort(arreglo, indicePivote + 1, fin, nivel + 1);
	}

	/** Coloca el pivote en su posición definitiva y devuelve su índice. */
	private static int particionar(int[] arreglo, int inicio, int fin, int pivote) {
		int menores = inicio + 1;
		int mayores = fin;

		while (menores <= mayores) {
			if (arreglo[menores] < pivote) {
				menores++;
			} else if (arreglo[mayores] >= pivote) {
				mayores--;
			} else {
				intercambiar(arreglo, menores, mayores);
				menores++;
				mayores--;
			}
		}

		intercambiar(arreglo, inicio, mayores);
		return mayores;
	}

	private static void intercambiar(int[] arreglo, int primero, int segundo) {
		int temporal = arreglo[primero];
		arreglo[primero] = arreglo[segundo];
		arreglo[segundo] = temporal;
	}

	private static String segmentoComoTexto(int[] arreglo, int inicio, int fin) {
		if (inicio > fin) {
			return "[]";
		}
		return Arrays.toString(Arrays.copyOfRange(arreglo, inicio, fin + 1));
	}
}
