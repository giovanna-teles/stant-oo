CREATE DATABASE sistema_gerenciamento;
USE sistema_gerenciamento;

-- Tabela de Administradores (com login)
CREATE TABLE Administrador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL, 
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Autores
CREATE TABLE Autor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    dt_nascimento DATE NOT NULL,
    biografia TEXT NOT NULL
);

-- Tabela de Livros (vinculada ao administrador)
CREATE TABLE Livro (
    ISBN INT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor_id INT NOT NULL,
    genero VARCHAR(100) NOT NULL,
    edicao INT,
    status ENUM('Disponível', 'Emprestado', 'Atrasado', 'Devolvido', 'Reservado') DEFAULT 'Disponível',
    nota TINYINT CHECK (nota BETWEEN 0 AND 5),  -- Nota de 0 a 5
    administrador_id INT NOT NULL,
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_Livro_Autor FOREIGN KEY (autor_id) REFERENCES Autor(id),
    CONSTRAINT fk_Livro_Administrador FOREIGN KEY (administrador_id) REFERENCES Administrador(id)
);

-- Tabela de Pessoas/Usuários (com dados únicos)
CREATE TABLE Pessoa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(15) NOT NULL UNIQUE, 
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uc_Pessoa_dados UNIQUE (nome, email, telefone)  
);

-- Tabela de Empréstimos
CREATE TABLE Emprestimo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    livro_isbn INT NOT NULL,
    pessoa_id INT NOT NULL,
    administrador_id INT NOT NULL,  
    data_emprestimo DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_prevista_dev DATE NOT NULL,
    data_devolucao DATE NULL,  
    status ENUM('Ativo', 'Devolvido', 'Atrasado') DEFAULT 'Ativo',
    CONSTRAINT fk_Emprestimo_Livro FOREIGN KEY (livro_isbn) REFERENCES Livro(ISBN),
    CONSTRAINT fk_Emprestimo_Pessoa FOREIGN KEY (pessoa_id) REFERENCES Pessoa(id),
    CONSTRAINT fk_Emprestimo_Administrador FOREIGN KEY (administrador_id) REFERENCES Administrador(id)
);


INSERT INTO Administrador (nome, email, senha_hash) 
VALUES ('Admin', 'admin@biblioteca.com', 'Jix8CsxCOSzKJ8LEUXABGA==:njAcv96K9gnlDBi/fn8OhrFE9As5Esr74hJRw6yvaio=');



select * from Administrador ;
select * from Autor;
select * from Livro;
select * from Pessoa ;
select * from Emprestimo;





