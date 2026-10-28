<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="card mb-4">
    <div class="card-header d-flex justify-content-between">
        <h4 class="mb-0">${post.title}</h4>
        <small class="text-muted">${post.createdAt}</small>
    </div>
<div class="card-body">
    <p class="text-muted mb-3">작성자: ${post.writerName}</p>
    <div style="white-space: pre-wrap;">${post.content}</div>
</div>

    <div class="card-footer">
        <!-- 본인 글일 때만 수정/삭제 -->
        <c:if test="${post.memberId == loginMember.memberId}">
            <a href="/posts/${post.postId}/edit" class="btn btn-warning">수정</a>

            <button type="button" id="btnDeletePost" class="btn btn-danger">삭제</button>
        </c:if>
    </div>

</div>

<hr>
<h5 class="mb-3">댓글</h5>
<ul class="list-group list-gruop-flush mb-3" id="commentList">
    <c:forEach var="comment" items="${comments}">
        <li class="list-group-item d-flex justify-content-between">
            <div>
                <strong>${comment.writerName}</strong>
        <span class="text-muted small ms-2">${comment.createdAt}</span>
        <div>${comment.content}</div>
        </div>
        <c:if test="${comment.memberId == loginMember.memberId}">
                <button type="button"
                        class="btn btn-sm btn-outline-danger btn-delete-comment"
                data-comment-id="${comment.commentId}">
                    삭제
                </button>
        </c:if>
        </li>
    </c:forEach>
    <c:if test="${empty comments}">
        <li class="list-group-item text-center text-muted" id="emptyComment">댓글이 없습니다.</li>
    </c:if>
</ul>

<form id="commentForm">
    <div class="input-group">
        <input type="text" id="commentContent" class="form-control"
            placeholder="댓글을 입력하세요" required>
        <button type="submit" class="btn btn-primary">등록</button>
    </div>
</form>

<script>
    const postId = ${post.postId};
    const loginMemberId = ${loginMember.memberId};

    const commentForm = document.getElementById('commentForm');
    const commentContent = document.getElementById('commentContent');
    const commentList = document.getElementById('commentList');
    const btnDeletePost = document.getElementById('btnDeletePost');

    // ==== 댓글 등록 (AJAX POST) ====
    commentForm.addEventListener('submit', function(e){
        e.preventDefault(); // form 기본 제출(새로고침) 막기

        const content = commentContent.value.trim();
        if (content === '') return;

        const body = new URLSearchParams();
        body.append('content', content);

        fetch('/api/posts/' + postId + '/comments', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: body
        })
            .then(function (response) {
                if (!response.ok) throw new Error('등록 실패');
                return response.json();
            })
            .then(function (data){
                // "댓글 없습니다" 문구 제거
                const empty = document.getElementById('emptyComment');
                if (empty) empty.remove();

                // 새 댓글 <li> 만들어서 목록에 추가
                const li = document.createElement('li');
                li.className = 'list-group-item d-flex justify-content-between';
                li.id = 'comment-' + data.commentId;
                li.innerHTML =
                    '<div>' +
                        '<strong>' + data.writerName + '</strong>' +
                        '<span class="text-muted small ms-2">' + data.createdAt + '</span>' +
                        '<div>' + data.content + '</div>' +
                    '</div>' +
                    '<button type="button" class="btn btn-sm btn-outline-danger btn-delete-comment" ' +
                        'data-comment-id="' + data.commentId + '">삭제</button>';

                commentList.appendChild(li);
                commentContent.value = '';
            })
            .catch(function (){
                alert('댓글 등록 중 오류가 발생했습니다.');
            });
    });

    // ==== 댓글 삭제 (이벤트 위임) ====
    commentList.addEventListener('click', function (e){
        if (!e.target.classList.contains('btn-delete-comment')) return;

        if (!confirm('댓글을 삭제하시겠습니까?')) return;

        const commentId = e.target.getAttribute('data-comment-id');

        fetch('/api/posts/' + postId + '/comments/' + commentId,{
            method: 'DELETE'
        })
            .then(function (response){ return response.json(); })
            .then(function (data){
                if (!data.success){
                    alert(data.message);
                    return;
                }
                // 화면에서 해당 <li>만 제거
                const li = document.getElementById('comment-' + commentId);
                if (li) li.remove();
            })
            .catch(function (){
                alert('댓글 삭제 중 오류가 발생했습니다.');
            });
    });

    // ==== 게시글 삭제 (AJAX DELETE) ====
    if (btnDeletePost){
        btnDeletePost.addEventListener('click', function (){
            if (!confirm('삭제하시겠습니까?')) return;

            fetch('/api/posts/' + postId, {
                method: 'DELETE'
            })
                .then(function (response){return response.json(); })
                .then(function (data){
                    if (data.success){
                        location.href = '/posts'; // 목록으로 이동
                    }else{
                        alert(data.message);
                    }
                })
                .catch(function (){
                    alert('삭제 중 오류가 발생했습니다.')
                });
        });
    }

</script>

<a href="/posts" class="btn btn-secondary">목록</a>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>