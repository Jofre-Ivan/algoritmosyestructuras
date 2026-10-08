/*Necesito un sistema simple de historial de navegación en Java usando una pila implementada con lista enlazada simple, hecha desde cero, sin usar Stack, ArrayList, LinkedList ni ArrayDeque. Uso una clase Nodo con pagina (String, la URL) y siguiente (Nodo), y una clase HistorialNavegacion con un atributo tope (head) que apunta al primer nodo, y un contador size. Al crear el historial, tope vale null y size vale 0. Si ya tengo una PilaEnlazada genérica hecha, podés reutilizarla con T = String.

Corresponde una pila porque "volver atrás" es LIFO: la última página que visité es la primera de la que salgo. Para llegar a una página visitada antes tengo que ir retrocediendo de a una, en orden inverso al que las visité, y no puedo saltar a una del medio. Una cola no sirve, porque al volver me llevaría a la primera página visitada y no a la anterior. Quiero esta explicación en un comentario arriba de la clase.

Internamente, el head es el tope y representa la página actual. Elijo el head porque es el único extremo donde puedo insertar y sacar sin recorrer la lista, así que visitar y volver son O(1).

Operaciones:
- visitar(String url): creo un nodo nuevo, su siguiente apunta al tope actual y después tope pasa a ser el nodo nuevo (en ese orden, para no perder el historial). Aumento size. O(1).
- volver(): saco la página actual con tope = tope.siguiente, y la página que queda en el tope pasa a ser la actual. El nodo viejo queda sin referencias y el Garbage Collector lo libera. Disminuyo size y devuelvo la página que quedó como actual. O(1).
- paginaActual(): devuelve la URL del tope sin modificar nada. O(1).
- imprimirHistorial(): recorre desde el tope con un auxiliar sin modificar la pila y muestra de la página actual hacia la más antigua, por ejemplo "C (actual) -> B -> A -> null", o "Historial vacío". O(n).

Casos especiales: volver no se puede si hay una sola página, porque no existe página anterior; en ese caso muestra "No hay página anterior" y deja el historial como estaba, sin vaciarlo. Con el historial vacío, paginaActual y volver también muestran un mensaje claro ("Historial vacío") y no devuelven null ni rompen el programa. No aceptar URLs vacías o null en visitar. Contemplar también visitar la misma página dos veces seguidas (se apila igual) y que size coincida siempre con la cantidad real de nodos.

Comentá el código en español. En el main hacé una demostración fija: historial vacío (paginaActual e imprimir), visitar "google.com", "youtube.com" y "wikipedia.org", mostrar la actual, volver dos veces verificando que se pasa por youtube.com y después google.com, intentar volver una vez más (un solo elemento, tiene que avisar), e imprimir el historial. Después agregá un menú por consola con Scanner (visitar, volver, ver página actual, imprimir historial, salir) que muestre la página actual después de cada operación.


 */
package ejercicios.guia6;

import java.util.Scanner;

/**
 * El historial usa una pila LIFO porque volver atrás quita primero la última
 * página visitada. Se retrocede en orden inverso, de a una, y no se puede saltar
 * a una página intermedia. Una cola FIFO no sirve: regresaría a la primera página
 * visitada, no a la anterior a la actual. El tope es el head para visitar y volver
 * en O(1), sin recorrer la lista.
 */
public class ej5 {
	private static class Nodo {
		private final String pagina;
		private final Nodo siguiente;

		private Nodo(String pagina, Nodo siguiente) {
			this.pagina = pagina;
			this.siguiente = siguiente;
		}
	}

	private static class HistorialNavegacion {
		// tope (head) es la página actual; los nodos siguientes son páginas anteriores.
		private Nodo tope;
		private int size;

		private void visitar(String url) {
			if (url == null || url.isBlank()) {
				throw new IllegalArgumentException("La URL no puede ser null ni estar vacía");
			}
			tope = new Nodo(url.trim(), tope);
			size++;
		}

