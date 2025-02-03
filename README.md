# 🚀 Automação de Testes de API com Rest-Assured, Cucumber, TestNG e Allure

![GitHub repo size](https://img.shields.io/github/repo-size/pedrothh/automacao-api-rest-assured)
![GitHub contributors](https://img.shields.io/github/contributors/pedrothh/automacao-api-rest-assured)
![GitHub last commit](https://img.shields.io/github/last-commit/pedrothh/automacao-api-rest-assured)
![GitHub stars](https://img.shields.io/github/stars/pedrothh/automacao-api-rest-assured?style=social)

## 📌 Descrição

Este projeto é uma **automação de testes de API** utilizando **Rest-Assured** para requisições HTTP, **Cucumber** para escrita de cenários em BDD, **TestNG** para execução dos testes e **Allure** para geração de relatórios detalhados.

## 🛠️ Tecnologias Utilizadas

- **Java 11+**
- **Maven**
- **Rest-Assured**
- **Cucumber (BDD)**
- **TestNG**
- **Allure Reports**
- **GitHub Actions (CI/CD)**

## ✅ Como Executar os Testes

### 🔹 Pré-requisitos

- **Java 11+** instalado
- **Maven** instalado (`mvn -v` para testar)
- **Allure** instalado (`allure --version` para testar)

### 🔹 Passos para execução

#### **1️⃣ Clonar o repositório**
```sh
git clone https://github.com/pedrothh/automacao-api-rest-assured.git
cd automacao-api
```

#### **2️⃣ Executar os testes**
```sh
mvn clean test
```

#### **3️⃣ Gerar e visualizar o relatório do Allure**
```sh
allure serve
```

📜 Licença

Este projeto é licenciado sob a MIT License - veja o arquivo LICENSE para mais detalhes.