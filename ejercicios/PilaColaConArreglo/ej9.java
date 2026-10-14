/*Necesito un programa en Java que implemente una cola simple de enteros con un arreglo int[] de tamaño fijo, sin usar Queue, LinkedList, ArrayList ni ArrayDeque, para demostrar el problema del desperdicio de posiciones. Esta cola NO tiene que ser circular y NO tiene que corregir el problema: quiero que el problema se vea.

Uso dos índices. front es la posición del primer elemento (el próximo en salir) y rear es la posición del último elemento agregado. Arrancan en front = 0 y rear = -1. En enqueue, incremento rear y guardo el valor en arreglo[rear]. En dequeue, devuelvo arreglo[front] y después incremento front. Los dos índices solo avanzan hacia la derecha: no usar módulo y no volver a poner front o rear en 0, ni siquiera cuando la cola queda vacía. Tampoco desplazar los elementos hacia el inicio después de un dequeue. isEmpty es front > rear e isFull es rear == capacidad - 1, mirando solamente rear, aunque haya lugares libres al principio.

El problema que quiero demostrar es que cada dequeue deja libre la posición donde estaba el elemento, pero como front y rear solo avanzan, esas posiciones del inicio nunca se vuelven a usar. La cola puede decir que está llena (rear llegó al final) con el arreglo casi vacío, y se desperdicia capacidad. Quiero esta explicación en un comentario al inicio de la clase, y que mencione que una cola circular lo resuelve.

Casos límite: enqueue con la cola llena y dequeue con la cola vacía muestran un mensaje claro ("Cola llena" o "Cola vacía") y no rompen el programa. Importante: el mensaje de "Cola llena" tiene que aparecer también cuando hay posiciones libres al inicio, y en ese caso el programa tiene que avisar que quedan espacios libres que no se pueden reutilizar.

Después de cada operación quiero que se muestre el arreglo completo posición por posición, con los índices, marcando las posiciones libres antes de front (ya usadas y liberadas) y las que están después de rear (sin usar), por ejemplo: "[ libre | libre | libre | 40 | 50 ]", junto con los valores de front, rear, la cantidad de elementos reales y la cantidad de posiciones desperdiciadas (las que están antes de front).

Hacé un main con una demostración fija con capacidad 5: encolar 10, 20, 30, 40 y 50 hasta llenar, desencolar tres veces, mostrar el arreglo con los tres espacios libres al inicio, intentar encolar 60 y verificar que dice "Cola llena" aunque hay solo 2 elementos y 3 lugares libres, y por último desencolar todo y mostrar que, aun vacía, la cola sigue sin poder agregar nada. Al final tiene que imprimir una conclusión que explique qué pasó. Después de la demostración agregá un menú por consola con Scanner (enqueue, dequeue, mostrar arreglo, salir) para probar otras secuencias.

Comentá el código  explicando para qué sirve cada índice y por qué se desperdician posiciones. @file:ej9.java  */
package ejercicios.guia4;

import java.util.Scanner;

/**
 * Cola lineal (no circular) de enteros para mostrar el desperdicio de posiciones:
 * front y rear solo avanzan, así que las celdas liberadas antes de front no se
 * reutilizan. rear puede llegar al final y declarar la cola llena aunque queden
 * pocas personas/valores activos y haya espacios libres al principio. Una cola
 * circular resuelve este problema haciendo que los índices vuelvan al inicio.
 */
public class ej9 {
	private static final int CAPACIDAD = 5;

	private static class ColaLinealEnteros {
		private final int[] arreglo;
		// front apunta al próximo valor que sale; solo avanza, nunca vuelve a cero.
		private int front = 0;
		// rear apunta al último valor agregado; -1 indica que aún no se agregó ninguno.
		private int rear = -1;

		private ColaLinealEnteros(int capacidad) {
			if (capacidad <= 0) {
				throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
			}
			arreglo = new int[capacidad];
		}

		private void enqueue(int valor) {
			if (isFull()) {
				throw new IllegalStateException("Cola llena");
			}
			// No hay módulo ni corrimiento: rear solo avanza hacia la derecha.
			arreglo[++rear] = valor;
		}

		private int dequeue() {
			if (isEmpty()) {
				throw new IllegalStateException("Cola vacía");
			}
			return arreglo[front++];
		}

		private boolean isEmpty() {
			return front > rear;
		}

		// Solo se mira rear: las posiciones que quedaron libres al principio no sirven.
		private boolean isFull() {
			return rear == arreglo.length - 1;
		}

		private int size() {
			return isEmpty() ? 0 : rear - front + 1;
		}

		private int posicionesDesperdiciadas() {
			return Math.min(front, arreglo.length);
		}

		private int posicionesLibresNoReutilizables() {
			return Math.min(front, arreglo.length);
		}