		/** Quita la página actual y devuelve la que queda como actual. */
		private String volver() {
			if (tope == null) {
				throw new IllegalStateException("Historial vacío");
			}
			if (tope.siguiente == null) {
				throw new IllegalStateException("No hay página anterior");
			}
			tope = tope.siguiente;
			size--;
			return tope.pagina;
		}

		private String paginaActual() {
			if (tope == null) {
				throw new IllegalStateException("Historial vacío");
			}
			return tope.pagina;
		}

		private void imprimirHistorial() {
			if (tope == null) {
				System.out.println("Historial vacío");
				return;
			}
			Nodo actual = tope;
			System.out.print(actual.pagina + " (actual)");
			actual = actual.siguiente;
			while (actual != null) {
				System.out.print(" -> " + actual.pagina);
				actual = actual.siguiente;
			}
			System.out.println(" -> null");
		}

		private int size() {
			return size;
		}
	}

	public static void main(String[] args) {
		HistorialNavegacion historial = new HistorialNavegacion();
		demostrarHistorial(historial);
		iniciarMenu(historial);
	}

	private static void demostrarHistorial(HistorialNavegacion historial) {
		System.out.println("=== Demostración del historial ===");
		mostrarPaginaActual(historial);
		historial.imprimirHistorial();
		mostrarSize(historial);

		for (String url : new String[]{"google.com", "youtube.com", "wikipedia.org"}) {
			historial.visitar(url);
			System.out.println("Visitada: " + url);
			mostrarSize(historial);
		}
		mostrarPaginaActual(historial);

		for (int i = 0; i < 2; i++) {
			try {
				System.out.println("Volver -> página actual: " + historial.volver());
				mostrarSize(historial);
			} catch (IllegalStateException exception) {
				System.out.println(exception.getMessage());
			}
		}

		try {
			System.out.println("Volver -> página actual: " + historial.volver());
		} catch (IllegalStateException exception) {
			System.out.println("Intento adicional de volver: " + exception.getMessage()
					+ " (el historial no cambia)");
		}
		historial.imprimirHistorial();
		mostrarSize(historial);
		System.out.println();
	}

	private static void iniciarMenu(HistorialNavegacion historial) {
		try (Scanner scanner = new Scanner(System.in)) {
			boolean continuar = true;
			while (continuar) {
				mostrarMenu();
				String opcion = scanner.nextLine().trim();				
				switch (opcion) {
					case "1" -> visitarPagina(scanner, historial);
					case "2" -> volverPagina(historial);
					case "3" -> mostrarPaginaActual(historial);
					case "4" -> historial.imprimirHistorial();
					case "5" -> {
						System.out.println("Fin del programa.");
						continuar = false;
					}
					default -> System.out.println("Opción inválida. Elija del 1 al 5.");
				}

				if (continuar) {
					mostrarPaginaActual(historial);
					mostrarSize(historial);
					System.out.println();
				}
			}
		}
	}

	private static void mostrarMenu() {
		System.out.println("=== Historial de navegación ===");
		System.out.println("1. Visitar página");
		System.out.println("2. Volver atrás");
		System.out.println("3. Ver página actual");
		System.out.println("4. Imprimir historial");
		System.out.println("5. Salir");
		System.out.print("Seleccione una opción: ");
	}

	private static void visitarPagina(Scanner scanner, HistorialNavegacion historial) {
		System.out.print("Ingrese la URL: ");
		String url = scanner.nextLine();
		try {
			historial.visitar(url);
			System.out.println("Página visitada: " + url.trim());
		} catch (IllegalArgumentException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private static void volverPagina(HistorialNavegacion historial) {
		try {
			System.out.println("Volviste atrás; la página actual es: " + historial.volver());
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private static void mostrarPaginaActual(HistorialNavegacion historial) {
		try {
			System.out.println("Página actual: " + historial.paginaActual());
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private static void mostrarSize(HistorialNavegacion historial) {
		System.out.println("Páginas en el historial: " + historial.size());
	}
}
