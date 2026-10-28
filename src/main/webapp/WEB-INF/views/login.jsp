<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>로그인</h2>

<c:if test="${not empty errorMsg}">
    <div class="alert alert-danger">${errorMsg}</div>
</c:if>

<div class="row justify-content-center">
    <div class="col-md-5">
        <div class="card shadow-sm">
            <div class="card-body">
                <h2 class="card-title text-center mb-4">로그인</h2>

                <form method="post" action="/login">
                    <div class="mb-3">
                        <label class="form-label">아이디</label>
                        <input type="text" name="loginId" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">비밀번호</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary">로그인</button>
                    <div class="mt-3">
                        <a href="/signup">회원가입</a>
                    </div>
                </form>

            </div>
        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>