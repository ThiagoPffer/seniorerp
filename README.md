<h1>Desafio técnico SÊNIOR Sistemas</h1>

<p>Este projeto trata-se de um desafio técnico propost em um processo seletivo para a Sênior Sistemas. 
Abaixo é possível conferir todos os endpoints e funcionalidades da aplicação:</p>

<h3>A aplicação</h3>
<p>Nesta aplicação, é possível cadastrar, editar, deletar e listar produtos, pedidos e seus itens de pedido.</p>

<h3>EndPoints</h3>
<div class="endpoint_box">
    <h4 class="endpoint">
        /produtos
    </h4>
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
    <h4 class="endpoint">
        /produtos:uuid
    </h4>
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
    <h4 class="endpoint">
        /pedidos
    </h4>
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
    <h4 class="endpoint">
        /pedidos:uuid
    </h4>
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