/*Necesito un programa en Java que simule el historial de navegación de un navegador usando una pila de URLs (String). Quiero implementar la pila yo mismo con un arreglo de tamaño fijo, sin usar Stack, ArrayDeque ni LinkedList.

Cada vez que el usuario visita una página, se hace push de la URL. La URL que está arriba de la pila es la página actual. Para volver a la página anterior se hace pop: se saca la página actual y la que queda arriba pasa a ser la actual. También quiero poder consultar la página actual sin sacarla (peek) y mostrar el historial completo de arriba hacia abajo, sin modificar la pila.

El historial funciona bajo el principio LIFO (último en entrar, primero en salir) porque la última página que visité es la primera de la que salgo al tocar "atrás". Para llegar a una página que visité antes tengo que ir retrocediendo una por una, en orden inverso al que las visité, y no puedo saltar directamente a una del medio. Una cola no sirve, porque al volver me llevaría a la primera página visitada y no a la anterior. Quiero esta explicación en un comentario al inicio de la clase.

Para el arreglo interno uso una variable top que guarda la posición de la página actual. Arranca en -1 (sin historial). En push incremento top y guardo la URL en arreglo[top]; en pop devuelvo arreglo[top] y decremento top.

Casos límite: no se puede volver si hay una sola página en el historial, porque no hay página anterior, y en ese caso tiene que mostrar un mensaje claro y dejar la pila como estaba. Tampoco se puede consultar la actual ni volver con el historial vacío. Si el historial se llena, tiene que avisar con un mensaje en lugar de romperse. No aceptar URLs vacías.

Comentá el codigo   . Hacé un main con un menú por consola con Scanner (visitar página, volver, ver página actual, mostrar historial, salir), que muestre la página actual después de cada operación. Antes del menú, hacé una demostración fija: visitar tres páginas, volver dos veces y verificar que se pasa por las páginas en orden inverso. */
package ejercicios.guia4;

import java.util.Scanner;

/**
 * El historial usa una pila LIFO: la última URL visitada es la primera que se
 * quita al volver, y se retrocede de una página en una. Una cola FIFO no sirve,
 * porque sacaría primero la página más antigua, no la anterior a la actual.
 * La pila se implementa con un arreglo de tamaño fijo.
 */
public class ej4 {
	private static final int CAPACIDAD_HISTORIAL = 10;

	private static class PilaURLs {
		private final String[] arreglo;
		// top señala la URL actual; -1 significa que todavía no hay páginas visitadas.
		private int top = -1;

		private PilaURLs(int capacidad) {
			if (capacidad <= 0) {
				throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
			}
			arreglo = new String[capacidad];
		}

		private void push(String url) {
			if (isFull()) {
				throw new IllegalStateException("El historial está lleno");
			}
			if (url == null || url.isBlank()) {
				throw new IllegalArgumentException("La URL no puede estar vacía");
			}
			arreglo[++top] = url;
		}

		private String pop() {
			if (isEmpty()) {
				throw new IllegalStateException("No hay historial para retroceder");
			}
			String url = arreglo[top];
			top--;
			return url;
		}

		private String peek() {
			if (isEmpty()) {
				throw new IllegalStateException("No hay una página actual");
			}
			return arreglo[top];
		}

		private boolean isEmpty() {
			return top == -1;
		}

		private boolean isFull() {
			return top == arreglo.length - 1;
		}

		private int size() {
			return top + 1;
		}

		/** Muestra desde la página actual hacia atrás sin cambiar top ni el arreglo. */
		private void mostrarDeArribaHaciaAbajo() {
			if (isEmpty()) {
				System.out.println("El historial está vacío.");
				return;
			}
			System.out.println("Historial (de actual a más antigua):");
			for (int i = top; i >= 0; i--) {
				System.out.println("  " + (i == top ? "[actual] " : "          ") + arreglo[i]);
			}
		}
	}

	public static void main(String[] args) {
		demostrarNavegacion();
		iniciarMenu();
	}

	private static void demostrarNavegacion() {
		System.out.println("=== Demostración del historial LIFO ===");
		PilaURLs historial = new PilaURLs(CAPACIDAD_HISTORIAL);
		String[] paginas = {
				"https://ejemplo.com/inicio",
				"https://ejemplo.com/noticias",
				"https://ejemplo.com/noticias/deportes"
		};

		for (String url : paginas) {
			historial.push(url);
			System.out.println("Visitada: " + url + " | Página actual: " + historial.peek());
		}

		System.out.println("Al volver, se recorren las páginas en orden inverso:");
		for (int i = 0; i < 2; i++) {
			String anterior = volver(historial);
			System.out.println("Se quitó: " + anterior + " | Página actual: " + paginaActual(historial));
		}
		System.out.println();
	}

	private static void iniciarMenu() {
		PilaURLs historial = new PilaURLs(CAPACIDAD_HISTORIAL);
		try (Scanner scanner = new Scanner(System.in)) {
			boolean continuar = true;
			while (continuar) {
				mostrarMenu();
				String opcion = scanner.nextLine().trim();

				switch (opcion) {
					case "1" -> visitarPagina(scanner, historial);
					case "2" -> {
						String urlQuitada = volver(historial);
						if (urlQuitada != null) {
							System.out.println("Volviste desde: " + urlQuitada);
						}
					}
					case "3" -> verPaginaActual(historial);
					case "4" -> historial.mostrarDeArribaHaciaAbajo();
					case "5" -> {
						System.out.println("Saliendo del navegador simulado.");
						continuar = false;
					}
					default -> System.out.println("Opción inválida. Elija del 1 al 5.");
				}

				if (continuar) {
					System.out.println("Página actual después de la operación: " + paginaActual(historial));
					System.out.println();
				}
			}
		}
	}

	private static void mostrarMenu() {
		System.out.println("=== Navegador (capacidad del historial: " + CAPACIDAD_HISTORIAL + ") ===");
		System.out.println("1. Visitar página");
		System.out.println("2. Volver a la página anterior");
		System.out.println("3. Ver página actual");
		System.out.println("4. Mostrar historial completo");
		System.out.println("5. Salir");
		System.out.print("Seleccione una opción: ");
	}

	private static void visitarPagina(Scanner scanner, PilaURLs historial) {
		System.out.print("Ingrese la URL: ");
		String url = scanner.nextLine().trim();
		try {
			historial.push(url);
			System.out.println("Página visitada: " + url);
		} catch (IllegalArgumentException | IllegalStateException exception) {
			System.out.println("No se pudo visitar la página: " + exception.getMessage());
		}
	}

	private static String volver(PilaURLs historial) {
		if (historial.size() <= 1) {
			System.out.println(historial.isEmpty()
					? "No hay historial para volver."
					: "No hay una página anterior; el historial queda sin cambios.");
			return null;
		}
		return historial.pop();
	}

	private static void verPaginaActual(PilaURLs historial) {
		if (historial.isEmpty()) {
			System.out.println("No hay una página actual: el historial está vacío.");
			return;
		}
		System.out.println("Página actual: " + historial.peek());
	}

	private static String paginaActual(PilaURLs historial) {
		return historial.isEmpty() ? "ninguna (historial vacío)" : historial.peek();
	}
}
