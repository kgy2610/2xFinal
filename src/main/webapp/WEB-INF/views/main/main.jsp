<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AGIT</title>
</head>
<style>
@charset "UTF-8";
@font-face {
    font-family: 'NanumSquareRound';
    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/NanumSquareRound.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
*{
    width: 100%; height: 100%;
    box-sizing: border-box;
    margin: 0 auto;
    padding: 0;
    overflow: hidden;
}
#main-bg{
    width: 100%; height: 100%;
    position: absolute;
    z-index: -1;
    background-image: url('resources/img/main-bg.png');
    background-repeat: no-repeat;
}
#input-btn > button{
    width: 490px; height: 128px;
    display: block;
    cursor: pointer;
    color: #6C584C;
    font-size: 50px;
    font-family: 'NanumSquareRound';
    font-weight: 800;
    position: absolute;
    background-color: #FAEDCD;
    border: 0px;
    border-radius: 50px;
    right: 280px;
    bottom: 140px;
}
#input-btn > button:hover{
    background-color: #BA8963;
    color: #fff;
}
</style>
<body>
    <div id="main-bg"></div>
    <div id="input-btn">
        <button onclick="inputLogin()"> 들어가기 +</button>
    </div>
    <script>
        function inputLogin(){
        	window.location.href = 'login';
        }
    </script>
    
</body>
</html>