package com.wildcodeschool.cerebook.controller;

public abstract class AbstractCrudLongController<E> extends com.wildcodeschool.cerebook.controller.AbstractCrudController<E, Long> {
    @Override
    protected Long parseId(String id) { return Long.parseLong(id); }
}
