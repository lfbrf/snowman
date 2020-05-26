 function openTourist(id){
	 window.location.href = "turistico/" + id;
  }	

  var allTourists = "";
  function refreshIdCategory(){
	  $("#idCategory").val($("#category").val());
  }
  
  function checkField(campo){
	  if ($(campo + "").val() == ""){
		  showMessage("error", "Faltou preencher um ou mais campos", 6000);
		  $("#closeModal").click();
		  return false
	  }
	  return true;
		  
  }
  
  $(function(){
      $("#header").load("components/header.html"); 
      $("#footer").load("components/footer.html");	
   });
  
  var arrayBuffer;
  var fileReader = new FileReader();
  fileReader.onload = function(event) {
      arrayBuffer = event.target.result;
      var bufView = new Int8Array(arrayBuffer);
      $("#picture").val(bufView);
      
      if (arrayBuffer && arrayBuffer.byteLength !== undefined){
    	 
    	  $("#picture").val( arrayBuffer );
    	  alert(arrayBuffer);
    	   $("#saveTourist").click();
      } 
      	
  };
  
  function newTourist(){
	  console.log($("#imgTouristUpload").val());
	// valida campos
	  if (checkField("imgTouristUpload") && checkField("name") && checkField("latLocalization") 
			  && checkField("longLocalization") && checkField("longLocalization")){

		 
		  $("#picture").val(  $('#imgTouristUpload').attr('src'));
		   $("#saveTourist").click();
		 
	  }
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
			
	
	function initAutocomplete() {
		var place = ""; 
		    autocomplete = new google.maps.places.Autocomplete(
				(document.getElementById('touristLocation')),
		        {types: ['geocode']});

		    autocomplete.addListener('place_changed', fillInAddress);
	}
	function fillInAddress() {
		place = autocomplete.getPlace();
		$("#latLocalization").val(place.geometry.location.lat());
		$("#longLocalization").val(place.geometry.location.lng());
     }	
  