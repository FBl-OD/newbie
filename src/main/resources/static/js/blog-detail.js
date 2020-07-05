

function like(btn,blogId) {
    let url;
    let liked=$(btn).attr("status");
    if(liked=='true')
        url="/blog/unlike/"+blogId;
    else
        url="/blog/like/"+blogId;
    console.log(url);
    $.post(
        url,
        { },
        function(res) {
            if(res.code == 1) {
                $(btn).siblings('span').text(res.data.likes);
                $(btn).attr("src",res.data.status==1?'/img/favorite.svg':'/img/favorite_border.svg')
                $(btn).attr("status",res.data.status==1?'true':'false');
                console.log(res.data.status);
                console.log(res.data.likes);
            } else {
                alert(res.data.msg);
            }
        }
    );
}