package ejercicios.guia1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* Implementá en Java dos soluciones para detectar duplicados en un int[]:
 * una con dos ciclos anidados y otra con HashSet. Antes del código, explicá
 * la estrategia de cada una, por qué es correcta y su complejidad temporal
 * y espacial. Reglas: cada duplicado se informa una sola vez, el vector no
 * se modifica, null lanza IllegalArgumentException. Incluí un main que pruebe
 * vacío, sin duplicados, un elemento, varios duplicados y todos iguales.
 * Comentá el código.   Al finalizar, incluí una comparación entre ambas implementaciones en
 * términos de complejidad temporal y espacial, indicando cuándo conviene cada una.
 */

/**
 * Comparación de complejidad:
 * <ul>
 *   <li>Ciclos anidados: tiempo O(n²). El espacio auxiliar es O(1), sin contar
 *       la lista devuelta, que puede ocupar O(k), donde k es la cantidad de
 *       duplicados distintos.</li>
 *   <li>HashSet: tiempo esperado O(n) y espacio O(n), porque almacena los
 *       elementos vistos y los duplicados.</li>
 * </ul>
 * Conviene usar ciclos anidados para vectores pequeños o cuando se quiere
 * evitar estructuras auxiliares. Conviene HashSet para vectores medianos o
 * grandes cuando importa reducir el tiempo de búsqueda y se dispone de memoria.
 */
public class ej4 {

    /**
     * Encuentra duplicados mediante comparaciones con ciclos anidados.
     * El arreglo de entrada no se modifica.
     *
     * @param vector arreglo de enteros que se desea analizar
     * @return lista sin repeticiones de los valores duplicados, en orden de aparición
     * @throws IllegalArgumentException si el arreglo es null
     */
    public static List<Integer> detectarDuplicadosAnidado(int[] vector) {
        validarVector(vector);
        List<Integer> duplicados = new ArrayList<>();

        for (int i = 0; i < vector.length; i++) {
            boolean primeraAparicion = true;

            for (int j = 0; j < i; j++) {
                if (vector[j] == vector[i]) {
                    primeraAparicion = false;
                    break;
                }
            }

            if (primeraAparicion) {
                for (int j = i + 1; j < vector.length; j++) {
                    if (vector[i] == vector[j]) {
                        duplicados.add(vector[i]);
                        break;
                    }
                }
            }
        }

        return duplicados;
    }

    /**
     * Encuentra duplicados registrando los valores vistos en conjuntos hash.
     * El arreglo de entrada no se modifica.
     *
     * @param vector arreglo de enteros que se desea analizar
     * @return lista sin repeticiones de los valores duplicados, en orden de detección
     * @throws IllegalArgumentException si el arreglo es null
     */
    public static List<Integer> detectarDuplicadosHashSet(int[] vector) {
        validarVector(vector);
        Set<Integer> vistos = new HashSet<>();
        Set<Integer> duplicados = new LinkedHashSet<>();

        for (int valor : vector) {
            if (!vistos.add(valor)) {
                duplicados.add(valor);
            }
        }

        return new ArrayList<>(duplicados);
    }

    /**
     * Rechaza el valor null para ambas estrategias.
     *
     * @param vector arreglo que se valida
     * @throws IllegalArgumentException si el arreglo es null
     */
    private static void validarVector(int[] vector) {
        if (vector == null) {
            throw new IllegalArgumentException("El vector no puede ser null.");
        }
    }

    /**
     * Ejecuta las dos estrategias para un caso y muestra los resultados.
     *
     * @param nombre descripción del caso de prueba
     * @param vector arreglo que se analizará
     */
    private static void probarCaso(String nombre, int[] vector) {
        List<Integer> duplicadosAnidados = detectarDuplicadosAnidado(vector);
        List<Integer> duplicadosHashSet = detectarDuplicadosHashSet(vector);

        System.out.println(nombre + " " + Arrays.toString(vector));
        System.out.println("  Dos ciclos anidados: " + duplicadosAnidados);
        System.out.println("  HashSet: " + duplicadosHashSet);
        System.out.println("  Resultados iguales: " + duplicadosAnidados.equals(duplicadosHashSet));
    }

    /**
     * Prueba vectores vacíos, sin duplicados, unitarios, repetidos e iguales.
     *
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        probarCaso("Vector vacío:", new int[]{});
        probarCaso("Sin duplicados:", new int[]{1, 2, 3, 4});
        probarCaso("Un elemento:", new int[]{7});
        probarCaso("Varios duplicados:", new int[]{4, 2, 4, 3, 2, 4, 5});
        probarCaso("Todos iguales:", new int[]{9, 9, 9, 9});

        try {
            detectarDuplicadosAnidado(null);
        } catch (IllegalArgumentException excepcion) {
            System.out.println("Vector null con ciclos anidados: " + excepcion.getMessage());
        }

        try {
            detectarDuplicadosHashSet(null);
        } catch (IllegalArgumentException excepcion) {
            System.out.println("Vector null con HashSet: " + excepcion.getMessage());
        }

        System.out.println("\nComparación de complejidad:");
        System.out.println("Dos ciclos anidados: tiempo O(n²), espacio auxiliar O(1) "
                + "más O(k) para la lista de duplicados.");
        System.out.println("HashSet: tiempo esperado O(n), espacio O(n).");
        System.out.println("Usá ciclos anidados para vectores pequeños o si querés evitar "
                + "estructuras auxiliares; HashSet para vectores grandes cuando priorizás "
                + "el tiempo y disponés de memoria.");
    }
}