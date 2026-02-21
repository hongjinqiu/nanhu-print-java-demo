function getData() {
    return {
        formatFunc: {
            amt: function(amt){
                return amt;
            },
            num: function(amt){
                return amt;
            },
            unitPrice: function(amt){
                return amt;
            }
        },
        "order": {
            "itemLi": [
                {"quantity": "10", "item": "Product A", "price": "100.00", "summa": "1000.00"}
            ,{"quantity": "5", "item": "Product B", "price": "200.00", "summa": "1000.00"}
            ,{"quantity": "8", "item": "Product C", "price": "150.00", "summa": "1200.00"}
            ,{"quantity": "3", "item": "Product D", "price": "300.00", "summa": "900.00"}
            ,{"quantity": "12", "item": "Product E", "price": "50.00", "summa": "600.00"}
            ,{"quantity": "7", "item": "Product F", "price": "180.00", "summa": "1260.00"}
            ,{"quantity": "4", "item": "Product G", "price": "250.00", "summa": "1000.00"}
            ,{"quantity": "9", "item": "Product H", "price": "120.00", "summa": "1080.00"}
            ,{"quantity": "6", "item": "Product I", "price": "160.00", "summa": "960.00"}
            ,{"quantity": "11", "item": "Product J", "price": "90.00", "summa": "990.00"}
            ,{"quantity": "2", "item": "Product K", "price": "400.00", "summa": "800.00"}
            ,{"quantity": "15", "item": "Product L", "price": "70.00", "summa": "1050.00"}
            ,{"quantity": "5", "item": "Product M", "price": "220.00", "summa": "1100.00"}
            ,{"quantity": "8", "item": "Product N", "price": "130.00", "summa": "1040.00"}
            ,{"quantity": "3", "item": "Product O", "price": "280.00", "summa": "840.00"}
            ,{"quantity": "10", "item": "Product P", "price": "110.00", "summa": "1100.00"}
            ,{"quantity": "7", "item": "Product Q", "price": "170.00", "summa": "1190.00"}
            ,{"quantity": "4", "item": "Product R", "price": "240.00", "summa": "960.00"}
            ,{"quantity": "9", "item": "Product S", "price": "140.00", "summa": "1260.00"}
            ,{"quantity": "6", "item": "Product T", "price": "190.00", "summa": "1140.00"}
            ,{"quantity": "12", "item": "Product U", "price": "80.00", "summa": "960.00"}
            ,{"quantity": "5", "item": "Product V", "price": "210.00", "summa": "1050.00"}
            ,{"quantity": "8", "item": "Product W", "price": "155.00", "summa": "1240.00"}
            ,{"quantity": "3", "item": "Product X", "price": "290.00", "summa": "870.00"}
            ,{"quantity": "10", "item": "Product Y", "price": "115.00", "summa": "1150.00"}
            ,{"quantity": "7", "item": "Product Z", "price": "175.00", "summa": "1225.00"}
            ,{"quantity": "4", "item": "Product AA", "price": "230.00", "summa": "920.00"}
            ,{"quantity": "9", "item": "Product AB", "price": "145.00", "summa": "1305.00"}
            ,{"quantity": "6", "item": "Product AC", "price": "185.00", "summa": "1110.00"}
            ,{"quantity": "11", "item": "Product AD", "price": "95.00", "summa": "1045.00"}
            ,{"quantity": "2", "item": "Product AE", "price": "380.00", "summa": "760.00"}
            ,{"quantity": "15", "item": "Product AF", "price": "75.00", "summa": "1125.00"}
            ,{"quantity": "5", "item": "Product AG", "price": "205.00", "summa": "1025.00"}
            ,{"quantity": "8", "item": "Product AH", "price": "135.00", "summa": "1080.00"}
            ,{"quantity": "3", "item": "Product AI", "price": "270.00", "summa": "810.00"}
            ,{"quantity": "10", "item": "Product AJ", "price": "105.00", "summa": "1050.00"}
            ,{"quantity": "7", "item": "Product AK", "price": "165.00", "summa": "1155.00"}
            ,{"quantity": "4", "item": "Product AL", "price": "235.00", "summa": "940.00"}
            ,{"quantity": "9", "item": "Product AM", "price": "125.00", "summa": "1125.00"}
            ,{"quantity": "6", "item": "Product AN", "price": "195.00", "summa": "1170.00"}
            ]
        }
    }
}

