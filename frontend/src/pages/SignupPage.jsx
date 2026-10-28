import {useState} from 'react';
import {useNavigate, Link} from 'react-router-dom';
import {signup} from '../api'

export default function SignupPage(){
    const [loginId, setLoginId] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    async function handleSubmit(e){
        e.preventDefault();
        setError('');
        try{
            const res = await signup(loginId, password, name);
            if (res.successs){
                navigate('/login');
            }else {
                setError(res.message);
            }
        }catch{
            setError('회원가입 요청 실패');
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <h1>회원가입</h1>
            {error && <p style={{color:'red'}}>{error}</p>}
            <div>
                <input value={loginId} onChange={(e) => setLoginId(e.target.value)} placeholder="아이디" required />
            </div>
            <div>
                <input type="password" value={password} onChange={(e)=> setPassword(e.target.value)} placeholder="비밀번호" required />
            </div>
            <div>
                <input value={name} onChange={(e)=> setName(e.target.value)} placeholder="이름" required />
            </div>
            <button type="submit">가입하기</button>
            <Link to="/login">로그인</Link>
        </form>
    );
}