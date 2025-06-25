<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Empréstimos</title>
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
                    <h1>Empréstimos</h1>
                </div>
                <div class="sidebar-menu">
                    <a href="index.jsp" class="menu-item">
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
                    <a href="emprestimos" class="menu-item active">
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
                    <!-- Error Message Alert -->
                    <c:if test="${not empty mensagemErro}">
                        <div class="alert alert-danger">
                            <i class="fas fa-exclamation-circle"></i> ${mensagemErro}
                        </div>
                    </c:if>

                    <!-- Loan Form Card -->
                    <div class="card">
                        <div class="card-header">
                            <h2><i class="fas fa-exchange-alt"></i> ${empty emprestimo ? 'Novo Empréstimo' : 'Editar Empréstimo'}</h2>
                        </div>
                        <div class="card-body">
                            <form action="./emprestimos?action=${empty emprestimo ? 'adicionar' : 'atualizar'}" method="post" class="form-container">
                                <c:if test="${not empty emprestimo}">
                                    <input type="hidden" name="id" value="${emprestimo.id}">
                                </c:if>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="livro"><i class="fas fa-book"></i> Livro:</label>
                                        <select id="livro" name="livro" ${not empty emprestimo ? 'disabled' : ''} required>
                                            <option value="">Selecione um livro</option>
                                            <c:forEach items="${livrosDisponiveis}" var="livro">
                                                <option value="${livro.isbn}" ${emprestimo.livro.isbn == livro.isbn ? 'selected' : ''}>${livro.titulo} - ${livro.autor.nome}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label for="pessoa"><i class="fas fa-user"></i> Pessoa:</label>
                                        <select id="pessoa" name="pessoa" ${not empty emprestimo ? 'disabled' : ''} required>
                                            <option value="">Selecione uma pessoa</option>
                                            <c:forEach items="${pessoas}" var="pessoa">
                                                <option value="${pessoa.id}" ${emprestimo.pessoa.id == pessoa.id ? 'selected' : ''}>${pessoa.nome}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="dataPrevistaDev"><i class="fas fa-calendar-day"></i> Data Prevista Devolução:</label>
                                        <input type="date" id="dataPrevistaDev" name="dataPrevistaDev" value="${emprestimo.dataPrevistaDev}" required>
                                    </div>
                                </div>

                                <div class="form-group button-group">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-save"></i> Salvar
                                    </button>
                                    <a href="${pageContext.request.contextPath}/emprestimos" class="btn btn-secondary">
                                        <i class="fas fa-times"></i> Cancelar
                                    </a>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Loans List Card -->
                    <div class="card">
                        <div class="card-header">
                            <h2><i class="fas fa-list"></i> Lista de Empréstimos</h2>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="data-table">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Livro</th>
                                            <th>Pessoa</th>
                                            <th>Data Empréstimo</th>
                                            <th>Data Prevista</th>
                                            <th>Status</th>
                                            <th>Ações</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${emprestimos}" var="emprestimo">
                                            <tr>
                                                <td>${emprestimo.id}</td>
                                                <td>${emprestimo.livro.titulo}</td>
                                                <td>${emprestimo.pessoa.nome}</td>
                                                <td>
                                                    <span class="date-badge">
                                                        <i class="fas fa-calendar-day"></i> ${emprestimo.dataEmprestimo}
                                                    </span>
                                                </td>
                                                <td>
                                                    <span class="date-badge">
                                                        <i class="fas fa-calendar-check"></i> ${emprestimo.dataPrevistaDev}
                                                    </span>
                                                </td>
                                                <td>
                                                    <span class="status-badge ${emprestimo.status == 'Ativo' ? 'active' : 'inactive'}">
                                                        ${emprestimo.status}
                                                    </span>
                                                </td>
                                                <td class="table-actions">
                                                    <c:if test="${emprestimo.status == 'Ativo'}">
                                                        <a href="${pageContext.request.contextPath}/emprestimos?action=devolver&id=${emprestimo.id}" class="btn-icon btn-success" title="Devolver" onclick="return confirm('Confirmar devolução do livro?')">
                                                            <i class="fas fa-undo"></i>
                                                        </a>
                                                    </c:if>
                                                    <a href="${pageContext.request.contextPath}/emprestimos?action=editar&id=${emprestimo.id}" class="btn-icon" title="Editar">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/emprestimos?action=excluir&id=${emprestimo.id}" class="btn-icon btn-danger" title="Excluir" onclick="return confirm('Tem certeza que deseja excluir este empréstimo?')">
                                                        <i class="fas fa-trash"></i>
                                                    </a>
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