/*implementar en Java un programa que compare tres algoritmos de ordenamiento simples: Bubble Sort, Selection Sort e Insertion Sort. El programa ordena el MISMO arreglo con los tres métodos y muestra cuánto trabajo hizo cada uno, para comparar las estrategias y no solo el resultado final.

## 1. Algoritmos a utilizar
Los tres implementados "a mano", orden ascendente, sin Arrays.sort() para ordenar. Cada algoritmo trabaja sobre su propia COPIA del arreglo original (Arrays.copyOf), para que los tres partan exactamente de los mismos datos.

- Bubble Sort: dos bucles anidados, comparando pares adyacentes; con optimización de corte temprano (si una pasada no hizo intercambios, termina). El límite del bucle interno se reduce en cada pasada.
- Selection Sort: en cada paso busca el mínimo de la parte no ordenada y lo intercambia con el primer elemento de esa parte. Solo hace el intercambio si el mínimo no está ya en su lugar.
- Insertion Sort: toma cada elemento como "clave" y desplaza hacia la derecha los elementos mayores de la zona ordenada para insertarla en su lugar.

## 2. Razonamiento de cada algoritmo
- Bubble Sort: compara y cambia vecinos; el mayor "burbujea" hacia el final en cada pasada. Hace muchos intercambios porque mueve los elementos de a una posición por vez.
- Selection Sort: elige el mínimo y lo coloca directamente en su posición final. Siempre hace la misma cantidad de comparaciones sin importar el orden de los datos, pero muy pocos intercambios (a lo sumo n-1).
- Insertion Sort: construye la zona ordenada como quien ordena cartas en la mano. Se adapta a los datos: casi no trabaja si el arreglo ya está ordenado o casi ordenado.
- Los tres son O(n²) en el peor caso, pero se diferencian en el tipo de trabajo que hacen: Selection minimiza escrituras, Insertion se adapta al orden previo, Bubble es el más costoso en intercambios.

## 3. Datos que recibe el programa
- Un arreglo de enteros definido en el código, por ejemplo: {64, 34, 25, 12, 22, 11, 90, 5}.
- Además, ejecutar la comparación con estos escenarios de tamaño n = 10 (o el del arreglo de ejemplo):
  a) arreglo aleatorio (Random con semilla fija para que sea reproducible),
  b) arreglo ya ordenado,
  c) arreglo en orden inverso (peor caso),
  d) arreglo casi ordenado.
- Opcional: n = 100 para ver cómo crecen los números (mostrar solo el resumen, no el arreglo).

## 4. Qué debe mostrar por pantalla
Para cada escenario:
1. "Arreglo original: [..]"
2. Para cada algoritmo: nombre, "Arreglo ordenado: [..]", "Comparaciones: X" y "Intercambios: Y" (o "Desplazamientos: Y" en Insertion Sort).
3. Una tabla resumen alineada con columnas: Algoritmo | Comparaciones | Intercambios/Desplazamientos | Verificación.
4. Una línea que indique cuál algoritmo hizo menos comparaciones y cuál menos movimientos en ese escenario.

Al final, una tabla comparativa general de todos los escenarios (filas = escenarios, columnas = algoritmos) y un breve texto que explique las diferencias observadas (por ejemplo, por qué Selection Sort hace casi siempre las mismas comparaciones, por qué Insertion Sort es mucho mejor con datos ordenados y por qué Bubble Sort hace más movimientos).

## 5. Convención de conteo (importante, respetala exactamente)
- Comparación: cada vez que se compara el valor de dos elementos del arreglo entre sí (arr[j] > arr[j+1] en Bubble, arr[j] < arr[min] en Selection, arr[j] > clave en Insertion). No contar las comparaciones de índices ni de la condición booleana de los bucles.
- Intercambio (Bubble y Selection): cada swap real de dos posiciones cuenta como 1.
- Desplazamiento (Insertion): cada vez que un elemento se copia una posición a la derecha cuenta como 1. La inserción final de la clave NO cuenta.
- En Insertion Sort, evaluar primero el límite del índice y después la comparación de valores (j >= 0 && arr[j] > clave), para no contar comparaciones inexistentes.
- Mostrar esta convención por pantalla al principio del programa.

## 6. Comentarios que debe incluir el código
- Al inicio de la clase: qué compara el programa y la complejidad de cada algoritmo (mejor y peor caso).
- Dentro de cada algoritmo: un */
package ejercicios.guia3;

import java.util.Arrays;
import java.util.Random;

/**
 * Compara Bubble Sort, Selection Sort e Insertion Sort sobre copias de los
 * mismos datos. En los tres, el peor caso es O(n^2); Bubble tiene mejor caso
 * O(n) con corte temprano, Selection O(n^2) y Insertion O(n) si ya está ordenado.
 */
public class ej9 {
	private static final int TAMANIO = 10;
	private static final long SEMILLA = 20261007L;

