package com.codeinsight.converter;


import java.util.List;

public abstract class AbstractConverter<E, B> {

	public abstract B getBeanFromEntity(E entity);

	public abstract E getEntityFromBean(B bean);

	public abstract List<E> getEntitiesFromBeans(List<B> beans);

	public abstract List<B> getBeansFromEntities(List<E> entities);


}
