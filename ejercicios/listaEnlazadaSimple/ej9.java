/*
Tengo una lista enlazada simple de enteros en Java, la del ejercicio anterior (clase Nodo con dato y siguiente, con getDato(), getSiguiente() y setSiguiente(), y clase ListaEnlazada con head y size, sin ArrayList ni LinkedList). Necesito agregarle el método void invertir(), que da vuelta la lista: 10 -> 20 -> 30 -> 40 -> null tiene que quedar 40 -> 30 -> 20 -> 10 -> null. Quiero invertirla cambiando los enlaces de los nodos que ya existen, sin crear nodos nuevos, sin copiar los datos a otra estructura y sin usar recursión.

Hacen falta tres referencias auxiliares:
- anterior: arranca en null. Es el nodo que ya quedó invertido y al que va a apuntar el nodo actual.
- actual: arranca en head. Es el nodo que estoy dando vuelta.
- siguiente: es el nodo que viene después de actual. Guarda lo que le sigue a actual antes de cambiarle el enlace.

En cada vuelta del while (mientras actual no sea null) hago estos pasos, en este orden:
1) siguiente = actual.getSiguiente();
2) actual.setSiguiente(anterior);
3) anterior = actual;
4) actual = siguiente;
Cuando el bucle termina, actual es null y anterior quedó parado en el que era el último nodo, entonces hago head = anterior. El antiguo head queda al final con siguiente en null, porque en la primera vuelta apuntó a anterior, que valía null.

El orden es fundamental. Cada nodo solo conoce al siguiente, así que si hago actual.setSiguiente(anterior) antes de guardar siguiente, pierdo la única referencia al resto de la lista y los nodos que seguían quedan inaccesibles, y el recorrido termina después de un solo nodo. Y si muevo actual = siguiente antes de hacer anterior = actual, anterior nunca avanza y los nodos se enlazan mal. Quiero esta explicación en un comentario arriba del método, junto con un ejemplo corto de qué hace cada referencia en la primera vuelta.

size no cambia porque no se agregan ni se sacan nodos. Hay que actualizar head al final, no durante el recorrido.

Casos a contemplar: lista vacía (head es null, no hace nada y no se rompe), lista de un solo elemento (queda igual), lista de dos elementos, lista de cantidad par e impar de elementos, valores repetidos, e invertir dos veces seguidas (tiene que volver al orden original).

Comentá el código en español. En el main armá la lista 10 -> 20 -> 30 -> 40, imprimila, invertila, imprimila de nuevo, invertila otra vez para comprobar que vuelve al original, y probá también una lista vacía, una de un solo elemento y una de tres elementos, mostrando el size en cada caso.

ademas pone todo este largo propm como comentario al principio, no resumas el promp ponelo entero
*/
package ejercicios.guia5;

public class ej9 {
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
				Nodo recorrido = head;
				while (recorrido.getSiguiente() != null) {
					recorrido = recorrido.getSiguiente();
				}
				recorrido.setSiguiente(nuevo);
			}
			size++;
		}

		/*
		 * Invierte los enlaces de los nodos existentes, sin crear nodos ni usar
		 * estructuras auxiliares. El orden de los pasos evita perder la lista:
		 * primero se guarda siguiente, luego actual apunta a anterior, y después
		 * avanzan anterior y actual. En la primera vuelta con 10 -> 20 -> ...:
		 * siguiente guarda 20; 10 apunta a null; anterior pasa a 10 y actual a 20.
		 * Si se cambia el enlace antes de guardar siguiente, se pierde el resto.
		 * Al final anterior es el antiguo último nodo y recién entonces se actualiza
		 * head. size no cambia porque no se agregan ni eliminan nodos.
		 */
		private void invertir() {
			Nodo anterior = null;
			Nodo actual = head;
			Nodo siguiente;

			while (actual != null) {
				siguiente = actual.getSiguiente();
				actual.setSiguiente(anterior);
				anterior = actual;
				actual = siguiente;
			}

			head = anterior;
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
		ListaEnlazada lista = crearLista(10, 20, 30, 40);
		mostrarEstado("Lista original", lista);
		lista.invertir();
		mostrarEstado("Después de invertir", lista);
		lista.invertir();
		mostrarEstado("Después de invertir por segunda vez", lista);

		ListaEnlazada vacia = new ListaEnlazada();
		mostrarEstado("Lista vacía antes de invertir", vacia);
		vacia.invertir();
		mostrarEstado("Lista vacía después de invertir", vacia);

		ListaEnlazada unElemento = crearLista(7);
		mostrarEstado("Lista de un elemento antes de invertir", unElemento);
		unElemento.invertir();
		mostrarEstado("Lista de un elemento después de invertir", unElemento);

		ListaEnlazada impar = crearLista(1, 2, 3);
		mostrarEstado("Lista impar antes de invertir", impar);
		impar.invertir();
		mostrarEstado("Lista impar después de invertir", impar);

		ListaEnlazada dosElementos = crearLista(4, 4);
		mostrarEstado("Lista de dos elementos repetidos antes de invertir", dosElementos);
		dosElementos.invertir();
		mostrarEstado("Lista de dos elementos repetidos después de invertir", dosElementos);
	}

	private static ListaEnlazada crearLista(int... valores) {
		ListaEnlazada lista = new ListaEnlazada();
		for (int valor : valores) {
			lista.insertarAlFinal(valor);
		}
		return lista;
	}

	private static void mostrarEstado(String titulo, ListaEnlazada lista) {
		System.out.print(titulo + ": ");
		lista.imprimir();
		System.out.println("size=" + lista.getSize() + "\n");
	}
}
