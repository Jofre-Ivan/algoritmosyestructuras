package ejercicios.guia1;

/*

Implementá en Java un algoritmo de ordenamiento burbuja que ordene un vector de números enteros de menor a mayor.

Requisitos:
1. Implementá el algoritmo de forma clara y modular.
2. Contabilizá y mostrá la cantidad real de comparaciones y de intercambios realizados durante la ejecución.
3. Incluí un main que pruebe un vector desordenado y muestre el vector antes y después de ordenarlo, junto con ambos contadores.
4. Explicá brevemente cómo funciona burbuja: compara elementos adyacentes e intercambia los que están en el orden incorrecto.
5. Explicá por qué es adecuado principalmente para fines didácticos o conjuntos pequeños: su tiempo de ejecución es cuadrático en promedio y en el peor caso, y puede realizar muchos intercambios. Mencioná que para conjuntos grandes suelen convenir algoritmos más eficientes.
6. Indicá la complejidad temporal y espacial. Si implementás una optimización que detiene el algoritmo cuando una pasada no realiza intercambios, explicá cómo afecta al mejor caso y contabilizá únicamente las operaciones que realmente se ejecutaron.
7. Mantené la solución enfocada en el ordenamiento, el conteo y su explicación.
*/

import java.util.Arrays;

/**
 * Ordena un vector de enteros mediante el método burbuja optimizado.
 * En cada pasada compara elementos adyacentes y los intercambia si están
 * desordenados. Se detiene cuando una pasada no realiza intercambios.
 * Es útil para aprender ordenamiento y para conjuntos pequeños; en promedio
 * y en el peor caso requiere O(n^2) tiempo, por lo que para conjuntos grandes
 * suelen preferirse algoritmos más eficientes.
 */
public class ej10 {

	/**
	 * Contiene el vector ordenado y los contadores de operaciones ejecutadas.
	 */
	public static class ResultadoOrdenamiento {
		private final int[] vectorOrdenado;
		private final long comparaciones;
		private final long intercambios;

		private ResultadoOrdenamiento(int[] vectorOrdenado, long comparaciones, long intercambios) {
			this.vectorOrdenado = vectorOrdenado;
			this.comparaciones = comparaciones;
			this.intercambios = intercambios;
		}

		public int[] getVectorOrdenado() {
			return vectorOrdenado;
		}

		public long getComparaciones() {
			return comparaciones;
		}

		public long getIntercambios() {
			return intercambios;
		}
	}

	/**
	 * Ordena el vector en el lugar y cuenta las comparaciones e intercambios.
	 * Complejidad temporal: mejor caso O(n), promedio y peor caso O(n^2).
	 * Complejidad espacial adicional: O(1). El vector recibido queda modificado.
	 *
	 * @param vector vector de enteros que se ordenará
	 * @return resultado con el mismo vector ordenado y los contadores
	 * @throws IllegalArgumentException si el vector es null
	 */
	public static ResultadoOrdenamiento ordenarBurbuja(int[] vector) {
		if (vector == null) {
			throw new IllegalArgumentException("El vector no puede ser null.");
		}

		long comparaciones = 0;
		long intercambios = 0;

		for (int limite = vector.length - 1; limite > 0; limite--) {
			boolean huboIntercambio = false;

			for (int indice = 0; indice < limite; indice++) {
				comparaciones++;
				if (vector[indice] > vector[indice + 1]) {
					int temporal = vector[indice];
					vector[indice] = vector[indice + 1];
					vector[indice + 1] = temporal;
					intercambios++;
					huboIntercambio = true;
				}
			}

			if (!huboIntercambio) {
				break;
			}
		}

		return new ResultadoOrdenamiento(vector, comparaciones, intercambios);
	}

	/**
	 * Muestra el funcionamiento del algoritmo y el análisis de complejidad.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		int[] vector = {7, 3, 9, 2, 5, 1};

		System.out.println("Vector antes de ordenar: " + Arrays.toString(vector));
		ResultadoOrdenamiento resultado = ordenarBurbuja(vector);
		System.out.println("Vector ordenado: " + Arrays.toString(resultado.getVectorOrdenado()));
		System.out.println("Comparaciones realizadas: " + resultado.getComparaciones());
		System.out.println("Intercambios realizados: " + resultado.getIntercambios());

		System.out.println("\nComplejidad temporal:");
		System.out.println("Mejor caso: O(n), si ya está ordenado gracias a la detención temprana.");
		System.out.println("Caso promedio: O(n^2).");
		System.out.println("Peor caso: O(n^2), por ejemplo, si está en orden inverso.");
		System.out.println("Complejidad espacial adicional: O(1).");
		System.out.println("Burbuja es adecuado para fines didácticos o vectores pequeños; "
				+ "para conjuntos grandes convienen algoritmos más eficientes.");
	}
}
