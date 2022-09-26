package com.alten.hotel.common.validator;

/**
 * Interface crated to implement Validators and
 * override some if needed
 *
 * @param <D> Object to be validated
 */
public interface Validator<D> {
    /**
     * Method that is going to call all
     * validations needed.
     *
     * @param data Object to be tested {@link D}
     */
    void validate(D data);
}
