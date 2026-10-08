package ejercicios.guia3;
/*implementar en java dos metodos una que sea con el algoritmo bubble sort y otra con el selection sort, 
comparando con un mismo vector aleatorio cuantas comparaciones se hicieron que ventajas y desventajas tienen,
 su complejidad, peor caso, mejor caso, promedio caso */
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class ej2 {
	private static final int VALOR_MAXIMO = 100;

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Ingrese la cantidad de elementos del vector: ");
			int cantidad = scanner.nextInt();

			if (cantidad < 0) {
				System.out.println("La cantidad no puede ser negativa.");
				return;
			}

			int[] vectorOriginal = generarVectorAleatorio(cantidad);
			int[] vectorBubble = Arrays.copyOf(vectorOriginal, vectorOriginal.length);
			int[] vectorSelection = Arrays.copyOf(vectorOriginal, vectorOriginal.length);

			System.out.println("\nVector aleatorio original: " + Arrays.toString(vectorOriginal));

			int comparacionesBubble = bubbleSort(vectorBubble);
			int comparacionesSelection = selectionSort(vectorSelection);

			System.out.println("\n--- Bubble Sort ---");
			System.out.println("Vector ordenado: " + Arrays.toString(vectorBubble));
			System.out.println("Comparaciones entre elementos: " + comparacionesBubble);

			System.out.println("\n--- Selection Sort ---");
			System.out.println("Vector ordenado: " + Arrays.toString(vectorSelection));
			System.out.println("Comparaciones entre elementos: " + comparacionesSelection);

			mostrarComparacionTeorica();
		}
	}

	private static int[] generarVectorAleatorio(int cantidad) {
		Random random = new Random();
		int[] vector = new int[cantidad];

		for (int i = 0; i < vector.length; i++) {
			vector[i] = random.nextInt(VALOR_MAXIMO);
		}

		return vector;
	}

	/** Ordena el vector en forma ascendente y devuelve las comparaciones entre valores. */
	private static int bubbleSort(int[] vector) {
		int comparaciones = 0;

		for (int pasada = 0; pasada < vector.length - 1; pasada++) {
			boolean huboIntercambio = false;

			for (int i = 0; i < vector.length - 1 - pasada; i++) {
				comparaciones++;
				if (vector[i] > vector[i + 1]) {
					int temporal = vector[i];
					vector[i] = vector[i + 1];
					vector[i + 1] = temporal;
					huboIntercambio = true;
				}
			}

			// Si no hubo intercambios, el vector ya está ordenado.
			if (!huboIntercambio) {
				break;
			}
		}

		return comparaciones;
	}

	/** Ordena el vector en forma ascendente y devuelve las comparaciones entre valores. */
	private static int selectionSort(int[] vector) {
		int comparaciones = 0;

		for (int inicio = 0; inicio < vector.length - 1; inicio++) {
			int indiceMenor = inicio;

			for (int i = inicio + 1; i < vector.length; i++) {
				comparaciones++;
				if (vector[i] < vector[indiceMenor]) {
					indiceMenor = i;
				}
			}

			if (indiceMenor != inicio) {
				int temporal = vector[inicio];
				vector[inicio] = vector[indiceMenor];
				vector[indiceMenor] = temporal;
			}
		}

		return comparaciones;
	}

	private static void mostrarComparacionTeorica() {
		System.out.println("\n--- Complejidad y características ---");
		System.out.println("Bubble Sort:");
		System.out.println("  Mejor caso: O(n), cuando ya está ordenado (con salida anticipada).");
		System.out.println("  Caso promedio: O(n^2). Peor caso: O(n^2), por ejemplo, en orden inverso.");
		System.out.println("  Espacio adicional: O(1).");
		System.out.println("  Ventajas: sencillo, estable y detecta rápidamente si ya está ordenado.");
		System.out.println("  Desventajas: puede hacer muchos intercambios; no es eficiente en vectores grandes.");

		System.out.println("\nSelection Sort:");
		System.out.println("  Mejor, promedio y peor caso: O(n^2) comparaciones.");
		System.out.println("  Comparaciones exactas: n * (n - 1) / 2. Espacio adicional: O(1).");
		System.out.println("  Ventajas: realiza como máximo n - 1 intercambios y es sencillo.");
		System.out.println("  Desventajas: no mejora si el vector ya está ordenado y no es estable en su versión usual.");
		System.out.println("\nEl conteo considera solo comparaciones entre valores del vector, "
				+ "no las condiciones de los bucles.");
	}
}
