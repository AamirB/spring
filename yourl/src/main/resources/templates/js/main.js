var myusername = $("#accountId").val();
$.ajax({
  type: "GET",
  url: "http://localhost:8080/register/",
  data: myusername,
  cache: false,
  success: function(data){
	  console.log("success");
  }
});