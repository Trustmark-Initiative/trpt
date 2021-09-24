package edu.gatech.gtri.trustmark.trpt.service.validation;

import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.F3;
import org.gtri.fj.product.P2;

import static java.util.Objects.requireNonNull;

public abstract class ValidationMessage<FIELD> {

    private ValidationMessage() {
    }

    public abstract FIELD getField();

    public abstract <T1> T1 match(
            F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeDistinct,
            F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeElementNonNull,
            F2<FIELD, Integer, T1> fValidationMessageMustBeEmpty,
            F3<FIELD, FIELD, FIELD, T1> fValidationMessageMustBeEqual,
            F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthGreaterThanOrEqual,
            F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthLessThanOrEqual,
            F1<FIELD, T1> fValidationMessageMustBeNonNull,
            F1<FIELD, T1> fValidationMessageMustBeNumeric,
            F2<FIELD, String, T1> fValidationMessageMustBePattern,
            F1<FIELD, T1> fValidationMessageMustBeReference,
            F1<FIELD, T1> fValidationMessageMustBeUnique,
            F2<FIELD, NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>>, T1> fValidationMessageNonEmptyList);

    public static class ValidationMessageMustBeDistinct<FIELD> extends ValidationMessage<FIELD> {

        private final FIELD field;
        private final NonEmptyList<Integer> indexNonEmptyList;

        public ValidationMessageMustBeDistinct(final FIELD field, final NonEmptyList<Integer> indexNonEmptyList) {
            this.field = field;
            this.indexNonEmptyList = indexNonEmptyList;
        }

        public FIELD getField() {
            return field;
        }

        public NonEmptyList<?> getIndexNonEmptyList() {
            return indexNonEmptyList;
        }

        public <T1> T1 match(
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeDistinct,
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeElementNonNull,
                F2<FIELD, Integer, T1> fValidationMessageMustBeEmpty,
                F3<FIELD, FIELD, FIELD, T1> fValidationMessageMustBeEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthGreaterThanOrEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthLessThanOrEqual,
                F1<FIELD, T1> fValidationMessageMustBeNonNull,
                F1<FIELD, T1> fValidationMessageMustBeNumeric,
                F2<FIELD, String, T1> fValidationMessageMustBePattern,
                F1<FIELD, T1> fValidationMessageMustBeReference,
                F1<FIELD, T1> fValidationMessageMustBeUnique,
                F2<FIELD, NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>>, T1> fValidationMessageNonEmptyList) {

            requireNonNull(fValidationMessageMustBeDistinct);
            requireNonNull(fValidationMessageMustBeElementNonNull);
            requireNonNull(fValidationMessageMustBeEmpty);
            requireNonNull(fValidationMessageMustBeEqual);
            requireNonNull(fValidationMessageMustBeLengthGreaterThanOrEqual);
            requireNonNull(fValidationMessageMustBeLengthLessThanOrEqual);
            requireNonNull(fValidationMessageMustBeNonNull);
            requireNonNull(fValidationMessageMustBeNumeric);
            requireNonNull(fValidationMessageMustBePattern);
            requireNonNull(fValidationMessageMustBeReference);
            requireNonNull(fValidationMessageMustBeUnique);
            requireNonNull(fValidationMessageNonEmptyList);

            return fValidationMessageMustBeDistinct.f(field, indexNonEmptyList);
        }
    }

    public static final class ValidationMessageMustBeElementNonNull<FIELD> extends ValidationMessage<FIELD> {

        private final FIELD field;
        private final NonEmptyList<Integer> indexNonEmptyList;

        public ValidationMessageMustBeElementNonNull(final FIELD field, final NonEmptyList<Integer> indexNonEmptyList) {
            this.field = field;
            this.indexNonEmptyList = indexNonEmptyList;
        }

        public FIELD getField() {
            return field;
        }

        public NonEmptyList<?> getIndexNonEmptyList() {
            return indexNonEmptyList;
        }

