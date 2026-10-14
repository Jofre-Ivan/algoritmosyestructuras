/*quiero que implementes  un sistema de ranking de puntajes. El programa recibe un arreglo de jugadores (nombre y puntaje) y lo ordena de mayor a menor puntaje. Además de implementarlo, el programa debe justificar por pantalla por qué se eligió el algoritmo.


Usar MergeSort recursivo, implementado  (sin Arrays.sort() ni Collections.sort() para ordenar), adaptado para ordenar objetos Jugador por puntaje en orden descendente 

Justificación que debe quedar escrita en un comentario del código y también impresa por pantalla:
- Estabilidad: si dos jugadores tienen el mismo puntaje, deben conservar el orden en que fueron cargados. En un ranking los empates son frecuentes, y un algoritmo estable evita que el orden entre empatados cambie de forma arbitraria. Para lograrlo, en la fusión se toma el elemento de la mitad izquierda cuando los puntajes son iguales (comparar con >= en el orden descendente).
- Rendimiento garantizado: O(n log n) en el mejor, promedio y peor caso, sin importar cómo vengan los datos. Un ranking puede crecer a miles de jugadores o llegar ya ordenado, y MergeSort no se degrada.
- Adecuado para objetos: solo necesita comparar y copiar referencias a Jugador.
- Por qué se descartaron los otros: Bubble, Selection e Insertion Sort son O(n²) y escalan mal; Selection Sort además no es estable; Quick Sort con el primer elemento como pivote se degrada a O(n²) con datos ya ordenados y tampoco es estable.
- Costo asumido: usa memoria extra O(n) para el arreglo auxiliar de la fusión, que es aceptable para este problema.


- Divide el arreglo de jugadores siempre por la mitad hasta llegar a subarreglos de 0 o 1 elemento, que ya están ordenados.
- Al volver de la recursión, fusiona dos mitades ya ordenadas comparando el puntaje del primer jugador pendiente de cada mitad y copiando el de mayor puntaje primero (orden descendente). Cuando una mitad se agota, copia el resto de la otra.
- Cada fusión produce una mitad más grande ya ordenada, hasta reconstruir el ranking completo.



mostrar por pantalla
 La justificación de la elección del algoritmo (resumida, 4 o 5 líneas).
 "Ranking original (sin ordenar):" con la lista de jugadores.
"Ranking final:" con el formato "1. Lucia - 1500 puntos", "2. Ana - 1200 puntos", "3. Pedro - 900 puntos".
 Para el arreglo con empates, el ranking final debe mostrar a Ana antes que Marco y a Pedro antes que Sofia (orden de carga entre empatados), con una nota "(empate)" junto a los jugadores que comparten puntaje.
Cantidad de comparaciones realizadas por MergeSort.


- Al inicio de la clase: qué hace el programa y la justificación de MergeSort (estabilidad, O(n log n), memoria extra).
- En la clase Jugador: qué representa cada atributo.
- En el caso base: por qué un subarreglo de un elemento ya está ordenado.
- En el método merge: por qué se usa >= al comparar puntajes (garantiza la estabilidad y el orden de los empatados) y cómo se invierte la lógica para ordenar de mayor a menor.
- Comentarios en español, cortos y claros, pensados para un estudiante que está aprendiendo.


- Comprobar que el ranking final está en orden descendente: puntaje[i] >= puntaje[i+1] para todo i, e imprimir "Orden descendente: CORRECTO" o "INCORRECTO".
- Comprobar que no se perdió ni duplicó ningún jugador: misma cantidad de elementos y mismos nombres que en el arreglo original.
- Comprobar la estabilidad: para dos jugadores con igual puntaje, el que estaba antes en el arreglo original debe aparecer antes en el ranking.
- Comparar el resultado con una copia ordenada con Arrays.sort(copia, comparador por puntaje descendente) (solo para verificar; ese método es estable, por lo que el resultado debe coincidir exactamente) e imprimir "Coincide con la referencia" o "NO coincide".
- Resultado esperado para el ejemplo base: 1. Lucia (1500), 2. Ana (1200), 3. Pedro (900).
- Probar casos borde: arreglo vacío, un solo jugador, todos con el mismo puntaje (el orden original debe mantenerse) y un ranking ya ordenado.
- Prueba de escala: generar 10000 jugadores con puntajes aleatorios (semilla fija en Random), ordenarlos y verificar sin imprimir el ranking completo (solo los 5 primeros y el resultado de la verificación).

 */
