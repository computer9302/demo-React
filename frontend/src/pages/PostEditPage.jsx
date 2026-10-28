import {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {fetchPost, updatePost} from '../api';

export default function PostEditPage(){
    const {postId} = useParams();
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        async function load(){
            try {
                const data = await fetchPost(postId);
                setTitle(data.post.title);
                setContent(data.post.content);
            }catch (e){
                if (e.message === 'UNAUTHORIZED') navigate('/login');
            }
        }
        load()
    }, [postId]);

    async function handleSubmit(e){
        e.preventDefault();
        setError('');
        try {
            const res = await updatePost(postId, title, content);
            if (res.success) navigate('/posts/' + postId);
            else setError(res.message);
        }catch{
            setError('수정 실패');
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <h1>글 수정</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <div>
                <input value={title} onChange={(e) => setTitle(e.target.value)} required />
            </div>
            <div>
                <textarea
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                    required
                    rows={8}
                />
            </div>
            <button type="submit">수정</button>
            <Link to={`/posts/${postId}`}>취소</Link>
        </form>
    )
}