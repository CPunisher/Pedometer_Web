
function getData() {
    var request = new XMLHttpRequest();
    request.onreadystatechange = onReadyStateChange;
    request.open("POST", "rankDataDay.do");
    request.send();
}

function onReadyStateChange(ev) {
    if (ev.target.readyState == XMLHttpRequest.DONE && ev.target.status == 200) {
        var pedometerData = JSON.parse(ev.target.responseText);

        var daily = document.getElementById("table_daily_data");

        var indexDay = 0;
        for (var i = 1; i <= pedometerData.length; ++i) {
            var data = pedometerData[i-1];
            createDataRow(daily, ++indexDay, data)
        }
    }
}

