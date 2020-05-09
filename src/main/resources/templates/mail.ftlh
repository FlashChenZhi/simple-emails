<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>$Title$</title>
</head>
<body>
<h1>Freemarker模板定制邮件</h1>
<div>
    <span>${username}</span>
    Hello, Welcome Join Us!
</div>
您的入职信息如下：
<table border="1">
    <tr>
        <td style="color: deepskyblue">职位</td>
        <td>${position}</td>
    </tr>
    <tr>
        <td style="color: deepskyblue">职称</td>
        <td>${job}</td>
    </tr>
    <tr>
        <td style="color: deepskyblue">薪资</td>
        <td>${salary}</td>
    </tr>
    <tr>
        <td style="color: deepskyblue">部门</td>
        <td>${department}</td>
    </tr>
</table>
<div style="color: #ff1a0e;font-size: 20px">
    希望在未来的日子里，与君携手共进！
</div>
</body>
</html>