<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>글 수정</h2>

<form method="post" action="/posts/${post.postId}/edit">
    <div class="mb-3">
        <label class="form-label">제목</label>
        <input type="text" name="title" class="form-control"
               value="${post.title}" required>
    </div>
    <div class="mb-3">
        <label class="form-label">내용</label>
        <textarea name="content" class="form-control" rows="10" required>${post.content}</textarea>
    </div>
    <button type="submit" class="btn btn-primary">수정</button>
    <a href="/posts/${post.postId}" class="btn btn-secondary">취소</a>
</form>