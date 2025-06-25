package controller;

import dao.AutorDAO;
import dao.LivroDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Administrador;
import model.Autor;
import model.Livro;

/**
 * Servlet para manipular operações CRUD de Livros.
 */
@WebServlet(name = "LivroServlet", urlPatterns = {"/livros"})
public class LivroServlet extends HttpServlet {

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
                listarLivros(request, response);
                break;

            case "adicionar":
                adicionarLivro(request, response);
                break;
            case "editar":
                exibirFormularioEdicaoLivro(request, response);
                break;
            case "atualizar":
                atualizarLivro(request, response);
                break;
            case "excluir":
                excluirLivro(request, response);
                break;
            default:
                listarLivros(request, response);
        }
    }

    /**
     * Lista todos os livros e redireciona para a página de listagem.
     */
    private void listarLivros(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        AutorDAO autorDAO = new AutorDAO();
        request.setAttribute("autores", autorDAO.listarTodos());
        
        LivroDAO livroDAO = new LivroDAO();
        request.setAttribute("livros", livroDAO.listarTodos());
        request.getRequestDispatcher("livros.jsp").forward(request, response);
    }

    /**
     * Adiciona um novo livro ao banco de dados.
     */
    private void adicionarLivro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int isbn = Integer.parseInt(request.getParameter("isbn"));
        String titulo = request.getParameter("titulo");
        int autorId = Integer.parseInt(request.getParameter("autor"));
        String genero = request.getParameter("genero");
        int edicao = Integer.parseInt(request.getParameter("edicao"));
        String status = request.getParameter("status");
        int nota = Integer.parseInt(request.getParameter("nota"));

        Livro livro = new Livro();
        livro.setIsbn(isbn);
        livro.setTitulo(titulo);

        Autor autor = new Autor();
        autor.setId(autorId);
        livro.setAutor(autor);

        livro.setGenero(genero);
        livro.setEdicao(edicao);
        livro.setStatus(status);
        livro.setNota(nota);

        // TODO: Pegar administrador logado da sessão
        Administrador admin = new Administrador();
        admin.setId(1); // Temporário - substituir por admin logado
        livro.setAdministrador(admin);

        LivroDAO livroDAO = new LivroDAO();

        if (livroDAO.inserir(livro)) {
            request.setAttribute("mensagemSucesso", "Livro adicionado com sucesso!");
        } else {
            request.setAttribute("mensagemErro", "Erro ao adicionar livro.");
        }

        listarLivros(request, response);
    }

    /**
     * Exibe o formulário para edição de um livro existente.
     */
    private void exibirFormularioEdicaoLivro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int isbn = Integer.parseInt(request.getParameter("isbn"));

        LivroDAO livroDAO = new LivroDAO();
        Livro livro = livroDAO.buscarPorIsbn(isbn);

        if (livro != null) {
            AutorDAO autorDAO = new AutorDAO();
            request.setAttribute("autores", autorDAO.listarTodos());
            request.setAttribute("livro", livro);
            listarLivros(request, response);
        } else {
            request.setAttribute("mensagemErro", "Livro não encontrado.");
            listarLivros(request, response);
        }
    }

    /**
     * Atualiza os dados de um livro no banco de dados.
     */
    private void atualizarLivro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int isbn = Integer.parseInt(request.getParameter("isbn"));
        String titulo = request.getParameter("titulo");
        int autorId = Integer.parseInt(request.getParameter("autor"));
        String genero = request.getParameter("genero");
        int edicao = Integer.parseInt(request.getParameter("edicao"));
        String status = request.getParameter("status");
        int nota = Integer.parseInt(request.getParameter("nota"));

        Livro livro = new Livro();
        livro.setIsbn(isbn);
        livro.setTitulo(titulo);

        Autor autor = new Autor();
        autor.setId(autorId);
        livro.setAutor(autor);

        livro.setGenero(genero);
        livro.setEdicao(edicao);
        livro.setStatus(status);
        livro.setNota(nota);

        LivroDAO livroDAO = new LivroDAO();

        if (livroDAO.atualizar(livro)) {
            request.setAttribute("mensagemSucesso", "Livro atualizado com sucesso!");
        } else {
            request.setAttribute("mensagemErro", "Erro ao atualizar livro.");
        }

        listarLivros(request, response);
    }

    /**
     * Exclui um livro do banco de dados.
     */
    private void excluirLivro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int isbn = Integer.parseInt(request.getParameter("isbn"));

        LivroDAO livroDAO = new LivroDAO();

        if (livroDAO.deletar(isbn)) {
            request.setAttribute("mensagemSucesso", "Livro excluído com sucesso!");
        } else {
            request.setAttribute("mensagemErro", "Erro ao excluir livro.");
        }

        listarLivros(request, response);
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
