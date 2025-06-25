package model;

import java.time.LocalDate;

/**
 * Classe que representa um autor de livros.
 * Contém informações como nome, data de nascimento e biografia.
 */
public class Autor {
    private int id;
    private String nome;
    private LocalDate dataNascimento;
    private String biografia;

    public Autor() {
    }

    public Autor(String nome, LocalDate dataNascimento, String biografia) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.biografia = biografia;
    }

    /* Getters e Setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }
}