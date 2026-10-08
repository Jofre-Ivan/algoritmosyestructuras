/*Necesito un programa en Java que simule una torre de platos usando una pila. Cada plato es un String con su nombre o número (por ejemplo "Plato 1"). Quiero implementar la pila yo mismo con un arreglo de tamaño fijo, sin usar Stack, ArrayDeque ni LinkedList.

El programa tiene que permitir agregar un plato (push), retirar el plato de arriba (pop) y consultar cuál es el plato que está arriba sin sacarlo (peek). Además agregá isEmpty e isFull para controlar los casos límite.

Se resuelve con una pila y no con una cola porque en una torre de platos el último plato que apilo es el primero que saco, o sea LIFO. Solo puedo acceder al plato de arriba. Una cola es FIFO: saldría primero el plato que puse al principio, que está abajo de todo, y eso no tiene sentido con una torre real. Quiero que esta explicación quede en un comentario al inicio de la clase.

Para el arreglo interno uso una variable top que guarda la posición del plato de arriba. Arranca en -1 (torre vacía). En push incremento top y guardo el plato en arreglo[top]; en pop devuelvo arreglo[top] y después decremento top; peek devuelve arreglo[top] sin cambiar top.

Casos límite: si intento agregar un plato con la torre llena, o retirar/consultar con la torre vacía, tiene que lanzar una excepción con un mensaje claro ("Torre llena" o "No hay platos"), no devolver null.

Comentá el codigo. Hacé un main con un menú simple por consola (agregar, retirar, ver el de arriba, salir) usando Scanner, y que muestre un mensaje después de cada operación. Antes del menú, hacé una demostración fija: apilar 3 platos, consultar el de arriba y retirarlos, verificando que salen en orden inverso al que entraron. */
package ejercicios.guia4;

import java.util.Scanner;

/**
 * Simula una torre de platos con una pila porque al retirar se obtiene primero
 * el último plato agregado (LIFO) y solo se accede al de arriba. Una cola sería
 * FIFO y quitaría primero el plato que se puso en la base, lo que no representa
 * el comportamiento de una torre real. La pila se implementa con un arreglo fijo.
 */
public class ej2 {
	private static final int CAPACIDAD_TORRE = 5;

	private static class PilaPlatos {
		private final String[] arreglo;
		// top apunta al plato de arriba; -1 indica que la torre está vacía.
		private int top = -1;

		private PilaPlatos(int capacidad) {
			if (capacidad <= 0) {
				throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
			}
			arreglo = new String[capacidad];
		}

		private void push(String plato) {
			if (isFull()) {
				throw new IllegalStateException("Torre llena");
			}
			if (plato == null || plato.isBlank()) {
				throw new IllegalArgumentException("El nombre del plato no puede estar vacío");
			}
			top++;
			arreglo[top] = plato;
		}

		private String pop() {
			if (isEmpty()) {
				throw new IllegalStateException("No hay platos");
			}
			String plato = arreglo[top];
			top--;
			return plato;
		}

		private String peek() {
			if (isEmpty()) {
				throw new IllegalStateException("No hay platos");
			}
			return arreglo[top];
		}

		private boolean isEmpty() {
			return top == -1;
		}

		private boolean isFull() {
			return top == arreglo.length - 1;
		}
	}

	public static void main(String[] args) {
		demostrarPila();
		iniciarMenu();
	}

	private static void demostrarPila() {
		System.out.println("=== Demostración de la torre LIFO ===");
		PilaPlatos torre = new PilaPlatos(CAPACIDAD_TORRE);
		String[] platos = {"Plato 1", "Plato 2", "Plato 3"};

		for (String plato : platos) {
			torre.push(plato);
			System.out.println("Apilado: " + plato);
		}
		System.out.println("Plato de arriba: " + torre.peek());

		System.out.println("Retirando platos (deben salir en orden inverso):");
		while (!torre.isEmpty()) {
			System.out.println("Retirado: " + torre.pop());
		}
		System.out.println();
	}

	private static void iniciarMenu() {
		PilaPlatos torre = new PilaPlatos(CAPACIDAD_TORRE);
		try (Scanner scanner = new Scanner(System.in)) {
			boolean continuar = true;
			while (continuar) {
				mostrarMenu();
				String opcion = scanner.nextLine().trim();

				switch (opcion) {
					case "1" -> agregarPlato(scanner, torre);
					case "2" -> retirarPlato(torre);
					case "3" -> verPlatoSuperior(torre);
					case "4" -> {
						System.out.println("Fin del programa.");
						continuar = false;
					}
					default -> System.out.println("Opción inválida. Elija del 1 al 4.");
				}
				System.out.println();
			}
		}
	}

	private static void mostrarMenu() {
		System.out.println("=== Torre de platos (capacidad: " + CAPACIDAD_TORRE + ") ===");
		System.out.println("1. Agregar plato");
		System.out.println("2. Retirar plato de arriba");
		System.out.println("3. Ver plato de arriba");
		System.out.println("4. Salir");
		System.out.print("Seleccione una opción: ");
	}

	private static void agregarPlato(Scanner scanner, PilaPlatos torre) {
		System.out.print("Nombre del plato: ");
		String nombre = scanner.nextLine().trim();
		try {
			torre.push(nombre);
			System.out.println("Plato agregado: " + nombre);
		} catch (IllegalStateException | IllegalArgumentException exception) {
			System.out.println("No se pudo agregar: " + exception.getMessage());
		}
	}

	private static void retirarPlato(PilaPlatos torre) {
		try {
			System.out.println("Plato retirado: " + torre.pop());
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private static void verPlatoSuperior(PilaPlatos torre) {
		try {
			System.out.println("Plato de arriba: " + torre.peek());
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage());
		}
	}
}
