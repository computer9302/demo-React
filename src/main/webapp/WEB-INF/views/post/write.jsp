<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/WEB-INF/views/layout/header.jsp"/>



<div class="card shadow-sm">
    <div class="card-body">
        <h2 class="card-title mb-4">글쓰기</h2>
        <form method="post" action="/posts">
            <div class="mb-3">
                <label class="form-label fw-bold">제목</label>
                <input type="text" name="title" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label fw-bold">내용</label>
                <textarea name="content" class="form-control" rows="12" required></textarea>
            </div>
            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-primary">등록</button>
                <a href="/posts" class="btn btn-secondary">목록</a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>