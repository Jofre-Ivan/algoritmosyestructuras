/*
 Tengo una lista enlazada simple de enteros en Java, la de los ejercicios anteriores (clase Nodo con dato y siguiente, con getDato(), setDato(), getSiguiente() y setSiguiente(), y clase ListaEnlazada con head y size, con insertarAlInicio, insertarAlFinal, insertarEnPosicion, imprimir, estaVacia, getSize, buscar, obtener, eliminar, eliminarEnPosicion, modificar, contarOcurrencias e invertir). Necesito transformarla en una versión genérica: Nodo<T> y ListaEnlazada<T>, para usar ListaEnlazada<Integer>, ListaEnlazada<String> y ListaEnlazada<Alumno>. Sigue sin usar ArrayList, LinkedList ni ninguna estructura de Java.

Lo que cambia es solo el tipo del dato: en Nodo<T> el atributo dato pasa de int a T y el nodo siguiente pasa a ser Nodo<T>; en ListaEnlazada<T> head es Nodo<T>, y los parámetros y retornos que eran int (dato en las inserciones, buscar, eliminar y contarOcurrencias, nuevoDato en modificar, retorno de obtener) pasan a ser T. Las variables auxiliares (actual, anterior, siguiente, nuevo) pasan a ser Nodo<T>. Tipos primitivos no se pueden usar como T, se usan las clases envoltorio como Integer.

Lo que cambia además, y no es solo reemplazar int por T: las comparaciones de datos. Con int se usaba ==, pero con objetos == compara referencias y no contenido, así que buscar, eliminar y contarOcurrencias deben usar dato.equals(actual.getDato()) y no ==. Para eso, la clase Alumno (con nombre y legajo) tiene que sobrescribir equals y hashCode, y toString para que imprimir() muestre algo legible. Como T puede ser null, comparar con Objects.equals(a, b) o decidir explícitamente si se acepta null (prefiero rechazarlo con una IllegalArgumentException en las inserciones, y dejarlo documentado).

Lo que se mantiene igual: la estructura de nodos y referencias, head y size, los recorridos con auxiliar hasta null, el orden de actualización de enlaces en inserciones, eliminaciones e invertir, las validaciones de posiciones con sus excepciones, y los casos límite (lista vacía, un solo elemento, primer y último nodo). Quiero un comentario al inicio de la clase que explique por qué: los algoritmos solo mueven referencias entre nodos y nunca miran qué hay dentro del dato. Recorrer, insertar y eliminar es cambiar a qué nodo apunta cada siguiente, y eso es igual si el nodo guarda un Integer, un String o un Alumno. El único momento en que el algoritmo necesita "mirar" el dato es al comparar (buscar, eliminar, contarOcurrencias), por eso esa es la única parte que depende del tipo y la que se resuelve con equals. Con genéricos escribo la lógica una sola vez y el compilador controla que cada lista guarde un solo tipo, sin casteos ni ClassCastException. A diferencia de la pila genérica con arreglo, acá no hace falta new T[] ni @SuppressWarnings, porque la lista no usa arreglos, solo nodos.

Comentá el código en español. En el main probá ListaEnlazada<Integer> (por ejemplo 10, 20, 30), ListaEnlazada<String> ("a", "b", "c") y ListaEnlazada<Alumno> con tres alumnos, ejecutando en cada una insertar, imprimir, buscar, eliminar, modificar, contarOcurrencias e invertir. Verificá especialmente que buscar y eliminar encuentren un Alumno creado aparte pero con el mismo contenido (new Alumno("Ana", 101)), que es lo que prueba que se usa equals. Agregá un comentario en el main indicando que guardar un String en una ListaEnlazada<Integer> no compila, y por qué es una ventaja.
ademas este mismo promp todo completo agregalo como comentario al principio del codigop orfavor
*/
package ejercicios.guia5;

import java.util.Objects;

/*
 * La lista es genérica porque sus algoritmos solo enlazan y desenlazan nodos;
 * esas operaciones no dependen del tipo del dato guardado. Solo buscar,
 * eliminar y contar comparan datos, y lo hacen con equals. Los genéricos
 * permiten reutilizar la implementación con seguridad de tipos, sin casteos.
 * Como se usan nodos y no arreglos, no hace falta crear un arreglo genérico.
 */
