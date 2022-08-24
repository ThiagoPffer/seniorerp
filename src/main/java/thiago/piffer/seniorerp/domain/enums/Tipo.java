package thiago.piffer.seniorerp.domain.enums;

public enum Tipo {
    PRODUTO(1, "Produto"), SERVICO(2, "Serviço");

    private int cod;
    private String desc;

    private Tipo(int cod, String desc) {
        this.cod = cod;
        this.desc = desc;
    }

    public int getCod() {
        return cod;
    }

    public String getDesc() {
        return desc;
    }

    public static Tipo toEnum(Integer cod) {
        if(cod == null) { return null; }

        for(Tipo tipo : Tipo.values()) {
            if(cod.equals(tipo.getCod())) {
                return tipo;
            }
        }

        throw new IllegalArgumentException("ID Inválido: " + cod);
    }
}