package ejercicios.guia3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Construye rankings descendentes estables con MergeSort. Se elige porque
 * garantiza O(n log n) en todos los casos y conserva el orden de carga en empates;
 * requiere memoria auxiliar O(n), aceptable para ordenar referencias a jugadores.
 * Bubble, Selection e Insertion son O(n^2); Selection no es estable y QuickSort
 * con pivote inicial puede degradarse a O(n^2) y tampoco garantiza estabilidad.
 */
public class ej10 {
	private static final long SEMILLA = 20261007L;

	/** Un jugador del ranking, con nombre y puntaje. */
	private static class Jugador {
		private final String nombre;
		private final int puntaje;

		private Jugador(String nombre, int puntaje) {
			this.nombre = nombre;
			this.puntaje = puntaje;
		}

		private String getNombre() {
			return nombre;
		}

		private int getPuntaje() {
			return puntaje;
		}

		@Override
		public String toString() {
			return nombre + " - " + puntaje + " puntos";
		}
	}

	private static class Estadisticas {
		private long comparaciones;
	}

	public static void main(String[] args) {
		mostrarJustificacion();

		Jugador[] ejemplo = {
				new Jugador("Ana", 1200),
				new Jugador("Pedro", 900),
				new Jugador("Lucia", 1500)
		};
		procesarRanking("Ejemplo base", ejemplo, true);

		Jugador[] conEmpates = {
				new Jugador("Ana", 1200),
				new Jugador("Pedro", 900),
				new Jugador("Marco", 1200),
				new Jugador("Lucia", 1500),
				new Jugador("Sofia", 900)
		};
		procesarRanking("Demostración de estabilidad con empates", conEmpates, true);

		procesarRanking("Arreglo vacío", new Jugador[0], true);
		procesarRanking("Un jugador", new Jugador[]{new Jugador("Iris", 700)}, true);
		procesarRanking("Todos con el mismo puntaje", new Jugador[]{
				new Jugador("Leo", 500), new Jugador("Mia", 500), new Jugador("Noa", 500)
		}, true);
		procesarRanking("Ya ordenado", new Jugador[]{
				new Jugador("Ada", 2000), new Jugador("Beto", 1500), new Jugador("Ciro", 1000)
		}, true);

		probarEscala();
	}

	private static void mostrarJustificacion() {
		System.out.println("¿Por qué MergeSort para el ranking?");
		System.out.println("- Es estable: los jugadores empatados conservan el orden de carga.");
		System.out.println("- Garantiza O(n log n) en mejor, promedio y peor caso.");
		System.out.println("- Para objetos, compara puntajes y copia referencias a Jugador.");
		System.out.println("- Bubble, Selection e Insertion son O(n^2); Selection además no es estable.");
		System.out.println("- QuickSort con pivote inicial puede caer en O(n^2) con datos ordenados.");
		System.out.println("- Usa memoria adicional O(n), un costo aceptable para este problema.\n");
	}

	private static void procesarRanking(String titulo, Jugador[] originales, boolean mostrarLista) {
		Jugador[] ranking = Arrays.copyOf(originales, originales.length);
		Estadisticas estadisticas = new Estadisticas();
		System.out.println("=== " + titulo + " ===");
		if (mostrarLista) {
			imprimirLista("Ranking original (sin ordenar):", originales);
		}

		mergeSort(ranking, new Jugador[ranking.length], 0, ranking.length - 1, estadisticas);
		imprimirRanking(ranking, mostrarLista ? "Ranking final:" : "Top del ranking:", mostrarLista ? ranking.length : 5);

		Jugador[] referencia = Arrays.copyOf(originales, originales.length);
		Arrays.sort(referencia, Comparator.comparingInt(Jugador::getPuntaje).reversed());
		boolean ordenDescendente = estaEnOrdenDescendente(ranking);
		boolean mismosJugadores = contieneMismosJugadores(originales, ranking);
		boolean estable = conservaEstabilidad(originales, ranking);
		boolean coincideReferencia = Arrays.equals(ranking, referencia);

		System.out.println("Comparaciones realizadas por MergeSort: " + estadisticas.comparaciones);
		System.out.println("Orden descendente: " + (ordenDescendente ? "CORRECTO" : "INCORRECTO"));
		System.out.println("Mismos jugadores, sin pérdidas ni duplicados: "
				+ (mismosJugadores ? "CORRECTO" : "INCORRECTO"));
		System.out.println("Estabilidad en empates: " + (estable ? "CORRECTA" : "INCORRECTA"));
		System.out.println("Coincide con la referencia: "
				+ (coincideReferencia ? "SÍ" : "NO coincide"));
		System.out.println();
	}

