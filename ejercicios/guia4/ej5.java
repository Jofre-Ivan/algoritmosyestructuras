/*Necesito una clase genérica Pila<T> en Java, implementada con un arreglo de tamaño fijo, sin usar Stack, ArrayList ni ArrayDeque. Tiene que poder guardar Integer, String u objetos simples (por ejemplo una clase Persona con nombre y edad), pero cada pila guarda un solo tipo.

Los genéricos resuelven el problema de tener que repetir la misma pila para cada tipo de dato. Con una PilaEnteros hecha con int[] solo puedo guardar enteros, y para guardar Strings tendría que copiar la clase entera y cambiar el tipo. La otra salida sería usar Object, pero entonces tengo que hacer casteos al sacar los datos y puedo mezclar tipos sin que el compilador me avise, con riesgo de ClassCastException en ejecución. Con Pila<T> escribo la lógica una sola vez y el compilador controla que Pila<Integer> solo acepte Integer y que Pila<String> solo acepte String, sin casteos al hacer pop. Quiero esta explicación en un comentario al inicio de la clase.

Operaciones: push, pop, peek, isEmpty, isFull y size. La capacidad se pasa por constructor y si es menor o igual a 0 lanza IllegalArgumentException.

Para el arreglo interno: en Java no se puede hacer new T[capacidad] por el borrado de tipos, así que hay que crear un Object[] y castearlo a T[], con @SuppressWarnings("unchecked") y un comentario que explique por qué es seguro acá. La variable top arranca en -1 (pila vacía); push incrementa top y guarda en arreglo[top]; pop devuelve arreglo[top], pone esa posición en null (para no retener referencias a objetos que ya no están en la pila) y decrementa top; peek devuelve arreglo[top] sin modificarlo; size es top + 1.

Casos límite: push con la pila llena y pop/peek con la pila vacía lanzan una excepción con mensaje claro ("Pila llena" o "Pila vacía"), no devuelven null. Un push de null no se acepta (IllegalArgumentException). Los tipos primitivos no se pueden usar como T, se usan las clases envoltorio como Integer.

Comentá el codigo Hacé un main que pruebe Pila<Integer>, Pila<String> y Pila<Persona>, con apilar hasta llenar, un push de más, desapilar todo y un pop con la pila vacía, mostrando el resultado de cada paso. Agregá un comentario en el main indicando que la línea que intenta guardar un String en una Pila<Integer> no compila, y por qué eso es una ventaja. */
package ejercicios.guia4;

/** Persona sencilla para demostrar que la pila también almacena objetos propios. */
public class ej5 {
	private static class Persona {
		private final String nombre;
		private final int edad;

		private Persona(String nombre, int edad) {
			this.nombre = nombre;
			this.edad = edad;
		}

		@Override
		public String toString() {
			return nombre + " (" + edad + " años)";
		}
	}

	public static void main(String[] args) {
		Pila<Integer> pilaEnteros = new Pila<>(2);
		probarPila("Pila<Integer>", pilaEnteros,
				new Integer[]{10, 20}, 30);

		Pila<String> pilaCadenas = new Pila<>(2);
		probarPila("Pila<String>", pilaCadenas,
				new String[]{"uno", "dos"}, "tres");

		Pila<Persona> pilaPersonas = new Pila<>(2);
		probarPila("Pila<Persona>", pilaPersonas,
				new Persona[]{new Persona("Ana", 25), new Persona("Luis", 31)},
				new Persona("Mia", 19));

		// pilaEnteros.push("texto"); // No compila: Pila<Integer> solo acepta Integer.
		// El compilador evita mezclar tipos y no hace falta castear el resultado de pop().
	}

	private static <T> void probarPila(String titulo, Pila<T> pila, T[] elementos, T extra) {
		System.out.println("=== " + titulo + " ===");
		for (T elemento : elementos) {
			pila.push(elemento);
			System.out.println("push(" + elemento + ") | tamaño: " + pila.size()
					+ " | cima: " + pila.peek());
		}
		System.out.println("¿Pila llena?: " + pila.isFull());

		try {
			pila.push(extra);
		} catch (IllegalStateException exception) {
			System.out.println("Push adicional: " + exception.getMessage());
		}

		while (!pila.isEmpty()) {
			System.out.println("pop() -> " + pila.pop() + " | tamaño restante: " + pila.size());
		}

		try {
			pila.pop();
		} catch (IllegalStateException exception) {
			System.out.println("Pop con pila vacía: " + exception.getMessage());
		}
		System.out.println("¿Pila vacía?: " + pila.isEmpty() + "\n");
	}
}

/**
 * Una sola implementación genérica evita repetir pilas para cada tipo. Pila<T>
 * permite que el compilador impida mezclar Integer, String u otros tipos, a
 * diferencia de usar Object y hacer casteos que podrían fallar en ejecución.
 */
class Pila<T> {
	private final T[] arreglo;
	private final int capacidad;
	// top señala el último elemento; -1 representa que la pila está vacía.
	private int top = -1;

	@SuppressWarnings("unchecked")
	public Pila(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
		}
		this.capacidad = capacidad;
		// Java borra el tipo genérico y no permite new T[]. El cast es seguro porque
		// solo se insertan T y solo se devuelve T mediante esta clase.
		this.arreglo = (T[]) new Object[capacidad];
	}

	public void push(T elemento) {
		if (isFull()) {
			throw new IllegalStateException("Pila llena");
		}
		if (elemento == null) {
			throw new IllegalArgumentException("No se acepta null en la pila");
		}
		arreglo[++top] = elemento;
	}

	public T pop() {
		if (isEmpty()) {
			throw new IllegalStateException("Pila vacía");
		}
		T elemento = arreglo[top];
		arreglo[top] = null;
		top--;
		return elemento;
	}

	public T peek() {
		if (isEmpty()) {
			throw new IllegalStateException("Pila vacía");
		}
		return arreglo[top];
	}

	public boolean isEmpty() {
		return top == -1;
	}

	public boolean isFull() {
		return top == capacidad - 1;
	}

	public int size() {
		return top + 1;
	}
}
