/*Tengo una pila de enteros en Java implementada con lista enlazada simple (clase NodoPila con dato y siguiente, y clase PilaEnlazada con tope, que es el head, y size, sin Stack, ArrayList ni LinkedList). Necesito transformarla en una versión genérica, Nodo<T> y PilaEnlazada<T>, para usar PilaEnlazada<Integer>, PilaEnlazada<String> y PilaEnlazada<Alumno>, con una clase Alumno propia (nombre y legajo). Sigue sin usar estructuras de Java.

Usar genéricos significa que la clase se escribe con un parámetro de tipo T, que es un tipo "a completar" cuando se crea el objeto. Al hacer new PilaEnlazada<String>(), T pasa a ser String y el compilador controla que esa pila solo acepte Strings, sin casteos al desapilar y sin riesgo de ClassCastException. Quiero esta explicación en un comentario arriba de la clase.

La lógica de la pila no depende del tipo porque push, pop y peek solo mueven referencias entre nodos: push hace que el nodo nuevo apunte al tope y que el tope sea el nodo nuevo, pop hace tope = tope.siguiente. En ningún momento miran qué hay dentro del dato, así que funciona igual si el nodo guarda un Integer, un String o un Alumno. Por eso push y pop siguen siendo O(1) y el orden de actualización de enlaces no cambia.

Qué cambia: en Nodo<T>, dato pasa de int a T y siguiente pasa a ser Nodo<T>; en PilaEnlazada<T>, tope es Nodo<T>, y los parámetros y retornos que eran int (push, pop, peek, buscar) pasan a ser T. No se pueden usar tipos primitivos como T, se usan las clases envoltorio como Integer.

Lo único que depende del tipo es la comparación en buscar. Con int se usaba ==, pero con objetos == compara referencias y no contenido, así que hay que usar equals. Alumno tiene que sobrescribir equals, hashCode y toString (para que imprimir muestre algo legible). Rechazar null en push con IllegalArgumentException, así no hay que manejar null en equals.

Todo lo demás se mantiene igual: pop y peek con la pila vacía lanzan excepción con mensaje "Pila vacía", imprimir y buscar no modifican la pila, y size se actualiza igual.

Comentá el código en español. En el main probá PilaEnlazada<Integer>, PilaEnlazada<String> y PilaEnlazada<Alumno>, con push, peek, imprimir, buscar, pop hasta vaciarla y un pop con la pila vacía capturando la excepción. Verificá que buscar encuentre un Alumno creado aparte pero con el mismo contenido (new Alumno("Ana", 101)), que prueba que se usa equals. Agregá un comentario en el main indicando que guardar un String en una PilaEnlazada<Integer> no compila, y por qué es una ventaja.


 */
package ejercicios.guia6;

import java.util.Objects;

/**
 * Los genéricos permiten escribir la pila una vez usando T como tipo a completar.
 * Al crear PilaEnlazada<String>, el compilador garantiza que esa pila solo guarde
 * String y permite recuperar valores sin casteos ni ClassCastException.
 * La lógica no depende del tipo: push, pop y peek solo cambian referencias entre
 * nodos y nunca inspeccionan el dato; por eso push y pop siguen siendo O(1).
 */
public class ej3 {
	private static class Nodo<T> {
		private final T dato;
		private Nodo<T> siguiente;

		private Nodo(T dato) {
			this.dato = dato;
		}
	}

	private static class PilaEnlazada<T> {
		// tope apunta al primer nodo, que es el elemento más reciente (LIFO).
		private Nodo<T> tope;
		private int size;

		private void push(T dato) {
			if (dato == null) {
				throw new IllegalArgumentException("La pila no acepta null");
			}
			Nodo<T> nuevo = new Nodo<>(dato);
			nuevo.siguiente = tope;
			tope = nuevo;
			size++;
		}

		private T pop() {
			if (isEmpty()) {
				throw new IllegalStateException("Pila vacía");
			}
			T dato = tope.dato;
			tope = tope.siguiente;
			size--;
			return dato;
		}

		private T peek() {
			if (isEmpty()) {
				throw new IllegalStateException("Pila vacía");
			}
			return tope.dato;
		}

		private boolean buscar(T dato) {
			Nodo<T> actual = tope;
			while (actual != null) {
				if (actual.dato.equals(dato)) {
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
			Nodo<T> actual = tope;
			while (actual != null) {
				System.out.print(actual.dato + " -> ");
				actual = actual.siguiente;
			}
			System.out.println("null");
		}

		private boolean isEmpty() {
			return tope == null;
		}

		private int size() {
			return size;
		}
	}

	/** Alumno identifica igualdad por nombre y legajo, no por referencia. */
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
		// No compila y eso es una ventaja: el compilador impide mezclar tipos en la misma pila.
		// PilaEnlazada<Integer> soloEnteros = new PilaEnlazada<>(); soloEnteros.push("texto");

		demostrarPila("Integer", new PilaEnlazada<>(),
				new Integer[]{10, 20, 30}, 20);
		demostrarPila("String", new PilaEnlazada<>(),
				new String[]{"uno", "dos", "tres"}, "dos");

		PilaEnlazada<Alumno> pilaAlumnos = new PilaEnlazada<>();
		pilaAlumnos.push(new Alumno("Ana", 101));
		pilaAlumnos.push(new Alumno("Luis", 202));
		pilaAlumnos.push(new Alumno("Mia", 303));
		System.out.println("=== PilaEnlazada<Alumno> ===");
		System.out.println("peek() -> " + pilaAlumnos.peek() + " | size=" + pilaAlumnos.size());
		pilaAlumnos.imprimir();
		Alumno anaEquivalente = new Alumno("Ana", 101);
		System.out.println("buscar(new Alumno(\"Ana\", 101)) -> "
				+ pilaAlumnos.buscar(anaEquivalente) + " | size=" + pilaAlumnos.size());
		vaciarPila(pilaAlumnos);
	}

	private static <T> void demostrarPila(String tipo, PilaEnlazada<T> pila,
			T[] valores, T valorABuscar) {
		System.out.println("=== PilaEnlazada<" + tipo + "> ===");
		for (T valor : valores) {
			pila.push(valor);
			System.out.println("push(" + valor + ") | size=" + pila.size());
		}
		System.out.println("peek() -> " + pila.peek() + " | size=" + pila.size());
		System.out.print("Contenido desde el tope: ");
		pila.imprimir();
		System.out.println("buscar(" + valorABuscar + ") -> " + pila.buscar(valorABuscar)
				+ " | size=" + pila.size());
		vaciarPila(pila);
	}

	private static <T> void vaciarPila(PilaEnlazada<T> pila) {
		while (!pila.isEmpty()) {
			System.out.println("pop() -> " + pila.pop() + " | size=" + pila.size());
		}
		try {
			pila.pop();
		} catch (IllegalStateException exception) {
			System.out.println("pop() con pila vacía -> " + exception.getMessage()
					+ " | size=" + pila.size() + "\n");
		}
	}
}
