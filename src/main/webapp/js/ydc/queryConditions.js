/**
 * Created by T440 on 2018/5/17.
 */
/**
 * API
 * provide arrangement query conditions
 * control parameters : columns and columnsWidth
 *
 * support list search and cascade query
 * control parameters : nextCascadeQuery
 * <div>
 *     <span></span>
 *     <input/>
 *     <dataList>
 *         <select>
 *         </select>
 *     </dataList>
 * </div>
 */

/**
 *
 * @param $query
 * @param objects
 * @param inputClassName  : all input class use this name
 * @constructor
 */
function QueryConditions($query, objects, inputClassName) {
    for (var i = 0, len = objects.length; i < len; i += 1) {
        var queryConditionRow = new QueryConditionRow($query, objects[i], i);
        queryConditionRow.build(inputClassName);
    }
}

/**
 * create query condition row
 * layout depend on columnWidths
 *
 * @param $query
 * @param objects
 * @param rowId
 * @constructor
 */
function QueryConditionRow($query, objects, rowId) {
    this.columnSize = objects.data.length;
    this.query = $query;
    this.objects = objects.data;
    this.conditions = [];
    this.rowId = this.query.attr("id") + rowId;
    this.contentWidth = $query.css('width');
}
QueryConditionRow.prototype.build = function (inputClassName) {
    var len = this.objects.length;
    for (var i = 0; i < len; i += this.columnSize) {
        var divElement = document.createElement('div');
        divElement.id = this.rowId;
        divElement.style.width = this.contentWidth;
        divElement.style.display = 'table';
        this.query.append(divElement);
        for (var j = 0; j < this.columnSize && (i + j) < len; j++) {
            var queryInput = new QueryCondition(this.objects[i + j], inputClassName);
            this.conditions.push(queryInput);
            var queryElement = this.objects[i + j].nextCascadeQuery ? queryInput.build(this.objects[i + j + 1]) : queryInput.build();
            queryElement.style.display = 'table-cell';
            divElement.appendChild(queryElement);
        }
    }
};

/**
 * create condition model
 * support dataList and cascade query
 * input class will contain classify when user need to select some share condition
 *
 * @param object
 * @param inputClassName
 * @constructor
 */
function QueryCondition(object, inputClassName) {
    this.label = object.label;
    this.field = object.field;
    this.width = object.width;
    this.markStar = object.markStar;
    this.useDataList = object.useDataList;
    this.isInit = object.isInit;
    this.useUrlInit = object.useUrlInit;
    this.initUrl = object.initUrl;
    this.initData = object.initData;
    this.selectedData = object.selectedData;
    this.nextCascadeQuery = object.nextCascadeQuery;
    this.cascadeQueryUrl = object.cascadeQueryUrl;

    this.spanClassName = 'querySpan';
    this.inputId = this.field;
    this.inputList = this.field + 'Data';
    this.inputClassName = object.classify == undefined ? inputClassName : inputClassName + " " + object.classify;
    this.dataListId = this.field + 'Data';
    this.selectId = this.field + 'Select';
}
QueryCondition.prototype = {
    init: function () {
        if (isBlank(this.useDataList)) this.useDataList = false;
        if (isBlank(this.isInit)) this.isInit = false;
        if (isBlank(this.isInit) || !this.isInit)this.useUrlInit = false;
        if (isBlank(this.useUrlInit)) this.useUrlInit = false;
        if (isBlank(this.nextCascadeQuery)) this.nextCascadeQuery = false;
        if (isBlank(this.markStar)) this.markStar = false;
    },
    build: function (nextObj) {
        this.init();
        var queryElement = this.createLabel(this.markStar);
        var inputElement = YDC.elementBuilder.input(this.inputId, this.inputClassName, 'text');
        if (!isBlank(this.width)) inputElement.style.width = this.width;
        if (this.useDataList) inputElement.setAttribute('list', this.inputList);
        queryElement.appendChild(inputElement);
        if (this.useDataList) {
            if(!isBlank(this.selectedData)) inputElement.value = this.selectedData;
            var dataListElement = YDC.elementBuilder.dataList(this.dataListId);
            var selectElement = YDC.elementBuilder.select(this.selectId);
            if (this.isInit) {
                if (this.useUrlInit) {
                    $.ajax({
                        url: this.initUrl,
                        dataType: "json",
                        success: function (data) {
                            $(data).each(function (i) {
                                var option = YDC.elementBuilder.option(this);
                                if (i == 0){
                                    option.selected = true;
                                }
                                selectElement.options.add(option);
                            });
                        }
                    });
                } else {
                    for (var k = 0, len = this.initData.length; k < len; k++) {
                        var option = YDC.elementBuilder.option(this.initData[k]);
                        if (k == 0){
                            option.selected = true;
                        }
                        selectElement.options.add(option);
                    }
                }
            }
            dataListElement.appendChild(selectElement);
            queryElement.appendChild(dataListElement);
            var nextObject = nextObj == undefined ? null : new QueryCondition(nextObj);
            if (this.nextCascadeQuery && !isBlank(nextObject)) {
                var nextSelectId = '#' + nextObject.selectId;
                var nextInputId = '#' + nextObject.inputId;
                var currentId = '#' + this.inputId;
                var cascadeQueryUrl = this.cascadeQueryUrl;
                inputElement.onblur = function () {
                    $(nextInputId).attr('value', '');
                    $(nextSelectId).removeAttr('option');
                    var conditionValue = $(currentId).val();
                    var optionStr = '';
                    $.ajax({
                        url: cascadeQueryUrl,
                        data: {conditionValue: conditionValue},
                        dataType: "json",
                        success: function (data) {
                            $(data).each(function () {
                                optionStr += "<option value='" + this + "'" + ">" + "</option>"
                            });
                            $(nextSelectId).html(optionStr);
                        }
                    });
                }
            }
        } else {
            if (this.isInit) {
                inputElement.value = this.initData;
            }
        }
        return queryElement;
    },
    createLabel: function (markStar) {
        var labelElement = document.createElement('div');
        var spanElement = document.createElement('span');
        spanElement.style.display = 'inline-block';
        spanElement.className = this.spanClassName;
        spanElement.innerText = this.label;
        spanElement.style.textAlign = 'center';
        labelElement.appendChild(spanElement);
        var starElement = document.createElement('span');
        starElement.innerText = '*';
        starElement.verticalAlign = 'middle';
        if (markStar) {
            starElement.style.color = 'red';
        }else{
            starElement.style.color = "rgba(255, 0, 0, 0)";
        }
        labelElement.appendChild(starElement);
        return labelElement;
    }
};
