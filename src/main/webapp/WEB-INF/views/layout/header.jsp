<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <script>
        window.addEventListener('pageshow', function(e){
            if (e.persisted){
                location.reload();
            }
        });
    </script>
    <meta charset="UTF-8">
    <title>Demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="/main">Demo</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/main">메인</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/posts">게시글</a>
                </li>
                <li class ="nav-item">
                    <form method="post" action="/logout" class="d-inline">
                    <button type="submit" class="btn btn-link nav-link text-decoration-none">
                        로그아웃
                    </button>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
<main class="container mt-4">