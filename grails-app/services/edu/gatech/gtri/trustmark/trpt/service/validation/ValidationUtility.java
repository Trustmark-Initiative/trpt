package edu.gatech.gtri.trustmark.trpt.service.validation;

import org.gtri.fj.Equal;
import org.gtri.fj.Ord;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.Try;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.Unit;

import java.util.regex.Pattern;

import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage.validationMessageMustBeDistinct;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage.validationMessageMustBeElementNonNull;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage.validationMessageMustBeEmpty;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage.validationMessageMustBeEqual;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage.validationMessageMustBeLengthGreaterThanOrEqual;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage.validationMessageMustBeLengthLessThanOrEqual;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage.validationMessageMustBeNonNull;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage.validationMessageMustBeNumeric;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage.validationMessageMustBePattern;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage.validationMessageMustBeReference;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage.validationMessageMustBeUnique;
import static edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage.validationMessageNonEmptyList;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.List.sequenceOption;
import static org.gtri.fj.data.NonEmptyList.fromList;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.condition;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.data.Validation.validation;
import static org.gtri.fj.product.P.p;
import static org.gtri.fj.product.Unit.unit;

public final class ValidationUtility {

    public static <FIELD, V1> Validation<NonEmptyList<ValidationMessage<FIELD>>, List<V1>> mustBeDistinct(
            final FIELD field,
            final List<V1> valueList,
            final Ord<V1> ord) {

        requireNonNull(field);
        requireNonNull(valueList);
        requireNonNull(ord);

        final List<NonEmptyList<P2<V1, Integer>>> valueListGroup = sequenceOption(valueList.zipIndex().groupBy(p -> p._1(), ord).values().map(NonEmptyList::fromList)).orSome(nil());

        return fromList(valueListGroup.filter(list -> !list.tail().isEmpty()))
                .map(nel -> Validation.<NonEmptyList<ValidationMessage<FIELD>>, List<V1>>fail(nel(validationMessageMustBeDistinct(field, nel.bind(nelInner -> nelInner.map(p -> p._2()))))))
                .orSome(success(valueList));
    }

    public static <FIELD, V1> Validation<NonEmptyList<ValidationMessage<FIELD>>, List<V1>> mustBeEmpty(
            final FIELD field,
            final List<V1> value) {

        requireNonNull(field);
        requireNonNull(value);

        return condition(
                value.isEmpty(),
                nel(validationMessageMustBeEmpty(field, value.length())),
                value);
    }

    public static <FIELD, VALUE> Validation<NonEmptyList<ValidationMessage<FIELD>>, VALUE> mustBeEqual(
            final FIELD field1,
            final FIELD field2,
            final Equal<VALUE> eq,
            final VALUE value1,
            final VALUE value2) {

        requireNonNull(field1);
        requireNonNull(field2);
        requireNonNull(eq);
        requireNonNull(value1);
        requireNonNull(value2);

        return condition(
                eq.eq(value1, value2),
                nel(validationMessageMustBeEqual(field1, field2)),
                value1);
    }

    public static <FIELD> Validation<NonEmptyList<ValidationMessage<FIELD>>, String> mustBeLengthGreaterThanOrEqual(
            final FIELD field,
            final int lengthMinimumInclusive,
            final String value) {

        requireNonNull(field);
        requireNonNull(value);

        return condition(
                value.length() >= lengthMinimumInclusive,
                nel(validationMessageMustBeLengthGreaterThanOrEqual(field, lengthMinimumInclusive, value.length())),
                value);
    }

    public static <FIELD> Validation<NonEmptyList<ValidationMessage<FIELD>>, String> mustBeLengthLessThanOrEqual(
            final FIELD field,
            final int lengthMinimumInclusive,
            final String value) {

        requireNonNull(field);
        requireNonNull(value);

        return condition(
                value.length() <= lengthMinimumInclusive,
                nel(validationMessageMustBeLengthLessThanOrEqual(field, lengthMinimumInclusive, value.length())),
                value);
    }

