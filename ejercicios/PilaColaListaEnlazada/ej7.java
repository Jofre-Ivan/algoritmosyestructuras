/*Necesito un programa en Java que reciba una expresión (String) y determine si los paréntesis están balanceados, mostrando "válida" o "inválida". Uso una pila implementada con lista enlazada simple, hecha desde cero, sin usar Stack, ArrayList, LinkedList ni ArrayDeque. Si ya tengo una PilaEnlazada genérica, reutilizala con T = Character; si no, armá una clase Nodo (dato char, siguiente) y una PilaEnlazada con tope (head) y size. Solo se controlan los paréntesis redondos y el resto de los caracteres se ignoran.

Se usa una pila porque el último paréntesis que se abre es el primero que tiene que cerrarse (LIFO). Cada cierre se tiene que corresponder con la apertura más reciente que sigue pendiente, y esa es justo la que está en el tope.

Recorro la expresión de izquierda a derecha, carácter por carácter:
- Cuando aparece un "(", hago push: es una apertura que todavía no encontró su cierre.
- Cuando aparece un ")", hago pop: ese cierre cancela a la última apertura pendiente. Si en ese momento la pila está vacía, no hay nada que cerrar, así que la expresión es inválida y corto ahí mismo.
- Al terminar de recorrer, si la pila no quedó vacía sobraron aperturas, así que también es inválida.
La expresión es válida solo si nunca intenté hacer pop con la pila vacía y al final la pila quedó vacía. Quiero esta explicación en un comentario arriba del método.

Casos a contemplar: expresión vacía o sin paréntesis (válida), solo "(" o solo ")" (inválida), "(2 + 3" (sobra una apertura), y "())(" que tiene la misma cantidad de aperturas y cierres pero está mal ordenado: falla en el tercer carácter porque el pop se hace con la pila vacía. Por eso no alcanza con un contador. No usar un contador entero como solución, tiene que usarse la pila. Validar que la expresión no sea null.

Comentá el código en español explicando cuándo se hace push, cuándo pop y por qué. En el main probá los ejemplos válidos "(2 + 3) * (5 - 1)" y "((a + b) * c)", los inválidos "(2 + 3" y "())(", y los casos límite de arriba, mostrando la expresión y el resultado de cada uno.

*/ 
package ejercicios.guia6;

/** Verifica el balance de paréntesis usando una pila enlazada propia. */
public class ej7 {
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
			if (isEmpty()) {
				throw new IllegalStateException("Pila vacía");
			}
			char dato = tope.dato;
			tope = tope.siguiente;
			size--;
			return dato;
		}

		private boolean isEmpty() {
			return size == 0;
		}
	}

	/*
	 * Se recorre de izquierda a derecha. Al ver '(' se hace push porque esa
	 * apertura queda pendiente; al ver ')' se hace pop porque debe cerrar la
	 * apertura pendiente más reciente (LIFO). Si llega un cierre con la pila vacía,
	 * se devuelve false inmediatamente. Al final también debe quedar vacía, o
	 * sobraron aperturas. La pila es necesaria: contar aperturas y cierres no
	 * detectaría un orden incorrecto, como en "())(".
	 */
	private static boolean parentesisBalanceados(String expresion) {
		if (expresion == null) {
			throw new IllegalArgumentException("La expresión no puede ser null");
		}

		PilaEnlazada aperturas = new PilaEnlazada();
		for (int i = 0; i < expresion.length(); i++) {
			char caracter = expresion.charAt(i);
			if (caracter == '(') {
				aperturas.push(caracter);
			} else if (caracter == ')') {
				if (aperturas.isEmpty()) {
					return false;
				}
				aperturas.pop();
			}
		}
		return aperturas.isEmpty();
	}

	public static void main(String[] args) {
		String[] expresiones = {
				"(2 + 3) * (5 - 1)",
				"((a + b) * c)",
				"(2 + 3",
				"())(",
				"",
				"sin paréntesis",
				"(",
				")"
		};

		for (String expresion : expresiones) {
			mostrarResultado(expresion);
		}
	}

	private static void mostrarResultado(String expresion) {
		boolean valida = parentesisBalanceados(expresion);
		System.out.println("Expresión: \"" + expresion + "\" -> "
				+ (valida ? "válida" : "inválida"));
	}
}
