/*Se requiere una funcionalidad que calcule
 el factorial de un número entero positivo
  mediante un enfoque recursivo. 
  La lógica debe descomponer el problema en llamadas más pequeñas hasta llegar al caso base, 
  evitando duplicaciones y manteniendo resultados correctos para valores esperados del dominio del ejercicio 
  Como usuario, quiero ingresar un número entero, para obtener el valor de su factorial.
Como usuario, quiero que el cálculo sea realizado de forma recursiva, para comprender el uso de llamadas anidadas.
Como usuario, quiero que la solución maneje valores no válidos, para evitar errores de cálculo o entradas inconsistentes.
El sistema debe permitir calcular el factorial de un número entero.
La solución debe utilizar un enfoque recursivo.
El caso base debe definirse correctamente para detener la recursión.
El cálculo debe considerar que el factorial de 0 es 1.
El cálculo debe considerar que el factorial de 1 es 1.
Si el número ingresado es negativo, el sistema debe rechazar la operación o indicar un mensaje de error.
Si el número ingresado es decimal o no entero, el sistema debe rechazar la operación.
El resultado debe mostrarse en el formato esperado por el ejercicio, por ejemplo, como valor numérico entero.
La lógica debe ser reutilizable dentro de la clase o método del ejercicio.
La solución debe ser comprensible y mantener la composición recursiva del problema.*/

package ejercicios.guia2;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Clase que implementa un ejemplo de recursividad para calcular
 * el factorial de un número entero no negativo.
 */
public class ej1 {

    /**
     * Calcula el factorial de un número entero mediante recursión.
     * La solución reduce el problema con la expresión n * factorial(n - 1)
     * hasta llegar al caso base n == 0 o n == 1.
     *
     * @param n número entero no negativo del cual se desea calcular el factorial
     * @return el factorial de {@code n}
     * @throws IllegalArgumentException si {@code n} es negativo
     */
    public static BigInteger factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("El factorial no está definido para números negativos");
        }
        if (n == 0 || n == 1) {
            return BigInteger.ONE; // caso base
        }
        return BigInteger.valueOf(n).multiply(factorial(n - 1));
    }

    /**
     * Lee un número desde la consola y muestra su factorial.
     *
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese un número: ");
        int n = scanner.nextInt();

        try {
            BigInteger resultado = factorial(n);
            System.out.println("El factorial de " + n + " es: " + resultado);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}
