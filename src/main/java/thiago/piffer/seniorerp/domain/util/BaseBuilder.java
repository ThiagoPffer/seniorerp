package thiago.piffer.seniorerp.domain.util;

public abstract class BaseBuilder<T>{

    protected final T entity;

    public BaseBuilder(T entity) { this.entity = entity; }

    public abstract void beforeBuild();

    public T build() {
        beforeBuild();
        return entity;
    }

}
