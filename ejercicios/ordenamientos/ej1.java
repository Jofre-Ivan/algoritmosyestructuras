package ejercicios.guia3;

import java.util.Arrays;

/**
 * Bubble Sort compara elementos adyacentes e intercambia los que están invertidos.
 * Su complejidad es O(n^2) en el peor caso y O(n) en el mejor, con salida anticipada.
 */
class BubbleSort {
    /** Ordena el arreglo ascendentemente e imprime la evolución de cada pasada. */
    static void bubbleSort(int[] arr) {
        System.out.println("Arreglo original: " + Arrays.toString(arr));
        int totalPasadas = 0;
        int totalIntercambios = 0;

        // Cada iteración del bucle externo representa una pasada.
        for (int i = 0; i < arr.length - 1; i++) {
            boolean huboIntercambios = false;
            int intercambiosEnPasada = 0;

            // Compara elementos adyacentes; el límite se reduce porque los mayores
            // ya quedaron fijados al final en las pasadas anteriores.
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // La variable auxiliar conserva un valor mientras se intercambian.
                    int auxiliar = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = auxiliar;
                    huboIntercambios = true;
                    intercambiosEnPasada++;
                }
            }

            totalPasadas++;
            totalIntercambios += intercambiosEnPasada;
            int indiceFijado = arr.length - 1 - i;
            System.out.println("Pasada " + totalPasadas + ": " + Arrays.toString(arr)
                    + "  (el " + arr[indiceFijado] + " quedó en su posición final, "
                    + intercambiosEnPasada + (intercambiosEnPasada == 1
                            ? " intercambio)" : " intercambios)"));

            // El mayor no ordenado avanza al final al intercambiarse con cada vecino.
            if (!huboIntercambios) {
                System.out.println("No hubo intercambios: el arreglo ya está ordenado.");
                break;
            }
        }

        System.out.println("Arreglo ordenado: " + Arrays.toString(arr));
        System.out.println("Total de pasadas: " + totalPasadas);
        System.out.println("Total de intercambios: " + totalIntercambios);
    }

    /** Devuelve true si cada elemento es menor o igual que el siguiente. */
    static boolean estaEnOrden(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] nombres = {
                "Desordenado", "Con repetidos", "Con negativos", "Ya ordenado", "Un elemento"
        };
        int[][] casos = {
                {64, 34, 25, 12, 22, 11, 90},
                {5, 1, 4, 2, 1},
                {3, -2, 0, -7, 4},
                {1, 2, 3, 4, 5},
                {8}
        };

        for (int i = 0; i < casos.length; i++) {
            System.out.println("\n=== Caso: " + nombres[i] + " ===");
            int[] arreglo = Arrays.copyOf(casos[i], casos[i].length);
            int[] referencia = Arrays.copyOf(casos[i], casos[i].length);

            bubbleSort(arreglo);
            System.out.println("Verificación: " + (estaEnOrden(arreglo) ? "CORRECTA" : "INCORRECTA"));

            // Arrays.sort se usa solo para comprobar el resultado, nunca para ordenar el arreglo probado.
            Arrays.sort(referencia);
            System.out.println("Coincide con Arrays.sort: " + Arrays.equals(arreglo, referencia));
        }
    }
}
