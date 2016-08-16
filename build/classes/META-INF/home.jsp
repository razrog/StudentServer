<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Jinni - Students Server</title>
<style>

div,a
{
    display: inline-block;
    padding: 0 20px;
    margin-right:4px;
    background:#F7F7F7;
    color:Black;
    text-decoration:none;
    font: normal 12px Arial;
    line-height: 24px;
    border:1px solid #CAD0DB;
    border-bottom:4;
    color:#666;
    vertical-align:top;
    text-decoration:none;
    
}


</style>

  <script src="https://code.jquery.com/jquery-1.10.2.js"></script>

<script>
function addNew()
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", '/controller', false ); // false for synchronous request
    xmlHttp.send( null );
    return xmlHttp.responseText;
}
</script>


</head>
<body>

<div id="#1">
<h1>Student Server System</h1>
</div>




<div>

<h2>Add New Student</h2>
<form name="addNewStudent" method="get" action="students/add">


ID     : <input type="text" name="studentId" /><br />
Name   : <input type="text" name="studentName" /><br />
Gender : Male<input type="radio" name="studentGender" />Female<input type="radio" name="studentGender" /><br />
GPA    : <input type="text" name="studentGPA" /><br />

<input type="submit" value="Submit"  />
</form>
</div>

<div>

<h2>Delete Student</h2>
<form name="deleteStudent" method="get" action="controller">

ID :     <input type="text" name="studentId" /><br />

<input type="submit" value="Submit" />
</form>
</div>

<div>
<h2>View Student Details</h2>
<form name="getStudent" method="get" action="controller">

ID :     <input type="text" name="studentId" /><br />

<input type="submit" value="Submit" />
</form>
</div>

<div>
<h2>Extra Functions</h2>
<a href="" >Fill DB</a>
<a href="" >Clean DB</a>

</div>
</body>
</html>