/**
 * Created by T440 on 2019/10/20.
 */

/**
 * API
 * provide arrangement query conditions
 * these input elements of all conditions use same inputClassName, if the input element apply to other models ,you can use classify.
 *
 * Notes
 * 1.width controller is labelCol and fieldCol
 * 2.cascadeField and cascadeUrl provide cascade query ,but target must be dataList;
 *
 * <tr>
 *     <td>
 *         <span>label</span>
 *     </td>
 *     <td>
 *         <input/>
 *         <dataList>
 *             <select> </select>
 *         </dataList>
 *     </td>
 * </tr>
 */
function getParam($class) {
    var object = {};
    $class.each(function () {
        if($(this).hasClass("easyui-datebox")) {
            object[$(this).attr('id')] = $(this).datebox("getValue");
        }else{
            object[$(this).attr('id')] = $(this).val();
        }
    });
    return object;
}

function QueryConditions($table, objects, inputClassName) {
    for (var i = 0, len = objects.length; i < len; i += 1) {
        var yConditionRow = new YConditionRow($table.attr("id"), objects[i], i);
        yConditionRow.build(inputClassName);
    }
}

function YConditionRow(tableId, objects, rowId) {
    this.table = document.getElementById(tableId);
    this.columnSize = objects.data.length;
    this.objects = objects.data;
    this.rowId = tableId + rowId;
}

YConditionRow.prototype.build = function (inputClassName) {
    var trElement = tr(this.rowId);
    for (var i = 0; i < this.columnSize; i++) {
        var query = new YCondition(this.objects[i], inputClassName);
        query.build(trElement);
    }
    this.table.appendChild(trElement);
};

function YCondition(object, inputClassName) {
    this.label = object.label;
    this.labelCol = object.labelCol;
    this.field = object.field;
    this.fieldCol = object.fieldCol;
    this.width = object.width;
    this.markStar = object.markStar;
    this.useDataList = object.useDataList;
    this.isInit = object.isInit;
    this.useUrlInit = object.useUrlInit;
    this.initUrl = object.initUrl;
    this.initData = object.initData;
    this.selectedData = object.selectedData;
    this.cascadeField = object.cascadeField;
    this.cascadeUrl = object.cascadeUrl;

    this.spanClassName = 'querySpan';
    this.inputId = this.field;
    this.inputClassName = object.classify == undefined ? inputClassName : inputClassName + " " + object.classify;
    this.dataListId = this.field + 'Data';
    this.selectId = this.field + 'Select';
}
YCondition.prototype = {
    init: function () {
        if (isBlank(this.useDataList)) this.useDataList = false;
        if (isBlank(this.isInit)) this.isInit = false;
        if (isBlank(this.isInit) || !this.isInit)this.useUrlInit = false;
        if (isBlank(this.useUrlInit)) this.useUrlInit = false;
        if (isBlank(this.nextCascadeQuery)) this.nextCascadeQuery = false;
        if (isBlank(this.markStar)) this.markStar = false;
    },
    build: function (trElement) {
        this.init();
        var labelTd = td(this.labelCol);
        labelTd.appendChild(span(this.spanClassName, this.label));
        labelTd.appendChild(star(this.markStar));
        trElement.appendChild(labelTd);

        var fieldTd = td(this.fieldCol);
        var inputElement = input(this.inputId, this.inputClassName,"text", this.width);
        if (this.useDataList) inputElement.setAttribute("list", this.dataListId);
        fieldTd.appendChild(inputElement);
        if (this.useDataList) {
            if (this.selectedData != undefined) inputElement.value = this.selectedData;
            var dataListElement = dataList(this.dataListId);
            var selectElement = select(this.selectId);
            if (this.isInit) {
                if (this.useUrlInit) {
                    urlSelect(selectElement, this.initUrl);
                } else {
                    dataSelect(selectElement, this.initData);
                }
            }
            dataListElement.appendChild(selectElement);
            fieldTd.appendChild(dataListElement);
        } else {
            if (this.isInit) {
                inputElement.value = this.initData;
            }
        }
        trElement.appendChild(fieldTd);

        if (this.cascadeField != undefined) {
            cascadeQuery(inputElement, this.cascadeField, this.cascadeUrl);
        }
    }
};

function cascadeQuery(inputElement, targetInputId, cascadeQueryUrl) {
    inputElement.onblur = function () {
        var targetInputElement = document.getElementById(targetInputId);
        var targetSelectElement = document.getElementById(targetInputId + "Select");
        targetInputElement.value = "";
        targetSelectElement.options.length = 0;
        var conditionValue = inputElement.value;
        $.ajax({
            url: cascadeQueryUrl + "=" + conditionValue ,
            dataType: "json",
            success: function (data) {
                $(data).each(function(i){
                    var option = optionELE(this);
                    if (i == 0 ){
                        targetInputElement.value = this;
                    }
                    targetSelectElement.options.add(option);
                });
            }
        });
    };
}

function tr(id) {
    var trElement = document.createElement("tr");
    trElement.id = id;
    return trElement;
}

function td(colspan) {
    var td = document.createElement("td");
    if (colspan != undefined) td.setAttribute("colspan", colspan);
    return td;
}

function dataList(id) {
    var dataListElement = document.createElement("dataList");
    dataListElement.id = id;
    return dataListElement;
}
function select(id) {
    var selectElement = document.createElement("select");
    selectElement.id = id;
    return selectElement;
}

function urlSelect(selectElement, url) {
    $.ajax({
        url: url,
        dataType: "json",
        success: function (data) {
            $(data).each(function (i) {
                var option = optionELE(this);
                if (i == 0){
                    option.selected = true;
                }
                selectElement.options.add(option);
            });
        }
    });
}

function dataSelect(selectElement, data) {
    for (var k = 0, len = data.length; k < len; k++) {
        var option = optionELE(data[k]);
        if (k == 0){
            option.selected = true;
        }
        selectElement.options.add(option);
    }
}

function span(spanClassName, label) {
    var spanElement = document.createElement("span");
    spanElement.style.display = "inline-block";
    spanElement.className = spanClassName;
    spanElement.innerText = label;
    spanElement.style.textAlign = 'right';
    return spanElement;
}

function star(markStar) {
    var starElement = document.createElement("span");
    starElement.innerText = "*";
    starElement.verticalAlign = "middle";
    starElement.style.float = "right";
    if (markStar) {
        starElement.style.color = 'red';
    }else{
        starElement.style.color = "rgba(255, 0, 0, 0)";
    }
    return starElement;
}

function optionELE(data) {
    var optionElement = document.createElement("option");
    optionElement.value = data;
    return optionElement;
}

function input(id, className, type, width) {
    var inputElement = document.createElement("input");
    inputElement.style.boxSizing = "border-box";
    inputElement.type = type;
    inputElement.style.textAlign = 'center';
    inputElement.style.width = width == undefined ? "100%" : width;
    inputElement.id = id;
    inputElement.className = className;
    return inputElement;
}