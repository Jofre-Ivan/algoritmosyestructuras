/*Necesito un sistema de atención para un banco en Java usando una cola implementada con lista enlazada simple, hecha desde cero, sin usar Queue, LinkedList, ArrayList ni ArrayDeque. Uso una clase Cliente con nombre (String), numeroTurno (int) y motivo (String), una clase Nodo con cliente y siguiente (Nodo), y una clase ColaBanco con head, tail y size. Al crear la cola, head y tail valen null y size vale 0. Si ya tengo una ColaEnlazada genérica hecha, podés reutilizarla con T = Cliente.

La estructura adecuada es una cola porque en un banco se atiende por orden de llegada: el primer cliente que llega es el primero en ser atendido (FIFO). Con una pila se atendería primero al último que llegó y los que esperan desde antes quedarían postergados indefinidamente, lo cual es injusto. El orden de llegada se representa con la posición de los nodos en la lista: el head es el cliente que lleva más tiempo esperando (el próximo a atender) y el tail es el último que llegó. Los clientes nuevos se agregan siempre en el tail y se atiende siempre desde el head, así ninguno se adelanta a otro. Quiero esta explicación en un comentario arriba de la clase.

Operaciones:
- agregarCliente(String nombre, String motivo): crea el Cliente asignándole automáticamente el siguiente número de turno (1, 2, 3...), que no se reutiliza ni se reinicia al atender. Creo el nodo nuevo; si la cola está vacía, head y tail pasan a ser ese nodo; si no, tail.siguiente apunta al nuevo y después tail pasa a ser el nuevo. Aumento size. O(1) gracias al tail, sin recorrer la lista.
- atenderSiguiente(): guarda el cliente del head, hace head = head.siguiente y disminuye size. Si head quedó en null, también pongo tail en null, porque si no tail seguiría apuntando a un nodo que ya salió. Devuelve el cliente atendido y muestra su nombre, turno y motivo. O(1).
- consultarSiguiente(): devuelve el cliente del head sin modificar la cola. O(1).
- imprimirFila(): recorre desde head con un auxiliar sin modificar la cola y muestra los clientes en orden de llegada, con turno, nombre y motivo, o "No hay clientes en espera". O(n).

Casos especiales: atenderSiguiente y consultarSiguiente con la fila vacía muestran o lanzan un mensaje claro ("No hay clientes en espera") y no se rompen. No aceptar nombres ni motivos vacíos o null. Primer cliente en una fila vacía, fila con un solo cliente (al atenderlo head y tail quedan en null), y volver a agregar clientes después de vaciar la fila, verificando que el número de turno siga aumentando. No existe "fila llena" porque no tiene capacidad fija.

Comentá el código en español. Cliente tiene que tener toString para que imprimirFila muestre algo legible, por ejemplo "Turno 3 - Ana (Apertura de cuenta)". En el main hacé una demostración fija: fila vacía (consultar e imprimir), agregar tres clientes con distintos motivos, consultar quién sigue, imprimir la fila, atenderlos verificando que salen en el mismo orden de llegada, volver a agregar uno y verificar que su turno es el 4, y un atender con la fila vacía. Después agregá un menú por consola con Scanner (agregar cliente, atender siguiente, ver quién sigue, imprimir fila, salir) que muestre un mensaje después de cada operación.

 */
package ejercicios.guia6;

import java.util.Scanner;

/**
 * Un banco necesita una cola FIFO porque atiende en orden de llegada. En la
 * lista, head es el cliente que lleva más tiempo esperando y tail es el último
 * que llegó. Agregar por tail y atender por head evita adelantar clientes y,
 * gracias a ambos extremos, las dos operaciones son O(1). Una pila LIFO sería
 * injusta: atendería primero al último en llegar y postergaría a los anteriores.
 */
public class ej6 {
	private static class Cliente {
		private final String nombre;
		private final int numeroTurno;
		private final String motivo;

		private Cliente(String nombre, int numeroTurno, String motivo) {
			this.nombre = nombre;
			this.numeroTurno = numeroTurno;
			this.motivo = motivo;
		}

		@Override
		public String toString() {
			return "Turno " + numeroTurno + " - " + nombre + " (" + motivo + ")";
		}
	}

	private static class Nodo {
		private final Cliente cliente;
		private Nodo siguiente;

		private Nodo(Cliente cliente) {
			this.cliente = cliente;
		}
	}

	private static class ColaBanco {
		private Nodo head;
		private Nodo tail;
		private int size;
		// Este contador nunca disminuye: atender clientes no reutiliza sus turnos.
		private int siguienteTurno = 1;

		private Cliente agregarCliente(String nombre, String motivo) {
			if (nombre == null || nombre.isBlank()) {
				throw new IllegalArgumentException("El nombre no puede ser null ni estar vacío");
			}
			if (motivo == null || motivo.isBlank()) {
				throw new IllegalArgumentException("El motivo no puede ser null ni estar vacío");
			}

			Cliente cliente = new Cliente(nombre.trim(), siguienteTurno++, motivo.trim());
			Nodo nuevo = new Nodo(cliente);
			if (head == null) {
				head = nuevo;
				tail = nuevo;
			} else {
				tail.siguiente = nuevo;
				tail = nuevo;
			}
			size++;
			return cliente;
		}

