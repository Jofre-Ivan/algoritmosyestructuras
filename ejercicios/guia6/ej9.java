/*Necesito una cola de impresión en Java implementada con lista enlazada simple, hecha desde cero, sin usar Queue, LinkedList, ArrayList ni ArrayDeque. Uso una clase TrabajoImpresion con nombreArchivo (String), paginas (int) y usuario (String), una clase Nodo con trabajo y siguiente (Nodo), y una clase ColaImpresion con head, tail y size. Al crear la cola, head y tail valen null y size vale 0. Si ya tengo una ColaEnlazada genérica, podés reutilizarla con T = TrabajoImpresion.

Una impresora debe procesar los trabajos con FIFO porque se imprimen en el mismo orden en que fueron enviados: el primer trabajo que llega es el primero que sale. Los trabajos nuevos se agregan siempre al final (tail) y la impresora toma siempre el del principio (head), así ningún trabajo se salta a otro. Con LIFO (una pila) se imprimiría primero el último enviado, y un usuario que mandó su archivo hace rato podría esperar indefinidamente mientras siguen llegando trabajos nuevos, lo cual es injusto. Quiero esta explicación en un comentario arriba de la clase.

Operaciones:
- agregarTrabajo(String archivo, int paginas, String usuario): creo el nodo nuevo; si la cola está vacía, head y tail pasan a ser ese nodo; si no, tail.siguiente apunta al nuevo y después tail pasa a ser el nuevo. Aumento size. O(1) gracias al tail.
- imprimirProximo(): guardo el trabajo del head, hago head = head.siguiente y disminuyo size. Si head quedó en null, también pongo tail en null, porque si no tail seguiría apuntando a un nodo que ya salió. Muestra nombre del archivo, páginas y usuario del trabajo impreso, y lo devuelve. O(1).
- consultarProximo(): devuelve el trabajo del head sin modificar la cola. O(1).
- mostrarPendientes(): recorre desde head con un auxiliar sin modificar la cola y muestra los trabajos en orden de impresión, o "No hay trabajos pendientes". Al final muestra el total de páginas pendientes. O(n).

Casos especiales: imprimirProximo y consultarProximo con la cola vacía muestran o lanzan un mensaje claro ("No hay trabajos pendientes") y no se rompen. No aceptar nombres de archivo ni usuarios vacíos o null, ni cantidades de páginas menores o iguales a 0. Primer trabajo en una cola vacía, cola con un solo trabajo (al imprimirlo head y tail quedan en null), y volver a agregar trabajos después de vaciarla. No existe "cola llena" porque no tiene capacidad fija. La cola no ordena por tamaño ni por usuario, solo por orden de llegada.

Comentá el código en español. TrabajoImpresion tiene que tener toString para mostrar algo legible, por ejemplo "informe.pdf - 10 páginas (usuario: ana)". En el main hacé una demostración fija: cola vacía (consultar, mostrar e imprimir capturando el mensaje), agregar tres trabajos con distinta cantidad de páginas (el último, el más largo), consultar el próximo, mostrar los pendientes, imprimirlos verificando que salen en el mismo orden en que se agregaron aunque el último sea el más largo, y volver a agregar uno después de vaciar la cola. Después agregá un menú por consola con Scanner (agregar trabajo, imprimir próximo, ver próximo, mostrar pendientes, salir).

*/
package ejercicios.guia6;

import java.util.Scanner;

/**
 * Una impresora usa una cola FIFO porque debe imprimir en orden de llegada:
 * el primero enviado es el primero que sale. Los trabajos se agregan al final
 * (tail) y se retiran del principio (head), evitando que alguno se adelante o
 * espere indefinidamente. Una pila LIFO imprimiría primero el último recibido.
 */
public class ej9 {
	private static class TrabajoImpresion {
		private final String nombreArchivo;
		private final int paginas;
		private final String usuario;

		private TrabajoImpresion(String nombreArchivo, int paginas, String usuario) {
			this.nombreArchivo = nombreArchivo;
			this.paginas = paginas;
			this.usuario = usuario;
		}

		@Override
		public String toString() {
			return nombreArchivo + " - " + paginas + " páginas (usuario: " + usuario + ")";
		}
	}

	private static class Nodo {
		private final TrabajoImpresion trabajo;
		private Nodo siguiente;

		private Nodo(TrabajoImpresion trabajo) {
			this.trabajo = trabajo;
		}
	}

	private static class ColaImpresion {
		private Nodo head;
		private Nodo tail;
		private int size;

		private TrabajoImpresion agregarTrabajo(String archivo, int paginas, String usuario) {
			if (archivo == null || archivo.isBlank()) {
				throw new IllegalArgumentException("El nombre del archivo no puede ser null ni estar vacío");
			}
			if (usuario == null || usuario.isBlank()) {
				throw new IllegalArgumentException("El usuario no puede ser null ni estar vacío");
			}
			if (paginas <= 0) {
				throw new IllegalArgumentException("La cantidad de páginas debe ser mayor que cero");
			}

			TrabajoImpresion trabajo = new TrabajoImpresion(archivo.trim(), paginas, usuario.trim());
			Nodo nuevo = new Nodo(trabajo);
			if (head == null) {
				head = nuevo;
				tail = nuevo;
			} else {
				tail.siguiente = nuevo;
				tail = nuevo;
			}
			size++;
			return trabajo;
		}

