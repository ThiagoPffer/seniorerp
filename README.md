# Desafio técnico Sênior Sistemas
Este projeto trata-se de um desafio técnico proposto em um processo seletivo para a Sênior Sistemas. Abaixo é possível conferir todos os endpoints e funcionalidades da aplicação:

## A aplicação
Nesta aplicação, é possível cadastrar, editar, deletar e listar produtos, pedidos e seus itens de pedido.

## Configuração, build e inicialização
Por padrão, a aplicação está com o perfil de desenvolvimento `dev` configurado, utilizando o banco de dados h2.
Para configurar um banco de dados postgres, deve-se acessar o arquivo `application.properties`, alterar o perfil para `test`, acessar o arquivo `application-test.properties` e inserir as informações do seu banco local.

Com tudo configurado, basta rodar a aplicação através do arquivo `SeniorerpApplication` em src/main/java/thiago.piffer.seniorerp

ou

No diretório da aplicação, rodar `-DskipTests clean dependency:list install` para buildar e, por fim, `java -D -jar target/seniorerp-0.0.1-SNAPSHOT.jar` para iniciar.
Para rodar os testes, basta rodar o primeiro comando sem o `-DskipTests`.

## Endpoints
### ```/produtos```
#### POST:
- **Função:** inserir um novo produto.
- **Corpo da requisição**: JSON
```
{
    "descricao": Descrição_do_produto,
    "tipo": Tipo_do_produto_podendo_ser_SERVICO_ou_PRODUTO,
    "valor": Valor_do_produto
}
```
- **Validações e formatos**:
    - *"descricao"*: Obrigatório; String
    - *"tipo"*: Obrigatório; String
    - *"valor"*: Obrigatório; BigDecimal
- **Corpo da resposta**: Nenhum

#### GET:
- **Função:** listar todos os produtos cadastrados, em paginação.
- **Corpo da requisição**: Nenhum
- **Corpo da resposta**: JSON
```
{
    "content": [
        {
            "id": "ef8a6c35-4d5c-4beb-aa4c-f84735ac2903",
            "descricao": "Mouse",
            "tipo": "PRODUTO",
            "valor": 260.00
        }
    ],
    [...dados da paginação...]
}
```

### ```/produtos/:uuid```
#### GET:
- **Função:** buscar um produto através do seu uuid.
- **Path param**: UUID
- **Corpo da requisição**: Nenhum
- **Corpo da resposta**: JSON
```
{
	"id": "ef8a6c35-4d5c-4beb-aa4c-f84735ac2903",
	"descricao": "Mouse",
	"tipo": "PRODUTO",
	"valor": 260.00
}
```

#### PUT:
- **Função:** editar um produto especificado pelo seu uuid.
- **Path param**: UUID
- **Corpo da requisição**: JSON
```
{
    "id": UUID_do_produto
    "descricao": Descrição_do_produto,
    "tipo": Tipo_do_produto_podendo_ser_SERVICO_ou_PRODUTO,
    "valor": Valor_do_produto
}
```
- **Corpo da resposta**: Nenhum

#### DELETE:
- **Função:** remove o produto especificado pelo seu uuid.
- **Path param**: UUID
- **Corpo da requisição**: Nenhum
- **Corpo da resposta**: Nenhum


### ```/pedidos```
#### POST:
- **Função:** inserir um novo pedido.
- **Corpo da requisição**: JSON
```
{
    "cliente": Nome_do_cliente,
    "desconto": Desconto_do_pedido_em_decimais,
    "itensPedido": [
        {
            "quantidade": Quantidade_de_produtos,
            "produtoId": UUID_do_produto
        },
        ...
    ]
}
```
- **Validações e formatos**:
    - *"cliente"*: Obrigatório; String
    - *"desconto"*: Opcional (default: 0); Double (Deve ser um número de 0 a 1)
    - *"itensPedido"*: Pelo menos um item; Array
        - *"quantidade"*: Deve ser acima de 1; Integer
        - *"produtoId"*: Obrigatório; UUID
