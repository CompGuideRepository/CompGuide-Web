/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var webServicePath = "http://compguide.net/webresources/";
var messages;
var index = 11;
var numberNotificationsRequested = 0;
var totalNotifications = 0;
var currentNumberNotifications = 0;
var notification_content_height = 380;
var conListheight = 71;
var messCounter = 0;

function pushNotifications() {
    $.ajax({
        url: webServicePath + 'com.compguide.web.persistence.entities.notification/',
        type: 'GET',
        dataType: 'JSON',
        contentType: 'text/plain; charset=utf-8',
        cache: false,
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].message === null || data[i].message === undefined) {
                    PF('pushNotifications').renderMessage({"summary": "Task to Check",
                        "detail": "You must check the Task " +
                                data[i].eventID.scheduleTaskID.taskIdentifier +
                                " which started at " + new Date(moment((data[i].eventID.startDate).split("+")[0], "YYYYMMDDhhmmss").format()).toDateString() +
                                " at " + new Date(moment((data[i].eventID.startDate).split("+")[0], "YYYYMMDDhhmmss").format()).toTimeString(),
                        "severity": "info"});
                }
            }

        },
        error: function (err) {
            //alert(JSON.stringify(err));
        }
    });

}

function removejscssfile(filename, filetype) {
    var targetelement = (filetype === "js") ? "script" : (filetype === "css") ? "link" : "none"; //determine element type to create nodelist from
    var targetattr = (filetype === "js") ? "src" : (filetype === "css") ? "href" : "none"; //determine corresponding attribute to test for
    var allsuspects = document.getElementsByTagName(targetelement);

    for (var i = allsuspects.length; i >= 0; i--) { //search backwards within nodelist for matching elements to remove
        if (allsuspects[i] && allsuspects[i].getAttribute(targetattr) !== null && allsuspects[i].getAttribute(targetattr).indexOf(filename) !== -1)
            allsuspects[i].parentNode.removeChild(allsuspects[i]); //remove element by calling parentNode.removeChild()
    }
}

function showOutcome() {
    $.ajax({
        url: webServicePath + 'com.compguide.web.persistence.entities.outcome/canask',
        type: 'GET',
        cache: false,
        success: function (data) {
            if (data === 'true' && $(document.body).hasClass('modal-open') === false) {
                $('#formOutcome\\:showOutcome').click();
            }
        },
        error: function (err) {
            //alert(JSON.stringify(err));
        }
    });


}

function showStopCondition() {
    $.ajax({
        url: webServicePath + 'com.compguide.web.persistence.entities.stopconditionset/canask',
        type: 'GET',
        cache: false,
        success: function (data) {
            if (data === 'true' && $(document.body).hasClass('modal-open') === false) {
                $('#formStopCondition\\:showStopCondition').click();
            }
        },
        error: function (err) {
            //alert(JSON.stringify(err));
        }
    });


}

function msgNoti()
{
    $.ajax({
        url: webServicePath + 'com.compguide.web.persistence.entities.notification/count/' + false,
        type: 'GET',
        cache: false,
        success: function (data) {
            if (parseInt(data) > 0) {
                notificationCheck = data;
                var html = '<span id="notification-counter" class="notification-counter">' + data +
                        '</span> ';

                if (data !== messCounter && !$('#notificationLink notification-counter')) {
                    $(html).appendTo("#notificationLink").hide().fadeIn("slow");
                    messCounter = data;
                } else if (data !== messCounter) {
                    $("#notification-counter").remove();
                    $(html).appendTo("#notificationLink").hide().fadeIn("slow");
                    messCounter = data;
                }
            } else {
                $(".msg_count").hide();
            }
        },
        error: function (err) {
            //alert(JSON.stringify(err));
        }
    });

    $.ajax({
        url: webServicePath + 'com.compguide.web.persistence.entities.notification/countnonchecked/' + false,
        type: 'GET',
        cache: false,
        success: function (data) {
            if (parseInt(data) > 0) {
                totalNotifications = data;
            }
        },
        error: function (err) {
            //alert(JSON.stringify(err));
        }
    });
}



$(document).ready(function ()
{
    removejscssfile("dropdown.js", "js");
});

