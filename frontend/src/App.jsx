import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import LoginPage from './pages/LoginPage';
import SignupPage from './pages/Signuppage';
import PostListPage from './pages/PostListPage'
import Layout from "./components/Layout";
import PostWritePage from "./pages/PostWritePage";
import PostDetailPage from "./pages/PostDetailPage";
import PostEditPage from './pages/PostEditPage';

export default function App(){
  return (
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/signup" element={<SignupPage />} />
          <Route element={<Layout />}>
            <Route path="/posts" element={<PostListPage />} />
              <Route path="/posts/new" element={<PostWritePage />} />
                  <Route path="/posts/:postId" element={<PostDetailPage />} />
                      <Route path="/posts/:postId/edit" element={<PostEditPage />} />
            <Route path="/" element={<Navigate to="/posts" replace/>} />
          </Route>
        </Routes>
      </BrowserRouter>
  );
}
