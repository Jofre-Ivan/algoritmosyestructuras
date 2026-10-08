/*Necesito una pila de enteros en Java implementada con una lista enlazada simple, hecha desde cero, sin usar Stack, ArrayList, LinkedList, ArrayDeque ni ninguna estructura de Java. Uso una clase NodoPila con dos atributos, dato (int) y siguiente (NodoPila), y una clase PilaEnlazada con un atributo tope (head) que apunta al primer nodo, y un contador size.

Corresponde usar una pila porque lo que necesito es LIFO: el último elemento apilado es el primero en desapilarse, y solo se puede acceder al elemento de arriba. Una cola no sirve porque saldría primero el elemento más viejo.

Internamente, el head de la lista representa el tope de la pila. Elijo el head y no el último nodo porque es el único lugar donde puedo insertar y sacar sin recorrer la lista: en una lista simple sin referencia al último nodo, llegar al final requiere recorrer los n nodos. Al estar el tope en el head, siempre tengo acceso directo a él. Al crear la pila, tope vale null y size vale 0.

Operaciones:
- push(int dato): creo un nodo nuevo, hago que su siguiente apunte al tope actual y después tope pasa a ser el nodo nuevo (en ese orden, para no perder el resto de la pila). Aumento size. O(1), porque no recorre nada.
- pop(): guardo el dato del tope, hago tope = tope.siguiente (el nodo viejo queda sin referencias y el Garbage Collector lo libera) y disminuyo size. Devuelve el dato. O(1), porque solo cambia una referencia.
- peek(): devuelve el dato del tope sin modificar la pila. O(1).
- isEmpty(): devuelve true si tope es null. O(1).
- buscar(int dato): recorre desde el tope con un auxiliar hasta encontrar el dato o llegar a null, devuelve true o false, sin mover tope ni modificar la pila. O(n).
- imprimir(): recorre desde el tope con un auxiliar sin modificar la pila y muestra los elementos del tope hacia abajo, por ejemplo "30 -> 20 -> 10 -> null", o "Pila vacía". O(n).
- size() devuelve el contador sin recorrer. O(1).

Casos especiales: pop y peek con la pila vacía lanzan una excepción con mensaje claro ("Pila vacía") y no devuelven -1, porque -1 puede ser un dato válido. Una pila enlazada no tiene capacidad fija, así que no existe "pila llena". Hay que contemplar pila con un solo elemento (el pop deja tope en null y size en 0), buscar e imprimir sobre una pila vacía, buscar un valor en el tope, en el fondo y uno que no existe, valores repetidos y negativos, y que size coincida siempre con la cantidad real de nodos.

Comentá el código en español, y dejá un comentario arriba de la clase explicando por qué el head es el tope y por qué push y pop son O(1). En el main probá: pila vacía (isEmpty, imprimir, un pop y un peek capturando la excepción), apilar 10, 20 y 30, imprimir, peek (30), buscar(20) y buscar(99), desapilar todo verificando que salen en orden 30, 20, 10, y un pop final con la pila vacía. Mostrá el size después de cada paso.


 */
package ejercicios.guia6;

/**
 * Pila LIFO enlazada: el último dato apilado es el primero que se desapila.
 * El head representa el tope, porque insertar y retirar allí solo cambia una
 * referencia, sin recorrer la lista; por eso push y pop son O(1). Usar el final
 * de una lista simplemente enlazada requeriría recorrer sus nodos.
 */
public class ej1 {
	private static class NodoPila {
		private final int dato;
		private NodoPila siguiente;

		private NodoPila(int dato) {
			this.dato = dato;
		}
	}

	private static class PilaEnlazada {
		// tope apunta al nodo de arriba; null representa una pila vacía.
		private NodoPila tope;
		private int size;

		private void push(int dato) {
			NodoPila nuevo = new NodoPila(dato);
			nuevo.siguiente = tope;
			tope = nuevo;
			size++;
		}

		private int pop() {
			if (isEmpty()) {
				throw new IllegalStateException("Pila vacía");
			}
			int dato = tope.dato;
			tope = tope.siguiente;
			size--;
			return dato;
		}

		private int peek() {
			if (isEmpty()) {
				throw new IllegalStateException("Pila vacía");
			}
			return tope.dato;
		}

		private boolean isEmpty() {
			return tope == null;
		}

		private boolean buscar(int dato) {
			NodoPila actual = tope;
			while (actual != null) {
				if (actual.dato == dato) {
					return true;
				}
				actual = actual.siguiente;
			}
			return false;
		}

		private void imprimir() {
			if (isEmpty()) {
				System.out.println("Pila vacía");
				return;
			}
			NodoPila actual = tope;
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

	public static void main(String[] args) {
		PilaEnlazada pila = new PilaEnlazada();
		System.out.println("=== Prueba de pila enlazada ===");
		System.out.println("Pila inicialmente vacía: " + pila.isEmpty() + " | size=" + pila.size());
		System.out.print("Contenido: ");
		pila.imprimir();
		probarOperacionVacia("pop", pila, true);
		probarOperacionVacia("peek", pila, false);

		for (int dato : new int[]{10, 20, 30}) {
			pila.push(dato);
			System.out.println("push(" + dato + ") | size=" + pila.size());
		}

		System.out.print("Pila desde el tope: ");
		pila.imprimir();
		System.out.println("peek() -> " + pila.peek() + " | size=" + pila.size());
		System.out.println("buscar(20) -> " + pila.buscar(20) + " | size=" + pila.size());
		System.out.println("buscar(99) -> " + pila.buscar(99) + " | size=" + pila.size());

		while (!pila.isEmpty()) {
			System.out.println("pop() -> " + pila.pop() + " | size=" + pila.size());
		}
		probarOperacionVacia("pop final", pila, true);
	}

	private static void probarOperacionVacia(String operacion, PilaEnlazada pila, boolean desapilar) {
		try {
			int resultado = desapilar ? pila.pop() : pila.peek();
			System.out.println(operacion + " -> " + resultado);
		} catch (IllegalStateException exception) {
			System.out.println(operacion + " -> " + exception.getMessage() + " | size=" + pila.size());
		}
	}
}
