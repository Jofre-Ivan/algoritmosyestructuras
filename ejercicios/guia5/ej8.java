/*
Prompt: Implementar contarOcurrencias(int dato) en una lista enlazada simple de
enteros hecha desde cero. Recorrer todos los nodos desde head hasta null, contar
cada coincidencia sin modificar la lista ni cortar al hallar la primera. Probar
valores repetidos, inexistentes, una lista con todos los valores iguales y una
lista vacía.
*/
package ejercicios.guia5;

public class ej8 {
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
		 * A diferencia de buscar(), no se puede cortar al hallar la primera coincidencia:
		 * los repetidos pueden estar en cualquier nodo. Para conocer el total se recorre
		 * toda la lista (O(n)), incluso si el primer nodo ya contiene el dato.
		 */
		private int contarOcurrencias(int dato) {
			int cantidad = 0;
			Nodo actual = head;
			while (actual != null) {
				if (actual.getDato() == dato) {
					cantidad++;
				}
				actual = actual.getSiguiente();
			}
			return cantidad;
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
		for (int dato : new int[]{10, 20, 10, 30, 10}) {
			lista.insertarAlFinal(dato);
		}
		System.out.print("Lista: ");
		lista.imprimir();
		mostrarConteo(lista, 10);
		mostrarConteo(lista, 20);
		mostrarConteo(lista, 99);

		ListaEnlazada todosIguales = new ListaEnlazada();
		for (int dato : new int[]{7, 7, 7}) {
			todosIguales.insertarAlFinal(dato);
		}
		System.out.print("\nLista con todos los valores iguales: ");
		todosIguales.imprimir();
		System.out.println("contarOcurrencias(7) -> " + todosIguales.contarOcurrencias(7)
				+ " (size=" + todosIguales.getSize() + ")");

		ListaEnlazada vacia = new ListaEnlazada();
		System.out.print("\nLista vacía: ");
		vacia.imprimir();
		mostrarConteo(vacia, 7);
	}

	private static void mostrarConteo(ListaEnlazada lista, int dato) {
		System.out.println("contarOcurrencias(" + dato + ") -> " + lista.contarOcurrencias(dato));
	}
}
