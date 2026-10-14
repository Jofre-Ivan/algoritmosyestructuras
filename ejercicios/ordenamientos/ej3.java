package ejercicios.guia3;

import java.util.Arrays;

/**
 * Insertion Sort ordena como al acomodar cartas en la mano: toma una carta
 * y la inserta en el lugar correcto de la zona ordenada. Su mejor caso es O(n)
 * y su peor caso es O(n^2); por eso funciona especialmente bien con arreglos
 * casi ordenados, donde se necesitan pocos desplazamientos.
 */
class InsertionSort {
	private static class Resultado {
		private final int comparaciones;
		private final int desplazamientos;

		private Resultado(int comparaciones, int desplazamientos) {
			this.comparaciones = comparaciones;
			this.desplazamientos = desplazamientos;
		}
	}

	/** Ordena el arreglo, muestra cada inserción y devuelve sus contadores. */
	private static Resultado insertionSort(int[] arr) {
		System.out.println("Arreglo original: " + Arrays.toString(arr));
		int comparaciones = 0;
		int desplazamientos = 0;

		// El primer elemento forma inicialmente la zona ordenada de la izquierda.
		for (int i = 1; i < arr.length; i++) {
			int clave = arr[i]; // La clave es la carta que se insertará en su lugar.
			int j = i - 1;
			int desplazamientosPaso = 0;

			// Desplaza a la derecha los elementos mayores para abrir lugar a la clave.
			// En un arreglo casi ordenado, normalmente este bucle se ejecuta muy poco.
			while (j >= 0) {
				comparaciones++;
				if (arr[j] > clave) {
					arr[j + 1] = arr[j];
					desplazamientos++;
					desplazamientosPaso++;
					j--;
				} else {
					break;
				}
			}

			arr[j + 1] = clave;
			System.out.println("Paso " + i + " (clave = " + clave + "): "
					+ Arrays.toString(arr) + " (" + desplazamientosPaso
					+ (desplazamientosPaso == 1 ? " desplazamiento)" : " desplazamientos)"));
		}

		System.out.println("Arreglo ordenado: " + Arrays.toString(arr));
		System.out.println("Total de comparaciones: " + comparaciones);
		System.out.println("Total de desplazamientos: " + desplazamientos);
		return new Resultado(comparaciones, desplazamientos);
	}

	/** Comprueba que cada elemento sea menor o igual que el siguiente. */
	private static boolean estaEnOrden(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] > arr[i + 1]) {
				return false;
			}
		}
		return true;
	}

	private static Resultado ejecutarCaso(String nombre, int[] datos) {
		System.out.println("\n=== " + nombre + " ===");
		int[] arr = Arrays.copyOf(datos, datos.length);
		int[] referencia = Arrays.copyOf(datos, datos.length);
		Resultado resultado = insertionSort(arr);

		System.out.println("Verificación: " + (estaEnOrden(arr) ? "CORRECTA" : "INCORRECTA"));
		// Arrays.sort se usa únicamente para verificar, nunca para ordenar arr.
		Arrays.sort(referencia);
		System.out.println("Coincide con Arrays.sort: " + Arrays.equals(arr, referencia));
		return resultado;
	}

	public static void main(String[] args) {
		int[] casiOrdenado = {1, 2, 4, 3, 5, 6, 8, 7, 9, 10};
		int[] desordenado = {9, 3, 7, 1, 10, 5, 2, 8, 4, 6};

		Resultado resultadoCasiOrdenado = ejecutarCaso("Arreglo casi ordenado", casiOrdenado);
		Resultado resultadoDesordenado = ejecutarCaso("Arreglo desordenado", desordenado);
		ejecutarCaso("Arreglo ya ordenado", new int[]{1, 2, 3, 4, 5});
		ejecutarCaso("Arreglo con repetidos", new int[]{4, 2, 4, 1, 2, 1});
		ejecutarCaso("Arreglo de un elemento", new int[]{7});

		System.out.println("\nComparación final:");
		System.out.println("Casi ordenado: " + resultadoCasiOrdenado.comparaciones
				+ " comparaciones / " + resultadoCasiOrdenado.desplazamientos
				+ " desplazamientos. Desordenado: " + resultadoDesordenado.comparaciones
				+ " comparaciones / " + resultadoDesordenado.desplazamientos + " desplazamientos.");
		System.out.println("El arreglo casi ordenado necesitó menos trabajo porque sus elementos "
				+ "ya estaban cerca de su posición final y hubo menos desplazamientos.");
	}
}
