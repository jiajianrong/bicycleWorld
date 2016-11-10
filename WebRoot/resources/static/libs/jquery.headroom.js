(function($) {

  if(!$) {
    return;
  }

  ////////////
  // Plugin //
  ////////////

  $.fn.headroom = function(option) {
    return this.each(function() {
      var $this   = $(this),
        data      = $this.data('headroom'),
        options   = typeof option === 'object' && option;

      options = $.extend(true, {}, Headroom.options, options);

      if (!data) {
        data = new Headroom(this, options);
        data.init();
        $this.data('headroom', data);
      }
      if (typeof option === 'string') {
        data[option]();
      }
    });
  };

  //////////////
  // Data API //
  //////////////

  //  $('[data-headroom]').each(function() {
  //    var $this = $(this);
  //    $this.headroom($this.data());
  //  });
  
  
  
  
  $(".top-menu-wrap").headroom({
      "tolerance": 5,
      "offset": 205,
      "classes": {
          "initial": "animated",
          "pinned": "slideDown",
          "unpinned": "slideUp"
      }
//    onPin: function(){
//        $(".pinned").css('marginTop','75px');
//    },
//    onUnpin: function(){
//        $(".pinned").css('marginTop','30px');
//    }
  });

}(window.Zepto || window.jQuery));