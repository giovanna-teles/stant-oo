package controller;

import dao.PessoaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Pessoa;

/**
 * Servlet para manipular operações CRUD de Pessoas.
 */
@WebServlet(name = "PessoaServlet", urlPatterns = {"/pessoas"})
public class PessoaServlet extends HttpServlet {

    /**
     * Processa requisições HTTP GET e POST.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "listar";
        }
        
        switch (action) {
            case "listar":
                listarPessoas(request, response);
                break;
        
            case "adicionar":
                adicionarPessoa(request, response);
                break;
            case "editar":
                exibirFormularioEdicaoPessoa(request, response);
                break;
            case "atualizar":
                atualizarPessoa(request, response);
                break;
            case "excluir":
                excluirPessoa(request, response);
                break;
            default:
                listarPessoas(request, response);
        }
    }
    
    /**
     * Lista todas as pessoas e redireciona para a página de listagem.
     */
    private void listarPessoas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PessoaDAO pessoaDAO = new PessoaDAO();
        request.setAttribute("pessoas", pessoaDAO.listarTodos());
        request.getRequestDispatcher("pessoas.jsp").forward(request, response);
    }
    
   
    
    /**
     * Adiciona uma nova pessoa ao banco de dados.
     */
    private void adicionarPessoa(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        
        Pessoa pessoa = new Pessoa(nome, email, telefone);
        PessoaDAO pessoaDAO = new PessoaDAO();
        
        if (pessoaDAO.inserir(pessoa)) {
            request.setAttribute("mensagemSucesso", "Pessoa adicionada com sucesso!");
        } else {
            request.setAttribute("mensagemErro", "Erro ao adicionar pessoa.");
        }
        
        listarPessoas(request, response);
    }
    
    /**
     * Exibe o formulário para edição de uma pessoa existente.
     */
    private void exibirFormularioEdicaoPessoa(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        PessoaDAO pessoaDAO = new PessoaDAO();
        Pessoa pessoa = pessoaDAO.buscarPorId(id);
        
        if (pessoa != null) {
            request.setAttribute("pessoa", pessoa);
            listarPessoas(request, response);
        } else {
            request.setAttribute("mensagemErro", "Pessoa não encontrada.");
            listarPessoas(request, response);
        }
    }
    
    /**
     * Atualiza os dados de uma pessoa no banco de dados.
     */
    private void atualizarPessoa(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        
        Pessoa pessoa = new Pessoa(nome, email, telefone);
        pessoa.setId(id);
        
        PessoaDAO pessoaDAO = new PessoaDAO();
        
        if (pessoaDAO.atualizar(pessoa)) {
            request.setAttribute("mensagemSucesso", "Pessoa atualizada com sucesso!");
        } else {
            request.setAttribute("mensagemErro", "Erro ao atualizar pessoa.");
        }
        
        listarPessoas(request, response);
    }
    
    /**
     * Exclui uma pessoa do banco de dados.
     */
    private void excluirPessoa(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        PessoaDAO pessoaDAO = new PessoaDAO();
        
        if (pessoaDAO.deletar(id)) {
            request.setAttribute("mensagemSucesso", "Pessoa excluída com sucesso!");
        } else {
            request.setAttribute("mensagemErro", "Erro ao excluir pessoa.");
        }
        
        listarPessoas(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}