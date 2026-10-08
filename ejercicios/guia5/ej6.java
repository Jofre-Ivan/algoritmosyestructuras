/* Tengo una lista enlazada simple de enteros en Java, la del ejercicio anterior (clase Nodo con dato y siguiente, con getSiguiente() y setSiguiente(), y clase ListaEnlazada con head y size, sin ArrayList ni LinkedList). Necesito agregarle el método void eliminarEnPosicion(int posicion), contando las posiciones desde 0.

Primero valido: las posiciones válidas van de 0 a size - 1. Si posicion < 0 o posicion >= size, lanzo una IndexOutOfBoundsException con un mensaje claro que incluya la posición y el tamaño. Con la lista vacía cualquier posición es inválida porque size es 0.

Caso posición 0: no existe nodo anterior, así que head pasa a apuntar a head.getSiguiente(). El nodo viejo queda sin referencias y el Garbage Collector se encarga de liberarlo, no hay que borrarlo a mano.

Caso general (medio y último): para eliminar el nodo en la posición p necesito el nodo anterior, el de la posición p - 1, porque es el único que puede cambiar su enlace (cada nodo solo conoce al siguiente, no al anterior). Recorro con un auxiliar actual que arranca en head y avanzo posicion - 1 veces con actual = actual.getSiguiente(), hasta quedar parado en el anterior. Ahí hago actual.setSiguiente(actual.getSiguiente().getSiguiente()), o sea el anterior pasa a apuntar al siguiente del nodo eliminado y lo saltea. Si el nodo eliminado era el último, su siguiente es null, entonces el anterior pasa a ser el último sin código especial. Quiero esta explicación en un comentario arriba del método.

En todos los casos válidos disminuyo size en 1, y no lo toco si la posición es inválida. Tampoco debe mover head salvo al eliminar la posición 0.

Casos a contemplar: lista vacía, lista de un solo elemento (eliminar la posición 0 deja head en null y size en 0), primera posición, posición del medio, última posición (size - 1), posición igual a size y posición negativa.

Comentá el código en español. En el main armá la lista 10 -> 20 -> 30 -> 40 -> 50 y probá, imprimiendo después de cada paso con el size: eliminarEnPosicion(0), eliminarEnPosicion(1), eliminarEnPosicion(2) (el último en ese momento), hasta dejar un solo elemento y después vaciarla. Probá también las posiciones inválidas (la lista ya vacía, 5 y -1) capturando la excepción e imprimiendo el mensaje.

*/
package ejercicios.guia5;

public class ej6 {
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
		 * Las posiciones válidas van de 0 a size - 1. En la posición 0 se actualiza
		 * head, ya que no hay nodo anterior; el nodo que deja de ser accesible podrá
		 * ser liberado por el Garbage Collector. Para las demás posiciones se busca
		 * el nodo anterior (posicion - 1), único que puede cambiar su enlace, y se
		 * hace que saltee al nodo eliminado. Si era el último, su siguiente es null
		 * y el anterior pasa a ser el último sin un caso adicional.
		 */
		private void eliminarEnPosicion(int posicion) {
			if (posicion < 0 || posicion >= size) {
				throw new IndexOutOfBoundsException(
						"Posición " + posicion + " inválida; tamaño de la lista: " + size);
			}

			if (posicion == 0) {
				head = head.getSiguiente();
			} else {
				Nodo actual = head;
				for (int i = 0; i < posicion - 1; i++) {
					actual = actual.getSiguiente();
				}
				actual.setSiguiente(actual.getSiguiente().getSiguiente());
			}
			size--;
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
		mostrarEstado(lista, "Lista inicial");

		eliminarYMostrar(lista, 0);
		eliminarYMostrar(lista, 1);
		eliminarYMostrar(lista, 2);
		eliminarYMostrar(lista, 1);
		eliminarYMostrar(lista, 0);

		System.out.println("\nPruebas de posiciones inválidas sobre la lista vacía:");
		probarPosicionInvalida(lista, 0);
		probarPosicionInvalida(lista, 5);
		probarPosicionInvalida(lista, -1);
	}

	private static void eliminarYMostrar(ListaEnlazada lista, int posicion) {
		lista.eliminarEnPosicion(posicion);
		mostrarEstado(lista, "Después de eliminar la posición " + posicion);
	}

	private static void mostrarEstado(ListaEnlazada lista, String titulo) {
		System.out.print(titulo + ": ");
		lista.imprimir();
		System.out.println("size=" + lista.getSize());
	}

	private static void probarPosicionInvalida(ListaEnlazada lista, int posicion) {
		try {
			lista.eliminarEnPosicion(posicion);
		} catch (IndexOutOfBoundsException exception) {
			System.out.println(exception.getMessage() + " | size permanece en " + lista.getSize());
		}
	}
}
