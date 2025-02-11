<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Management | Quiz App</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>User Management</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>User ID</th>
            <th>Email</th>
            <th>Name</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.userId}</td>
                <td>${user.email}</td>
                <td>${user.firstname} ${user.lastname}</td>
                <td>${user.isAdmin ? "Admin" : "User"}</td>
                <td>
                    <a href="<%= request.getContextPath() %>/admin/edit-user/${user.userId}" class="btn btn-primary btn-sm">Edit</a>
                    <a href="<%= request.getContextPath() %>/admin/delete-user/${user.userId}" class="btn btn-danger btn-sm">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
