/*Tengo una cola de enteros en Java implementada con lista enlazada simple (clase Nodo con dato y siguiente, y clase ColaEnlazada con head, tail y size, sin Queue, LinkedList, ArrayList ni ArrayDeque). Necesito transformarla en una versión genérica, Nodo<T> y ColaEnlazada<T>, y probarla con una ColaEnlazada<String> de nombres de personas y con una ColaEnlazada<Cliente>, con una clase Cliente propia (nombre y número de cliente). Sigue sin usar estructuras de Java.

El comportamiento FIFO no depende del tipo de dato porque lo garantiza la estructura de enlaces y no el contenido. encolar siempre agrega al final, con tail.siguiente apuntando al nodo nuevo y tail pasando a ser ese nodo, y desencolar siempre saca del principio, con head = head.siguiente. Ninguna de las dos operaciones mira qué hay dentro del dato, solo mueven referencias entre nodos, así que el primero que entra es el primero que sale tanto si el nodo guarda un String como un Cliente. Por la misma razón encolar y desencolar siguen siendo O(1). Quiero esta explicación en un comentario arriba de la clase, junto con una explicación corta de qué son los genéricos (un parámetro de tipo T que se completa al crear la cola, y el compilador controla que solo acepte ese tipo, sin casteos).

Qué cambia: en Nodo<T>, dato pasa de int a T y siguiente pasa a ser Nodo<T>; en ColaEnlazada<T>, head y tail son Nodo<T>, y los parámetros y retornos que eran int (encolar, desencolar, frente, buscar) pasan a ser T. Lo único que depende del tipo es la comparación en buscar: con int se usaba ==, pero con objetos hay que usar equals. Cliente tiene que sobrescribir equals, hashCode y toString para que imprimir muestre algo legible. Rechazar null en encolar con IllegalArgumentException.

Todo lo demás se mantiene igual: el caso de la cola vacía en encolar (head y tail pasan a ser el nodo nuevo), poner tail en null cuando desencolar deja la cola vacía, las excepciones con mensaje "Cola vacía" en desencolar y frente, y que buscar e imprimir no modifiquen la cola.

Comentá el código en español. En el main probá la cola de nombres encolando "Ana", "Luis" y "Marta", imprimiendo, mirando el frente, buscando "Luis" y desencolando todo, y la cola de Cliente de la misma forma. En ambas verificá que los elementos salen en el mismo orden en que entraron, que buscar encuentre un Cliente creado aparte con el mismo contenido (prueba que se usa equals), que se pueda volver a encolar después de vaciarla y que desencolar con la cola vacía lance la excepción. Agregá un comentario en el main indicando que encolar un Cliente en una ColaEnlazada<String> no compila, y por qué es una ventaja.


 */
package ejercicios.guia6;

import java.util.Objects;

/**
 * Cola FIFO genérica: T es el tipo que se completa al crear la cola y el
 * compilador controla que solo entren elementos de ese tipo, sin casteos.
 * El orden FIFO depende de los enlaces, no del contenido: encolar agrega por
 * tail y desencolar retira por head, moviendo solo referencias. Por eso ambas
 * operaciones siguen siendo O(1) tanto para String como para Cliente.
 */
public class ej4 {
	private static class Nodo<T> {
		private final T dato;
		private Nodo<T> siguiente;

		private Nodo(T dato) {
			this.dato = dato;
		}
	}

	private static class ColaEnlazada<T> {
		private Nodo<T> head;
		private Nodo<T> tail;
		private int size;

		private void encolar(T dato) {
			if (dato == null) {
				throw new IllegalArgumentException("La cola no acepta null");
			}
			Nodo<T> nuevo = new Nodo<>(dato);
			if (head == null) {
				head = nuevo;
				tail = nuevo;
			} else {
				tail.siguiente = nuevo;
				tail = nuevo;
			}
			size++;
		}

		private T desencolar() {
			if (estaVacia()) {
				throw new IllegalStateException("Cola vacía");
			}
			T dato = head.dato;
			head = head.siguiente;
			size--;
			if (head == null) {
				tail = null;
			}
			return dato;
		}

