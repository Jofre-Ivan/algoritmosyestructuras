/*Necesito una clase PilaEnteros en Java que implemente una pila de enteros con un arreglo de tamaño fijo. La capacidad se pasa por el constructor, y si es menor o igual a 0 tiene que tirar IllegalArgumentException. No quiero usar ArrayList ni la clase Stack, solo un int[].

Tiene que tener los métodos push, pop, peek, isEmpty, isFull y size.

Para controlar la pila uso una variable top que guarda la posición del último elemento apilado. Arranca en -1, que significa pila vacía. Con push, primero incremento top y después guardo el valor en arreglo[top]. Con pop, devuelvo arreglo[top] y después decremento top, sin borrar el valor del arreglo porque se va a pisar en el próximo push. peek devuelve arreglo[top] sin modificar top. size devuelve top + 1. isEmpty es top == -1 e isFull es top == capacidad - 1.

Casos límite: si hago push con la pila llena o pop/peek con la pila vacía, tiene que lanzar una excepción con un mensaje claro (por ejemplo "Pila llena" o "Pila vacía"), no devolver un valor inventado como -1, porque -1 también puede ser un dato válido.

Comentá el código  explicando qué hace top y por qué empieza en -1. Agregá un main que pruebe: apilar hasta llenar, intentar un push de más, desapilar todo y probar un pop con la pila vacía, mostrando el resultado de cada paso. */
package ejercicios.guia4;

/** Pila LIFO de enteros con capacidad fija, implementada exclusivamente con int[]. */
class PilaEnteros {
    private final int[] arreglo;
    private final int capacidad;
    // top señala el último elemento apilado; empieza en -1 porque aún no hay ninguno.
    private int top;

    public PilaEnteros(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        }
        this.capacidad = capacidad;
        this.arreglo = new int[capacidad];
        this.top = -1;
    }

    public void push(int valor) {
        if (isFull()) {
            throw new IllegalStateException("Pila llena");
        }
        top++;
        arreglo[top] = valor;
    }

    public int pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Pila vacía");
        }
        int valor = arreglo[top];
        top--;
        return valor;
    }

    public int peek() {
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

    public static void main(String[] args) {
        PilaEnteros pila = new PilaEnteros(3);
        System.out.println("Pila creada con capacidad " + 3 + ". Vacía: " + pila.isEmpty());

        for (int valor : new int[]{10, -1, 30}) {
            pila.push(valor);
            System.out.println("push(" + valor + ") -> tamaño: " + pila.size()
                    + ", cima: " + pila.peek());
        }
        System.out.println("¿Está llena?: " + pila.isFull());

        try {
            pila.push(40);
        } catch (IllegalStateException exception) {
            System.out.println("Intento de push extra: " + exception.getMessage());
        }

        while (!pila.isEmpty()) {
            System.out.println("pop() -> " + pila.pop() + ", tamaño restante: " + pila.size());
        }

        try {
            pila.pop();
        } catch (IllegalStateException exception) {
            System.out.println("Intento de pop con pila vacía: " + exception.getMessage());
        }

        try {
            pila.peek();
        } catch (IllegalStateException exception) {
            System.out.println("Intento de peek con pila vacía: " + exception.getMessage());
        }
    }
}
