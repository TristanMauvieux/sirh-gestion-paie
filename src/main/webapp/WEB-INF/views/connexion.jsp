<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Paie - App</title>
        <link rel="stylesheet" href='<c:url value="/bootstrap-3.3.7-dist/css/bootstrap.min.css"></c:url>'>
    </head>
    <body class="container">        

        <h1>Connexion</h1>

        <!-- Spring Security s'attend aux param�tres "username" et "password" -->
        <form method="post">
            <input name="username" placeholder="Username" class="form-control">
            <input name="password" placeholder="password" class="form-control">
            <input type="submit" value="Se connecter" class="btn btn-danger">
        
        <sec:csrfInput/>
        </form>

        <!-- en cas d'erreur un param�tre "error" est cr�� par Spring Security -->
        <c:if test="${param.error !=null}">
            Erreur d'authentification
        </c:if>
        
        <script src="/bootstrap-3.3.7-dist/js/bootstrap.min.js" />
    </body>
</html>