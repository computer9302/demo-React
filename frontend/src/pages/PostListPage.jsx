import { useEffect, useState} from 'react';
import { Link, useNavigate} from 'react-router-dom';
import { fetchPosts } from '../api';

export default function PostListPage(){
    const [pageDto, setPageDto] = useState(null);
    const [page, setPage] = useState(1);
    const [keyword, setKeyword] = useState('');
    const [type, setType] = useState('title');
    const navigate = useNavigate();

    async function load(p= page, kw = keyword, t=type){
        try{
            const data = await fetchPosts(p, kw, t);
            setPageDto(data);
        }catch (e){
            if (e.message === 'UNAUTHORIZED'){
                navigate('/login');
            }
        }
    }

    useEffect(()=>{
        load(1);
    }, []);

    function onSearch(e){
        e.preventDefault();
        setPage(1);
        load(1, keyword, type);
    }

    if (!pageDto) return <p>로딩...</p>;

    return (
        <div>
            <h1>게시글 목록</h1>
            <Link to="/posts/new">글쓰기</Link>

            <form onSubmit={onSearch}>
                <select value={type} onChange={(e)=> setType(e.target.value)}>
                    <option value="title">제목</option>
                    <option value="writer">작성자</option>
                </select>
                <input value={keyword} onChange={(e)=> setKeyword(e.target.value)} />
                <button type="submit">검색</button>
            </form>

            <ul>
                {pageDto.list.map((post)=> (
                    <li key={post.postId}>
                        <Link to={`/posts/${post.postId}`}>
                            [{post.postId}] {post.title} / {post.writerName}
                        </Link>
                    </li>
                ))}
            </ul>

        <div>
            <button
                disabled={page <= 1}
                onClick={()=> {
                    const next = page - 1;
                    setPage(next);
                    load(next);
                }}
            >
                이전
            </button>
            <span>
                {pageDto.page} / {pageDto.totalPages || 1}
            </span>
            <button
                disabled={page >= pageDto.totalPages}
                onClick={()=> {
                    const next = page + 1;
                    setPage(next);
                    load(next);
                }}
            >
                다음
             </button>
            </div>
        </div>
    );
}