        public <T1> T1 match(
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeDistinct,
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeElementNonNull,
                F2<FIELD, Integer, T1> fValidationMessageMustBeEmpty,
                F3<FIELD, FIELD, FIELD, T1> fValidationMessageMustBeEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthGreaterThanOrEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthLessThanOrEqual,
                F1<FIELD, T1> fValidationMessageMustBeNonNull,
                F1<FIELD, T1> fValidationMessageMustBeNumeric,
                F2<FIELD, String, T1> fValidationMessageMustBePattern,
                F1<FIELD, T1> fValidationMessageMustBeReference,
                F1<FIELD, T1> fValidationMessageMustBeUnique,
                F2<FIELD, NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>>, T1> fValidationMessageNonEmptyList) {

            requireNonNull(fValidationMessageMustBeDistinct);
            requireNonNull(fValidationMessageMustBeElementNonNull);
            requireNonNull(fValidationMessageMustBeEmpty);
            requireNonNull(fValidationMessageMustBeEqual);
            requireNonNull(fValidationMessageMustBeLengthGreaterThanOrEqual);
            requireNonNull(fValidationMessageMustBeLengthLessThanOrEqual);
            requireNonNull(fValidationMessageMustBeNonNull);
            requireNonNull(fValidationMessageMustBeNumeric);
            requireNonNull(fValidationMessageMustBePattern);
            requireNonNull(fValidationMessageMustBeReference);
            requireNonNull(fValidationMessageMustBeUnique);
            requireNonNull(fValidationMessageNonEmptyList);

            return fValidationMessageMustBeElementNonNull.f(field, indexNonEmptyList);
        }
    }

    public static final class ValidationMessageMustBeEmpty<FIELD> extends ValidationMessage<FIELD> {

        private final FIELD field;
        private final int size;

        public ValidationMessageMustBeEmpty(
                final FIELD field,
                final int size) {
            this.field = field;
            this.size = size;
        }

        public FIELD getField() {
            return field;
        }

        public int getSize() {
            return size;
        }

        public <T1> T1 match(
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeDistinct,
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeElementNonNull,
                F2<FIELD, Integer, T1> fValidationMessageMustBeEmpty,
                F3<FIELD, FIELD, FIELD, T1> fValidationMessageMustBeEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthGreaterThanOrEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthLessThanOrEqual,
                F1<FIELD, T1> fValidationMessageMustBeNonNull,
                F1<FIELD, T1> fValidationMessageMustBeNumeric,
                F2<FIELD, String, T1> fValidationMessageMustBePattern,
                F1<FIELD, T1> fValidationMessageMustBeReference,
                F1<FIELD, T1> fValidationMessageMustBeUnique,
                F2<FIELD, NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>>, T1> fValidationMessageNonEmptyList) {

            requireNonNull(fValidationMessageMustBeDistinct);
            requireNonNull(fValidationMessageMustBeElementNonNull);
            requireNonNull(fValidationMessageMustBeEmpty);
            requireNonNull(fValidationMessageMustBeEqual);
            requireNonNull(fValidationMessageMustBeLengthGreaterThanOrEqual);
            requireNonNull(fValidationMessageMustBeLengthLessThanOrEqual);
            requireNonNull(fValidationMessageMustBeNonNull);
            requireNonNull(fValidationMessageMustBeNumeric);
            requireNonNull(fValidationMessageMustBePattern);
            requireNonNull(fValidationMessageMustBeReference);
            requireNonNull(fValidationMessageMustBeUnique);
            requireNonNull(fValidationMessageNonEmptyList);

            return fValidationMessageMustBeEmpty.f(field, size);
        }
    }

    public static final class ValidationMessageMustBeEqual<FIELD> extends ValidationMessage<FIELD> {

        private final FIELD field1;
        private final FIELD field2;

        public ValidationMessageMustBeEqual(final FIELD field1, final FIELD field2) {
            this.field1 = field1;
            this.field2 = field2;
        }

        public FIELD getField() {
            return field1;
        }

        public FIELD getField1() {
            return field1;
        }

        public FIELD getField2() {
            return field2;
        }

