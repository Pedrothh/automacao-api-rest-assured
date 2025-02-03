#language: pt
@users
Funcionalidade: Users

  Contexto:
    Dado que tenho um token válido

  Cenário: Validar o endpoint GET /users
    Dado que o endpoint GET "/users" está disponível
    Quando enviar a requisição GET
    Então o código de resposta deve ser 200
    E a estrutura de resposta deve estar correta

  Esquema do Cenário: Validar o endpoint GET /user/{id}
    Dado que o endpoint GET "/user/{id}" está disponível com o parâmetro <id>
    Quando enviar a requisição GET
    Então o código de resposta deve ser 200
    E a estrutura de resposta deve estar correta
    Exemplos:
        | id |
        | 1  |
        | 2  |

  Cenário: Validar o endpoint GET /user-info
    Dado que o endpoint GET "/user-info" está disponível
    Quando enviar a requisição GET
    Então o código de resposta deve ser 200
    E a estrutura de resposta deve estar correta

  Cenário: Validar o endpoint DELETE /inativar-user/{id}
    Dado que o endpoint DELETE "/inativar-user/{id}" está disponível com o parâmetro 2
    Quando enviar a requisição DELETE
    Então o código de resposta deve ser 200
    E a estrutura de resposta deve estar correta
    E a mensagem da resposta deve ser "Conta inativada com sucesso."

  Cenário: Validar o endpoint PUT /reativar-user/{id}
    Dado que o endpoint PUT "/reativar-user/{id}" está disponível com o parâmetro 2
    Quando enviar a requisição PUT
    Então o código de resposta deve ser 200
    E a estrutura de resposta deve estar correta
    E a mensagem da resposta deve ser "Conta reativada com sucesso."

