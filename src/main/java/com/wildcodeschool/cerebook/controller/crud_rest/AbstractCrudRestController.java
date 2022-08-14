package com.wildcodeschool.cerebook.controller.crud_rest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class AbstractCrudRestController<E, EK> {
    protected abstract JpaRepository<E, EK> getRepository();
    protected abstract String getControllerRoute();
    protected abstract EK parseId(String id);
    protected abstract String[] getElementFields();
    protected abstract Class<E> getElementClass();

    protected void preProcessElement(E e, HttpServletRequest hsr) {}

    protected void postProcessElementForUpdateGet(E e) {}

    protected E getElement(String id) {
        return getRepository().getById(parseId(id));
    }

    @GetMapping("")
    public List<E> getAll() {
        return getRepository().findAll();
    }

    @GetMapping("/{id}")
    public E getOne(@PathVariable("id") String id) {
        return getRepository().findById(
                parseId(id)
        ).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND, getNotFoundMessage(id)
                ));
    }

    @PostMapping("")
    public E addOne(@ModelAttribute E e, HttpServletRequest hsr) {
        preProcessElement(e, hsr);
        return getRepository().save(e);
    }

    @PutMapping("/{id}")
    @PatchMapping("/{id}")
    public E updateOne(
            @PathVariable("id") String id,
            @ModelAttribute E e,
            HttpServletRequest hsr
    ) {
        preProcessElement(e, hsr);
        return getRepository().save(e);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable("id") String id) {
        try {
            getRepository().deleteById(
                    parseId(id)
            );
        }
        catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, getNotFoundMessage(id));
        }
    }

    protected String getNotFoundMessage(String id) {
        return "Entity " + getElementClass().getName() + "with id " + id + " not found";
    }
}