        public <T1> T1 match(
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeDistinct,
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeElementNonNull,
                F2<FIELD, Integer, T1> fValidationMessageMustBeEmpty,
                F3<FIELD, FIELD, FIELD, T1> fValidationMessageMustBeEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthGreaterThanOrEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthLessThanOrEqual,
                F1<FIELD, T1> fValidationMessageMustBeNonNull,
                F1<FIELD, T1> fValidationMessageMustBeNumeric,
                F2<FIELD, String, T1> fValidationMessageMustBePattern,
                F1<FIELD, T1> fValidationMessageMustBeReference,
                F1<FIELD, T1> fValidationMessageMustBeUnique,
                F2<FIELD, NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>>, T1> fValidationMessageNonEmptyList) {

            requireNonNull(fValidationMessageMustBeDistinct);
            requireNonNull(fValidationMessageMustBeElementNonNull);
            requireNonNull(fValidationMessageMustBeEmpty);
            requireNonNull(fValidationMessageMustBeEqual);
            requireNonNull(fValidationMessageMustBeLengthGreaterThanOrEqual);
            requireNonNull(fValidationMessageMustBeLengthLessThanOrEqual);
            requireNonNull(fValidationMessageMustBeNonNull);
            requireNonNull(fValidationMessageMustBeNumeric);
            requireNonNull(fValidationMessageMustBePattern);
            requireNonNull(fValidationMessageMustBeReference);
            requireNonNull(fValidationMessageMustBeUnique);
            requireNonNull(fValidationMessageNonEmptyList);

            return fValidationMessageMustBeEqual.f(field1, field1, field2);
        }
    }

    public static final class ValidationMessageMustBeLengthGreaterThanOrEqual<FIELD> extends ValidationMessage<FIELD> {

        private final FIELD field;
        private final int lengthMinimumInclusive;
        private final int length;

        public ValidationMessageMustBeLengthGreaterThanOrEqual(
                final FIELD field,
                final int lengthMinimumInclusive,
                final int length) {
            this.field = field;
            this.lengthMinimumInclusive = lengthMinimumInclusive;
            this.length = length;
        }

        public FIELD getField() {
            return field;
        }

        public int getLengthMinimumInclusive() {
            return lengthMinimumInclusive;
        }

        public int getLength() {
            return length;
        }

        public <T1> T1 match(
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeDistinct,
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeElementNonNull,
                F2<FIELD, Integer, T1> fValidationMessageMustBeEmpty,
                F3<FIELD, FIELD, FIELD, T1> fValidationMessageMustBeEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthGreaterThanOrEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthLessThanOrEqual,
                F1<FIELD, T1> fValidationMessageMustBeNonNull,
                F1<FIELD, T1> fValidationMessageMustBeNumeric,
                F2<FIELD, String, T1> fValidationMessageMustBePattern,
                F1<FIELD, T1> fValidationMessageMustBeReference,
                F1<FIELD, T1> fValidationMessageMustBeUnique,
                F2<FIELD, NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>>, T1> fValidationMessageNonEmptyList) {

            requireNonNull(fValidationMessageMustBeDistinct);
            requireNonNull(fValidationMessageMustBeElementNonNull);
            requireNonNull(fValidationMessageMustBeEmpty);
            requireNonNull(fValidationMessageMustBeEqual);
            requireNonNull(fValidationMessageMustBeLengthGreaterThanOrEqual);
            requireNonNull(fValidationMessageMustBeLengthLessThanOrEqual);
            requireNonNull(fValidationMessageMustBeNonNull);
            requireNonNull(fValidationMessageMustBeNumeric);
            requireNonNull(fValidationMessageMustBePattern);
            requireNonNull(fValidationMessageMustBeReference);
            requireNonNull(fValidationMessageMustBeUnique);
            requireNonNull(fValidationMessageNonEmptyList);

            return fValidationMessageMustBeLengthGreaterThanOrEqual.f(field, lengthMinimumInclusive, length);
        }
    }

    public static final class ValidationMessageMustBeLengthLessThanOrEqual<FIELD> extends ValidationMessage<FIELD> {

        private final FIELD field;
        private final int lengthMaximumInclusive;
        private final int length;

        public ValidationMessageMustBeLengthLessThanOrEqual(
                final FIELD field,
                final int lengthMaximumInclusive,
                final int length) {
            this.field = field;
            this.lengthMaximumInclusive = lengthMaximumInclusive;
            this.length = length;
        }

        public FIELD getField() {
            return field;
        }

        public int getLengthMaximumInclusive() {
            return lengthMaximumInclusive;
        }

