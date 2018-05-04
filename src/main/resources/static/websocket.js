window.onload = init;
var socket = new WebSocket("ws://localhost:8080");
socket.onmessage = onMessage;

function onMessage(event) {
    var factory = JSON.parse(event.data);
    if (factory.action === "add") {
        printFactoryElement(factory);
    }
    if (factory.action === "remove") {
        document.getElementById(factory.id).remove();
    }

}

function addFactory(name, rangeLow, rangeHigh) {
    var FactoryAction = {
        action: "add",
        name: name,
        rangeLow: rangeLow,
        rangeHigh: rangeHigh
    };
    socket.send(JSON.stringify(FactoryAction));
}

function removeFactory(element) {
    var id = element;
    var FactoryAction = {
        action: "remove",
        id: id
    };
    socket.send(JSON.stringify(FactoryAction));
}

function printFactoryElement(factory) {
    var content = document.getElementById("content");

    var factoryDiv = document.createElement("div");
    factoryDiv.setAttribute("id", factory.id);
    factoryDiv.setAttribute("class", "factory ");
    content.appendChild(factoryDiv);

    var factoryName = document.createElement("span");
    factoryName.innerHTML = factory.name;
    factoryName.setAttribute("class", "factoryName");
    factoryDiv.appendChild(factoryName);

    var factoryRange = document.createElement("span");
    factoryRange.innerHTML = "<b>Range:</b>" + factory.rangeLow + " : " + factory.rangeHigh;
    factoryDiv.appendChild(factoryRange);

    var removeFactory = document.createElement("span");
    removeFactory.setAttribute("class", "removeFactory");
    removeFactory.innerHTML = "<a href=\"#\" OnClick=removeFactory(" + factory.id + ")>Remove Factory</a>";
    factoryDiv.appendChild(removeFactory);
}

function showForm() {
    document.getElementById("addFactoryForm").style.display = '';
}

function hideForm() {
    document.getElementById("addFactoryForm").style.display = "none";
}

function formSubmit() {
    var form = document.getElementById("addFactoryForm");
    var name = form.elements["factory_name"].value;
    var rangeLow = form.elements["range_low"].value;
    var rangeHigh = form.elements["range_high"].value;
    hideForm();
    document.getElementById("addFactoryForm").reset();
    addFactory(name, rangeLow, rangeHigh);
}

function init() {
    hideForm();
}