		/** Retira, informa y devuelve el próximo trabajo de impresión. */
		private TrabajoImpresion imprimirProximo() {
			if (head == null) {
				throw new IllegalStateException("No hay trabajos pendientes");
			}
			TrabajoImpresion trabajo = head.trabajo;
			head = head.siguiente;
			size--;
			if (head == null) {
				tail = null;
			}
			System.out.println("Imprimiendo: " + trabajo);
			return trabajo;
		}

		private TrabajoImpresion consultarProximo() {
			if (head == null) {
				throw new IllegalStateException("No hay trabajos pendientes");
			}
			return head.trabajo;
		}

		private void mostrarPendientes() {
			if (head == null) {
				System.out.println("No hay trabajos pendientes");
				System.out.println("Total de páginas pendientes: 0");
				return;
			}

			long totalPaginas = 0;
			System.out.println("Trabajos en orden de impresión:");
			Nodo actual = head;
			while (actual != null) {
				System.out.println("  " + actual.trabajo);
				totalPaginas += actual.trabajo.paginas;
				actual = actual.siguiente;
			}
			System.out.println("Total de páginas pendientes: " + totalPaginas);
		}

		private int size() {
			return size;
		}
	}

	public static void main(String[] args) {
		ColaImpresion cola = new ColaImpresion();
		demostrarCola(cola);
		iniciarMenu(cola);
	}

	private static void demostrarCola(ColaImpresion cola) {
		System.out.println("=== Demostración de cola FIFO de impresión ===");		
		try {
			cola.consultarProximo();
		} catch (IllegalStateException exception) {
			System.out.println("Consultar cola vacía: " + exception.getMessage());
		}
		cola.mostrarPendientes();
		try {
			cola.imprimirProximo();
		} catch (IllegalStateException exception) {
			System.out.println("Imprimir cola vacía: " + exception.getMessage());
		}

		String[] archivos = {"carta.pdf", "resumen.docx", "manual.pdf"};
		String[] usuarios = {"ana", "luis", "marta"};
		int[] paginas = {2, 5, 18};
		for (int i = 0; i < archivos.length; i++) {
			System.out.println("Agregado: " + cola.agregarTrabajo(archivos[i], paginas[i], usuarios[i])
					+ " | trabajos en cola: " + cola.size());
		}

		System.out.println("Próximo trabajo: " + cola.consultarProximo());
		cola.mostrarPendientes();

		boolean ordenCorrecto = true;
		for (String archivoEsperado : archivos) {
			TrabajoImpresion impreso = cola.imprimirProximo();
			if (!impreso.nombreArchivo.equals(archivoEsperado)) {
				ordenCorrecto = false;
			}
			System.out.println("Trabajos restantes: " + cola.size());
		}
		System.out.println("Verificación FIFO (independiente de la cantidad de páginas): "
				+ (ordenCorrecto ? "CORRECTA" : "INCORRECTA"));
		cola.mostrarPendientes();

		TrabajoImpresion nuevo = cola.agregarTrabajo("nuevo.pdf", 3, "sofia");
		System.out.println("Agregado después de vaciar: " + nuevo + " | trabajos en cola: " + cola.size());
		cola.mostrarPendientes();
		System.out.println();
	}

	private static void iniciarMenu(ColaImpresion cola) {
		try (Scanner scanner = new Scanner(System.in)) {
			boolean continuar = true;
			while (continuar) {
				mostrarMenu();
				String opcion = scanner.nextLine().trim();
				switch (opcion) {
					case "1" -> agregarDesdeMenu(scanner, cola);
					case "2" -> imprimirDesdeMenu(cola);
					case "3" -> consultarDesdeMenu(cola);
					case "4" -> cola.mostrarPendientes();
					case "5" -> {
						System.out.println("Fin de la cola de impresión.");
						continuar = false;
					}
					default -> System.out.println("Opción inválida. Elija del 1 al 5.");
				}
				if (continuar) {
					System.out.println("Trabajos pendientes en la cola: " + cola.size());
					System.out.println();
				}
			}
		}
	}

	private static void mostrarMenu() {
		System.out.println("=== Cola de impresión ===");
		System.out.println("1. Agregar trabajo");
		System.out.println("2. Imprimir próximo");
		System.out.println("3. Ver próximo trabajo");
		System.out.println("4. Mostrar pendientes y páginas");
		System.out.println("5. Salir");
		System.out.print("Seleccione una opción: ");
	}

	private static void agregarDesdeMenu(Scanner scanner, ColaImpresion cola) {
		System.out.print("Nombre del archivo: ");
		String archivo = scanner.nextLine();
		System.out.print("Cantidad de páginas: ");
		String entradaPaginas = scanner.nextLine().trim();
		System.out.print("Usuario: ");
		String usuario = scanner.nextLine();
		try {
			int paginas = Integer.parseInt(entradaPaginas);
			System.out.println("Trabajo agregado: " + cola.agregarTrabajo(archivo, paginas, usuario));
		} catch (NumberFormatException exception) {
			System.out.println("La cantidad de páginas debe ser un número entero.");
		} catch (IllegalArgumentException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private static void imprimirDesdeMenu(ColaImpresion cola) {
		try {
			cola.imprimirProximo();
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private static void consultarDesdeMenu(ColaImpresion cola) {
		try {
			System.out.println("Próximo trabajo: " + cola.consultarProximo());
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage());
		}
	}
}
