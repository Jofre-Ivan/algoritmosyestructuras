/*Necesito una lista doblemente enlazada genérica en Java, ListaDoble<T> y NodoDoble<T>, hecha desde cero, sin usar ArrayList, LinkedList ni ninguna estructura de Java. Tiene que funcionar con Integer, String y una clase propia (por ejemplo Alumno, con nombre y legajo).

Cada nodo tiene tres referencias: dato (T), siguiente (NodoDoble<T>), que apunta al nodo que le sigue, y anterior (NodoDoble<T>), que apunta al nodo que lo precede. El primer nodo tiene anterior en null y el último tiene siguiente en null. La lista tiene head (primer nodo), tail (último nodo) y size. Al crearla, head y tail valen null y size vale 0.

Se necesitan anterior y siguiente porque con una sola referencia solo se puede recorrer en un sentido. Con anterior puedo recorrer de atrás hacia adelante partiendo de tail, y además, al eliminar un nodo, ya tengo a mano su vecino anterior y no tengo que recorrer desde head para encontrarlo, como pasa en la lista simple. El costo es que cada nodo guarda una referencia más y cada inserción o eliminación tiene que actualizar los enlaces en las dos direcciones. Quiero esta explicación en un comentario arriba de la clase.

Operaciones:
- insertarAlInicio(T dato): creo el nodo nuevo. Si la lista está vacía, head y tail pasan a ser ese nodo. Si no, nuevo.siguiente = head, head.anterior = nuevo y después head = nuevo (en ese orden, para no perder la referencia al head viejo). Aumento size. O(1).
- insertarAlFinal(T dato): igual pero del otro lado. Si está vacía, head y tail son el nuevo. Si no, nuevo.anterior = tail, tail.siguiente = nuevo y después tail = nuevo. Aumento size. O(1) gracias a tail, sin recorrer.
- eliminar(T dato): elimina la primera aparición y devuelve true, o false si no está. Busca con un auxiliar desde head usando equals (no ==, porque con objetos == compara referencias). O(n) para buscar, pero una vez encontrado el nodo, desenlazarlo es O(1).
- imprimirAdelante(): recorre desde head con un auxiliar siguiendo siguiente hasta null, mostrando por ejemplo "10 <-> 20 <-> 30". Si está vacía muestra "Lista vacía". No modifica head.
- imprimirAtras(): recorre desde tail siguiendo anterior hasta null, mostrando "30 <-> 20 <-> 10". No modifica tail.

Casos especiales al eliminar (son cuatro, y cada uno actualiza referencias distintas):
1) Lista vacía: devuelve false sin romperse.
2) Único nodo (head y tail son el mismo): después de eliminarlo head y tail quedan en null.
3) Primer nodo: head pasa a ser head.siguiente y el nuevo head.anterior se pone en null, para que no siga apuntando al nodo eliminado.
4) Último nodo: tail pasa a ser tail.anterior y el nuevo tail.siguiente se pone en null.
5) Nodo del medio: el anterior del nodo eliminado pasa a apuntar a su siguiente (anterior.siguiente = nodo.siguiente) y el siguiente pasa a apuntar a su anterior (siguiente.anterior = nodo.anterior). Hay que actualizar los dos enlaces. Si se actualiza solo uno, imprimir hacia adelante funciona y hacia atrás sigue pasando por el nodo eliminado, o al revés.
El nodo eliminado queda sin referencias y el Garbage Collector lo libera, no hay que borrarlo a mano. Disminuyo size solo cuando realmente eliminé un nodo. Dato inexistente: devuelve false y la lista no cambia.

Otros casos: insertar al inicio y al final en una lista vacía, lista con un solo elemento, datos repetidos (elimina solo la primera aparición), y que size coincida siempre con la cantidad real de nodos y con el recorrido hacia atrás. Rechazar null en las inserciones con IllegalArgumentException. Alumno tiene que sobrescribir equals, hashCode y toString.

Comentá el código en español, explicando por qué se actualizan los enlaces en ese orden. En el main probá ListaDoble<Integer>: lista vacía (imprimir en ambos sentidos y eliminar), insertar al inicio 20 y 10, insertar al final 30, 40 y 50, imprimir en ambos sentidos, eliminar(10) (primero), eliminar(50) (último), eliminar(30) (medio) y eliminar(99) (inexistente), imprimiendo en ambos sentidos después de cada eliminación y mostrando el size, hasta dejar un solo elemento, eliminarlo y verificar que la lista queda vacía. Después probá ListaDoble<String> y ListaDoble<Alumno>, verificando que eliminar encuentre un Alumno creado aparte con el mismo contenido.
*/
package ejercicios.guia6;

import java.util.Objects;

public class ej10 {
	private static class NodoDoble<T> {
		private final T dato;
		private NodoDoble<T> siguiente;
		private NodoDoble<T> anterior;

		private NodoDoble(T dato) {
			this.dato = dato;
		}
	}

	private static class ListaDoble<T> {
		private NodoDoble<T> head;
		private NodoDoble<T> tail;
		private int size;

		private void insertarAlInicio(T dato) {
			validarDato(dato);
			NodoDoble<T> nuevo = new NodoDoble<>(dato);
			if (head == null) {
				head = nuevo;
				tail = nuevo;
			} else {
				// Primero se enlaza el nodo nuevo con el head anterior y luego se actualiza head.
				nuevo.siguiente = head;
				head.anterior = nuevo;
				head = nuevo;
			}
			size++;
		}

