package thiago.piffer.seniorerp.domain.enums;

public enum Situacao {
    EM_ABERTO(1, "Em aberto"), FECHADO(2, "Fechado");

    private int cod;
    private String desc;

    private Situacao(int cod, String desc) {
        this.cod = cod;
        this.desc = desc;
    }

    public int getCod() {
        return cod;
    }

    public String getDesc() {
        return desc;
    }

    public static Situacao toEnum(Integer cod) {
        if(cod == null) { return null; }

        for(Situacao situacao : Situacao.values()) {
            if(cod.equals(situacao.getCod())) {
                return situacao;
            }
        }

        throw new IllegalArgumentException("ID Inválido: " + cod);
    }
}