$(document).ready(function ()
{
    /* Set Number of Notifications */
    msgNoti();
    var track_load = 0; //total loaded record group(s)
    var loading = false; //to prevents multipal ajax loads
    /* Message Count */
    var auto_refresh = setInterval(function ()
    {

        msgNoti();
    }, 15000);
    var auto_refresh = setInterval(function ()
    {
        pushNotifications();
    }, 30000);
    var auto_refresh = setInterval(function ()
    {
        showOutcome();
    }, 10000);
    var auto_refresh = setInterval(function ()
    {
        showStopCondition();
    }, 10000);
    $("span.timeago").livequery(function () {
        $(this).timeago();
    });
    /* Notification link */
    $("#notificationLink").click(function ()
    {
        $("#notificationContainer").fadeToggle(300);
        $("#notification_count").fadeOut("slow");
        $("#notification-counter").fadeOut("slow");
        $("#notification_li").toggleClass("open");
        $.ajax({
            url: webServicePath + "com.compguide.web.persistence.entities.notificationread/read/" + true,
            type: 'PUT',
            contentType: 'application/json; charset=utf-8',
            success: function () {
            },
            error: function (err) {
                //alert(JSON.stringify(err));
            }
        });
        return false;
//                e.stopPropagation();
    });
    //Document Click
    $(document).click(function ()
    {
        $("#notificationContainer").hide();
        $("#notification_li").toggleClass("open");
        $("#notification_li")
                .removeClass("dropdown dropdown open")
                .addClass("dropdown dropdown");
    });
    //Popup Click
    $("#notificationContainer").click(function ()
    {
        $("#notificationContainer").hide();
//        $("#notification_li")
//                .removeClass("dropdown dropdown open")
//                .addClass("dropdown dropdown");

        return false;
    });
    /* Notification Scroll Apply*/
    $('#notifications').slimScroll({height: '380px'});
    /* Notification Scroll Results */
    $('.notifications').scroll(function (eve) {
        var a = 0;
        var s = $(document).height() - notification_content_height;
        conListheight = $('.length').height();
        var totalHeight = currentNumberNotifications * conListheight;
        if ($('#notifications').scrollTop() + $('#notifications').height() >= (totalHeight - 30) &&
                currentNumberNotifications < totalNotifications &&
                loading === false) {
            $('.animation_image').show(); //show loading image

        }

        if ($('#notifications').scrollTop() + $('#notifications').height() >= (totalHeight) &&
                currentNumberNotifications < totalNotifications &&
                loading === false) {
            var from = numberNotificationsRequested;
            var to = numberNotificationsRequested + index;
            loading = true;
            if (to > totalNotifications) {
                to = totalNotifications;
            }

            $.ajax({
                type: "GET",
                url: webServicePath + "com.compguide.web.persistence.entities.notification/" + from + "/" + to,
                dataType: 'JSON',
                contentType: 'text/plain; charset=utf-8',
                cache: false,
                success: function (json) {
                    var html = htmlNotificationContainer(json);
                    if ($.trim(html).length > 46) {
                        $('.animation_image').hide(); //show loading image
                        $("#notifications_container").append(html).fadeIn("slow");
                        loading = false;
                    } else {
                        $("#notifications_container").append(html).fadeIn("slow");
                        $("#notifications").removeClass('notifications');
                        $('.animation_image').hide(); //show loading image
                        loading = false;
                    }
                }
            });
            a = 1;
        }

        if (s > 128)
        {
            s = 128;
        }
        if ($('.notifications').scrollTop() >= s) {

            var from = numberNotificationsRequested;
            var to = numberNotificationsRequested + index;
            $('.animation_image').show(); //show loading image


            if (to > totalNotifications) {
                to = totalNotifications;
            }

            if (a === 0)
            {
                $.ajax({
                    type: "GET",
                    url: webServicePath + "com.compguide.web.persistence.entities.notification/" + from + "/" + to,
                    dataType: 'JSON',
                    contentType: 'text/plain; charset=utf-8',
                    cache: false,
                    success: function (html) {
                        html = htmlNotificationContainer(html);
                        if ($.trim(html).length > 46) {
                            $('.animation_image').hide(); //show loading image
                            $("#notifications_container").append(html).fadeIn("slow");
                            ;
                        } else {
                            $('.animation_image').hide(); //show loading image
                            $("#notifications_container").append(html).fadeIn("slow");
                            ;
                            $("#notifications").removeClass('notifications');
                        }
                    }
                });
                a = 1;
            }
        }

    });
    $(".noti_stbody").mouseup(function ()
    {
        return false;
    });
    var from = 0;
    var to = index;
    var URL = webServicePath + 'com.compguide.web.persistence.entities.notification/' + from + "/" + to;
    $.ajax({
        type: "GET",
        url: URL,
        cache: false,
        dataType: 'JSON',
        contentType: 'text/plain; charset=utf-8',
        success: function (json) {
            if (json) {
                var html = htmlNotificationContainer(json);
                $("#notifications_container").html(html);
            }
        }
    });
});
function deleteNotification(obj)
{

    jConfirm("Sure you want to check the task?", '',
            function (r)
            {
                if (r === true)
                {
                    $.ajax({
                        type: "DELETE",
                        url: webServicePath + "com.compguide.web.persistence.entities.notification/" + obj,
                        cache: false,
                        dataType: 'JSON',
                        contentType: 'text/plain; charset=utf-8',
                        success: function () {
                            $("#" + obj).remove().fadeOut("slow");
                            currentNumberNotifications--;
                        }
                    });
                }
            });
    return false;
}

