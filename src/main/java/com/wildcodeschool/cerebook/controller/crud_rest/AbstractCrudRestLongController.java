package com.wildcodeschool.cerebook.controller.crud_rest;

public abstract class AbstractCrudRestLongController<E> extends AbstractCrudRestController<E, Long> {
    @Override
    protected Long parseId(String id) {
        return Long.parseLong(id);
    }
}