- **Corpo da resposta**: Nenhum

#### GET:
- **Função:** listar todos os pedidos cadastrados, em paginação.
- **Corpo da requisição**: Nenhum
- **Corpo da resposta**: JSON
```
{
    "content": [
        {
            "id": "3f1c5276-c076-4051-bb47-1aa78f21b4c0",
            "cliente": "Thiago Piffer",
            "total": 156.00,
            "desconto": 0.4,
            "situacao": "EM_ABERTO",
            "dataPedido": "2022-08-24T12:26:22.958255",
            "itensPedido": [
                {
                    "id": "9ba18b3b-0d6a-4d16-bbd8-ed9cb246c667",
                    "subTotal": 260.00,
                    "quantidade": 1,
                    "produto": {
                        "id": "ef8a6c35-4d5c-4beb-aa4c-f84735ac2903",
                        "descricao": "Mouse",
                        "tipo": "PRODUTO",
                        "valor": 260.00
                    }
                }
            ]
        }
    ],
    [...dados da paginação...]
}
```

### ```/pedidos/:uuid```
#### GET:
- **Função:** buscar um pedido através do seu uuid.
- **Path param**: UUID
- **Corpo da requisição**: Nenhum
- **Corpo da resposta**: JSON
```
{
    "id": "ef8a6c35-4d5c-4beb-aa4c-f84735ac2903",
    "descricao": "Mouse",
    "tipo": "PRODUTO",
    "valor": 260.00
}
```

#### PUT:
- **Função:** editar um produto especificado pelo seu uuid.
- **Path param**: UUID
- **Corpo da requisição**: JSON
```
{
    "id": UUID do pedido
    "cliente": Nome_do_cliente,
    "desconto": Desconto_do_pedido_em_decimais,
    "itensPedido": [
        {
            "id": UUID do item de pedido
            "quantidade": Quantidade_de_produtos,
            "produtoId": UUID_do_produto
        },
        ...
    ]
}
```
- **Corpo da resposta**: Nenhum

#### DELETE:
- **Função:** remove o pedido especificado pelo seu uuid, removendo também seus itens de pedido.
- **Path param**: UUID
- **Corpo da requisição**: Nenhum
- **Corpo da resposta**: Nenhum


### ```/pedidos/:uuid/situacoes```
#### PUT:
- **Função:** Alterar a situação de um pedido.
- **Path param**: UUID
- **Query param**: situacao=EM_ABERTO / FECHADO
- **Corpo da requisição**: Nenhum
- **Corpo da resposta**: Nenhum


### ```/itemPedidos```
#### GET:
- **Função:** listar todos os itens de pedidos cadastrados, em paginação.
- **Corpo da requisição**: Nenhum
- **Corpo da resposta**: JSON
```
{
    "content": [
        {
            "id": "9ba18b3b-0d6a-4d16-bbd8-ed9cb246c667",
            "subTotal": 260.00,
            "quantidade": 1,
            "produto": {
                "id": "ef8a6c35-4d5c-4beb-aa4c-f84735ac2903",
                "descricao": "Mouse",
                "tipo": "PRODUTO",
                "valor": 260.00
            }
        }
    ],
    [...dados da paginação...]
}
```

### ```/itemPedidos/:uuid```
#### GET:
- **Função:** buscar um item de pedido através do seu uuid.
- **Path param**: UUID
- **Corpo da requisição**: Nenhum
- **Corpo da resposta**: JSON
```
{
    "id": "9ba18b3b-0d6a-4d16-bbd8-ed9cb246c667",
    "subTotal": 260.00,
    "quantidade": 1,
    "produto": {
        "id": "ef8a6c35-4d5c-4beb-aa4c-f84735ac2903",
        "descricao": "Mouse",
        "tipo": "PRODUTO",
        "valor": 260.00
    }
}
```
