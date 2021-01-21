package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @deprecated (when, why, refactoring advice...)
 */
@Deprecated
@JsonInclude(Include.NON_NULL)
public class Response<T> {
    
    private boolean success;
    private T data;
    private Set<Error> errors;
    
    public Response(T data) {
        success = true;
        this.data = data;
    }
    
    public Response(Set<Error> errors) {
        success = false;
        this.errors = errors;
    }
    
    public Response(Error error) {
        success = false;
        errors = new HashSet<>(1);
        errors.add(error);
    }
    
    public boolean isSuccess() {
        return success;
    }
        
    public T getData() {
        return data;
    }
    
    public Set<Error> getErrors() {
        return errors;
    }
        
    public void addError(Error error) {
        if (errors == null) {
            errors = new HashSet<>();
        }
        success = false;
        errors.add(error);
    }
}