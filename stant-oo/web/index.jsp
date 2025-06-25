<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page import="dao.PessoaDAO"%>
<%@page import="dao.LivroDAO"%>
<%@page import="dao.AutorDAO"%>
<%@page import="dao.EmprestimoDAO"%>
<%@page import="model.Emprestimo"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard - Stant</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="assets/styles.css">
    </head>
    <body>        
        <div class="app-container">
            <div class="sidebar">
                <div class="sidebar-header">
                    <h1>Dashboard - Stant</h1>
                </div>
                <div class="sidebar-menu">
                    <a href="index.jsp" class="menu-item active">
                        <i class="fas fa-tachometer-alt"></i>
                        <span>Dashboard</span>
                    </a>
                    <a href="livros" class="menu-item">
                        <i class="fas fa-book"></i>
                        <span>Livros</span>
                    </a>
                    <a href="autores" class="menu-item">
                        <i class="fas fa-user-edit"></i>
                        <span>Autores</span>
                    </a>
                    <a href="emprestimos" class="menu-item">
                        <i class="fas fa-exchange-alt"></i>
                        <span>Empréstimos</span>
                    </a>
                    <a href="pessoas" class="menu-item">
                        <i class="fas fa-users"></i>
                        <span>Pessoas</span>
                    </a>
                </div>
            </div>

            <div class="main-content">
                <div class="content-wrapper">
                    <%
                        PessoaDAO pessoaDAO = new PessoaDAO();
                        LivroDAO livroDAO = new LivroDAO();
                        AutorDAO autorDAO = new AutorDAO();
                        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
                        
                        int totalPessoas = pessoaDAO.listarTodos().size();
                        int totalLivros = livroDAO.listarTodos().size();
                        int totalAutores = autorDAO.listarTodos().size();
                        int totalEmprestimos = emprestimoDAO.listarTodos().size();
                        List<Emprestimo> emprestimos = emprestimoDAO.listarTodos();
                        List<Emprestimo> emprestimosRecentes = emprestimoDAO.listarTodos().subList(Math.max(emprestimos.size() - 3, 0), emprestimos.size());
                        
                        pageContext.setAttribute("totalPessoas", totalPessoas);
                        pageContext.setAttribute("totalLivros", totalLivros);
                        pageContext.setAttribute("totalAutores", totalAutores);
                        pageContext.setAttribute("totalEmprestimos", totalEmprestimos);
                        pageContext.setAttribute("emprestimosRecentes", emprestimosRecentes);
                    %>
                    
                    <!-- Cards Informativos -->
                    <div class="cards-container">
                        <!-- Card de Empréstimos -->
                        <div class="info-card">
                            <div class="card-icon bg-primary">
                                <i class="fas fa-exchange-alt"></i>
                            </div>
                            <div class="card-content">
                                <h3>Empréstimos</h3>
                                <p class="count">${totalEmprestimos}</p>
                                <p class="description">Total de empréstimos ativos</p>
                            </div>
                            <a href="emprestimos" class="card-link">Ver todos <i class="fas fa-arrow-right"></i></a>
                        </div>

                        <!-- Card de Livros -->
                        <div class="info-card">
                            <div class="card-icon bg-success">
                                <i class="fas fa-book"></i>
                            </div>
                            <div class="card-content">
                                <h3>Livros</h3>
                                <p class="count">${totalLivros}</p>
                                <p class="description">Livros cadastrados</p>
                            </div>
                            <a href="livros" class="card-link">Ver todos <i class="fas fa-arrow-right"></i></a>
                        </div>

                        <!-- Card de Pessoas -->
                        <div class="info-card">
                            <div class="card-icon bg-warning">
                                <i class="fas fa-users"></i>
                            </div>
                            <div class="card-content">
                                <h3>Pessoas</h3>
                                <p class="count">${totalPessoas}</p>
                                <p class="description">Pessoas cadastradas</p>
                            </div>
                            <a href="pessoas" class="card-link">Ver todos <i class="fas fa-arrow-right"></i></a>
                        </div>

                        <!-- Card de Autores -->
                        <div class="info-card">
                            <div class="card-icon bg-info">
                                <i class="fas fa-user-edit"></i>
                            </div>
                            <div class="card-content">
                                <h3>Autores</h3>
                                <p class="count">${totalAutores}</p>
                                <p class="description">Autores cadastrados</p>
                            </div>
                            <a href="autores" class="card-link">Ver todos <i class="fas fa-arrow-right"></i></a>
                        </div>
                    </div>

                    <!-- Seção de Empréstimos Recentes -->
                    <div class="card">
                        <div class="card-header">
                            <h2><i class="fas fa-history"></i> Empréstimos Recentes</h2>
                            <a href="emprestimos" class="btn btn-sm btn-primary">Ver todos</a>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="data-table">
                                    <thead>
                                        <tr>
                                            <th>Livro</th>
                                            <th>Pessoa</th>
                                            <th>Data Empréstimo</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${emprestimosRecentes}" var="emprestimo">
                                            <tr>
                                                <td>${emprestimo.livro.titulo}</td>
                                                <td>${emprestimo.pessoa.nome}</td>
                                                <td>${emprestimo.dataEmprestimo}</td>
                                                <td>
                                                    <span class="status-badge ${emprestimo.status.toLowerCase()}">
                                                        ${emprestimo.status}
                                                    </span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>