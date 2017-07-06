/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var username = false;
var name = false;
var lastname = false;
var email = false;
var password = false;
var confirmpassword = false;
var lastElementFocus;

function changeIcon(e, icons) {
    var $emt = $(e.target).parents('.panel'),
            $ico = $emt.find('h4 a i'),
            evt = e.type,
            isIn = ($emt.find('.panel-collapse').hasClass('in')),
            icoClosed = icons[0], //icon when panel is close
            icoOpen = icons[1], //icon when panel is open
            icoHover = icons[2];			//icon when panel is hover

    $ico.removeClass();

    if (evt === 'show') {
        $ico.addClass(icoOpen);
    } else if (evt === 'hide') {
        $ico.addClass(icoClosed);
    } else if (evt === 'mouseenter') {
        $ico.addClass(icoHover);
    } else if (evt === 'mouseleave') {
        (isIn) ? $ico.addClass(icoOpen) : $ico.addClass(icoClosed);
    }
}

function bindChangeIcon(collapse, heading, icons) {
    collapse.on('hide.bs.collapse', function (e) {
        changeIcon(e, icons);
    });
    collapse.on('show.bs.collapse', function (e) {
        changeIcon(e, icons);
    });
    heading.on('mouseenter', function (e) {
        changeIcon(e, icons);
    });
    heading.on('mouseleave', function (e) {
        changeIcon(e, icons);
    });
}

function change() {
    var $collapse1 = $('#accordion'),
            $heading1 = $collapse1.find('.panel-heading'),
            icons1 = ['fa fa-angle-down', 'fa fa-angle-down', 'fa fa-angle-up'];

    bindChangeIcon($collapse1, $heading1, icons1);

    var $collapse2 = $('#accordion-work2'),
            $heading2 = $collapse2.find('.panel-heading'),
            icons2 = ['fa fa-minus-square', 'fa fa-plus-square', 'fa fa-plus-square'];

    bindChangeIcon($collapse2, $heading2, icons2);

    var $collapse3 = $('#accordion-work3'),
            $heading3 = $collapse3.find('.panel-heading'),
            icons3 = ['fa fa-minus-square', 'fa fa-plus-square', 'fa fa-plus-square'];

    bindChangeIcon($collapse3, $heading3, icons3);
}


function lastFocus(obj) {
    lastElementFocus = obj;
}

function clickButton() {
    $('#' + lastElementFocus).focus();
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

function startCountDown(obj) {
    switch (obj) {
        case 'schedule':
            scheduleCountDown();
            break;
        case 'timeline':
            timelineCountDown();
            break;

        default :
            break;
    }
}

function timelineCountDown() {

    var dat = $("#timelinecontent\\:timelineTime_input").val();  //$("p[id]").attr("id")
    var dateFormat = dat.toString().replace(" ", "T");
    var newDate = new Date(dateFormat);
    var currentDate = new Date(),
            finished = false,
            availiableExamples = {
                set15daysFromNow: 15 * 24 * 60 * 60 * 1000,
                set5minFromNow: 5 * 60 * 1000,
                set1minFromNow: 1 * 60 * 1000
            };

    function callback(event) {
        $this = $(this);
        switch (event.type) {
            case "seconds":
            case "minutes":
            case "hours":
            case "days":
            case "weeks":
            case "daysLeft":
                $this.find('span#' + event.type + "TimeLine").html(event.value);
                if (finished) {
                    $this.fadeTo(0, 1);
                    finished = false;
                }
                break;
            case "finished":
                $this.fadeTo('slow', .5);
                finished = true;
                break;
        }
    }

    var value = newDate.valueOf() - currentDate.valueOf();
    if (value < 0) {
        $("#content\\:timeleftlabelTimeLine").html("Time Expired")
        value = currentDate.valueOf() - newDate.valueOf();
    }

    $('div#clockTimeLine').countdown(value + currentDate.valueOf(), callback);

    $('select#exampleDate').change(function () {
        try {
            var $this = $(this),
                    value;
            currentDate = new Date();
            switch ($this.attr('value')) {
                case "other":
                    value = prompt('Set the date to countdown:\nThe hh:mm:ss parameters are opitionals', 'YYYY/MM/DD hh:mm:ss');
                    break;
                case "daysFromNow":
                    value = prompt('How many days from now?', '');
                    value = new Number(value) * 24 * 60 * 60 * 1000 + currentDate.valueOf();
                    break;
                case "minutesFromNow":
                    value = prompt('How many minutes from now?', '');
                    value = new Number(value) * 60 * 1000 + currentDate.valueOf();
                    break;
                default:
                    value = availiableExamples[$this.attr('value')] + currentDate.valueOf();
            }
            $('div#clockTimeLine').countdown(value, callback);
            $this.find('option:first').attr('selected', true);
        } catch (e) {
            alert(e);
        }
    });
}

function scheduleCountDown() {

    var dat = $("#content\\:taskTime_input").val();  //$("p[id]").attr("id")
    if (typeof dat !== "undefined") {
        var dateFormat = dat.toString().replace(" ", "T");
        var from = dateFormat.split("-");
        var newDate = new Date(from[2], from[1] - 1, from[0]);
        var currentDate = new Date(),
                finished = false,
                availiableExamples = {
                    set15daysFromNow: 15 * 24 * 60 * 60 * 1000,
                    set5minFromNow: 5 * 60 * 1000,
                    set1minFromNow: 1 * 60 * 1000
                };

        function callback(event) {
            $this = $(this);
            switch (event.type) {
                case "seconds":
                case "minutes":
                case "hours":
                case "days":
                case "weeks":
                case "daysLeft":
                    $this.find('span#' + event.type).html(event.value);
                    if (finished) {
                        $this.fadeTo(0, 1);
                        finished = false;
                    }
                    break;
                case "finished":
                    $this.fadeTo('slow', .5);
                    finished = true;
                    break;
            }
        }

        var value = newDate.valueOf() - currentDate.valueOf();
        if (value < 0) {
            $("#content\\:timeleftlabel").html("Time Expired");
            $("#content\\:timeleftp").html("Time remaining until the next execution.");
            value = currentDate.valueOf() - newDate.valueOf();
        }

        $('div#clock').countdown(value + currentDate.valueOf(), callback);

        $('select#exampleDate').change(function () {
            try {
                var $this = $(this),
                        value;
                currentDate = new Date();
                switch ($this.attr('value')) {
                    case "other":
                        value = prompt('Set the date to countdown:\nThe hh:mm:ss parameters are opitionals', 'YYYY/MM/DD hh:mm:ss');
                        break;
                    case "daysFromNow":
                        value = prompt('How many days from now?', '');
                        value = new Number(value) * 24 * 60 * 60 * 1000 + currentDate.valueOf();
                        break;
                    case "minutesFromNow":
                        value = prompt('How many minutes from now?', '');
                        value = new Number(value) * 60 * 1000 + currentDate.valueOf();
                        break;
                    default:
                        value = availiableExamples[$this.attr('value')] + currentDate.valueOf();
                }
                $('div#clock').countdown(value, callback);
                $this.find('option:first').attr('selected', true);
            } catch (e) {
                alert(e);
            }
        });
    }
}

