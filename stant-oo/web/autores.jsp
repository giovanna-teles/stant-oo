<%-- 
    Document   : autores
    Created on : 3 de jun. de 2025, 20:44:36
    Author     : giova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html> 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Autores</title>
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
                    <h1>Autores</h1>
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
                    <a href="autores" class="menu-item active">
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
                    <!-- Error Message Alert -->
                    <c:if test="${not empty mensagemErro}">
                        <div class="alert alert-danger">
                            <i class="fas fa-exclamation-circle"></i> ${mensagemErro}
                        </div>
                    </c:if>

                    <!-- Author Form Card -->
                    <div class="card">
                        <div class="card-header">
                            <h2><i class="fas fa-user-edit"></i> ${empty autor ? 'Adicionar Autor' : 'Editar Autor'}</h2>
                        </div>
                        <div class="card-body">
                            <form action="./autores?action=${empty autor ? 'adicionar' : 'atualizar'}" method="post" class="form-container">
                                <c:if test="${not empty autor}">
                                    <input type="hidden" name="id" value="${autor.id}">
                                </c:if>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="nome"><i class="fas fa-signature"></i> Nome:</label>
                                        <div class="input-with-icon">
                                            <i class="fas fa-user"></i>
                                            <input type="text" id="nome" name="nome" value="${autor.nome}" placeholder="Digite o nome completo" required>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="dataNascimento"><i class="fas fa-calendar-alt"></i> Data de Nascimento:</label>
                                        <div class="input-with-icon">
                                            <i class="fas fa-birthday-cake"></i>
                                            <input type="date" id="dataNascimento" name="dataNascimento" value="${autor.dataNascimento}" required>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="biografia"><i class="fas fa-book-open"></i> Biografia:</label>
                                    <textarea id="biografia" name="biografia" rows="5" placeholder="Informe a biografia do autor..." required>${autor.biografia}</textarea>
                                </div>

                                <div class="form-group button-group">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-save"></i> Salvar
                                    </button>
                                    <a href="${pageContext.request.contextPath}/autores" class="btn btn-secondary">
                                        <i class="fas fa-times"></i> Cancelar
                                    </a>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Authors List Card -->
                    <div class="card">
                        <div class="card-header">
                            <h2><i class="fas fa-list"></i> Lista de Autores</h2>
                            <div class="header-actions">
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="data-table">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Nome</th>
                                            <th>Data de Nascimento</th>
                                            <th>Ações</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${autores}" var="autor">
                                            <tr>
                                                <td>${autor.id}</td>
                                                <td>${autor.nome}</td>
                                                <td>
                                                    <span class="date-badge">
                                                        <i class="fas fa-calendar-day"></i> ${autor.dataNascimento}
                                                    </span>
                                                </td>
                                                <td class="table-actions">
                                                    <a href="${pageContext.request.contextPath}/autores?action=editar&id=${autor.id}" class="btn-icon" title="Editar">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/autores?action=excluir&id=${autor.id}" class="btn-icon btn-danger" title="Excluir" onclick="return confirm('Tem certeza que deseja excluir este autor?')">
                                                        <i class="fas fa-trash"></i>
                                                    </a>
                                                    <a href="#" class="btn-icon btn-info" title="Ver detalhes">
                                                        <i class="fas fa-eye"></i>
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