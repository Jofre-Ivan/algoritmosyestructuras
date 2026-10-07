package ejercicios.guia1;

import java.util.Arrays;

/**
 * Suma todos los elementos de una matriz recorriéndola fila por fila.
 * Para una matriz de R filas y C columnas, el tiempo total es O(R + R * C),
 * incluyendo la validación de las filas; cuando C es mayor que cero se simplifica
 * a O(R * C). El espacio adicional es O(1). Se cuentan como operaciones
 * principales una visita/lectura y una suma por cada celda; no se contabilizan
 * controles del ciclo, validación ni instrumentación de los contadores.
 */
public class ej8 {

	/**
	 * Guarda la suma y las cantidades de operaciones principales realizadas.
	 */
	public static class ResultadoSuma {
		private final long suma;
		private final long celdasVisitadas;
		private final long sumasRealizadas;

		private ResultadoSuma(long suma, long celdasVisitadas, long sumasRealizadas) {
			this.suma = suma;
			this.celdasVisitadas = celdasVisitadas;
			this.sumasRealizadas = sumasRealizadas;
		}

		public long getSuma() {
			return suma;
		}

		public long getCeldasVisitadas() {
			return celdasVisitadas;
		}

		public long getSumasRealizadas() {
			return sumasRealizadas;
		}

		public long getOperacionesPrincipales() {
			return celdasVisitadas + sumasRealizadas;
		}
	}

	/**
	 * Recorre cada fila y, dentro de ella, cada columna para sumar todos los valores.
	 *
	 * @param matriz matriz rectangular de enteros que se desea sumar
	 * @return suma y contadores de operaciones principales
	 * @throws IllegalArgumentException si la matriz es null, contiene una fila null
	 *                                  o sus filas tienen longitudes distintas
	 */
	public static ResultadoSuma sumarMatriz(int[][] matriz) {
		validarMatriz(matriz);

		long suma = 0;
		long celdasVisitadas = 0;
		long sumasRealizadas = 0;

		for (int fila = 0; fila < matriz.length; fila++) {
			for (int columna = 0; columna < matriz[fila].length; columna++) {
				int valor = matriz[fila][columna];
				celdasVisitadas++;
				suma += valor;
				sumasRealizadas++;
			}
		}

		return new ResultadoSuma(suma, celdasVisitadas, sumasRealizadas);
	}

	/**
	 * Comprueba que la matriz no sea null y que todas sus filas sean rectangulares.
	 *
	 * @param matriz matriz que se validará
	 * @throws IllegalArgumentException si la matriz no cumple las condiciones
	 */
	private static void validarMatriz(int[][] matriz) {
		if (matriz == null) {
			throw new IllegalArgumentException("La matriz no puede ser null.");
		}

		int cantidadColumnas = matriz.length == 0 ? 0 : longitudFila(matriz[0], 0);
		for (int fila = 0; fila < matriz.length; fila++) {
			int longitudActual = longitudFila(matriz[fila], fila);
			if (longitudActual != cantidadColumnas) {
				throw new IllegalArgumentException("La matriz debe tener todas sus filas del mismo largo.");
			}
		}
	}

	/**
	 * Obtiene la longitud de una fila y verifica que no sea null.
	 *
	 * @param fila arreglo correspondiente a una fila
	 * @param indiceFila posición de la fila en la matriz
	 * @return cantidad de elementos de la fila
	 * @throws IllegalArgumentException si la fila es null
	 */
	private static int longitudFila(int[] fila, int indiceFila) {
		if (fila == null) {
			throw new IllegalArgumentException("La fila " + indiceFila + " no puede ser null.");
		}
		return fila.length;
	}

	/**
	 * Ejecuta una prueba, mostrando la matriz, la suma y el conteo de operaciones.
	 *
	 * @param nombre descripción de la prueba
	 * @param matriz matriz que se procesará
	 */
	private static void probarCaso(String nombre, int[][] matriz) {
		ResultadoSuma resultado = sumarMatriz(matriz);
		System.out.println(nombre);
		System.out.println("Matriz: " + Arrays.deepToString(matriz));
		System.out.println("Suma: " + resultado.getSuma());
		System.out.println("Celdas visitadas/lecturas: " + resultado.getCeldasVisitadas());
		System.out.println("Sumas realizadas: " + resultado.getSumasRealizadas());
		System.out.println("Operaciones principales contabilizadas: "
				+ resultado.getOperacionesPrincipales());
	}

	/**
	 * Prueba una matriz rectangular y una matriz sin elementos.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		probarCaso("Matriz de ejemplo:", new int[][]{
				{1, 2, 3},
				{4, 5, 6}
		});
		probarCaso("Matriz vacía:", new int[][]{});

		System.out.println("\nComplejidad:");
		System.out.println("Tiempo: O(R + R * C), incluyendo la validación de las filas; "
				+ "si C > 0, se simplifica a O(R * C).");
		System.out.println("Espacio adicional: O(1), porque solo se usan acumuladores y contadores.");
		System.out.println("Operaciones principales: 2 * N, donde N es la cantidad de celdas "
				+ "(N visitas/lecturas + N sumas).");
	}
}