function popupNotification(obj)
{

//var X=$(this).attr("my");

    jConfirm("Sure you want to check the task?", '',
            function (r)
            {
                if (r === true)
                {
                    $.ajax({
                        type: "DELETE",
                        url: webServicePath + "com.compguide.web.persistence.entities.notification/" + obj,
                        cache: false,
                        dataType: 'JSON',
                        contentType: 'text/plain; charset=utf-8',
                        success: function () {
                            $("#" + obj).remove().fadeOut("slow");
                            currentNumberNotifications--;
                            var eventID = 0;
                            var scheduleTaskID = 0;
                            for (var i = 0; i < messages.length; i++) {
                                if (messages[i][0].toString() === obj.toString()) {
                                    eventID = messages[i][2];
                                    scheduleTaskID = messages[i][3];
                                }
                            }

                            $.ajax({
                                url: webServicePath + "com.compguide.web.persistence.entities.task/" + scheduleTaskID + "/incrementrepetitionvalue",
                                type: 'PUT',
                                contentType: 'application/json; charset=utf-8',
                                success: function (json) {
                                    PF('meucalendario').update();
                                    PF('timeLineTaskEvents').update();
                                }
                            });
                            $.ajax({
                                url: webServicePath + "com.compguide.web.persistence.entities.event/event/" + eventID + "/" + true,
                                type: 'PUT',
                                contentType: 'application/json; charset=utf-8',
                                success: function (json) {
                                    PF('meucalendario').update();
                                    PF('timeLineTaskEvents').update();
                                }
                            });
                        }
                    });
                }
            });
    return false;
}

function htmlNotificationContainer(json)
{
    messages = initArray(messages, json.length, 4);
    var notificationContainer = '';
    numberNotificationsRequested += json.length;
    for (var i = 0; i < json.length; i++) {
        if ((json[i].message === null || json[i].message === undefined) && json[i].eventID.checked === false) {
            messages[i][0] = json[i].notificationID;
            messages[i][1] = json[i].viewed;
            messages[i][2] = json[i].eventID.eventID;
            messages[i][3] = json[i].eventID.scheduleTaskID.scheduleTaskID;
            currentNumberNotifications++;
            var onclick = (json[i].eventID.canCheck === true) ? false : true;
            var text = '';
            if (onclick === false) {
                text = 'onclick="popupNotification(' + json[i].notificationID + ');"';
            } else {
                text = 'style="display:none"';
            }

            notificationContainer += '<div class="length">' +
                    '<a href="#" class="noti_a" id="' + json[i].notificationID + '" >' +
                    '<div class="conList" ' + '>' +
                    '<span ' + text +
                    'class="reply_stdelete" href="#"' +
                    '" original-title="Delete Update" disabled="true"' +
                    '" >' +
                    '</span>' +
                    '<div class="noti_stimg">' +
                    '</div>' +
                    '<div class="noti_sttext">' +
                    '<b>' + json[i].eventID.scheduleTaskID.taskIdentifier + '</b>' + ' must be finished' +
                    '<div class="noti_sttime">' +
                    '<span class="timeago" title=' +
                    new Date(moment((json[i].eventID.endDate).split("+")[0], "YYYYMMDDhhmmss")).toISOString() + '> </span>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</a>' +
                    '</div>';
        } else if (json[i].message !== null) {
            currentNumberNotifications++;
            var text = 'onclick="deleteNotification(' + json[i].notificationID + ');"';
            notificationContainer += '<div class="length">' +
                    '<a href="#" class="noti_a" id="' + json[i].notificationID + '" >' +
                    '<div class="conList" ' + '>' +
                    '<span ' + text +
                    'class="reply_stdelete" href="#"' +
                    '" original-title="Delete Update" disabled="true"' +
                    '" >' +
                    '</span>' +
                    '<div class="noti_stimg">' +
                    '</div>' +
                    '<div class="noti_sttext">' +
                    '<b>' + json[i].message + '</b>' + ' must be finished' +
                    '<div class="noti_sttime">' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</a>' +
                    '</div>';
        }
    }

    if (numberNotificationsRequested === 0 && json.length < 1) {
        return '<h4 id="noupdates">No Notifications Found</h4>';
    }

    return notificationContainer;
}

function initArray(array, rows, col)
{
    array = new Array(rows);
    for (var i = 0; i < rows; i++) {
        array[i] = new Array(col);
    }

    return array;
}



