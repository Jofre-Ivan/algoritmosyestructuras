/*implementar ahora el peor caso de quicksort (n^2) que seria sobre un arreglo ya ordeando y contar la cantidad de llamadas recursivas  realizadas. QuickSort es eficiente cuando el pivote divide el arreglo en dos partes parecidas: la profundidad de la recursión es log n y el costo total es O(n log n).
- Si el arreglo YA ESTÁ ORDENADO y el pivote es el primer elemento, el pivote es el menor de todos: ningún elemento es menor que él, así que queda en la posición low, a la izquierda quedan 0 elementos y a la derecha n-1.
- Eso pasa en cada llamada: el problema solo se reduce en 1 elemento por nivel, la profundidad de recursión es n y las comparaciones suman 
elegir el primer elemento como pivote no siempre es buena idea, porque el rendimiento depende del orden de los datos y justo el caso "ya ordenado" (muy común en la práctica) es el peor.

## 3. Datos que recibe el programa
- Un arreglo ya ordenado ascendentemente generado por código: {1, 2, 3, ..., n}. Primero con n = 10 (para ver el detalle paso a paso) y luego con n = 100, 500 y 1000 (solo resumen).
- Para comparar, un arreglo desordenado (valores aleatorios con una semilla fija en Random para que sea reproducible) del mismo tamaño.
- No usar n mayor a 5000 para evitar StackOverflowError por la profundidad de la recursión; dejar un comentario que lo explique.

## 4. Qué debe mostrar por pantalla
1. Para n = 10: "Arreglo original: [..]" y, en cada llamada recursiva, una línea con indentación según la profundidad, por ejemplo: "quickSort(low=0, high=9) pivote=1 -> posición final=0 | izquierda: 0 elementos, derecha: 9 elementos".
2. "Arreglo ordenado: [..]".
3. Contador de llamadas recursivas: contar TODAS las llamadas a quickSort, incluyendo la inicial y las que terminan de inmediato por el caso base. Aclarar en pantalla qué convención se usó.
4. Contador de comparaciones entre elementos.
5. Una tabla final para cada n con: llamadas recursivas, comparaciones, y los valores teóricos (llamadas = 2n+1, comparaciones = n(n-1)/2 para el peor caso), junto con los resultados del arreglo desordenado para comparar.
6. Una frase final que explique por qué el arreglo ordenado necesitó muchas más llamadas y comparaciones que el desordenado.

## 5. Comentarios que debe incluir el código
- Comentario al inicio de la clase con qué es QuickSort y su complejidad: O(n log n) en el caso promedio y O(n²) en el peor caso.
- Comentario en la elección del pivote explicando por qué usar el primer elemento es riesgoso con datos ya ordenados.
- Comentario en la partición explicando qué representa cada índice (i, j) y por qué en un arreglo ordenado el pivote no se mueve.
- Comentario junto a los contadores explicando qué se cuenta exactamente.
- Comentarios en español, cortos y claros, pensados para un estudiante que está aprendiendo.


 */
package ejercicios.guia3;

import java.util.Arrays;
import java.util.Random;

/**
 * QuickSort particiona alrededor de un pivote y ordena recursivamente cada lado.
 * Su complejidad promedio es O(n log n), pero con particiones muy desiguales
 * (como al ordenar datos con pivote inicial) el peor caso es O(n^2).
 */
public class ej7 {
	private static final long SEMILLA = 20261007L;

	private static class Estadisticas {
		// Cuenta cada entrada a quickSort, incluidas las llamadas que terminan en el caso base.
		private long llamadas;
		// Cuenta comparaciones de un elemento del arreglo contra el pivote en la partición.
		private long comparaciones;
	}

