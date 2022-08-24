package thiago.piffer.seniorerp.domain.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PedidoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    @NotBlank(message = "Nome do cliente é obrigatório")
    private String cliente;

    private Double desconto;

    @Valid
    @NotEmpty(message = "Ao menos um produto deve ser inserido em um novo pedido")
    private List<ItemPedidoDto> itensPedido = new ArrayList<>();

    public PedidoDto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public List<ItemPedidoDto> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItemPedidoDto> itensPedido) {
        this.itensPedido = itensPedido;
    }
}
