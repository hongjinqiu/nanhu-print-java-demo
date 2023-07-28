function getData() {
    return {
        formatFunc: {
            // 金额格式
            amt: function(amt){
                return amt;
            },
            // 数量格式化
            num: function(amt){
                return amt;
            },
            // 单价格式化
            unitPrice: function(amt){
                return amt;
            }
        },
        "data": {
            "firstPage": "this is firstPage",
            "lastPageTbottom": "this is lastPage tbottom",
            "contentList": [
                "content1"
                ,"content2"
                ,"content3"
                ,"content4"
                ,"content5"
                ,"content6"
                ,"content7"
                ,"content8"
                ,"content9"
                ,"content10wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"
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

    // 将未勾上的 checkbox, 值置为 0
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

    // console.log("data is:");
    // console.log(data);
    return data;
}

function getXml() {
    var xml = null;
    $.ajax({
        method: 'GET',
        async: false,
        contentType: "application/json; charset=UTF-8",
        dataType: "json",
        url: contextPath + "/config/getXml.json?fileName=twoMode",
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
    // body,宽,高,rotate 赋值
    var bodyMetaObj = nanhuprintEval_metaObj[nanhuprintEval_body_id];
    var widthString = nanhuprintEval_getCssAttribute(bodyMetaObj, "ifAndForEachAndSet", "width");
    if (widthString) {
        widthString = widthString.replace(/^\s+|[\s;]+$/g, "");// 清除表头,表尾空格分号
    }
    var heightString = nanhuprintEval_getCssAttribute(bodyMetaObj, "ifAndForEachAndSet", "height");
    if (heightString) {
        heightString = heightString.replace(/^\s+|[\s;]+$/g, "");// 清除表头,表尾空格分号
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

    // console.log("data is:");
    // console.log(JSON.stringify(data));

    var nanhuprintInterpreter = new NanhuprintInterpreter();
    nanhuprintInterpreter.interpreterString(xml, data);

    applyBodyAttribute();

    // console.log("data.nanhuprint_result is:");
    // console.log(data.nanhuprint_result);

    // console.log("data.nanhuprint_result.step1Xml is:");
    // console.log(data.nanhuprint_result.step1Xml);

    // console.log("data.nanhuprint_result.step2Html is:");
    // console.log(data.nanhuprint_result.step2Html);

    var step2Html = data.nanhuprint_result.step2Html;
    // var regExp = new RegExp("<body [^>]*?>(.*?)<\\/body>", "msi");
    var regExp = new RegExp("<body [^>]*?>(.*?)<\\/body>", "mi");
    if (regExp.test(step2Html)) {
        var bodyContent = RegExp.$1;
        $("#rightArea").html(bodyContent);
    }

    nanhuprintEval_clearAll();
}

/**
 * 生成 pdf
 */
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
