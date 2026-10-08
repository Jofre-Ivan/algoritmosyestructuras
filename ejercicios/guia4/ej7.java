/*Necesito un programa en Java que simule un sistema de turnos para una oficina usando una cola. Quiero implementar la cola yo mismo con un arreglo circular de tamaño fijo, sin usar Queue, LinkedList, ArrayList ni ArrayDeque. Cada persona se representa con una clase Persona con nombre (String) y número de turno (int).

El sistema tiene que permitir agregar una persona (enqueue), que recibe el siguiente número de turno automáticamente (1, 2, 3...), atender a la siguiente persona (dequeue), que la saca de la cola y muestra su nombre y turno, y consultar quién está primero sin sacarlo (front). También quiero poder mostrar cuántas personas están esperando y listar la cola en orden de llegada sin modificarla.

Este problema usa FIFO (primero en entrar, primero en salir) y no LIFO porque en una oficina se atiende por orden de llegada: la persona que llegó primero es la primera en ser atendida. Con LIFO (una pila) atendería primero al último que llegó, y los que llegaron antes quedarían esperando indefinidamente mientras siguen llegando personas nuevas, lo cual es injusto. Quiero esta explicación en un comentario al inicio de la clase.

Para el arreglo interno uso dos índices. front es la posición de la persona que será atendida próximamente y rear es la posición de la última persona que llegó. Arrancan en front = 0 y rear = -1, con un contador size en 0. En enqueue, rear avanza con rear = (rear + 1) % capacidad, guardo la persona en arreglo[rear] y aumento size. En dequeue, leo arreglo[front], avanzo front con front = (front + 1) % capacidad, pongo esa posición en null, disminuyo size y devuelvo la persona. El módulo hace el arreglo circular para reutilizar los lugares de las personas ya atendidas. isEmpty es size == 0 e isFull es size == capacidad.

Casos límite: no se puede agregar una persona si la cola está llena (mensaje "No hay más turnos disponibles") ni atender o consultar si está vacía (mensaje "No hay personas esperando"); en ambos casos el programa no debe romperse. No aceptar nombres vacíos. El número de turno no debe reutilizarse ni reiniciarse cuando se atiende a alguien. Tiene que funcionar bien cuando los índices dan la vuelta (llenar, atender a algunos, agregar más) manteniendo el orden de llegada.

Comentá el código . Hacé un main con un menú por consola con Scanner (agregar persona, atender siguiente, ver quién está primero, ver cola de espera, salir), que muestre un mensaje después de cada operación. Antes del menú, hacé una demostración fija: agregar tres personas, consultar quién está primero, atenderlas y verificar que salen en el mismo orden en que llegaron. */
package ejercicios.guia4;

import java.util.Scanner;

/**
 * Simula turnos de oficina con una cola FIFO: la primera persona que llega es
 * atendida primero. Una pila LIFO atendería a la última llegada y podría dejar
 * esperando indefinidamente a quienes llegaron antes. La cola usa un arreglo
 * circular fijo para reutilizar los espacios liberados.
 */
public class ej7 {
	private static final int CAPACIDAD_COLA = 5;

	private static class Persona {
		private final String nombre;
		private final int numeroTurno;

		private Persona(String nombre, int numeroTurno) {
			this.nombre = nombre;
			this.numeroTurno = numeroTurno;
		}

		@Override
		public String toString() {
			return nombre + " (turno " + numeroTurno + ")";
		}
	}

	private static class ColaTurnos {
		private final Persona[] arreglo;
		// front apunta a quien será atendido; rear apunta a la última persona agregada.
		private int front = 0;
		private int rear = -1;
		private int size = 0;
		// Se incrementa al asignar turnos y nunca disminuye al atender personas.
		private int siguienteTurno = 1;

		private ColaTurnos(int capacidad) {
			if (capacidad <= 0) {
				throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
			}
			arreglo = new Persona[capacidad];
		}

		private Persona enqueue(String nombre) {
			if (isFull()) {
				throw new IllegalStateException("No hay más turnos disponibles");
			}
			if (nombre == null || nombre.isBlank()) {
				throw new IllegalArgumentException("El nombre no puede estar vacío");
			}

			Persona persona = new Persona(nombre.trim(), siguienteTurno++);
			// El módulo vuelve rear a cero al llegar al final y reutiliza espacios libres.
			rear = (rear + 1) % arreglo.length;
			arreglo[rear] = persona;
			size++;
			return persona;
		}

