/*Necesito un programa en Java que reciba una expresión matemática como String (por ejemplo "(5 + 3) * (2 + 1)") y determine si los paréntesis están balanceados, mostrando "válido" o "inválido". Quiero usar una pila implementada por mí con un arreglo de caracteres, sin usar Stack ni ArrayDeque. Solo hay que controlar paréntesis redondos, el resto de los caracteres se ignoran.

Recorro la expresión de izquierda a derecha, carácter por carácter. Cada vez que aparece un "(" hago push a la pila, porque es un paréntesis abierto que todavía no encontró su cierre. Cada vez que aparece un ")" hago pop, porque ese cierre corresponde al último paréntesis abierto, que es justo el que está arriba de la pila (el último en entrar es el primero en salir, por eso sirve una pila). Si aparece un ")" y la pila está vacía, no hay nada que cerrar, así que la expresión es inválida y corto ahí mismo. Si termino de recorrer la expresión y la pila no quedó vacía, sobraron paréntesis abiertos, entonces también es inválida. Solo es válida si nunca intenté hacer pop con la pila vacía y al final quedó vacía.

Casos límite: expresión vacía o sin paréntesis (válida), solo "(" o solo ")" (inválida), y un caso como ")(" que tiene la misma cantidad de ambos pero está mal ordenado, por lo que no alcanza con contar. La capacidad de la pila tiene que ser al menos el largo de la expresión.

Comentá el código  explicando cuándo se hace push y cuándo pop, y por qué. Hacé un main que pruebe los dos ejemplos de arriba más los casos límite, mostrando la expresión y el resultado de cada uno, y que además permita ingresar una expresión por teclado con Scanner. */
package ejercicios.guia4;

import java.util.Scanner;

public class ej3 {
	private static class PilaCaracteres {
		private final char[] elementos;
		private int top = -1;

		private PilaCaracteres(int capacidad) {
			elementos = new char[capacidad];
		}

		private void push(char caracter) {
			if (top == elementos.length - 1) {
				throw new IllegalStateException("La pila de caracteres está llena");
			}
			elementos[++top] = caracter;
		}

		private char pop() {
			if (isEmpty()) {
				throw new IllegalStateException("La pila de caracteres está vacía");
			}
			return elementos[top--];
		}

		private boolean isEmpty() {
			return top == -1;
		}
	}

	/** Revisa solo paréntesis redondos; los demás caracteres se ignoran. */
	private static boolean parentesisBalanceados(String expresion) {
		PilaCaracteres pila = new PilaCaracteres(expresion.length());

		for (int i = 0; i < expresion.length(); i++) {
			char caracter = expresion.charAt(i);
			if (caracter == '(') {
				// Guarda cada apertura pendiente: su cierre deberá corresponder a esta.
				pila.push(caracter);
			} else if (caracter == ')') {
				// El cierre retira la apertura más reciente, siguiendo la regla LIFO.
				if (pila.isEmpty()) {
					return false;
				}
				pila.pop();
			}
		}

		// Si quedan aperturas en la pila, no encontraron su paréntesis de cierre.
		return pila.isEmpty();
	}

	private static void mostrarResultado(String expresion) {
		System.out.println("Expresión: \"" + expresion + "\" -> "
				+ (parentesisBalanceados(expresion) ? "válido" : "inválido"));
	}

	public static void main(String[] args) {
		System.out.println("Pruebas de paréntesis balanceados:");
		mostrarResultado("(5 + 3) * (2 + 1)");
		mostrarResultado("((8 - 2) / 3) + 4");
		mostrarResultado("");
		mostrarResultado("sin paréntesis");
		mostrarResultado("(");
		mostrarResultado(")");
		mostrarResultado(")(");

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("\nIngrese una expresión: ");
			String expresion = scanner.nextLine();
			mostrarResultado(expresion);
		}
	}
}
