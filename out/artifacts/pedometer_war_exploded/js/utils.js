function createDataRow(table, index, data) {
    table.insertRow(index);
    table.rows[index].insertCell(0);
    table.rows[index].cells[0].appendChild(document.createTextNode(data.stuNum));
    table.rows[index].insertCell(1);
    table.rows[index].cells[1].appendChild(document.createTextNode(data.name));
    table.rows[index].insertCell(2);
    table.rows[index].cells[2].appendChild(document.createTextNode(data.steps));
    table.rows[index].insertCell(3);
    table.rows[index].cells[3].appendChild(document.createTextNode(String(Math.round(data.steps * 0.57))));
}