var Lock = function () {

    return {
        //main function to initiate the module
        init: function () {

             $.backstretch([
		        "/plugins/bootstrap.admin.theme/assets/img/bg/1.jpg",
		        "/plugins/bootstrap.admin.theme/assets/img/bg/2.jpg",
		        "/plugins/bootstrap.admin.theme/assets/img/bg/3.jpg",
		        "/plugins/bootstrap.admin.theme/assets/img/bg/4.jpg"
		        ], {
		          fade: 1000,
		          duration: 8000
		      });
        }

    };

}();