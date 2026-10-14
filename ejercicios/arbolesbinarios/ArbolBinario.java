package ejercicios.arbolesbinarios;

/** Árbol binario que conserva una referencia a su nodo raíz. */
public class ArbolBinario {
    private Nodo raiz;

    public ArbolBinario() {
        this(null);
    }

    public ArbolBinario(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }
}
