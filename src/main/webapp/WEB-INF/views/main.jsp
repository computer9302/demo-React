<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="row justify-content-center">
<div class="col-md-6">
<div class="card shadow-sm">
<div class="card-body text-center">
<h2 class="card-title mb-3">메인</h2>
<p class="card-text">안녕하세요, <strong>${loginMember.name}</strong>님</p>
<a href="/posts" class="btn btn-primary me-2">게시글 목록</a>
<form method="post" action="/logout" class="d-inline">
    <button type="submit" class="btn btn-outline-secondary">로그아웃</button>
</form>
</div>
</div>
</div>
</div>


<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>