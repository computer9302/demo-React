import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { createPost } from '../api';

export default function PostWritePage(){
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    async function handleSubmit(e){
        e.preventDefault();
        setError('');
        try {
            const res = await createPost(title, content);
            if (res.success) navigate('/posts');
            else setError(res.message);
        }catch (e){
            if (e.message === 'UNAUTHORIZED') navigate('/login');
            else setError('등록 실패');
        }
    }

    return(
        <form onSubmit={handleSubmit}>
            <h1>글쓰기</h1>
            {error && <p style={{color: 'red'}}>{error}</p>}
            <div>
                <input
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                placeholder="제목"
                required
                />
            </div>
            <div>
                <textarea
                value={content}
                onChange={(e) => setContent(e.target.value)}
                placeholder="내용"
                required
                rows={8}
                />
            </div>
            <button type="submit">등록</button>
            <Link to="/posts">취소</Link>

        </form>
    )
}