package controller;

import dao.AutorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import model.Autor;

/**
 * Servlet para manipular operações CRUD de Autores.
 */
@WebServlet(name = "AutorServlet", urlPatterns = {"/autores"})
public class AutorServlet extends HttpServlet {

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
                listarAutores(request, response);
                break;
            case "adicionar":
                adicionarAutor(request, response);
                break;
            case "editar":
                exibirFormularioEdicaoAutor(request, response);
                break;
            case "atualizar":
                atualizarAutor(request, response);
                break;
            case "excluir":
                excluirAutor(request, response);
                break;
            default:
                listarAutores(request, response);
        }
    }
    
    /**
     * Lista todos os autores e redireciona para a página de listagem.
     */
    private void listarAutores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AutorDAO autorDAO = new AutorDAO();
        request.setAttribute("autores", autorDAO.listarTodos());
        request.getRequestDispatcher("autores.jsp").forward(request, response);
    }
    
    /**
     * Adiciona um novo autor ao banco de dados.
     */
    private void adicionarAutor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        LocalDate dataNascimento = LocalDate.parse(request.getParameter("dataNascimento"));
        String biografia = request.getParameter("biografia");
        
        Autor autor = new Autor(nome, dataNascimento, biografia);
        AutorDAO autorDAO = new AutorDAO();
        
        if (autorDAO.inserir(autor)) {
            request.setAttribute("mensagemSucesso", "Autor adicionado com sucesso!");
        } else {
            request.setAttribute("mensagemErro", "Erro ao adicionar autor.");
        }
        
        listarAutores(request, response);
    }
    
    /**
     * Exibe o formulário para edição de um autor existente.
     */
    private void exibirFormularioEdicaoAutor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        AutorDAO autorDAO = new AutorDAO();
        Autor autor = autorDAO.buscarPorId(id);
        
        if (autor != null) {
            request.setAttribute("autor", autor);
            listarAutores(request, response);
        } else {
            request.setAttribute("mensagemErro", "Autor não encontrado.");
            listarAutores(request, response);
        }
    }
    
    /**
     * Atualiza os dados de um autor no banco de dados.
     */
    private void atualizarAutor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        LocalDate dataNascimento = LocalDate.parse(request.getParameter("dataNascimento"));
        String biografia = request.getParameter("biografia");
        
        Autor autor = new Autor(nome, dataNascimento, biografia);
        autor.setId(id);
        
        AutorDAO autorDAO = new AutorDAO();
        
        if (autorDAO.atualizar(autor)) {
            request.setAttribute("mensagemSucesso", "Autor atualizado com sucesso!");
        } else {
            request.setAttribute("mensagemErro", "Erro ao atualizar autor.");
        }
        
        listarAutores(request, response);
    }
    
    /**
     * Exclui um autor do banco de dados.
     */
    private void excluirAutor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        AutorDAO autorDAO = new AutorDAO();
        
        if (autorDAO.deletar(id)) {
            request.setAttribute("mensagemSucesso", "Autor excluído com sucesso!");
        } else {
            request.setAttribute("mensagemErro", "Erro ao excluir autor.");
        }
        
        listarAutores(request, response);
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