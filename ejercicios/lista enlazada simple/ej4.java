/*Tengo una lista enlazada simple de enteros en Java, la del ejercicio anterior (clase Nodo con dato y siguiente, y clase ListaEnlazada con head y size, sin ArrayList ni LinkedList). Necesito agregarle el método void insertarEnPosicion(int dato, int posicion), contando las posiciones desde 0. Si a Nodo le faltan getSiguiente() y setSiguiente(), agregalos y usalos en todo el código.

Las posiciones válidas van de 0 a size, ambas incluidas: 0 es insertar al inicio y size es insertar al final. Si posicion < 0 o posicion > size, lanzo una IndexOutOfBoundsException con un mensaje claro que incluya la posición y el tamaño. Con la lista vacía, la única posición válida es 0.

Caso posición 0: el nodo nuevo apunta al head actual con nuevo.setSiguiente(head) y después head pasa a ser el nuevo. No hay nodo anterior, por eso este caso va aparte.

Caso general (medio y final): recorro con un auxiliar actual desde head hasta quedar parado en el nodo anterior a la posición, o sea avanzo posicion - 1 veces. Ahí actualizo los enlaces en este orden:
1) nuevo.setSiguiente(actual.getSiguiente());
2) actual.setSiguiente(nuevo);
Primero el nuevo toma el enlace al resto de la lista y recién después el anterior se engancha al nuevo. Si la posición es size, actual es el último nodo y su siguiente es null, entonces el nuevo queda con null y es el último, sin código especial. Quiero esta explicación en un comentario arriba del método.

Si se invierte el orden, haciendo primero actual.setSiguiente(nuevo), el actual.getSiguiente() ya es el nuevo, entonces el segundo paso deja nuevo apuntando a sí mismo. Se pierde la referencia al resto de la lista (los nodos que seguían quedan inaccesibles) y se forma un ciclo, así que imprimir o cualquier recorrido queda en un bucle infinito. Quiero esto también en el comentario.

Al final aumento size en 1 en todos los casos. No debe mover head salvo al insertar en la posición 0.

Casos a contemplar: lista vacía con posición 0, inicio, medio, final (posicion == size), posición == size + 1, posición negativa y size correcto después de cada inserción.

Comentá el código . En el main partí de la lista vacía e insertá: (10, 0), (30, 1), (20, 1), (5, 0), (40, 4), imprimiendo después de cada paso. Tiene que quedar 5 -> 10 -> 20 -> 30 -> 40 -> null con size 5. Después probá posiciones 6 y -1 capturando la excepción e imprimiendo el mensaje.

*/ 
package ejercicios.guia5;

public class ej4 {
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

		/*
		 * Inserta en una posición entre 0 y size. En la posición 0 el nuevo nodo
		 * apunta primero al head actual y luego head se actualiza. En los demás
		 * casos se recorre posicion - 1 veces hasta el nodo anterior; el nuevo toma
		 * primero el enlace al resto y recién después el anterior apunta al nuevo.
		 * Si se invierte ese orden, el nuevo podría apuntarse a sí mismo: se perdería
		 * el resto de la lista y se formaría un ciclo que dejaría los recorridos en
		 * un bucle infinito. Para posicion == size, el anterior es el último nodo
		 * y el nuevo queda enlazado a null naturalmente.
		 */
		private void insertarEnPosicion(int dato, int posicion) {
			if (posicion < 0 || posicion > size) {
				throw new IndexOutOfBoundsException(
						"Posición " + posicion + " inválida; tamaño de la lista: " + size);
			}

			Nodo nuevo = new Nodo(dato);
			if (posicion == 0) {
				nuevo.setSiguiente(head);
				head = nuevo;
			} else {
				Nodo actual = head;
				for (int i = 0; i < posicion - 1; i++) {
					actual = actual.getSiguiente();
				}
				nuevo.setSiguiente(actual.getSiguiente());
				actual.setSiguiente(nuevo);
			}
			size++;
		}

		private void imprimir() {
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
		lista.imprimir();
		System.out.println("Tamaño: " + lista.getSize());

		int[][] inserciones = {{10, 0}, {30, 1}, {20, 1}, {5, 0}, {40, 4}};
		for (int[] insercion : inserciones) {
			int dato = insercion[0];
			int posicion = insercion[1];
			lista.insertarEnPosicion(dato, posicion);
			System.out.print("Después de insertar " + dato + " en posición " + posicion + ": ");
			lista.imprimir();
			System.out.println("Tamaño: " + lista.getSize());
		}

		probarPosicionInvalida(lista, 6);
		probarPosicionInvalida(lista, -1);
	}

	private static void probarPosicionInvalida(ListaEnlazada lista, int posicion) {
		try {
			lista.insertarEnPosicion(99, posicion);
		} catch (IndexOutOfBoundsException exception) {
			System.out.println("Inserción en posición " + posicion + ": " + exception.getMessage());
		}
	}
}