		private Persona dequeue() {
			if (isEmpty()) {
				throw new IllegalStateException("No hay personas esperando");
			}
			Persona persona = arreglo[front];
			arreglo[front] = null;
			// front avanza circularmente hasta la siguiente persona que llegó.
			front = (front + 1) % arreglo.length;
			size--;
			return persona;
		}

		private Persona front() {
			if (isEmpty()) {
				throw new IllegalStateException("No hay personas esperando");
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

		/** Lista en orden FIFO sin retirar ni reordenar personas. */
		private void mostrarCola() {
			if (isEmpty()) {
				System.out.println("No hay personas esperando.");
				return;
			}
			System.out.println("Cola de espera (orden de llegada):");
			for (int i = 0; i < size; i++) {
				int indice = (front + i) % arreglo.length;
				System.out.println("  " + arreglo[indice]);
			}
		}
	}

	public static void main(String[] args) {
		ColaTurnos cola = new ColaTurnos(CAPACIDAD_COLA);
		demostrarOrdenDeLlegada(cola);
		iniciarMenu(cola);
	}

	private static void demostrarOrdenDeLlegada(ColaTurnos cola) {
		System.out.println("=== Demostración FIFO ===");
		String[] nombres = {"Ana", "Bruno", "Carla"};
		for (String nombre : nombres) {
			System.out.println("Agregada: " + cola.enqueue(nombre));
		}

		System.out.println("Primera persona en la cola: " + cola.front());
		boolean ordenCorrecto = true;
		System.out.println("Atendiendo en el orden en que llegaron:");
		for (String nombreEsperado : nombres) {
			Persona atendida = cola.dequeue();
			System.out.println("Atendida: " + atendida);
			if (!atendida.nombre.equals(nombreEsperado)) {
				ordenCorrecto = false;
			}
		}
		System.out.println("Verificación del orden FIFO: " + (ordenCorrecto ? "CORRECTA" : "INCORRECTA"));
		System.out.println("Próximo turno disponible: " + cola.siguienteTurno + "\n");
	}

	private static void iniciarMenu(ColaTurnos cola) {
		try (Scanner scanner = new Scanner(System.in)) {
			boolean continuar = true;
			while (continuar) {
				mostrarMenu(cola.size());
				String opcion = scanner.nextLine().trim();
				switch (opcion) {
					case "1" -> agregarPersona(scanner, cola);
					case "2" -> atenderSiguiente(cola);
					case "3" -> verPrimero(cola);
					case "4" -> cola.mostrarCola();
					case "5" -> {
						System.out.println("Fin del sistema de turnos.");
						continuar = false;
					}
					default -> System.out.println("Opción inválida. Elija del 1 al 5.");
				}
				if (continuar) {
					System.out.println("Personas esperando: " + cola.size() + " de " + CAPACIDAD_COLA + "\n");
				}
			}
		}
	}

	private static void mostrarMenu(int esperando) {
		System.out.println("=== Sistema de turnos (" + esperando + "/" + CAPACIDAD_COLA + " esperando) ===");
		System.out.println("1. Agregar persona");
		System.out.println("2. Atender siguiente");
		System.out.println("3. Ver quién está primero");
		System.out.println("4. Ver cola de espera");
		System.out.println("5. Salir");
		System.out.print("Seleccione una opción: ");
	}

	private static void agregarPersona(Scanner scanner, ColaTurnos cola) {
		System.out.print("Nombre de la persona: ");
		String nombre = scanner.nextLine();
		try {
			System.out.println("Turno asignado: " + cola.enqueue(nombre));
		} catch (IllegalStateException | IllegalArgumentException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private static void atenderSiguiente(ColaTurnos cola) {
		try {
			System.out.println("Atendiendo a: " + cola.dequeue());
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private static void verPrimero(ColaTurnos cola) {
		try {
			System.out.println("Primera persona en la cola: " + cola.front());
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage());
		}
	}
}