        public int getLength() {
            return length;
        }

        public <T1> T1 match(
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeDistinct,
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeElementNonNull,
                F2<FIELD, Integer, T1> fValidationMessageMustBeEmpty,
                F3<FIELD, FIELD, FIELD, T1> fValidationMessageMustBeEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthGreaterThanOrEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthLessThanOrEqual,
                F1<FIELD, T1> fValidationMessageMustBeNonNull,
                F1<FIELD, T1> fValidationMessageMustBeNumeric,
                F2<FIELD, String, T1> fValidationMessageMustBePattern,
                F1<FIELD, T1> fValidationMessageMustBeReference,
                F1<FIELD, T1> fValidationMessageMustBeUnique,
                F2<FIELD, NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>>, T1> fValidationMessageNonEmptyList) {

            requireNonNull(fValidationMessageMustBeDistinct);
            requireNonNull(fValidationMessageMustBeElementNonNull);
            requireNonNull(fValidationMessageMustBeEmpty);
            requireNonNull(fValidationMessageMustBeEqual);
            requireNonNull(fValidationMessageMustBeLengthGreaterThanOrEqual);
            requireNonNull(fValidationMessageMustBeLengthLessThanOrEqual);
            requireNonNull(fValidationMessageMustBeNonNull);
            requireNonNull(fValidationMessageMustBeNumeric);
            requireNonNull(fValidationMessageMustBePattern);
            requireNonNull(fValidationMessageMustBeReference);
            requireNonNull(fValidationMessageMustBeUnique);
            requireNonNull(fValidationMessageNonEmptyList);

            return fValidationMessageMustBeLengthLessThanOrEqual.f(field, lengthMaximumInclusive, length);
        }
    }

    public static final class ValidationMessageMustBeNonNull<FIELD> extends ValidationMessage<FIELD> {

        private final FIELD field;

        public ValidationMessageMustBeNonNull(
                final FIELD field) {
            this.field = field;
        }

        public FIELD getField() {
            return field;
        }

        public <T1> T1 match(
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeDistinct,
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeElementNonNull,
                F2<FIELD, Integer, T1> fValidationMessageMustBeEmpty,
                F3<FIELD, FIELD, FIELD, T1> fValidationMessageMustBeEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthGreaterThanOrEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthLessThanOrEqual,
                F1<FIELD, T1> fValidationMessageMustBeNonNull,
                F1<FIELD, T1> fValidationMessageMustBeNumeric,
                F2<FIELD, String, T1> fValidationMessageMustBePattern,
                F1<FIELD, T1> fValidationMessageMustBeReference,
                F1<FIELD, T1> fValidationMessageMustBeUnique,
                F2<FIELD, NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>>, T1> fValidationMessageNonEmptyList) {

            requireNonNull(fValidationMessageMustBeDistinct);
            requireNonNull(fValidationMessageMustBeElementNonNull);
            requireNonNull(fValidationMessageMustBeEmpty);
            requireNonNull(fValidationMessageMustBeEqual);
            requireNonNull(fValidationMessageMustBeLengthGreaterThanOrEqual);
            requireNonNull(fValidationMessageMustBeLengthLessThanOrEqual);
            requireNonNull(fValidationMessageMustBeNonNull);
            requireNonNull(fValidationMessageMustBeNumeric);
            requireNonNull(fValidationMessageMustBePattern);
            requireNonNull(fValidationMessageMustBeReference);
            requireNonNull(fValidationMessageMustBeUnique);
            requireNonNull(fValidationMessageNonEmptyList);

            return fValidationMessageMustBeNonNull.f(field);
        }
    }

    public static final class ValidationMessageMustBeNumeric<FIELD> extends ValidationMessage<FIELD> {

        private final FIELD field;

        public ValidationMessageMustBeNumeric(
                final FIELD field) {
            this.field = field;
        }

        public FIELD getField() {
            return field;
        }

