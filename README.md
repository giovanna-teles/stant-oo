# Stant-oo - Sistema de Gerenciamento de Biblioteca

Sistema completo para gerenciamento de bibliotecas físicas com controle de empréstimos, desenvolvido com Jakarta EE 10 seguindo os padrões MVC e DAO.

# ✨ Funcionalidades

📚 CRUD completo para Autores, Livros, Pessoas e Empréstimos

🔒 Controle de status de livros (Disponível, Emprestado, Atrasado, etc.)

📊 Dashboard com estatísticas

🖥️ Interface web responsiva

# 🛠️ Tecnologias
Backend: Jakarta EE 10 (Servlets, JSP, JSTL)

Frontend: HTML5, CSS3, JavaScript Vanilla

Banco de Dados: MySQL

Servidor: Apache TomEE 8.0+

Dependências:

MySQL Connector/J

Jakarta Servlet API 5.0

Jakarta Server Pages 3.0

Jakarta Expression Language 4.0

# 🏗️ Estrutura do Projeto
text
```bash
biblioteca-web/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controller/      # Servlets
│   │   │   ├── dao/             # Data Access Objects
│   │   │   ├── model/           # Entidades
│   │   │   └── util/            # Utilitários
│   │   ├── resources/
│   │   │   └── META-INF/        # Configurações
│   │   └── webapp/
│   │       ├── assets/          # CSS, JS, imagens
│   │       ├── WEB-INF/
│   │       │   └── views/       # Páginas JSP
│   │       └── index.jsp        # Página inicial
├── database/
│   └── schema.sql               # Script do banco de dados
└── pom.xml                      # Configuração Maven
````
# 📚 Documentação
Rotas Principais
```bash
| Rota               | Descrição                 | Método      |
|--------------------|---------------------------|-------------|
| `/dashboard`       | Painel principal          | GET         |
| `/autores`         | CRUD de autores           | GET/POST    |
| `/livros`          | CRUD de livros            | GET/POST    |
| `/pessoas`         | CRUD de usuários          | GET/POST    |
| `/emprestimos`     | Gerenciar empréstimos     | GET/POST    |
````

# 📄 Licença
Este projeto está licenciado sob a Licença MIT - veja o arquivo LICENSE para detalhes.

# ✉️ Contato
Desenvolvedora: Giovanna Teles

LinkedIn: https://www.linkedin.com/in/giovanna-teles-43b947204/