    public static <FIELD, VALUE> Validation<NonEmptyList<ValidationMessage<FIELD>>, VALUE> mustBeNonNull(
            final FIELD field,
            final VALUE value) {

        requireNonNull(field);

        return condition(
                value != null,
                nel(validationMessageMustBeNonNull(field)),
                value);
    }

    public static <FIELD> Validation<NonEmptyList<ValidationMessage<FIELD>>, Long> mustBeNumeric(
            final FIELD field,
            final String value) {

        requireNonNull(field);
        requireNonNull(value);

        return Try.f(() -> Long.parseLong(value))._1()
                .f().map(runtimeException -> nel(validationMessageMustBeNumeric(field)));
    }

    public static <FIELD> Validation<NonEmptyList<ValidationMessage<FIELD>>, String> mustBePattern(
            final FIELD field,
            final Pattern pattern,
            final String value) {

        requireNonNull(field);
        requireNonNull(pattern);

        return condition(
                pattern.matcher(value).matches(),
                nel(validationMessageMustBePattern(field, pattern.pattern())),
                value);
    }

    public static <FIELD, KEY, VALUE> Validation<NonEmptyList<ValidationMessage<FIELD>>, VALUE> mustBeReference(
            final FIELD field,
            final F1<KEY, Option<VALUE>> findBy,
            final KEY key) {

        requireNonNull(field);
        requireNonNull(findBy);
        requireNonNull(key);

        return findBy.f(key).toValidation(nel(validationMessageMustBeReference(field)));
    }

    public static <FIELD, KEY, VALUE> Validation<NonEmptyList<ValidationMessage<FIELD>>, VALUE> mustBeReference(
            final FIELD field,
            final F1<KEY, Option<VALUE>> findBy,
            final KEY key,
            final List<VALUE> valueList,
            final Equal<VALUE> eq) {

        requireNonNull(field);
        requireNonNull(findBy);
        requireNonNull(key);
        requireNonNull(valueList);
        requireNonNull(eq);

        return mustBeReference(field, findBy, key, value -> valueList.exists(valueInner -> eq.eq(value, valueInner)));
    }

    public static <FIELD, KEY, VALUE> Validation<NonEmptyList<ValidationMessage<FIELD>>, VALUE> mustBeReference(
            final FIELD field,
            final F1<KEY, Option<VALUE>> findBy,
            final KEY key,
            final F1<VALUE, Boolean> exists) {

        requireNonNull(field);
        requireNonNull(findBy);
        requireNonNull(key);
        requireNonNull(exists);

        return findBy.f(key).filter(exists).toValidation(nel(validationMessageMustBeReference(field)));
    }

    public static <FIELD, KEY, VALUE> Validation<NonEmptyList<ValidationMessage<FIELD>>, Unit> mustBeUnique(
            final FIELD field,
            final F1<KEY, Option<VALUE>> findBy,
            final KEY key) {

        requireNonNull(field);
        requireNonNull(findBy);
        requireNonNull(key);

        return condition(
                findBy.f(key).isNone(),
                nel(validationMessageMustBeUnique(field)),
                unit());
    }

    public static <FIELD, V1, V2> Validation<NonEmptyList<ValidationMessage<FIELD>>, List<V2>> mustBeValid(
            final FIELD field,
            final List<V1> valueList,
            final F1<V1, Validation<NonEmptyList<ValidationMessage<FIELD>>, V2>> fValidation) {

        requireNonNull(field);
        requireNonNull(valueList);
        requireNonNull(fValidation);

        final List<P2<Option<Validation<NonEmptyList<ValidationMessage<FIELD>>, V2>>, Integer>> validationList = valueList
                .map(Option::fromNull)
                .zipIndex()
                .map(p -> p(p._1().map(fValidation), p._2()));

        Option<ValidationMessage<FIELD>> validationListMessageMustBeElementNonNullOption = fromList(validationList.filter(p -> p._1().isNone())).map(nelInner -> validationMessageMustBeElementNonNull(field, nelInner.map(P2::_2)));
        Option<ValidationMessage<FIELD>> validationMessageNonEmptyListOption = fromList(validationList.filter(p -> p._1().map(Validation::isFail).orSome(false))).map(nelInner -> validationMessageNonEmptyList(field, nelInner.map(p -> p(p._1().some().fail(), p._2()))));
        List<V2> list = validationList.filter(p -> p._1().map(Validation::isSuccess).orSome(false)).map(p -> p._1().some().success());

        return validation(validationListMessageMustBeElementNonNullOption
                .map(validationListMessageMustBeElementNonNull -> validationMessageNonEmptyListOption
                        .map(validationMessageNonEmptyList -> nel(validationListMessageMustBeElementNonNull, validationMessageNonEmptyList))
                        .orSome(nel(validationListMessageMustBeElementNonNull)))
                .orElse(validationMessageNonEmptyListOption.map(NonEmptyList::nel))
                .toEither(list)
                .swap());
    }