        public <T1> T1 match(
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeDistinct,
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeElementNonNull,
                F2<FIELD, Integer, T1> fValidationMessageMustBeEmpty,
                F3<FIELD, FIELD, FIELD, T1> fValidationMessageMustBeEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthGreaterThanOrEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthLessThanOrEqual,
                F1<FIELD, T1> fValidationMessageMustBeNonNull,
                F1<FIELD, T1> fValidationMessageMustBeNumeric,
                F2<FIELD, String, T1> fValidationMessageMustBePattern,
                F1<FIELD, T1> fValidationMessageMustBeReference,
                F1<FIELD, T1> fValidationMessageMustBeUnique,
                F2<FIELD, NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>>, T1> fValidationMessageNonEmptyList) {

            requireNonNull(fValidationMessageMustBeDistinct);
            requireNonNull(fValidationMessageMustBeElementNonNull);
            requireNonNull(fValidationMessageMustBeEmpty);
            requireNonNull(fValidationMessageMustBeEqual);
            requireNonNull(fValidationMessageMustBeLengthGreaterThanOrEqual);
            requireNonNull(fValidationMessageMustBeLengthLessThanOrEqual);
            requireNonNull(fValidationMessageMustBeNonNull);
            requireNonNull(fValidationMessageMustBeNumeric);
            requireNonNull(fValidationMessageMustBePattern);
            requireNonNull(fValidationMessageMustBeReference);
            requireNonNull(fValidationMessageMustBeUnique);
            requireNonNull(fValidationMessageNonEmptyList);

            return fValidationMessageMustBeNonNull.f(field);
        }
    }

    public static final class ValidationMessageMustBePattern<FIELD> extends ValidationMessage<FIELD> {

        private final FIELD field;
        private final String pattern;

        public ValidationMessageMustBePattern(
                final FIELD field,
                final String pattern) {
            this.field = field;
            this.pattern = pattern;
        }

        public FIELD getField() {
            return field;
        }

        public String getPattern() {
            return pattern;
        }

        public <T1> T1 match(
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeDistinct,
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeElementNonNull,
                F2<FIELD, Integer, T1> fValidationMessageMustBeEmpty,
                F3<FIELD, FIELD, FIELD, T1> fValidationMessageMustBeEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthGreaterThanOrEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthLessThanOrEqual,
                F1<FIELD, T1> fValidationMessageMustBeNonNull,
                F1<FIELD, T1> fValidationMessageMustBeNumeric,
                F2<FIELD, String, T1> fValidationMessageMustBePattern,
                F1<FIELD, T1> fValidationMessageMustBeReference,
                F1<FIELD, T1> fValidationMessageMustBeUnique,
                F2<FIELD, NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>>, T1> fValidationMessageNonEmptyList) {

            requireNonNull(fValidationMessageMustBeDistinct);
            requireNonNull(fValidationMessageMustBeElementNonNull);
            requireNonNull(fValidationMessageMustBeEmpty);
            requireNonNull(fValidationMessageMustBeEqual);
            requireNonNull(fValidationMessageMustBeLengthGreaterThanOrEqual);
            requireNonNull(fValidationMessageMustBeLengthLessThanOrEqual);
            requireNonNull(fValidationMessageMustBeNonNull);
            requireNonNull(fValidationMessageMustBeNumeric);
            requireNonNull(fValidationMessageMustBePattern);
            requireNonNull(fValidationMessageMustBeReference);
            requireNonNull(fValidationMessageMustBeUnique);
            requireNonNull(fValidationMessageNonEmptyList);

            return fValidationMessageMustBeNonNull.f(field);
        }
    }

    public static final class ValidationMessageMustBeReference<FIELD> extends ValidationMessage<FIELD> {

        private final FIELD field;

        public ValidationMessageMustBeReference(
                final FIELD field) {
            this.field = field;
        }

        public FIELD getField() {
            return field;
        }

