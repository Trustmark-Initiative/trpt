function matchArrayLength(
    array,
    f0,
    f1,
    fn) {

    return array.length === 0 ? f0() :
        array.length === 1 ? f1(array[0]) :
            fn(array)
}

function input(
    classMap) {

    return elementNone("input", classMap)
}

function a(
    content) {

    if (typeof content === "string" || content instanceof String || Array.isArray(content)) {
        return elementSome("a", {}, Array.from(arguments).flatMap(element => Array.isArray(element) ? element : [element]))
    } else {
        return elementSome("a", content, Array.from(arguments).slice(1).flatMap(element => Array.isArray(element) ? element : [element]))
    }
}

function label(
    content) {

    if (typeof content === "string" || content instanceof String || Array.isArray(content)) {
        return elementSome("label", {}, Array.from(arguments).flatMap(element => Array.isArray(element) ? element : [element]))
    } else {
        return elementSome("label", content, Array.from(arguments).slice(1).flatMap(element => Array.isArray(element) ? element : [element]))
    }
}

function span(
    content) {

    if (typeof content === "string" || content instanceof String || Array.isArray(content)) {
        return elementSome("span", {}, Array.from(arguments).flatMap(element => Array.isArray(element) ? element : [element]))
    } else {
        return elementSome("span", content, Array.from(arguments).slice(1).flatMap(element => Array.isArray(element) ? element : [element]))
    }
}

function div(
    content) {

    if (typeof content === "string" || content instanceof String || Array.isArray(content)) {
        return elementSome("div", {}, Array.from(arguments).flatMap(element => Array.isArray(element) ? element : [element]))
    } else {
        return elementSome("div", content, Array.from(arguments).slice(1).flatMap(element => Array.isArray(element) ? element : [element]))
    }
}

function ul(
    content) {

    if (typeof content === "string" || content instanceof String || Array.isArray(content)) {
        return elementSome("ul", {}, Array.from(arguments).flatMap(element => Array.isArray(element) ? element : [element]))
    } else {
        return elementSome("ul", content, Array.from(arguments).slice(1).flatMap(element => Array.isArray(element) ? element : [element]))
    }
}


function li(
    content) {

    if (typeof content === "string" || content instanceof String || Array.isArray(content)) {
        return elementSome("li", {}, Array.from(arguments).flatMap(element => Array.isArray(element) ? element : [element]))
    } else {
        return elementSome("li", content, Array.from(arguments).slice(1).flatMap(element => Array.isArray(element) ? element : [element]))
    }
}

function elementSome(
    element,
    classMap,
    contentArray) {

    return `<${element} ${Object.keys(classMap).map(name => classMap[name] === false ? "" : classMap[name] === true ? name : `${name}="${classMap[name]}"`).join(" ")}>${contentArray.join(" ")}</${element}>`
}

function elementNone(
    element,
    classMap) {

    return `<${element} ${Object.keys(classMap).map(name => classMap[name] === false ? "" : classMap[name] === true ? name : `${name}="${classMap[name]}"`).join(" ")}/>`
}
