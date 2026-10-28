import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { login } from '../api';

export default function LoginPage(){
    const [loginId, setLoginId] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate('');

    async function handleSubmit(e){
        e.preventDefault();
        setError('');
        try{
            const res = await login(loginId, password);
            if (res.success){
                navigate('/posts');
            }else {
                setError(res.message);
            }
        }catch{
            setError('로그인 요청 실패');
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <h1>로그인</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <div>
                <input
                    value={loginId}
                    onChange={(e) => setLoginId(e.target.value)}
                    placeholder="아이디"
                    required
                />
            </div>
            <div>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="비밀번호"
                    required
                />
            </div>
            <button type="submit">로그인</button>
            <Link to="/signup">회원가입</Link>
        </form>
    )
}