        public <T1> T1 match(
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeDistinct,
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeElementNonNull,
                F2<FIELD, Integer, T1> fValidationMessageMustBeEmpty,
                F3<FIELD, FIELD, FIELD, T1> fValidationMessageMustBeEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthGreaterThanOrEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthLessThanOrEqual,
                F1<FIELD, T1> fValidationMessageMustBeNonNull,
                F1<FIELD, T1> fValidationMessageMustBeNumeric,
                F2<FIELD, String, T1> fValidationMessageMustBePattern,
                F1<FIELD, T1> fValidationMessageMustBeReference,
                F1<FIELD, T1> fValidationMessageMustBeUnique,
                F2<FIELD, NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>>, T1> fValidationMessageNonEmptyList) {

            requireNonNull(fValidationMessageMustBeDistinct);
            requireNonNull(fValidationMessageMustBeElementNonNull);
            requireNonNull(fValidationMessageMustBeEmpty);
            requireNonNull(fValidationMessageMustBeEqual);
            requireNonNull(fValidationMessageMustBeLengthGreaterThanOrEqual);
            requireNonNull(fValidationMessageMustBeLengthLessThanOrEqual);
            requireNonNull(fValidationMessageMustBeNonNull);
            requireNonNull(fValidationMessageMustBeNumeric);
            requireNonNull(fValidationMessageMustBePattern);
            requireNonNull(fValidationMessageMustBeReference);
            requireNonNull(fValidationMessageMustBeUnique);
            requireNonNull(fValidationMessageNonEmptyList);

            return fValidationMessageMustBeReference.f(field);
        }
    }

    public static final class ValidationMessageMustBeUnique<FIELD> extends ValidationMessage<FIELD> {

        private final FIELD field;

        public ValidationMessageMustBeUnique(
                final FIELD field) {
            this.field = field;
        }

        public FIELD getField() {
            return field;
        }

        public <T1> T1 match(
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeDistinct,
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeElementNonNull,
                F2<FIELD, Integer, T1> fValidationMessageMustBeEmpty,
                F3<FIELD, FIELD, FIELD, T1> fValidationMessageMustBeEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthGreaterThanOrEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthLessThanOrEqual,
                F1<FIELD, T1> fValidationMessageMustBeNonNull,
                F1<FIELD, T1> fValidationMessageMustBeNumeric,
                F2<FIELD, String, T1> fValidationMessageMustBePattern,
                F1<FIELD, T1> fValidationMessageMustBeReference,
                F1<FIELD, T1> fValidationMessageMustBeUnique,
                F2<FIELD, NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>>, T1> fValidationMessageNonEmptyList) {

            requireNonNull(fValidationMessageMustBeDistinct);
            requireNonNull(fValidationMessageMustBeElementNonNull);
            requireNonNull(fValidationMessageMustBeEmpty);
            requireNonNull(fValidationMessageMustBeEqual);
            requireNonNull(fValidationMessageMustBeLengthGreaterThanOrEqual);
            requireNonNull(fValidationMessageMustBeLengthLessThanOrEqual);
            requireNonNull(fValidationMessageMustBeNonNull);
            requireNonNull(fValidationMessageMustBeNumeric);
            requireNonNull(fValidationMessageMustBePattern);
            requireNonNull(fValidationMessageMustBeReference);
            requireNonNull(fValidationMessageMustBeUnique);
            requireNonNull(fValidationMessageNonEmptyList);

            return fValidationMessageMustBeUnique.f(field);
        }
    }

    public static final class ValidationMessageNonEmptyList<FIELD> extends ValidationMessage<FIELD> {

        private final FIELD field;
        private final NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>> validationMessageNonEmptyList;

        public ValidationMessageNonEmptyList(
                final FIELD field,
                final NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>> validationMessageNonEmptyList) {
            this.field = field;
            this.validationMessageNonEmptyList = validationMessageNonEmptyList;
        }

        public FIELD getField() {
            return field;
        }

        public NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>> getValidationMessageNonEmptyList() {
            return validationMessageNonEmptyList;
        }

