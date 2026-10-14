/* Tengo una lista enlazada simple de enteros en Java, la del ejercicio anterior (clase Nodo con dato y siguiente, con getDato(), setDato(), getSiguiente() y setSiguiente(), y clase ListaEnlazada con head y size, sin ArrayList ni LinkedList). Si a Nodo le falta setDato(), agregalo. Necesito agregarle el método void modificar(int posicion, int nuevoDato), contando las posiciones desde 0.

Un nodo tiene dos partes y modificar puede significar dos cosas distintas. Modificar el dato es cambiar el valor guardado dentro del nodo con setDato(nuevoDato): el nodo sigue siendo el mismo, sigue en el mismo lugar y los enlaces no se tocan. Modificar la referencia al siguiente es cambiar con setSiguiente() a qué nodo apunta, y eso altera la estructura de la lista: se puede saltear, perder o repetir nodos. En este método solo hay que cambiar el dato, no los enlaces. Por eso no se crea un nodo nuevo ni se mueve head, y size no cambia. Quiero esta explicación en un comentario arriba del método.

Primero valido, antes de recorrer: las posiciones válidas van de 0 a size - 1. Si posicion < 0 o posicion >= size, lanzo una IndexOutOfBoundsException con un mensaje claro que incluya la posición y el tamaño. Con la lista vacía cualquier posición es inválida porque size es 0. Después recorro con un auxiliar que arranca en head y avanza posicion veces con actual = actual.getSiguiente(), hasta quedar parado en el nodo de esa posición, y ahí hago actual.setDato(nuevoDato). El recorrido no debe mover head.

Casos a contemplar: lista vacía, posición 0, última posición (size - 1), posición del medio, posición igual a size, posición negativa, y que después de modificar la lista mantenga el mismo largo y los mismos enlaces, cambiando solo el valor de ese nodo. Aceptar valores repetidos y negativos como nuevoDato.

Comentá el código en español. En el main armá la lista 10 -> 20 -> 30 -> 40 y probá, imprimiendo después de cada paso con el size: modificar(0, 15), modificar(2, 35) y modificar(3, 45). Tiene que quedar 15 -> 20 -> 35 -> 45 -> null con size 4. Probá también modificar(4, 99), modificar(-1, 99) y modificar sobre una lista vacía capturando la excepción e imprimiendo el mensaje.


*/
package ejercicios.guia5;

public class ej7 {
	private static class Nodo {
		private int dato;
		private Nodo siguiente;

		private Nodo(int dato) {
			this.dato = dato;
		}

		private int getDato() {
			return dato;
		}

		private void setDato(int dato) {
			this.dato = dato;
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
		 * En este método se modifica el dato del nodo con setDato(), no su enlace
		 * siguiente. Cambiar el dato conserva el mismo nodo, su posición y la
		 * estructura de enlaces; por eso no se crea otro nodo, no cambia head y
		 * size permanece igual.
		 */
		private void modificar(int posicion, int nuevoDato) {
			if (posicion < 0 || posicion >= size) {
				throw new IndexOutOfBoundsException(
						"Posición " + posicion + " inválida; tamaño de la lista: " + size);
			}

			Nodo actual = head;
			for (int i = 0; i < posicion; i++) {
				actual = actual.getSiguiente();
			}
			actual.setDato(nuevoDato);
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
		for (int dato : new int[]{10, 20, 30, 40}) {
			lista.insertarAlFinal(dato);
		}
		mostrarEstado(lista, "Lista inicial");

		modificarYMostrar(lista, 0, 15);
		modificarYMostrar(lista, 2, 35);
		modificarYMostrar(lista, 3, 45);

		probarPosicionInvalida(lista, 4, 99);
		probarPosicionInvalida(lista, -1, 99);

		System.out.println("\nPrueba con lista vacía:");
		probarPosicionInvalida(new ListaEnlazada(), 0, 99);
	}

	private static void modificarYMostrar(ListaEnlazada lista, int posicion, int nuevoDato) {
		lista.modificar(posicion, nuevoDato);
		mostrarEstado(lista, "Después de modificar(" + posicion + ", " + nuevoDato + ")");
	}

	private static void mostrarEstado(ListaEnlazada lista, String titulo) {
		System.out.print(titulo + ": ");
		lista.imprimir();
		System.out.println("size=" + lista.getSize());
	}

	private static void probarPosicionInvalida(ListaEnlazada lista, int posicion, int nuevoDato) {
		try {
			lista.modificar(posicion, nuevoDato);
		} catch (IndexOutOfBoundsException exception) {
			System.out.println("modificar(" + posicion + ", " + nuevoDato + "): " + exception.getMessage()
					+ " | size=" + lista.getSize());
		}
	}
}
