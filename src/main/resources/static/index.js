var latSearch = "";
var lngSearch = "";
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
      autoCompleteSearch();
   });
  

  
  function newTourist(){
	  console.log($("#imgTouristUpload").val());
	// valida campos
	  if (checkField("imgTouristUpload") && checkField("name") && checkField("latLocalization") 
			  && checkField("longLocalization") && checkField("longLocalization")){

		 
		   $("#picture").val(  $('#imgTouristUpload').attr('src'));
		  //$("#picture").val(  "BLA BLA BLA");
		   $("#saveTourist").click();
		 
	  }
  }

	
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
	
	function autoCompleteSearch(){
		if (typeof google == "undefined"){
			setTimeout(function(){ 
				autocomplete = new google.maps.places.Autocomplete(
						(document.getElementById('searchNear')),
				        {types: ['geocode']});
				autocomplete.addListener('place_changed', fillInAddressSearch);
			}, 1500);
		}
		else{
			autocomplete = new google.maps.places.Autocomplete(
					(document.getElementById('searchNear')),
			        {types: ['geocode']});
			autocomplete.addListener('place_changed', fillInAddressSearch);
		}
		
	}
	
	function fillInAddressSearch() {
		place = autocomplete.getPlace();
		lngSearch = place.geometry.location.lng();
		latSearch = place.geometry.location.lat();
		if ($("#searchNear").val() == ""){
			latSearch = "";
			lngSearch = "";
		}
     }
	
	function retriveNearPlaces(){
		arrayIdToHide = [];
		if (latSearch != "" && lngSearch != ""){
			for (var i = 0; i< arrayIds.length; i++){
				distance = calculateDistanceTourist(latSearch, lngSearch, arrayLatLocalization[i], arrayLongLocalization[i]);
				if (distance >  5000){
					arrayIdToHide.push(arrayIds[i]);
				}
			}
		}
		
		for (var i =0; i<arrayIds.length; i++){
			$("#listTouristId"+arrayIds[i]).show();
		}
		for (var i = 0; i<arrayIdToHide.length; i++){
			$("#listTouristId"+arrayIdToHide[i]).hide();
		}
		
		
	}
		
	
	function calculateDistanceTourist(latitude1, longitude1, latitude2, longitude2 ){
		var distance = google.maps.geometry.spherical.computeDistanceBetween
		(new google.maps.LatLng(latitude1, longitude1), new google.maps.LatLng(latitude2, longitude2)); 
		return distance;
	}
	
	function searchByName(){
		window.location.href = "buscanome?nome=" + $("#searchName").val();
	}
  