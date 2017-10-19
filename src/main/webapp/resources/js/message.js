//添加会话信息
function appendMessage(LoR,message){
    var clazz;
    var img;
    //判断会话信息的位置,
    if (LoR=='left') {
        clazz="message";
        img="1_copy";
    }else{
        clazz="message right";
        img="2_copy";
    }
    var str1='<div class=\"'+clazz+'\">'
        +'<img src=\"../resources/images/message/'+img+'.jpg\" />'
        +'<div class=\"bubble\">'+message
        +'<span></span>'
        +'</div></div>';

    if($('#chat-messages div').length ==0){
        $('#chat-messages label').after(str1);
        return;
    }
    //聚焦在对话框底部
    if(clazz=='message'){
        $('.message.right:last-child').after(str1);
        var height=$('.message:last div').outerHeight();
        $('.message:last').css('margin-bottom',height);
        $('#chat-messages').scrollTop($('#chat-messages')[0].scrollHeight);
        return;
    }else if(clazz=='message right'){
        $('.message:last-child').after(str1);
        var height=$('.message.right:last div').outerHeight();
        $('.message.right:last').css('margin-bottom',height);
        $('#chat-messages').scrollTop($('#chat-messages')[0].scrollHeight);
        return;
    }
};

function sendMessage(timestamp){

    var userMessage=$('#sendmessage input').val();
    $('#sendmessage input').val("");
    if (userMessage.trim()=="" || userMessage == 'message...') {
        alert("请输入内容!");
        return;
    }
    appendMessage('right',userMessage);
    $.ajax({
        type:"POST",
        url:"/autoReply",
        data : JSON.stringify({"info":userMessage, "userid":timestamp}),
        dataType:"json",
        contentType:"application/json;charset=UTF-8",
        success:function(responseData){
            var data = eval('('+responseData+')');
            if ( data.url == null || data.url == undefined ) {
                appendMessage('left', data.text);
            }else {
                appendMessage('left',data.text.replace(/\n/g,"</br>")+"<a target='_blank' style='float: right' href='"+data.url+"'>Go To</a>");
            }
        }
    })
};

$(document).ready(function () {
    var timestamp=new Date().getTime();
    var historicalMessage = "";
    $('#sendmessage input').focus(function () {
        if ($(this).val() == 'message...') {
            $(this).val('');
        }
    });
    $('#sendmessage input').blur(function () {
        if ($(this).val() == '') {
            $(this).val('message...');
        }
    });
    $('.friend').ready(function () {
        var myDate = new Date();
        var clone = '<img src="../resources/images/message/1_copy.jpg" />';
        $(clone).css({ 'top': top }).addClass('floatingImg').appendTo('#chatbox');
        setTimeout(function () {
            $('#profile p').addClass('animate');
            $('#profile').addClass('animate');
        });
        setTimeout(function () {
            $('#chat-messages').addClass('animate');
        });
        $('.floatingImg').animate({
            'width': '68px',
            'left': '108px',
            'top': '20px'
        });
        $('#chatview').fadeIn();
        $('.message').not('.right').find('img').attr('src', $(clone).attr('src'));
        $('#profile p').html("coco");
        $('#chat-messages label').html(myDate.getFullYear()+'-'+(myDate.getMonth()+1)+'-'+myDate.getDate());
        appendMessage('left',' o(*≧▽≦)ツ hi~ 你好呀~');
    });

    //单击控件及上下选词
    $('#send').click(function(){
        historicalMessage = $('#sendmessage input').val();
        sendMessage(timestamp);
    });
    $('#sendText').keydown(function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        switch (e.keyCode){
            //enter
            case 13:
                historicalMessage = $('#sendmessage input').val();
                sendMessage(timestamp);
                break;
            //up
            case 38:
                $('#sendmessage input').val(historicalMessage);
                break;
            //down
            case 40:
                $('#sendmessage input').val('')
                break;
        }
    });
});