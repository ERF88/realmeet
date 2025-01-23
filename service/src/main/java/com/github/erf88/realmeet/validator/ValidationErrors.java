package com.github.erf88.realmeet.validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.util.Streamable;

@ToString
public class ValidationErrors implements Streamable<ValidationError> {
    private final List<ValidationError> validationErrorList;

    public ValidationErrors() {
        validationErrorList = new ArrayList<>();
    }

    public ValidationErrors add(String field, String errorCode) {
        return add(new ValidationError(field, errorCode));
    }

    public ValidationErrors add(ValidationError validationError) {
        validationErrorList.add(validationError);
        return this;
    }

    public ValidationError getError(int index) {
        return validationErrorList.get(index);
    }

    public int getNumberOfErrors() {
        return validationErrorList.size();
    }

    public boolean hasErrors() {
        return !validationErrorList.isEmpty();
    }

    @Override
    @NonNull
    public Iterator<ValidationError> iterator() {
        return validationErrorList.iterator();
    }
}
