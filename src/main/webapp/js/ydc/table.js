window.Table = {};
Table.datagrid = {
    config: {
        idField: 'id', //只要创建数据表格 就必须要加 ifField
        fit: true,
        fitColumns: false,
        nowrap: true,
        striped: true, //隔行变色特性
        loadMsg: '数据正在加载,请耐心的等待...',
        pagination: true,
        pageList: [10, 20, 30],
        pageSize: 10,
        singleSelect: true,
        selectOnCheck: true,
        checkOnSelect: true
    },
    initColumns : function(fields, useCheckBox){
        var columns = [[]];
        if(useCheckBox) columns[0][0] = {field:"ck", checkbox:true};
        $(fields).each(function(i,val){
            var index = useCheckBox ? i + 1 : i;
            columns[0][index] = {field: val, title: val, editor: "text"};
        });
        return columns;
    },
    autoColumns: function (object, useCheckBox) {
        var columns = [[]];
        var i = 0;
        if (useCheckBox) {
            columns[0][0] = {field:"ck", checkbox:true};
            i++;
        }
        $.each(object, function (field) {
            columns[0][i] = {field: field, title: field, editor: "text"};
            i++;
        });
        return columns;
    },
    /**
     * url load data
     * @param $table
     * @param title
     * @param url
     * @param columns
     * @param cellClickFun
     */
    load: function ($table, title, url, columns, cellClickFun) {
        var config = this.config;
        config.title = title;
        config.url = url;
        config.columns = columns;
        if (cellClickFun != undefined) {
            config.onClickCell = function (rowIndex, field, value) {
                cellClickFun(field, value);
            };
        }
        $table.datagrid(config);
    },
    /**
     * load local data
     * @param $table
     * @param title
     * @param data
     * @param columns
     * @param cellClickFun
     */
    loadData: function ($table, title, data, columns, cellClickFun) {
        var config = this.config;
        config.title = title;
        config.columns = columns == undefined || columns == null ? this.autoColumns(data[0], true) : columns;
        if (cellClickFun != undefined) {
            config.onClickCell = function (rowIndex, field, value) {
                cellClickFun(field, value);
            };
        }
        $table.datagrid(config);
        $table.datagrid("loadData", data);
    }
};

Table.simple = {
    /**
     * one object vertical display
     * @param $table
     * @param object
     * @param titles
     */
    vertical: function ($table, object, titles) {
        var trs = "";
        if (titles == undefined) {
            $.each(object, function (field, val) {
                trs += '<tr><td>' + field + ':</td><td>' + val + '</td></tr>';
            });
        } else {
            $(titles).each(function () {
                trs += '<tr><td>' + this.label + ':</td><td>' + object[this.field] + '</td></tr>';
            });
        }
        $table.html(trs);
    },

    /**
     * all object horizontal display
     * @param $table
     * @param objects
     * @param titles
     */
    horizontal: function ($table, objects, titles) {
        var table = '<tr>';
        if (titles == undefined) {
            $.each(objects[0], function (field, val) {
                table += '<td>' + field + '</td>';
            });
        } else {
            $(titles).each(function () {
                table += '<td>' + this.label + '</td>';
            });
        }
        table += '</tr>';

        $(objects).each(function () {
            table += '<tr>';
            var object = this;
            if (titles == undefined) {
                $.each(object, function (field, val) {
                    table += '<td>' + val + '</td>';
                })
            } else {
                $(titles).each(function () {
                    table += '<td>' + (object[this.field] == undefined ? "" : object[this.field]) + '</td>';
                });
            }
            table += '</tr>';
        });
        $table.html(table);
    }
};
