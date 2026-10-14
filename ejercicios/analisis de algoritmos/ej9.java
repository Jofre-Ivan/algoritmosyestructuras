package ejercicios.guia1;

import java.util.Arrays;

/**
 > Implementá en Java un algoritmo que encuentre el mayor número de una matriz de enteros.
>
> Recorré todas las filas y columnas para comparar sus elementos. Explicá brevemente que es necesario revisar toda la matriz porque, si no se presupone un orden especial, el valor máximo puede estar en cualquier posición.
>
> Mostrá el número mayor encontrado y, al final, imprimí claramente:
>
> - **Complejidad temporal:** `O(f × c)`, donde `f` es la cantidad de filas y `c` la cantidad de columnas.
> - **Complejidad espacial:** `O(1)`, porque el algoritmo solo necesita una variable para conservar el máximo actual.
>
> Mantené la solución enfocada únicamente en encontrar el máximo y explicar su recorrido y complejidad.
 */
public class ej9 {

	/**
	 * Recorre la matriz por filas y devuelve el mayor elemento.
	 *
	 * @param matriz matriz rectangular de enteros no vacía
	 * @return el mayor valor de la matriz
	 * @throws IllegalArgumentException si la matriz es null, vacía, irregular
	 *                                  o no contiene elementos
	 */
	public static int encontrarMayor(int[][] matriz) {
		validarMatriz(matriz);

		int mayor = matriz[0][0];
		for (int fila = 0; fila < matriz.length; fila++) {
			for (int columna = 0; columna < matriz[fila].length; columna++) {
				if (matriz[fila][columna] > mayor) {
					mayor = matriz[fila][columna];
				}
			}
		}

		return mayor;
	}

	/**
	 * Comprueba que la matriz tenga filas y columnas y que sea rectangular.
	 *
	 * @param matriz matriz que se valida
	 * @throws IllegalArgumentException si la matriz no cumple las condiciones
	 */
	private static void validarMatriz(int[][] matriz) {
		if (matriz == null || matriz.length == 0 || matriz[0] == null || matriz[0].length == 0) {
			throw new IllegalArgumentException("La matriz debe tener al menos una fila y una columna.");
		}

		int columnas = matriz[0].length;
		for (int fila = 0; fila < matriz.length; fila++) {
			if (matriz[fila] == null || matriz[fila].length != columnas) {
				throw new IllegalArgumentException("La matriz debe ser rectangular y no contener filas null.");
			}
		}
	}

	/**
	 * Muestra un ejemplo de la búsqueda del mayor número.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		int[][] matriz = {
				{12, 5, 18},
				{-3, 27, 9},
				{14, 2, 21}
		};

		System.out.println("Matriz: " + Arrays.deepToString(matriz));
		System.out.println("Número mayor: " + encontrarMayor(matriz));
		System.out.println("Recorrido: se revisa cada fila de izquierda a derecha.");
		System.out.println("Es necesario revisar toda la matriz porque el mayor puede estar en cualquier posición.");
		System.out.println("Complejidad temporal: O(f * c), con f filas y c columnas.");
		System.out.println("Complejidad espacial: O(1), solo se conserva la variable del mayor.");
	}
}