	/** Divide hasta segmentos de cero o un elemento, ya ordenados, y luego los fusiona. */
	private static void mergeSort(Jugador[] jugadores, Jugador[] auxiliar,
			int inicio, int fin, Estadisticas estadisticas) {
		if (inicio >= fin) {
			return;
		}

		int medio = inicio + (fin - inicio) / 2;
		mergeSort(jugadores, auxiliar, inicio, medio, estadisticas);
		mergeSort(jugadores, auxiliar, medio + 1, fin, estadisticas);
		fusionar(jugadores, auxiliar, inicio, medio, fin, estadisticas);
	}

	private static void fusionar(Jugador[] jugadores, Jugador[] auxiliar, int inicio,
			int medio, int fin, Estadisticas estadisticas) {
		int izquierda = inicio;
		int derecha = medio + 1;
		int destino = inicio;

		while (izquierda <= medio && derecha <= fin) {
			estadisticas.comparaciones++;
			// >= ordena puntajes descendentes y, en empate, elige izquierda para mantener estabilidad.
			if (jugadores[izquierda].getPuntaje() >= jugadores[derecha].getPuntaje()) {
				auxiliar[destino++] = jugadores[izquierda++];
			} else {
				auxiliar[destino++] = jugadores[derecha++];
			}
		}

		while (izquierda <= medio) {
			auxiliar[destino++] = jugadores[izquierda++];
		}
		while (derecha <= fin) {
			auxiliar[destino++] = jugadores[derecha++];
		}

		for (int i = inicio; i <= fin; i++) {
			jugadores[i] = auxiliar[i];
		}
	}

	private static void imprimirLista(String titulo, Jugador[] jugadores) {
		System.out.println(titulo);
		for (Jugador jugador : jugadores) {
			System.out.println("  " + jugador);
		}
	}

	private static void imprimirRanking(Jugador[] ranking, String titulo, int limite) {
		System.out.println(titulo);
		int cantidad = Math.min(limite, ranking.length);
		for (int i = 0; i < cantidad; i++) {
			String empate = hayEmpate(ranking, ranking[i].getPuntaje()) ? " (empate)" : "";
			System.out.println("  " + (i + 1) + ". " + ranking[i].getNombre() + " - "
					+ ranking[i].getPuntaje() + " puntos" + empate);
		}
		if (cantidad < ranking.length) {
			System.out.println("  ... (" + (ranking.length - cantidad) + " jugadores más)");
		}
	}

	private static boolean hayEmpate(Jugador[] jugadores, int puntaje) {
		int cantidad = 0;
		for (Jugador jugador : jugadores) {
			if (jugador.getPuntaje() == puntaje) {
				cantidad++;
			}
		}
		return cantidad > 1;
	}

	private static boolean estaEnOrdenDescendente(Jugador[] jugadores) {
		for (int i = 1; i < jugadores.length; i++) {
			if (jugadores[i - 1].getPuntaje() < jugadores[i].getPuntaje()) {
				return false;
			}
		}
		return true;
	}

	private static boolean contieneMismosJugadores(Jugador[] originales, Jugador[] ranking) {
		if (originales.length != ranking.length) {
			return false;
		}
		Map<Jugador, Integer> cantidades = new IdentityHashMap<>();
		for (Jugador jugador : originales) {
			cantidades.put(jugador, cantidades.getOrDefault(jugador, 0) + 1);
		}
		for (Jugador jugador : ranking) {
			Integer cantidad = cantidades.get(jugador);
			if (cantidad == null) {
				return false;
			}
			if (cantidad == 1) {
				cantidades.remove(jugador);
			} else {
				cantidades.put(jugador, cantidad - 1);
			}
		}
		return cantidades.isEmpty();
	}

	private static boolean conservaEstabilidad(Jugador[] originales, Jugador[] ranking) {
		Map<Jugador, Integer> posicionesOriginales = new IdentityHashMap<>();
		for (int i = 0; i < originales.length; i++) {
			posicionesOriginales.put(originales[i], i);
		}
		Map<Integer, Integer> ultimaPosicionPorPuntaje = new HashMap<>();
		for (Jugador jugador : ranking) {
			int posicionOriginal = posicionesOriginales.get(jugador);
			Integer ultimaPosicion = ultimaPosicionPorPuntaje.put(jugador.getPuntaje(), posicionOriginal);
			if (ultimaPosicion != null && ultimaPosicion > posicionOriginal) {
				return false;
			}
		}
		return true;
	}

	private static void probarEscala() {
		int cantidad = 10_000;
		Random random = new Random(SEMILLA);
		Jugador[] jugadores = new Jugador[cantidad];
		for (int i = 0; i < cantidad; i++) {
			jugadores[i] = new Jugador("Jugador" + i, random.nextInt(1_000_000));
		}
		procesarRanking("Prueba de escala: " + cantidad + " jugadores", jugadores, false);
	}
}