	public static void main(String[] args) {
		int[] tamanos = {10, 100, 500, 1000};
		System.out.println("QuickSort con el primer elemento como pivote.");
		System.out.println("Convención: llamadas recursivas cuenta todas las invocaciones, "
				+ "incluidas la inicial y las que llegan al caso base.");
		System.out.println("Comparaciones cuenta cada elemento examinado contra el pivote; "
				+ "no cuenta comparaciones de índices.\n");

		System.out.printf("%-6s | %-18s | %-18s | %-18s | %-18s | %-18s | %-18s%n",
				"n", "Ordenado: llamadas", "Ordenado: comps.", "Teoría: llamadas",
				"Teoría: comps.", "Aleatorio: llamadas", "Aleatorio: comps.");
		System.out.println("-".repeat(126));

		for (int n : tamanos) {
			int[] ordenado = generarOrdenado(n);
			int[] aleatorio = generarAleatorio(n, SEMILLA + n);
			boolean mostrarDetalle = n == 10;

			System.out.println("\n=== n = " + n + ": arreglo ascendente ===");
			if (mostrarDetalle) {
				System.out.println("Arreglo original: " + Arrays.toString(ordenado));
			}
			Estadisticas estadisticasOrdenado = new Estadisticas();
			quickSort(ordenado, 0, ordenado.length - 1, estadisticasOrdenado, mostrarDetalle, 0);
			if (mostrarDetalle) {
				System.out.println("Arreglo ordenado: " + Arrays.toString(ordenado));
			}

			System.out.println("\n=== n = " + n + ": arreglo aleatorio reproducible ===");
			if (mostrarDetalle) {
				System.out.println("Arreglo original: " + Arrays.toString(aleatorio));
			}
			Estadisticas estadisticasAleatorio = new Estadisticas();
			quickSort(aleatorio, 0, aleatorio.length - 1, estadisticasAleatorio, mostrarDetalle, 0);
			if (mostrarDetalle) {
				System.out.println("Arreglo ordenado: " + Arrays.toString(aleatorio));
			}

			long llamadasTeoricas = 2L * n + 1;
			long comparacionesTeoricas = (long) n * (n - 1) / 2;
			System.out.printf("%-6d | %-18d | %-18d | %-18d | %-18d | %-18d | %-18d%n",
					n, estadisticasOrdenado.llamadas, estadisticasOrdenado.comparaciones,
					llamadasTeoricas, comparacionesTeoricas,
					estadisticasAleatorio.llamadas, estadisticasAleatorio.comparaciones);
		}

		System.out.println("\nEl arreglo ascendente requiere muchas más llamadas y comparaciones: "
				+ "al elegir el primer elemento como pivote, este siempre es el menor, "
				+ "por lo que una partición queda vacía y la otra conserva n - 1 elementos. "
				+ "La recursión se vuelve lineal (O(n) de profundidad) y el trabajo total O(n^2), "
				+ "en vez de las particiones más equilibradas que suelen dar O(n log n).");
		System.out.println("Se limita n a 1000 en estos ejemplos; entradas ordenadas grandes pueden "
				+ "producir una recursión profunda y StackOverflowError. Evita probar tamaños "
				+ "mayores a 5000 con esta implementación recursiva.");
	}

	private static int[] generarOrdenado(int n) {
		int[] arreglo = new int[n];
		for (int i = 0; i < n; i++) {
			arreglo[i] = i + 1;
		}
		return arreglo;
	}

	private static int[] generarAleatorio(int n, long semilla) {
		int[] arreglo = generarOrdenado(n);
		Random random = new Random(semilla);
		for (int i = arreglo.length - 1; i > 0; i--) {
			int indice = random.nextInt(i + 1);
			int temporal = arreglo[i];
			arreglo[i] = arreglo[indice];
			arreglo[indice] = temporal;
		}
		return arreglo;
	}

	private static void quickSort(int[] arreglo, int low, int high, Estadisticas estadisticas,
			boolean mostrarDetalle, int profundidad) {
		estadisticas.llamadas++;
		String sangria = "  ".repeat(profundidad);

		if (low > high) {
			if (mostrarDetalle) {
				System.out.println(sangria + "quickSort(low=" + low + ", high=" + high + ") caso base");
			}
			return;
		}

		// Usar el primer elemento como pivote es riesgoso: si el segmento ya está ordenado,
		// ese pivote es el menor y produce una partición vacía y otra de tamaño n - 1.
		int pivote = arreglo[low];
		int posicionPivote = particionar(arreglo, low, high, estadisticas);

		if (mostrarDetalle) {
			System.out.println(sangria + "quickSort(low=" + low + ", high=" + high + ") pivote="
					+ pivote + " -> posición final=" + posicionPivote
					+ " | izquierda: " + (posicionPivote - low) + " elementos "
					+ segmentoComoTexto(arreglo, low, posicionPivote - 1)
					+ ", derecha: " + (high - posicionPivote) + " elementos "
					+ segmentoComoTexto(arreglo, posicionPivote + 1, high));
		}

		quickSort(arreglo, low, posicionPivote - 1, estadisticas, mostrarDetalle, profundidad + 1);
		quickSort(arreglo, posicionPivote + 1, high, estadisticas, mostrarDetalle, profundidad + 1);
	}

	private static int particionar(int[] arreglo, int low, int high, Estadisticas estadisticas) {
		int i = low; // i marca el final de la zona con valores menores que el pivote.
		for (int j = low + 1; j <= high; j++) { // j examina cada elemento restante.
			estadisticas.comparaciones++;
			if (arreglo[j] < arreglo[low]) {
				i++;
				intercambiar(arreglo, i, j);
			}
		}

		// En un segmento ascendente, ningún valor es menor que el pivote:
		// i sigue siendo low y el intercambio final deja el pivote en su sitio.
		intercambiar(arreglo, low, i);
		return i;
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
