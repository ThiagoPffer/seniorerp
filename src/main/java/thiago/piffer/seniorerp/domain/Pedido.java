package thiago.piffer.seniorerp.domain;

import org.hibernate.annotations.GenericGenerator;
import thiago.piffer.seniorerp.domain.enums.Situacao;
import thiago.piffer.seniorerp.domain.util.BaseBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "PEDIDOS")
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column
    private UUID id;

    private String cliente;
    private BigDecimal total;

    private Double desconto = 0D;
    private Situacao situacao;

    private LocalDateTime dataPedido;

    @NotEmpty(message = "Ao menos um produto deve ser inserido em um novo pedido")
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itensPedido = new ArrayList<>();

    public Pedido() {}

    public Pedido(Pedido pedido) {
        this.id = pedido.id;
        this.cliente = pedido.cliente;
        this.total = pedido.total;
        this.desconto = pedido.desconto;
        this.situacao = pedido.situacao;
        this.dataPedido = pedido.dataPedido;
        this.itensPedido = pedido.itensPedido;
    }

    public UUID getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    private void setTotal() {
        this.total = BigDecimal.ZERO;
        BigDecimal totalProdutos = BigDecimal.ZERO;
        var valorDesconto = BigDecimal.ZERO;

        if (getItensPedido() != null && !getItensPedido().isEmpty()) {
            for (ItemPedido itemPedido : getItensPedido()) {
                if (itemPedido.getDescontoAplicavel()) {
                    totalProdutos = totalProdutos.add(itemPedido.getSubTotal());
                } else {
                    this.total = this.total.add(itemPedido.getSubTotal());
                }
            }
            valorDesconto = totalProdutos.multiply(BigDecimal.valueOf(getDesconto()));
            this.total = this.total.add(totalProdutos.subtract(valorDesconto));
        }
    }

    public Double getDesconto() {
        return desconto;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public List<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
        setTotal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return id.equals(pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class Builder extends BaseBuilder<Pedido> {

        private Builder(Pedido entity) { super(entity); }
        public static Builder create() { return new Builder(new Pedido()); }
        public static Builder from(Pedido pedido) { return new Builder(new Pedido(pedido)); }

        public Builder id(UUID id) {
            entity.id = id;
            return this;
        }

        public Builder cliente(String cliente) {
            entity.cliente = cliente;
            return this;
        }

        public Builder desconto(Double desconto) {
            entity.desconto = desconto;
            return this;
        }

        public Builder situacao(Situacao situacao) {
            entity.situacao = situacao;
            return this;
        }

        public Builder dataPedido(LocalDateTime dataPedido) {
            entity.dataPedido = dataPedido;
            return this;
        }

        public Builder itensPedido(List<ItemPedido> itensPedido) {
            entity.itensPedido = itensPedido;
            entity.setTotal();
            return this;
        }

        @Override
        public void beforeBuild() {}
    }
}