	private static class Resultado {
		private final String algoritmo;
		private final long comparaciones;
		private final long movimientos;
		private final String nombreMovimiento;
		private final boolean verificado;

		private Resultado(String algoritmo, long comparaciones, long movimientos,
				String nombreMovimiento, boolean verificado) {
			this.algoritmo = algoritmo;
			this.comparaciones = comparaciones;
			this.movimientos = movimientos;
			this.nombreMovimiento = nombreMovimiento;
			this.verificado = verificado;
		}
	}

	private static class Escenario {
		private final String nombre;
		private final int[] valores;
		private Resultado[] resultados;

		private Escenario(String nombre, int[] valores) {
			this.nombre = nombre;
			this.valores = valores;
		}
	}

	public static void main(String[] args) {
		System.out.println("Convención de conteo:");
		System.out.println("- Comparación: cada comparación entre dos valores del arreglo.");
		System.out.println("- Bubble y Selection: un movimiento por cada intercambio real.");
		System.out.println("- Insertion: un movimiento por cada elemento desplazado a la derecha; "
				+ "la inserción de la clave no cuenta.");
		System.out.println("- No se cuentan condiciones de índices ni de bucles.\n");

		Escenario[] escenarios = crearEscenarios();		
		for (Escenario escenario : escenarios) {
			procesarEscenario(escenario);
		}

		mostrarTablaGeneral(escenarios);
		mostrarExplicacionFinal();
	}

	private static Escenario[] crearEscenarios() {
		int[] aleatorio = new int[TAMANIO];
		Random random = new Random(SEMILLA);
		for (int i = 0; i < aleatorio.length; i++) {
			aleatorio[i] = random.nextInt(100);
		}

		int[] ordenado = new int[TAMANIO];
		int[] inverso = new int[TAMANIO];
		for (int i = 0; i < TAMANIO; i++) {
			ordenado[i] = i + 1;
			inverso[i] = TAMANIO - i;
		}

		int[] casiOrdenado = Arrays.copyOf(ordenado, ordenado.length);
		intercambiar(casiOrdenado, 3, 4);
		intercambiar(casiOrdenado, 7, 8);

		return new Escenario[]{
				new Escenario("Aleatorio (semilla " + SEMILLA + ")", aleatorio),
				new Escenario("Ya ordenado", ordenado),
				new Escenario("Orden inverso", inverso),
				new Escenario("Casi ordenado", casiOrdenado)
		};
	}

	private static void procesarEscenario(Escenario escenario) {
		System.out.println("=== Escenario: " + escenario.nombre + " ===");
		System.out.println("Arreglo original: " + Arrays.toString(escenario.valores));

		int[] bubble = Arrays.copyOf(escenario.valores, escenario.valores.length);
		int[] selection = Arrays.copyOf(escenario.valores, escenario.valores.length);
		int[] insertion = Arrays.copyOf(escenario.valores, escenario.valores.length);

		long[] bubbleStats = bubbleSort(bubble);
		long[] selectionStats = selectionSort(selection);
		long[] insertionStats = insertionSort(insertion);

		escenario.resultados = new Resultado[]{
				crearResultado("Bubble Sort", bubbleStats, "intercambios", bubble, escenario.valores),
				crearResultado("Selection Sort", selectionStats, "intercambios", selection, escenario.valores),
				crearResultado("Insertion Sort", insertionStats, "desplazamientos", insertion, escenario.valores)
		};

		System.out.println("\nBubble Sort — Arreglo ordenado: " + Arrays.toString(bubble));
		System.out.println("Comparaciones: " + bubbleStats[0] + " | Intercambios: " + bubbleStats[1]);
		System.out.println("Selection Sort — Arreglo ordenado: " + Arrays.toString(selection));
		System.out.println("Comparaciones: " + selectionStats[0] + " | Intercambios: " + selectionStats[1]);
		System.out.println("Insertion Sort — Arreglo ordenado: " + Arrays.toString(insertion));
		System.out.println("Comparaciones: " + insertionStats[0] + " | Desplazamientos: " + insertionStats[1]);

		System.out.println("\nResumen del escenario:");
		System.out.printf("%-18s | %14s | %29s | %12s%n",
				"Algoritmo", "Comparaciones", "Intercambios/Desplazamientos", "Verificación");
		System.out.println("-".repeat(83));
		for (Resultado resultado : escenario.resultados) {
			System.out.printf("%-18s | %14d | %15d %-13s | %12s%n",
					resultado.algoritmo, resultado.comparaciones, resultado.movimientos,
					resultado.nombreMovimiento, resultado.verificado ? "CORRECTA" : "INCORRECTA");
		}

		Resultado menosComparaciones = minimo(escenario.resultados, true);
		Resultado menosMovimientos = minimo(escenario.resultados, false);
		System.out.println("Menos comparaciones: " + menosComparaciones.algoritmo + " ("
				+ menosComparaciones.comparaciones + "). Menos movimientos: "
				+ menosMovimientos.algoritmo + " (" + menosMovimientos.movimientos + " "
				+ menosMovimientos.nombreMovimiento + ").\n");
	}

