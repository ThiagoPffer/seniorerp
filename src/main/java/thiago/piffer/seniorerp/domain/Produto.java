package thiago.piffer.seniorerp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import thiago.piffer.seniorerp.domain.enums.Tipo;
import thiago.piffer.seniorerp.domain.util.BaseBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "PRODUTOS")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column
    private UUID id;

    @NotBlank(message = "Descrição do produto é obrigatória")
    private String descricao;

    @NotNull(message = "Tipo do produto é obrigatório")
    private Tipo tipo;

    @NotNull(message = "Valor do produto é obrigatório")
    private BigDecimal valor;

    @JsonIgnore
    private Boolean ativo = Boolean.TRUE;

    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<ItemPedido> itensPedido = new ArrayList<>();

    public Produto() {}
    public Produto(Produto produto) {
        this.id = produto.id;
        this.valor = produto.valor;
        this.descricao = produto.descricao;
        this.tipo = produto.tipo;
        this.ativo = produto.ativo;
        this.itensPedido = produto.itensPedido;
    }

    public UUID getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public List<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id.equals(produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class Builder extends BaseBuilder<Produto> {

        private Builder(Produto entity) { super(entity); }
        public static Builder create() { return new Builder(new Produto()); }
        public static Builder from(Produto produto) { return new Builder(new Produto(produto)); }

        public Builder id(UUID id) {
            entity.id = id;
            return this;
        }

        public Builder descricao(String descricao) {
            entity.descricao = descricao;
            return this;
        }

        public Builder tipo(Tipo tipo) {
            entity.tipo = tipo;
            return this;
        }

        public Builder valor(BigDecimal valor) {
            entity.valor = valor;
            return this;
        }

        public Builder ativo(Boolean ativo) {
            entity.ativo = ativo;
            return this;
        }

        public Builder itensPedido(List<ItemPedido> itensPedido) {
            entity.itensPedido = itensPedido;
            return this;
        }

        @Override
        public void beforeBuild() {}
    }
}