		private Cliente atenderSiguiente() {
			if (head == null) {
				throw new IllegalStateException("No hay clientes en espera");
			}
			Cliente clienteAtendido = head.cliente;
			head = head.siguiente;
			size--;
			if (head == null) {
				tail = null;
			}
			return clienteAtendido;
		}

		private Cliente consultarSiguiente() {
			if (head == null) {
				throw new IllegalStateException("No hay clientes en espera");
			}
			return head.cliente;
		}

		private void imprimirFila() {
			if (head == null) {
				System.out.println("No hay clientes en espera");
				return;
			}
			Nodo actual = head;
			while (actual != null) {
				System.out.println(actual.cliente);
				actual = actual.siguiente;
			}
		}

		private boolean estaVacia() {
			return head == null;
		}

		private int size() {
			return size;
		}
	}

	public static void main(String[] args) {
		ColaBanco cola = new ColaBanco();
		demostrarAtencion(cola);
		iniciarMenu(cola);
	}

	private static void demostrarAtencion(ColaBanco cola) {
		System.out.println("=== Demostración de atención FIFO ===");
		mostrarSiguiente(cola);
		cola.imprimirFila();
		mostrarSize(cola);

		String[] nombres = {"Ana", "Luis", "Marta"};
		String[] motivos = {"Apertura de cuenta", "Depósito", "Consulta de saldo"};
		for (int i = 0; i < nombres.length; i++) {
			Cliente agregado = cola.agregarCliente(nombres[i], motivos[i]);
			System.out.println("Agregado: " + agregado + " | size=" + cola.size());
		}

		mostrarSiguiente(cola);
		System.out.println("Fila en orden de llegada:");
		cola.imprimirFila();
		mostrarSize(cola);

		boolean ordenCorrecto = true;
		System.out.println("Atendiendo a los clientes:");
		for (int i = 0; i < nombres.length; i++) {
			Cliente atendido = cola.atenderSiguiente();
			System.out.println("Atendido: " + atendido + " | size=" + cola.size());
			if (!atendido.nombre.equals(nombres[i])) {
				ordenCorrecto = false;
			}
		}
		System.out.println("Verificación del orden FIFO: "
				+ (ordenCorrecto ? "CORRECTA" : "INCORRECTA"));
		System.out.println("Fila vacía tras atender a los tres clientes: " + cola.estaVacia());

		Cliente cuarto = cola.agregarCliente("Sofía", "Actualización de datos");
		System.out.println("Después de vaciar, se agrega: " + cuarto
				+ " | se esperaba turno 4 | size=" + cola.size());

		// La fila no está vacía porque ya se registró al cuarto cliente; se prueba el error
		// de atención vacía después de extraerlo.
		System.out.println("Atendido: " + cola.atenderSiguiente() + " | size=" + cola.size());
		try {
			cola.atenderSiguiente();
		} catch (IllegalStateException exception) {
			System.out.println("Atender con fila vacía: " + exception.getMessage()
					+ " | size=" + cola.size());
		}
		System.out.println();
	}

	private static void iniciarMenu(ColaBanco cola) {
		try (Scanner scanner = new Scanner(System.in)) {
			boolean continuar = true;
			while (continuar) {
				mostrarMenu();
				String opcion = scanner.nextLine().trim();
				switch (opcion) {
					case "1" -> agregarDesdeMenu(scanner, cola);
					case "2" -> atenderDesdeMenu(cola);
					case "3" -> mostrarSiguiente(cola);
					case "4" -> {
						System.out.println("Fila de espera:");
						cola.imprimirFila();
					}
					case "5" -> {
						System.out.println("Fin del sistema de atención.");
						continuar = false;
					}
					default -> System.out.println("Opción inválida. Elija del 1 al 5.");
				}

				if (continuar) {
					mostrarSiguiente(cola);
					mostrarSize(cola);
					System.out.println();
				}
			}
		}
	}

	private static void mostrarMenu() {
		System.out.println("=== Banco: atención por turnos ===");
		System.out.println("1. Agregar cliente");
		System.out.println("2. Atender siguiente");
		System.out.println("3. Consultar quién sigue");
		System.out.println("4. Imprimir fila");
		System.out.println("5. Salir");
		System.out.print("Seleccione una opción: ");
	}

	private static void agregarDesdeMenu(Scanner scanner, ColaBanco cola) {
		System.out.print("Nombre del cliente: ");
		String nombre = scanner.nextLine();
		System.out.print("Motivo de la visita: ");
		String motivo = scanner.nextLine();
		try {
			System.out.println("Cliente agregado: " + cola.agregarCliente(nombre, motivo));
		} catch (IllegalArgumentException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private static void atenderDesdeMenu(ColaBanco cola) {
		try {
			System.out.println("Atendido: " + cola.atenderSiguiente());
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private static void mostrarSiguiente(ColaBanco cola) {
		try {
			System.out.println("Siguiente cliente: " + cola.consultarSiguiente());
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private static void mostrarSize(ColaBanco cola) {
		System.out.println("Clientes en espera: " + cola.size());
	}
}
