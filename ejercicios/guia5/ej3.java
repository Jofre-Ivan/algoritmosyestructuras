/*Tengo una lista enlazada simple de enteros en Java (clase Nodo con dato y siguiente, y clase ListaEnlazada con head y size, hecha desde cero, sin ArrayList ni LinkedList). Necesito agregarle un método int obtener(int posicion) que devuelva el dato guardado en esa posición, contando desde 0.

Aunque reciba una posición, la lista enlazada no tiene acceso directo como un arreglo. En un arreglo arreglo[3] va directo a esa posición, pero acá los nodos no están contiguos en memoria y cada uno solo conoce al siguiente. Lo único que tengo es head, así que para llegar a la posición p tengo que recorrer nodo por nodo desde head, avanzando p veces con actual = actual.siguiente. Quiero esta explicación en un comentario arriba del método, aclarando que el costo crece con la posición pedida.

Primero valido: si posicion < 0 o posicion >= size, lanzo una IndexOutOfBoundsException con un mensaje claro que incluya la posición y el tamaño. No devuelvo -1 porque puede ser un dato válido. Con la lista vacía, cualquier posición es inválida porque size es 0. Después recorro con un auxiliar que arranca en head, sin mover head ni modificar la lista, y devuelvo actual.dato.

Casos a contemplar: lista vacía, posición 0 (el head), última posición (size - 1), posición del medio, posición igual a size y posición negativa.

Comentá el código  En el main armá la lista 10 -> 20 -> 30 -> 40 y probá obtener(0), obtener(2) y obtener(3), mostrando el resultado, y probá obtener(4), obtener(-1) y obtener sobre una lista vacía capturando la excepción e imprimiendo el mensaje. */
package ejercicios.guia5;

public class ej3 {
	private static class Nodo {
		private final int dato;
		private Nodo siguiente;

		private Nodo(int dato) {
			this.dato = dato;
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
				while (actual.siguiente != null) {
					actual = actual.siguiente;
				}
				actual.siguiente = nuevo;
			}
			size++;
		}

		/*
		 * Aunque se recibe una posición, no hay acceso directo como arreglo[posicion]:
		 * los nodos no son contiguos en memoria y cada uno solo enlaza al siguiente.
		 * Se recorre desde head nodo por nodo; el costo crece con la posición pedida,
		 * hasta O(n) en la última posición.
		 */
		private int obtener(int posicion) {
			if (posicion < 0 || posicion >= size) {
				throw new IndexOutOfBoundsException(
						"Posición " + posicion + " fuera de rango; tamaño de la lista: " + size);
			}

			Nodo actual = head;
			for (int i = 0; i < posicion; i++) {
				actual = actual.siguiente;
			}
			return actual.dato;
		}

		private void imprimir() {
			Nodo actual = head;
			while (actual != null) {
				System.out.print(actual.dato + " -> ");
				actual = actual.siguiente;
			}
			System.out.println("null");
		}
	}

	public static void main(String[] args) {
		ListaEnlazada lista = new ListaEnlazada();
		lista.insertarAlFinal(10);
		lista.insertarAlFinal(20);
		lista.insertarAlFinal(30);
		lista.insertarAlFinal(40);

		System.out.print("Lista: ");
		lista.imprimir();
		probarObtener(lista, 0);
		probarObtener(lista, 2);
		probarObtener(lista, 3);
		probarObtener(lista, 4);
		probarObtener(lista, -1);

		System.out.println("Prueba con lista vacía:");
		probarObtener(new ListaEnlazada(), 0);
	}

	private static void probarObtener(ListaEnlazada lista, int posicion) {
		try {
			System.out.println("obtener(" + posicion + ") -> " + lista.obtener(posicion));
		} catch (IndexOutOfBoundsException exception) {
			System.out.println("obtener(" + posicion + ") -> " + exception.getMessage());
		}
	}
}