function getFormData() {
    var data = {};
    var value = $('#configForm').serializeArray();
    $.each(value, function (index, item) {
        data[item.name] = item.value;
    });

    $('#configForm input[type="checkbox"]').each(function(index, item){
        var key = $(item).attr("name");
        if (!$(item).prop("checked") && !data[key]) {
            data[key] = "0";
        }
    });

    $('#configForm input[type="radio"]').each(function(index, item){
        var key = $(item).attr("name");
        if (!$(item).prop("checked") && !data[key]) {
            data[key] = "0";
        }
    });

    return data;
}

function getXml() {
    var xml = null;
    $.ajax({
        method: 'GET',
        async: false,
        contentType: "application/json; charset=UTF-8",
        dataType: "json",
        url: contextPath + "/config/getXml.json?fileName=paginatedBorder",
        data: null,
        success: function (result) {
            xml = result.xmlContent;
        },
        error: function (err) {
            console.error(err);
        }
    });
    return xml;
}

function applyBodyAttribute() {
    var bodyMetaObj = nanhuprintEval_metaObj[nanhuprintEval_body_id];
    var widthString = nanhuprintEval_getCssAttribute(bodyMetaObj, "ifAndForEachAndSet", "width");
    if (widthString) {
        widthString = widthString.replace(/^\s+|[\s;]+$/g, "");
    }
    var heightString = nanhuprintEval_getCssAttribute(bodyMetaObj, "ifAndForEachAndSet", "height");
    if (heightString) {
        heightString = heightString.replace(/^\s+|[\s;]+$/g, "");
    }
    var rotate = (bodyMetaObj.rotate && bodyMetaObj.rotate == "true") ? true : false;
    if (rotate) {
        if (!widthString || !heightString) {
            throw new Error("body 配置了 rotate='true', width 和 height 必须同时有值!");
        }
        $("#rightArea").css("width", heightString);
        $("#rightArea").css("height", widthString);
    } else {
        if (widthString) {
            $("#rightArea").css("width", widthString);
        }
        if (heightString) {
            $("#rightArea").css("height", heightString);
        }
    }
}

function getSubmitData() {
    var data = getData();
    var formData = getFormData();
    data.printConfigTemplate = formData;
    data.printConfigTemplate.fontFamily = "arial";
    return data;
}

function genHtml() {
    var xml = getXml();
    var data = getSubmitData();

    var nanhuprintInterpreter = new NanhuprintInterpreter();
    nanhuprintInterpreter.interpreterString(xml, data);

    applyBodyAttribute();

    var step2Html = data.nanhuprint_result.step2Html;
    var regExp = new RegExp("<body [^>]*?>(.*?)<\\/body>", "mi");
    if (regExp.test(step2Html)) {
        var bodyContent = RegExp.$1;
        $("#rightArea").html(bodyContent);
    }

    nanhuprintEval_clearAll();
}

function genPdf() {
    document.forms["pdfForm"].printConfigJSON.value = JSON.stringify(getSubmitData());
    document.forms["pdfForm"].submit();
}

function init() {
    $('#configForm input[type="checkbox"]').click(function(){
        genHtml();
    });
    $('#configForm input[type="radio"]').click(function(){
        genHtml();
    });
    $('#configForm input[type="text"]').blur(function(){
        genHtml();
    });

    genHtml();
}

$(document).ready(function(){
    init();
});
