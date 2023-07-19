package pe.edu.utp.tp.Utilidades;


// Autor del modulo: Leandro

// clase utilitaria para almacenar SOLAMENTE dos valores de cualquier tipo en una sola variable.
public class Tupla<M, N> {
    public M primero;
    public N segundo;

    public Tupla(M primero, N segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }
}
