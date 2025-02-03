#language: pt
@auth
Funcionalidade: Auth

  Cenário: Validar o endpoint POST /login
    Dado que o endpoint POST "/login" está disponível
    Quando enviar a requisição POST
    Então o código de resposta deve ser 200
    E a estrutura de resposta deve estar correta

  Cenário: Validar o endpoint PUT /update-password
    Dado que tenho um token válido
    E que o endpoint PUT "/update-password" está disponível
    Quando enviar a requisição PUT
    Então o código de resposta deve ser 201
    E a estrutura de resposta deve estar correta
    E a mensagem da resposta deve ser "Senha atualizada com sucesso."

  Esquema do Cenário: Validar o endpoint POST /login com Credenciais incorretas
    Dado que o endpoint POST "/login" está disponível
    Quando enviar a requisição POST com tags alteradas
      | Propriedade | Valor    |
      | password    | <valor>  |
    Então o código de resposta deve ser 401
    E a mensagem da resposta deve ser "Credenciais incorretas."
    Exemplos:
        | valor    |
        | password |
        | empty    |
