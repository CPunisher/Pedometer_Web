
function getData() {
    var request = new XMLHttpRequest();
    request.onreadystatechange = onReadyStateChange;
    request.open("POST", "rankDataAll.do");
    request.send();
}

function onReadyStateChange(ev) {
    if (ev.target.readyState == XMLHttpRequest.DONE && ev.target.status == 200) {
        var pedometerData = JSON.parse(ev.target.responseText);

        var daily = document.getElementById("table_daily_data");
        var weekly = document.getElementById("table_weekly_data");
        var monthly = document.getElementById("table_monthly_data");

        var indexDay = 0;
        var indexWeek = 0;
        var indexMonth = 0;
        for (var i = 1; i <= pedometerData.length; ++i) {
            var data = pedometerData[i-1];
            switch (data.date.type) {
                case 0:createDataRow(daily, ++indexDay, data);break;
                case 1:createDataRow(weekly, ++indexWeek, data);break;
                case 2:createDataRow(monthly, ++indexMonth, data);break;
            }
        }
    }
}