		private void mostrarEstado(String titulo) {
			System.out.println(titulo);
			System.out.print("Arreglo: [");
			for (int i = 0; i < arreglo.length; i++) {
				if (i > 0) {
					System.out.print(" | ");
				}
				System.out.print(i + ": " + descripcionPosicion(i));
			}
			System.out.println("]");
			System.out.println("front=" + front + ", rear=" + rear
					+ ", elementos reales=" + size()
					+ ", posiciones desperdiciadas antes de front=" + posicionesDesperdiciadas());
		}

		private String descripcionPosicion(int indice) {
			if (indice < front) {
				return "libre (desperdiciada)";
			}
			if (!isEmpty() && indice <= rear) {
				return String.valueOf(arreglo[indice]);
			}
			return "libre (sin usar)";
		}
	}

	public static void main(String[] args) {
		demostracionFija();
		System.out.println("\n=== Menú interactivo con una cola nueva de capacidad " + CAPACIDAD + " ===");
		// La demostración termina con rear al final; se usa otra cola para probar secuencias propias.
		iniciarMenu(new ColaLinealEnteros(CAPACIDAD));
	}

	private static void demostracionFija() {
		System.out.println("=== Demostración del desperdicio (capacidad " + CAPACIDAD + ") ===");
		ColaLinealEnteros cola = new ColaLinealEnteros(CAPACIDAD);
		cola.mostrarEstado("Estado inicial");

		for (int valor : new int[]{10, 20, 30, 40, 50}) {
			cola.enqueue(valor);
			cola.mostrarEstado("enqueue(" + valor + ")");
		}

		for (int i = 0; i < 3; i++) {
			System.out.println("dequeue() -> " + cola.dequeue());
			cola.mostrarEstado("Después de dequeue");
		}

		try {
			cola.enqueue(60);
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage() + ": quedan " + cola.size()
					+ " elementos, pero hay " + cola.posicionesLibresNoReutilizables()
					+ " espacios libres al inicio que no se pueden reutilizar.");
		}
		cola.mostrarEstado("Después del intento de enqueue(60)");

		while (!cola.isEmpty()) {
			System.out.println("dequeue() -> " + cola.dequeue());
			cola.mostrarEstado("Después de dequeue");
		}

		try {
			cola.enqueue(70);
		} catch (IllegalStateException exception) {
			System.out.println("Aunque la cola está vacía, " + exception.getMessage()
					+ " porque rear sigue en la última posición y no se reinicia.");
		}
		cola.mostrarEstado("Estado final: cola vacía pero sin posibilidad de agregar");
		System.out.println("Conclusión: se liberaron posiciones al desencolar, pero front y rear "
				+ "solo avanzaron. La cola lineal desperdició esos espacios y quedó bloqueada; "
				+ "una cola circular permitiría reutilizarlos.\n");
	}

	private static void iniciarMenu(ColaLinealEnteros cola) {
		try (Scanner scanner = new Scanner(System.in)) {
			boolean continuar = true;
			while (continuar) {
				mostrarMenu(cola);
				String opcion = scanner.nextLine().trim();
				switch (opcion) {
					case "1" -> encolar(scanner, cola);
					case "2" -> desencolar(cola);
					case "3" -> cola.mostrarEstado("Estado actual");
					case "4" -> {
						System.out.println("Fin del programa.");
						continuar = false;
					}
					default -> System.out.println("Opción inválida. Elija del 1 al 4.");
				}
				if (continuar && !opcion.equals("3")) {
					cola.mostrarEstado("Estado después de la operación");
				}
				if (continuar) {
					System.out.println();
				}
			}
		}
	}

	private static void mostrarMenu(ColaLinealEnteros cola) {
		System.out.println("=== Cola lineal ===");
		System.out.println("1. Enqueue");
		System.out.println("2. Dequeue");
		System.out.println("3. Mostrar arreglo e índices");
		System.out.println("4. Salir");
		System.out.print("front=" + cola.front + ", rear=" + cola.rear
				+ ", elementos=" + cola.size() + ". Opción: ");
	}

	private static void encolar(Scanner scanner, ColaLinealEnteros cola) {
		System.out.print("Valor entero a encolar: ");
		try {
			int valor = Integer.parseInt(scanner.nextLine().trim());
			cola.enqueue(valor);
			System.out.println("Encolado: " + valor);
		} catch (NumberFormatException exception) {
			System.out.println("Ingrese un número entero válido.");
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage() + ": rear llegó al final. Hay "
					+ cola.posicionesLibresNoReutilizables()
					+ " posiciones libres al inicio que no pueden reutilizarse.");
		}
	}

	private static void desencolar(ColaLinealEnteros cola) {
		try {
			System.out.println("dequeue() -> " + cola.dequeue());
		} catch (IllegalStateException exception) {
			System.out.println(exception.getMessage());
		}
	}
 }
