
    var url = window.location.toString();

    var code = getParameterByName("code", url);
    var state = getParameterByName("state", url);

    getLoginButtonUri();
    function getLoginButtonUri() {
        $.ajax({
            url: "/facebook/getLoginUri",
            success: function (res, status) {
                $("#login").attr("href", res);
            }
        })
    }

    isAuthenticated();

    function isAuthenticated() {
        $.ajax({
            url: "/facebook/auth",
            success: function (res, status) {
                console.log("Authenticated: ", res)
                if (res === true) {
                    $("#login").hide();
                    $("#logout").show();
                    //showUserInfo()
                    setUserName();
                }
                else {
                    $("#logout").hide();
                    $("#login").show();
                    $("#userName").text("Log com sua conta do facebook para todas as funcionalidades");
                }
            },
            error: function (res, status) {
                console.error(res, status)
            }
        });
    }

    function showUserInfo() {
        $.ajax({
            url: "/facebook/userinfo",
            success: function (res, status) {
                //$("#userinfo").html(JSON.stringify(res, null, 2));
            }
        });
    }
    
    function setUserName(){
    	 $.ajax({
             url: "/facebook/userinfo",
             success: function (res, status) {
            	 $("#userName").text(res.name);
             }
         });
    }

    $("#logout").click(function () {
        $.ajax({
            url: "/facebook/logout",
            success: function (res, status) {
                console.log(res)
                window.location.replace("./")
            }
        })
    });

    if (code && state) {
        $.ajax({
            url: "/facebook/login?code=" + code + "&state=" + state,
            success: function (res, status) {
                console.log(status);
                window.location.replace("./")
            }
        });
    }

    function getParameterByName(name, url) {
        if (!url) url = window.location.href;
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }
    
    $('#exampleModal').on('show.bs.modal', function (event) {
    	  var button = $(event.relatedTarget) // Button that triggered the modal
    	  var recipient = button.data('whatever') 
    	  var modal = $(this)
    	  modal.find('.modal-title').text('New message to ' + recipient)
    	  modal.find('.modal-body input').val(recipient)
     });
    
    function showMessage(id, message, time){
    	$("#" + id).append(message).slideToggle();
    	setTimeout(function(){
    		$("#" + id).slideToggle();
    	}, time);
    }
    
    function validateCategory (){ 
    	
        for(var i =0; i<categories.length; i++){
        	if (categories[i].name == $("#name").val()){
        		$("#closeModal").click();
        		showMessage("error", "Categoria já existe, por favor tente novamente", 5000);
        		return;
        	}
        }
        showMessage("success", "Categoria cadastrada com sucesso", 2000);
        $("#saveCategory").click();
    }    
   


    
    
    $(document).on('change', '.btn-file :file', function() {
		var input = $(this),
			label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
		input.trigger('fileselect', [label]);
	});

	$('.btn-file :file').on('fileselect', function(event, label) {
		    
		    var input = $(this).parents('.input-group').find(':text'),
		        log = label;
		    
		    if( input.length ) {
		        input.val(log);
		    } else {
		        if( log ) alert(log);
		    }
	    
	});
		
	function readURL(input) {
		    if (input.files && input.files[0]) {
		        var reader = new FileReader();
		        
		        reader.onload = function (e) {
		            $('#imgTouristUpload').attr('src', e.target.result);
		        }
		        
		        reader.readAsDataURL(input.files[0]);
		    }
		}

		$("#imgInp").change(function(){
		    readURL(this);
		}); 
;