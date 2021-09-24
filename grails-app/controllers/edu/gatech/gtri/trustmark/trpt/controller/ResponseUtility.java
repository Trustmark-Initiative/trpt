package edu.gatech.gtri.trustmark.trpt.controller;

import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.HashMap;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.P2;

import java.util.Map;

import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.Ordering.fromInt;
import static org.gtri.fj.data.HashMap.arrayHashMap;
import static org.gtri.fj.data.HashMap.iterableHashMap;
import static org.gtri.fj.product.P.p;

public class ResponseUtility {

    public static <FIELD extends Enum<FIELD>, SUCCESS> P2<Object, Integer> toResponse(
            final Validation<NonEmptyList<ValidationMessage<FIELD>>, SUCCESS> validation) {

        return Either.reduce(validation.toEither().bimap(
                failure -> p(
                        toMap(failure),
                        400),
                success -> p(
                        success,
                        200)));
    }

    private static <FIELD extends Enum<FIELD>> Map<FIELD, java.util.List<Map<String, Object>>> toMap(final NonEmptyList<ValidationMessage<FIELD>> validationMessageNonEmptyList) {

        return validationMessageNonEmptyList.toList()
                .groupBy(ValidationMessage::getField, ord((f1, f2) -> fromInt(f1.compareTo(f2))))
                .map(list -> list.map(ResponseUtility::toMap))
                .map(list -> list.toJavaList())
                .toMutableMap();
    }

    private static <FIELD extends Enum<FIELD>> Map<String, Object> toMap(final ValidationMessage<FIELD> validationMessage) {

        return validationMessage.<HashMap<String, Object>>match(
                        (field, indexNonEmptyList) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName()), p("indexNonEmptyList", indexNonEmptyList)),
                        (field, indexNonEmptyList) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName()), p("indexNonEmptyList", indexNonEmptyList)),
                        (field, size) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName()), p("size", size)),
                        (field, field1, field2) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName()), p("field1", field1.name()), p("field2", field2.name())),
                        (field, lengthMinimumInclusive, length) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName()), p("lengthMinimumInclusive", lengthMinimumInclusive), p("length", length)),
                        (field, lengthMaximumInclusive, length) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName()), p("lengthMaximumInclusive", lengthMaximumInclusive), p("length", length)),
                        (field) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName())),
                        (field) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName())),
                        (field, pattern) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName()), p("pattern", pattern)),
                        (field) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName())),
                        (field) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName())),
                        (field, validationMessageNonEmptyListIndexed) -> iterableHashMap(validationMessageNonEmptyListIndexed.map(p -> p.swap().map1(Object::toString).map2(ResponseUtility::toMap).map2(value -> value))))
                .toMap();
    }
}