        public <T1> T1 match(
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeDistinct,
                F2<FIELD, NonEmptyList<Integer>, T1> fValidationMessageMustBeElementNonNull,
                F2<FIELD, Integer, T1> fValidationMessageMustBeEmpty,
                F3<FIELD, FIELD, FIELD, T1> fValidationMessageMustBeEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthGreaterThanOrEqual,
                F3<FIELD, Integer, Integer, T1> fValidationMessageMustBeLengthLessThanOrEqual,
                F1<FIELD, T1> fValidationMessageMustBeNonNull,
                F1<FIELD, T1> fValidationMessageMustBeNumeric,
                F2<FIELD, String, T1> fValidationMessageMustBePattern,
                F1<FIELD, T1> fValidationMessageMustBeReference,
                F1<FIELD, T1> fValidationMessageMustBeUnique,
                F2<FIELD, NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>>, T1> fValidationMessageNonEmptyList) {

            requireNonNull(fValidationMessageMustBeDistinct);
            requireNonNull(fValidationMessageMustBeElementNonNull);
            requireNonNull(fValidationMessageMustBeEmpty);
            requireNonNull(fValidationMessageMustBeEqual);
            requireNonNull(fValidationMessageMustBeLengthGreaterThanOrEqual);
            requireNonNull(fValidationMessageMustBeLengthLessThanOrEqual);
            requireNonNull(fValidationMessageMustBeNonNull);
            requireNonNull(fValidationMessageMustBeNumeric);
            requireNonNull(fValidationMessageMustBePattern);
            requireNonNull(fValidationMessageMustBeReference);
            requireNonNull(fValidationMessageMustBeUnique);
            requireNonNull(fValidationMessageNonEmptyList);

            return fValidationMessageNonEmptyList.f(field, validationMessageNonEmptyList);
        }
    }

    public static <FIELD> ValidationMessageMustBeDistinct<FIELD> validationMessageMustBeDistinct(
            final FIELD field,
            final NonEmptyList<Integer> indexNonEmptyList) {

        return new ValidationMessageMustBeDistinct<>(field, indexNonEmptyList);
    }

    public static <FIELD> ValidationMessageMustBeElementNonNull<FIELD> validationMessageMustBeElementNonNull(
            final FIELD field,
            final NonEmptyList<Integer> indexNonEmptyList) {

        return new ValidationMessageMustBeElementNonNull<>(field, indexNonEmptyList);
    }

    public static <FIELD> ValidationMessageMustBeEmpty<FIELD> validationMessageMustBeEmpty(
            final FIELD field,
            final int size) {

        return new ValidationMessageMustBeEmpty<>(field, size);
    }

    public static <FIELD> ValidationMessageMustBeEqual<FIELD> validationMessageMustBeEqual(
            final FIELD field1,
            final FIELD field2) {

        return new ValidationMessageMustBeEqual<>(field1, field2);
    }

    public static <FIELD> ValidationMessageMustBeLengthGreaterThanOrEqual<FIELD> validationMessageMustBeLengthGreaterThanOrEqual(
            final FIELD field,
            final int lengthMinimumInclusive,
            final int length) {

        return new ValidationMessageMustBeLengthGreaterThanOrEqual<>(field, lengthMinimumInclusive, length);
    }

    public static <FIELD> ValidationMessageMustBeLengthLessThanOrEqual<FIELD> validationMessageMustBeLengthLessThanOrEqual(
            final FIELD field,
            final int lengthMaximumInclusive,
            final int length) {

        return new ValidationMessageMustBeLengthLessThanOrEqual<>(field, lengthMaximumInclusive, length);
    }

    public static <FIELD> ValidationMessageMustBeNonNull<FIELD> validationMessageMustBeNonNull(
            final FIELD field) {

        return new ValidationMessageMustBeNonNull<>(field);
    }

    public static <FIELD> ValidationMessageMustBeNumeric<FIELD> validationMessageMustBeNumeric(
            final FIELD field) {

        return new ValidationMessageMustBeNumeric<>(field);
    }

    public static <FIELD> ValidationMessageMustBePattern<FIELD> validationMessageMustBePattern(
            final FIELD field,
            final String pattern) {

        return new ValidationMessageMustBePattern<>(field, pattern);
    }

    public static <FIELD> ValidationMessageMustBeReference<FIELD> validationMessageMustBeReference(
            final FIELD field) {

        return new ValidationMessageMustBeReference<>(field);
    }

    public static <FIELD> ValidationMessageMustBeUnique<FIELD> validationMessageMustBeUnique(
            final FIELD field) {

        return new ValidationMessageMustBeUnique<>(field);
    }

    public static <FIELD> ValidationMessageNonEmptyList<FIELD> validationMessageNonEmptyList(
            final FIELD field,
            final NonEmptyList<P2<NonEmptyList<ValidationMessage<FIELD>>, Integer>> validationMessageNonEmptyList) {

        return new ValidationMessageNonEmptyList<>(field, validationMessageNonEmptyList);
    }
}
