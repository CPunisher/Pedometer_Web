function send(e) {
    e.preventDefault();

    var request = new XMLHttpRequest();
    var inputs = document.getElementById("callback_form").getElementsByTagName("input");
    var content = "content=" + document.getElementById("content").value + "&";
    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].type != "submit") {
            content += inputs[i].getAttribute("name") + "=" + inputs[i].value + "&";
        }
    }

    request.onreadystatechange = function (ev) {
        if (ev.target.readyState == XMLHttpRequest.DONE && ev.target.status == 200) {
            alert("发送成功!");
        }
    }
    request.open("POST", "callback.do")
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send(content);
    window.location.reload();
}