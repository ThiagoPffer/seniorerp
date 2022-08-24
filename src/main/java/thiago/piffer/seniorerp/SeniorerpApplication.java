package thiago.piffer.seniorerp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import thiago.piffer.seniorerp.domain.ItemPedido;
import thiago.piffer.seniorerp.repositories.ItemPedidoRepository;
import thiago.piffer.seniorerp.domain.Pedido;
import thiago.piffer.seniorerp.repositories.ProdutoRepository;
import thiago.piffer.seniorerp.services.PedidoService;
import thiago.piffer.seniorerp.domain.Produto;
import thiago.piffer.seniorerp.services.ProdutoService;
import thiago.piffer.seniorerp.domain.enums.Tipo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class SeniorerpApplication{

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public static void main(String[] args) {
        SpringApplication.run(SeniorerpApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
////        Produto produto = Produto.Builder.create().id(null).descricao("Microondas").tipo(Tipo.PRODUTO).valor(BigDecimal.valueOf(550)).build();
////        Produto produto2 = Produto.Builder.create().id(null).descricao("Instalação").tipo(Tipo.SERVICO).valor(BigDecimal.valueOf(150)).build();
////        Produto produtoInativo = Produto.Builder.create().id(null).descricao("Teclado").tipo(Tipo.PRODUTO).valor(BigDecimal.valueOf(250)).ativo(Boolean.FALSE).build();
////        Pedido pedido = Pedido.Builder.create().id(null).cliente("Thiago Piffer").desconto(0.5).build();
////        ItemPedido itemPedido = ItemPedido.Builder.create().id(null).quantidade(5).produto(produto).pedido(pedido).build();
////        ItemPedido itemPedido2 = ItemPedido.Builder.create().id(null).quantidade(1).produto(produto2).pedido(pedido).build();
////        produto.setItensPedido(Arrays.asList(itemPedido));
////        produto2.setItensPedido(Arrays.asList(itemPedido2));
////        produtoRepository.saveAll(Arrays.asList(produto, produto2, produtoInativo));
////        List<ItemPedido> list = new ArrayList<>(Arrays.asList(itemPedido, itemPedido2));
////        pedido.setItensPedido(list);
////        pedidoService.save(pedido);
//    }
}
