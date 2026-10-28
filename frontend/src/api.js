const BASE = 'http://localhost:8080';

async function request(path, options = {}){
    const res = await fetch(BASE + path, {
        credentials: 'include', // 세션 쿠키 필수
        ...options,
    });

    if (res.status === 401){
        throw new Error('UNAUTHORIZED');
    }

    const contentType = res.headers.get('content-type') || '';
    if (contentType.includes('application/json')){
        return res.json();
    }
    return res.text();
}

export function login(loginId, password){
    const body = new URLSearchParams({loginId, password});
    return request('/api/login', {
        method: 'POST',
        headers:{'Content-Type': 'application/x-www-form-urlencoded'},
        body,
    });
}

export function logout(){
    return request('/api/logout', {method:'POST'});
}

export function me(){
    return request('/api/me');
}

export function signup(loginId, password, name) {
    const body = new URLSearchParams({loginId, password, name});
    return request('/api/signup', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body,
    });
}


export function fetchPosts(page = 1, keyword='', type= 'title'){
        const q = new URLSearchParams({page, keyword, type});
        return request('/api/posts?' + q.toString());
}

export function fetchPost(postId){
    return request('/api/posts/' + postId);
}

export function createPost(title, content){
    const body = new URLSearchParams({title, content});
    return request('/api/posts', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body,
    });
}

export function updatePost(postId, title, content){
    const body = new URLSearchParams({title, content});
    return request('/api/posts/' + postId,{
        method: 'PUT',
        headers: {'Content-Type' : 'application/x-www-form-urlencoded'},
        body,
    });
}

export function deletePost(postId){
    return request('/api/posts/' + postId, {method: 'DELETE'});
}

export function addComment(postId, content){
    const body = new URLSearchParams({content});
    return request('/api/posts/' + postId + '/comments',{
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body,
    });
}

export function deleteComment(postId, commentId){
    return request('/api/posts/' + postId + '/comments/' + commentId, {
        method:'DELETE',
    });
}