    public static <FIELD, V1, V2> Validation<NonEmptyList<ValidationMessage<FIELD>>, List<V2>> mustBeNonNullAndDistinctAndValid(
            final FIELD field,
            final List<V1> valueList,
            final Ord<V1> ord,
            final F1<V1, Validation<NonEmptyList<ValidationMessage<FIELD>>, V2>> fValidation) {

        requireNonNull(field);
        requireNonNull(ord);

        return mustBeNonNull(field, valueList)
                .bind(nonNull -> accumulate(
                        mustBeDistinct(field, nonNull, ord),
                        mustBeValid(field, nonNull, fValidation),
                        (distinct, valid) -> valid));
    }

    public static <FIELD> Validation<NonEmptyList<ValidationMessage<FIELD>>, String> mustBeLength(
            final FIELD field,
            final int lengthMinimumInclusive,
            final int lengthMaximumInclusive,
            final String value) {

        requireNonNull(field);
        requireNonNull(value);

        return accumulate(
                mustBeLengthLessThanOrEqual(field, lengthMaximumInclusive, value),
                mustBeLengthGreaterThanOrEqual(field, lengthMinimumInclusive, value),
                (value1, value2) -> value);
    }

    public static <FIELD> Validation<NonEmptyList<ValidationMessage<FIELD>>, Long> mustBeNonNullAndNumeric(
            final FIELD field,
            final String value) {

        return mustBeNonNull(field, value)
                .bind(nonNull -> mustBeNumeric(field, nonNull));
    }

    public static <FIELD> Validation<NonEmptyList<ValidationMessage<FIELD>>, String> mustBeNonNullAndLength(
            final FIELD field,
            final int lengthMinimumInclusive,
            final int lengthMaximumInclusive,
            final String value) {

        return mustBeNonNull(field, value)
                .bind(nonNull -> mustBeLength(field, lengthMinimumInclusive, lengthMaximumInclusive, nonNull));
    }

    public static <FIELD, VALUE> Validation<NonEmptyList<ValidationMessage<FIELD>>, String> mustBeNonNullAndUniqueAndLength(
            final FIELD field,
            final F1<String, Option<VALUE>> findBy,
            final int lengthMinimumInclusive,
            final int lengthMaximumInclusive,
            final String value) {

        return mustBeNonNull(field, value)
                .bind(nonNull -> accumulate(
                        mustBeLength(field, lengthMinimumInclusive, lengthMaximumInclusive, nonNull),
                        mustBeUnique(field, findBy, nonNull),
                        (length, unqiue) -> nonNull));
    }

    public static <FIELD, KEY, VALUE> Validation<NonEmptyList<ValidationMessage<FIELD>>, VALUE> mustBeNonNullAndReference(
            final FIELD field,
            final F1<KEY, Option<VALUE>> findBy,
            final KEY key) {

        requireNonNull(field);
        requireNonNull(findBy);

        return mustBeNonNull(field, key)
                .bind(keyInner -> findBy.f(key).toValidation(nel(validationMessageMustBeReference(field))));
    }

    public static <FIELD, V1, V2> Validation<NonEmptyList<ValidationMessage<FIELD>>, V1> mustBeNullOr(
            final FIELD field,
            final V1 value,
            final F2<FIELD, V1, Validation<NonEmptyList<ValidationMessage<FIELD>>, V1>> validation) {

        requireNonNull(field);
        requireNonNull(validation);

        return value == null ?
                success(value) :
                validation.f(field, value);
    }
}
