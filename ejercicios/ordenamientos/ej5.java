/* promp implementar shell-sort mostrando en cada iteracion que valor toma el gap,
 si es un array de 12 elementos entonces el primer gap
  es de 6 el segundo es de 3 y asi hasta llegar
   al caso de un insertion sort y explica claramente 
   porque shell sort mejora a insertion sort */
package ejercicios.guia3;

import java.util.Arrays;

public class ej5 {
	public static void main(String[] args) {
		int[] arreglo = {23, 12, 1, 8, 34, 54, 2, 3, 17, 9, 7, 11};
		System.out.println("Arreglo original: " + Arrays.toString(arreglo));
		System.out.println("Shell Sort comienza ordenando elementos separados por un gap. "
				+ "Para 12 elementos, la secuencia será 6, 3 y 1.\n");

		int comparaciones = 0;
		int desplazamientos = 0;

		for (int gap = arreglo.length / 2; gap > 0; gap /= 2) {
			System.out.println("=== Nueva iteración: gap = " + gap + " ===");

			for (int i = gap; i < arreglo.length; i++) {
				int valor = arreglo[i];
				int j = i;
				int desplazamientosPaso = 0;

				// Inserción por intervalo: compara con elementos gap posiciones atrás.
				while (j >= gap) {
					comparaciones++;
					if (arreglo[j - gap] > valor) {
						arreglo[j] = arreglo[j - gap];
						j -= gap;
						desplazamientos++;
						desplazamientosPaso++;
					} else {
						break;
					}
				}

				arreglo[j] = valor;
				System.out.println("  Inserta " + valor + ": " + Arrays.toString(arreglo)
						+ " (" + desplazamientosPaso + " desplazamientos)");
			}
		}

		System.out.println("\nArreglo ordenado: " + Arrays.toString(arreglo));
		System.out.println("Comparaciones: " + comparaciones);
		System.out.println("Desplazamientos: " + desplazamientos);
		System.out.println("\n¿En qué mejora a Insertion Sort?");
		System.out.println("Insertion Sort solo mueve elementos vecinos. Si un valor pequeño "
				+ "está muy lejos de su lugar, puede necesitar muchos movimientos uno por uno.");
		System.out.println("Shell Sort primero compara y mueve elementos lejanos usando gaps grandes "
				+ "(6 y luego 3 en este ejemplo). Esto acerca los valores a su posición final "
				+ "y reduce desorden e inversiones antes del gap 1.");
		System.out.println("Con gap = 1 se realiza una inserción final sobre un arreglo que ya está "
				+ "más ordenado, por lo que suele hacer menos desplazamientos que Insertion Sort "
				+ "aplicado directamente al arreglo original. La eficiencia exacta depende de "
				+ "la secuencia de gaps utilizada.");
	}
}
