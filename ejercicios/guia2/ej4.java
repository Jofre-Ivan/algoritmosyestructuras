package ejercicios.guia2;

/*Quiero implementar en Java una función recursiva que calcule la potencia `base^exponente` sin usar `Math.pow`.

Este problema puede resolverse recursivamente porque una potencia es una multiplicación sucesiva de la base por sí misma tantas veces como indica el exponente. Es decir, `base^exponente = base * base^(exponente - 1)`. Cada potencia se expresa como una multiplicación por una potencia más chica del mismo problema, por ejemplo `2^4 = 2 * 2^3`.

El caso base es `exponente == 0`, porque cualquier número elevado a 0 vale 1 (el elemento neutro de la multiplicación). Además, es la condición a la que siempre se llega al reducir el exponente de a uno, por lo que la recursión termina. Por convención, `0^0` devuelve 1.

El caso recursivo es `base * potencia(base, exponente - 1)`, porque en cada llamada el exponente se reduce en 1 mientras la base permanece constante, acercando el problema al caso base.

La función debe devolver:
- `1` cuando el exponente es 0 (caso base);
- `base * potencia(base, exponente - 1)` cuando el exponente es mayor que 0 (caso recursivo);
- el resultado como `long`, para soportar valores más grandes que un `int`;
- una `IllegalArgumentException` con un mensaje claro si el exponente es negativo, porque la función solo trabaja con exponentes enteros no negativos.

No debe usar `Math.pow` ni ninguna otra función de potencia de la librería estándar.

Quiero que el código tenga comentarios explicando el caso base, el caso recursivo y la pila de llamadas. En particular, quiero que incluya un ejemplo de la pila de llamadas para `potencia(2, 4)`, mostrando cómo se apilan `2 * potencia(2, 3)`, `2 * potencia(2, 2)`, `2 * potencia(2, 1)`, `2 * potencia(2, 0)`, y cómo luego se resuelven al desapilar: `1`, `2`, `4`, `8` y finalmente `16`.

Antes del código, quiero una explicación breve de la estrategia y de la complejidad temporal y espacial (incluyendo el costo de la pila de llamadas).

También quiero probarlo con estos casos, mostrando en un `main` el resultado esperado y el obtenido:
- `potencia(2, 3)` → 8 (caso general)
- `potencia(5, 0)` → 1 (caso base)
- `potencia(7, 1)` → 7 (exponente 1)
- `potencia(0, 5)` → 0 (base cero)
- `potencia(0, 0)` → 1 (convención)
- `potencia(1, 100)` → 1 (base 1)
- `potencia(-2, 3)` → -8 (base negativa, exponente impar)
- `potencia(-2, 4)` → 16 (base negativa, exponente par)
- `potencia(2, -1)` → debe lanzar `IllegalArgumentException` (exponente negativo) */
public class ej4 {

	/**
	 * Calcula una potencia sin usar Math.pow.
	 *
	 * @param base base entera de la potencia
	 * @param exponente exponente entero no negativo
	 * @return el resultado como long
	 * @throws IllegalArgumentException si el exponente es negativo
	 * @throws ArithmeticException si el resultado no cabe en un long
	 */
	public static long potencia(int base, int exponente) {
		if (exponente < 0) {
			throw new IllegalArgumentException("El exponente no puede ser negativo.");
		}

		// Caso base: cualquier base elevada a cero vale 1.
		// Por convención, este método también devuelve 1 para 0^0.
		if (exponente == 0) {
			return 1L;
		}

		// Caso recursivo: se multiplica la base por la misma potencia
		// con un exponente menor, acercándose al caso base.
		// La multiplicación exacta detecta si el resultado excede el rango long.
		// Para potencia(2, 4), la pila acumula:
		// 2 * potencia(2, 3)
		// 2 * potencia(2, 2)
		// 2 * potencia(2, 1)
		// 2 * potencia(2, 0)
		// Al desapilar: potencia(2, 0) devuelve 1; luego se obtienen
		// 2, 4, 8 y finalmente 16.
		return Math.multiplyExact((long) base, potencia(base, exponente - 1));
	}

	/**
	 * Imprime los resultados esperados y obtenidos para los casos indicados.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		System.out.println("Estrategia: reducir el exponente en cada llamada hasta el caso base 0.");
		System.out.println("Complejidad temporal: O(n), donde n es el exponente.");
		System.out.println("Complejidad espacial: O(n), por la pila de llamadas recursivas.\n");

		probarCaso(2, 3, 8);
		probarCaso(5, 0, 1);
		probarCaso(7, 1, 7);
		probarCaso(0, 5, 0);
		probarCaso(0, 0, 1);
		probarCaso(1, 100, 1);
		probarCaso(-2, 3, -8);
		probarCaso(-2, 4, 16);

		System.out.println("potencia(2, -1) | esperado: IllegalArgumentException");
		try {
			long obtenido = potencia(2, -1);
			System.out.println("  obtenido: " + obtenido + " (error: se esperaba una excepción)");
		} catch (IllegalArgumentException excepcion) {
			System.out.println("  obtenido: " + excepcion.getClass().getSimpleName()
					+ " - " + excepcion.getMessage());
		}
	}

	/**
	 * Ejecuta un caso y muestra el resultado esperado y el calculado.
	 *
	 * @param base base de la potencia
	 * @param exponente exponente de la potencia
	 * @param esperado resultado esperado
	 */
	private static void probarCaso(int base, int exponente, long esperado) {
		long obtenido = potencia(base, exponente);
		System.out.println("potencia(" + base + ", " + exponente + ")"
				+ " | esperado: " + esperado
				+ " | obtenido: " + obtenido
				+ " | correcto: " + (esperado == obtenido));
	}
}
