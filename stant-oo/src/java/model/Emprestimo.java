package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Classe que representa um empréstimo de livro.
 * Contém informações sobre o livro, pessoa, administrador, datas e status.
 */
public class Emprestimo {
    private int id;
    private Livro livro;
    private Pessoa pessoa;
    private Administrador administrador;
    private LocalDateTime dataEmprestimo;
    private LocalDate dataPrevistaDev;
    private LocalDate dataDevolucao;
    private String status;

    public Emprestimo() {
    }

    public Emprestimo(Livro livro, Pessoa pessoa, Administrador administrador, LocalDate dataPrevistaDev) {
        this.livro = livro;
        this.pessoa = pessoa;
        this.administrador = administrador;
        this.dataPrevistaDev = dataPrevistaDev;
    }

    /* Getters e Setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public LocalDateTime getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDateTime dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataPrevistaDev() {
        return dataPrevistaDev;
    }

    public void setDataPrevistaDev(LocalDate dataPrevistaDev) {
        this.dataPrevistaDev = dataPrevistaDev;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}