		private void insertarAlFinal(T dato) {
			validarDato(dato);
			NodoDoble<T> nuevo = new NodoDoble<>(dato);
			if (tail == null) {
				head = nuevo;
				tail = nuevo;
			} else {
				// Se conecta el nuevo con el tail previo en ambas direcciones antes de mover tail.
				nuevo.anterior = tail;
				tail.siguiente = nuevo;
				tail = nuevo;
			}
			size++;
		}

		/** Elimina la primera coincidencia por contenido y conserva ambos sentidos de enlace. */
		private boolean eliminar(T dato) {
			NodoDoble<T> actual = head;
			while (actual != null && !Objects.equals(actual.dato, dato)) {
				actual = actual.siguiente;
			}
			if (actual == null) {
				return false;
			}

			NodoDoble<T> anterior = actual.anterior;
			NodoDoble<T> siguiente = actual.siguiente;
			if (anterior == null) {
				head = siguiente;
			} else {
				anterior.siguiente = siguiente;
			}
			if (siguiente == null) {
				tail = anterior;
			} else {
				siguiente.anterior = anterior;
			}

			// Se desconecta el nodo eliminado; el Garbage Collector podrá liberarlo.
			actual.anterior = null;
			actual.siguiente = null;
			size--;
			return true;
		}

		private void imprimirAdelante() {
			if (head == null) {
				System.out.println("Lista vacía");
				return;
			}
			NodoDoble<T> actual = head;
			while (actual != null) {
				System.out.print(actual.dato);
				actual = actual.siguiente;
				System.out.print(actual == null ? "" : " <-> ");
			}
			System.out.println();
		}

		private void imprimirAtras() {
			if (tail == null) {
				System.out.println("Lista vacía");
				return;
			}
			NodoDoble<T> actual = tail;
			while (actual != null) {
				System.out.print(actual.dato);
				actual = actual.anterior;
				System.out.print(actual == null ? "" : " <-> ");
			}
			System.out.println();
		}

		private int getSize() {
			return size;
		}

		private void validarDato(T dato) {
			if (dato == null) {
				throw new IllegalArgumentException("La lista no acepta elementos null");
			}
		}
	}

	private static class Alumno {
		private final String nombre;
		private final int legajo;

		private Alumno(String nombre, int legajo) {
			this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser null");
			this.legajo = legajo;
		}

		@Override
		public boolean equals(Object objeto) {
			if (this == objeto) {
				return true;
			}
			if (!(objeto instanceof Alumno)) {
				return false;
			}
			Alumno otro = (Alumno) objeto;
			return legajo == otro.legajo && nombre.equals(otro.nombre);
		}

		@Override
		public int hashCode() {
			return Objects.hash(nombre, legajo);
		}

		@Override
		public String toString() {
			return nombre + " (legajo " + legajo + ")";
		}
	}

	public static void main(String[] args) {
		probarEnteros();
		probarCadenas();
		probarAlumnos();
	}

	private static void probarEnteros() {
		System.out.println("=== ListaDoble<Integer> ===");
		ListaDoble<Integer> lista = new ListaDoble<>();
		mostrarEnAmbosSentidos(lista, "Lista vacía");
		System.out.println("eliminar(10) en lista vacía -> " + lista.eliminar(10));

		lista.insertarAlInicio(20);
		lista.insertarAlInicio(10);
		lista.insertarAlFinal(30);
		lista.insertarAlFinal(40);
		lista.insertarAlFinal(50);
		mostrarEnAmbosSentidos(lista, "Después de insertar al inicio y al final");

		eliminarYMostrar(lista, 10, "Eliminar primero");
		eliminarYMostrar(lista, 50, "Eliminar último");
		eliminarYMostrar(lista, 30, "Eliminar del medio");
		eliminarYMostrar(lista, 99, "Dato inexistente");
		eliminarYMostrar(lista, 40, "Dejar un solo elemento");
		eliminarYMostrar(lista, 20, "Eliminar el único elemento");
	}

	private static void probarCadenas() {
		System.out.println("\n=== ListaDoble<String> ===");
		ListaDoble<String> lista = new ListaDoble<>();
		lista.insertarAlInicio("b");
		lista.insertarAlInicio("a");
		lista.insertarAlFinal("c");
		mostrarEnAmbosSentidos(lista, "Cadenas insertadas");
		System.out.println("eliminar(\"b\") -> " + lista.eliminar(new String("b")));
		mostrarEnAmbosSentidos(lista, "Después de eliminar b por contenido");
	}

	private static void probarAlumnos() {
		System.out.println("\n=== ListaDoble<Alumno> ===");
		ListaDoble<Alumno> lista = new ListaDoble<>();
		lista.insertarAlFinal(new Alumno("Ana", 101));
		lista.insertarAlFinal(new Alumno("Luis", 202));
		lista.insertarAlFinal(new Alumno("Marta", 303));
		mostrarEnAmbosSentidos(lista, "Alumnos insertados");
		boolean eliminado = lista.eliminar(new Alumno("Ana", 101));
		System.out.println("Eliminar Alumno creado aparte con mismo contenido -> " + eliminado);
		mostrarEnAmbosSentidos(lista, "Después de eliminar Ana");
	}

	private static <T> void eliminarYMostrar(ListaDoble<T> lista, T dato, String titulo) {
		System.out.println(titulo + " (" + dato + ") -> " + lista.eliminar(dato));
		mostrarEnAmbosSentidos(lista, "Estado posterior");
	}

	private static <T> void mostrarEnAmbosSentidos(ListaDoble<T> lista, String titulo) {
		System.out.println(titulo + ":");
		System.out.print("  Adelante: ");
		lista.imprimirAdelante();
		System.out.print("  Atrás:    ");
		lista.imprimirAtras();
		System.out.println("  size=" + lista.getSize());
	}
}
