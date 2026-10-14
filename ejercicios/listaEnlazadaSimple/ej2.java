/*Tengo una lista enlazada simple de enteros en Java (clase Nodo con dato y siguiente, y clase ListaEnlazada con head y size, hecha desde cero, sin ArrayList ni LinkedList). Necesito agregarle un método boolean buscar(int dato).

El método recorre la lista desde head con un auxiliar (actual), avanzando con actual = actual.siguiente, hasta encontrar el dato o llegar a null. Si encuentra un nodo con ese dato devuelve true y corta ahí, sin seguir recorriendo. Si llega a null sin encontrarlo devuelve false. No debe mover head ni modificar la lista.

La búsqueda tiene que ser secuencial porque en una lista enlazada los nodos no están uno al lado del otro en memoria como en un arreglo, así que no existe una posición calculable tipo lista[3]. Lo único que tengo es head, y cada nodo solo conoce al siguiente, entonces para llegar a un nodo tengo que pasar por todos los anteriores. En el peor caso (el dato está al final o no está) recorro los n nodos. Quiero esta explicación en un comentario arriba del método.

Casos a contemplar: lista vacía (devuelve false sin romperse, porque head es null), dato en el primer nodo, en el último, en el medio, dato que no existe y dato repetido (devuelve true al encontrar la primera aparición).

Comentá el código en español. En el main armá la lista 10 -> 20 -> 30 -> 40 y probá buscar(10), buscar(30), buscar(40), buscar(99) y buscar sobre una lista vacía, mostrando el resultado de cada uno. */
package ejercicios.guia5;

public class ej2 {
	public static void main(String[] args) {
		ListaEnlazada lista = new ListaEnlazada();
		lista.insertarAlFinal(10);
		lista.insertarAlFinal(20);
		lista.insertarAlFinal(30);
		lista.insertarAlFinal(40);

		System.out.print("Lista: ");
		lista.imprimir();
		mostrarBusqueda(lista, 10);
		mostrarBusqueda(lista, 30);
		mostrarBusqueda(lista, 40);
		mostrarBusqueda(lista, 99);

		ListaEnlazada vacia = new ListaEnlazada();
		mostrarBusqueda(vacia, 10);

		ListaEnlazada repetidos = new ListaEnlazada();
		repetidos.insertarAlFinal(5);
		repetidos.insertarAlFinal(8);
		repetidos.insertarAlFinal(5);
		System.out.print("Lista con repetidos: ");
		repetidos.imprimir();
		mostrarBusqueda(repetidos, 5);
	}

	private static void mostrarBusqueda(ListaEnlazada lista, int dato) {
		System.out.println("buscar(" + dato + ") -> " + lista.buscar(dato));
	}
}

/** Nodo de la lista: contiene un entero y la referencia al siguiente nodo. */
class Nodo {
	int dato;
	Nodo siguiente;

	Nodo(int dato) {
		this.dato = dato;
		this.siguiente = null;
	}
}

/** Lista enlazada simple que guarda su primer nodo y la cantidad de elementos. */
class ListaEnlazada {
	private Nodo head;
	private int size;

	ListaEnlazada() {
		head = null;
		size = 0;
	}

	public void insertarAlFinal(int dato) {
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
	 * La búsqueda es secuencial porque los nodos no ocupan posiciones contiguas
	 * en memoria y cada nodo solo conoce al siguiente. Se debe recorrer desde head;
	 * en el peor caso (dato al final o ausente) se visitan los n nodos: O(n).
	 */
	public boolean buscar(int dato) {
		Nodo actual = head;
		while (actual != null) {
			if (actual.dato == dato) {
				return true;
			}
			actual = actual.siguiente;
		}
		return false;
	}

	public void imprimir() {
		if (head == null) {
			System.out.println("Lista vacía");
			return;
		}
		Nodo actual = head;
		while (actual != null) {
			System.out.print(actual.dato + " -> ");
			actual = actual.siguiente;
		}
		System.out.println("null");
	}

	public boolean estaVacia() {
		return head == null;
	}

	public int getSize() {
		return size;
	}
}
