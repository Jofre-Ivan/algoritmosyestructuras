/*Necesito un programa en Java que invierta una palabra usando una pila implementada con lista enlazada simple, hecha desde cero, sin usar Stack, ArrayList, LinkedList, ArrayDeque, StringBuilder.reverse() ni ningún método de Java que invierta. Por ejemplo, "algoritmo" tiene que dar "omtirogla". Si ya tengo una PilaEnlazada genérica, reutilizala con T = Character; si no, armá una clase Nodo (dato char, siguiente) y una PilaEnlazada con tope (head) y size, con push, pop y estaVacia.

La política LIFO permite invertir porque el último elemento que entra a la pila es el primero que sale. Si apilo los caracteres en el orden de la palabra, la primera letra queda en el fondo y la última queda en el tope. Al desapilar todo, salen empezando por la última letra y terminando con la primera, que es el orden inverso. La pila "da vuelta" el orden sin que yo tenga que calcular posiciones. Quiero esta explicación en un comentario arriba del método.

El método invertir(String palabra) funciona en dos fases. Primero recorro la palabra de izquierda a derecha y hago push de cada carácter. Después, mientras la pila no esté vacía, hago pop y voy armando el resultado en un String o StringBuilder (agregando, no invirtiendo). Con "ola" se apila o, l, a, y el tope es "a", entonces se desapila a, l, o. Tanto apilar como desapilar es O(n) en total, porque push y pop son O(1).

Casos a contemplar: palabra vacía (devuelve vacío sin romperse), un solo carácter (queda igual), palíndromos como "reconocer" (queda igual), palabras con mayúsculas, espacios, números o símbolos (cada carácter se trata igual, "Hola mundo" da "odnum aloH"), y que la pila quede vacía al terminar. Validar que la palabra no sea null.

Comentá el código en español explicando cuándo se apila y cuándo se desapila. En el main probá "algoritmo" (tiene que dar "omtirogla"), "ola", "a", "", "reconocer" y "Hola mundo", mostrando la palabra original y la invertida. Después permití ingresar una palabra por teclado con Scanner.


 */
package ejercicios.guia6;

import java.util.Scanner;

public class ej8 {
	private static class Nodo {
		private final char dato;
		private Nodo siguiente;

		private Nodo(char dato) {
			this.dato = dato;
		}
	}

	private static class PilaEnlazada {
		private Nodo tope;
		private int size;

		private void push(char dato) {
			Nodo nuevo = new Nodo(dato);
			nuevo.siguiente = tope;
			tope = nuevo;
			size++;
		}

		private char pop() {
			if (estaVacia()) {
				throw new IllegalStateException("Pila vacía");
			}
			char dato = tope.dato;
			tope = tope.siguiente;
			size--;
			return dato;
		}

		private boolean estaVacia() {
			return size == 0;
		}
	}

	/*
	 * LIFO invierte el orden: al apilar los caracteres de izquierda a derecha,
	 * la última letra queda en el tope. Al desapilar se obtiene primero esa última
	 * letra y luego las anteriores. Por ejemplo, al apilar o, l, a, se desapila
	 * a, l, o. La primera fase hace push de cada carácter; la segunda hace pop y
	 * agrega cada carácter al resultado. Como push y pop son O(1), ambas fases
	 * juntas recorren n caracteres y toman O(n).
	 */
	private static String invertir(String palabra) {
		if (palabra == null) {
			throw new IllegalArgumentException("La palabra no puede ser null");
		}

		PilaEnlazada pila = new PilaEnlazada();
		for (int i = 0; i < palabra.length(); i++) {
			pila.push(palabra.charAt(i));
		}

		StringBuilder invertida = new StringBuilder();
		while (!pila.estaVacia()) {
			invertida.append(pila.pop());
		}
		return invertida.toString();
	}

	public static void main(String[] args) {
		String[] ejemplos = {"algoritmo", "ola", "a", "", "reconocer", "Hola mundo"};
		System.out.println("Inversión de palabras con pila enlazada:");
		for (String ejemplo : ejemplos) {
			mostrarResultado(ejemplo);
		}

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("\nIngrese una palabra o frase para invertir: ");
			String entrada = scanner.nextLine();
			mostrarResultado(entrada);
		}
	}

	private static void mostrarResultado(String palabra) {
		System.out.println("Original: \"" + palabra + "\" | Invertida: \"" + invertir(palabra) + "\"");
	}
}
