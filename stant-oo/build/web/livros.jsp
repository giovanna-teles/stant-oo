<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Livros</title>
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
                    <h1>Livros</h1>
                </div>
                <div class="sidebar-menu">
                    <a href="index.jsp" class="menu-item">
                        <i class="fas fa-tachometer-alt"></i>
                        <span>Dashboard</span>
                    </a>
                    <a href="livros" class="menu-item active">
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
                    <!-- Error Message Alert -->
                    <c:if test="${not empty mensagemErro}">
                        <div class="alert alert-danger">
                            <i class="fas fa-exclamation-circle"></i> ${mensagemErro}
                        </div>
                    </c:if>

                    <!-- Book Form Card -->
                    <div class="card">
                        <div class="card-header">
                            <h2><i class="fas fa-book"></i> ${empty livro ? 'Adicionar Livro' : 'Editar Livro'}</h2>
                        </div>
                        <div class="card-body">
                            <form action="./livros?action=${empty livro ? 'adicionar' : 'atualizar'}" method="post" class="form-container">
                                <c:if test="${not empty livro}">
                                    <input type="hidden" name="isbn" value="${livro.isbn}">
                                </c:if>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="isbn"><i class="fas fa-barcode"></i> ISBN:</label>
                                        <input type="number" id="isbn" name="isbn" value="${livro.isbn}" ${not empty livro ? 'readonly' : ''} required>
                                    </div>

                                    <div class="form-group">
                                        <label for="titulo"><i class="fas fa-heading"></i> Título:</label>
                                        <input type="text" id="titulo" name="titulo" value="${livro.titulo}" required>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="autor"><i class="fas fa-user-edit"></i> Autor:</label>
                                        <select id="autor" name="autor" required>
                                            <option value="">Selecione um autor</option>
                                            <c:forEach items="${autores}" var="autor">
                                                <option value="${autor.id}" ${livro.autor.id == autor.id ? 'selected' : ''}>${autor.nome}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label for="genero"><i class="fas fa-tags"></i> Gênero:</label>
                                        <input type="text" id="genero" name="genero" value="${livro.genero}" required>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="edicao"><i class="fas fa-layer-group"></i> Edição:</label>
                                        <input type="number" id="edicao" name="edicao" value="${livro.edicao}">
                                    </div>

                                    <div class="form-group">
                                        <label for="status"><i class="fas fa-info-circle"></i> Status:</label>
                                        <select id="status" name="status" required>
                                            <option value="Disponível" ${livro.status == 'Disponível' ? 'selected' : ''}>Disponível</option>
                                            <option value="Emprestado" ${livro.status == 'Emprestado' ? 'selected' : ''}>Emprestado</option>
                                            <option value="Atrasado" ${livro.status == 'Atrasado' ? 'selected' : ''}>Atrasado</option>
                                            <option value="Devolvido" ${livro.status == 'Devolvido' ? 'selected' : ''}>Devolvido</option>
                                            <option value="Reservado" ${livro.status == 'Reservado' ? 'selected' : ''}>Reservado</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="nota"><i class="fas fa-star"></i> Nota (0-5):</label>
                                    <input type="number" id="nota" name="nota" min="0" max="5" value="${livro.nota}">
                                </div>

                                <div class="form-group button-group">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-save"></i> Salvar
                                    </button>
                                    <a href="${pageContext.request.contextPath}/livros" class="btn btn-secondary">
                                        <i class="fas fa-times"></i> Cancelar
                                    </a>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Books List Card -->
                    <div class="card">
                        <div class="card-header">
                            <h2><i class="fas fa-list"></i> Lista de Livros</h2>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="data-table">
                                    <thead>
                                        <tr>
                                            <th>ISBN</th>
                                            <th>Título</th>
                                            <th>Autor</th>
                                            <th>Gênero</th>
                                            <th>Status</th>
                                            <th>Ações</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${livros}" var="livro">
                                            <tr>
                                                <td>${livro.isbn}</td>
                                                <td>${livro.titulo}</td>
                                                <td>${livro.autor.nome}</td>
                                                <td>${livro.genero}</td>
                                                <td>
                                                    <span class="status-badge ${livro.status.toLowerCase()}">
                                                        ${livro.status}
                                                    </span>
                                                </td>
                                                <td class="table-actions">
                                                    <a href="${pageContext.request.contextPath}/livros?action=editar&isbn=${livro.isbn}" class="btn-icon" title="Editar">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/livros?action=excluir&isbn=${livro.isbn}" class="btn-icon btn-danger" title="Excluir" onclick="return confirm('Tem certeza que deseja excluir este livro?')">
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