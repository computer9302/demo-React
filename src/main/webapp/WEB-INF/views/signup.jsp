<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>회원가입</h2>

<c:if test="${not empty errorMsg}">
    <div class="alert alert-danger">${errorMsg}</div>
</c:if>

<div class="row justify-content-center">
    <div class="col-md-5">
        <div class="card shadow-sm">
            <div class="card-body">
                <h2 class="card-title text-center mb-4">로그인</h2>

                <form method="post" action="/signup" id="signupForm">
                    <div class="mb-3">
                        <label class="form-label">아이디</label>
                        <div class="input-group">
                            <input type="text" id="loginId" name="loginId" class="form-control"
                                   value="${loginId}" required>
                            <button type="button" id="btnCheckDuplicate" class="btn btn-outline-secondary">
                                중복확인
                            </button>
                        </div>
                        <div id="checkResult" class="form-text"></div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">비밀번호</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">이름</label>
                        <input type="text" name="name" class="form-control" value="${name}" required>
                    </div>

                    <button type="submit" class="btn btn-primary">가입하기</button>
                    <a href="/login" class="btn btn-secondary">로그인</a>
                </form>

            </div>
        </div>
    </div>

</div>



<script>
   // 중복확인 성공 여부 (가입 전 검증용)
   let isIdChecked = false;
   let checkedLoginId = '';

   const loginIdInput = document.getElementById('loginId');
   const checkResult = document.getElementById('checkResult');
   const btnCheck = document.getElementById('btnCheckDuplicate');
   const signupForm = document.getElementById('signupForm');

   // 이이디 바꾸면 중복확인 다시 해야함
   loginIdInput.addEventListener('input', function (){
       isIdChecked = false;
       checkedLoginId = '';
       checkResult.textContent = '';
       checkResult.className = 'form-text';
   });

   btnCheck.addEventListener('click', function (){
       const loginId = loginIdInput.value.trim();

       if (loginId === ''){
           checkResult.textContent = '아이디를 입력하세요.';
           checkResult.className = 'form-text text-danger';
           return;
       }

       // AJAX 요청 (fetch)
       fetch('/api/members/check-loginId?loginId=' + encodeURIComponent(loginId))
           .then(function (response){
               if (!response.ok){
                   throw new Error('서버 오류');
               }
               return response.json(); // JSON 파싱
          })
       .then(function (data){
            checkResult.textContent = data.message;

            if (data.available){
                checkResult.className = 'form-text text-success';
                isIdChecked = true;
                checkedLoginId = loginId;
            }else{
                checkResult.className = 'form-text text-danger';
                isIdChecked = false;
                checkedLoginId = '';
            }
       })
           .catch(function(){
               checkResult.textContent = '중복확인 중 오류가 발생했습니다.';
               checkResult.className = 'form-text text-danger';
           });
   });

   // 가입 제출 전: 중복확인 했는지 검사
   signupForm.addEventListener('submit', function (e){
       const loginId = loginIdInput.value.trim();

       if (!isIdChecked || checkedLoginId !== loginId){
           e.preventDefault(); // form 전송 막기
           alert('아이디 중복확인을 해주세요.');
       }
   });
</script>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>