public class ej10 {
	private static class Nodo<T> {
		private T dato;
		private Nodo<T> siguiente;

		private Nodo(T dato) {
			this.dato = dato;
		}

		private T getDato() {
			return dato;
		}

		private void setDato(T dato) {
			this.dato = dato;
		}

		private Nodo<T> getSiguiente() {
			return siguiente;
		}

		private void setSiguiente(Nodo<T> siguiente) {
			this.siguiente = siguiente;
		}
	}

	private static class ListaEnlazada<T> {
		private Nodo<T> head;
		private int size;

		private void insertarAlInicio(T dato) {
			validarDato(dato);
			Nodo<T> nuevo = new Nodo<>(dato);
			nuevo.setSiguiente(head);
			head = nuevo;
			size++;
		}

		private void insertarAlFinal(T dato) {
			validarDato(dato);
			Nodo<T> nuevo = new Nodo<>(dato);
			if (head == null) {
				head = nuevo;
			} else {
				Nodo<T> actual = head;
				while (actual.getSiguiente() != null) {
					actual = actual.getSiguiente();
				}
				actual.setSiguiente(nuevo);
			}
			size++;
		}

		private void insertarEnPosicion(T dato, int posicion) {
			validarDato(dato);
			if (posicion < 0 || posicion > size) {
				throw new IndexOutOfBoundsException(mensajePosicion(posicion));
			}

			Nodo<T> nuevo = new Nodo<>(dato);
			if (posicion == 0) {
				nuevo.setSiguiente(head);
				head = nuevo;
			} else {
				Nodo<T> actual = head;
				for (int i = 0; i < posicion - 1; i++) {
					actual = actual.getSiguiente();
				}
				nuevo.setSiguiente(actual.getSiguiente());
				actual.setSiguiente(nuevo);
			}
			size++;
		}

		private void imprimir() {
			if (estaVacia()) {
				System.out.println("Lista vacía");
				return;
			}
			Nodo<T> actual = head;
			while (actual != null) {
				System.out.print(actual.getDato() + " -> ");
				actual = actual.getSiguiente();
			}
			System.out.println("null");
		}

		private boolean estaVacia() {
			return head == null;
		}

		private int getSize() {
			return size;
		}

		private boolean buscar(T dato) {
			Nodo<T> actual = head;
			while (actual != null) {
				if (Objects.equals(dato, actual.getDato())) {
					return true;
				}
				actual = actual.getSiguiente();
			}
			return false;
		}

		private T obtener(int posicion) {
			validarPosicionExistente(posicion);
			Nodo<T> actual = head;
			for (int i = 0; i < posicion; i++) {
				actual = actual.getSiguiente();
			}
			return actual.getDato();
		}

		private boolean eliminar(T dato) {
			if (head == null) {
				return false;
			}
			if (Objects.equals(dato, head.getDato())) {
				head = head.getSiguiente();
				size--;
				return true;
			}

			Nodo<T> actual = head;
			while (actual.getSiguiente() != null) {
				if (Objects.equals(dato, actual.getSiguiente().getDato())) {
					actual.setSiguiente(actual.getSiguiente().getSiguiente());
					size--;
					return true;
				}
				actual = actual.getSiguiente();
			}
			return false;
		}

		private void eliminarEnPosicion(int posicion) {
			validarPosicionExistente(posicion);
			if (posicion == 0) {
				head = head.getSiguiente();
			} else {
				Nodo<T> actual = head;
				for (int i = 0; i < posicion - 1; i++) {
					actual = actual.getSiguiente();
				}
				actual.setSiguiente(actual.getSiguiente().getSiguiente());
			}
			size--;
		}

		private void modificar(int posicion, T nuevoDato) {
			validarPosicionExistente(posicion);
			validarDato(nuevoDato);
			Nodo<T> actual = head;
			for (int i = 0; i < posicion; i++) {
				actual = actual.getSiguiente();
			}
			actual.setDato(nuevoDato);
		}

