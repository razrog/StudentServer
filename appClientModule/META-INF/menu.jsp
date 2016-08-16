<!DOCTYPE html>
<html>

<head>
		<link href="../css/wrapperClassStyle.css" rel="stylesheet" type="text/css" />
		
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>	
	
<script>
$(document).ready(function(){
	$('#navigation').fadeToggle(1000).fadeToggle(1000);
	
})
</script>
		
		
		
		
</head>

<body>

<div id = "navigation">

<div id="nav">
<ul id="menu">
<li></li>
</ul>
<h3>Deposits</h3>
<ul id="menu">
 <li><a href="Controller?command=createNewDeposit">Create New Deposit</a></li>
 <li><a href="Controller?command=viewClientDeposits">View/Pre-Open Deposit</a></li>
</ul>  

<h3><%=request.getSession(false).getAttribute("userName") %></h3>
<ul id="menu">
 <li><a href="Controller?command=viewClientProfile">View/Update My Profile</a></li>
 <li><a href="Controller?command=viewClientActivities">View My Activities</a></li>
</ul>
<h3>Account</h3>
<ul id="menu">
 <li><a href="Controller?command=depositToAccount">Deposit</a></li>
 <li><a href="Controller?command=withdrawFromAccount">Withdraw</a></li>
 <li><a href="Controller?command=viewUserAccount">View My Account</a></li>
</ul>  

<h3>MBank</h3>
<ul id="menu">
 <li><a href="Controller?command=viewSystemProp">View System Properties</a></li>
</ul>  
</div>

</div>

</body>
</html>