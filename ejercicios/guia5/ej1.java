/*Necesito una lista enlazada simple de enteros en Java hecha desde cero, sin usar ArrayList, LinkedList ni ninguna estructura de Java.

Un nodo se representa con una clase Nodo que tiene dos atributos: dato (int) y siguiente (Nodo), que es la referencia al nodo que le sigue. El último nodo tiene siguiente en null.

La clase ListaEnlazada tiene un atributo head que apunta al primer nodo (null si la lista está vacía) y un atributo size que arranca en 0. No uso referencia al último nodo, para recorrer la lista con un auxiliar.

Métodos: insertarAlInicio, insertarAlFinal, imprimir, estaVacia y getSize.

En insertarAlInicio, el nodo nuevo apunta al head actual y después head pasa a ser el nodo nuevo (en ese orden, para no perder la lista). En insertarAlFinal, si la lista está vacía el nodo nuevo es el head; si no, recorro con un auxiliar hasta el último nodo y su siguiente apunta al nuevo. En ambos aumento size en 1. imprimir recorre con un auxiliar sin mover head y muestra 10 -> 20 -> null, o "Lista vacía". getSize devuelve size sin recorrer.

Comentá el código en español. En el main probá la lista vacía, insertar al inicio 30, 20, 10 e insertar al final 40 y 50, imprimiendo después de cada paso. Al final tiene que quedar 10 -> 20 -> 30 -> 40 -> 50 -> null con size 5.


Ajustes realizados luego de la primera respuesta de OpenCode:
...
*/ 
package ejercicios.guia5;

public class ej1 {
    public static void main(String[] args) {
        ListaEnlazada lista = new ListaEnlazada();

        System.out.println("Lista vacía:");
        lista.imprimir();
        System.out.println("Tamaño: " + lista.getSize());

        int[] valoresInicio = {30, 20, 10};
        for (int valor : valoresInicio) {
            lista.insertarAlInicio(valor);
            System.out.print("Después de insertar al inicio " + valor + ": ");
            lista.imprimir();
        }

        int[] valoresFinal = {40, 50};
        for (int valor : valoresFinal) {
            lista.insertarAlFinal(valor);
            System.out.print("Después de insertar al final " + valor + ": ");
            lista.imprimir();
        }

        System.out.println("Tamaño final: " + lista.getSize());
    }
}

/** Nodo de una lista enlazada simple: almacena un entero y referencia al siguiente nodo. */
class Nodo {
    int dato;
    Nodo siguiente;

    Nodo(int dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}

/** Lista enlazada simple de enteros, sin referencia al último nodo. */
class ListaEnlazada {
    private Nodo head;
    private int size;

    ListaEnlazada() {
        head = null;
        size = 0;
    }

    public void insertarAlInicio(int dato) {
        Nodo nuevo = new Nodo(dato);
        nuevo.siguiente = head;
        head = nuevo;
        size++;
    }

    public void insertarAlFinal(int dato) {
        Nodo nuevo = new Nodo(dato);
        if (head == null) {
            head = nuevo;
        } else {
            Nodo auxiliar = head;
            while (auxiliar.siguiente != null) {
                auxiliar = auxiliar.siguiente;
            }
            auxiliar.siguiente = nuevo;
        }
        size++;
    }

    public void imprimir() {
        if (estaVacia()) {
            System.out.println("Lista vacía");
            return;
        }

        Nodo auxiliar = head;
        while (auxiliar != null) {
            System.out.print(auxiliar.dato + " -> ");
            auxiliar = auxiliar.siguiente;
        }
        System.out.println("null");
    }

    public boolean estaVacia() {
        return head == null;
    }

    public int getSize() {
        return size;
    }
}
