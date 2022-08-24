<style>
    .endpoint {
        background-color: #9BFF91; 
        color: black; 
        padding: 8px 15px; 
        font-size: 18px; 
        border-radius: 4px; 
        font-family: Courier; 
        font-weigth: 500;    
    }

    ul, li {
        padding: 0;
        margin: 10px 0;
        list-style: none;
    }

    li span {
        font-size: 16px;
    }

    .endpoint_box {
        margin: 25px 0;
    }

    .endpoint_method {
        padding: 5px 10px;
        border-radius: 4px;
        margin-right: 15px;
        color: black;
        background-color: #6879ff;
    }
</style>

<h1>Desafio técnico SÊNIOR Sistemas</h1>

<p>Este projeto trata-se de um desafio técnico propost em um processo seletivo para a Sênior Sistemas. 
Abaixo é possível conferir todos os endpoints e funcionalidades da aplicação:</p>

<h3>A aplicação</h3>
<p>Nesta aplicação, é possível cadastrar, editar, deletar e listar produtos, pedidos e seus itens de pedido.</p>

<h3>EndPoints</h3>
<div class="endpoint_box">
    <span class="endpoint">
        /produtos
    </span>
    <ul>
        <li><b class="endpoint_method">GET</b>
            <span>Retorna todos os produtos já cadastrados, em paginação.</span>
        </li>
        <li><b class="endpoint_method">POST</b>
            <span>
                Insere um novo produto no banco.            
            </span>
        </li>
    </ul>
</div>

<div class="endpoint_box">
    <span class="endpoint">
        /produtos:uuid
    </span>
    <ul>
        <li><b class="endpoint_method">GET</b>
            <span>Retorna um produto de acordo com o uuid correspondente ao enviado na url.</span>
        </li>
        <li><b class="endpoint_method">PUT</b>
            <span>Edita o produto com uuid correspondente ao enviado na url.</span>
        </li>
        <li><b class="endpoint_method">DELETE</b>
            <span>Exclui do banco o produto com uuid correspondente ao enviado na url.</span>
        </li>
    </ul>
</div>

<div class="endpoint_box">
    <span class="endpoint">
        /pedidos
    </span>
    <ul>
        <li><b class="endpoint_method">GET</b>
            <span>Retorna um produto de acordo com o uuid correspondente ao enviado na url.</span>
        </li>
        <li><b class="endpoint_method">POST</b>
            <span>
                Insere um novo pedido no banco.            
            </span>
        </li>
    </ul>
</div>

<div class="endpoint_box">
    <span class="endpoint">
        /pedidos:uuid
    </span>
    <ul>
        <li><b class="endpoint_method">GET</b>
            <span>Retorna um pedido de acordo com o uuid correspondente ao enviado na url.</span>
        </li>
        <li><b class="endpoint_method">PUT</b>
            <span>Edita o pedido com uuid correspondente ao enviado na url.</span>
        </li>
        <li><b class="endpoint_method">DELETE</b>
            <span>Exclui do banco o pedido com uuid correspondente ao enviado na url.</span>
        </li>
    </ul>
</div>