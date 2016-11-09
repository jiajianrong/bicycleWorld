
(function($) {
    
    
    
    $(".top-menu-wrap").headroom({
        "tolerance": 5,
        "offset": 205,
        "classes": {
            "initial": "animated",
            "pinned": "slideDown",
            "unpinned": "slideUp"
        }
//      onPin: function(){
//          $(".pinned").css('marginTop','75px');
//      },
//      onUnpin: function(){
//          $(".pinned").css('marginTop','30px');
//      }
    });
    
    $(".pinned").pin({
        //containerSelector: ".main-container"
        top: 10, 
        bottom: 10
    })
})(jQuery);