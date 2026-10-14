package ejercicios.guia2;
/*Quiero implementar en Java una función recursiva que imprima un conteo regresivo desde `n` hasta 0.

Este problema puede resolverse recursivamente porque un conteo desde `n` hasta 0 consiste en imprimir `n` y luego hacer el conteo desde `n - 1` hasta 0, que es el mismo problema con un valor más chico. Por ejemplo, el conteo desde 3 es: imprimir 3 y luego contar desde 2.

La función no necesariamente devuelve un valor: su tarea es producir un efecto (imprimir por consola), no calcular un resultado. Por eso quiero que sea `void`. Esto significa que la recursión no combina resultados de las llamadas anteriores; cada llamada hace su trabajo (imprimir) y delega el resto a la siguiente. Quiero que el código comente esta diferencia con las funciones que sí devuelven un valor, como un factorial.

El caso base es `n == 0`, porque es el último número que se debe imprimir y a partir de ahí no queda nada más por contar. En ese caso la función imprime `0` y termina sin hacer otra llamada recursiva.

El caso recursivo es imprimir `n` y luego llamar a `contar(n - 1)`, porque en cada llamada el parámetro se reduce en 1, acercándose al caso base. Si `n` es positivo, siempre se llega a 0 en exactamente `n` pasos.

Quiero que el código incluya un comentario que explique qué pasaría si en lugar de `n - 1` se usara `n + 1`: el valor se alejaría del caso base en lugar de acercarse, la condición `n == 0` nunca se cumpliría, y la recursión nunca terminaría. Como cada llamada queda apilada esperando a la siguiente, la pila de llamadas crecería hasta producir un `StackOverflowError`. No quiero que esa versión esté implementada en el código; solo explicada en el comentario.

La función debe validar la entrada: si `n` es negativo, debe lanzar una `IllegalArgumentException` con un mensaje claro, porque con `n - 1` un valor negativo se alejaría de 0 y tendría el mismo problema de recursión infinita.

Quiero que el código tenga comentarios explicando el caso base, el caso recursivo y la pila de llamadas. En particular, quiero que incluya un ejemplo de la pila de llamadas para `contar(3)`, mostrando cómo se apilan `contar(3)`, `contar(2)`, `contar(1)` y `contar(0)`, en qué momento se imprime cada valor, y cómo luego se desapilan en orden inverso hasta terminar. Quiero que se aclare que en esta versión el valor se imprime antes de la llamada recursiva, y por eso el conteo sale en orden descendente.

Antes del código, quiero una explicación breve de la estrategia y de la complejidad temporal y espacial (incluyendo el costo de la pila de llamadas).

También quiero probarlo con estos casos, mostrando en un `main` lo que se imprime y lo que se esperaba:
- `contar(5)` → imprime 5, 4, 3, 2, 1, 0 (caso general)
- `contar(1)` → imprime 1, 0 (caso mínimo con una llamada recursiva)
- `contar(0)` → imprime solo 0 (caso base directo)
- `contar(-1)` → debe lanzar `IllegalArgumentException` (valor negativo) */
/**
 * Imprime recursivamente un conteo descendente desde n hasta cero.
 * Estrategia: cada llamada imprime su valor antes de delegar en n - 1.
 * Complejidad temporal: O(n); complejidad espacial: O(n), debido a las
 * llamadas que permanecen en la pila hasta alcanzar el caso base.
 */
public class ej5 {

	/**
	 * Imprime los enteros desde n hasta 0, separados por flechas.
	 *
	 * @param n número inicial no negativo
	 * @throws IllegalArgumentException si n es negativo
	 */
	public static void contar(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("El valor inicial no puede ser negativo.");
		}

		// A diferencia de una función como factorial, este método es void:
		// no devuelve ni combina resultados; su propósito es imprimir valores.

		// Caso base: imprime 0 y termina sin realizar otra llamada recursiva.
		if (n == 0) {
			System.out.println(0);
			return;
		}

		// Caso recursivo: imprime n ANTES de llamar a contar(n - 1).
		// Por eso los valores aparecen en orden descendente al avanzar las llamadas.
		System.out.print(n + " -> ");

		// Pila para contar(3): se apilan contar(3), contar(2), contar(1), contar(0).
		// Cada llamada imprime su valor antes de apilar la siguiente: 3, 2, 1, 0.
		// contar(0) imprime el último valor y retorna; luego se desapilan contar(1),
		// contar(2) y contar(3), en orden inverso, hasta que finaliza el método.
		// Si se usara n + 1, el valor se alejaría de cero y n == 0 nunca ocurriría;
		// las llamadas crecerían hasta producir StackOverflowError. Por el mismo
		// motivo, se rechazan negativos: al restar uno, también se alejan de cero.
		contar(n - 1);
	}

	/**
	 * Muestra el resultado esperado y ejecuta un caso de conteo.
	 *
	 * @param n valor inicial del conteo
	 * @param esperado secuencia esperada o descripción de la excepción
	 */
	private static void probarCaso(int n, String esperado) {
		System.out.println("contar(" + n + ")");
		System.out.println("Esperado: " + esperado);
		System.out.print("Obtenido: ");

		try {
			contar(n);
		} catch (IllegalArgumentException excepcion) {
			System.out.println(excepcion.getClass().getSimpleName()
					+ ": " + excepcion.getMessage());
		}
		System.out.println();
	}

	/**
	 * Prueba los casos general, mínimo, base y negativo.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		probarCaso(5, "5 -> 4 -> 3 -> 2 -> 1 -> 0");
		probarCaso(1, "1 -> 0");
		probarCaso(0, "0");
		probarCaso(-1, "IllegalArgumentException");

		System.out.println("Complejidad temporal: O(n).");
		System.out.println("Complejidad espacial: O(n), por la pila recursiva.");
	}
}