		private T frente() {
			if (estaVacia()) {
				throw new IllegalStateException("Cola vacía");
			}
			return head.dato;
		}

		private boolean buscar(T dato) {
			Nodo<T> actual = head;
			while (actual != null) {
				if (Objects.equals(actual.dato, dato)) {
					return true;
				}
				actual = actual.siguiente;
			}
			return false;
		}

		private boolean estaVacia() {
			return head == null;
		}

		private void imprimir() {
			if (estaVacia()) {
				System.out.println("Cola vacía");
				return;
			}
			Nodo<T> actual = head;
			while (actual != null) {
				System.out.print(actual.dato + " -> ");
				actual = actual.siguiente;
			}
			System.out.println("null");
		}

		private int size() {
			return size;
		}
	}

	/** Cliente identifica igualdad por nombre y número de cliente. */
	private static class Cliente {
		private final String nombre;
		private final int numeroCliente;

		private Cliente(String nombre, int numeroCliente) {
			this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser null");
			this.numeroCliente = numeroCliente;
		}

		@Override
		public boolean equals(Object objeto) {
			if (this == objeto) {
				return true;
			}
			if (!(objeto instanceof Cliente)) {
				return false;
			}
			Cliente otro = (Cliente) objeto;
			return numeroCliente == otro.numeroCliente && nombre.equals(otro.nombre);
		}

		@Override
		public int hashCode() {
			return Objects.hash(nombre, numeroCliente);
		}

		@Override
		public String toString() {
			return nombre + " (cliente " + numeroCliente + ")";
		}
	}

	public static void main(String[] args) {
		// No compila y eso es útil: ColaEnlazada<String> rechaza Cliente en tiempo de compilación.
		// ColaEnlazada<String> soloNombres = new ColaEnlazada<>(); soloNombres.encolar(new Cliente("Ana", 101));

		String[] nombres = {"Ana", "Luis", "Marta"};
		demostrarCola("String", crearCola(nombres), nombres, "Luis", "Sofía");

		Cliente ana = new Cliente("Ana", 101);
		Cliente luis = new Cliente("Luis", 202);
		Cliente marta = new Cliente("Marta", 303);
		Cliente[] clientes = {ana, luis, marta};
		ColaEnlazada<Cliente> colaClientes = crearCola(clientes);
		demostrarCola("Cliente", colaClientes, clientes, new Cliente("Ana", 101),
				new Cliente("Sofía", 404));
	}

	private static <T> ColaEnlazada<T> crearCola(T[] elementos) {
		ColaEnlazada<T> cola = new ColaEnlazada<>();
		for (T elemento : elementos) {
			cola.encolar(elemento);
		}
		return cola;
	}

	private static <T> void demostrarCola(String tipo, ColaEnlazada<T> cola,
			T[] ordenEsperado, T valorABuscar, T valorParaReencolar) {
		System.out.println("\n=== ColaEnlazada<" + tipo + "> ===");
		System.out.print("Cola inicial: ");
		cola.imprimir();
		System.out.println("size=" + cola.size());
		System.out.println("frente() -> " + cola.frente());
		System.out.println("buscar(" + valorABuscar + ") -> " + cola.buscar(valorABuscar));

		boolean ordenCorrecto = true;
		for (T esperado : ordenEsperado) {
			T obtenido = cola.desencolar();
			System.out.println("desencolar() -> " + obtenido + " | size=" + cola.size());
			if (!Objects.equals(esperado, obtenido)) {
				ordenCorrecto = false;
			}
		}
		System.out.println("Verificación FIFO: " + (ordenCorrecto ? "CORRECTA" : "INCORRECTA"));
		System.out.println("Cola vacía después de desencolar todo: " + cola.estaVacia()
				+ " | size=" + cola.size());

		try {
			cola.desencolar();
		} catch (IllegalStateException exception) {
			System.out.println("desencolar() vacía -> " + exception.getMessage());
		}

		cola.encolar(valorParaReencolar);
		System.out.println("Encolado tras vaciar: " + valorParaReencolar + " | size=" + cola.size());
		System.out.print("Cola reutilizada: ");
		cola.imprimir();
	}
}
