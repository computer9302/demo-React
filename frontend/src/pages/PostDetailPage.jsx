import { useEffect, useState} from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { fetchPost, addComment, deleteComment, deletePost, me} from '../api';

export default function PostDetailPage(){
    const { postId } = useParams();
    const navigate = useNavigate();
    const [data, setData] = useState(null);
    const [user, setUser] = useState(null);
    const [content, setContent] = useState('');
    const [error, setError] = useState('');

    async function load(){
        try {
            const [detail, meRes] = await Promise.all([fetchPost(postId), me()]);
            setData(detail);
            setUser(meRes);
        }catch (e){
            if (e.message === 'UNAUTHORIZED') navigate('/login');
            else setError('글을 불러오지 못했습니다.');
        }
    }

    useEffect(()=> {
        load();
    }, [postId]);

    async function onAddComment(e){
        e.preventDefault();
        await addComment(postId, content);
        setContent('');
        load();
    }

    async function onDeleteComment(commentId){
        if (!confirm('댓글을 삭제하시겠습니까?')) return;
        const res = await deleteComment(postId, commentId);
        if (!res.success) alert(res.message);
        load();
    }

    async function onDeletePost(){
        if (!confirm('글을 삭제하시겠습니까?')) return;
        const res = await deletePost(postId);
        if (res.success) navigate('/posts');
        else alert(res.message);
    }

    if (error) return <p style={{ color: 'red' }}>{error}</p>;
    if (!data) return <p>로딩...</p>;

    const { post, comments } = data;
    const isOwner = user && user.memberId === post.memberId;

    return (
        <div>
            <h1>{post.title}</h1>
            <p>
                {post.writerName} / {post.createdAt}
            </p>
            <div style={{ whiteSpace: 'pre-wrap'}}>{post.content}</div>

            {isOwner && (
                <div>
                    <Link to={`/posts/${postId}/edit`}>수정</Link>{' '}
                    <button type="button" onClick={onDeletePost}>
                        삭제
                    </button>
                    </div>
                    )}

            <hr />
            <h3>댓글</h3>
            <ul>
                {comments.map((c) =>(
                    <li key = {c.commentId}>
                        <strong>{c.writerName}</strong> {c.content}{' '}
                        <small>{c.createdAt}</small>
                        {user && user.memberId === c.memberId && (
                            <button type="button" onClick={() => onDeleteComment(c.commentId)}>
                                삭제
                            </button>
                        )}
                        </li>
                    ))}
            </ul>

            <form onSubmit={onAddComment}>
                <input
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                    placeholder="댓글 입력"
                    required
                />
                <button type="submit">등록</button>
            </form>

            <p>
                <Link to="/posts">목록</Link>
            </p>
        </div>
    );
}