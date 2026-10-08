/*Necesito un programa en Java que simule una cola de impresión usando una cola. Quiero implementar la cola yo mismo con un arreglo circular de tamaño fijo, sin usar Queue, LinkedList, ArrayList ni ArrayDeque. Cada documento se representa con una clase Documento con nombre (String) y cantidad de páginas (int).

El sistema tiene que permitir agregar un documento a la cola (enqueue), imprimir el siguiente documento (dequeue), que lo saca de la cola y muestra su nombre y cantidad de páginas, y consultar cuál es el próximo a imprimir sin sacarlo (front). También quiero mostrar la cola completa en orden de impresión sin modificarla, y calcular el total de páginas pendientes recorriendo la cola sin sacar elementos.

Una cola representa correctamente el orden de impresión porque los documentos se imprimen en el mismo orden en que fueron agregados: el primero que se manda a imprimir es el primero que sale, o sea FIFO. Los documentos nuevos se agregan siempre al final (rear) y la impresora toma siempre el del principio (front), así ningún documento se salta a otro ni queda esperando indefinidamente. Con una pila se imprimiría primero el último documento enviado y los anteriores quedarían atrasados. Quiero esta explicación en un comentario al inicio de la clase.

Para el arreglo interno uso dos índices. front es la posición del próximo documento a imprimir y rear es la posición del último documento agregado. Arrancan en front = 0 y rear = -1, con un contador size en 0. En enqueue, rear avanza con rear = (rear + 1) % capacidad, guardo el documento en arreglo[rear] y aumento size. En dequeue, leo arreglo[front], avanzo front con front = (front + 1) % capacidad, pongo esa posición en null, disminuyo size y devuelvo el documento. El módulo hace el arreglo circular para reutilizar los lugares de los documentos ya impresos. isEmpty es size == 0 e isFull es size == capacidad.

Casos límite: no se puede agregar un documento si la cola está llena (mensaje "La cola de impresión está llena") ni imprimir o consultar si está vacía (mensaje "No hay documentos pendientes"); en ambos casos el programa no debe romperse. No aceptar nombres vacíos ni cantidades de páginas menores o iguales a 0. Tiene que funcionar bien cuando los índices dan la vuelta (llenar la cola, imprimir algunos, agregar más) manteniendo el orden de llegada, y el total de páginas pendientes tiene que seguir siendo correcto en ese caso.

Comentá el código . Hacé un main con un menú por consola con Scanner (agregar documento, imprimir siguiente, ver próximo, mostrar cola, ver total de páginas pendientes, salir), que muestre un mensaje después de cada operación. Antes del menú, hacé una demostración fija: agregar tres documentos con distinta cantidad de páginas, mostrar el total pendiente, imprimirlos y verificar que salen en el mismo orden en que se agregaron. */
package ejercicios.guia4;

import java.util.Scanner;

/**
 * Simula una cola de impresión FIFO: los documentos se imprimen en el mismo
 * orden en que fueron enviados. Los nuevos se agregan al final (rear) y la
 * impresora toma el primero (front), para que ninguno se adelante o quede
 * postergado. Una pila imprimiría primero el último documento recibido.
 * La cola se implementa con un arreglo circular de capacidad fija.
 */
public class ej8 {
	private static final int CAPACIDAD_COLA = 5;

	private static class Documento {
		private final String nombre;
		private final int paginas;

		private Documento(String nombre, int paginas) {
			this.nombre = nombre;
			this.paginas = paginas;
		}

		@Override
		public String toString() {
			return nombre + " (" + paginas + " páginas)";
		}
	}

	private static class ColaImpresion {
		private final Documento[] arreglo;
		// front señala el próximo documento a imprimir; rear, el último agregado.
		private int front = 0;
		private int rear = -1;
		private int size = 0;

		private ColaImpresion(int capacidad) {
			if (capacidad <= 0) {
				throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
			}
			arreglo = new Documento[capacidad];
		}

		private void enqueue(String nombre, int paginas) {
			if (isFull()) {
				throw new IllegalStateException("La cola de impresión está llena");
			}
			if (nombre == null || nombre.isBlank()) {
				throw new IllegalArgumentException("El nombre del documento no puede estar vacío");
			}
			if (paginas <= 0) {
				throw new IllegalArgumentException("La cantidad de páginas debe ser mayor que cero");
			}

			// El módulo hace que rear vuelva a cero y reutilice espacios ya impresos.
			rear = (rear + 1) % arreglo.length;
			arreglo[rear] = new Documento(nombre.trim(), paginas);
			size++;
		}

		private Documento dequeue() {
			if (isEmpty()) {
				throw new IllegalStateException("No hay documentos pendientes");
			}
			Documento documento = arreglo[front];
			arreglo[front] = null;
			// front avanza circularmente al documento que sigue en orden FIFO.
			front = (front + 1) % arreglo.length;
			size--;
			return documento;
		}

