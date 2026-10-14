package ejercicios.guia2;

/**
 Quiero implementar en Java una función recursiva que calcule la multiplicación de dos números enteros sin usar el operador `*`.

Este problema puede resolverse recursivamente porque multiplicar `a * b` equivale a sumar `a` consigo mismo `b` veces. Es decir, `a * b = a + a * (b - 1)`. Cada multiplicación se puede expresar como una suma más una multiplicación más pequeña, hasta llegar a una multiplicación trivial.

El caso base es cuando `b == 0`, porque cualquier número multiplicado por 0 da 0. Además, es la condición a la que siempre se llega al reducir `b` de a uno, por lo que la recursión termina.

El caso recursivo es `a + multiplicar(a, b - 1)`, porque en cada llamada el parámetro `b` se reduce en 1 mientras `a` permanece constante, acercando el problema al caso base. El parámetro que se reduce es `b` (la cantidad de sumas que faltan realizar).

La función debe devolver el producto de `a` y `b` como un `int`. Debe manejar también el caso en que `b` sea negativo: en ese caso debe calcular `multiplicar(a, -b)` y devolver el resultado con el signo cambiado, para que la recursión siempre trabaje con un `b` no negativo y llegue al caso base. No debe usar el operador `*` en ninguna parte de la solución.

Quiero que el código tenga comentarios explicando el caso base, el caso recursivo y la pila de llamadas. En particular, quiero que incluya un ejemplo de la pila de llamadas para `multiplicar(3, 4)`, mostrando cómo se apilan las llamadas `3 + multiplicar(3, 3)`, `3 + multiplicar(3, 2)`, `3 + multiplicar(3, 1)`, `3 + multiplicar(3, 0)`, y cómo luego se resuelven al desapilar hasta obtener 12.

Antes del código, quiero una explicación breve de la estrategia y de la complejidad temporal y espacial (incluyendo el costo de la pila de llamadas).

También quiero probarlo con estos casos, mostrando en un `main` el resultado esperado y el obtenido:
- `multiplicar(3, 4)` → 12 (caso general)
- `multiplicar(5, 0)` → 0 (caso base)
- `multiplicar(0, 5)` → 0 (primer factor en cero)
- `multiplicar(1, 7)` → 7 (factor 1)
- `multiplicar(6, 1)` → 6 (b igual a 1)
- `multiplicar(-3, 4)` → -12 (primer factor negativo)
- `multiplicar(3, -4)` → -12 (segundo factor negativo)
- `multiplicar(-3, -4)` → 12 (ambos negativos)
 */
public class ej3 {

	/**
	 * Devuelve el producto de dos enteros usando sumas recursivas.
	 *
	 * @param a valor que se suma repetidamente
	 * @param b cantidad de veces que se suma a, con signo
	 * @return producto de a y b
	 * @throws ArithmeticException si el resultado no cabe en un int
	 */
	public static int multiplicar(int a, int b) {
		long cantidad = b;
		long sumando = a;

		if (cantidad < 0) {
			cantidad = -cantidad;
			sumando = -sumando;
		}

		return Math.toIntExact(sumarRecursivamente(sumando, cantidad));
	}

	/**
	 * Suma el mismo valor tantas veces como indica la cantidad restante.
	 *
	 * @param sumando valor que se agrega en cada llamada
	 * @param cantidad cantidad de sumas pendientes, no negativa
	 * @return resultado acumulado de las sumas
	 */
	private static long sumarRecursivamente(long sumando, long cantidad) {
		// Caso base: con cero sumas pendientes, el aporte restante es cero.
		// Esta llamada detiene la recursión y permite resolver las anteriores.
		if (cantidad == 0) {
			return 0;
		}

		// Caso recursivo: se agrega un sumando y se reduce la cantidad en uno.
		// Para multiplicar(3, 4), la pila acumula estas llamadas:
		// 3 + sumarRecursivamente(3, 3)
		// 3 + sumarRecursivamente(3, 2)
		// 3 + sumarRecursivamente(3, 1)
		// 3 + sumarRecursivamente(3, 0)
		// Al llegar al caso base, se desapilan y resuelven así:
		// 3 + 0 = 3, luego 3 + 3 = 6, luego 3 + 6 = 9,
		// y finalmente 3 + 9 = 12.
		return sumando + sumarRecursivamente(sumando, cantidad - 1);
	}

	/**
	 * Ejecuta los casos de prueba y muestra el valor esperado y el obtenido.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		probarCaso(3, 4, 12);
		probarCaso(5, 0, 0);
		probarCaso(0, 5, 0);
		probarCaso(1, 7, 7);
		probarCaso(6, 1, 6);
		probarCaso(-3, 4, -12);
		probarCaso(3, -4, -12);
		probarCaso(-3, -4, 12);
	}

	/**
	 * Compara el resultado del método con el esperado para un caso de prueba.
	 *
	 * @param a primer factor
	 * @param b segundo factor
	 * @param esperado resultado esperado
	 */
	private static void probarCaso(int a, int b, int esperado) {
		int obtenido = multiplicar(a, b);
		System.out.println("multiplicar(" + a + ", " + b + ")"
				+ " | esperado: " + esperado
				+ " | obtenido: " + obtenido
				+ " | correcto: " + (esperado == obtenido));
	}
}
