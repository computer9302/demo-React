import { Outlet, Link, useNavigate } from 'react-router-dom'
import { logout } from '../api';

export default function Layout() {
    const navigate = useNavigate();

    async function onLogout() {
        await logout();
        navigate('/login');
    }

    return (
        <div>
            <nav>
                <Link to="/posts">게시글</Link>
                {' | '}
                <button type="button" onClick={onLogout}>로그아웃</button>
            </nav>
            <hr />
            <Outlet />
        </div>
    );
}