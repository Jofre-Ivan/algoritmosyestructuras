/*Necesito una cola de enteros en Java implementada con una lista enlazada simple, hecha desde cero, sin usar Queue, LinkedList, ArrayList ni ArrayDeque. Uso una clase Nodo con dato (int) y siguiente (Nodo), y una clase ColaEnlazada con head, tail y size. Al crear la cola, head y tail valen null y size vale 0.

Corresponde una cola porque necesito FIFO: el primero que entra es el primero que sale. Con una pila saldría primero el último.

El head es el frente (de ahí se desencola) y el tail es el final (ahí se encola). La cola necesita los dos porque cada operación trabaja en un extremo distinto. Si solo tuviera head, desencolar seguiría siendo directo, pero para encolar tendría que recorrer los n nodos hasta llegar al último, y pasaría de O(1) a O(n). Con tail encolo directamente. Quiero esta explicación en un comentario arriba de la clase.

Operaciones: encolar(int dato), desencolar(), frente(), estaVacia(), buscar(int dato), imprimir() y getSize().

- encolar: creo el nodo nuevo. Si la cola está vacía, head y tail pasan a ser ese nodo. Si no, tail.siguiente apunta al nuevo y después tail pasa a ser el nuevo. Aumento size. O(1).
- desencolar: guardo el dato de head, hago head = head.siguiente y disminuyo size. Si después de eso head quedó en null, también pongo tail en null, porque si no tail seguiría apuntando a un nodo que ya salió. Devuelvo el dato. O(1).
- frente: devuelve el dato de head sin modificar nada. O(1).
- estaVacia: head es null. O(1).
- buscar: recorre desde head con un auxiliar hasta encontrar el dato o llegar a null, sin modificar la cola. O(n).
- imprimir: recorre desde head sin modificar la cola y muestra por ejemplo "10 -> 20 -> 30 -> null", o "Cola vacía". O(n).

Casos especiales: desencolar y frente con la cola vacía lanzan una excepción con mensaje claro ("Cola vacía"), no devuelven -1. Primer encolar en una cola vacía, cola con un solo elemento (al desencolarla head y tail quedan en null), y encolar de nuevo después de haberla vaciado. No existe "cola llena" porque no tiene capacidad fija. Buscar e imprimir con la cola vacía no deben romperse.

Comentá el código en español. En el main probá: cola vacía (desencolar capturando la excepción), encolar 10, 20 y 30, imprimir, frente (10), buscar(20) y buscar(99), desencolar todo verificando el orden 10, 20, 30, y volver a encolar un valor después de vaciarla. Mostrá el size en cada paso */
package ejercicios.guia6;

/**
 * Cola FIFO enlazada: head es el frente del que se desencola y tail es el final
 * donde se encola. Se necesitan ambos extremos para que encolar y desencolar
 * sean O(1); con solo head habría que recorrer los n nodos para encontrar el
 * último al encolar, haciendo esa operación O(n).
 */
public class ej2 {
	private static class Nodo {
		private final int dato;
		private Nodo siguiente;

		private Nodo(int dato) {
			this.dato = dato;
		}
	}

	private static class ColaEnlazada {
		private Nodo head;
		private Nodo tail;
		private int size;

		private void encolar(int dato) {
			Nodo nuevo = new Nodo(dato);
			if (estaVacia()) {
				head = nuevo;
				tail = nuevo;
			} else {
				tail.siguiente = nuevo;
				tail = nuevo;
			}
			size++;
		}

		private int desencolar() {
			if (estaVacia()) {
				throw new IllegalStateException("Cola vacía");
			}
			int dato = head.dato;
			head = head.siguiente;
			size--;
			if (head == null) {
				tail = null;
			}
			return dato;
		}

		private int frente() {
			if (estaVacia()) {
				throw new IllegalStateException("Cola vacía");
			}
			return head.dato;
		}

		private boolean estaVacia() {
			return head == null;
		}

		private boolean buscar(int dato) {
			Nodo actual = head;
			while (actual != null) {
				if (actual.dato == dato) {
					return true;
				}
				actual = actual.siguiente;
			}
			return false;
		}

		private void imprimir() {
			if (estaVacia()) {
				System.out.println("Cola vacía");
				return;
			}
			Nodo actual = head;
			while (actual != null) {
				System.out.print(actual.dato + " -> ");
				actual = actual.siguiente;
			}
			System.out.println("null");
		}

		private int getSize() {
			return size;
		}
	}

	public static void main(String[] args) {
		ColaEnlazada cola = new ColaEnlazada();
		System.out.println("Cola recién creada | size=" + cola.getSize()
				+ " | vacía=" + cola.estaVacia());
		System.out.print("Contenido: ");
		cola.imprimir();
		System.out.println("buscar(10) -> " + cola.buscar(10) + " | size=" + cola.getSize());

		try {
			cola.desencolar();
		} catch (IllegalStateException exception) {
			System.out.println("desencolar() -> " + exception.getMessage() + " | size=" + cola.getSize());
		}

		for (int dato : new int[]{10, 20, 30}) {
			cola.encolar(dato);
			System.out.println("encolar(" + dato + ") | size=" + cola.getSize());
		}

		System.out.print("Cola actual: ");
		cola.imprimir();
		System.out.println("Después de imprimir | size=" + cola.getSize());
		System.out.println("frente() -> " + cola.frente() + " | size=" + cola.getSize());
		System.out.println("buscar(20) -> " + cola.buscar(20) + " | size=" + cola.getSize());
		System.out.println("buscar(99) -> " + cola.buscar(99) + " | size=" + cola.getSize());

		while (!cola.estaVacia()) {
			System.out.println("desencolar() -> " + cola.desencolar() + " | size=" + cola.getSize());
		}
		System.out.println("Cola vaciada | vacía=" + cola.estaVacia() + " | size=" + cola.getSize());

		cola.encolar(40);
		System.out.println("encolar(40) después de vaciarla | size=" + cola.getSize());
		System.out.print("Cola final: ");
		cola.imprimir();
		System.out.println("Size final: " + cola.getSize());
	}
}
