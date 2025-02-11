<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit User | Quiz App</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>Edit User</h2>
    <form action="<%= request.getContextPath() %>/admin/edit-user" method="post">
        <input type="hidden" name="userId" value="${user.userId}">

        <div class="mb-3">
            <label>Email</label>
            <input type="email" name="email" class="form-control" value="${user.email}" required>
        </div>

        <div class="mb-3">
            <label>First Name</label>
            <input type="text" name="firstname" class="form-control" value="${user.firstname}" required>
        </div>

        <div class="mb-3">
            <label>Last Name</label>
            <input type="text" name="lastname" class="form-control" value="${user.lastname}" required>
        </div>

        <div class="mb-3">
            <label>Is Active?</label>
            <select name="isActive" class="form-select">
                <option value="true" ${user.isActive ? "selected" : ""}>Yes</option>
                <option value="false" ${!user.isActive ? "selected" : ""}>No</option>
            </select>
        </div>

        <div class="mb-3">
            <label>Role</label>
            <select name="isAdmin" class="form-select">
                <option value="true" ${user.isAdmin ? "selected" : ""}>Admin</option>
                <option value="false" ${!user.isAdmin ? "selected" : ""}>User</option>
            </select>
        </div>

        <button type="submit" class="btn btn-success">Save Changes</button>
        <a href="<%= request.getContextPath() %>/admin/users" class="btn btn-secondary">Cancel</a>
    </form>
</div>

</body>
</html>