		private Documento front() {
			if (isEmpty()) {
				throw new IllegalStateException("No hay documentos pendientes");
			}
			return arreglo[front];
		}

		private boolean isEmpty() {
			return size == 0;
		}

		private boolean isFull() {
			return size == arreglo.length;
		}

		private int size() {
			return size;
		}

		/** Suma páginas recorriendo la cola en orden sin retirar documentos. */
		private long totalPaginasPendientes() {
			long total = 0;
			for (int i = 0; i < size; i++) {
				int indice = (front + i) % arreglo.length;
				total += arreglo[indice].paginas;
			}
			return total;
		}

		/** Muestra los documentos en orden de impresión sin cambiar la cola. */
		private void mostrarCola() {
			if (isEmpty()) {
				System.out.println("No hay documentos pendientes.");
				return;
			}
			System.out.println("Documentos en orden de impresión:");
			for (int i = 0; i < size; i++) {
				int indice = (front + i) % arreglo.length;
				System.out.println("  " + (i + 1) + ". " + arreglo[indice]);
			}
		}
	}

	public static void main(String[] args) {
		ColaImpresion cola = new ColaImpresion(CAPACIDAD_COLA);
		demostrarImpresion(cola);
		iniciarMenu(cola);
	}

	private static void demostrarImpresion(ColaImpresion cola) {
		System.out.println("=== Demostración FIFO de impresión ===");
		String[] nombresEsperados = {"Informe", "Presentación", "Apuntes"};
		int[] cantidadesPaginas = {4, 12, 7};
		for (int i = 0; i < nombresEsperados.length; i++) {
			cola.enqueue(nombresEsperados[i], cantidadesPaginas[i]);
			System.out.println("Agregado: " + nombresEsperados[i] + " ("
					+ cantidadesPaginas[i] + " páginas)");
		}
		System.out.println("Total de páginas pendientes: " + cola.totalPaginasPendientes());

		boolean ordenCorrecto = true;
		System.out.println("Imprimiendo documentos en orden de llegada:");
		for (String nombreEsperado : nombresEsperados) {
			Documento impreso = cola.dequeue();
			System.out.println("Impreso: " + impreso);
			if (!impreso.nombre.equals(nombreEsperado)) {
				ordenCorrecto = false;
			}
		}
		System.out.println("Verificación del orden FIFO: " + (ordenCorrecto ? "CORRECTA" : "INCORRECTA"));
		System.out.println("Páginas pendientes después de imprimir: " + cola.totalPaginasPendientes() + "\n");
	}

	private static void iniciarMenu(ColaImpresion cola) {
		try (Scanner scanner = new Scanner(System.in)) {
			boolean continuar = true;
			while (continuar) {
				mostrarMenu(cola.size());
				String opcion = scanner.nextLine().trim();				
				switch (opcion) {
					case "1" -> agregarDocumento(scanner, cola);
					case "2" -> imprimirSiguiente(cola);
					case "3" -> verProximo(cola);
					case "4" -> cola.mostrarCola();
					case "5" -> System.out.println("Páginas pendientes: " + cola.totalPaginasPendientes());
					case "6" -> {
						System.out.println("Fin de la cola de impresión.");
						continuar = false;
					}
					default -> System.out.println("Opción inválida. Elija del 1 al 6.");
				}
				if (continuar) {
					System.out.println("Documentos pendientes: " + cola.size()
							+ " | Páginas pendientes: " + cola.totalPaginasPendientes() + "\n");
				}
			}
		}
	}

	private static void mostrarMenu(int pendientes) {
		System.out.println("=== Cola de impresión (" + pendientes + "/" + CAPACIDAD_COLA + ") ===");
		System.out.println("1. Agregar documento");
		System.out.println("2. Imprimir siguiente documento");
		System.out.println("3. Ver próximo documento");
		System.out.println("4. Mostrar cola completa");
		System.out.println("5. Ver total de páginas pendientes");
		System.out.println("6. Salir");
		System.out.print("Seleccione una opción: ");
	}

	private static void agregarDocumento(Scanner scanner, ColaImpresion cola) {
		System.out.print("Nombre del documento: ");
		String nombre = scanner.nextLine();
		System.out.print("Cantidad de páginas: ");
		String entradaPaginas = scanner.nextLine().trim();
		try {
			int paginas = Integer.parseInt(entradaPaginas);
			cola.enqueue(nombre, paginas);
			System.out.println("Documento agregado a la cola: " + nombre.trim());
		} catch (NumberFormatException exception) {
			System.out.println("La cantidad de páginas debe ser un número entero.");
		} catch (IllegalArgumentException | IllegalStateException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private static void imprimirSiguiente(ColaImpresion cola) {
		try {
			System.out.println("Imprimiendo: " + cola.dequeue());
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private static void verProximo(ColaImpresion cola) {
		try {
			System.out.println("Próximo documento: " + cola.front());
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage());
		}
	}
}
