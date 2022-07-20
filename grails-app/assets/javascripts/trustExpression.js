function generator(prefix = "id-", index = 0) {
    return {
        next: function () {
            return prefix + index++
        }
    }
}

function trustExpressionAll(
    trustInteroperabilityProfileParentURI,
    trustExpression,
    evaluationLocalDateTime,
    generator) {

    return matchTrustExpressionClassName(
        trustExpression["$Type"],
        () => trustExpressionUnary(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionUnary(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionUnary(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionBinary(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionBinary(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionBinary(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionBinary(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionBinary(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionBinary(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionBinary(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionBinary(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionBinary(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionTerminal(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionTerminal(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionTerminal(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionTerminal(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionTerminal(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionTerminal(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => trustExpressionFailure(trustInteroperabilityProfileParentURI, trustExpression, evaluationLocalDateTime, generator),
        () => ""
    )
}

function trustExpressionBinary(
    trustInteroperabilityProfileParentURI,
    trustExpression,
    evaluationLocalDateTime,
    generator) {

    function trustExpressionOperatorBinaryDiv(
        trustInteroperabilityProfileURI,
        trustExpression,
        evaluationLocalDateTime,
        generator) {

        return div({"class": "TrustExpressionOperatorBinary"},
            div(
                div(
                    matchTrustExpressionOperatorBinary(
                        trustExpression["$Type"],
                        () => "AND",
                        () => "OR",
                        () => "&lt;",
                        () => "&lt;=",
                        () => "&gt;=",
                        () => "&gt;",
                        () => "==",
                        () => "!=",
                        () => "CONTAINS",
                        () => "?"))),
            div(
                trustExpression["TrustExpression"].map(trustExpressionInner => trustExpressionAll(
                    trustInteroperabilityProfileURI,
                    trustExpressionInner,
                    evaluationLocalDateTime,
                    generator))))
    }

    return trustExpression["TrustExpressionData"].hasOwnProperty("TrustExpressionFailureList") ?
        trustExpressionDiv(
            trustInteroperabilityProfileParentURI,
            trustExpression["TrustExpressionData"]["TrustExpressionFailureList"][0]["TrustInteroperabilityProfileList"][0]["Identifier"],
            trustExpression["TrustExpressionData"]["TrustExpressionFailureList"][0]["TrustInteroperabilityProfileList"][0]["Name"],
            trustExpression,
            "FAILURE",
            evaluationLocalDateTime,
            generator,
            trustExpressionOperatorBinaryDiv(
                trustExpression["TrustExpressionData"]["TrustExpressionFailureList"][0]["TrustInteroperabilityProfileList"][0]["Identifier"],
                trustExpression,
                evaluationLocalDateTime,
                generator)) :
        trustExpressionDiv(
            trustInteroperabilityProfileParentURI,
            trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Identifier"],
            trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Name"],
            trustExpression,
            trustExpressionEvaluatorStateClassName(trustInteroperabilityProfileParentURI, trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataType"], trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataValue"]),
            evaluationLocalDateTime,
            generator,
            trustExpressionOperatorBinaryDiv(
                trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Identifier"],
                trustExpression,
                evaluationLocalDateTime,
                generator))
}

function trustExpressionUnary(
    trustInteroperabilityProfileParentURI,
    trustExpression,
    evaluationLocalDateTime,
    generator) {

    function trustExpressionOperatorUnaryDiv(
        trustInteroperabilityProfileURI,
        trustExpression,
        evaluationLocalDateTime,
        generator) {

        return div({"class": "TrustExpressionOperatorUnary"},
            div(
                div(
                    matchTrustExpressionOperatorUnary(
                        trustExpression["$Type"],
                        () => "",
                        () => "NOT",
                        () => "EXISTS",
                        () => "?"))),
            div(
                trustExpressionAll(
                    trustInteroperabilityProfileURI,
                    trustExpression["TrustExpression"],
                    evaluationLocalDateTime,
                    generator)))
    }

    // trustExpression["$Type"] === "TrustExpressionOperatorNoop" && trustInteroperabilityProfileParentURI === trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Identifier"] ?
    //
    //     trustExpressionAll(
    //         trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Identifier"],
    //         trustExpression["TrustExpression"],
    //         evaluationLocalDateTime,
    //         generator) :
    //
    //
    //     trustExpressionDiv(
    //         trustInteroperabilityProfileParentURI,
    //         trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Identifier"],
    //         trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Name"],
    //         trustExpression,
    //         trustExpressionEvaluatorStateClassName(trustInteroperabilityProfileParentURI, trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataType"], trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataValue"]),
    //         evaluationLocalDateTime,
    //         generator,
    //         trustExpressionOperatorUnaryDiv(
    //             trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Identifier"],
    //             trustExpression,
    //             evaluationLocalDateTime,
    //             generator)) :

    return trustExpression["TrustExpressionData"].hasOwnProperty("TrustExpressionFailureList") ?

        trustExpression["$Type"] === "TrustExpressionOperatorNoop" ?

            trustExpressionAll(
                trustExpression["TrustExpressionData"]["TrustExpressionFailureList"][0]["TrustInteroperabilityProfileList"][0]["Identifier"],
                trustExpression["TrustExpression"],
                evaluationLocalDateTime,
                generator) :

            trustExpressionDiv(
                trustInteroperabilityProfileParentURI,
                trustExpression["TrustExpressionData"]["TrustExpressionFailureList"][0]["TrustInteroperabilityProfileList"][0]["Identifier"],
                trustExpression["TrustExpressionData"]["TrustExpressionFailureList"][0]["TrustInteroperabilityProfileList"][0]["Name"],
                trustExpression,
                "FAILURE",
                evaluationLocalDateTime,
                generator,
                trustExpressionOperatorUnaryDiv(
                    trustExpression["TrustExpressionData"]["TrustExpressionFailureList"][0]["TrustInteroperabilityProfileList"][0]["Identifier"],
                    trustExpression,
                    evaluationLocalDateTime,
                    generator)) :

        trustExpression["$Type"] === "TrustExpressionOperatorNoop" && trustInteroperabilityProfileParentURI === trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Identifier"] ?

            trustExpressionAll(
                trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Identifier"],
                trustExpression["TrustExpression"],
                evaluationLocalDateTime,
                generator) :

            trustExpressionDiv(
                trustInteroperabilityProfileParentURI,
                trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Identifier"],
                trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Name"],
                trustExpression,
                trustExpressionEvaluatorStateClassName(trustInteroperabilityProfileParentURI, trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataType"], trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataValue"]),
                evaluationLocalDateTime,
                generator,
                trustExpressionOperatorUnaryDiv(
                    trustExpression["TrustExpressionData"]["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Identifier"],
                    trustExpression,
                    evaluationLocalDateTime,
                    generator))
}

function trustExpressionTerminal(
    trustInteroperabilityProfileParentURI,
    trustExpression,
    evaluationLocalDateTime,
    generator) {

    function sentenceForTrustExpressionEvaluatorDataType(trustExpressionEvaluatorDataType) {

        return matchTrustExpressionType(
            trustExpressionEvaluatorDataType,
            () => "A literal boolean.",
            () => "A literal date-time.",
            () => "A literal decimal.",
            () => "A literal string.",
            () => "A literal string list.",
            () => "A literal none.",
            () => "")
    }

    function typeNameForTrustExpressionEvaluatorDataType(trustExpressionEvaluatorDataType) {

        return matchTrustExpressionType(
            trustExpressionEvaluatorDataType,
            () => "Boolean",
            () => "Date-Time",
            () => "Decimal",
            () => "String",
            () => "String List",
            () => "None",
            () => "?")
    }

    function valueForTrustExpressionEvaluatorDataValue(trustExpressionEvaluatorDataType, trustExpressionEvaluatorDataValue) {
        return matchTrustExpressionType(
            trustExpressionEvaluatorDataType,
            () => `${trustExpressionEvaluatorDataValue}`,
            () => `${trustExpressionEvaluatorDataValue}`,
            () => `${trustExpressionEvaluatorDataValue}`,
            () => `"${trustExpressionEvaluatorDataValue}"`,
            () => matchArrayLength(
                trustExpressionEvaluatorDataValue,
                () => `()`,
                (element) => `("${element}")`,
                (array) => array.map((it, index) =>
                    index === 0 ?
                        `("${it}"` :
                        index === array.length - 1 ?
                            `, "${it}")` :
                            `, "${it}"`).join(``)),
            () => `none`,
            () => ``)
    }

    function phraseForTrustExpressionEvaluatorDataValue(trustExpressionEvaluatorDataType, trustExpressionEvaluatorDataValue) {
        return matchTrustExpressionType(
            trustExpressionEvaluatorDataType,
            () => `boolean: ${trustExpressionEvaluatorDataValue}.`,
            () => `date time stamp: ${trustExpressionEvaluatorDataValue}.`,
            () => `decimal: ${trustExpressionEvaluatorDataValue}.`,
            () => `string: "${trustExpressionEvaluatorDataValue}".`,
            () => matchArrayLength(
                trustExpressionEvaluatorDataValue,
                () => `list of strings; the list is empty.`,
                (element) => `list of strings: "${element}".`,
                (array) => `list of strings: <ul>` + array.map(it => `<li>"${it}"</li>`).join(``) + `</ul>`),
            () => `none.`,
            () => ``)
    }

    function trustExpressionTerminalForTrustExpressionTerminalDiv(
        trustInteroperabilityProfileParentURI,
        trustInteroperabilityProfileURI,
        trustExpression,
        evaluationLocalDateTime,
        generator) {

        function trustExpressionEvaluatorSourceValue(
            trustExpressionEvaluatorDataType,
            trustExpressionEvaluatorDataValue,
            trustInteroperabilityProfileList,
            id) {

            return [
                label({for: id, title: sentenceForTrustExpressionEvaluatorDataType(trustExpressionEvaluatorDataType)},
                    span({class: "glyphicon bi-chat-left"}),
                    valueForTrustExpressionEvaluatorDataValue(trustExpressionEvaluatorDataType, trustExpressionEvaluatorDataValue)),
                div({class: "TrustExpressionDetail"},
                    div({class: "Head"}, "Evaluation: ", typeNameForTrustExpressionEvaluatorDataType(trustExpressionEvaluatorDataType)),
                    div("A literal ", phraseForTrustExpressionEvaluatorDataValue(trustExpressionEvaluatorDataType, trustExpressionEvaluatorDataValue)),
                    div({class: "Head"}, "Trust Interoperability Profile Trace"),
                    trustInteroperabilityProfileReferenceListBody(trustInteroperabilityProfileList))
            ]
        }

        function trustExpressionEvaluatorSourceTrustmarkDefinitionRequirement(
            trustExpressionEvaluatorDataSource,
            trustInteroperabilityProfileList,
            id) {

            const trustmarkDefinitionRequirementName = trustExpressionEvaluatorDataSource["TrustmarkDefinitionRequirement"]["Name"]
            const trustmarkList = trustExpressionEvaluatorDataSource["TrustmarkList"]
            const trustmarkVerifierFailureList = trustExpressionEvaluatorDataSource["TrustmarkVerifierFailureList"]

            return [
                label({for: id, title: trustmarkList.length === 0 ? `The trustmark &quot;${trustmarkDefinitionRequirementName}&quot; does not appear to be bound to the candidate system.` : "The candidate system satisfies the trustmark requirement."},
                    span({class: "glyphicon bi-tag"}), trustmarkDefinitionRequirementName),
                div({class: "TrustExpressionDetail"},
                    trustmarkList.length === 0 ?
                        div({class: "Head"}, "Evaluation: False") :
                        div({class: "Head"}, "Evaluation: True"),
                    trustmarkList.length === 0 && trustmarkVerifierFailureList.length === 0 ?
                        div(`The trustmark "${trustmarkDefinitionRequirementName}" does not appear to be bound to the candidate system.`) :
                        trustmarkList.length === 0 && trustmarkVerifierFailureList.length !== 0 ? [
                            div(`The trustmark "${trustmarkDefinitionRequirementName}" was bound to the candidate system, but the trustmark relying party tool could not verify the trustmark.`),
                            ul(trustmarkVerifierFailureList.map(trustmarkVerifierFailure => li(trustmarkVerifierFailure)))] : [
                            div("The candidate system satisfies the trustmark requirement."),
                            trustmarkList.map(trustmark => div({class: "code"}, a({target: "_blank", href: trustmark["Identifier"]}, trustmark["Identifier"]))).join(``)],
                    div({class: "Head"}, "Trust Interoperability Profile Trace"),
                    trustInteroperabilityProfileReferenceListBody(trustInteroperabilityProfileList))
            ]
        }

        function trustExpressionEvaluatorSourceTrustmarkDefinitionParameter(
            trustExpressionEvaluatorDataType,
            trustExpressionEvaluatorDataValue,
            trustExpressionEvaluatorDataSource,
            trustInteroperabilityProfileList,
            id) {

            return [
                label({for: id, title: "A trustmark parameter binding value."},
                    span({class: "glyphicon bi-tag"}),
                    trustExpressionEvaluatorDataSource["TrustmarkDefinitionRequirement"]["Name"], ": ", trustExpressionEvaluatorDataSource["TrustmarkDefinitionParameter"]),
                div({class: "TrustExpressionDetail"},
                    div({class: "Head"}, "Evaluation: ", typeNameForTrustExpressionEvaluatorDataType(trustExpressionEvaluatorDataType)),
                    div("The candidate system satisfies the trustmark requirement."),
                    trustExpressionEvaluatorDataSource["TrustmarkList"].map(trustmark => div({class: "code"}, a({target: "_blank", href: trustmark["Identifier"]}, trustmark["Identifier"]))),
                    div("The parameter ", trustExpressionEvaluatorDataSource["TrustmarkDefinitionParameter"], " is bound to a ", phraseForTrustExpressionEvaluatorDataValue(trustExpressionEvaluatorDataType, trustExpressionEvaluatorDataValue)),
                    div({class: "Head"}, "Trust Interoperability Profile Trace"),
                    trustInteroperabilityProfileReferenceListBody(trustInteroperabilityProfileList))
            ]
        }

        const id = generator.next()

        return div({class: "TrustExpressionTerminal"},
            input({type: "checkbox", id: id}),
            matchTrustExpressionEvaluatorSource(
                trustExpression["TrustExpressionEvaluatorDataSource"]["$Type"],
                () => trustExpressionEvaluatorSourceValue(
                    trustExpression["TrustExpressionEvaluatorDataType"],
                    trustExpression["TrustExpressionEvaluatorDataValue"],
                    trustExpression["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"],
                    id),
                () => trustExpressionEvaluatorSourceTrustmarkDefinitionRequirement(
                    trustExpression["TrustExpressionEvaluatorDataSource"],
                    trustExpression["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"],
                    id),
                () => trustExpressionEvaluatorSourceTrustmarkDefinitionParameter(
                    trustExpression["TrustExpressionEvaluatorDataType"],
                    trustExpression["TrustExpressionEvaluatorDataValue"],
                    trustExpression["TrustExpressionEvaluatorDataSource"],
                    trustExpression["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"],
                    id),
                () => ""))
    }

    return trustExpressionDiv(
        trustInteroperabilityProfileParentURI,
        trustExpression["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Identifier"],
        trustExpression["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Name"],
        trustExpression,
        trustExpressionEvaluatorStateClassName(trustInteroperabilityProfileParentURI, trustExpression["TrustExpressionEvaluatorDataType"], trustExpression["TrustExpressionEvaluatorDataValue"]),
        evaluationLocalDateTime,
        generator,
        trustExpressionTerminalForTrustExpressionTerminalDiv(
            trustInteroperabilityProfileParentURI,
            trustExpression["TrustExpressionEvaluatorDataSource"]["TrustInteroperabilityProfileList"][0]["Identifier"],
            trustExpression,
            evaluationLocalDateTime,
            generator))
}

function trustExpressionFailure(
    trustInteroperabilityProfileParentURI,
    trustExpression,
    evaluationLocalDateTime,
    generator) {

    function trustExpressionTerminalForTrustExpressionFailureDiv(
        trustInteroperabilityProfileURI,
        trustExpression,
        evaluationLocalDateTime,
        generator) {

        function trustExpressionFailureHelper(id, title, icon, labelContent, body, trustInteroperabilityProfileList) {

            return [
                label({for: id, title: title},
                    span({class: `glyphicon ${icon}`}), labelContent),
                div({class: "TrustExpressionDetail"},
                    div({class: "Head"}, "Failure"),
                    div(body),
                    trustInteroperabilityProfileReferenceListBody(trustInteroperabilityProfileList))]
        }

        function trustExpressionFailureResolveTrustInteroperabilityProfile(trustExpressionFailure, id) {

            return trustExpressionFailureHelper(
                id,
                trustExpressionFailure["Uri"],
                "bi-list-ul",
                trustExpressionFailure["Uri"],
                `The system could not resolve the Trust Interoperability Profile ("${trustExpressionFailure["Uri"]}"): ${trustExpressionFailure["Message"]}.`,
                trustExpressionFailure["TrustInteroperabilityProfileList"])
        }


        function trustExpressionFailureResolveTrustmarkDefinition(trustExpressionFailure, id) {

            return trustExpressionFailureHelper(
                id,
                trustExpressionFailure["Uri"],
                "bi-tag",
                trustExpressionFailure["Uri"],
                `The system could not resolve the Trustmark Definition ("${trustExpressionFailure["Uri"]}"): ${trustExpressionFailure["Message"]}.`,
                trustExpressionFailure["TrustInteroperabilityProfileList"])
        }

        function trustExpressionFailureTrustmarkAbsent(trustExpressionFailure, id) {

            return trustExpressionFailureHelper(
                id,
                `The trustmark &quot;${trustExpressionFailure["TrustmarkDefinitionRequirement"]["Name"]}&quot; does not appear to be bound to the candidate system; the parameter &quot;${trustExpressionFailure["TrustmarkDefinitionParameterIdentifier"]}&quot; does not appear to be bound to a value.`,
                "bi-tag",
                `${trustExpressionFailure["TrustmarkDefinitionRequirement"]["Name"]}: ${trustExpressionFailure["TrustmarkDefinitionParameterIdentifier"]}`,
                `The trustmark "${trustExpressionFailure["TrustmarkDefinitionRequirement"]["Name"]}" does not ap pear to be bound to the candidate system; the parameter "${trustExpressionFailure["TrustmarkDefinitionParameterIdentifier"]}" does not appear to be bound to a value.`,
                trustExpressionFailure["TrustInteroperabilityProfileList"])
        }

        function trustExpressionFailureTrustmarkVerifierFailure(trustExpressionFailure, id) {

            return trustExpressionFailureHelper(
                id,
                `The trustmark &quot${trustExpressionFailure["TrustmarkDefinitionRequirement"]["Name"]}&quot; is bound to the candidate system, but the trustmark relying party tool could not verify the trustmark.`,
                "bi-tag",
                trustExpressionFailure["TrustmarkDefinitionRequirement"]["Name"],
                `The trustmark "${trustExpressionFailure["TrustmarkDefinitionRequirement"]["Name"]}" is bound to the candidate system, but the trustmark relying party tool could not verify the trustmark.`,
                trustExpressionFailure["TrustInteroperabilityProfileList"])
                .append(ul(
                    trustExpressionFailure["TrustmarkVerifierFailureNonEmptyList"].map(trustmarkVerifierFailure =>
                        li(trustmarkVerifierFailure))))
        }

        function trustExpressionFailureOther(
            trustExpressionFailure,
            id) {

            return trustExpressionFailureHelper(
                id,
                trustExpressionFailure["$Type"],
                "bi-tag",
                trustExpressionFailure["$Type"],
                trustExpressionFailure["$Type"],
                trustExpressionFailure["TrustInteroperabilityProfileList"])
        }

        const id = generator.next()

        return div({class: "TrustExpressionTerminal"},
            input({type: "checkbox", id: id}),
            matchTrustExpressionFailure(
                trustExpression["TrustExpressionFailureList"][0]["$Type"],
                /* fTrustExpressionFailureURI                                              */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureResolveTrustInteroperabilityProfile              */ () => trustExpressionFailureResolveTrustInteroperabilityProfile(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureCycle                                            */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureResolveTrustmarkDefinition                       */ () => trustExpressionFailureResolveTrustmarkDefinition(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureParser                                           */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureIdentifierUnknown                                */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter    */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement  */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureNonTerminalUnexpected                            */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureTrustmarkAbsent                                  */ () => trustExpressionFailureTrustmarkAbsent(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureTrustmarkVerifierFailure                         */ () => trustExpressionFailureTrustmarkVerifierFailure(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureTypeUnexpected                                   */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureTypeUnexpectedLeft                               */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureTypeUnexpectedRight                              */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureTypeMismatch                                     */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureTypeUnorderableLeft                              */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureTypeUnorderableRight                             */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureExpression                                       */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureExpressionLeft                                   */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureExpressionRight                                  */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id),
                /* fTrustExpressionFailureOther                                            */ () => trustExpressionFailureOther(trustExpression["TrustExpressionFailureList"][0], id)))
    }

    return trustExpressionDiv(
        trustInteroperabilityProfileParentURI,
        trustExpression["TrustExpressionFailureList"][0]["TrustInteroperabilityProfileList"][0]["Identifier"],
        trustExpression["TrustExpressionFailureList"][0]["TrustInteroperabilityProfileList"][0]["Name"],
        trustExpression,
        "FAILURE",
        evaluationLocalDateTime,
        generator,
        trustExpressionTerminalForTrustExpressionFailureDiv(
            trustExpression["TrustExpressionFailureList"][0]["TrustInteroperabilityProfileList"][0]["Identifier"],
            trustExpression,
            evaluationLocalDateTime,
            generator))
}

function trustExpressionDiv(
    trustInteroperabilityProfileParentURI,
    trustInteroperabilityProfileURI,
    trustInteroperabilityProfileName,
    trustExpression,
    trustExpressionEvaluatorState,
    evaluationLocalDateTime,
    generator,
    content) {

    function trustExpressionClassName(trustInteroperabilityProfileParentURI, trustInteroperabilityProfileURI) {
        return trustInteroperabilityProfileParentURI === "" ?
            "TrustExpressionTop" :
            trustInteroperabilityProfileParentURI !== trustInteroperabilityProfileURI ?
                "TrustExpressionSub" :
                "TrustExpression"
    }

    const id = generator.next()

    return div({"class": [trustExpressionClassName(trustInteroperabilityProfileParentURI, trustInteroperabilityProfileURI), trustExpressionEvaluatorState].join(" ")},
        trustInteroperabilityProfileParentURI !== trustInteroperabilityProfileURI ? [
            input({type: "checkbox", id: id, checked: trustInteroperabilityProfileParentURI !== "" && trustExpressionEvaluatorState !== "TRUE" }),
            label({for: id, class: "TrustInteroperabilityProfileInner"},
                span({class: "glyphicon bi-list-ul"}),
                trustInteroperabilityProfileName,
                span({class: "EvaluationLocalDateTime"}, evaluationLocalDateTime))] : [],
        content)
}

function trustInteroperabilityProfileReferenceListBody(trustInteroperabilityProfileList) {

    return div({"class": "TrustInteroperabilityProfileReferenceList Body"},
        trustInteroperabilityProfileList.flatMap((trustInteroperabilityProfile, index) => [
            index === 0 ?
                div("Referenced in") :
                div("Referenced by"),
            div(
                a({target: "_blank", href: trustInteroperabilityProfile["Identifier"]},
                    trustInteroperabilityProfile.hasOwnProperty("Name") ?
                        trustInteroperabilityProfile["Name"] :
                        "(no name)"))]))
}

function trustExpressionEvaluatorStateClassName(
    trustInteroperabilityProfileParentURI,
    trustExpressionEvaluatorDataType,
    trustExpressionEvaluatorDataValue) {

    return trustInteroperabilityProfileParentURI === "" ?
        trustExpressionEvaluatorDataType === "TrustExpressionTypeBoolean" ?
            trustExpressionEvaluatorDataValue ?
                "TRUE" :
                "FALSE" :
            "FAILURE" :
        trustExpressionEvaluatorDataType === "TrustExpressionTypeBoolean" ?
            trustExpressionEvaluatorDataValue ?
                "TRUE" :
                "FALSE" :
            "UNKNOWN"
}

function matchTrustExpressionType(
    trustExpressionType,
    fTrustExpressionTypeBoolean,
    fTrustExpressionTypeDateTimeStamp,
    fTrustExpressionTypeDecimal,
    fTrustExpressionTypeString,
    fTrustExpressionTypeStringList,
    fTrustExpressionTypeNone,
    fTrustExpressionTypeOther) {

    return trustExpressionType === "TrustExpressionTypeBoolean" ? fTrustExpressionTypeBoolean() :
        trustExpressionType === "TrustExpressionTypeDateTimeStamp" ? fTrustExpressionTypeDateTimeStamp() :
            trustExpressionType === "TrustExpressionTypeDecimal" ? fTrustExpressionTypeDecimal() :
                trustExpressionType === "TrustExpressionTypeString" ? fTrustExpressionTypeString() :
                    trustExpressionType === "TrustExpressionTypeStringList" ? fTrustExpressionTypeStringList() :
                        trustExpressionType === "TrustExpressionTypeNone" ? fTrustExpressionTypeNone() :
                            fTrustExpressionTypeOther()
}

function matchTrustExpressionOperatorBinary(
    trustExpressionOperatorBinary,
    fTrustExpressionOperatorAnd,
    fTrustExpressionOperatorOr,
    fTrustExpressionOperatorLessThan,
    fTrustExpressionOperatorLessThanOrEqual,
    fTrustExpressionOperatorGreaterThanOrEqual,
    fTrustExpressionOperatorGreaterThan,
    fTrustExpressionOperatorEqual,
    fTrustExpressionOperatorNotEqual,
    fTrustExpressionOperatorContains,
    fTrustExpressionOperatorOther) {

    return trustExpressionOperatorBinary === "TrustExpressionOperatorAnd" ? fTrustExpressionOperatorAnd() :
        trustExpressionOperatorBinary === "TrustExpressionOperatorOr" ? fTrustExpressionOperatorOr() :
            trustExpressionOperatorBinary === "TrustExpressionOperatorLessThan" ? fTrustExpressionOperatorLessThan() :
                trustExpressionOperatorBinary === "TrustExpressionOperatorLessThanOrEqual" ? fTrustExpressionOperatorLessThanOrEqual() :
                    trustExpressionOperatorBinary === "TrustExpressionOperatorGreaterThanOrEqual" ? fTrustExpressionOperatorGreaterThanOrEqual() :
                        trustExpressionOperatorBinary === "TrustExpressionOperatorGreaterThan" ? fTrustExpressionOperatorGreaterThan() :
                            trustExpressionOperatorBinary === "TrustExpressionOperatorEqual" ? fTrustExpressionOperatorEqual() :
                                trustExpressionOperatorBinary === "TrustExpressionOperatorNotEqual" ? fTrustExpressionOperatorNotEqual() :
                                    trustExpressionOperatorBinary === "TrustExpressionOperatorContains" ? fTrustExpressionOperatorContains() :
                                        fTrustExpressionOperatorOther()
}

function matchTrustExpressionOperatorUnary(
    trustExpressionOperatorUnary,
    fTrustExpressionOperatorNoop,
    fTrustExpressionOperatorNot,
    fTrustExpressionOperatorExists,
    fTrustExpressionOperatorOther) {

    return trustExpressionOperatorUnary === "TrustExpressionOperatorNoop" ? fTrustExpressionOperatorNoop() :
        trustExpressionOperatorUnary === "TrustExpressionOperatorNot" ? fTrustExpressionOperatorNot() :
            trustExpressionOperatorUnary === "TrustExpressionOperatorExists" ? fTrustExpressionOperatorExists() :
                fTrustExpressionOperatorOther()
}

function matchTrustExpressionClassName(
    trustExpressionClassName,
    fTrustExpressionClassNameOperatorNoop,
    fTrustExpressionClassNameOperatorNot,
    fTrustExpressionClassNameOperatorExists,
    fTrustExpressionClassNameOperatorAnd,
    fTrustExpressionClassNameOperatorOr,
    fTrustExpressionClassNameOperatorLessThan,
    fTrustExpressionClassNameOperatorLessThanOrEqual,
    fTrustExpressionClassNameOperatorGreaterThanOrEqual,
    fTrustExpressionClassNameOperatorGreaterThan,
    fTrustExpressionClassNameOperatorEqual,
    fTrustExpressionClassNameOperatorNotEqual,
    fTrustExpressionClassNameOperatorContains,
    fTrustExpressionClassNameEvaluatorDataValueBoolean,
    fTrustExpressionClassNameEvaluatorDataValueDateTimeStamp,
    fTrustExpressionClassNameEvaluatorDataValueDecimal,
    fTrustExpressionClassNameEvaluatorDataValueString,
    fTrustExpressionClassNameEvaluatorDataValueStringList,
    fTrustExpressionClassNameEvaluatorDataValueNone,
    fTrustExpressionClassNameFailure,
    fTrustExpressionClassNameOther) {

    return trustExpressionClassName === "TrustExpressionOperatorNoop" ? fTrustExpressionClassNameOperatorNoop() :
        trustExpressionClassName === "TrustExpressionOperatorNot" ? fTrustExpressionClassNameOperatorNot() :
            trustExpressionClassName === "TrustExpressionOperatorExists" ? fTrustExpressionClassNameOperatorExists() :
                trustExpressionClassName === "TrustExpressionOperatorAnd" ? fTrustExpressionClassNameOperatorAnd() :
                    trustExpressionClassName === "TrustExpressionOperatorOr" ? fTrustExpressionClassNameOperatorOr() :
                        trustExpressionClassName === "TrustExpressionOperatorLessThan" ? fTrustExpressionClassNameOperatorLessThan() :
                            trustExpressionClassName === "TrustExpressionOperatorLessThanOrEqual" ? fTrustExpressionClassNameOperatorLessThanOrEqual() :
                                trustExpressionClassName === "TrustExpressionOperatorGreaterThanOrEqual" ? fTrustExpressionClassNameOperatorGreaterThanOrEqual() :
                                    trustExpressionClassName === "TrustExpressionOperatorGreaterThan" ? fTrustExpressionClassNameOperatorGreaterThan() :
                                        trustExpressionClassName === "TrustExpressionOperatorEqual" ? fTrustExpressionClassNameOperatorEqual() :
                                            trustExpressionClassName === "TrustExpressionOperatorNotEqual" ? fTrustExpressionClassNameOperatorNotEqual() :
                                                trustExpressionClassName === "TrustExpressionOperatorContains" ? fTrustExpressionClassNameOperatorContains() :
                                                    trustExpressionClassName === "TrustExpressionEvaluatorDataValueBoolean" ? fTrustExpressionClassNameEvaluatorDataValueBoolean() :
                                                        trustExpressionClassName === "TrustExpressionEvaluatorDataValueDateTimeStamp" ? fTrustExpressionClassNameEvaluatorDataValueDateTimeStamp() :
                                                            trustExpressionClassName === "TrustExpressionEvaluatorDataValueDecimal" ? fTrustExpressionClassNameEvaluatorDataValueDecimal() :
                                                                trustExpressionClassName === "TrustExpressionEvaluatorDataValueString" ? fTrustExpressionClassNameEvaluatorDataValueString() :
                                                                    trustExpressionClassName === "TrustExpressionEvaluatorDataValueStringList" ? fTrustExpressionClassNameEvaluatorDataValueStringList() :
                                                                        trustExpressionClassName === "TrustExpressionEvaluatorDataValueNone" ? fTrustExpressionClassNameEvaluatorDataValueNone() :
                                                                            trustExpressionClassName === "TrustExpressionFailure" ? fTrustExpressionClassNameFailure() :
                                                                                fTrustExpressionOther()
}

function matchTrustExpressionEvaluatorSource(
    trustExpressionEvaluatorSource,
    fTrustExpressionEvaluatorSourceValue,
    fTrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement,
    fTrustExpressionEvaluatorSourceTrustmarkDefinitionParameter,
    fTrustExpressionEvaluatorSourceOther) {

    return trustExpressionEvaluatorSource === "TrustExpressionEvaluatorSourceValue" ? fTrustExpressionEvaluatorSourceValue() :
        trustExpressionEvaluatorSource === "TrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement" ? fTrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement() :
            trustExpressionEvaluatorSource === "TrustExpressionEvaluatorSourceTrustmarkDefinitionParameter" ? fTrustExpressionEvaluatorSourceTrustmarkDefinitionParameter() :
                fTrustExpressionEvaluatorSourceOther()
}

function matchTrustExpressionFailure(
    trustExpressionFailure,
    fTrustExpressionFailureURI,
    fTrustExpressionFailureResolveTrustInteroperabilityProfile,
    fTrustExpressionFailureCycle,
    fTrustExpressionFailureResolveTrustmarkDefinition,
    fTrustExpressionFailureParser,
    fTrustExpressionFailureIdentifierUnknown,
    fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile,
    fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter,
    fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement,
    fTrustExpressionFailureNonTerminalUnexpected,
    fTrustExpressionFailureTrustmarkAbsent,
    fTrustExpressionFailureTrustmarkVerifierFailure,
    fTrustExpressionFailureTypeUnexpected,
    fTrustExpressionFailureTypeUnexpectedLeft,
    fTrustExpressionFailureTypeUnexpectedRight,
    fTrustExpressionFailureTypeMismatch,
    fTrustExpressionFailureTypeUnorderableLeft,
    fTrustExpressionFailureTypeUnorderableRight,
    fTrustExpressionFailureExpression,
    fTrustExpressionFailureExpressionLeft,
    fTrustExpressionFailureExpressionRight,
    fTrustExpressionFailureOther) {

    return trustExpressionFailure === "TrustExpressionFailureURI" ? fTrustExpressionFailureURI() :
        trustExpressionFailure === "TrustExpressionFailureResolveTrustInteroperabilityProfile" ? fTrustExpressionFailureResolveTrustInteroperabilityProfile() :
            trustExpressionFailure === "TrustExpressionFailureCycle" ? fTrustExpressionFailureCycle() :
                trustExpressionFailure === "TrustExpressionFailureResolveTrustmarkDefinition" ? fTrustExpressionFailureResolveTrustmarkDefinition() :
                    trustExpressionFailure === "TrustExpressionFailureParser" ? fTrustExpressionFailureParser() :
                        trustExpressionFailure === "TrustExpressionFailureIdentifierUnknown" ? fTrustExpressionFailureIdentifierUnknown() :
                            trustExpressionFailure === "TrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile" ? fTrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile() :
                                trustExpressionFailure === "TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter" ? fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter() :
                                    trustExpressionFailure === "TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement" ? fTrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement() :
                                        trustExpressionFailure === "TrustExpressionFailureNonTerminalUnexpected" ? fTrustExpressionFailureNonTerminalUnexpected() :
                                            trustExpressionFailure === "TrustExpressionFailureTrustmarkAbsent" ? fTrustExpressionFailureTrustmarkAbsent() :
                                                trustExpressionFailure === "TrustExpressionFailureTrustmarkVerifierFailure" ? fTrustExpressionFailureTrustmarkVerifierFailure() :
                                                    trustExpressionFailure === "TrustExpressionFailureTypeUnexpected" ? fTrustExpressionFailureTypeUnexpected() :
                                                        trustExpressionFailure === "TrustExpressionFailureTypeUnexpectedLeft" ? fTrustExpressionFailureTypeUnexpectedLeft() :
                                                            trustExpressionFailure === "TrustExpressionFailureTypeUnexpectedRight" ? fTrustExpressionFailureTypeUnexpectedRight() :
                                                                trustExpressionFailure === "TrustExpressionFailureTypeMismatch" ? fTrustExpressionFailureTypeMismatch() :
                                                                    trustExpressionFailure === "TrustExpressionFailureTypeUnorderableLeft" ? fTrustExpressionFailureTypeUnorderableLeft() :
                                                                        trustExpressionFailure === "TrustExpressionFailureTypeUnorderableRight" ? fTrustExpressionFailureTypeUnorderableRight() :
                                                                            trustExpressionFailure === "TrustExpressionFailureExpression" ? fTrustExpressionFailureExpression() :
                                                                                trustExpressionFailure === "TrustExpressionFailureExpressionLeft" ? fTrustExpressionFailureExpressionLeft() :
                                                                                    trustExpressionFailure === "TrustExpressionFailureExpressionRight" ? fTrustExpressionFailureExpressionRight() :
                                                                                        fTrustExpressionFailureOther()
}
