<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pessoas</title>
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
                    <h1>Pessoas</h1>
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
                    <a href="emprestimos" class="menu-item">
                        <i class="fas fa-exchange-alt"></i>
                        <span>Empréstimos</span>
                    </a>
                    <a href="pessoas" class="menu-item active">
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

                    <!-- Person Form Card -->
                    <div class="card">
                        <div class="card-header">
                            <h2><i class="fas fa-user-plus"></i> ${empty pessoa ? 'Adicionar Pessoa' : 'Editar Pessoa'}</h2>
                        </div>
                        <div class="card-body">
                            <form action="./pessoas?action=${empty pessoa ? 'adicionar' : 'atualizar'}" method="post" class="form-container">
                                <c:if test="${not empty pessoa}">
                                    <input type="hidden" name="id" value="${pessoa.id}">
                                </c:if>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="nome"><i class="fas fa-user"></i> Nome:</label>
                                        <input type="text" id="nome" name="nome" value="${pessoa.nome}" required>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="email"><i class="fas fa-envelope"></i> Email:</label>
                                        <input type="email" id="email" name="email" value="${pessoa.email}" required>
                                    </div>

                                    <div class="form-group">
                                        <label for="telefone"><i class="fas fa-phone"></i> Telefone:</label>
                                        <input type="text" id="telefone" name="telefone" value="${pessoa.telefone}" required>
                                    </div>
                                </div>

                                <div class="form-group button-group">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-save"></i> Salvar
                                    </button>
                                    <a href="${pageContext.request.contextPath}/pessoas" class="btn btn-secondary">
                                        <i class="fas fa-times"></i> Cancelar
                                    </a>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- People List Card -->
                    <div class="card">
                        <div class="card-header">
                            <h2><i class="fas fa-users"></i> Lista de Pessoas</h2>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="data-table">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Nome</th>
                                            <th>Email</th>
                                            <th>Telefone</th>
                                            <th>Ações</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${pessoas}" var="pessoa">
                                            <tr>
                                                <td>${pessoa.id}</td>
                                                <td>${pessoa.nome}</td>
                                                <td>${pessoa.email}</td>
                                                <td>${pessoa.telefone}</td>
                                                <td class="table-actions">
                                                    <a href="${pageContext.request.contextPath}/pessoas?action=editar&id=${pessoa.id}" class="btn-icon" title="Editar">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/pessoas?action=excluir&id=${pessoa.id}" class="btn-icon btn-danger" title="Excluir" onclick="return confirm('Tem certeza que deseja excluir esta pessoa?')">
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