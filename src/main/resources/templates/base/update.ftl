
<head>
    <#include "../includable/bootstrap.ftl">
    <#include "../includable/jquery.ftl">
    <#include "../includable/baseFieldValidator.ftl">

</head>
<html>
<body>
    <h1>${page}</h1>
    <form id="updateForm" action="" method="POST">
        <#include "../includable/formUpdateContent.ftl">
        <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
        <br>
            <input type="submit" value="update"/>
        </br>
    </form>
    <a href="../list">Back</a>
</body>
</html>