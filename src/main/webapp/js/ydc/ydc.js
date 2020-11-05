/**
 * Created by YDC on 2018/5/18.
 */
window.YDC = {};

YDC.dynamicLoading = {
    /**
     *
     * @param path
     */
    css: function (path) {
        if (!path || path.length === 0) {
            throw new Error('argument "path" is required !');
        }
        var head = document.getElementsByTagName('head')[0];
        var link = document.createElement('link');
        link.href = path;
        link.rel = 'stylesheet';
        link.type = 'text/css';
        head.appendChild(link);
    },
    /**
     *
     * @param path
     * @param callbackFn
     */
    js: function (path, callbackFn) {
        if (!path || path.length === 0) {
            throw new Error('argument "path" is required !');
        }
        var script = document.createElement('script');
        script.src = path;
        script.type = 'text/javascript';
        if (callbackFn != undefined) {
            script.onload = callbackFn;
        }
        var head = document.getElementsByTagName('head')[0];
        head.appendChild(script);
    }
};

YDC.canvas = {
    drawCircle: function (cxt, x, y, r, sAngle, eAngle, fillColor, direction) {
        var _direction = isBlank(direction) ? true : direction;
        cxt.beginPath();
        cxt.arc(x, y, r, sAngle, eAngle, _direction);
        cxt.fillStyle = fillColor;
        cxt.fill();
        cxt.closePath();
    }
};

YDC.setLayout = {
    horizontal: function (contain, areas) {
        var offsetLeft = 0;
        var len = areas.length;
        for (var i = 0; i < len; i++) {
            if (i == len - 1) {
                $(contain).append($('<div id= "' + areas[i].id + '" style="position:absolute;top:0px;right:0;width:' + (100.1 - offsetLeft) + '%;height:100%' + '">'));
            } else {
                $(contain).append($('<div id= "' + areas[i].id + '" style="position:absolute;top:0px;left:' + offsetLeft + '%;width:' + areas[i].width + '%;height:100%' + '">'));
            }
            offsetLeft += areas[i].width;
        }
    }
};
YDC.cursor = {
    defineIcon: function (target, icon) {
        $(target).css('cursor', isBlank(icon) ? 'default' : icon);
    }
};
YDC.mouse = {
    position: function (event, parentContain) {
        var e = event || window.event;
        var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
        var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
        var xx = e.pageX || e.clientX + scrollX;
        var yy = e.pageY || e.clientY + scrollY;
        if (parentContain != undefined) {
            xx = xx - $(parentContain).offset().left;
            yy = yy - $(parentContain).offset().top;
        }
        return {x: xx, y: yy};
    }
};
YDC.audio = {
    /**
     *
     * @param audioId
     * @param progressLoadId
     * @param progressBarLength
     * @param currentTimeId
     * @param delay
     * @param sliderControllerId
     * @param sliderSize
     */
    flashProgressBar: function (audioId, progressLoadId, progressBarLength, currentTimeId, delay, sliderControllerId, sliderSize) {
        setTimeout(function () {
            var audioElement = document.getElementById(audioId);
            var currentWidth = audioElement.currentTime / audioElement.duration * progressBarLength;
            var progressElement = document.getElementById(progressLoadId);
            progressElement.style.width = currentWidth + 'px';
            var playedTimeElement = document.getElementById(currentTimeId);
            playedTimeElement.innerText = YDC.audio.formatTime(audioElement.currentTime);
            if (!isBlank(sliderControllerId)) {
                var controllerElement = document.getElementById(sliderControllerId);
                controllerElement.style.marginLeft = (currentWidth - sliderSize / 2.0) + 'px';
            }
            YDC.audio.flashProgressBar(audioId, progressLoadId, progressBarLength, currentTimeId, delay, sliderControllerId, sliderSize);
        }, delay);
    },
    /**
     * format audio time
     * @param seconds
     * @returns {string}
     */
    formatTime: function (seconds) {
        var min = Math.floor(seconds / 60);
        var second = (seconds % 60).toFixed(0);
        var hour, newMin;
        if (min > 60) {
            hour = Math.floor(min / 60);
            newMin = min % 60;
        }
        if (hour < 10) {
            hour = '0' + hour;
        }
        if (second < 10) {
            second = '0' + second;
        }
        if (min < 10) {
            min = '0' + min;
        }
        return hour ? (hour + ':' + newMin + ':' + second) : (min + ':' + second);
    }
};

YDC.dateOperation = {
    dt: function (date) {
        this.year = date.getFullYear();
        this.month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        this.day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        this.hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        this.minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        this.second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
    },
    getDate: function (date) {
        var today = new this.dt(date);
        return today.year + '-' + today.month + '-' + today.day;
    },
    currentDate: function () {
        var today = new this.dt(new Date());
        return today.year + '-' + today.month + '-' + today.day;
    },
    nextDate: function () {
        var today = new Date();
        var next = new this.dt(new Date(today.getTime() + 24 * 60 * 60 * 1000));
        return next.year + '-' + next.month + '-' + next.day;
    }
};