	private static Resultado crearResultado(String algoritmo, long[] contadores,
			String nombreMovimiento, int[] arreglo, int[] original) {
		int[] referencia = Arrays.copyOf(original, original.length);
		Arrays.sort(referencia); // Solo se usa como referencia para verificar, no para ordenar los resultados.
		return new Resultado(algoritmo, contadores[0], contadores[1], nombreMovimiento,
				estaEnOrden(arreglo) && Arrays.equals(arreglo, referencia));
	}

	/** Compara vecinos; el mayor pendiente avanza al final. */
	private static long[] bubbleSort(int[] arr) {
		long comparaciones = 0;
		long intercambios = 0;

		for (int pasada = 0; pasada < arr.length - 1; pasada++) {
			boolean huboIntercambio = false;
			for (int j = 0; j < arr.length - 1 - pasada; j++) {
				comparaciones++;
				if (arr[j] > arr[j + 1]) {
					intercambiar(arr, j, j + 1);
					intercambios++;
					huboIntercambio = true;
				}
			}
			if (!huboIntercambio) {
				break;
			}
		}
		return new long[]{comparaciones, intercambios};
	}

	/** Busca el mínimo restante; compara siempre la misma cantidad de pares. */
	private static long[] selectionSort(int[] arr) {
		long comparaciones = 0;
		long intercambios = 0;

		for (int i = 0; i < arr.length - 1; i++) {
			int indiceMinimo = i;
			for (int j = i + 1; j < arr.length; j++) {
				comparaciones++;
				if (arr[j] < arr[indiceMinimo]) {
					indiceMinimo = j;
				}
			}
			if (indiceMinimo != i) {
				intercambiar(arr, i, indiceMinimo);
				intercambios++;
			}
		}
		return new long[]{comparaciones, intercambios};
	}

	/** Inserta cada clave en su lugar desplazando a la derecha valores mayores. */
	private static long[] insertionSort(int[] arr) {
		long comparaciones = 0;
		long desplazamientos = 0;

		for (int i = 1; i < arr.length; i++) {
			int clave = arr[i];
			int j = i - 1;
			while (j >= 0) {
				comparaciones++;
				if (arr[j] > clave) {
					arr[j + 1] = arr[j];
					desplazamientos++;
					j--;
				} else {
					break;
				}
			}
			arr[j + 1] = clave;
		}
		return new long[]{comparaciones, desplazamientos};
	}

	private static boolean estaEnOrden(int[] arr) {
		for (int i = 1; i < arr.length; i++) {
			if (arr[i - 1] > arr[i]) {
				return false;
			}
		}
		return true;
	}

	private static void intercambiar(int[] arr, int primero, int segundo) {
		int temporal = arr[primero];
		arr[primero] = arr[segundo];
		arr[segundo] = temporal;
	}

	private static Resultado minimo(Resultado[] resultados, boolean porComparaciones) {
		Resultado minimo = resultados[0];
		for (int i = 1; i < resultados.length; i++) {
			long actual = porComparaciones ? resultados[i].comparaciones : resultados[i].movimientos;
			long menor = porComparaciones ? minimo.comparaciones : minimo.movimientos;
			if (actual < menor) {
				minimo = resultados[i];
			}
		}
		return minimo;
	}

	private static void mostrarTablaGeneral(Escenario[] escenarios) {
		System.out.println("\n=== Tabla comparativa general (comparaciones / movimientos) ===");
		System.out.printf("%-30s | %-24s | %-24s | %-24s%n",
				"Escenario", "Bubble Sort", "Selection Sort", "Insertion Sort");
		System.out.println("-".repeat(111));
		for (Escenario escenario : escenarios) {
			System.out.printf("%-30s | %-24s | %-24s | %-24s%n",
					escenario.nombre,
					formatear(escenario.resultados[0]),
					formatear(escenario.resultados[1]),
					formatear(escenario.resultados[2]));
		}
	}

	private static String formatear(Resultado resultado) {
		return resultado.comparaciones + " / " + resultado.movimientos;
	}

	private static void mostrarExplicacionFinal() {
		System.out.println("\nSelection Sort hace n(n-1)/2 comparaciones en todos los escenarios, "
				+ "pero como máximo n-1 intercambios. Insertion Sort se adapta al orden previo: "
				+ "en arreglos ordenados o casi ordenados necesita pocas comparaciones y desplazamientos. "
				+ "Bubble Sort puede acumular muchos intercambios porque mueve los valores de a una posición.\n"
				+ "Las comparaciones de movimientos de Insertion Sort (desplazamientos) y de los otros "
				+ "dos (intercambios) son conteos distintos; la tabla los muestra según la operación propia de cada algoritmo.");
	}
}
