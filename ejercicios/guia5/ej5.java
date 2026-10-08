/*Tengo una lista enlazada simple de enteros en Java, la del ejercicio anterior (clase Nodo con dato y siguiente, con getSiguiente() y setSiguiente(), y clase ListaEnlazada con head y size, sin ArrayList ni LinkedList). Necesito agregarle el método boolean eliminar(int dato), que elimina la primera aparición del dato y devuelve true si lo eliminó o false si no lo encontró.

En Java no se borra un nodo manualmente como en otros lenguajes. Eliminar es dejar el nodo inaccesible: si ninguna referencia apunta a él, ni head ni el siguiente de otro nodo, el Garbage Collector lo libera solo cuando lo necesite. Por eso eliminar consiste en cambiar un enlace para que la lista "saltee" el nodo, no en borrarlo. Quiero esta explicación en un comentario arriba del método.

Lista vacía (head es null): devuelve false sin romperse.

Eliminar el primer nodo: si head.getDato() es igual al dato, head pasa a apuntar a head.getSiguiente(). El nodo viejo queda sin referencias. Este caso va aparte porque no tiene nodo anterior.

Resto de la lista (medio y último): recorro con un auxiliar actual parado en el nodo anterior al que quiero eliminar, mirando actual.getSiguiente().getDato() mientras actual.getSiguiente() no sea null. Cuando lo encuentro, hago actual.setSiguiente(actual.getSiguiente().getSiguiente()), o sea el anterior se enlaza con el siguiente del nodo eliminado. Si el nodo era el último, su siguiente es null y el anterior pasa a ser el último, sin código especial. Corto el recorrido en la primera aparición, así que si el dato está repetido solo elimina el primero.

Dato inexistente: si el recorrido llega al final sin encontrarlo, devuelve false y la lista no cambia, tampoco size. Solo disminuyo size en 1 cuando realmente eliminé un nodo.

Casos a contemplar: lista vacía, lista de un solo elemento (eliminarlo deja head en null y size en 0), primer nodo, nodo del medio, último nodo, dato inexistente y dato repetido.

Comentá el código en español. En el main armá la lista 10 -> 20 -> 30 -> 40 -> 50 y probá, imprimiendo después de cada paso con el resultado y el size: eliminar(10) (primero), eliminar(30) (medio), eliminar(50) (último), eliminar(99) (inexistente), eliminar(20) y eliminar(40) hasta dejarla vacía, y un eliminar más sobre la lista vacía. También probá una lista con un dato repetido, como 5 -> 7 -> 5, eliminando el 5 y verificando que queda 7 -> 5.


*/ 
package ejercicios.guia5;

public class ej5 {
	private static class Nodo {
		private final int dato;
		private Nodo siguiente;

		private Nodo(int dato) {
			this.dato = dato;
		}

		private int getDato() {
			return dato;
		}

		private Nodo getSiguiente() {
			return siguiente;
		}

		private void setSiguiente(Nodo siguiente) {
			this.siguiente = siguiente;
		}
	}

	private static class ListaEnlazada {
		private Nodo head;
		private int size;

		private void insertarAlFinal(int dato) {
			Nodo nuevo = new Nodo(dato);
			if (head == null) {
				head = nuevo;
			} else {
				Nodo actual = head;
				while (actual.getSiguiente() != null) {
					actual = actual.getSiguiente();
				}
				actual.setSiguiente(nuevo);
			}
			size++;
		}

		/*
		 * En Java no se borra el nodo manualmente: se lo deja inaccesible cambiando
		 * un enlace, y el Garbage Collector podrá liberarlo cuando corresponda.
		 * Si el dato está en head, se avanza head porque no existe nodo anterior.
		 * En el resto, el nodo anterior saltea al encontrado enlazándose con su
		 * siguiente. Se corta en la primera coincidencia para eliminar solo una.
		 */
		private boolean eliminar(int dato) {
			if (head == null) {
				return false;
			}

			if (head.getDato() == dato) {
				head = head.getSiguiente();
				size--;
				return true;
			}

			Nodo actual = head;
			while (actual.getSiguiente() != null) {
				if (actual.getSiguiente().getDato() == dato) {
					actual.setSiguiente(actual.getSiguiente().getSiguiente());
					size--;
					return true;
				}
				actual = actual.getSiguiente();
			}
			return false;
		}

		private void imprimir() {
			if (head == null) {
				System.out.println("Lista vacía");
				return;
			}
			Nodo actual = head;
			while (actual != null) {
				System.out.print(actual.getDato() + " -> ");
				actual = actual.getSiguiente();
			}
			System.out.println("null");
		}

		private int getSize() {
			return size;
		}
	}

	public static void main(String[] args) {
		ListaEnlazada lista = new ListaEnlazada();
		for (int dato : new int[]{10, 20, 30, 40, 50}) {
			lista.insertarAlFinal(dato);
		}
		System.out.print("Lista inicial: ");
		lista.imprimir();

		probarEliminacion(lista, 10);
		probarEliminacion(lista, 30);
		probarEliminacion(lista, 50);
		probarEliminacion(lista, 99);
		probarEliminacion(lista, 20);
		probarEliminacion(lista, 40);
		probarEliminacion(lista, 100);

		ListaEnlazada repetidos = new ListaEnlazada();
		for (int dato : new int[]{5, 7, 5}) {
			repetidos.insertarAlFinal(dato);
		}
		System.out.print("\nLista con repetidos antes: ");
		repetidos.imprimir();
		probarEliminacion(repetidos, 5);

		ListaEnlazada unElemento = new ListaEnlazada();
		unElemento.insertarAlFinal(42);
		System.out.print("\nLista de un solo elemento antes: ");
		unElemento.imprimir();
		probarEliminacion(unElemento, 42);
	}

	private static void probarEliminacion(ListaEnlazada lista, int dato) {
		boolean eliminado = lista.eliminar(dato);
		System.out.println("eliminar(" + dato + ") -> " + eliminado + " | size=" + lista.getSize());
		System.out.print("Lista: ");
		lista.imprimir();
	}
}