YDC.elementBuilder = {
    /**
     *
     * @param id
     * @param className
     * @param isDisplay
     * @returns {HTMLElement}
     */
    ul: function (id, className, isDisplay) {
        var ulElement = document.createElement('ul');
        if (!isBlank(id))ulElement.id = id;
        if (!isBlank(className))ulElement.className = className;
        ulElement.style.listStyle = 'none';
        ulElement.style.margin = '0';
        ulElement.style.padding = '0';
        ulElement.style.display = isBlank(isDisplay) ? 'none' : isDisplay ? 'block' : 'none';
        return ulElement;
    },
    /**
     *
     * @param className
     * @returns {HTMLElement}
     */
    li: function (className) {
        var liElement = document.createElement('li');
        if (!isBlank(className)) liElement.className = className;
        return liElement;
    },
    /**
     *
     * @param className optional
     * @returns {HTMLElement}
     */
    a: function (className) {
        var aElement = document.createElement('a');
        if (!isBlank(className))aElement.className = className;
        return aElement;
    },
    /**
     *
     * @param className optional
     * @param text optional
     * @returns {HTMLElement}
     */
    span: function (className, text) {
        var spanElement = document.createElement('span');
        if (!isBlank(className)) spanElement.className = className;
        if (!isBlank(text)) spanElement.innerText = text;
        spanElement.style.verticalAlign = 'middle';
        return spanElement;
    },
    /**
     *
     * @param width optional
     * @param height optional
     * @param id optional
     * @param className optional
     * @param url optional
     * @returns {HTMLElement}
     */
    img: function (id, className, url, width, height) {
        var imgElement = document.createElement('img');
        if (!isBlank(id)) imgElement.id = id;
        if (!isBlank(className)) imgElement.className = className;
        if (!isBlank(width)) imgElement.width = width;
        if (!isBlank(height)) imgElement.height = height;
        imgElement.style.verticalAlign = 'middle';
        imgElement.src = url;
        imgElement.alt = '';
        return imgElement;
    },
    /**
     *
     * @param id
     * @param className
     * @returns {HTMLElement}
     */
    input: function (id, className, type) {
        var inputElement = document.createElement('input');
        inputElement.type = type;
        if (!isBlank(id))inputElement.id = id;
        inputElement.style.textAlign = 'center';
        if (!isBlank(className))inputElement.className = className;
        return inputElement;
    },
    /**
     *
     * @param id
     * @returns {HTMLElement}
     */
    dataList: function (id) {
        var dataListElement = document.createElement('dataList');
        if (!isBlank(id))dataListElement.id = id;
        return dataListElement;
    },
    /**
     *
     * @param id
     * @returns {HTMLElement}
     */
    select: function (id) {
        var selectElement = document.createElement('select');
        if (!isBlank(id))selectElement.id = id;
        return selectElement;
    },
    /**
     *
     * @param data
     * @returns {HTMLElement}
     */
    option: function (data) {
        var optionElement = document.createElement("option");
        optionElement.value = data;
        return optionElement;
    },
    /**
     *
     * @param id
     * @param className
     * @param position
     * @param width
     * @param height
     * @param borderRadius
     * @param backgroundColor
     * @returns {HTMLElement}
     */
    div: function (id, className, position, width, height, borderRadius, backgroundColor) {
        var divElement = document.createElement('div');
        if (!isBlank(id))divElement.id = id;
        if (!isBlank(className))divElement.className = className;
        if (!isBlank(position))divElement.style.position = position;
        if (!isBlank(width))divElement.style.width = width + 'px';
        if (!isBlank(height))divElement.style.height = height + 'px';
        if (!isBlank(borderRadius)) divElement.style.borderRadius = borderRadius + 'px';
        if (!isBlank(backgroundColor))divElement.style.backgroundColor = backgroundColor;
        return divElement;
    },
    /**
     *
     * @param text
     * @returns {HTMLElement}
     */
    tableCellDiv: function (text) {
        var cellElement = document.createElement('div');
        cellElement.style.display = 'table-cell';
        cellElement.style.textAlign = 'center';
        cellElement.style.overflow = 'hidden';
        cellElement.style.whiteSpace = 'nowrap';
        if (!isBlank(text))cellElement.innerText = text;
        return cellElement;
    },
    /**
     *
     * @param id
     * @param width
     * @param height
     * @returns {HTMLElement}
     */
    canvas: function (id, width, height) {
        var canvasElement = document.createElement('canvas');
        if (!isBlank(id))canvasElement.id = id;
        if (!isBlank(width))canvasElement.width = width;
        if (!isBlank(height))canvasElement.height = height;
        return canvasElement;
    }
};

YDC.cssOperation = {
    localCorrection: function (target, topCorrectionOffset, leftCorrectionOffset) {
        $(target).each(function () {
            var p_w = parseInt($(this).parent().css('width'));
            var p_h = parseInt($(this).parent().css('height'));
            var w = parseInt($(this).css('width'));
            var h = parseInt($(this).css('height'));
            $(this).css({
                'margin-top': ((p_w - w) / 2 + (topCorrectionOffset ? topCorrectionOffset : 0)) + 'px',
                'margin-left': ((p_h - h) / 2 + (leftCorrectionOffset ? leftCorrectionOffset : 0)) + 'px'
            })
        })
    }
};

/**
 *
 * @param loadId
 * @param url
 */
function loadContent(loadId, url) {
    var load = $('#' + loadId);
    if (!isBlank(url)) {
        load.empty();
        load.load(url);
        load.trigger("create");
    }
}

/**
 *
 * @param obj
 * @returns {boolean}
 */
function isBlank(obj) {
    return !(obj !== null && obj !== undefined && obj !== '');
}