		private int contarOcurrencias(T dato) {
			int cantidad = 0;
			Nodo<T> actual = head;
			while (actual != null) {
				if (Objects.equals(dato, actual.getDato())) {
					cantidad++;
				}
				actual = actual.getSiguiente();
			}
			return cantidad;
		}

		private void invertir() {
			Nodo<T> anterior = null;
			Nodo<T> actual = head;
			Nodo<T> siguiente;
			while (actual != null) {
				siguiente = actual.getSiguiente();
				actual.setSiguiente(anterior);
				anterior = actual;
				actual = siguiente;
			}
			head = anterior;
		}

		private void validarPosicionExistente(int posicion) {
			if (posicion < 0 || posicion >= size) {
				throw new IndexOutOfBoundsException(mensajePosicion(posicion));
			}
		}

		private String mensajePosicion(int posicion) {
			return "Posición " + posicion + " inválida; tamaño de la lista: " + size;
		}

		// Se rechaza null para que los elementos de la lista siempre sean comparables y válidos.
		private void validarDato(T dato) {
			if (dato == null) {
				throw new IllegalArgumentException("La lista no acepta elementos null");
			}
		}
	}

	/** Alumno compara su contenido por nombre y legajo, no por identidad de referencia. */
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
			if (!(objeto instanceof Alumno otro)) {
				return false;
			}
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
		// No compila: la verificación de tipos impide mezclar String en una ListaEnlazada<Integer>.
		// ListaEnlazada<Integer> soloNumeros = new ListaEnlazada<>(); soloNumeros.insertarAlFinal("texto");

		demostrarOperaciones("Integer", new ListaEnlazada<>(),
				new Integer[]{10, 20, 30}, 10, -5, 20);
		demostrarOperaciones("String", new ListaEnlazada<>(),
				new String[]{"a", "b", "c"}, "a", "z", "modificado");

		ListaEnlazada<Alumno> alumnos = new ListaEnlazada<>();
		Alumno ana = new Alumno("Ana", 101);
		Alumno bea = new Alumno("Bea", 102);
		Alumno carlos = new Alumno("Carlos", 103);
		Alumno anaBusqueda = new Alumno("Ana", 101);
		demostrarOperaciones("Alumno", alumnos,
				new Alumno[]{ana, bea, carlos}, anaBusqueda,
				new Alumno("Diego", 104), new Alumno("Elena", 105));
	}

	private static <T> void demostrarOperaciones(String tipo, ListaEnlazada<T> lista,
			T[] elementos, T valorBusqueda, T reemplazo, T agregadoFinal) {
		System.out.println("=== ListaEnlazada<" + tipo + "> ===");
		lista.insertarAlInicio(elementos[0]);
		lista.insertarAlFinal(elementos[1]);
		lista.insertarEnPosicion(elementos[2], 1);
		System.out.print("Después de insertar al inicio, al final y en posición 1: ");
		lista.imprimir();
		System.out.println("size=" + lista.getSize() + ", estaVacia=" + lista.estaVacia());

		System.out.println("buscar(valor inicial) -> " + lista.buscar(valorBusqueda));
		System.out.println("obtener(1) -> " + lista.obtener(1));
		System.out.println("contarOcurrencias(valor inicial) -> "
				+ lista.contarOcurrencias(valorBusqueda));

		lista.modificar(2, reemplazo);
		System.out.print("Después de modificar la posición 2: ");
		lista.imprimir();
		System.out.println("size sin cambios: " + lista.getSize());

		boolean eliminado = lista.eliminar(valorBusqueda);
		System.out.println("eliminar(valor inicial) -> " + eliminado);
		System.out.print("Después de eliminar: ");
		lista.imprimir();
		System.out.println("size=" + lista.getSize());

		lista.eliminarEnPosicion(0);
		System.out.print("Después de eliminarEnPosicion(0): ");
		lista.imprimir();
		System.out.println("size=" + lista.getSize());

		lista.insertarAlFinal(agregadoFinal);
		lista.invertir();
		System.out.print("Después de agregar otro elemento e invertir: ");
		lista.imprimir();
		System.out.println("size=" + lista.getSize() + "\n");
	}
}
