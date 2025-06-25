package controller;

import dao.EmprestimoDAO;
import dao.LivroDAO;
import dao.PessoaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import model.Administrador;
import model.Emprestimo;
import model.Livro;
import model.Pessoa;

/**
 * Servlet para manipular operações CRUD de Empréstimos.
 */
@WebServlet(name = "EmprestimoServlet", urlPatterns = {"/emprestimos"})
public class EmprestimoServlet extends HttpServlet {

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
                listarEmprestimos(request, response);
                break;
         
            case "adicionar":
                adicionarEmprestimo(request, response);
                break;
            case "editar":
                exibirFormularioEdicaoEmprestimo(request, response);
                break;
            case "atualizar":
                atualizarEmprestimo(request, response);
                break;
            case "excluir":
                excluirEmprestimo(request, response);
                break;
            case "devolver":
                devolverEmprestimo(request, response);
                break;
            default:
                listarEmprestimos(request, response);
        }
    }
    
    /**
     * Lista todos os empréstimos e redireciona para a página de listagem.
     */
    private void listarEmprestimos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         LivroDAO livroDAO = new LivroDAO();
        PessoaDAO pessoaDAO = new PessoaDAO();
        
        request.setAttribute("livrosDisponiveis", livroDAO.listarPorStatus("Disponível"));
        request.setAttribute("pessoas", pessoaDAO.listarTodos());
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        request.setAttribute("emprestimos", emprestimoDAO.listarTodos());
        request.getRequestDispatcher("emprestimos.jsp").forward(request, response);
    }
    

    
    /**
     * Adiciona um novo empréstimo ao banco de dados.
     */
    private void adicionarEmprestimo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int livroIsbn = Integer.parseInt(request.getParameter("livro"));
        int pessoaId = Integer.parseInt(request.getParameter("pessoa"));
        LocalDate dataPrevistaDev = LocalDate.parse(request.getParameter("dataPrevistaDev"));
        
        Emprestimo emprestimo = new Emprestimo();
        
        Livro livro = new Livro();
        livro.setIsbn(livroIsbn);
        emprestimo.setLivro(livro);
        
        Pessoa pessoa = new Pessoa();
        pessoa.setId(pessoaId);
        emprestimo.setPessoa(pessoa);
        
        // TODO: Pegar administrador logado da sessão
        Administrador admin = new Administrador();
        admin.setId(1); // Temporário - substituir por admin logado
        emprestimo.setAdministrador(admin);
        
        emprestimo.setDataPrevistaDev(dataPrevistaDev);
        emprestimo.setStatus("Ativo");
        
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        
        if (emprestimoDAO.inserir(emprestimo)) {
            // Atualiza status do livro para "Emprestado"
            LivroDAO livroDAO = new LivroDAO();
            Livro livroAtualizado = livroDAO.buscarPorIsbn(livroIsbn);
            livroAtualizado.setStatus("Emprestado");
            livroDAO.atualizar(livroAtualizado);
            
            request.setAttribute("mensagemSucesso", "Empréstimo registrado com sucesso!");
        } else {
            request.setAttribute("mensagemErro", "Erro ao registrar empréstimo.");
        }
        
        listarEmprestimos(request, response);
    }
    
    /**
     * Exibe o formulário para edição de um empréstimo existente.
     */
    private void exibirFormularioEdicaoEmprestimo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        Emprestimo emprestimo = emprestimoDAO.buscarPorId(id);
        
        if (emprestimo != null) {
            request.setAttribute("emprestimo", emprestimo);
             listarEmprestimos(request, response);
        } else {
            request.setAttribute("mensagemErro", "Empréstimo não encontrado.");
            listarEmprestimos(request, response);
        }
    }
    
    /**
     * Atualiza os dados de um empréstimo no banco de dados.
     */
    private void atualizarEmprestimo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        LocalDate dataPrevistaDev = LocalDate.parse(request.getParameter("dataPrevistaDev"));
        
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        Emprestimo emprestimo = emprestimoDAO.buscarPorId(id);
        
        if (emprestimo != null) {
            emprestimo.setDataPrevistaDev(dataPrevistaDev);
            
            if (emprestimoDAO.atualizar(emprestimo)) {
                request.setAttribute("mensagemSucesso", "Empréstimo atualizado com sucesso!");
            } else {
                request.setAttribute("mensagemErro", "Erro ao atualizar empréstimo.");
            }
        } else {
            request.setAttribute("mensagemErro", "Empréstimo não encontrado.");
        }
        
        listarEmprestimos(request, response);
    }
    
    /**
     * Registra a devolução de um livro emprestado.
     */
    private void devolverEmprestimo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        Emprestimo emprestimo = emprestimoDAO.buscarPorId(id);
        
        if (emprestimo != null) {
            emprestimo.setDataDevolucao(LocalDate.now());
            emprestimo.setStatus("Devolvido");
            
            if (emprestimoDAO.atualizar(emprestimo)) {
                // Atualiza status do livro para "Disponível"
                LivroDAO livroDAO = new LivroDAO();
                Livro livro = livroDAO.buscarPorIsbn(emprestimo.getLivro().getIsbn());
                livro.setStatus("Disponível");
                livroDAO.atualizar(livro);
                
                request.setAttribute("mensagemSucesso", "Devolução registrada com sucesso!");
            } else {
                request.setAttribute("mensagemErro", "Erro ao registrar devolução.");
            }
        } else {
            request.setAttribute("mensagemErro", "Empréstimo não encontrado.");
        }
        
        listarEmprestimos(request, response);
    }
    
    /**
     * Exclui um empréstimo do banco de dados.
     */
    private void excluirEmprestimo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        
        if (emprestimoDAO.deletar(id)) {
            request.setAttribute("mensagemSucesso", "Empréstimo excluído com sucesso!");
        } else {
            request.setAttribute("mensagemErro", "Erro ao excluir empréstimo.");
        }
        
        listarEmprestimos(request, response);
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